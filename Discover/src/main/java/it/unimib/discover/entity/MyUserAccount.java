package it.unimib.discover.entity;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "USER_ACCOUNTS")
public class MyUserAccount implements Serializable {
	 
	private static final long serialVersionUID = 6513759807588969347L;

	public static final String ROLE_USER = "ROLE_USER";
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;
	
	@Column(name = "USER_NAME", nullable = false, length = 100)
	private String userName;
	
	@Column(name = "FIRST_NAME", nullable = true, length = 100)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = true, length = 100)
	private String lastName;
	
	@Column(name = "PASSWORD", nullable = false, length = 255)
	private String password;
	
	@Column(name = "IMAGE_URL", nullable = true, length = 400)
	private String imageUrl;
	
	@Column(name = "ROLE", nullable = true, length = 20)
	private String role;
	
	@Column(name = "PUNTI")
	private Integer punti;
	
	@Column(name = "SESSO")
	private char sesso;
	
	@Column(name = "LIVELLO")
	private Integer livello;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_NASCITA")
	private Date dataNascita;
	
	@Transient
	private String retypePassword;
	 
	public MyUserAccount() {}
	 
    public MyUserAccount(String id, String email,String userName, String firstName,
           String lastName, String password,
           String imageUrl, String role) {
	    this.id = id;
	    this.email = email;
	    this.userName= userName;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.password = password;
	    this.imageUrl = imageUrl;
	    this.role = role;
    }
	 
    public MyUserAccount(String id, String email,String userName, String firstName,
           String lastName, String password,
           String imageUrl, String role, Integer punti, Integer livello) {
	    this.id = id;
	    this.email = email;
	    this.userName= userName;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.password = password;
	    this.imageUrl = imageUrl;
	    this.role = role;
	    this.punti = punti;
	    this.livello = livello;
    }
 
    public String getId() {
    	return id;
    }
 
    public void setId(String id) {
    	this.id = id;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
   public String getLastName() {
       return lastName;
   }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRole() {
       return role;
	}
 
    public void setRole(String role) {
        this.role = role;
    }
 
	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public Integer getPunti() {
		return punti;
	}

	public void setPunti(Integer punti) {
		this.punti = punti;
	}

	public char getSesso() {
		return sesso;
	}

	public void setSesso(char sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Integer getLivello() {
		return livello;
	}

	public void setLivello(Integer livello) {
		this.livello = livello;
	}
 
}
