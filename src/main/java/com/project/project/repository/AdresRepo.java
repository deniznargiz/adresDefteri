package com.project.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.project.model.Adres;

@Repository
public interface AdresRepo  extends JpaRepository<Adres,Integer>{
	public List<Adres> findByAdresAdi(String adresAdi);	
	public Adres findById(int id);

}
