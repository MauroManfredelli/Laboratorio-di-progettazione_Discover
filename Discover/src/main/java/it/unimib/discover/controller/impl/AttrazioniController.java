package it.unimib.discover.controller.impl;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.unimib.discover.entity.Attrazione;
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
        return modelAndView;
	}
}
