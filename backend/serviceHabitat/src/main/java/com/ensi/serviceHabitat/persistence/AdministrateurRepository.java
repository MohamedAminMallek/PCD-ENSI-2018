package com.ensi.serviceHabitat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensi.serviceHabitat.entity.Administrateur;

@Repository("administrateurRepository")
public interface AdministrateurRepository  extends JpaRepository<Administrateur, Long>{
	
	public Administrateur findByUserNameAndPassWord(String username, String password);
	
}
