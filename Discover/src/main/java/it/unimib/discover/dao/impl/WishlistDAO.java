package it.unimib.discover.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.unimib.discover.dao.AbstractEntityDao;
import it.unimib.discover.entity.Wishlist;

@Repository
public class WishlistDAO extends AbstractEntityDao<Integer, Wishlist> {

	public List<Wishlist> getWishlistAttiveByUser(String id) {
		String sql = "select w.* " + 
				"from wishlist w " +
				"where w.USER_PROPRIETARIO=:user and w.ARCHIVIATA = 0 and exists (select 'x' from rel_wishlist_attrazione wa where wa.ID_WISHLIST=w.ID)";
		SQLQuery query = (SQLQuery) getSQLQuery(sql)
				.addEntity(Wishlist.class)
				.setParameter("user", id);
		return query.list();
	}

}
