package it.unimib.discover.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unimib.discover.dao.impl.ListaDAO;
import it.unimib.discover.dao.impl.WishlistDAO;
import it.unimib.discover.entity.Lista;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Wishlist;

@Service
public class ListeService {
	
	@Autowired
	private ListaDAO listaDAO;
	
	@Autowired
	private WishlistDAO wishlistDAO;

	@Transactional(propagation=Propagation.REQUIRED)
	public List<Lista> getListeByUser(String id) {
		return listaDAO.getListeByUser(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<Lista> getListeArchiviateByUser(String id) {
		return listaDAO.getListeArchiviateByUser(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void salvaWishlist(Wishlist wishlist, MyUserAccount user) {
		wishlist.setArchiviata(false);
		wishlist.setDataCreazione(new Date());
		wishlist.setUserProprietario(user);
		wishlistDAO.persist(wishlist);
	}
}
