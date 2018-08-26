package it.unimib.discover.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import it.unimib.discover.enumerator.Reazione;

@Entity
@Table(name = "RECENSIONI")
public class Recensione implements Serializable {

	private static final long serialVersionUID = 1240108810543779683L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "TITOLO")
	private String titolo;
	
	@Column(name = "TESTO")
	private String testo;
	
	@Column(name = "VALUTAZIONE")
	private Double valutazione;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "REAZIONE")
	private Reazione reazione;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "VISITA_CONFERMATA")
    private Boolean visitaConfermata;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ATTRAZIONE")
	private Attrazione attrazione;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_INSERIMENTO")
	private MyUserAccount userInserimento;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INSERIMENTO")
	private Date dataInserimento;
	
	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "recensione", fetch = FetchType.EAGER)
	private List<Foto> foto;
	
	public Recensione() {}
	
	public Recensione(String titolo, String testo, Double valutazione, 
					  Reazione reazione, Boolean visitaConfermata, MyUserAccount userInserimento, 
					  Date dataInserimento, List<Foto> foto) {
		this.titolo = titolo;
		this.testo = testo;
		this.valutazione = valutazione;
		this.reazione = reazione;
		this.visitaConfermata = visitaConfermata;
		this.userInserimento = userInserimento;
		this.dataInserimento = dataInserimento;
		this.foto = foto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Double getValutazione() {
		return valutazione;
	}

	public void setValutazione(Double valutazione) {
		this.valutazione = valutazione;
	}

	public Reazione getReazione() {
		return reazione;
	}

	public void setReazione(Reazione reazione) {
		this.reazione = reazione;
	}

	public Boolean getVisitaConfermata() {
		return visitaConfermata;
	}

	public void setVisitaConfermata(Boolean visitaConfermata) {
		this.visitaConfermata = visitaConfermata;
	}

	public Attrazione getAttrazione() {
		return attrazione;
	}

	public void setAttrazione(Attrazione attrazione) {
		this.attrazione = attrazione;
	}

	public MyUserAccount getUserInserimento() {
		return userInserimento;
	}

	public void setUserInserimento(MyUserAccount userInserimento) {
		this.userInserimento = userInserimento;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public List<Foto> getFoto() {
		return foto;
	}

	public void setFoto(List<Foto> foto) {
		this.foto = foto;
	}
}
