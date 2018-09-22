package it.unimib.discover.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.Itinerario;

@Repository
public class ItinerarioDAO extends AbstractEntityDao<Integer, Itinerario> {

	@Transactional
	public boolean existsItinerarioSameNameForUser(String nome, String idUtente, Integer idItinerario) {
		String sql = "select ID " + 
				"from itinerari " + 
				"where NOME=:nome and USER_PROPRIETARIO=:idUtente and ID_ITINERARIO=:idItinerario";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addScalar("ID", IntegerType.INSTANCE)
				.setParameter("nome", nome)
				.setParameter("idUtente", idUtente)
				.setParameter("idItinerario", idItinerario == null ? -1 : idItinerario);
		return !query.list().isEmpty();
	}

}
