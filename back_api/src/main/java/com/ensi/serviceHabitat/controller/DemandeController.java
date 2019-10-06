package com.ensi.serviceHabitat.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.ensi.serviceHabitat.entity.DemandeurService;
import com.ensi.serviceHabitat.entity.EtatDemande;
import com.ensi.serviceHabitat.entity.FournisseurService;
import com.ensi.serviceHabitat.entity.Note;
import com.ensi.serviceHabitat.entity.NoteID;
import com.ensi.serviceHabitat.entity.Reponse;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;


@RestController
@RequestMapping("/demande")
public class DemandeController {
	@Autowired
	private ServiceHabitatServiceImpl habitatService;

	@RequestMapping(value = "/ajouterDemande", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public DemandeContact createEsme(@RequestBody final DemandeContact demandeContact) throws Exception {
		demandeContact.setEtat(EtatDemande.EnAttente.toString());
		return habitatService.saveAndFlush(demandeContact);
		
	}
	@CrossOrigin
	@RequestMapping(value = "/createDemande", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public DemandeContact addDemande(
			@RequestParam("fournisseur") String cinFrs,
			@RequestParam("demandeur") String cinDemds,
			@RequestParam("titre") String titre,
			@RequestParam("description") String description
								) throws Exception {
		
		DemandeurService demandeurService = habitatService.findOneDemandeurService(cinDemds);
		FournisseurService fournisseurService = habitatService.findOneFournisseurService(cinFrs);
		DemandeContact contact = new DemandeContact();
		contact.setDemandeurService(demandeurService);
		contact.setFournisseurService(fournisseurService);
		contact.setEtat(EtatDemande.EnAttente.toString());
		contact.setTitre(titre);
		contact.setDescription(description);
		Date date = new Date();
		
		contact.setDate(date);
		
		contact.setId(new ContactID());
		return habitatService.saveAndFlush(contact);
				
	}
	@RequestMapping(value = "/supprimerDemande", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public DemandeContact delete(@RequestBody final DemandeContact testHBC) throws Exception {
		if (habitatService.findOne(testHBC.getId())==testHBC) {
			habitatService.delete(testHBC);
			return(testHBC);
		}
		else {
			return(null);
		}
	}
	
	
	@CrossOrigin
	@RequestMapping(value = "/repondreDemande", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public DemandeContact repondre(
			@RequestParam("id") int id,
			@RequestParam("fournisseur") String cinF,
			@RequestParam("demandeur") String cinD,
			@RequestParam("etat") String Etat) throws Exception {		
		ContactID contactID = new ContactID();
		contactID.setContact_id(id);
		contactID.setFournisseur_id(cinF);
		contactID.setDemandeur_id(cinD);
		DemandeContact contact = habitatService.findOne(contactID);
		contact.setEtat(Etat);
		habitatService.saveAndFlush(contact);
		
		
		String msg = "Mr. "+contact.getFournisseurService().getNom()+" "+contact.getFournisseurService().getPrenom()+" a accepter votre demande. \n Voici son numero "+
		contact.getFournisseurService().getTel()+" Contactez le";
		
		if(Etat.compareTo("Accepter")==0)
		{
			msg = "Le+fournisseur+de+service+"+contact.getFournisseurService().getNom()+"+"+contact.getFournisseurService().getPrenom()+"+a+accepté+votre+demande.+Voici+son+NUMERO+=+"+contact.getFournisseurService().getTel()+"+.Vous+pouvez+le+contacter+maintenant.+++++++";
			
			msg.replace(" ", "+");
			String src = msg;
			StringBuffer result = new StringBuffer();
	        if(src!=null && src.length()!=0) {
	            int index = -1;
	            char c = (char)0;
	            String chars= "àâäéèêëîïôöùûüç";
	            String replace= "aaaeeeeiioouuuc";
	            for(int i=0; i<src.length(); i++) {
	                c = src.charAt(i);
	                if( (index=chars.indexOf(c))!=-1 )
	                    result.append(replace.charAt(index));
	                else
	                    result.append(c);
	            }
	        }
	        msg = result.toString();
			
			try{
				URL oracle = new URL("https://rest.nexmo.com/sms/json?api_key=71eabd6c&api_secret=38a0985bdd2f6db9&from=Application_PCD&to=21650474932&text="+msg+"");
		        URLConnection yc = oracle.openConnection();
		        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		        String inputLine;
		        while ((inputLine = in.readLine()) != null) 
		            System.out.println(inputLine);
		        in.close();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			
			
		}
		
		
		return(contact);
	}
	



	@RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<DemandeContact> test() throws Exception {
		return habitatService.findAllDemandeContact();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/demandesbyfrs", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<DemandeView> findDemandeContactBYfrs(@RequestParam("cin") String cin) throws Exception {
		List<DemandeContact> demandeContacts = habitatService.findByFrsAndEtat(habitatService.findOneFournisseurService(cin), "EnAttente");
		List<DemandeView> demandeViews  = new ArrayList<>();
		for(DemandeContact d : demandeContacts)
		{
			DemandeView demandeView = new DemandeView();
			demandeView.setContact(d);
			demandeView.setPositionGps(d.getDemandeurService().getPositionGps());
			demandeView.setFullName(d.getDemandeurService().getNom()+" "+d.getDemandeurService().getPrenom());
			demandeView.setScore(d.getDemandeurService().getScore());
			demandeView.setEtoile(d.getDemandeurService().getEtoile());
			demandeViews.add(demandeView);
		}
		
		return demandeViews;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/demandesbydemandeur", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<DemandeView> findDemandeContactBYdemandeur(@RequestParam("cin") String cin) throws Exception {
		
		
		List<DemandeContact> demandeContacts = habitatService.findByDemandeurAndEtat(habitatService.findOneDemandeurService(cin), "EnAttente");
		
		List<DemandeView> demandeViews  = new ArrayList<>();
		for(DemandeContact d : demandeContacts)
		{
			DemandeView demandeView = new DemandeView();
			demandeView.setContact(d);
			demandeView.setPositionGps(d.getFournisseurService().getPositionGps());
			demandeView.setFullName(d.getFournisseurService().getPositionGps());
			demandeView.setScore(d.getFournisseurService().getScore());
			demandeView.setEtoile(d.getFournisseurService().getEtoile());
			
			demandeViews.add(demandeView);
		}
		
		return demandeViews;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/demandesAccepter", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<DemandeView> findDemandeContactAccepter(@RequestParam("cin") String cin,@RequestParam("isFRS") boolean frs,@RequestParam("isDemandeur") boolean demandeur) throws Exception {
		
		List<DemandeView> demandeViews  = new ArrayList<>();
		
		if(demandeur) {
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
				boolean nb = habitatService.exist(id);
				
				if(!nb) {
					DemandeView demandeView = new DemandeView();
					demandeView.setContact(d);
					demandeView.setPositionGps(d.getFournisseurService().getPositionGps());
					demandeViews.add(demandeView);
				}
			}
		}
		if(frs)
		{
			
			List<DemandeContact> demandeContacts = habitatService.findByFrsAndEtat(habitatService.findOneFournisseurService(cin), "Accepter");
			
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
				boolean nb = habitatService.exist(id);
				
				if(!nb) {
					DemandeView demandeView = new DemandeView();
					demandeView.setContact(d);
					demandeView.setPositionGps(d.getDemandeurService().getPositionGps());
					demandeViews.add(demandeView);
				}
			}
			
		}
		return demandeViews;
	}
	
}
class DemandeView
{
	private DemandeContact contact;
	private String positionGps;
	private String fullName;
	private int score;
	private double etoile;
	
	
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public double getEtoile() {
		return etoile;
	}
	public void setEtoile(double etoile) {
		this.etoile = etoile;
	}
	public DemandeContact getContact() {
		return contact;
	}
	public void setContact(DemandeContact contact) {
		this.contact = contact;
	}
	public String getPositionGps() {
		return positionGps;
	}
	public void setPositionGps(String positionGps) {
		this.positionGps = positionGps;
	}
	
}