package it.unimib.discover.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.Wishlist;

@Repository
public class WishlistDAO extends AbstractEntityDao<Integer, Wishlist> {

	public List<Wishlist> getWishlistAttiveByUser(String id) {
		String sql = "select * " + 
				"from wishlist " +
				"where USER_PROPRIETARIO=:user and ARCHIVIATA = 0 ";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addEntity(Wishlist.class)
				.setParameter("user", id);
		return query.list();
	}

}
