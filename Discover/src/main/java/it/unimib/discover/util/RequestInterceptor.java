package it.unimib.discover.util;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import it.unimib.discover.entity.MyUserAccount;

public class RequestInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private Environment environment;
	
	private static Logger log = Logger.getLogger(RequestInterceptor.class);

	/**
	 * Intercept pre-handle request
	 * Print full request url
	 * Print Codice fiscale field
	 * Print Partita Iva field
	 * @return 
	 * @throws IOException 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		request.setAttribute("startTime", System.currentTimeMillis());
		log.info("Request URL:: " + request.getRequestURI());
		if(request.getQueryString()!=null){
				log.info("\t Params: " + request.getQueryString());
		}
		checkAuthorization(request);
		checkApplicationAttributes(request);
		
		return true;
	}

	/**
	 * Intercept after post-handle request
	 * Print code URL params
	 * Print view name
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView != null){			
			log.info("\t " + modelAndView.getViewName());
		}
    }

	/**
	 * Intercept after completion request
	 * Print time to execute the request
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long startTime = (Long) request.getAttribute("startTime");
		Date time = new Date(System.currentTimeMillis() - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
		log.info("\t Time:: " + (formatter.format(time)));
	}
	
	/**
	 * Check if session is valid.
	 * @param request
	 */
	private void checkAuthorization(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		MyUserAccount user = (MyUserAccount) session.getAttribute("currentUser");
		if (user != null){
			log.info("\t User:: " + user.getUserName());
		}else{
			SecurityContextHolder.clearContext();
			session = null;
		}		
	}	
	
	/**
	 * Add Default application attributes, always available.
	 * @param request HttpServletRequest
	 */
	private void checkApplicationAttributes(HttpServletRequest request) {
		request.setAttribute("version", environment.getProperty("version.number"));
		request.setAttribute("jsVersion", environment.getProperty("version.js.number"));
		request.setAttribute("buildDate", environment.getProperty("version.date"));
	}

}