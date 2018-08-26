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
@Table(name = "MESSAGGI")
public class Messaggio implements Serializable {

	private static final long serialVersionUID = -2767346087775186891L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "TESTO")
	private String testo;
		
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INVIO")
	private Date dataInvio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_FROM")
	private MyUserAccount userFrom;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TO")
	private MyUserAccount userTo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public MyUserAccount getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(MyUserAccount userFrom) {
		this.userFrom = userFrom;
	}

	public MyUserAccount getUserTo() {
		return userTo;
	}

	public void setUserTo(MyUserAccount userTo) {
		this.userTo = userTo;
	}
	
//	@Column(name = "cost_second")
//	private Double costSecond;
//	
//	@ManyToOne
//	@JoinColumn(name = "path_id")
//	private Path path;
//	
//	@ManyToOne
//	@JoinColumn(name = "plane_id")
//	private Plane plane;
//	
//	@Fetch(FetchMode.SELECT)
//	@Where(clause = "obsolete=0")
//	@OneToOne(fetch = FetchType.EAGER ,mappedBy = "fly")
//	private Promotion promotion;
//	
//	@Formula("(SELECT p.discount_percentage FROM promotions p WHERE depart_date BETWEEN p.start_date AND p.end_date AND p.obsolete=0 AND p.air_miles_scores IS NULL LIMIT 1 )")
//	private Double discountTimeFlyPromotion;
//	
//	@Formula("(SELECT p.discount_percentage FROM promotions p WHERE p.fly_id = fly_id AND p.obsolete=0 AND  p.air_miles_scores IS NULL LIMIT 1 )")
//	private Double discountFlyPromotion;
//	
//	@Fetch(FetchMode.SELECT)
//	@Where(clause = "status = 'VERIFICATO'")
//	@OneToMany(mappedBy = "fly", fetch = FetchType.EAGER)
//	private List<Ticket> tickets;
	
}
