package it.unimib.discover.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import it.unimib.discover.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
    private MyUserDetailsService myUserDetailsService;
	 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    // Set Service load Users in Database
	    auth.userDetailsService(myUserDetailsService);
	}
 
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		 
        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/signup", "/login", "/logout").permitAll();
 
        // /userInfo page requires login as ROLE_USER  
        // If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/secure/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
								.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
								.antMatchers("/**").permitAll();
 
        // Form Login config
        http.authorizeRequests().and().formLogin()//
               // Submit URL of login page.
               .loginProcessingUrl("/j_spring_security_check") // Submit URL
               .loginPage("/login")//
               .defaultSuccessUrl("/secure")//
               .successHandler(successHandler())
               .failureUrl("/login?error")//
               .usernameParameter("username")//
               .passwordParameter("password");
 
        // Logout Config
        http.authorizeRequests().and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
        
        // Spring Social Config.
        http.apply(new SpringSocialConfigurer());
		
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler(){
		CustomSuccessHandler successHandler = new CustomSuccessHandler();
		return successHandler;
	}	
	
	@Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }
	
    @Override
    public UserDetailsService userDetailsService() {
    	return myUserDetailsService;
    }

}