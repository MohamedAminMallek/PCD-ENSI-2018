package com.ensi.serviceHabitat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensi.serviceHabitat.entity.Metier;

@Repository("metierRepository")
public interface MetierRepository extends JpaRepository<Metier, Long>{
	public Metier findByTitre(String titre);
}