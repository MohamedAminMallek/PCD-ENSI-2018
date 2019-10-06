package com.ensi.serviceHabitat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensi.serviceHabitat.entity.PropositionMetier;
import com.ensi.serviceHabitat.entity.PropositionMetierID;

@Repository("propositionMetierRepository")
public interface PropositionMetierRepository extends JpaRepository<PropositionMetier, PropositionMetierID>{

}
