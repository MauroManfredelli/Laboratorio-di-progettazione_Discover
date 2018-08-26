package it.unimib.discover.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.entity.MySocialUserDetails;
import it.unimib.discover.entity.MyUserAccount;

//Service to Get user info from Database.
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MyUserAccountDAO myUserAccountDAO;
	
	public MyUserDetailsService() {}
	
	// (This method is used by Spring Security API).
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
		MyUserAccount myUserAccount = myUserAccountDAO.findByEmailAndProvider(userName, "DISCOVER");
		if (myUserAccount == null) {
			throw new UsernameNotFoundException("No user found with userName: " + userName);
		}
		// Note: SocialUserDetails extends UserDetails.
		SocialUserDetails principal = new MySocialUserDetails(myUserAccount);
		
		return principal;
	}

}