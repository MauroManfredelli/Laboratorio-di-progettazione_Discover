package it.unimib.discover.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "VISITE")
public class Visita implements Serializable {

	private static final long serialVersionUID = 5410778427663786818L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "GIORNO")
	private Integer giorno;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_VISITA")
	private Date dataVisita;
	
	@Column(name = "ORA")
	private String ora;
	
	@Column(name = "ETICHIETTA")
	private String etichetta;
	
	@Column(name = "NOTA_PREC")
	private String notaPrec;
	
	@Column(name = "NOTA")
	private String nota;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ITINERARIO")
	private Itinerario itinerario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ATTRAZIONE")
	private Attrazione attrazione;

	public Visita() {}

	public Visita(Attrazione attrazione, Itinerario itinerario) {
		this.attrazione = attrazione;
		this.itinerario = itinerario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGiorno() {
		return giorno;
	}

	public void setGiorno(Integer giorno) {
		this.giorno = giorno;
	}

	public Date getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(Date dataVisita) {
		this.dataVisita = dataVisita;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public String getEtichetta() {
		return etichetta;
	}

	public void setEtichetta(String etichetta) {
		this.etichetta = etichetta;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getNotaPrec() {
		return notaPrec;
	}

	public void setNotaPrec(String notaPrec) {
		this.notaPrec = notaPrec;
	}

	public Itinerario getItinerario() {
		return itinerario;
	}

	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}

	public Attrazione getAttrazione() {
		return attrazione;
	}

	public void setAttrazione(Attrazione attrazione) {
		this.attrazione = attrazione;
	}

}
