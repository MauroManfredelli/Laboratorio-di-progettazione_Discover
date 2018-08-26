package it.unimib.discover.login.autologin;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.unimib.discover.entity.MyUserAccount;

@Service
public class Autologin {

    public void setSecuritycontext(MyUserAccount userForm) {
	   	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	   	grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	   	Authentication authentication = new UsernamePasswordAuthenticationToken(userForm.getEmail(), userForm.getPassword(), grantedAuthorities);
	   	SecurityContextHolder.getContext().setAuthentication(authentication);
   }
}
