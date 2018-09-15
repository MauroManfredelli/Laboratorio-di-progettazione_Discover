package it.unimib.discover.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import it.unimib.discover.dao.impl.AttrazioneDAO;
import it.unimib.discover.dao.impl.AttrazioneWishlistDAO;
import it.unimib.discover.dao.impl.ItinerarioDAO;
import it.unimib.discover.dao.impl.MyUserAccountDAO;
import it.unimib.discover.dao.impl.VisitaDAO;
import it.unimib.discover.dao.impl.WishlistDAO;
import it.unimib.discover.entity.AttrazioneWishlist;
import it.unimib.discover.entity.Itinerario;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Visita;
import it.unimib.discover.entity.Wishlist;

@Service
public class TestService {
	
	@Autowired
	private MyUserAccountDAO myUserAccountDAO;
	
	@Autowired
	private WishlistDAO wishlistDAO;
	
	@Autowired
	private VisitaDAO visitaDAO;
	
	@Autowired
	private AttrazioneWishlistDAO attrazioneWishlistDAO;
	
	@Autowired
	private ItinerarioDAO itinerarioDAO;
	
	@Autowired
	private AttrazioneDAO attrazioneDAO;
	
	@Transactional
	public MyUserAccount setUpTestUser() {
		return myUserAccountDAO.findById("4");
	}
	
	@Transactional
	public Wishlist setUpWishlist(MyUserAccount userTest) {
		Wishlist wishlistTest = new Wishlist();
    	wishlistTest.setNome("Test wishlist 1");
    	wishlistTest.setArchiviata(false);
    	wishlistTest.setDataCreazione(new Date());
    	wishlistTest.setUserProprietario(userTest);
    	AttrazioneWishlist attrazione1 = new AttrazioneWishlist(attrazioneDAO.findByKey(100), wishlistTest);
    	AttrazioneWishlist attrazione2 = new AttrazioneWishlist(attrazioneDAO.findByKey(101), wishlistTest);
    	wishlistTest.setAttrazioniWishlist(Lists.newArrayList(attrazione1, attrazione2));
    	wishlistDAO.persist(wishlistTest);
    	attrazioneWishlistDAO.persist(attrazione1);
    	attrazioneWishlistDAO.persist(attrazione2);
    	return wishlistTest;
	}
	
	@Transactional
	public Itinerario setUpItinerario(MyUserAccount userTest) {
		Itinerario itinerarioTestSameName = new Itinerario();
    	itinerarioTestSameName.setArchiviata(false);
    	itinerarioTestSameName.setUserProprietario(userTest);
    	itinerarioTestSameName.setNome("Test itinerario 1");
    	itinerarioTestSameName.setNumeroGiorni(3);
    	itinerarioDAO.persist(itinerarioTestSameName);
    	return itinerarioTestSameName;
	}
	
	@Transactional
	public void tearDownTest(Itinerario itinerarioTest, Itinerario itinerarioTestSameName) {
		if(itinerarioTestSameName != null) {
			itinerarioDAO.delete(itinerarioTestSameName);
		} if(itinerarioTest != null) {
			for(Visita visita : itinerarioTest.getVisite()) {
				visitaDAO.delete(visita);
			}
        	itinerarioDAO.delete(itinerarioTest);
        }
	}
	
}
