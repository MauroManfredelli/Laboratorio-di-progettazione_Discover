package it.unimib.discover.controller.impl;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.unimib.discover.model.MarkerAttrazione;
import it.unimib.discover.service.impl.MapsService;

@Controller
public class MapsController {
	
	@Autowired
	private MapsService mapsService;
	
	@RequestMapping(value="/mappa")
	public ModelAndView cerca(HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("secure/attrazioni/mappa");
        return modelAndView;
	}
	
	@RequestMapping(value = "/mappa/getMarkersAttrazioni", method = RequestMethod.GET)
    public @ResponseBody List<MarkerAttrazione> getMarkersAttrazioni(HttpServletRequest request) {
		return mapsService.getMarkersAttrazioniJson();
    }
	
	@RequestMapping(value = "/mappa/getMarkersAttrazioniVisitaLive", method = RequestMethod.GET)
    public @ResponseBody List<MarkerAttrazione> getMarkersAttrazioniVisitaLive(@RequestParam(name="idItinerario") Integer idItinerario, HttpServletRequest request) {
		return mapsService.getMarkersAttrazioniVisitaLiveJson(idItinerario);
    }
	
}
