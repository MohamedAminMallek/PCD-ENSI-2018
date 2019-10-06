package com.ensi.serviceHabitat;


import static org.mockito.Matchers.contains;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.web.bind.annotation.RequestParam;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.ensi.serviceHabitat.configuration.TestLogicConfig;
import com.ensi.serviceHabitat.entity.Administrateur;
import com.ensi.serviceHabitat.entity.ContactID;
import com.ensi.serviceHabitat.entity.DemandeContact;
import com.ensi.serviceHabitat.entity.DemandeurService;
import com.ensi.serviceHabitat.entity.EtatDemande;
import com.ensi.serviceHabitat.entity.FournisseurService;
import com.ensi.serviceHabitat.entity.MessageID;
import com.ensi.serviceHabitat.entity.Metier;
import com.ensi.serviceHabitat.entity.Msg;
import com.ensi.serviceHabitat.entity.Note;
import com.ensi.serviceHabitat.entity.NoteID;
import com.ensi.serviceHabitat.entity.Personne;
import com.ensi.serviceHabitat.entity.PropositionMetier;
import com.ensi.serviceHabitat.entity.PropositionMetierID;
import com.ensi.serviceHabitat.entity.Reponse;
import com.ensi.serviceHabitat.entity.ReponseID;
import com.ensi.serviceHabitat.entity.User;
import com.ensi.serviceHabitat.persistence.MessageRepository;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestLogicConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
public class AppTest {
	
	@Autowired
	private ServiceHabitatServiceImpl habitatService;
	
