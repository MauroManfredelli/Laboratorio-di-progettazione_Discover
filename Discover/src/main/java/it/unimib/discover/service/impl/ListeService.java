package it.unimib.discover.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

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
import it.unimib.discover.entity.MarkerPosizione;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Visita;
import it.unimib.discover.entity.Wishlist;
import it.unimib.discover.model.ItinerarioModel;
import it.unimib.discover.model.MarkerAttrazione;

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
	public Itinerario getItinerarioById(Integer idItinerario) {
		return itinerarioDAO.findByKey(idItinerario);
	}

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
		if(wishlist.getId() != null) {
			Wishlist wishlistRemote = wishlistDAO.findByKey(wishlist.getId());
			wishlistRemote.setNome(wishlist.getNome());
			wishlistDAO.persist(wishlistRemote);
		} else {
			wishlist.setArchiviata(false);
			wishlist.setDataCreazione(new Date());
			wishlist.setUserProprietario(user);
			wishlistDAO.persist(wishlist);
		}
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

	@Transactional(propagation=Propagation.REQUIRED)
	public void archiviaLista(String idLista) {
		Lista lista = listaDAO.findByKey(idLista);
		if(lista.getIdWishlist() != null) {
			Wishlist wishlist = wishlistDAO.findByKey(lista.getIdWishlist());
			wishlist.setArchiviata(true);
			wishlistDAO.persist(wishlist);
		} else {
			Itinerario itinerario = itinerarioDAO.findByKey(lista.getIdItinerario());
			itinerario.setArchiviata(true);
			itinerarioDAO.persist(itinerario);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void recuperaLista(String idLista) {
		Lista lista = listaDAO.findByKey(idLista);
		if(lista.getIdWishlist() != null) {
			Wishlist wishlist = wishlistDAO.findByKey(lista.getIdWishlist());
			wishlist.setArchiviata(false);
			wishlistDAO.persist(wishlist);
		} else {
			Itinerario itinerario = itinerarioDAO.findByKey(lista.getIdItinerario());
			itinerario.setArchiviata(false);
			itinerarioDAO.persist(itinerario);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void eliminaLista(String idLista) {
		Lista lista = listaDAO.findByKey(idLista);
		if(lista.getIdWishlist() != null) {
			Wishlist wishlist = wishlistDAO.findByKey(lista.getIdWishlist());
			List<AttrazioneWishlist> listAw = attrazioneWishlistDAO.getByIdWishList(wishlist.getId());
			attrazioneWishlistDAO.delete(listAw);
			wishlistDAO.delete(wishlist);
		} else {
			Itinerario itinerario = itinerarioDAO.findByKey(lista.getIdItinerario());
			List<Visita> visite = visitaDAO.getByIdItinerario(itinerario.getId());
			visitaDAO.delete(visite);
			itinerarioDAO.delete(itinerario);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<Wishlist> getWishlistAttiveByUser(String id) {
		return wishlistDAO.getWishlistAttiveByUser(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void salvaItinerario(ItinerarioModel itinerarioModel, MyUserAccount user) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(itinerarioModel.getId() != null) {
			Itinerario itinerario = itinerarioDAO.findByKey(itinerarioModel.getId());
			itinerario.setNome(itinerarioModel.getNome());
			Date dataInizio = null, dataFine = null;
			if(itinerarioModel.getDivisione() == 1) {
				itinerario.setDataInizio(sdf.parse(itinerarioModel.getDataInizio()));
				itinerario.setDataFine(sdf.parse(itinerarioModel.getDataFine()));
				itinerario.setNumeroGiorni(null);
				dataInizio = sdf.parse(itinerarioModel.getDataInizio());
				dataFine = sdf.parse(itinerarioModel.getDataFine());
			} else {
				itinerario.setDataInizio(null);
				itinerario.setDataFine(null);
				itinerario.setNumeroGiorni(itinerarioModel.getNumeroGiorni());
			}
			for(Visita visita : itinerario.getVisite()) {
				if(itinerarioModel.getDivisione() == 1) {
					visita.setGiorno(null);
					if(visita.getDataVisita() != null &&
							(visita.getDataVisita().before(dataInizio) || visita.getDataVisita().after(dataFine))) {
						visita.setDataVisita(null);
					}
				} else {
					visita.setDataVisita(null);
					if(visita.getGiorno() > itinerario.getNumeroGiorni()) {
						visita.setGiorno(null);
					}
				}
				visita.setNota(null);
				visita.setNotaPrec(null);
			}
			itinerarioDAO.persist(itinerario);
		} else {
			Itinerario itinerario = new Itinerario();
			itinerario.setNome(itinerarioModel.getNome());
			if(itinerarioModel.getDivisione() == 1) {
				itinerario.setDataInizio(sdf.parse(itinerarioModel.getDataInizio()));
				itinerario.setDataFine(sdf.parse(itinerarioModel.getDataFine()));
			} else {
				itinerario.setNumeroGiorni(itinerarioModel.getNumeroGiorni());
			}
			itinerario.setDataCreazione(new Date());
			itinerario.setArchiviata(false);
			itinerario.setUserProprietario(user);
			itinerarioDAO.persist(itinerario);
			List<Wishlist> wishlistList = new ArrayList<Wishlist>();
			for(Integer id : itinerarioModel.getIdWishlist()) {
				wishlistList.add(wishlistDAO.findByKey(id));
			}
			for(Wishlist wishlist : wishlistList) {
				List<AttrazioneWishlist> listAw = wishlist.getAttrazioniWishlist();
				for(AttrazioneWishlist attrazioneWish : listAw) {
					Visita visita = new Visita();
					visita.setAttrazione(attrazioneWish.getAttrazione());
					visita.setItinerario(itinerario);
					visita.setEtichetta(attrazioneWish.getAttrazione().getNome());
					visitaDAO.persist(visita);
				}
				attrazioneWishlistDAO.delete(listAw);
				wishlistDAO.delete(wishlist);
			}
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public Lista getListaById(String idLista) {
		Lista lista = listaDAO.findByKey(idLista);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(lista.getDataInizio() != null) {
			lista.setFormattedDataInizio(sdf.format(lista.getDataInizio()));
			lista.setFormattedDataFine(sdf.format(lista.getDataFine()));
		}
		return lista;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<MarkerAttrazione> getMarkersAttrazioniByItinerario(Integer idItinerario) {
		Itinerario itinerario = itinerarioDAO.findByKey(idItinerario);
		List<MarkerAttrazione> markers = new ArrayList<MarkerAttrazione>();
		Date dataMinima = itinerario.getDataInizio();
		for(Visita visita : itinerario.getVisite()) {
			Attrazione attrazione = visita.getAttrazione();
			MarkerPosizione posizione = attrazione.getPosizione();
			MarkerAttrazione markerAttrazione = new MarkerAttrazione();
			markerAttrazione.setId(visita.getId());
			markerAttrazione.setIdAttrazione(attrazione.getId());
			markerAttrazione.setLatitudine(posizione.getLatitudine());
			markerAttrazione.setLongitudine(posizione.getLongitudine());
			markerAttrazione.setLocalita(posizione.getDescrizione());
			markerAttrazione.setNome(visita.getEtichetta());
			markerAttrazione.setImagePath(attrazione.getFotoPrincipali().get(0).getPath());
			if(visita.getDataVisita() != null || visita.getGiorno() != null) {
				markerAttrazione.setTipoMarker("marker_red");
			} else {
				markerAttrazione.setTipoMarker("marker_black");
			}
			if(visita.getDataVisita() != null) {
				Integer ordineGiorno = (int) ((visita.getDataVisita().getTime() - dataMinima.getTime()) / (24 * 60 * 60 * 1000)) + 1;
				Integer ordineNelGiorno = 1;
				for(Visita visitaOther : itinerario.getVisite()) {
					if(!visitaOther.equals(visita) && visitaOther.getDataVisita() != null && visitaOther.getOra() != null &&
							visitaOther.getDataVisita().equals(visita.getDataVisita()) && visitaOther.getOra().compareTo(visita.getOra()) > 0) {
						ordineNelGiorno ++;
					}
				}
				markerAttrazione.setOrdineMarker(ordineGiorno+"-"+ordineNelGiorno);
			} else if(visita.getGiorno() != null) {
				Integer ordineGiorno = visita.getGiorno();
				Integer ordineNelGiorno = 1;
				for(Visita visitaOther : itinerario.getVisite()) {
					if(!visitaOther.equals(visita) && visitaOther.getGiorno() != null && visitaOther.getOra() != null &&
							visitaOther.getGiorno() == visita.getGiorno() && visitaOther.getOra().compareTo(visita.getOra()) > 0) {
						ordineNelGiorno ++;
					}
				}
				markerAttrazione.setOrdineMarker(ordineGiorno+"-"+ordineNelGiorno);
			} else {
				Integer ordineNelGiorno = 1;
				for(Visita visitaOther : itinerario.getVisite()) {
					if(!visitaOther.equals(visita) && visitaOther.getGiorno() == null && visitaOther.getDataVisita() == null &&
							visitaOther.getOrdine() != null) {
						ordineNelGiorno ++;
					}
				}
				markerAttrazione.setOrdineMarker("0-"+ordineNelGiorno);
			}
			markers.add(markerAttrazione);
		}
		return markers;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, List<Visita>> getMapAttrazioniItinerario(Itinerario itinerario) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, List<Visita>> mapAttrazioni = new TreeMap<String, List<Visita>>();
		Date dataMinima = itinerario.getDataInizio();
		for(Visita visita: itinerario.getVisite()) {
			if(visita.getDataVisita() != null) {
				Integer ordineGiorno = (int) ((visita.getDataVisita().getTime() - dataMinima.getTime()) / (24 * 60 * 60 * 1000)) + 1;
				Integer ordineNelGiorno = 1;
				for(Visita visitaOther : itinerario.getVisite()) {
					if(!visitaOther.equals(visita) && visitaOther.getDataVisita() != null && visitaOther.getOra() != null &&
							visitaOther.getDataVisita().equals(visita.getDataVisita()) && visitaOther.getOra().compareTo(visita.getOra()) > 0) {
						ordineNelGiorno ++;
					}
				}
				visita.setOrdine(ordineGiorno+"-"+ordineNelGiorno);
			} else if(visita.getGiorno() != null) {
				Integer ordineGiorno = visita.getGiorno();
				Integer ordineNelGiorno = 1;
				for(Visita visitaOther : itinerario.getVisite()) {
					if(!visitaOther.equals(visita) && visitaOther.getGiorno() != null && visitaOther.getOra() != null &&
							visitaOther.getGiorno() == visita.getGiorno() && visitaOther.getOra().compareTo(visita.getOra()) > 0) {
						ordineNelGiorno ++;
					}
				}
				visita.setOrdine(ordineGiorno+"-"+ordineNelGiorno);
			} else {
				Integer ordineNelGiorno = 1;
				for(Visita visitaOther : itinerario.getVisite()) {
					if(!visitaOther.equals(visita) && visitaOther.getGiorno() == null && visitaOther.getDataVisita() == null &&
							visitaOther.getOrdine() != null) {
						ordineNelGiorno ++;
					}
				}
				visita.setOrdine("0-"+ordineNelGiorno);
			}
		}
		for(Visita visita: itinerario.getVisite()) {
			if(visita.getDataVisita() != null) {
				String dataVisita = sdf.format(visita.getDataVisita());
				if(mapAttrazioni.containsKey(dataVisita)) {
					mapAttrazioni.get(dataVisita).add(visita);
				} else {
					mapAttrazioni.put(dataVisita, Lists.newArrayList(visita));
				}
			} else if(visita.getGiorno() != null) {
				String giorno = "giorno "+visita.getGiorno();
				if(mapAttrazioni.containsKey(giorno)) {
					mapAttrazioni.get(giorno).add(visita);
				} else {
					mapAttrazioni.put(giorno, Lists.newArrayList(visita));
				}
			} else {
				if(mapAttrazioni.containsKey("Non programm.")) {
					mapAttrazioni.get("Non programm.").add(visita);
				} else {
					mapAttrazioni.put("Non programm.", Lists.newArrayList(visita));
				}
			}
			if(mapAttrazioni.containsKey("Tutte le date")) {
				mapAttrazioni.get("Tutte le date").add(visita);
			} else {
				mapAttrazioni.put("Tutte le date", Lists.newArrayList(visita));
			}
		}
		if(itinerario.getDataInizio() != null) {
			Date dataInizio = itinerario.getDataInizio();
			while(!dataInizio.after(itinerario.getDataFine())) {
				if(!mapAttrazioni.containsKey(sdf.format(dataInizio))) {
					mapAttrazioni.put(sdf.format(dataInizio), null);
				}
				dataInizio = this.addDays(dataInizio, 1);
			}
		} else {
			Integer giorno = 1;
			while(giorno < itinerario.getNumeroGiorni()) {
				if(!mapAttrazioni.containsKey("giorno "+giorno)) {
					mapAttrazioni.put(sdf.format("giorno "+giorno), null);
				}
				giorno++;
			}
		}
		if(!mapAttrazioni.containsKey("Non programm.")) {
			mapAttrazioni.put("Non programm.", null);
		}
		for(String key : mapAttrazioni.keySet()) {
			if(mapAttrazioni.get(key) != null) {
				Collections.sort(mapAttrazioni.get(key));
			}
		}
		return mapAttrazioni;
	}
	
	private Date addDays(Date currDate, int days) {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(currDate);
	    for(int i=0;i<days;i++) {
	        cal.add(Calendar.DAY_OF_MONTH, 1);
	    }
	    return cal.getTime();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void aggiornaVisite(Integer idItinerario, Integer idVisita, String key) throws ParseException {
		Itinerario itinerario = itinerarioDAO.findByKey(idItinerario);
		Visita visita = visitaDAO.findByKey(idVisita);
		if(key.contains("Non programm")) {
			if(visita.getDataVisita() != null) {
				visita.setDataVisita(null);
			} else {
				visita.setGiorno(null);
			}
			visita.setOra(itinerario.getOrdineVisitaNonProgramm());
			visitaDAO.persist(visita);
		} else if(key.contains("giorno")) {
			Integer giorno = Integer.valueOf(key.substring(key.indexOf(" ") + 1));
			visita.setGiorno(giorno);
			visita.setOra(itinerario.getOrdineVisita(giorno));
			visitaDAO.persist(visita);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dataVisita = sdf.parse(key);
			visita.setDataVisita(dataVisita);
			visita.setOra(itinerario.getOrdineVisita(dataVisita));
			visitaDAO.persist(visita);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void aggiornaVisiteStessaData(Integer idItinerario, Integer idVisita, String key, Integer ordine) {
		Visita visita = visitaDAO.findByKey(idVisita);
		visita.setOra(ordine+"");
		visitaDAO.persist(visita);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public Visita copiaVisita(Integer idVisita) {
		Visita visitaRemote = visitaDAO.findByKey(idVisita);
		Visita visita = new Visita(visitaRemote);
		Itinerario itinerario = visita.getItinerario();
		changeEtichettaIfExists(visita, itinerario.getVisite(), 2);
		visitaDAO.persist(visita);
		visita.setItinerario(null);
		visita.getAttrazione().setRecensioni(null);
		return visita;
	}

	private void changeEtichettaIfExists(Visita visita, List<Visita> visite, Integer versione) {
		for(Visita v : visite) {
			if(visita.getEtichetta().equals(v.getEtichetta())) {
				visita.setEtichetta(visita.getEtichetta().substring(0, visita.getEtichetta().length() - 2) + "_" + versione++);
				changeEtichettaIfExists(visita, visite, versione);
				return;
			}
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void eliminaVisita(Integer idVisita) {
		Visita visitaRemote = visitaDAO.findByKey(idVisita);
		visitaRemote.setAttrazione(null);
		visitaRemote.setItinerario(null);
		visitaDAO.delete(visitaRemote);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void salvaModificaEtichetta(Integer idVisita, String etichetta) {
		Visita visitaRemote = visitaDAO.findByKey(idVisita);
		visitaRemote.setEtichetta(etichetta);
		visitaDAO.persist(visitaRemote);
	}
}
