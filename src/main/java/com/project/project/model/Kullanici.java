package com.project.project.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name= "kullanici")
public class Kullanici  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	//kullanıcı ıd nin otomatik arttığı söyluyoz ve ıd belirtiyo
	private int kullaniciID;
	private String isim;
	private String soyisim; 
	private String dogumTarihi;
	private String isYeri;
	public Kullanici() {
		// TODO Auto-generated constructor stub
		telefonlar = new ArrayList<>();
	}

	public int getKullaniciID() {
		return kullaniciID;
	}
	public void setKullaniciID(int kullaniciID) {
		this.kullaniciID = kullaniciID;
	}
	public String getIsim() {
		return isim;
	}
	public void setIsim(String isim) {
		this.isim = isim;
	}
	public String getSoyisim() {
		return soyisim;
	}
	public void setSoyisim(String soyisim) {
		this.soyisim = soyisim;
	}

	public String getDogumTarihi() {
		return dogumTarihi;
	}
	public void setDogumTarihi(String dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}
	public String getIsYeri() {
		return isYeri;
	}
	public void setIsYeri(String isYeri) {
		this.isYeri = isYeri;
	}
	
	@OneToMany(cascade = CascadeType.ALL,
				fetch = FetchType.LAZY,
				mappedBy = "Kullanici")
    		private List<Telefon> telefonlar = new ArrayList<>();
    
	@OneToMany(cascade = CascadeType.ALL,
			    fetch = FetchType.LAZY,
			    mappedBy = "kullanici")
		    private List<Adres> adresler = new ArrayList<>();
	
	public List<Adres> getAdresler() {
		return adresler;
	}
	public void setAdresler(List<Adres> adresler) {
		this.adresler = adresler;
	}
	public List<Telefon> getTelefonlar() {
		return telefonlar;
	}
	public void setTelefonlar(List<Telefon> telefonlar) {
		this.telefonlar = telefonlar;
	}

	
}
