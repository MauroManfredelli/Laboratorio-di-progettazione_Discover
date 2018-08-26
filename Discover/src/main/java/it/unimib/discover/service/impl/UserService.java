package it.unimib.discover.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.entity.MyUserAccount;

@Service
public class UserService {
	
	@Autowired
	private MyUserAccountDAO myUserAccountDAO;

	public MyUserAccount getUserById(String id) {
		return myUserAccountDAO.findById(id);
	}
	
}
