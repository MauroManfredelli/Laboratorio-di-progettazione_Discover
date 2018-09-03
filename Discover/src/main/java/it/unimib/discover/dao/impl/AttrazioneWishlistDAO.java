package it.unimib.discover.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.AttrazioneWishlist;

@Repository
public class AttrazioneWishlistDAO extends AbstractEntityDao<Integer, AttrazioneWishlist>{

	public List<AttrazioneWishlist> getByIdWishList(Integer id) {
		String sql = "select * " + 
				"from rel_wishlist_attrazione " +
				"where ID_WISHLIST=:id ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addEntity(AttrazioneWishlist.class)
				.setParameter("id", id);
		return query.list();
	}

	public void delete(List<AttrazioneWishlist> listAw) {
		for(AttrazioneWishlist e : listAw) {
			super.delete(e);
		}
	}

}
