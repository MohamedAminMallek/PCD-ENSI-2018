package com.ensi.serviceHabitat.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ensi.serviceHabitat.entity.*;
import com.ensi.serviceHabitat.persistence.*;
@Service
public class ServiceHabitatServiceImpl implements ServiceHabitatService {

	@Autowired
	AdministrateurRepository administrateurRepository;
	
	@Autowired
	DemandeContactRepository demandeContactRepository;
	
	@Autowired
	DemandeurServiceRepository demandeurServiceRepository;
	
	@Autowired
	FournisseurServiceRepository fournisseurServiceRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	MetierRepository metierRepository;
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	PersonneRepository personneRepository;
	
	@Autowired
	PropositionMetierRepository propositionMetierRepository;
	
	@Autowired
	ReponseRepository reponseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<Administrateur> findAll() {
		return administrateurRepository.findAll();
	}

	@Override
	public Administrateur findOne(Long arg0) {
		return administrateurRepository.findOne(arg0);
	}

	@Override
	public Administrateur getOne(Long arg0) {
		return administrateurRepository.getOne(arg0);
	}

	@Override
	public Administrateur saveAndFlush(Administrateur arg0) {
		return administrateurRepository.saveAndFlush(arg0);
	}

	@Override
	public void delete(Long arg0) {
		administrateurRepository.delete(arg0);
	}

	@Override
	public void delete(Administrateur arg0) {
		administrateurRepository.delete(arg0);
	}

	@Override
	public Administrateur findOne(String username, String password) {
		
		return administrateurRepository.findByUserNameAndPassWord(username, password);
	}
	@Override
	public Personne saveAndFlush(Personne arg0) {
		return personneRepository.saveAndFlush(arg0);
	}

	@Override
	public void delete(String arg0) {
		personneRepository.delete(arg0);
	}

	@Override
	public void delete(Personne arg0) {
		
		personneRepository.delete(arg0);
	}
	
	@Override
	public void inscriptionDemandeur(String cin,String profession)
	{
		demandeurServiceRepository.inscription(cin, profession);
	}

	
	@Override
	public Msg saveAndFlush(Msg m) {
		
		return messageRepository.saveAndFlush(m);
	}

	@Override
	public Personne getOne(String cin) {
		
		return personneRepository.getOne(cin);
		
	}

	@Override
	public void saveAndFlush(Reponse reponse) {
		
		reponseRepository.saveAndFlush(reponse.getDescription(), reponse.getTitre(), reponse.getId().getMessgae_id().getEmetteur_id(), reponse.getId().getMessgae_id().getMessage_id());
	}

	@Override
	public Reponse findOne(ReponseID reponseId) {
		
		return reponseRepository.findOne(reponseId);
	}

	@Override
	public Personne findOnePersonne(String cin) {
		
		return personneRepository.findOne(cin);
	}

	@Override
	public DemandeContact saveAndFlush(DemandeContact arg0) {
		return demandeContactRepository.saveAndFlush(arg0);
	}

	@Override
	public FournisseurService saveAndFlush(FournisseurService arg0) {
		return fournisseurServiceRepository.saveAndFlush(arg0);
	}

	@Override
	public Metier saveAndFlush(Metier arg0) {
		return metierRepository.saveAndFlush(arg0);
	}

	@Override
	public Note saveAndFlush(Note arg0) {
		return noteRepository.saveAndFlush(arg0);
	}

	@Override
	public PropositionMetier saveAndFlush(PropositionMetier arg0) {
		return propositionMetierRepository.saveAndFlush(arg0);
	}

	@Override
	public List<Personne> findAllPersonne() {
		return personneRepository.findAllWithQuery();
	}

	@Override
	public List<DemandeurService> findAllDemandeurService() {
		return demandeurServiceRepository.findAll();
	}

	@Override
	public DemandeurService findOneDemandeurService(String arg0) {
		return demandeurServiceRepository.findOne(arg0);
	}

	@Override
	public DemandeurService getOneDemandeurService(String arg0) {
		return demandeurServiceRepository.getOne(arg0);
	}

	@Override
	public void delete(DemandeurService arg0) {
		demandeurServiceRepository.delete(arg0);
	}

	@Override
	public List<Msg> findAllMsg() {
		return messageRepository.findAll();
	}

	@Override
	public Msg findOne(MessageID arg0) {
		return messageRepository.findOne(arg0);
	}

	@Override
	public Msg getOne(MessageID arg0) {
		return messageRepository.getOne(arg0);
	}

	@Override
	public void delete(Msg arg0) {
		messageRepository.delete(arg0);
	}

	@Override
	public List<Reponse> findAllReponse() {
		return reponseRepository.findAll();
	}

	@Override
	public Reponse getOne(ReponseID arg0) {
		return reponseRepository.getOne(arg0);
	}

	@Override
	public void delete(Reponse arg0) {
	reponseRepository.delete(arg0);
		
	}

	@Override
	public List<DemandeContact> findAllDemandeContact() {
		return demandeContactRepository.findAll();
	}

	@Override
	public DemandeContact findOne(ContactID arg0) {
		return demandeContactRepository.findOne(arg0);
	}

	@Override
	public DemandeContact getOne(ContactID arg0) {
		return demandeContactRepository.getOne(arg0);
	}

	@Override
	public void delete(DemandeContact arg0) {
		demandeContactRepository.delete(arg0);
		
	}

	@Override
	public List<FournisseurService> findAllFournisseurService() {
		return fournisseurServiceRepository.findAll();
	}