	/*@Test
	public void remplirLaBase()
	{
		
		Personne personne1 = new Personne();
		personne1.setCin("11051105");
		personne1.setNom("Mallek");
		personne1.setPrenom("Mohamed Amin");
		personne1.setTel(50474932);
		personne1.setAdresse("Sfax");
		personne1.setDateNaissance(new Date(1994, 07, 9));
		personne1.setUserName("Mallek");
		personne1.setPassWord("mallek");
		personne1.setScore(10);
		habitatService.saveAndFlush(personne1);
		
		Personne personne2 = new Personne();
		personne2.setCin("09194800");
		personne2.setNom("Ghorbel");
		personne2.setPrenom("Mohamed");
		personne2.setTel(45852741);
		personne2.setAdresse("Manouba");
		personne2.setDateNaissance(new Date(1995, 04, 10));
		personne2.setUserName("Ghorbel");
		personne2.setPassWord("Ghorbel");
		personne2.setScore(10);
		habitatService.saveAndFlush(personne2);
		
		Metier metier = new Metier();
		metier.setTitre("Plombier");
		metier.setDescription("C'est un plombier");
		habitatService.saveAndFlush(metier);
		
		FournisseurService fournisseurService = new FournisseurService();
		fournisseurService.setMetier(metier);
		fournisseurService.setDisponibilite(true);
		fournisseurService.setCin(personne1.getCin());
		habitatService.inscription(fournisseurService);
		
		DemandeurService demandeurService = new DemandeurService();
		demandeurService.setCin(personne2.getCin());
		demandeurService.setProfession("Etudiant");
		habitatService.inscriptionDemandeur(demandeurService.getCin(),demandeurService.getProfession());
		
		Msg msg = new Msg();
		msg.setEmetteur(personne1);
		msg.setTitre("Probleme de paiement");
		msg.setDescription("j'ete pas payer par Mohamed Ghorbel");
		msg.setId(new MessageID());
		habitatService.saveAndFlush(msg);
		
		Msg msg1 = new Msg();
		msg1.setEmetteur(personne2);
		msg1.setTitre("Service");
		msg1.setDescription("je suis pas satisfait par le service de mallek Mohamed Amin");
		msg1.setId(new MessageID());
		habitatService.saveAndFlush(msg1);
		
		PropositionMetier metier2 = new PropositionMetier();
		metier2.setEmetteur(personne2);
		metier2.setTitre("Jardinier");
		metier2.setDescription("c'est un jardinier");
		metier2.setId(new PropositionMetierID());
		habitatService.saveAndFlush(metier2);
		
	}*/
	/*@Test
	public void createTestHBC()  {
		TestHBC test=new TestHBC();
		test.setLabel("hbc");
		test.setPrenom("zaaaa");
		testHBCService.saveAndFlush(test);
	}
	@Test
	public void createAdmin()  {
		Administrateur admin = new Administrateur("ghorbel","123456");
		habitatService.saveAndFlush(admin);
	}
	@Test
	public void findAdmin()  {
		//Administrateur admin = new Administrateur("ghorbel","123456");
		//habitatService.saveAndFlush(admin);
		System.out.println(habitatService.findOne("ghorbel", "123456"));
	}
	@Test
	public void testPersonne()
	{
		
		DemandeurService demandeurService = new DemandeurService();
		demandeurService.setNom("Mallek");
		demandeurService.setPrenom("Amin");
		demandeurService.setCin("11051115");
		demandeurService.setAdresse("Sfax");
		demandeurService.setProfession("Etudiant");
		
		Metier metier=new Metier();
		metier.setTitre("plombier");
		
		FournisseurService fournisseurService = new FournisseurService();
		fournisseurService.setCin("09194800");
		fournisseurService.setMetier(metier);
		
		DemandeContact demandeContact= new DemandeContact();
		demandeContact.setFournisseurService(fournisseurService);
		demandeContact.setDemandeurService(demandeurService);
		demandeContact.setId(new ContactID());
		
		Note note=new Note();
		
		note.setAutheur(demandeurService);
		note.setSujet(fournisseurService);
		note.setScore(55);
		note.setContact(demandeContact);
		note.setId(new NoteID());
		
		Msg msg = new Msg();
		msg.setEmetteur(demandeurService);
		msg.setTitre("c'est un titre");
		msg.setId(new MessageID());
	
		//habitatService.saveAndFlush(demandeurService);	
		//habitatService.saveAndFlush(metier);		
		//habitatService.saveAndFlush(fournisseurService);	
		//habitatService.saveAndFlush(demandeContact);	
		//habitatService.saveAndFlush(note);	
		//habitatService.saveAndFlush(msg);
		
		Administrateur admin = habitatService.findOne("Ghorbel", "je suis un password");
		System.out.println(admin);
		admin.setPassWord("123456Pass");
		habitatService.saveAndFlush(admin);
		System.out.println(admin);
	}
	*/
	@Test
	public void testMethodes()  {
		
		
		//Personne p = habitatService.authentification("test", "test");
		//System.out.println(p.getCin());
		//habitatService.bannir(p, 5);
		//System.out.println(habitatService.findByCin("11051115"));
		//System.out.println(habitatService.authentification("test", "test"));
		
		/*
		
		ContactID contactID = new ContactID();
		contactID.setContact_id(2);
		contactID.setFournisseur_id("11051115");
		contactID.setDemandeur_id("09194800");
		DemandeContact contact = habitatService.findOne(contactID);
		contact.setEtat("Refusee");
		habitatService.saveAndFlush(contact);
		*/
		
		/*
		String a,s;
		a="09194800";
		s="11051115";
		
		ContactID contactID = new ContactID();
		contactID.setContact_id(2);
		contactID.setFournisseur_id("11051115");
		contactID.setDemandeur_id("09194800");
		DemandeContact contact = habitatService.findOne(contactID);


		Note note = new Note();
		
		note.setContact(contact);
		
		note.setId(new NoteID());
		if(contact.getFournisseurService().getCin().equals(s))
		{
			note.setSujet((Personne)contact.getFournisseurService());
			note.setAutheur((Personne)contact.getDemandeurService());
		
		}else {
			note.setSujet((Personne)contact.getDemandeurService());
			note.setAutheur((Personne)contact.getFournisseurService());
		}
		note.setScore(20);
		note.setCommentaire("c'est un trés bien ! merci monsieur le plombier");
		
		//habitatService.saveAndFlush(note);
		
		*/
		
		/*
		
		DemandeContact contact = new DemandeContact();
		contact.setDemandeurService(habitatService.findOneDemandeurService("09194800"));
		contact.setFournisseurService(habitatService.findOneFournisseurService("11051115"));
		contact.setId(new ContactID());
		contact.setTitre("prob de plomberie");
		contact.setEtat(EtatDemande.Acceptee.toString());
		habitatService.saveAndFlush(contact);
		///FournisseurService f  = new FournisseurService();
		//f.setCin(personne.getCin());
		//f.setMetier(metier);
		//f.setDisponibilite(true);
		//habitatService.inscription(f);
		*/
		/*
		Personne p = habitatService.findByCin("11051115");
		Msg msg = new Msg();
		msg.setEmetteur(p);
		msg.setTitre("Mohamed Ghorbel");
		msg.setDescription("He is a bitch");
		msg.setId(new MessageID());
		
		habitatService.saveAndFlush(msg);
		*/
		/*
		MessageID id = new MessageID();
		id.setEmetteur_id("11051115");
		id.setMessage_id(3);
		Msg msg = new Msg();
		msg = habitatService.findOne(id);
		Reponse reponse = new Reponse();
		reponse.setMessage(msg);
		reponse.setId(new ReponseID());
		reponse.setTitre("Ghorbel Mohamed");
		reponse.setDescription("Yes He is");
		
		habitatService.saveAndFlush(reponse);
		*/
		
		
		/*
		for(Msg m : habitatService.getMsgWithOutResponse())
			System.out.println(m.getEmetteur().getNom());
		*/
		
		
		/*
		long msgId=1;
		String cin="11051115";
		String description="yes he is";
		String titre="ghorbel mohamed";
		MessageID id = new MessageID();
		id.setMessage_id(msgId);
		id.setEmetteur_id(cin);
		Msg msg = habitatService.findOne(id);
		Reponse reponse = new Reponse();
		reponse.setMessage(msg);
		reponse.setDescription(description);
		reponse.setTitre(titre);
		reponse.setId(new ReponseID());
		//habitatService.saveAndFlush(reponse);
		*/
		
		//PropositionMetier p = new PropositionMetier(0, "Jardinier", "c'est jardinier", habitatService.findByCin("11051115"));
		
		//habitatService.saveAndFlush(p);
		/*
		Personne pp = habitatService.findByCin("11051105");
		System.out.println(pp.getNom());
		PropositionMetier metier2 = new PropositionMetier();
		metier2.setEmetteur(pp);
		metier2.setTitre("Plombier");
		metier2.setDescription("c'est un Plombier");
		metier2.setId(new PropositionMetierID());
		//habitatService.saveAndFlush(metier2);
		
		PropositionMetier metier3 = new PropositionMetier();
		metier3.setEmetteur(pp);
		metier3.setTitre("Femme de m�nage");
		metier3.setDescription("description d'une femme de m�nage");
		metier3.setId(new PropositionMetierID());
		habitatService.saveAndFlush(metier3);
		System.out.println(habitatService.findByTitre("plombier").getDescription());
		for(PropositionMetier p :habitatService.findAllPropositionMetier())
			System.out.println(p.getTitre());
		
		*/
		/*
		List<Metier> listMetiers = habitatService.findAllMetier();
		for(Metier m : listMetiers)
		{	
			System.out.println(m.getTitre());
			for(FournisseurService f : habitatService.findByMetier(habitatService.findOneMetier(m.getId())))
			{
				System.out.println(f.getNom()+" "+f.getPrenom());
			}
		}*/
		
		//System.out.println(habitatService.findOneDemandeurService("11051105"));
		//System.out.println(habitatService.findOneFournisseurService("11051105"));
		
		//habitatService.findOneFournisseurService("11051105");
		//habitatService.findOneDemandeurService("11051105");
		habitatService.findAllMsg();
		//habitatService.findAllMetier();
		/*
		List<DemandeContact> contacts = habitatService.findByFrsAndEtat(habitatService.findOneFournisseurService("12334"), "EnAttente");
		for(DemandeContact d : contacts)
			System.out.println(d.getTitre()+" "+d.getDemandeurService().getNom());
		
		List<DemandeContact> contact2 = habitatService.findByDemandeurAndEtat(habitatService.findOneDemandeurService("11051105"), "EnAttente");
		for(DemandeContact d : contact2)
			System.out.println(d.getTitre()+" "+d.getFournisseurService().getNom());
		
		
		
		habitatService.updateDis("11051105", true);
		*/
		/*
		Date toDay = new Date();
		Personne p = habitatService.authentification("1", "1");
		System.out.println(p.getBannedUntil().compareTo(toDay));
		*/
		/*
		String cin = "11051105";
		List<DemandeContact> demandeContacts = habitatService.findByDemandeurAndEtat(habitatService.findOneDemandeurService(cin), "Accepter");
		for(DemandeContact d : demandeContacts)
		{
			
			ContactID contactID = new ContactID();
			contactID.setContact_id(d.getId().getContact_id());
			contactID.setFournisseur_id(d.getId().getFournisseur_id());
			contactID.setDemandeur_id(d.getId().getDemandeur_id());
			
			NoteID id = new NoteID();
			id.setAuteur(cin);
			id.setContact_id(contactID);
			if(d.getId().getDemandeur_id().compareTo(cin)==0)
				id.setSujet(d.getId().getFournisseur_id());
			else
				id.setSujet(d.getId().getDemandeur_id());
			
			
			System.out.println(id.getContact_id().getContact_id()+" "+id.getContact_id().getDemandeur_id()+" "+id.getContact_id().getFournisseur_id()+" "+id.getAuteur()+" "+id.getSujet());
			
			boolean nb = habitatService.exist(id);
			System.out.println(nb);
			
			if(!nb) {
				
				System.out.println(d.getDescription());
				
			}
			//System.out.println(d.getDescription());
		}
		
		
		habitatService.findBySujet("11051105");
		*/
		
		Reponse p = new Reponse();
		p.setDescription("tetete");
		p.setTitre("fdfdfdf");
		ReponseID pp = new ReponseID();
		pp.setReponse_id(1);
		MessageID mm = new MessageID();
		mm.setEmetteur_id("11051105");
		mm.setMessage_id(2);
		pp.setMessgae_id(mm);
		p.setId(pp);
		
		//habitatService.saveAndFlush(p);
		
	}
	
	
	
}
