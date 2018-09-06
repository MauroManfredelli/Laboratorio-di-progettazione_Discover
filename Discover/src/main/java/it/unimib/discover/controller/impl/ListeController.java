package it.unimib.discover.controller.impl;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.unimib.discover.entity.Itinerario;
import it.unimib.discover.entity.Lista;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Visita;
import it.unimib.discover.entity.Wishlist;
import it.unimib.discover.model.ItinerarioModel;
import it.unimib.discover.model.MarkerAttrazione;
import it.unimib.discover.model.ValidationResponse;
import it.unimib.discover.service.impl.ListeService;
import it.unimib.discover.validator.ItinerarioModelValidator;

@Controller
public class ListeController {
	
	@Autowired
	private ListeService listeService;
	
	@Autowired
	private ItinerarioModelValidator itinerarioModelValidator;
	
	private void addParametersIndexListe(ModelAndView modelAndView, String idUser) {
		modelAndView.addObject("wishlist", new Wishlist());
		modelAndView.addObject("itinerario", new ItinerarioModel());
		modelAndView.addObject("wishlistUtente", listeService.getWishlistAttiveByUser(idUser));
		modelAndView.addObject("liste", listeService.getListeByUser(idUser));
		modelAndView.addObject("listeArchiviate", listeService.getListeArchiviateByUser(idUser));
	}
	
	@RequestMapping(value="/liste")
	public ModelAndView cerca(HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/liste/indexListe");
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		this.addParametersIndexListe(modelAndView, user.getId());
        return modelAndView;
	}
	
	@RequestMapping( value = "/liste/salvaWishlist" )
	public ModelAndView salvaWishlist(Wishlist wishlist, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("secure/liste/indexListe");
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		listeService.salvaWishlist(wishlist, user);
		request.getSession().setAttribute("listeUtente", listeService.getListeByUser(user.getId()));
		this.addParametersIndexListe(modelAndView, user.getId());
        return modelAndView;
	}
	
	@RequestMapping( value = "/liste/creaItinerario", method = RequestMethod.GET )
	public @ResponseBody ValidationResponse creaItinerario(ItinerarioModel itinerarioModel, BindingResult errors, HttpServletRequest request) throws ParseException {
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		itinerarioModelValidator.validate(itinerarioModel, errors);
		if(errors.hasErrors()) {
			return new ValidationResponse("ERROR", errors);
		} else {
			listeService.salvaItinerario(itinerarioModel, user);
			return new ValidationResponse("SUCCESS");
		}
	}
	
	@RequestMapping( value = "/liste/getLista", method = RequestMethod.GET )
	public @ResponseBody Lista salvaWishlist(@RequestParam(name="idLista") String idLista, HttpServletRequest request) {
        return listeService.getListaById(idLista);
	}
	
	@RequestMapping(value = "/liste/aggiungiAttrazioneToLista", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiungiAttrazioneToLista(@RequestParam(name="idAttrazione") String idAttrazione, @RequestParam(name="idLista") String idLista,  HttpServletRequest request) {
		if(!listeService.containsAttrazione(Integer.valueOf(idAttrazione), idLista)) {
			listeService.addAttrazioneToLista(Integer.valueOf(idAttrazione), idLista);
			return new ValidationResponse("SUCCESS");
		} else {
			return new ValidationResponse("ERROR");
		}
    }
	
	@RequestMapping(value = "/liste/aggiornaIdListaUtente", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiornaIdListaUtente(@RequestParam(name="idLista") String idLista,  HttpServletRequest request) {
		request.getSession().setAttribute("idListaUtente", idLista == null ? "" : idLista);
		return new ValidationResponse("SUCCESS");
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
			modelAndView.addObject("localitaCentroMappa", itinerario.getVisite().get(0).getAttrazione().getPosizione().getDescrizione());
	        return modelAndView;
		} else {
			return null;
		}
	}
	
	@RequestMapping(value = "/liste/aggiornaVisite", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiornaVisite(@RequestParam(name="idItinerario") Integer idItinerario, @RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="key") String key,  HttpServletRequest request) throws ParseException {
		listeService.aggiornaVisite(idItinerario, idVisita, key);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/aggiornaVisiteStessaDataDB", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse aggiornaVisiteStessaDataDB(@RequestParam(name="idItinerario") Integer idItinerario, @RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="key") String key, @RequestParam(name="ordine") Integer ordine,  HttpServletRequest request) throws ParseException {
		listeService.aggiornaVisiteStessaData(idItinerario, idVisita, key, ordine);
		return new ValidationResponse("SUCCESS");
    }
	
	@RequestMapping(value = "/liste/salvaModificaEtichetta", method = RequestMethod.GET)
    public @ResponseBody ValidationResponse salvaModificaEtichetta(@RequestParam(name="idVisita") Integer idVisita, @RequestParam(name="etichetta") String etichetta, HttpServletRequest request) throws ParseException {
		if(StringUtils.isNotBlank(etichetta)) {
			listeService.salvaModificaEtichetta(idVisita, etichetta);
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
	
	@RequestMapping(value = "/liste/copiaVisita", method = RequestMethod.GET)
    public @ResponseBody Visita copiaVisita(@RequestParam(name="idVisita") Integer idVisita, HttpServletRequest request) throws ParseException {
		return listeService.copiaVisita(idVisita);
    }
	
}
