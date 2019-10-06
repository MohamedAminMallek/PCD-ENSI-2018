package com.ensi.serviceHabitat.persistence;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensi.serviceHabitat.entity.ContactID;
import com.ensi.serviceHabitat.entity.DemandeContact;
import com.ensi.serviceHabitat.entity.DemandeurService;
import com.ensi.serviceHabitat.entity.FournisseurService;

@Repository("demandeContactRepository")
public interface DemandeContactRepository extends JpaRepository<DemandeContact, ContactID>{
	
	
	public ArrayList<DemandeContact> findByFournisseurServiceAndEtat(FournisseurService fournisseurService,String etat);
	public ArrayList<DemandeContact> findByDemandeurServiceAndEtat(DemandeurService demandeurService,String etat);

}
