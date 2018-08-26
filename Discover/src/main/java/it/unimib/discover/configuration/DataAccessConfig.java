package it.unimib.discover.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
@ComponentScan({ "it.unimib.discover" })
public class DataAccessConfig {
	
	@Autowired
    private Environment environment;
 
    @Bean
    @Description("Spring session factory")
    public SessionFactory sessionFactory() {
    	LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
    	sessionFactoryBuilder.scanPackages("it.unimib.discover.*");
    	sessionFactoryBuilder.addProperties(hibernateProperties());
        
        return sessionFactoryBuilder.buildSessionFactory();
     }
     
    @Bean
    @Description("Spring data source")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        
        return dataSource;
    }
    
    /**
     * Set hibernate properties
     * @return Properties
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        
        return properties;        
    }
     
    @Bean
    @Autowired
    @Description("Hibernate transaction manager")
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    	
    	return new HibernateTransactionManager(sessionFactory());   		
    }
	
}
