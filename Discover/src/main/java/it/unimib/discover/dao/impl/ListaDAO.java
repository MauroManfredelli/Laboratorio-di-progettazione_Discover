package it.unimib.discover.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.Lista;

@Repository
public class ListaDAO extends AbstractEntityDao<String, Lista> {

	@SuppressWarnings("unchecked")
	public List<Lista> getListeByUser(String id) {
		String sql = "select * " + 
				"from vw_liste_utente " +
				"where USER_PROPRIETARIO=:user and ARCHIVIATA = 0 ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addEntity(Lista.class)
				.setParameter("user", id);
		return query.list();
	}

	public List<Lista> getListeArchiviateByUser(String id) {
		String sql = "select * " + 
				"from vw_liste_utente " +
				"where USER_PROPRIETARIO=:user and ARCHIVIATA = 1 ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addEntity(Lista.class)
				.setParameter("user", id);
		return query.list();
	}

}
