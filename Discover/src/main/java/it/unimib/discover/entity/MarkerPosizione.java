package it.unimib.discover.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MARKER_POSIZIONE")
public class MarkerPosizione implements Serializable {

	private static final long serialVersionUID = -8771024894495236288L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer ID;
	
	@Column(name = "LATITUDINE")
	private String latitudine;
	
	@Column(name = "LONGITUDINE")
	private String longitudine;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}

	public String getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
