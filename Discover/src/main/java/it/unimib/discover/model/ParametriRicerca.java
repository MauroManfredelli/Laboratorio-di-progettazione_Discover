package it.unimib.discover.model;

import java.io.Serializable;
import java.util.List;

public class ParametriRicerca implements Serializable {
	
	private static final long serialVersionUID = 8281204710978785902L;
	
	private String nomeAttrazione;
	private String localita;
	private Integer lontananzaMinima;
	private Integer lontananzaMassima;
	private List<Integer> tipoAttrazione;
	private List<Integer> statoAttrazione;
	private Double valutazioneMedia;
	private Integer numeroVisite;
	private Integer numeroRecensioni;
	private Double percentualeReazioniPositive;
	private Boolean visitata;
	
	public String getNomeAttrazione() {
		return nomeAttrazione;
	}
	public void setNomeAttrazione(String nomeAttrazione) {
		this.nomeAttrazione = nomeAttrazione;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String località) {
		this.localita = località;
	}
	public Integer getLontananzaMinima() {
		return lontananzaMinima;
	}
	public void setLontananzaMinima(Integer lontananzaMinima) {
		this.lontananzaMinima = lontananzaMinima;
	}
	public Integer getLontananzaMassima() {
		return lontananzaMassima;
	}
	public void setLontananzaMassima(Integer lontananzaMassima) {
		this.lontananzaMassima = lontananzaMassima;
	}
	public List<Integer> getTipoAttrazione() {
		return tipoAttrazione;
	}
	public void setTipoAttrazione(List<Integer> tipoAttrazione) {
		this.tipoAttrazione = tipoAttrazione;
	}
	public List<Integer> getStatoAttrazione() {
		return statoAttrazione;
	}
	public void setStatoAttrazione(List<Integer> statoAttrazione) {
		this.statoAttrazione = statoAttrazione;
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
	public Integer getNumeroRecensioni() {
		return numeroRecensioni;
	}
	public void setNumeroRecensioni(Integer numeroRecensioni) {
		this.numeroRecensioni = numeroRecensioni;
	}
	public Double getPercentualeReazioniPositive() {
		return percentualeReazioniPositive;
	}
	public void setPercentualeReazioniPositive(Double percentualeReazioniPositive) {
		this.percentualeReazioniPositive = percentualeReazioniPositive;
	}
	public Boolean getVisitata() {
		return visitata;
	}
	public void setVisitata(Boolean visitata) {
		this.visitata = visitata;
	}

}
