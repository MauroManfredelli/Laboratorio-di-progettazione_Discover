package it.unimib.discover.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

@ComponentScan("it.unimib.discover")
public class ApplicationInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		//Load MVC, DAO, Security context
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(MvcDispatcherConfig.class, DataAccessConfig.class, SecurityConfig.class);      
        ctx.setServletContext(servletContext);
        servletContext.addListener(new ContextLoaderListener(ctx));
        
        //Sitemesh filter
        FilterRegistration.Dynamic sitemesh = servletContext.addFilter("siteMeshFilter", new SiteMeshFilter());
        sitemesh.addMappingForUrlPatterns( null, true, "/*");
        
        //Add servlet dispatcher
        DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
        //necessario per gestire ExceptionHandelerController
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        //Add servlet dispatcher
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);
 
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");	
        
        // Spring Security Filter
        FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");
	} 
}