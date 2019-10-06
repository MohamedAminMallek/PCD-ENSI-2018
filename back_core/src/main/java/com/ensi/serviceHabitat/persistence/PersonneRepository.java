package com.ensi.serviceHabitat.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensi.serviceHabitat.entity.Personne;

@Repository("personneRepository")
public interface PersonneRepository extends JpaRepository<Personne, String> {
	
	@Query("select cin,nom,prenom,tel,dateNaissance,adresse,positionGps,score,photo,passWord,userName,bannedUntil,etoile from Personne order by score desc")
	public List<Personne> findAllWithQuery();
	
	
	@Query("select NEW com.ensi.serviceHabitat.entity.Personne(cin,nom,prenom,tel,dateNaissance,adresse,positionGps,score,photo,userName,passWord,bannedUntil,etoile) from Personne where cin = ?1")
	public Personne findBy(String cin);
	
	@Query("select NEW com.ensi.serviceHabitat.entity.Personne(cin,nom,prenom,tel,dateNaissance,adresse,positionGps,score,photo,"
			+ "userName,passWord,bannedUntil,etoile) from Personne where userName = ?1 and passWord = ?2")
	public Personne findByUserNameAndPassWord(String username,String password);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Personne p SET p.bannedUntil = :d WHERE p.cin = :cin")
	@Transactional
    public int bannir(@Param("d")Date date, @Param("cin") String cin);
	
	@Modifying
	@Query(value="update personne p SET p.score = :score, p.etoile = :etoile WHERE p.personne_id = :cin",nativeQuery=true)
	@Transactional
	public void updateScore(@Param("score")int score,@Param("etoile")double etoile, @Param("cin") String cin);

	
	
}
