package it.unimib.discover.controller.impl;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Wishlist;
import it.unimib.discover.model.ValidationResponse;
import it.unimib.discover.service.impl.ListeService;

@Controller
public class ListeController {
	
	@Autowired
	private ListeService listeService;
	
	@RequestMapping(value="/liste")
	public ModelAndView cerca(HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/liste/indexListe");
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		modelAndView.addObject("wishlist", new Wishlist());
		modelAndView.addObject("liste", listeService.getListeByUser(user.getId()));
		modelAndView.addObject("listeArchiviate", listeService.getListeArchiviateByUser(user.getId()));
        return modelAndView;
	}
	
	@RequestMapping( value = "/liste/salvaWishlist" )
	public ModelAndView salvaWishlist(Wishlist wishlist, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("secure/liste/indexListe");
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		listeService.salvaWishlist(wishlist, user);
		request.getSession().setAttribute("listeUtente", listeService.getListeByUser(user.getId()));
		modelAndView.addObject("wishlist", new Wishlist());
		modelAndView.addObject("liste", listeService.getListeByUser(user.getId()));
		modelAndView.addObject("listeArchiviate", listeService.getListeArchiviateByUser(user.getId()));
        return modelAndView;
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
	
}
