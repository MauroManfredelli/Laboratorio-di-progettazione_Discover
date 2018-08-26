package it.unimib.discover.controller.impl;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;

public class MyConnectController extends ConnectController {
	
    public MyConnectController(ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		super(connectionFactoryLocator, connectionRepository);
	}
    
    @Override
    protected String connectedView(String providerId) {
        return "redirect:/"+providerId;
    }
    
    // Per gestire il caso in cui l'utente remoto non riesca ad autenticarsi
//    @Override
//    protected String connectView(String providerId) {
//		return "redirect:/"+providerId;	
//	}

}
