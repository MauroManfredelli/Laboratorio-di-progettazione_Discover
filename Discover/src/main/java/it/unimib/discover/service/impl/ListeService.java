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
	public List<Lista> getListeByUser(String id, String ordineListe) {
		List<Lista> liste = listaDAO.getListeByUser(id, ordineListe);
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
	public List<Lista> getListeArchiviateByUser(String id, String ordineListe) {
		List<Lista> liste = listaDAO.getListeArchiviateByUser(id, ordineListe);
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
	public void removeAttrazioneFromLista(Integer idAttrazione, String idLista) {
		Lista lista = listaDAO.findByKey(idLista);
		if(lista.getIdWishlist() != null) {
			attrazioneWishlistDAO.deleyeByWishlistAttrazione(lista.getIdWishlist(), idAttrazione);
		} else {
			visitaDAO.deleteByItinerarioAttrazione(lista.getIdItinerario(), idAttrazione);
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
			itinerario.setConfermato(false);
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
					if(visita.getGiorno() != null && visita.getGiorno() > itinerario.getNumeroGiorni()) {
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
	public Lista getListaByIdItinerario(String idItinerario) {
		Lista lista = listaDAO.getListaByIdItinerario(idItinerario);
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
		if(itinerario.getVisite() != null) {
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
						if(!visitaOther.equals(visita) && visitaOther.getDataVisita() != null && visitaOther.getOrdineNelGiorno() != null &&
								visitaOther.getDataVisita().equals(visita.getDataVisita()) && visitaOther.getOrdineNelGiorno().compareTo(visita.getOrdineNelGiorno()) > 0) {
							ordineNelGiorno ++;
						}
					}
					markerAttrazione.setOrdineMarker(ordineGiorno+"-"+ordineNelGiorno);
				} else if(visita.getGiorno() != null) {
					Integer ordineGiorno = visita.getGiorno();
					Integer ordineNelGiorno = 1;
					for(Visita visitaOther : itinerario.getVisite()) {
						if(!visitaOther.equals(visita) && visitaOther.getGiorno() != null && visitaOther.getOrdineNelGiorno() != null &&
								visitaOther.getGiorno() == visita.getGiorno() && visitaOther.getOrdineNelGiorno().compareTo(visita.getOrdineNelGiorno()) > 0) {
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
					visita.setOrdine(ordineNelGiorno+"");
					markerAttrazione.setOrdineMarker("0-"+ordineNelGiorno);
				}
				markers.add(markerAttrazione);
			}
		}
		return markers;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, List<Visita>> getMapAttrazioniItinerario(Itinerario itinerario) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, List<Visita>> mapAttrazioni = new TreeMap<String, List<Visita>>();
		Date dataMinima = itinerario.getDataInizio();
		if(itinerario.getVisite() != null) {
			for(Visita visita: itinerario.getVisite()) {
				if(visita.getDataVisita() != null) {
					Integer ordineGiorno = (int) ((visita.getDataVisita().getTime() - dataMinima.getTime()) / (24 * 60 * 60 * 1000)) + 1;
					Integer ordineNelGiorno = 1;
					for(Visita visitaOther : itinerario.getVisite()) {
						if(!visitaOther.equals(visita) && visitaOther.getDataVisita() != null && visitaOther.getOrdineNelGiorno() != null &&
								visitaOther.getDataVisita().equals(visita.getDataVisita()) && visitaOther.getOrdineNelGiorno().compareTo(visita.getOrdineNelGiorno()) > 0) {
							ordineNelGiorno ++;
						}
					}
					visita.setOrdine(ordineGiorno+"-"+ordineNelGiorno);
				} else if(visita.getGiorno() != null) {
					Integer ordineGiorno = visita.getGiorno();
					Integer ordineNelGiorno = 1;
					for(Visita visitaOther : itinerario.getVisite()) {
						if(!visitaOther.equals(visita) && visitaOther.getGiorno() != null && visitaOther.getOrdineNelGiorno() != null &&
								visitaOther.getGiorno() == visita.getGiorno() && visitaOther.getOrdineNelGiorno().compareTo(visita.getOrdineNelGiorno()) > 0) {
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
			Date oggi = new Date();
			for(Visita visita: itinerario.getVisite()) {
				if(visita.getDataVisita() != null) {
					String dataVisita = sdf.format(visita.getDataVisita());
					if(sdf.format(oggi).equals(sdf.format(visita.getDataVisita()))) {
						dataVisita += " (Attivo)";
					} else if(visita.getDataVisita().before(oggi)) {
						dataVisita += " (Concluso)";
					}
					if(mapAttrazioni.containsKey(dataVisita)) {
						mapAttrazioni.get(dataVisita).add(visita);
					} else {
						mapAttrazioni.put(dataVisita, Lists.newArrayList(visita));
					}
				} else if(visita.getGiorno() != null) {
					String giorno = "Giorno "+visita.getGiorno();
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
					String keyData = sdf.format(dataInizio);
					if(sdf.format(oggi).equals(sdf.format(dataInizio))) {
						keyData += " (Attivo)";
					} else if(dataInizio.before(oggi)) {
						keyData += " (Concluso)";
					}
					if(!mapAttrazioni.containsKey(keyData)) {
						mapAttrazioni.put(keyData, null);
					}
					dataInizio = this.addDays(dataInizio, 1);
				}
			} else {
				Integer giorno = 1;
				while(giorno <= itinerario.getNumeroGiorni()) {
					if(!mapAttrazioni.containsKey("Giorno "+giorno)) {
						mapAttrazioni.put("Giorno "+giorno, null);
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
			visita.setOrdineNelGiorno(itinerario.getOrdineVisitaNonProgramm());
			if(visita.getDataVisita() != null) {
				visita.setDataVisita(null);
			} else {
				visita.setGiorno(null);
			}
			visitaDAO.persist(visita);
		} else if(key.contains("Giorno")) {
			Integer giorno = Integer.valueOf(key.substring(key.indexOf(" ") + 1));
			visita.setOrdineNelGiorno(itinerario.getOrdineVisita(giorno));
			visita.setGiorno(giorno);
			visitaDAO.persist(visita);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dataVisita = sdf.parse(key);
			visita.setOrdineNelGiorno(itinerario.getOrdineVisita(dataVisita));
			visita.setDataVisita(dataVisita);
			visitaDAO.persist(visita);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void aggiornaVisiteVisitaLive(Integer idItinerario, Integer idVisita, String conferma) {
		Visita visita = visitaDAO.findByKey(idVisita);
		if(visita.getConferma()) {
			visita.setConferma(false);
		} else {
			visita.setConferma(true);
		}
		visitaDAO.persist(visita);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void aggiornaVisiteStessaData(Integer idItinerario, Integer idVisita, String key, Integer ordine) {
		Visita visita = visitaDAO.findByKey(idVisita);
		visita.setOrdineNelGiorno(ordine+"");
		visitaDAO.persist(visita);
	}

	@Transactional
	public Visita copiaVisita(Integer idVisita) {
		Visita visitaRemote = visitaDAO.findByKey(idVisita);
		Visita visita = new Visita(visitaRemote);
		Itinerario itinerario = visita.getItinerario();
		changeEtichettaIfExists(visita, itinerario.getVisite(), 2);
		visitaDAO.persist(visita);
		if(visita.getDataVisita() == null && visita.getGiorno() == null) {
			visita.setOrdine("0-"+visitaRemote.getOrdineNelGiorno());
		} else if(visita.getGiorno() == null) {
			Integer ordineGiorno = (int) ((visita.getDataVisita().getTime() - itinerario.getDataInizio().getTime()) / (24 * 60 * 60 * 1000)) + 1;
			visita.setOrdine(ordineGiorno+"-"+visitaRemote.getOrdineNelGiorno());
		} else {
			visita.setOrdine(visita.getGiorno()+"-"+visitaRemote.getOrdineNelGiorno());
		}
		return visita;
	}

	private void changeEtichettaIfExists(Visita visita, List<Visita> visite, Integer versione) {
		for(Visita v : visite) {
			if(visita.getEtichetta().equals(v.getEtichetta())) {
				visita.setEtichetta(visita.getEtichetta().substring(0, visita.getEtichetta().length() - 2) + "_" + ++versione);
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
	public void salvaModificaEtichetta(Integer idVisita, String etichetta, String ora) {
		Visita visitaRemote = visitaDAO.findByKey(idVisita);
		visitaRemote.setEtichetta(etichetta);
		visitaRemote.setOra(ora != null && !ora.equals("") ? ora : "");
		visitaDAO.persist(visitaRemote);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void salvaNotaPrec(Integer idVisita, String notaPrec) {
		Visita visitaRemote = visitaDAO.findByKey(idVisita);
		visitaRemote.setNotaPrec(notaPrec);
		visitaDAO.persist(visitaRemote);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void salvaNota(Integer idVisita, String nota) {
		Visita visitaRemote = visitaDAO.findByKey(idVisita);
		visitaRemote.setNota(nota);
		visitaDAO.persist(visitaRemote);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean salvaModificaDataVisita(Integer idVisita, String dataVisita) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Visita visita = visitaDAO.findByKey(idVisita);
		Itinerario itinerario = visita.getItinerario();
		if(dataVisita.equals("")) {
			return true;
		}
		Date dataVisitaNew = sdf.parse(dataVisita);
		if(dataVisitaNew.before(itinerario.getDataInizio()) || dataVisitaNew.after(dataVisitaNew)) {
			return false;
		} else {
			return true;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean salvaModificaGiornoVisita(Integer idVisita, Integer giornoVisita) {
		Visita visita = visitaDAO.findByKey(idVisita);
		Itinerario itinerario = visita.getItinerario();
		if(giornoVisita != null && (giornoVisita <= 0 || giornoVisita > itinerario.getNumeroGiorni())) {
			return false;
		} else {
			return true;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void confermaItinerario(Integer idItinerario) {
		Itinerario itinerario = itinerarioDAO.findByKey(idItinerario);
		itinerario.setConfermato(true);
		if(itinerario.getDataInizio() == null) {
			Date data = new Date();
			itinerario.setDataInizio(data);
			for(int giorno = 1; giorno < itinerario.getNumeroGiorni(); giorno++) {
				for(Visita visita : itinerario.getVisite()) {
					if(visita.getGiorno() != null && visita.getGiorno() == giorno) {
						visita.setGiorno(null);
						visita.setDataVisita(data);
					}
				}
				data = this.addDays(data, 1);
			}
			itinerario.setDataFine(data);
			itinerario.setNumeroGiorni(null);
		}
		itinerarioDAO.persist(itinerario);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void rimuoviConfermaItinerario(Integer idItinerario) {
		Itinerario itinerario = itinerarioDAO.findByKey(idItinerario);
		itinerario.setConfermato(false);
		itinerarioDAO.persist(itinerario);
		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean checkVisitaLive(Integer idItinerario) {
		Itinerario itinerario = itinerarioDAO.findByKey(idItinerario);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(itinerario.getConfermato()) {
			Date oggi = new Date();
			for(Visita visita : itinerario.getVisite()) {
				if(visita.getDataVisita() != null && sdf.format(visita.getDataVisita()).equals(sdf.format(oggi))) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<Visita> getMapAttrazioniItinerarioOggi(Itinerario itinerario) {
		Map<String, List<Visita>> mapAttrazioni = this.getMapAttrazioniItinerario(itinerario);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String oggi = sdf.format(new Date());
		for(String key : mapAttrazioni.keySet()) {
			String keyCmp = key;
			if(key.contains("(")) {
				keyCmp = key.substring(0, key.indexOf("(") - 1);
			}
			if(keyCmp.equals(oggi)) {
				return mapAttrazioni.get(key);
			}
		}
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void aggiornaVisiteStessaDataVisitaLiveDB(Integer idItinerario, Integer idVisita, Integer ordine) {}

	@Transactional
	public boolean confermaVisita(Integer idVisita) {
		Visita visita = visitaDAO.findByKey(idVisita);
		if(visita.getConferma()) {
			visita.setConferma(false);
		} else {
			visita.setConferma(true);
		}
		visitaDAO.persist(visita);
		return visita.getConferma();
	}

	public Map<String, String> getMapGiorniItinerario(Itinerario itinerario) {
		Map<String, String> mapGiorniItinerario = new TreeMap<String, String>();
		for(int giorno = 1; giorno<=itinerario.getNumeroGiorni(); giorno++) {
			mapGiorniItinerario.put(giorno+"", "Giorno "+giorno);
		}
		return mapGiorniItinerario;
	}

	public Map<String, String> getMapDateItinerario(Itinerario itinerario) {
		Map<String, String> mapDateItinerario = new TreeMap<String, String>();
		Date data = itinerario.getDataInizio();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		while(!data.after(itinerario.getDataFine())) {
			mapDateItinerario.put(sdf.format(data), sdf.format(data));
			data = this.addDays(data, 1);
		}
		return mapDateItinerario;
	}
}
