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
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import it.unimib.discover.entity.readonly.StatoAttrazione;
import it.unimib.discover.entity.readonly.TipoAttrazione;
import it.unimib.discover.enumerator.TipoAccesso;

@Entity
@Table(name = "ATTRAZIONI")
public class Attrazione implements Serializable {

	private static final long serialVersionUID = 5810431696077329076L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO_ATTRAZIONE")
	private TipoAttrazione tipoAttrazione;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ACCESSO")
	private TipoAccesso accesso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_MARKER_POSIZIONE")
	private MarkerPosizione posizione;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_STATO_ATTRAZIONE")
	private StatoAttrazione stato;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_INSERIMENTO")
	private MyUserAccount userInserimento;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INSERIMENTO")
	private Date dataInserimento;
	
	@Formula("(SELECT count(*) FROM recensioni r WHERE r.REAZIONE='MI_PIACE' and r.ID_ATTRAZIONE=ID)")
	private Integer reazioniPositive;
	
	@Formula("(SELECT count(*) FROM recensioni r WHERE r.REAZIONE='NON_MI_PIACE' and r.ID_ATTRAZIONE=ID)")
	private Integer reazioniNegative;
	
	@Formula("(SELECT avg(r.valutazione) FROM recensioni r WHERE r.VALUTAZIONE is not null and r.ID_ATTRAZIONE=ID)")
	private Double valutazioneMedia;
	
	@Formula("(SELECT count(*) FROM recensioni r WHERE r.VISITA_CONFERMATA=1 and r.ID_ATTRAZIONE=ID)")
	private Integer numeroVisite;
	
	@Fetch(FetchMode.SELECT)
	@Where(clause = "principale = 1")
	@OneToMany(mappedBy = "attrazione", fetch = FetchType.EAGER)
	private List<Foto> fotoPrincipali;
	
	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "attrazione", fetch = FetchType.EAGER)
	private List<Recensione> recensioni;

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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public TipoAttrazione getTipoAttrazione() {
		return tipoAttrazione;
	}

	public void setTipoAttrazione(TipoAttrazione tipoAttrazione) {
		this.tipoAttrazione = tipoAttrazione;
	}

	public TipoAccesso getAccesso() {
		return accesso;
	}

	public void setAccesso(TipoAccesso accesso) {
		this.accesso = accesso;
	}

	public MarkerPosizione getPosizione() {
		return posizione;
	}

	public void setPosizione(MarkerPosizione posizione) {
		this.posizione = posizione;
	}

	public StatoAttrazione getStato() {
		return stato;
	}

	public void setStato(StatoAttrazione stato) {
		this.stato = stato;
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

	public Integer getReazioniPositive() {
		return reazioniPositive;
	}

	public void setReazioniPositive(Integer reazioniPositive) {
		this.reazioniPositive = reazioniPositive;
	}

	public Integer getReazioniNegative() {
		return reazioniNegative;
	}

	public void setReazioniNegative(Integer reazioniNegative) {
		this.reazioniNegative = reazioniNegative;
	}

	public Double getValutazioneMedia() {
		return valutazioneMedia;
	}

	public void setValutazioneMedia(Double valutazioneMedia) {
		this.valutazioneMedia = valutazioneMedia;
	}

	public Integer getNumeroVisite() {
		return numeroVisite;
	}

	public void setNumeroVisite(Integer numeroVisite) {
		this.numeroVisite = numeroVisite;
	}

	public List<Foto> getFotoPrincipali() {
		return fotoPrincipali;
	}

	public void setFotoPrincipali(List<Foto> fotoPrincipali) {
		this.fotoPrincipali = fotoPrincipali;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

}
