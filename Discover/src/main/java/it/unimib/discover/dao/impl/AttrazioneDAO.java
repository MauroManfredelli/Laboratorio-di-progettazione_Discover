package it.unimib.discover.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.Attrazione;
import it.unimib.discover.model.MarkerAttrazione;
import it.unimib.discover.model.ParametriRicerca;

@Repository
public class AttrazioneDAO extends AbstractEntityDao<Integer, Attrazione> {

	private String addParametriRicerca(String sql, ParametriRicerca parametriRicerca) {
		if(StringUtils.isNotBlank(parametriRicerca.getNomeAttrazione())) {
			sql += "and a.nome like '%"+parametriRicerca.getNomeAttrazione()+"%' ";
		} if(StringUtils.isNotBlank(parametriRicerca.getLocalita())) {
			sql += "and mp.descrizione like '%"+parametriRicerca.getLocalita()+"%' ";
		} if(parametriRicerca.getTipoAttrazione() != null && !parametriRicerca.getTipoAttrazione().isEmpty()) {
			String in = "(";
			for(int i=0; i<parametriRicerca.getTipoAttrazione().size(); i++) {
				in += parametriRicerca.getTipoAttrazione().get(i);
				if(i != (parametriRicerca.getTipoAttrazione().size() - 1) ) {
					in += ",";
				}
			}
			in += ")";
			sql += "and a.ID_TIPO_ATTRAZIONE in "+in+" ";
		}  if(parametriRicerca.getStatoAttrazione() != null && !parametriRicerca.getStatoAttrazione().isEmpty()) {
			String in = "(";
			for(int i=0; i<parametriRicerca.getStatoAttrazione().size(); i++) {
				in += parametriRicerca.getStatoAttrazione().get(i);
				if(i != (parametriRicerca.getStatoAttrazione().size() - 1) ) {
					in += ",";
				}
			}
			in += ")";
			sql += "and a.ID_STATO_ATTRAZIONE in "+in+" ";
		}
		return sql;
	}

	@SuppressWarnings("unchecked")
	public List<Attrazione> getAttrazioniByRicerca(ParametriRicerca parametriRicerca) {
		String sql = "select a.ID " + 
				"from attrazioni a " + 
				"	join marker_posizione mp on mp.ID=a.ID_MARKER_POSIZIONE " +
				"where 1=1 ";
		sql = this.addParametriRicerca(sql, parametriRicerca);
		SQLQuery query = getSQLQuery(sql)
				.addScalar("ID", IntegerType.INSTANCE);
		List<Integer> idList = query.list();
		List<Attrazione> res = new ArrayList<Attrazione>();
		for(Integer idAttrazione : idList) {
			res.add(super.findByKey(idAttrazione));
		}
		return res;
	}

	public List<Attrazione> getPrimeAttrazioniOfWishlist(Integer idWishlist) {
		String sql = "select a.ID " + 
				"from attrazioni a  " + 
				"	join rel_wishlist_attrazione rwa on a.ID=rwa.ID_ATTRAZIONE " + 
				"where rwa.ID_WISHLIST=:idWishlist " + 
				"limit 4";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addScalar("ID", IntegerType.INSTANCE)
				.setParameter("idWishlist", idWishlist);
		List<Integer> idList = query.list();
		List<Attrazione> res = new ArrayList<Attrazione>();
		for(Integer idAttrazione : idList) {
			res.add(super.findByKey(idAttrazione));
		}
		return res;
	}

	public List<Attrazione> getPrimeAttrazioniOfItinerario(Integer idItinerario) {
		String sql = "select a.ID " + 
				"from attrazioni a  " + 
				"	join visite v on a.ID=v.ID_ATTRAZIONE " + 
				"where v.ID_ITINERARIO=:idItinerario " + 
				"limit 4";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addScalar("ID", IntegerType.INSTANCE)
				.setParameter("idItinerario", idItinerario);
		List<Integer> idList = query.list();
		List<Attrazione> res = new ArrayList<Attrazione>();
		for(Integer idAttrazione : idList) {
			res.add(super.findByKey(idAttrazione));
		}
		return res;
	}

	public Attrazione findByPosizione(Integer id) {
		String sql = "select a.ID " + 
				"from attrazioni a  " + 
				"where a.ID_MARKER_POSIZIONE = :id ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addScalar("ID", IntegerType.INSTANCE)
				.setParameter("id", id);
		Integer idAttrazione = (Integer) query.uniqueResult();
		return this.findByKey(idAttrazione);
	}
	
}
