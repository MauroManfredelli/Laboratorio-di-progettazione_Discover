package it.unimib.discover.login.providers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;

import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.entity.MyUserAccount;

@Service
public class GoogleProvider   {

	@Autowired
	BaseProvider baseProvider ;
	
	@Autowired
	MyUserAccountDAO myUserAcoountDAO;
	
	private Google google;
	
	private static final String PROVIDER="GOOGLE";
    
	public MyUserAccount getGoogleUserData(String code, String state, HttpServletRequest request, MyUserAccount userBean) {
		ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();
		if (connectionRepository.findPrimaryConnection(Google.class) == null) {
			return null;
		}
		google = connectionRepository.getPrimaryConnection(Google.class).getApi();

		populateUserDetailsFromGoogle(userBean);
		//Check if all Info has been collected
		if(!baseProvider.isAllInformationAvailable(userBean)) {
		    return null;
		}
		try {
			//Save the details in DB
			baseProvider.saveUserDetails(userBean, PROVIDER);
			baseProvider.updateUserDetails(userBean, PROVIDER);
		} catch(Exception e) {
			baseProvider.updateUserDetails(userBean, PROVIDER);
		}
		
		//Login the User
		baseProvider.autoLoginUser(userBean);
				
		return userBean;
	}

	protected void populateUserDetailsFromGoogle(MyUserAccount userform) {
		Person googleUser = google.plusOperations().getGoogleProfile();
		userform.setId(googleUser.getId());
		userform.setEmail(googleUser.getAccountEmail());
		userform.setFirstName(googleUser.getGivenName());
		userform.setLastName(googleUser.getFamilyName());
		userform.setUserName(googleUser.getDisplayName());
		userform.setImageUrl(googleUser.getImageUrl());
		userform.setRole(MyUserAccount.ROLE_USER);
	}

}
