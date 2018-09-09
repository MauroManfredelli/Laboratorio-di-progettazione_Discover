package it.unimib.discover.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.Attrazione;
import it.unimib.discover.entity.MyUserAccount;
import it.unimib.discover.model.ParametriRicerca;

@Repository
public class AttrazioneDAO extends AbstractEntityDao<Integer, Attrazione> {

	private String addParametriRicerca(String sql, ParametriRicerca parametriRicerca, MyUserAccount user) {
		if(StringUtils.isNotBlank(parametriRicerca.getNomeAttrazione())) {
			sql += "and a.nome like '%"+parametriRicerca.getNomeAttrazione()+"%' ";
		} if(StringUtils.isNotBlank(parametriRicerca.getLocalita())) {
			// sql += "and mp.descrizione like '%"+parametriRicerca.getLocalita()+"%' ";
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
		} if(parametriRicerca.getStatoAttrazione() != null && !parametriRicerca.getStatoAttrazione().isEmpty()) {
			String in = "(";
			for(int i=0; i<parametriRicerca.getStatoAttrazione().size(); i++) {
				in += parametriRicerca.getStatoAttrazione().get(i);
				if(i != (parametriRicerca.getStatoAttrazione().size() - 1) ) {
					in += ",";
				}
			}
			in += ")";
			sql += "and a.ID_STATO_ATTRAZIONE in "+in+" ";
		} if(parametriRicerca.getVisitata() != null && !parametriRicerca.getVisitata().isEmpty() && parametriRicerca.getVisitata().size() == 1) {
			if(parametriRicerca.getVisitata().contains(1)) {
				sql += "and (exists (select 'x' from recensioni where VISITA_CONFERMATA=1 AND USER_INSERIMENTO=:user) or exists (select * from visite v join itinerari i on v.ID_ITINERARIO=i.ID where v.conferma=1 and i.USER_PROPRIETARIO=:user and v.id_attrazione = a.id)) ";
			} else {
				sql += "and not exists (select 'x' from recensioni where VISITA_CONFERMATA=1 AND USER_INSERIMENTO=:user) and not exists (select * from visite v join itinerari i on v.ID_ITINERARIO=i.ID where v.conferma=1 and i.USER_PROPRIETARIO=:user and v.id_attrazione = a.id) ";
			}
		}
		return sql;
	}

	@SuppressWarnings("unchecked")
	public List<Attrazione> getAttrazioniByRicerca(ParametriRicerca parametriRicerca, MyUserAccount user) {
		String sql = "select a.ID " + 
				"from attrazioni a " + 
				"	join marker_posizione mp on mp.ID=a.ID_MARKER_POSIZIONE " +
				"where 1=1 ";
		sql = this.addParametriRicerca(sql, parametriRicerca, user);
		SQLQuery query = getSQLQuery(sql)
				.addScalar("ID", IntegerType.INSTANCE);
		if(sql.contains(":user")) {
			query.setParameter("user", user.getId());
		}
		List<Integer> idList = query.list();
		List<Attrazione> attrazioni = new ArrayList<Attrazione>();
		for(Integer idAttrazione : idList) {
			attrazioni.add(super.findByKey(idAttrazione));
		}
		List<Attrazione> res = new ArrayList<Attrazione>();
		for(Attrazione attrazione : attrazioni) {
			if(parametriRicerca.getValutazioneMedia() != null) {
				if(attrazione.getValutazioneMedia() == null || attrazione.getValutazioneMedia() < parametriRicerca.getValutazioneMedia()) {
					continue;
				}
			} if(parametriRicerca.getNumeroRecensioni() != null) {
				if(attrazione.getRecensioni() == null || attrazione.getRecensioni().size() < parametriRicerca.getNumeroRecensioni()) {
					continue;
				}
			} if(parametriRicerca.getNumeroVisite() != null) {
				if(attrazione.getNumeroVisite() == null || attrazione.getNumeroVisite() < parametriRicerca.getNumeroVisite()) {
					continue;
				}
			} if(parametriRicerca.getPercentualeReazioniPositive() != null) {
				double percAttrazione = (attrazione.getReazioniPositive()) / (attrazione.getReazioniPositive() + attrazione.getReazioniNegative());
				if(percAttrazione < parametriRicerca.getPercentualeReazioniPositive()) {
					continue;
				}
			} if(StringUtils.isNotEmpty(parametriRicerca.getLocalita()) && parametriRicerca.getLontananzaMinima() != null && parametriRicerca.getLontananzaMassima() != null) {
				String lat1 = parametriRicerca.getLatCentro();
				String lng1 = parametriRicerca.getLongCentro();
				String lat2 = attrazione.getPosizione().getLatitudine();
				String lng2 = attrazione.getPosizione().getLongitudine();
				float distMetri = this.distFrom(Float.valueOf(lat1), Float.valueOf(lng1), Float.valueOf(lat2), Float.valueOf(lng2));
				float lontananzaMinimia = parametriRicerca.getLontananzaMinima()*1000;
				float lontananzaMassima = parametriRicerca.getLontananzaMassima()*1000;
				if(distMetri < lontananzaMinimia || distMetri > lontananzaMassima) {
					continue;
				}
			}
			res.add(attrazione);
		}
		return res;
	}
	
	// Calcola la distanza in metri sapendo latitudini e longitudini
	private float distFrom(float lat1, float lng1, float lat2, float lng2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    return dist;
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

	public List<Integer> getAttrazioniByItinerario(Integer idItinerario) {
		String sql = "select a.ID " + 
				"from attrazioni a  " + 
				"	join visite v on a.ID=v.ID_ATTRAZIONE " + 
				"where v.ID_ITINERARIO=:idItinerario ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addScalar("ID", IntegerType.INSTANCE)
				.setParameter("idItinerario", idItinerario);
		return query.list();
	}

	public List<Integer> getAttrazioniByWishlist(Integer idWishlist) {
		String sql = "select a.ID " + 
				"from attrazioni a  " + 
				"	join rel_wishlist_attrazione rwa on a.ID=rwa.ID_ATTRAZIONE " + 
				"where rwa.ID_WISHLIST=:idWishlist ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addScalar("ID", IntegerType.INSTANCE)
				.setParameter("idWishlist", idWishlist);
		return query.list();
	}
	
}
