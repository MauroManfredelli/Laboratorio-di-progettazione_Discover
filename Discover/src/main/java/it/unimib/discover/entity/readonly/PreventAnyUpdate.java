package it.unimib.discover.entity.readonly;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class PreventAnyUpdate {
	
	@PrePersist
    void onPrePersist(Object o) {
        throw new RuntimeException("Si sta provando a eseguire il persist di entit� readonly: "+o.getClass());
    }

    @PreUpdate
    void onPreUpdate(Object o) {
    	throw new RuntimeException("Si sta provando a eseguire l'update di entit� readonly: "+o.getClass());
    }

    @PreRemove
    void onPreRemove(Object o) {
    	throw new RuntimeException("Si sta provando a eseguire il remove di entit� readonly: "+o.getClass());
    }

}
