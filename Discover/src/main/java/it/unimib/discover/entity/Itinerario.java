package it.unimib.discover.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ITINERARI")
public class Itinerario implements Serializable {

	private static final long serialVersionUID = -2140660291323248206L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NOME")
	private String nome;
		
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CREAZIONE")
	private Date dataCreazione;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "ARCHIVIATA")
    private Boolean archiviata;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_PROPRIETARIO")
	private MyUserAccount userProprietario;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INIZIO")
	private Date dataInizio;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_FINE")
	private Date dataFine;
	
	@Column(name = "NUMERO_GIORNI")
	private Integer numeroGiorni;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "CONFERMATO")
    private Boolean confermato;
	
	@OneToMany(mappedBy = "itinerario", fetch = FetchType.EAGER)
	private List<Visita> visite;
	
	@Transient
	private Map<String, List<Visita>> mapAttrazioni;
	
	public Itinerario() {
		this.confermato = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Boolean getArchiviata() {
		return archiviata;
	}

	public void setArchiviata(Boolean archiviata) {
		this.archiviata = archiviata;
	}

	public MyUserAccount getUserProprietario() {
		return userProprietario;
	}

	public void setUserProprietario(MyUserAccount userProprietario) {
		this.userProprietario = userProprietario;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Integer getNumeroGiorni() {
		return numeroGiorni;
	}

	public void setNumeroGiorni(Integer numeroGiorni) {
		this.numeroGiorni = numeroGiorni;
	}

	public List<Visita> getVisite() {
		return visite;
	}

	public void setVisite(List<Visita> visite) {
		this.visite = visite;
	}

	public Map<String, List<Visita>> getMapAttrazioni() {
		return mapAttrazioni;
	}

	public void setMapAttrazioni(Map<String, List<Visita>> mapAttrazioni) {
		this.mapAttrazioni = mapAttrazioni;
	}

	public Boolean getConfermato() {
		return confermato;
	}

	public void setConfermato(Boolean confermato) {
		this.confermato = confermato;
	}

	public String getOrdineVisita(Date dataVisita) {
		Integer ordine = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(Visita visita : this.visite) {
			if(visita.getDataVisita() != null && sdf.format(visita.getDataVisita()).equals(sdf.format(dataVisita))) {
				ordine ++;
			}
		}
		return ordine + "";
	}

	public String getLastOrdineConfermato(Date dataVisita) {
		String ordine = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(Visita visita : this.visite) {
			if(visita.getConferma() && visita.getDataVisita() != null && sdf.format(visita.getDataVisita()).equals(sdf.format(dataVisita))) {
				if(visita.getOrdineNelGiorno() != null && visita.getOrdineNelGiorno().compareTo(ordine) > 0) {
					ordine = visita.getOrdineNelGiorno();
				}
			}
		}
		return ordine;
	}

	public String getOrdineVisita(Integer giorno) {
		Integer ordine = 1;
		for(Visita visita : this.visite) {
			if(visita.getGiorno() != null && visita.getGiorno() == giorno) {
				ordine ++;
			}
		}
		return ordine + "";
	}

	public String getOrdineVisitaNonProgramm() {
		Integer ordine = 1;
		for(Visita visita : this.visite) {
			if(visita.getGiorno() == null && visita.getDataVisita() == null) {
				ordine ++;
			}
		}
		return ordine + "";
	}

	public Visita getVisitaByOrdine(Integer ordine) {
		for(Visita visita : this.visite) {
			if(visita.getOrdineNelGiorno().equals(ordine+"")) {
				return visita;
			}
		}
		return null;
	}

	public String getNumeroVisiteGiorno(Integer giorno, Date dataVisita) {
		int numeroVisite = 1;
		if(dataVisita != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for(Visita visita : this.visite) {
				if(visita.getDataVisita() != null && sdf.format(visita.getDataVisita()).equals(sdf.format(dataVisita))) {
					numeroVisite ++;
				}
			}
		} else if(giorno != null) {
			for(Visita visita : this.visite) {
				if(visita.getGiorno() != null && visita.getGiorno() == giorno) {
					numeroVisite ++;
				}
			}
		} else {
			for(Visita visita : this.visite) {
				if(visita.getGiorno() == null && visita.getDataVisita() == null) {
					numeroVisite ++;
				}
			}
		}
		return numeroVisite+"";
	}

}
