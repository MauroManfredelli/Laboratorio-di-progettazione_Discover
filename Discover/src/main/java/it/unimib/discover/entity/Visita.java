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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "VISITE")
public class Visita implements Serializable, Comparable<Visita> {

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
	
	@Column(name = "ETICHETTA")
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
	
	@Transient
	private String ordine;

	public Visita() {}

	public Visita(Attrazione attrazione, Itinerario itinerario) {
		this.attrazione = attrazione;
		this.itinerario = itinerario;
	}

	public Visita(Visita visita) {
		this.giorno = visita.giorno;
		this.ora = visita.ora;
		this.dataVisita = visita.dataVisita;
		this.etichetta = visita.etichetta+"_2";
		this.nota = visita.nota;
		this.notaPrec = "";
		this.itinerario = visita.itinerario;
		this.attrazione = visita.attrazione;
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

	public String getOrdine() {
		return ordine;
	}

	public void setOrdine(String ordine) {
		this.ordine = ordine;
	}

	@Override
	public int compareTo(Visita o) {
		if(ordine != null && o.ordine != null) {
			return ordine.compareTo(o.ordine);
		} else if(this.dataVisita == null && this.giorno == null) {
			return -1;
		} else if(o.dataVisita == null && o.giorno == null) {
			return 1;
		} else {
			return ora.compareTo(o.ora);
		}
	}

}
