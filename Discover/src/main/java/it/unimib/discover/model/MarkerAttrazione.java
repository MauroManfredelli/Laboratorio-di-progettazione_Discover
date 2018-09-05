package it.unimib.discover.model;

import java.io.Serializable;

public class MarkerAttrazione implements Serializable {
	
	private static final long serialVersionUID = 3892687835127784582L;
	private Integer id;
	private Integer idAttrazione;
	private String nome;
	private String localita;
	private String latitudine;
	private String longitudine;
	
	private String tipoMarker;
	private String ordineMarker;
	private String imagePath;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdAttrazione() {
		return idAttrazione;
	}
	public void setIdAttrazione(Integer idAttrazione) {
		this.idAttrazione = idAttrazione;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	public String getTipoMarker() {
		return tipoMarker;
	}
	public void setTipoMarker(String tipoMarker) {
		this.tipoMarker = tipoMarker;
	}
	public String getOrdineMarker() {
		return ordineMarker;
	}
	public void setOrdineMarker(String ordineMarker) {
		this.ordineMarker = ordineMarker;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