	@Override
	public FournisseurService findOneFournisseurService(String arg0) {
		return fournisseurServiceRepository.findOne(arg0);
	}

	@Override
	public FournisseurService getOneFournisseurService(String arg0) {
		return fournisseurServiceRepository.getOne(arg0);
	}

	@Override
	public void delete(FournisseurService arg0) {
		fournisseurServiceRepository.delete(arg0);
		
	}

	@Override
	public List<Metier> findAllMetier() {
		return metierRepository.findAll();
	}

	@Override
	public Metier findOneMetier(Long arg0) {
		return metierRepository.findOne((long) arg0);
	}

	@Override
	public Metier getOneMetier(Long arg0) {
		return metierRepository.getOne((long) arg0);
	}
	
	@Override
	public Metier findByTitre(String arg0) {
		return metierRepository.findByTitre(arg0);
	}
	

	@Override
	public void delete(Metier arg0) {
		metierRepository.delete(arg0);
	}

	@Override
	public List<Note> findAllNote() {
		return noteRepository.findAll();
	}

	@Override
	public Note findOne(NoteID arg0) {
		return noteRepository.findOne(arg0);
	}

	@Override
	public Note getOne(NoteID arg0) {
		return noteRepository.getOne(arg0);
	}

	@Override
	public void delete(Note arg0) {
		noteRepository.delete(arg0);
		
	}

	@Override
	public List<PropositionMetier> findAllPropositionMetier() {
		return propositionMetierRepository.findAll();
	}

	@Override
	public PropositionMetier findOne(PropositionMetierID arg0) {
		return propositionMetierRepository.findOne(arg0);
	}

	@Override
	public PropositionMetier getOne(PropositionMetierID arg0) {
		return propositionMetierRepository.getOne(arg0);
	}

	@Override
	public void delete(PropositionMetier arg0) {
		propositionMetierRepository.delete(arg0);
		
	}

	@Override
	public DemandeurService update(DemandeurService demandeurService) {
		DemandeurService demandeurService2 = findOneDemandeurService(demandeurService.getCin());
		demandeurService2 = demandeurService;
		demandeurServiceRepository.saveAndFlush(demandeurService2);
		return demandeurService2;
	}

	@Override
	public void inscription(FournisseurService arg0) {
		
		fournisseurServiceRepository.inscription(arg0.getCin(), arg0.isDisponibilite(), arg0.getMetier().getId());
		
	}

	@Override
	public Personne authentification(String username, String password) {
		
		return personneRepository.findByUserNameAndPassWord(username, password);
		//return personneRepository.findBy(cin);
	}

	@Override
	public Personne bannir(Personne personne,int jours) {
		
		Date d = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(d); 
		c.add(Calendar.DATE, jours);
		d = c.getTime();
		
		
		personne.setBannedUntil(d);
		
		personneRepository.bannir(d, personne.getCin());
		
		return null;
	}

	@Override
	public Personne findByCin(String cin) {
		// TODO Auto-generated method stub
		return personneRepository.findBy(cin);
	}

	@Override
	public Metier findOne(int arg0) {
		return metierRepository.findOne((long)arg0);
	}

	@Override
	public List<Msg> getMsgWithOutResponse() {
		return messageRepository.getAllMsg();
	}

	@Override
	public List<FournisseurService> findByMetier(Metier metier) {
		
		return fournisseurServiceRepository.findByMetierAndDisponibilite(metier,true);
	}

	@Override
	public List<DemandeContact> findByFrsAndEtat(FournisseurService fournisseurService, String etat) {
		return demandeContactRepository.findByFournisseurServiceAndEtat(fournisseurService, etat);
	}

	@Override
	public List<DemandeContact> findByDemandeurAndEtat(DemandeurService demandeurService, String etat) {
		return demandeContactRepository.findByDemandeurServiceAndEtat(demandeurService, etat);
	}

	@Override
	public void updateDis(String cin, boolean dis) {
		// TODO Auto-generated method stub
		fournisseurServiceRepository.updateDis(cin, dis);
		
	}

	@Override
	public List<Reponse> findReponseByCin(String cin) {
		
		Personne emetteur = personneRepository.findBy(cin);
		
		List<Msg> listMsg = messageRepository.findByEmetteur(emetteur);
		
		List<Reponse> listeReponse = new ArrayList<>();
		
		for(Msg m : listMsg)
		{
			List<Reponse> aux = reponseRepository.findByMessage(m.getEmetteur().getCin(),m.getId().getMessage_id());
			listeReponse.addAll(aux);
		}
		
		return listeReponse;
		
	}

	@Override
	public void updateScore(Personne p) {
		
		System.out.println("ZZ"+p.getCin()+" "+p.getScore()+" "+p.getEtoile());
		personneRepository.updateScore(p.getScore(), p.getEtoile(), p.getCin());
		System.out.println(p.getCin()+" "+p.getScore()+" "+p.getEtoile());
		
	}

	@Override
	public Note ajouterNoteAndUpdatePersonne(Note n, Personne p) {
		
		saveAndFlush(n);
		updateScore(p);
		
		return n;
		
	}

	@Override
	public List<Note> findBySujet(String cin) {
		
		return noteRepository.findBySujet(personneRepository.findBy(cin));
	}

	@Override
	public boolean exist(NoteID id) {
		
		int nb =noteRepository.nbNote(id.getContact_id().getContact_id(), id.getContact_id().getDemandeur_id(), id.getContact_id().getFournisseur_id(), id.getAuteur(), id.getSujet());
		
		return nb>0;
	}


	
	
	
}
