package it.unimib.discover.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;

public abstract class AbstractEntityDao<PK extends Serializable, T> extends AbstractDao {
    
    private final Class<T> persistentClass;
     
    @SuppressWarnings("unchecked")
    public AbstractEntityDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    
    @SuppressWarnings("unchecked")
    public T findByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }
    
    @SuppressWarnings("unchecked")
	public List<T> findAll() {
        return createEntityCriteria().list();
    }

    public void persist(T entity) {
        getSession().saveOrUpdate(entity);
    }
 
    public void delete(T entity) {
        getSession().delete(entity);
    }
     
    protected Criteria createEntityCriteria(){
    	logger.info(String.format("createEntityCriteria for class: %s", this.persistentClass.getCanonicalName()));
        return getSession().createCriteria(persistentClass);
    }
}