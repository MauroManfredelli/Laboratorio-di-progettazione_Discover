package it.unimib.discover.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unimib.discover.dao.impl.AttrazioneDAO;
import it.unimib.discover.dao.impl.AttrazioneWishlistDAO;
import it.unimib.discover.dao.impl.ItinerarioDAO;
import it.unimib.discover.dao.impl.ListaDAO;
import it.unimib.discover.dao.impl.VisitaDAO;
import it.unimib.discover.dao.impl.WishlistDAO;
import it.unimib.discover.entity.Attrazione;
import it.unimib.discover.entity.AttrazioneWishlist;
import it.unimib.discover.entity.Itinerario;
import it.unimib.discover.entity.Lista;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Visita;
import it.unimib.discover.entity.Wishlist;

@Service
public class ListeService {
	
	@Autowired
	private ListaDAO listaDAO;
	
	@Autowired
	private WishlistDAO wishlistDAO;
	
	@Autowired
	private ItinerarioDAO itinerarioDAO;
	
	@Autowired
	private AttrazioneWishlistDAO attrazioneWishlistDAO;
	
	@Autowired
	private VisitaDAO visitaDAO;
	
	@Autowired
	private AttrazioneDAO attrazioneDAO;

	@Transactional(propagation=Propagation.REQUIRED)
	public List<Lista> getListeByUser(String id) {
		List<Lista> liste = listaDAO.getListeByUser(id);
		for(Lista lista : liste) {
			if(lista.getIdWishlist() != null) {
				lista.setAttrazioni(attrazioneDAO.getPrimeAttrazioniOfWishlist(lista.getIdWishlist()));
			} else {
				lista.setAttrazioni(attrazioneDAO.getPrimeAttrazioniOfItinerario(lista.getIdItinerario()));
			}
		}
		return liste;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<Lista> getListeArchiviateByUser(String id) {
		List<Lista> liste = listaDAO.getListeArchiviateByUser(id);
		for(Lista lista : liste) {
			if(lista.getIdWishlist() != null) {
				lista.setAttrazioni(attrazioneDAO.getPrimeAttrazioniOfWishlist(lista.getIdWishlist()));
			} else {
				lista.setAttrazioni(attrazioneDAO.getPrimeAttrazioniOfItinerario(lista.getIdItinerario()));
			}
		}
		return liste;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void salvaWishlist(Wishlist wishlist, MyUserAccount user) {
		wishlist.setArchiviata(false);
		wishlist.setDataCreazione(new Date());
		wishlist.setUserProprietario(user);
		wishlistDAO.persist(wishlist);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean containsAttrazione(Integer idAttrazione, String idLista) {
		return listaDAO.containsAttrazione(idAttrazione, idLista);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void addAttrazioneToLista(Integer idAttrazione, String idLista) {
		Attrazione attrazione = attrazioneDAO.findByKey(idAttrazione);
		Lista lista = listaDAO.findByKey(idLista);
		Wishlist wishlist = null;
		Itinerario itinerario = null;
		if(lista.getIdWishlist() != null) {
			wishlist = wishlistDAO.findByKey(lista.getIdWishlist());
			AttrazioneWishlist aw = new AttrazioneWishlist(attrazione, wishlist);
			attrazioneWishlistDAO.persist(aw);
		} else {
			itinerario = itinerarioDAO.findByKey(lista.getIdItinerario());
			Visita visita = new Visita(attrazione, itinerario);
			visitaDAO.persist(visita);
		}
	}
}
