package it.unimib.discover.controller.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.unimib.discover.entity.Foto;
import it.unimib.discover.entity.Itinerario;
import it.unimib.discover.entity.Lista;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Visita;
import it.unimib.discover.entity.Wishlist;
import it.unimib.discover.model.ItinerarioModel;
import it.unimib.discover.model.MarkerAttrazione;
import it.unimib.discover.model.ValidationResponse;
import it.unimib.discover.service.impl.AttrazioniService;
import it.unimib.discover.service.impl.ListeService;
import it.unimib.discover.validator.ItinerarioModelValidator;

@Controller
public class ListeController {
	
	@Autowired
	private ListeService listeService;
	
	@Autowired
	private AttrazioniService attrazioniService;
	
	@Autowired
	private ItinerarioModelValidator itinerarioModelValidator;
	
	private void addParametersIndexListe(ModelAndView modelAndView, String idUser, String ordineListe) {
		modelAndView.addObject("wishlist", new Wishlist());
		modelAndView.addObject("itinerario", new ItinerarioModel());
		modelAndView.addObject("wishlistUtente", listeService.getWishlistAttiveByUser(idUser));
		modelAndView.addObject("liste", listeService.getListeByUser(idUser, ordineListe));
		modelAndView.addObject("listeArchiviate", listeService.getListeArchiviateByUser(idUser,ordineListe));
	}
	
	@RequestMapping(value="/liste")
	public ModelAndView cerca(HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/liste/indexListe");
		Object inputOrdinaListeObj = request.getSession().getAttribute("inputOrdinaListe");
		String inputOrdinaListe = "";
    	if(inputOrdinaListeObj == null) {
    		request.getSession().setAttribute("inputOrdinaListe", "");
    	} else {
    		inputOrdinaListe = inputOrdinaListeObj.toString();
    	}
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		this.addParametersIndexListe(modelAndView, user.getId(), inputOrdinaListe);
        return modelAndView;
	}
	
