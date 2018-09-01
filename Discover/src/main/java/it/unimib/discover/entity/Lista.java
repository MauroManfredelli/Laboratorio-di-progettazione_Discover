package it.unimib.discover.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "VW_LISTE_UTENTE")
public class Lista implements Serializable {

	private static final long serialVersionUID = -1538457764114775169L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private String id;
	
	@Column(name = "ID_WISHLIST")
	private Integer idWishlist;
	
	@Column(name = "ID_ITINERARIO")
	private Integer idItinerario;
	
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
	
	@Column(name = "NUMERO_ATTRAZIONI")
	private Integer numeroAttrazioni;
	
	@Transient
	private List<Attrazione> attrazioni;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIdWishlist() {
		return idWishlist;
	}

	public void setIdWishlist(Integer idWishlist) {
		this.idWishlist = idWishlist;
	}

	public Integer getIdItinerario() {
		return idItinerario;
	}

	public void setIdItinerario(Integer idItinerario) {
		this.idItinerario = idItinerario;
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

	public Integer getNumeroAttrazioni() {
		return numeroAttrazioni;
	}

	public void setNumeroAttrazioni(Integer numeroAttrazioni) {
		this.numeroAttrazioni = numeroAttrazioni;
	}

	public List<Attrazione> getAttrazioni() {
		return attrazioni;
	}

	public void setAttrazioni(List<Attrazione> attrazioni) {
		this.attrazioni = attrazioni;
	}

}
