package com.ensi.serviceHabitat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ensi.serviceHabitat.entity.MessageID;
import com.ensi.serviceHabitat.entity.Msg;
import com.ensi.serviceHabitat.entity.Personne;

@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Msg, MessageID> {
	
	
	@Query(value="select * from message m where not exists(select * from reponse r where r.message_id=m.message_id and r.emetteur_id=m.emetteur_id)",nativeQuery=true)
	public List<Msg> getAllMsg();
	
	public List<Msg> findByEmetteur(Personne emetteur);
	

}