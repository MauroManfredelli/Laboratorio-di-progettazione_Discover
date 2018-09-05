package it.unimib.discover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unimib.discover.dao.impl.AttrazioneDAO;
import it.unimib.discover.entity.Attrazione;
import it.unimib.discover.entity.readonly.StatoAttrazione;
import it.unimib.discover.entity.readonly.TipoAttrazione;
import it.unimib.discover.model.ParametriRicerca;

@Service
public class AttrazioniService {
	
	@Autowired
	private AttrazioneDAO attrazioneDAO;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Attrazione getAttrazioneById(Integer id) {
		Attrazione attrazione = attrazioneDAO.findByKey(id);
		return attrazione;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Attrazione> getAttrazioniBacheca() {
		return attrazioneDAO.findAll();
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<TipoAttrazione> getAllTipologieAttrazioni() {
		return attrazioneDAO.getAllTipoByClassList(TipoAttrazione.class);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<StatoAttrazione> getAllStatiAttrazione() {
		return attrazioneDAO.getAllTipoByClassList(StatoAttrazione.class);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<Attrazione> getAttrazioniByRicerca(ParametriRicerca parametriRicerca) {
		return attrazioneDAO.getAttrazioniByRicerca(parametriRicerca);
	}
	
}
