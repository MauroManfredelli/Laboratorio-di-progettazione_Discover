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

import it.unimib.discover.enumerator.TipoNotifica;

@Entity
@Table(name = "NOTIFICHE")
public class Notifica implements Serializable {

	private static final long serialVersionUID = -4830072092554286899L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	@Column(name = "TIPO")
	private TipoNotifica tipo;
		
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_NOTIFICA")
	private Date dataNotifica;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TO")
	private MyUserAccount userTo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public TipoNotifica getTipo() {
		return tipo;
	}

	public void setTipo(TipoNotifica tipo) {
		this.tipo = tipo;
	}

	public Date getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public MyUserAccount getUserTo() {
		return userTo;
	}

	public void setUserTo(MyUserAccount userTo) {
		this.userTo = userTo;
	}

}
