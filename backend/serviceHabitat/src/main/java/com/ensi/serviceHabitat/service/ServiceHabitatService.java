package com.ensi.serviceHabitat.service;

import java.util.List;
import java.util.Set;

import com.ensi.serviceHabitat.entity.*;

public interface ServiceHabitatService {
	
	public List<Administrateur> findAll();

	public Administrateur findOne(Long arg0);

	public Administrateur getOne(Long arg0);

	public Administrateur saveAndFlush(Administrateur arg0);

	public void delete(Long arg0);

	public void delete(Administrateur arg0);
	
	public Administrateur findOne(String username,String password);
		
	public List<Personne> findAllPersonne();

	public Personne authentification(String cin,String password);
	
	public Personne findOnePersonne(String arg0);

	public Personne getOne(String arg0);

	public Personne saveAndFlush(Personne arg0);
	
	public Personne bannir(Personne personne ,int jours);
	
	public Personne findByCin(String cin);
	
	public void updateScore(Personne p);

	public void delete(String arg0);

	public void delete(Personne arg0);
	
	public void inscriptionDemandeur(String cin,String profession);
	
	public List<DemandeurService> findAllDemandeurService();

	public DemandeurService findOneDemandeurService(String arg0);

	public DemandeurService getOneDemandeurService(String arg0);

	public void delete(DemandeurService arg0);
	
	public DemandeurService update(DemandeurService demandeurService);
	
	public List<Msg> findAllMsg();

	public Msg findOne(MessageID arg0);

	public Msg getOne(MessageID arg0);

	public Msg saveAndFlush(Msg arg0);

	public void delete(Msg arg0);
	
	public List<Msg> getMsgWithOutResponse();

	public List<Reponse> findAllReponse();
	
	public List<Reponse> findReponseByCin(String cin);

	public Reponse findOne(ReponseID arg0);

	public Reponse getOne(ReponseID arg0);

	public void saveAndFlush(Reponse arg0);

	public void delete(Reponse arg0);
	

	public List<DemandeContact> findAllDemandeContact();
	public List<DemandeContact> findByFrsAndEtat(FournisseurService fournisseurService,String etat);
	public List<DemandeContact> findByDemandeurAndEtat(DemandeurService demandeurService,String etat);

	public DemandeContact findOne(ContactID arg0);

	public DemandeContact getOne(ContactID arg0);

	public DemandeContact saveAndFlush(DemandeContact arg0);

	public void delete(DemandeContact arg0);
	
	
	public List<FournisseurService> findAllFournisseurService();

	public FournisseurService findOneFournisseurService(String arg0);

	public FournisseurService getOneFournisseurService(String arg0);
	
	public List<FournisseurService> findByMetier(Metier metier);

	public FournisseurService saveAndFlush(FournisseurService arg0);
	
	public void updateDis(String cin,boolean dis);

	public void inscription(FournisseurService arg0);
	
	public void delete(FournisseurService arg0);
	
	
	public List<Metier> findAllMetier();

	public Metier findOneMetier(Long arg0);
	public Metier findOne(int arg0);
	
	public Metier findByTitre(String arg0);

	public Metier getOneMetier(Long arg0);

	public Metier saveAndFlush(Metier arg0);

	public void delete(Metier arg0);
	
	
	public List<Note> findAllNote();
	
	public List<Note> findBySujet(String cin);
	
	public Note findOne(NoteID arg0);

	public Note getOne(NoteID arg0);

	public Note saveAndFlush(Note arg0);
	public boolean exist(NoteID id);
	
	
	public void delete(Note arg0);
	
	
	public List<PropositionMetier> findAllPropositionMetier();

	public PropositionMetier findOne(PropositionMetierID arg0);

	public PropositionMetier getOne(PropositionMetierID arg0);

	public PropositionMetier saveAndFlush(PropositionMetier arg0);

	public void delete(PropositionMetier arg0);
			
	public Note ajouterNoteAndUpdatePersonne(Note n,Personne p);
	
}