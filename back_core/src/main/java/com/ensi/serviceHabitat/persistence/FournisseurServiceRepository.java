package com.ensi.serviceHabitat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensi.serviceHabitat.entity.FournisseurService;
import com.ensi.serviceHabitat.entity.Metier;

@Repository("fournisseurServiceRepository")
public interface FournisseurServiceRepository extends JpaRepository<FournisseurService, String>{

	@Modifying
    @Query(value="insert into Fournisseur_Service(personne_id,disponibilite,id_metier) values(:id_personne,:dis,:metier_id)",nativeQuery=true)
	@Transactional
	public void inscription(@Param("id_personne")String id_personne,@Param("dis")boolean disp,@Param("metier_id")Long meiter_id);
	
	public List<FournisseurService> findByMetierAndDisponibilite(Metier metier,boolean disp);
	
	@Modifying
    @Query(value="update Fournisseur_Service set disponibilite = :dis where personne_id = :id_personne",nativeQuery=true)
	@Transactional
	public void updateDis(@Param("id_personne")String id_personne,@Param("dis")boolean dis);
	
	
}
