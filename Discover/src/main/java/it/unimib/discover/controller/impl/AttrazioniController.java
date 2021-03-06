package it.unimib.discover.controller.impl;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.unimib.discover.entity.Attrazione;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.entity.Wishlist;
import it.unimib.discover.model.ParametriRicerca;
import it.unimib.discover.service.impl.AttrazioniService;

@Controller
public class AttrazioniController {
	
	@Autowired
	private AttrazioniService attrazioniService;
	
	@RequestMapping(value="/attrazione/{id}")
	public ModelAndView visualizzaAttrazione(@PathVariable Integer id, HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/attrazioni/attrazione");
		Attrazione a = attrazioniService.getAttrazioneById(id);
		modelAndView.addObject("attrazione", a);
		modelAndView.addObject("wishlist", new Wishlist());
        return modelAndView;
	}
	
	@RequestMapping(value="/cerca")
	public ModelAndView cerca(HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/cerca");
		modelAndView.addObject("parametriRicerca", new ParametriRicerca());
		modelAndView.addObject("tipologie", attrazioniService.getAllTipologieAttrazioni());
		modelAndView.addObject("stati", attrazioniService.getAllStatiAttrazione());
		modelAndView.addObject("wishlist", new Wishlist());
        return modelAndView;
	}
	
	@RequestMapping( value = "/attrazioni/cerca" )
	public ModelAndView cercaAttrazioni(ParametriRicerca parametriRicerca, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("secure/cerca");
		MyUserAccount user = (MyUserAccount) request.getSession().getAttribute("currentUser");
		modelAndView.addObject("parametriRicerca", parametriRicerca);
		List<Attrazione> listAttrazioni = attrazioniService.getAttrazioniByRicerca(parametriRicerca, user);
		modelAndView.addObject("listAttrazioni", listAttrazioni);
		modelAndView.addObject("tipologie", attrazioniService.getAllTipologieAttrazioni());
		modelAndView.addObject("stati", attrazioniService.getAllStatiAttrazione());
		modelAndView.addObject("wishlist", new Wishlist());
        return modelAndView;
	}
}
