package it.unimib.discover.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.entity.MyUserAccount;

public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static Logger log = Logger.getLogger(CustomSuccessHandler.class);
	
	private final int SESSION_TIMEOUT = 60;
	
	@Autowired
	private MyUserAccountDAO myUserAccountDAO;
	
	@Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
            final HttpServletResponse response, final Authentication authentication)
            throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);

        HttpSession session = request.getSession(true);

        try {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		if (!(auth instanceof AnonymousAuthenticationToken)) {
    			UserDetails userDetail = (UserDetails) auth.getPrincipal();
    			if (session.getAttribute("currentUser") == null){
    				MyUserAccount currentUser= myUserAccountDAO.findByEmailAndProvider(userDetail.getUsername(), "DISCOVER");
    				session.setAttribute("currentUser", currentUser);    				
    			}
    			session.setMaxInactiveInterval(SESSION_TIMEOUT*60);
    		}
        } catch (Exception e) {
        	log.error("Error setting currentuser in session: "+e);
        } 
    }
	
}
