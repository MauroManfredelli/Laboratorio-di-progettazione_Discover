package it.unimib.discover.entity.readonly;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TIPO_ATTRAZIONE")
public class TipoAttrazione extends TipologicaAbstract implements Serializable {

	private static final long serialVersionUID = -7232227467669583520L;

}
