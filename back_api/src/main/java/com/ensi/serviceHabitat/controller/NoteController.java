package com.ensi.serviceHabitat.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensi.serviceHabitat.entity.ContactID;
import com.ensi.serviceHabitat.entity.DemandeContact;
import com.ensi.serviceHabitat.entity.Note;
import com.ensi.serviceHabitat.entity.NoteID;
import com.ensi.serviceHabitat.entity.Personne;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;


@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	private ServiceHabitatServiceImpl habitatService;

	@CrossOrigin
	@RequestMapping(value = "/ajouterNote", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Note createNote(		
			@RequestParam("id") int id,
			@RequestParam("fournisseur") String cinF,
			@RequestParam("demandeur") String cinD,
			@RequestParam("auteur") String s,
			@RequestParam("score") double score,
			@RequestParam("commentaire") String commentaire
		) throws Exception {
		
		ContactID contactID = new ContactID();
		contactID.setContact_id(id);
		contactID.setFournisseur_id(cinF);
		contactID.setDemandeur_id(cinD);
		DemandeContact contact = habitatService.findOne(contactID);
		
		Note note = new Note();
		note.setContact(contact);
		note.setId(new NoteID());
		
		Personne p = new Personne();
		
		if(contact.getFournisseurService().getCin().compareTo(s)==0)
		{
			note.setAutheur((Personne)contact.getFournisseurService());
			note.setSujet((Personne)contact.getDemandeurService());
			
			p = habitatService.findByCin(contact.getDemandeurService().getCin());
			p.setCin(contact.getDemandeurService().getCin());
			double res = (p.getScore()*p.getEtoile() + score)/(p.getScore()+1);
			p.setScore(p.getScore()+1);
			p.setEtoile(res);
		}else
		{
			note.setSujet((Personne)contact.getFournisseurService());
			note.setAutheur((Personne)contact.getDemandeurService());
			p = habitatService.findByCin(contact.getFournisseurService().getCin());
			p.setCin(contact.getFournisseurService().getCin());
			double res = (p.getScore()*p.getEtoile() + score)/(p.getScore()+1);
			p.setScore(p.getScore()+1);
			p.setEtoile(res);
			//habitatService.updateScore(p);
		}
		
		note.setScore(score);
		note.setCommentaire(commentaire);
		return habitatService.ajouterNoteAndUpdatePersonne(note, p);
		
	}
	@RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<Note> all() throws Exception {
		return habitatService.findAllNote();
		
	}
	@CrossOrigin
	@RequestMapping(value = "/getNotesBySujet", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<Note> getNotesBySujet(@RequestParam("cin") String cin) throws Exception {
				
		return habitatService.findBySujet(cin);
		
	}
	
	
}
