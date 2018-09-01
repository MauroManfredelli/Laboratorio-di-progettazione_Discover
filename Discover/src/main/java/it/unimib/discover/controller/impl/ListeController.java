package it.unimib.discover.controller.impl;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Wishlist;
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
		modelAndView.addObject("wishlist", new Wishlist());
		modelAndView.addObject("liste", listeService.getListeByUser(user.getId()));
		modelAndView.addObject("listeArchiviate", listeService.getListeArchiviateByUser(user.getId()));
        return modelAndView;
	}
	
}
