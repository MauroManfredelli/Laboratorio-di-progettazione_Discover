package it.unimib.discover.model;

import java.io.Serializable;
import java.util.List;

public class ItinerarioModel implements Serializable {

	private static final long serialVersionUID = 8318033398679740760L;
	private Integer id;
	private String nome;
	private Integer divisione;
	private String dataInizio;
	private String dataFine;
	private Integer numeroGiorni;
	private List<Integer> idWishlist;
	
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
	public Integer getDivisione() {
		return divisione;
	}
	public void setDivisione(Integer divisione) {
		this.divisione = divisione;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public Integer getNumeroGiorni() {
		return numeroGiorni;
	}
	public void setNumeroGiorni(Integer numeroGiorni) {
		this.numeroGiorni = numeroGiorni;
	}
	public List<Integer> getIdWishlist() {
		return idWishlist;
	}
	public void setIdWishlist(List<Integer> idWishlist) {
		this.idWishlist = idWishlist;
	}

}
