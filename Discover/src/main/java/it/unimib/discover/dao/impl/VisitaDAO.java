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
				"from visite " +
				"where ID_ITINERARIO=:id ";
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

	public void deleteByItinerarioAttrazione(Integer idItinerario, Integer idAttrazione) {
		String sql = "delete " + 
				"from visite " +
				"where ID_ITINERARIO=:idItinerario and ID_ATTRAZIONE=:idAttrazione ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.setParameter("idItinerario", idItinerario)
				.setParameter("idAttrazione", idAttrazione);
		query.executeUpdate();
	}

}
