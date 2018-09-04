package it.unimib.discover.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unimib.discover.dao.impl.AttrazioneDAO;
import it.unimib.discover.dao.impl.MarkerPosizioneDAO;
import it.unimib.discover.entity.Attrazione;
import it.unimib.discover.entity.MarkerPosizione;
import it.unimib.discover.model.MarkerAttrazione;

@Service
public class MapsService {
	
	@Autowired
	private MarkerPosizioneDAO markerPosizioneDAO;
	
	@Autowired
	private AttrazioneDAO attrazioneDAO;

	@Transactional(propagation=Propagation.REQUIRED)
	public List<MarkerAttrazione> getMarkersAttrazioniJson() {
		List<MarkerPosizione> markersDB = markerPosizioneDAO.findAll();
		List<MarkerAttrazione> markers = new ArrayList<MarkerAttrazione>();
		for(MarkerPosizione posizione : markersDB) {
			MarkerAttrazione markerAttrazione = new MarkerAttrazione();
			Attrazione attrazione = attrazioneDAO.findByPosizione(posizione.getID());
			markerAttrazione.setId(posizione.getID());
			markerAttrazione.setIdAttrazione(attrazione.getId());
			markerAttrazione.setLatitudine(posizione.getLatitudine());
			markerAttrazione.setLongitudine(posizione.getLongitudine());
			markerAttrazione.setLocalita(posizione.getDescrizione());
			markerAttrazione.setNome(attrazione.getNome());
			markers.add(markerAttrazione);
		}
		return markers;
	}

}
