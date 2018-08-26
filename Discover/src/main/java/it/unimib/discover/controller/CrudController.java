package it.unimib.discover.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * Potrebbe essere utile per la gestione degli utenti o di attrazioni.
 * AL MOMENTO NON UTILIZZATO
 */
public abstract class CrudController<PK extends Serializable, T> {

    public abstract ModelAndView index(ModelMap model);
	
    public abstract ModelAndView newPage(ModelMap model);
    
    public abstract ModelAndView show(ModelMap model, @PathVariable PK pk);
    
    public abstract ModelAndView edit(ModelMap model, @PathVariable PK pk);
    
	public abstract ModelAndView update(HttpServletRequest request, Model model, @PathVariable PK pk, @Validated T t, BindingResult result);
    
	public abstract ModelAndView create(HttpServletRequest request, Model model, @Validated T t, BindingResult result);
    
    public abstract ModelAndView delete(HttpServletRequest request, ModelMap model, @PathVariable PK pk);
}
