package com.project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.project.model.Telefon;

@Repository
public interface TelefonRepo extends JpaRepository<Telefon,Integer> {

	
	public Telefon findById(int id);

}
