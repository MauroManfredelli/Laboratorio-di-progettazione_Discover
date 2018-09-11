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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "WISHLIST")
public class Wishlist implements Serializable {

	private static final long serialVersionUID = -5673242183411769372L;
	
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
	
	@OneToMany(mappedBy = "wishlist", fetch = FetchType.EAGER)
	private List<AttrazioneWishlist> attrazioniWishlist;

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

	public List<AttrazioneWishlist> getAttrazioniWishlist() {
		return attrazioniWishlist;
	}

	public void setAttrazioniWishlist(List<AttrazioneWishlist> attrazioniWishlist) {
		this.attrazioniWishlist = attrazioniWishlist;
	}
	
}
