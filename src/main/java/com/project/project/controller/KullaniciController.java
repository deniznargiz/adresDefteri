package com.project.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.project.project.model.Adres;
import com.project.project.model.Kullanici;
import com.project.project.model.Telefon;
import com.project.project.repository.AdresRepo;
import com.project.project.repository.KullaniciRepo;
import com.project.project.repository.TelefonRepo;

@RestController
public class KullaniciController {
	
	@Autowired
	KullaniciRepo kullaniciRepo;
	
	@Autowired
	TelefonRepo telefonRepo;
	
	@Autowired
	AdresRepo adresRepo;
	
	@GetMapping("/kullanicilar")
	 public  @ResponseBody List<Kullanici> getAllKullanicilar() {
        return kullaniciRepo.findAll();
        
    }
	@GetMapping("/adresler")
	public @ResponseBody List<Adres> getAllAdres(){
		return adresRepo.findAll();
	}
	//TODO: isim ara
	@GetMapping(value = "/kullanici",
				params = {"isim"})	
	public @ResponseBody List<Kullanici> getByName(@RequestParam("isim")String isim){
		return kullaniciRepo.findByisim(isim);
	}
	
	//TODO: soyisim ara
	@GetMapping(value = "/kullanici",
				params = {"soyisim"})
	public @ResponseBody List<Kullanici> getBySurname(@RequestParam("soyisim")String soyisim){
		return kullaniciRepo.findBysoyisim(soyisim);
	}
	
	//TODO: adres ara
	@GetMapping (value ="/adres",
				params = {"adresAdi"})
	public @ResponseBody List<Adres> getbyAddressName(@RequestParam("adresAdi")String adresAdi){
		return adresRepo.findByAdresAdi(adresAdi);
	}
				
	//TODO: kullanici ekleme + telefon ve adres eklenecek
	@PostMapping(value = "/kullanici_ekle")
	public ResponseEntity<Kullanici> addUser(Kullanici kullanici){
		kullaniciRepo.save(kullanici);
		return new ResponseEntity<Kullanici>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/telefon_ekle",
				 params = {"kullaniciID"})
	public ResponseEntity<Telefon> addPhoneToUser(Telefon telefon,@RequestParam("kullaniciID")int kullaniciId){
		if(!kullaniciRepo.existsById(kullaniciId)) {
			return new ResponseEntity<Telefon>(HttpStatus.NO_CONTENT);
		}else {
			Kullanici kullanici = kullaniciRepo.findById(kullaniciId);
			telefon.setKullanici(kullanici);	
			telefonRepo.save(telefon);
			return new ResponseEntity<Telefon>(HttpStatus.OK);
		}	
	}
	
	@PostMapping(value = "/adres_ekle",
				params = {"kullaniciID"})
	public ResponseEntity<Adres> addAddressToUser(Adres adres,@RequestParam("kullaniciID")int kullaniciId){
		if(!kullaniciRepo.existsById(kullaniciId)) {
			return new ResponseEntity<Adres>(HttpStatus.NO_CONTENT);
		}else {
			Kullanici kullanici= kullaniciRepo.findById(kullaniciId);
			adres.setKullanici(kullanici);
			adresRepo.save(adres);
			return new ResponseEntity<Adres>(HttpStatus.OK);
		}
	}

	// TODO: kullanici silme ---> tel ve adresden de silinecek db cascade
	 @DeleteMapping(value = "/kullanici_sil",
			 		params = {"kullaniciID"})
	 public ResponseEntity<Kullanici> DeleteUser(@RequestParam("kullaniciID")int kullaniciId){
		 if(kullaniciRepo.existsById(kullaniciId)) {
			 Kullanici kullaniciSil = kullaniciRepo.findById(kullaniciId);
			 kullaniciRepo.delete(kullaniciSil);
			 return ResponseEntity.ok().build();
		 }else {
			 return new ResponseEntity<Kullanici>(HttpStatus.NO_CONTENT);
			 }
	 }
	
	 //TODO: güncelleme kullanici tel ve adres guncellenecek
	 @PutMapping(value = "/kullanici_guncelle",
			 	params = {"kullaniciID"})
	 public Kullanici UpdateUser(Kullanici kullanici, @RequestParam("kullaniciID")int kullaniciID){
		 if(kullaniciRepo.existsById(kullaniciID)) {
			 Kullanici kullaniciGuncelle = kullaniciRepo.findById(kullaniciID);
			 kullaniciGuncelle.setIsim(kullanici.getIsim());
			 kullaniciGuncelle.setSoyisim(kullanici.getSoyisim());
			 kullaniciGuncelle.setDogumTarihi(kullanici.getDogumTarihi());
			 kullaniciGuncelle.setIsYeri(kullanici.getIsYeri());
	         return kullaniciRepo.save(kullaniciGuncelle);	
		 }
		return kullanici;
			 
	 } 
	 //TODO: telefon guncelleme
	 @PutMapping(value = "/telefon_guncelle",
			 	params = {"kullaniciID","telefonID"})
	 public Telefon UpdatePhone ( Telefon telefon,@RequestParam("kullaniciID")int kullaniciId,
			 												  @RequestParam("telefonID")int telefonId){
		 if(kullaniciRepo.existsById(kullaniciId)){
			 if(telefonRepo.existsById(telefonId)) {
				 Telefon telefonGuncelle = telefonRepo.findById(telefonId);
				 telefonGuncelle.setTelefon_cesit(telefon.getTelefon_cesit());
				 telefonGuncelle.setTelefonNo(telefon.getTelefonNo());
				 return telefonRepo.save(telefonGuncelle);
			 }	
		 }
		return telefon;
	 }
	 
	 //TODO: adres guncelleme 
	 @PutMapping(value="/adres_guncelle",
			 	params = {"kullaniciID","adresID"})
	 public Adres UpdateAddress (Adres adres,@RequestParam("kullaniciID")int kullaniciId,
			 											  @RequestParam("adresID")int adresId) {
		 if(kullaniciRepo.existsById(kullaniciId)) {
			 if(adresRepo.existsById(adresId)) {
				 Adres adresGuncelle = adresRepo.findById(adresId);
				 adresGuncelle.setAdresAdi(adres.getAdresAdi());
				 adresGuncelle.setSehir(adres.getSehir());
				 adresGuncelle.setIlce(adres.getIlce());
				 adresGuncelle.setMahalle(adres.getMahalle());
				 adresGuncelle.setSokak(adres.getSokak());
				 adresGuncelle.setApartman(adres.getApartman());
				 adresGuncelle.setDaireNo(adres.getDaireNo());
				 return adresRepo.save(adresGuncelle);
			 }
		 }
		 return adres;		 
	 }
}
