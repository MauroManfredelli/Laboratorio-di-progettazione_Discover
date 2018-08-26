/**
 * 
 */
package it.unimib.discover.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@PropertySource({ "classpath:email.properties" })
public class MailerConfig {
	
	@Autowired
    private Environment environment;
	
	public boolean isEnable(){
		return Boolean.valueOf(environment.getProperty("email.enable"));
	}
	
	@Bean
    public JavaMailSender javaMailService() {
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setDefaultEncoding("UTF-8");
		javaMailSender.setHost(environment.getProperty("email.host"));
		javaMailSender.setPort(environment.getProperty("email.port", Integer.class));
		javaMailSender.setUsername(environment.getProperty("email.username"));
		javaMailSender.setPassword(environment.getProperty("email.password"));
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", environment.getProperty("email.smtp.auth"));
		properties.put("mail.smtp.starttls.enable", environment.getProperty("email.smtp.starttls.enable"));
		javaMailSender.setJavaMailProperties(properties);
		return javaMailSender;
    }
	
	@Bean
	public VelocityEngine velocityEngine() throws VelocityException, IOException{
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		factory.setVelocityProperties(props);
		
		return factory.createVelocityEngine();
	}

}
