package it.unimib.discover.login.providers;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Service;

import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.login.autologin.Autologin;

@Service
public class BaseProvider {
	
	@Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private TextEncryptor textEncryptor;

    @Autowired
    private MyUserAccountDAO myUserAccountDAO;

    @Autowired
    protected Autologin autologin;

    protected void saveUserDetails(MyUserAccount userBean, String provider) {
    	if (StringUtils.isNotEmpty(userBean.getPassword())) {
			userBean.setPassword(textEncryptor.encrypt(userBean.getPassword()));
		} else {
			userBean.setPassword("password");
		}
    	myUserAccountDAO.registerNewUserAccount(userBean, provider);
    }

	protected void updateUserDetails(MyUserAccount userBean, String provider) {
		myUserAccountDAO.updateUserDetails(userBean, provider);
	}
    
    protected boolean isAllInformationAvailable(MyUserAccount userBean) {
		return  StringUtils.isNotEmpty(userBean.getEmail()) &&
				StringUtils.isNotEmpty(userBean.getFirstName()) &&
				StringUtils.isNotEmpty(userBean.getLastName());
    }

    public void autoLoginUser(MyUserAccount userBean) {
    	autologin.setSecuritycontext(userBean);
    }

    public ConnectionRepository getConnectionRepository() {
    	return connectionRepository;
    }

    public void setConnectionRepository(ConnectionRepository connectionRepository) {
    	this.connectionRepository = connectionRepository;
    }

}
