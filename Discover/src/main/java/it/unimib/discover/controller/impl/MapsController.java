package it.unimib.discover.controller.impl;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapsController {
	
	@RequestMapping(value="/mappa")
	public ModelAndView cerca(HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/attrazioni/mappa");
        return modelAndView;
	}
	
}
