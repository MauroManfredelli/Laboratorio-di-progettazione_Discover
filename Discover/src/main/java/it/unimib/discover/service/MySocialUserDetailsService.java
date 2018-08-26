package it.unimib.discover.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.entity.MySocialUserDetails;
import it.unimib.discover.entity.MyUserAccount;

@Service
public class MySocialUserDetailsService implements SocialUserDetailsService {
    
    
   @Autowired
   private MyUserAccountDAO myUserAccountDAO;
 
   // Loads the UserDetails by using the userID of the user.
   // (This method Is used by Spring Security API).
   @Override
   public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
 
       MyUserAccount account= myUserAccountDAO.findById(userId);
        
       MySocialUserDetails userDetails= new MySocialUserDetails(account);
        
       return userDetails;
   }
 
}