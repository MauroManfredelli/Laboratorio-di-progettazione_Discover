package it.unimib.discover.dao;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.unimib.discover.entity.readonly.TipologicaAbstract;

public abstract class AbstractDao {
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    protected Logger logger;
    
    public AbstractDao(){
    	this.logger = Logger.getLogger(this.getClass());
    }
 
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    protected SQLQuery getSQLQuery(String query){
    	logger.info(String.format("createSQLQuery: %s", query));
        return getSession().createSQLQuery(query);
    }
    
    protected Query getQuery(String query){
    	logger.info(String.format("getQuery: %s", query));
        return getSession().createQuery(query);
    }
    
    // METODI PER LA GESTIONE DELLE CLASSI TIPOLOGICHE
 	@SuppressWarnings("unchecked")
 	public <E extends TipologicaAbstract> E getTipoById(Class<E> clazz, Integer id){
 		return (E) getSession().createQuery("FROM " + clazz.getSimpleName() +" WHERE ID = "+id).list().get(0);
 	}
 	
 	@SuppressWarnings("unchecked")
 	public <E extends TipologicaAbstract> E getTipoById(Class<E> clazz, String id){
 		return (E) getSession().createQuery("FROM " + clazz.getSimpleName() +" WHERE ID = "+id).list().get(0);
 	}
 	
 	@SuppressWarnings("unchecked")
 	public <E extends TipologicaAbstract> E getTipoById(Class<E> clazz, Long id){
 		return (E) getSession().createQuery("FROM " + clazz.getSimpleName() +" WHERE ID = "+id).list().get(0);
 	}
 	
 	@SuppressWarnings("unchecked")
 	public <E extends TipologicaAbstract> List<E> tipoByClass(Class<E> clazz, boolean hasAttrOrdine){
 	    if(hasAttrOrdine) {
 			return getSession().createQuery("FROM " + clazz.getSimpleName() + " ORDER BY ORDINE").list();
 		} else {
 			List<E> list = getSession().createQuery("FROM " + clazz.getSimpleName()).list();
 			// Senza un parametro d'ordine, ordino i tipi in base all'id
 			Collections.sort(list, new Comparator<E>(){
 			       public int compare(E o1, E o2) {
 			    	   return o1.getId().compareTo(o2.getId());
 			       }
 			});
 			return list;
 		}
 	}
 	
 	public <E extends TipologicaAbstract> Map<Long,String> getAllTipoByClass(Class<E> clazz){
 		Map <Long, String> res = new LinkedHashMap<>();
 		boolean hasAttrOrdine = false;
 		Method[] methodList = clazz.getMethods();
 		for (Method method : methodList) {
 	        if (method.getName().equalsIgnoreCase("getOrdine")) {
 	        	hasAttrOrdine = true;
 	        	break;
 	        }
 	    }
 		List<E> list = this.tipoByClass(clazz, hasAttrOrdine);
 		for(E e : list){
 			res.put(e.getId(),e.getDescrizione());
 		}
 		return res;
 	}
 
}