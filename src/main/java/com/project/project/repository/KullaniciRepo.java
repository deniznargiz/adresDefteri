package com.project.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.project.model.Kullanici;

@Repository
public interface KullaniciRepo extends JpaRepository<Kullanici, Integer>{
	public List<Kullanici> findByisim(String isim);
	public List<Kullanici> findBysoyisim(String soyisim);
	public Kullanici findById(int id);
}

