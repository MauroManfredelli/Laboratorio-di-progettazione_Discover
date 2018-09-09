package it.unimib.discover.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.Lista;

@Repository
public class ListaDAO extends AbstractEntityDao<String, Lista> {

	@SuppressWarnings("unchecked")
	public List<Lista> getListeByUser(String id, String ordineListe) {
		String sql = "select * " + 
				"from vw_liste_utente " +
				"where USER_PROPRIETARIO=:user and ARCHIVIATA = 0 ";
		if(ordineListe.equals("dataCreazione")) {
			sql += "order by DATA_CREAZIONE";
		} else if(ordineListe.equals("nome")) {
			sql += "order by nome";
		} else if(ordineListe.equals("numeroAttrazioni")) {
			sql += "order by NUMERO_ATTRAZIONI";
		}
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addEntity(Lista.class)
				.setParameter("user", id);
		return query.list();
	}

	public List<Lista> getListeArchiviateByUser(String id, String ordineListe) {
		String sql = "select * " + 
				"from vw_liste_utente " +
				"where USER_PROPRIETARIO=:user and ARCHIVIATA = 1 ";
		if(ordineListe.equals("dataCreazione")) {
			sql += "order by DATA_CREAZIONE";
		} else if(ordineListe.equals("nome")) {
			sql += "order by nome";
		} else if(ordineListe.equals("numeroAttrazioni")) {
			sql += "order by NUMERO_ATTRAZIONI";
		}
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addEntity(Lista.class)
				.setParameter("user", id);
		return query.list();
	}

	public boolean containsAttrazione(Integer idAttrazione, String idLista) {
		String sql = "select l.ID " + 
				"from vw_liste_utente l  " + 
				"	left join wishlist w on l.ID_WISHLIST=w.ID " + 
				"	left join rel_wishlist_attrazione rwa on w.ID=rwa.ID_WISHLIST " + 
				"	left join itinerari i on i.ID=l.ID_ITINERARIO " + 
				"	left join visite v on i.ID=v.ID_ITINERARIO " + 
				"where l.ID=:idLista and (rwa.ID_ATTRAZIONE=:idAttrazione or v.ID_ATTRAZIONE=:idAttrazione)";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addScalar("ID", StringType.INSTANCE)
				.setParameter("idAttrazione", idAttrazione)
				.setParameter("idLista", idLista);
		return !query.list().isEmpty();
	}

}
