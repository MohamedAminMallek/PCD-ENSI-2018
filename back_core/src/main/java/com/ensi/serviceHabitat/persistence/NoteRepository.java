package com.ensi.serviceHabitat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensi.serviceHabitat.entity.Note;
import com.ensi.serviceHabitat.entity.NoteID;
import com.ensi.serviceHabitat.entity.Personne;

@Repository("noteRepository")
public interface NoteRepository extends JpaRepository<Note, NoteID>{
	
	
	@Query("select NEW com.ensi.serviceHabitat.entity.Note(commentaire) from Note where sujet=?1")
	public List<Note> findBySujet(Personne p);
	
	@Query(value="select count(*) from note where contact_id=:id and demandeur_id = :id_d and fournisseur_id = :id_f and autheur = :a and sujet = :s",nativeQuery=true)
	public int nbNote(@Param("id") long id,@Param("id_d") String d,@Param("id_f") String f,@Param("a") String a,@Param("s") String s);
 
}