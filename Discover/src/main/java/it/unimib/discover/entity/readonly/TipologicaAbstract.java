package it.unimib.discover.entity.readonly;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class TipologicaAbstract extends PreventAnyUpdate {
	
	@Id
	@Column(name="ID")
	private Long id;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
