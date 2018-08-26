package it.unimib.discover.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.unimib.discover.entity.Attrazione;
import it.unimib.discover.service.impl.AttrazioniService;

@Controller
public class HomepageController {
	
	@Autowired
	private AttrazioniService attrazioniService;
	
	@RequestMapping("/")
	public String defaultUrl() {
		return "redirect:/secure";
	}

	@RequestMapping(value = { "/secure", "/secure/home"} )
    public ModelAndView index(HttpServletRequest request, ModelMap model) {
    	ModelAndView modelAndView = new ModelAndView("secure/attrazioni/bacheca");
    	List<Attrazione> attrazioniBacheca = attrazioniService.getAttrazioniBacheca();
    	modelAndView.addObject("listAttrazioni", attrazioniBacheca);
        return modelAndView;
    }
	
	@RequestMapping(value = "/errore" )
    public void errore(HttpServletRequest request, ModelMap model) {
		throw new NullPointerException();
    }
	
}
