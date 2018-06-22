package com.project.project.model;
import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="adres") 
public class Adres implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int adresID;
	private String adresAdi;
	private String sehir;
	private String ilce;
	private String mahalle;
	private String sokak;
	private String apartman;
	private int daireNo;
	
	public int getAdresID() {
		return adresID;
	}
	public void setAdresID(int adresID) {
		this.adresID = adresID;
	}

	public String getAdresAdi() {
		return adresAdi;
	}
	public void setAdresAdi(String adresAdi) {
		this.adresAdi = adresAdi;
	}
	public String getSehir() {
		return sehir;
	}
	public void setSehir(String sehir) {
		this.sehir = sehir;
	}
	public String getIlce() {
		return ilce;
	}
	public void setIlce(String ilce) {
		this.ilce = ilce;
	}
	public String getMahalle() {
		return mahalle;
	}
	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}
	public String getSokak() {
		return sokak;
	}
	public void setSokak(String sokak) {
		this.sokak = sokak;
	}
	public String getApartman() {
		return apartman;
	}
	public void setApartman(String apartman) {
		this.apartman = apartman;
	}
	public int getDaireNo() {
		return daireNo;
	}
	public void setDaireNo(int daireNo) {
		this.daireNo = daireNo;
	}
	  public Adres() {
		
	}

	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "kullaniciID")
	  @JsonIgnore
    private Kullanici kullanici;

	public Kullanici getKullanici() {
		return kullanici;
	}
	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}
}
