package com.ensi.serviceHabitat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensi.serviceHabitat.entity.DemandeurService;
import com.ensi.serviceHabitat.entity.Personne;

@Repository("demandeurServiceRepository")
public interface DemandeurServiceRepository extends JpaRepository<DemandeurService, String>{
	
	@Modifying
    @Query(value="insert into Demandeur_Service(personne_id,profession) values(:id_personne,:profession)",nativeQuery=true)
	@Transactional
	public void inscription(@Param("id_personne")String id_personne,@Param("profession")String profession);

}