	@RequestMapping( value = "/liste/salvaWishlist" )
	public ModelAndView salvaWishlist(Wishlist wishlist, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView("/discover/liste/"));
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		listeService.salvaWishlist(wishlist, user);
		Object inputOrdinaListeObj = request.getSession().getAttribute("inputOrdinaListe");
		String inputOrdinaListe = "";
    	if(inputOrdinaListeObj == null) {
    		request.getSession().setAttribute("inputOrdinaListe", "");
    	} else {
    		inputOrdinaListe = inputOrdinaListeObj.toString();
    	}
		request.getSession().setAttribute("listeUtente", listeService.getListeByUser(user.getId(), ""));
		this.addParametersIndexListe(modelAndView, user.getId(), inputOrdinaListe);
        return modelAndView;
	}
	
	@RequestMapping( value = "/liste/creaItinerario", method = RequestMethod.GET )
	public @ResponseBody ValidationResponse creaItinerario(ItinerarioModel itinerarioModel, BindingResult errors, HttpServletRequest request) throws ParseException {
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		itinerarioModel.setIdUtente(user.getId());
		itinerarioModelValidator.validate(itinerarioModel, errors);
		if(errors.hasErrors()) {
			return new ValidationResponse("ERROR", errors);
		} else {
			Integer idGenerato = listeService.salvaItinerario(itinerarioModel, user);
			ValidationResponse vr = new ValidationResponse("SUCCESS");
			vr.setIdGenerato(idGenerato+"");
			return vr;
		}
	}
	
	@RequestMapping( value = "/liste/getLista", method = RequestMethod.GET )
	public @ResponseBody Lista salvaWishlist(@RequestParam(name="idLista") String idLista, HttpServletRequest request) {
        return listeService.getListaById(idLista);
	}
	
	@RequestMapping( value = "/liste/getListaByIdItinerario", method = RequestMethod.GET )
	public @ResponseBody Lista getListaByIdItinerario(@RequestParam(name="idItinerario") String idItinerario, HttpServletRequest request) {
        return listeService.getListaByIdItinerario(idItinerario);
	}
	
	@RequestMapping( value = "/liste/getListaByIdWishlist", method = RequestMethod.GET )
	public @ResponseBody Lista getListaByIdWishlist(@RequestParam(name="idWishlist") String idWishlist, HttpServletRequest request) {
        return listeService.getListaByIdWishlist(idWishlist);
	}
	
	@RequestMapping(value = "/liste/rimuoviAttrazioneFromWishlist", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse rimuoviAttrazioneFromWishlist(@RequestParam(name="idAW") String idAW, HttpServletRequest request) {
		listeService.removeAttrazioneFromWishlist(Integer.valueOf(idAW));
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/aggiungiAttrazioneToLista", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiungiAttrazioneToLista(@RequestParam(name="idAttrazione") String idAttrazione, @RequestParam(name="idLista") String idLista,  HttpServletRequest request) {
		if(!listeService.containsAttrazione(Integer.valueOf(idAttrazione), idLista)) {
			listeService.addAttrazioneToLista(Integer.valueOf(idAttrazione), idLista);
			return new ValidationResponse("SUCCESS");
		} else {
			listeService.removeAttrazioneFromLista(Integer.valueOf(idAttrazione), idLista);
			return new ValidationResponse("ERROR");
		}
    }
	
	@RequestMapping(value = "/liste/aggiornaIdListaUtente", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiornaIdListaUtente(@RequestParam(name="idLista") String idLista,  HttpServletRequest request) {
		request.getSession().setAttribute("idListaUtente", idLista == null ? "" : idLista);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/salvaOrdinaListe", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse salvaOrdinaListe(@RequestParam(name="inputOrdinaListe") String inputOrdinaListe,  HttpServletRequest request) {
		request.getSession().setAttribute("inputOrdinaListe", inputOrdinaListe == null ? "" : inputOrdinaListe);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/getAttrazioniLista", method = RequestMethod.GET)
    public @ResponseBody List<Integer> getAttrazioniLista(@RequestParam(name="idLista") String idLista,  HttpServletRequest request) {
		return attrazioniService.getAttrazioniByLista(idLista);
    }
	
	@RequestMapping(value = "/liste/archiviaLista", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse archiviaLista(@RequestParam(name="idLista") String idLista,  HttpServletRequest request) {
		listeService.archiviaLista(idLista);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/recuperaLista", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse recuperaLista(@RequestParam(name="idLista") String idLista,  HttpServletRequest request) {
		listeService.recuperaLista(idLista);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/eliminaLista", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse eliminaLista(@RequestParam(name="idLista") String idLista,  HttpServletRequest request) {
		listeService.eliminaLista(idLista);
		Object idListaUtenteObj = request.getSession().getAttribute("idListaUtente");
    	if(idListaUtenteObj != null) {
    		String idListaUtente = idListaUtenteObj.toString();
    		if(idListaUtente.equals(idLista)) {
    			request.getSession().setAttribute("idListaUtente", "");
    		}
    	}
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/getMarkersAttrazioniByItinerario", method = RequestMethod.GET)
    public @ResponseBody List<MarkerAttrazione> getMarkersItinerario(@RequestParam(name="idItinerario") Integer idItinerario,  HttpServletRequest request) {
		return listeService.getMarkersAttrazioniByItinerario(idItinerario);
    }
	
	@RequestMapping(value="/liste/{id}")
	public ModelAndView visualizzaAttrazione(@PathVariable String id, HttpServletRequest request) throws ParseException {
		Lista lista = listeService.getListaById(id);
		if(lista.getIdItinerario() != null) {
			ModelAndView modelAndView = new ModelAndView("secure/liste/itinerario");
			Itinerario itinerario = listeService.getItinerarioById(lista.getIdItinerario());
			itinerario.setMapAttrazioni(listeService.getMapAttrazioniItinerario(itinerario));
			modelAndView.addObject("itinerario", itinerario);
			if(itinerario.getNumeroGiorni() != null) {
				modelAndView.addObject("giorniItinerario", listeService.getMapGiorniItinerario(itinerario));
			} else {
				modelAndView.addObject("dateItinerario", listeService.getMapDateItinerario(itinerario));
			}
			if(itinerario.getVisite() != null && !itinerario.getVisite().isEmpty()) {
				modelAndView.addObject("localitaCentroMappa", itinerario.getVisite().get(0).getAttrazione().getPosizione().getDescrizione());
			}
	        return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("secure/liste/wishlist");
			Wishlist wishlist = listeService.getWishlistById(lista.getIdWishlist());
			modelAndView.addObject("wishlist", wishlist);
			modelAndView.addObject("itinerario", new ItinerarioModel());
			return modelAndView;
		}
	}
	
	@RequestMapping(value="/liste/{id}/tutteLeDate")
	public ModelAndView tutteLeDate(@PathVariable Integer id, HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/liste/itinerario");
		Itinerario itinerario = listeService.getItinerarioById(id);
		itinerario.setMapAttrazioni(listeService.getMapAttrazioniItinerario(itinerario));
		modelAndView.addObject("itinerario", itinerario);
		modelAndView.addObject("allDate", true);
		if(itinerario.getNumeroGiorni() != null) {
			modelAndView.addObject("giorniItinerario", listeService.getMapGiorniItinerario(itinerario));
		} else {
			modelAndView.addObject("dateItinerario", listeService.getMapDateItinerario(itinerario));
		}
		if(itinerario.getVisite() != null && !itinerario.getVisite().isEmpty()) { 
			modelAndView.addObject("localitaCentroMappa", itinerario.getVisite().get(0).getAttrazione().getPosizione().getDescrizione());
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/liste/aggiornaVisite", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiornaVisite(@RequestParam(name="idItinerario") Integer idItinerario, @RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="key") String key,  HttpServletRequest request) throws ParseException {
		listeService.aggiornaVisite(idItinerario, idVisita, key);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/confermaItinerario", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse confermaItinerario(@RequestParam(name="idItinerario") Integer idItinerario, HttpServletRequest request) throws ParseException {
		listeService.confermaItinerario(idItinerario);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/rimuoviConfermaItinerario", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse rimuoviConfermaItinerario(@RequestParam(name="idItinerario") Integer idItinerario, HttpServletRequest request) throws ParseException {
		listeService.rimuoviConfermaItinerario(idItinerario);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/aggiornaVisiteStessaDataDB", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiornaVisiteStessaDataDB(@RequestParam(name="idItinerario") Integer idItinerario, @RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="key") String key, @RequestParam(name="ordine") Integer ordine,  HttpServletRequest request) throws ParseException {
		listeService.aggiornaVisiteStessaData(idItinerario, idVisita, key, ordine);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/salvaNotaPrec", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse salvaNotaPrec(@RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="notaPrec") String notaPrec, HttpServletRequest request) throws ParseException {
		listeService.salvaNotaPrec(idVisita, notaPrec);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/salvaNotaVisita", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse salvaNotaVisita(@RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="nota") String nota, HttpServletRequest request) throws ParseException {
		listeService.salvaNota(idVisita, nota);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/salvaModificaEtichetta", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse salvaModificaEtichetta(@RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="etichetta") String etichetta, @RequestParam(name="ora") String ora, HttpServletRequest request) throws ParseException {
		if(StringUtils.isNotBlank(etichetta)) {
			listeService.salvaModificaEtichetta(idVisita, etichetta, ora);
			return new ValidationResponse("SUCCESS");
		} else {
			return new ValidationResponse("ERROR");
		}
    }
	
	@RequestMapping(value = "/liste/salvaModificaDataVisita", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse salvaModificaDataVisita(@RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="dataVisita") String dataVisita, HttpServletRequest request) throws ParseException {
		if(dataVisita != null && listeService.salvaModificaDataVisita(idVisita, dataVisita)) {
			return new ValidationResponse("SUCCESS");
		} else {
			return new ValidationResponse("ERROR");
		}
    }
	
	@RequestMapping(value = "/liste/salvaModificaGiornoVisita", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse salvaModificaGiornoVisita(@RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="giornoVisita") Integer giornoVisita, HttpServletRequest request) throws ParseException {
		if(listeService.salvaModificaGiornoVisita(idVisita, giornoVisita)) {
			return new ValidationResponse("SUCCESS");
		} else {
			return new ValidationResponse("ERROR");
		}
    }
	
	@RequestMapping(value = "/liste/eliminaVisita", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse eliminaVisita(@RequestParam(name="idVisita") Integer idVisita, HttpServletRequest request) throws ParseException {
		listeService.eliminaVisita(idVisita);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/eliminaVisitaLive", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse eliminaVisitaLive(@RequestParam(name="idVisita") Integer idVisita, HttpServletRequest request) throws ParseException {
		listeService.eliminaVisitaLive(idVisita);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/copiaVisita", method = RequestMethod.GET)
    public @ResponseBody Visita copiaVisita(@RequestParam(name="idVisita") Integer idVisita, HttpServletRequest request) throws ParseException {
		Visita visita = listeService.copiaVisita(idVisita);
		visita.setItinerario(null);
		visita.getAttrazione().setRecensioni(null);
		for(Foto foto : visita.getAttrazione().getFotoPrincipali()) {
			foto.setAttrazione(null);
			foto.setRecensione(null);
		}
		return visita;
    }
	
	@RequestMapping(value = "/liste/visitaLive", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse visitaLive(@RequestParam(name="idItinerario") Integer idItinerario, HttpServletRequest request) throws ParseException {
		if(listeService.checkVisitaLive(idItinerario)) {
			return new ValidationResponse("SUCCESS");
		} else {
			return new ValidationResponse("ERROR");
		}
    }
	
	@RequestMapping(value="/liste/visitaLive/{idItinerario}")
	public ModelAndView visitaLiveItinerario(@PathVariable Integer idItinerario, HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/liste/visitaLive");
		Itinerario itinerario = listeService.getItinerarioById(idItinerario);
		modelAndView.addObject("itinerario", itinerario);
		modelAndView.addObject("visiteLive", listeService.getMapAttrazioniItinerarioOggi(itinerario));
        return modelAndView;
	}
	
	@RequestMapping(value = "/liste/aggiornaVisiteVisitaLive", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiornaVisiteVisitaLive(@RequestParam(name="idItinerario") Integer idItinerario, @RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="conferma") String conferma,  HttpServletRequest request) throws ParseException {
		listeService.aggiornaVisiteVisitaLive(idItinerario, idVisita, conferma);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/aggiornaVisiteStessaDataVisitaLiveDB", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiornaVisiteStessaDataVisitaLiveDB(@RequestParam(name="idItinerario") Integer idItinerario, @RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="ordine") Integer ordine,  HttpServletRequest request) throws ParseException {
		listeService.aggiornaVisiteStessaDataVisitaLiveDB(idItinerario, idVisita, ordine);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/confermaVisita", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse confermaVisita(@RequestParam(name="idVisita") Integer idVisita, HttpServletRequest request) throws ParseException {
		boolean confermata = listeService.confermaVisita(idVisita);
		if(confermata) {
			return new ValidationResponse("CONFERMATA");
		} else {
			return new ValidationResponse("NON CONFERMATA");
		}
    }
	
	@RequestMapping(value = "/liste/aggiornaOrdiniDB")
    public @ResponseBody ValidationResponse aggiornaOrdiniDB(@RequestBody ArrayList<Map<String, String>> updates,  HttpServletRequest request) {
		listeService.aggiornaOrdiniDB(updates);
		return new ValidationResponse("SUCCESS");
    }
	
}
