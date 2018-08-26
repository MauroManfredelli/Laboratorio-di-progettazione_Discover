package it.unimib.discover.configuration;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import it.unimib.discover.controller.impl.MyConnectController;
import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.login.providers.MyConnectionSignUp;

@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {
	
	private boolean autoSignUp = true;
	
    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private MyUserAccountDAO myUserAccountDAO;
    
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        // Facebook
        FacebookConnectionFactory ffactory = new FacebookConnectionFactory(
            	environment.getRequiredProperty("spring.social.facebook.appId"), 
    			environment.getRequiredProperty("spring.social.facebook.appSecret"));
        
        
        ffactory.setScope(environment.getProperty("spring.social.facebook.scope"));
        
        // auth_type=reauthenticate
   
        cfConfig.addConnectionFactory(ffactory);
   
        
        // Google
        GoogleConnectionFactory gfactory = new GoogleConnectionFactory(
            	environment.getRequiredProperty("spring.social.google.appId"), 
    			environment.getRequiredProperty("spring.social.google.appSecret"));
   
        gfactory.setScope(environment.getProperty("spring.social.google.scope"));
        
        cfConfig.addConnectionFactory(gfactory);
    }
   
    // The UserIdSource determines the userID of the user.
    @Override
    public UserIdSource getUserIdSource() {
        return new SessionIdUserIdSource();
    }

    public static final class SessionIdUserIdSource implements UserIdSource {
        @Override
        public String getUserId() {
        	RequestAttributes request = RequestContextHolder.currentRequestAttributes();
            String uuid = (String) request.getAttribute("_socialUserUUID", RequestAttributes.SCOPE_SESSION);
            if (uuid == null) {
                uuid = UUID.randomUUID().toString();
                request.setAttribute("_socialUserUUID", uuid, RequestAttributes.SCOPE_SESSION);
            }
            return uuid;
        }
    }
   
    // Read and insert to USERCONNECTION.
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
   
        // org.springframework.social.security.SocialAuthenticationServiceRegistry
        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator,
                Encryptors.noOpText());
   
        if (autoSignUp) {
            // Config to:
            // After login to social.
            // Automatically create corresponding USER_ACCOUNT if not already.
            ConnectionSignUp connectionSignUp = new MyConnectionSignUp(myUserAccountDAO);
            usersConnectionRepository.setConnectionSignUp(connectionSignUp);
        } else {
            // Config to:
            // After login to social.
            // If USER_ACCOUNTS does not exists
            // Redirect to register page.
            usersConnectionRepository.setConnectionSignUp(null);
        }
        return usersConnectionRepository;
    }
    
    /**
     * This bean manages the connection flow between
     * the account provider and the example application.
     */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public MyConnectController myConnectController(final ConnectionFactoryLocator connectionFactoryLocator,
                                               final ConnectionRepository connectionRepository) {
    	MyConnectController myConnectController = new MyConnectController(connectionFactoryLocator, connectionRepository);
        return myConnectController;
    }
    
//    @Bean
//    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
//    public Google google(final ConnectionRepository repository) {
//        final Connection<Google> connection = repository.findPrimaryConnection(Google.class);
//        return connection != null ? connection.getApi() : null;
//    }
//    
//    @Bean
//    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
//    public Facebook facebook(final ConnectionRepository repository) {
//        final Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
//        return connection != null ? connection.getApi() : null;
//    }
	
}
