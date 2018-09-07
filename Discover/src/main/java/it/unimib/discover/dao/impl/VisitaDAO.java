package it.unimib.discover.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.Visita;

@Repository
public class VisitaDAO extends AbstractEntityDao<Integer, Visita> {

	public List<Visita> getByIdItinerario(Integer id) {
		String sql = "select * " + 
				"from itinerari " +
				"where ID=:id ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addEntity(Visita.class)
				.setParameter("id", id);
		return query.list();
	}

	public void delete(List<Visita> visite) {
		for(Visita e : visite) {
			super.delete(e);
		}
	}

}
