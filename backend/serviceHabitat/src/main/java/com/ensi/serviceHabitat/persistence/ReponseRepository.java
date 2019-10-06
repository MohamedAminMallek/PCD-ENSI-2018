package com.ensi.serviceHabitat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensi.serviceHabitat.entity.Msg;
import com.ensi.serviceHabitat.entity.Reponse;
import com.ensi.serviceHabitat.entity.ReponseID;

@Repository("reponseRepository")
public interface ReponseRepository extends JpaRepository<Reponse, ReponseID> {

	
	@Query("select NEW com.ensi.serviceHabitat.entity.Reponse(titre,description) from Reponse where emetteur_id = ?1 and message_id = ?2")
	public List<Reponse> findByMessage(String e,long m);
	@Modifying
    @Query(value="insert into reponse(description,titre,emetteur_id,message_id) values(:d,:t,:e,:m)",nativeQuery=true)
	@Transactional
	public void saveAndFlush(@Param("d") String d,@Param("t") String t,@Param("e") String e,@Param("m") long m);
	
}
