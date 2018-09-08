package it.unimib.discover.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unimib.discover.dao.impl.AttrazioneDAO;
import it.unimib.discover.dao.impl.ItinerarioDAO;
import it.unimib.discover.dao.impl.MarkerPosizioneDAO;
import it.unimib.discover.dao.impl.VisitaDAO;
import it.unimib.discover.entity.Attrazione;
import it.unimib.discover.entity.Itinerario;
import it.unimib.discover.entity.MarkerPosizione;
import it.unimib.discover.entity.Visita;
import it.unimib.discover.model.MarkerAttrazione;

@Service
public class MapsService {
	
	@Autowired
	private MarkerPosizioneDAO markerPosizioneDAO;
	
	@Autowired
	private AttrazioneDAO attrazioneDAO;
	
	@Autowired
	private ItinerarioDAO itinerarioDAO;
	
	@Autowired
	private VisitaDAO visitaDAO;

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

	@Transactional(propagation=Propagation.REQUIRED)
	public List<MarkerAttrazione> getMarkersAttrazioniVisitaLiveJson(Integer idItinerario) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Itinerario itinerario = itinerarioDAO.findByKey(idItinerario);
		List<MarkerAttrazione> markers = new ArrayList<MarkerAttrazione>();
		List<Visita> visite = visitaDAO.getByIdItinerario(idItinerario);
		for(Visita visita : visite) {
			if(sdf.format(new Date()).equals(sdf.format(visita.getDataVisita()))) {
				Attrazione attrazione = visita.getAttrazione();
				MarkerPosizione posizione = attrazione.getPosizione();
				MarkerAttrazione markerAttrazione = new MarkerAttrazione();
				markerAttrazione.setId(visita.getId());
				markerAttrazione.setIdAttrazione(attrazione.getId());
				markerAttrazione.setLatitudine(posizione.getLatitudine());
				markerAttrazione.setLongitudine(posizione.getLongitudine());
				markerAttrazione.setLocalita(posizione.getDescrizione());
				markerAttrazione.setNome(visita.getEtichetta());
				markerAttrazione.setImagePath(attrazione.getFotoPrincipali().get(0).getPath());
				if(visita.getConferma()) {
					markerAttrazione.setTipoMarker("marker_black");
				} else {
					markerAttrazione.setTipoMarker("marker_red");
				}
				Integer ordineGiorno = (int) ((visita.getDataVisita().getTime() - itinerario.getDataInizio().getTime()) / (24 * 60 * 60 * 1000)) + 1;
				markerAttrazione.setOrdineMarker(ordineGiorno+"-"+visita.getOrdineNelGiorno());
				markers.add(markerAttrazione);
			}
		}
		return markers;
	}

}
