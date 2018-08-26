package it.unimib.discover.login.providers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import it.unimib.discover.entity.MyUserAccount;

@Service
public class FacebookProvider  {
	
	@Autowired
	private BaseProvider baseProvider ;
	
	private Facebook facebook;
	
	private static final String PROVIDER = "FACEBOOK";

	public MyUserAccount getFacebookUserData(String code, String state, HttpServletRequest request, MyUserAccount userBean) {
		ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return null;
		} else {
			facebook = connectionRepository.getPrimaryConnection(Facebook.class).getApi();
		}
		//Populate the Bean
		populateUserDetailsFromFacebook(userBean);
		//Check if all Info has been collected
		if(!baseProvider.isAllInformationAvailable(userBean)) {
		    return null;
		}
		//Save the details in DB
		baseProvider.saveUserDetails(userBean, PROVIDER);
		//Login the User
		baseProvider.autoLoginUser(userBean);
		//model.addAttribute("loggedInUser",userBean);
		return userBean;
	}

	protected void populateUserDetailsFromFacebook(MyUserAccount userform) {
		FacebookProfile user = facebook.userOperations().getUserProfile();
		userform.setId(user.getId());
		userform.setEmail(user.getEmail());
		userform.setFirstName(user.getFirstName());
		userform.setLastName(user.getLastName());
		userform.setUserName(user.getUsername());
		byte[] img = facebook.userOperations().getUserProfileImage();
		// TODO salvare l'immagine del profilo e aggiungerla all'url
		userform.setImageUrl(null);
		userform.setRole(MyUserAccount.ROLE_USER);
	}

	 

}
