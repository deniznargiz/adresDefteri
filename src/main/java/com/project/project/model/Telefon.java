package com.project.project.model;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="telefon")
public class Telefon  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int telefonID;
	private String telefon_cesit;
	private String telefonNo;
	public int getTelefonID() {
		return telefonID;
	}
	public void setTelefonID(int telefonID) {
		this.telefonID = telefonID;
	}
	public Telefon() {
		
	}
	public String getTelefon_cesit() {
		return telefon_cesit;
	}
	public void setTelefon_cesit(String telefon_cesit) {
		this.telefon_cesit = telefon_cesit;
	}
	public String getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(String telefonNo) {
		this.telefonNo = telefonNo;
	}
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kullaniciID")
    private Kullanici Kullanici;
    @JsonIgnore
	public Kullanici getKullanici() {
		return Kullanici;
	}
	public void setKullanici(Kullanici kullanici) {
		this.Kullanici = kullanici;
	}


}
