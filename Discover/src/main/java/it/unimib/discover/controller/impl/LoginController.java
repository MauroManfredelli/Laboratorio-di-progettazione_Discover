package it.unimib.discover.controller.impl;

import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.login.autologin.Autologin;
import it.unimib.discover.login.providers.FacebookProvider;
import it.unimib.discover.login.providers.GoogleProvider;

@Controller
public class LoginController {
	
	@Autowired
    private MyUserAccountDAO userRepository;

	@Autowired
	private TextEncryptor textEncryptor;

    @Autowired
    private Autologin autologin;
    
    @Autowired
	private MessageSource messageSource;
    
	private static final String REDIRECT_CONNECT_HOME = "redirect:/secure";
	private static final int SESSION_TIMEOUT = 60;
	private static final String PROVIDER = "DISCOVER";
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "googleError", required = false) String googleError,
			@RequestParam(value = "facebookError", required = false) String facebookError,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
    	ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		if (googleError != null) {
			model.addObject("error", "Il servizio Google+ per l'autenticazione non è al momento disponibile.");
		}
		if (facebookError != null) {
			model.addObject("error", "Il servizio Facebook per l'autenticazione non è al momento disponibile.");
		}
		if (logout != null) {
			model.addObject("msg", messageSource.getMessage("login.logut_success", null, "" , Locale.ITALIAN));
		}
		model.setViewName("session/login");

		return model;

	}
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

 	// for 403 access denied page
 	@RequestMapping(value = "/403", method = RequestMethod.GET)
 	public ModelAndView accesssDenied() {

 		ModelAndView model = new ModelAndView();

 		// check if user is login
 		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
 		if (!(auth instanceof AnonymousAuthenticationToken)) {
 			UserDetails userDetail = (UserDetails) auth.getPrincipal();

 			model.addObject("username", userDetail.getUsername());

 		}

 		model.setViewName("403");
 		return model;

 	}

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView showRegistration(MyUserAccount userBean) {
    	return new ModelAndView("session/register");
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUser(HttpServletResponse httpServletResponse, HttpServletRequest request, Model model, @Valid MyUserAccount userBean, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
		    return "registration";
		}
		// userBean.setProvider("REGISTRATION");
		// Save the details in DB
		if (StringUtils.isNotEmpty(userBean.getPassword())) {
		    userBean.setPassword(textEncryptor.encrypt(userBean.getPassword()));
		}
		String id = UUID.randomUUID().toString();
		userBean.setId(id);
		userRepository.registerNewUserAccount(userBean, PROVIDER);
	
		autologin.setSecuritycontext(userBean);
	
		request.getSession().setAttribute("currentUser", userBean);
		request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT*60);
		return REDIRECT_CONNECT_HOME;
    }
	
    /** If we can't find a user/email combination */
    @RequestMapping("/login-error")
    public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
    }

	@Autowired
    FacebookProvider facebookProvider;
    
    private static final String REDIRECT_CONNECT_FACEBOOK_ERROR = "redirect:/login?facebookError";

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String loginToFacebook(HttpServletRequest request, Model model) {
    	MyUserAccount user = facebookProvider.getFacebookUserData("", "", request, new MyUserAccount());
    	if(user == null) {
    		return REDIRECT_CONNECT_FACEBOOK_ERROR;
    	} else {
    		request.getSession().setAttribute("currentUser", user);
    		request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT*60);
    		return REDIRECT_CONNECT_HOME;
    	}
    }

    @Autowired
    GoogleProvider googleProvider;
    
	private static final String REDIRECT_CONNECT_GOOGLE_ERROR = "redirect:/login?googleError";

    @RequestMapping(value = "/google", method = RequestMethod.GET)
    public String loginToGoogle(HttpServletRequest request, Model model) {
    	MyUserAccount user = googleProvider.getGoogleUserData("", "", request, new MyUserAccount());
    	if(user == null) {
    		return REDIRECT_CONNECT_GOOGLE_ERROR;
    	} else {
    		request.getSession().setAttribute("currentUser", user);
    		request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT*60);
    		return REDIRECT_CONNECT_HOME;
    	}
    }

}
