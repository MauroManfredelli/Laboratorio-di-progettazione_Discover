package it.unimib.discover.entity.readonly;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STATO_ATTRAZIONE")
public class StatoAttrazione extends TipologicaAbstract implements Serializable {

	private static final long serialVersionUID = -2077283424485545021L;

}
