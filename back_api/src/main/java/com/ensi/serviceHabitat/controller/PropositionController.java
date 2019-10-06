package com.ensi.serviceHabitat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensi.serviceHabitat.entity.Metier;
import com.ensi.serviceHabitat.entity.PropositionMetier;
import com.ensi.serviceHabitat.entity.PropositionMetierID;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;


@RestController
@RequestMapping("/proposition")
public class PropositionController {
	@Autowired
	private ServiceHabitatServiceImpl habitatService;

	@CrossOrigin
	@RequestMapping(value = "/ajouterProposition", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public PropositionMetier ajouterProposition(@RequestParam("cin") String cin,@RequestParam("titre") String titre,@RequestParam("description") String desc) throws Exception {
		
		PropositionMetier p = new PropositionMetier();
		p.setEmetteur(habitatService.findByCin(cin));
		p.setTitre(titre);
		p.setDescription(desc);
		p.setId(new PropositionMetierID());
		return habitatService.saveAndFlush(p);
		
	}

	@CrossOrigin
	@RequestMapping(value = "/accepter", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Metier accepter(@RequestBody final PropositionMetier proposition) throws Exception {
		System.out.println(proposition.getTitre());
		System.out.println(proposition.isReponse());
		System.out.println(proposition.getId().getCin());
		System.out.println(proposition.getId().getId());
		if (proposition.isReponse() && habitatService.findByTitre(proposition.getTitre())==null) {
			Metier m=new Metier();
			m.setTitre(proposition.getTitre());
			m.setDescription(proposition.getDescription());
			habitatService.saveAndFlush(m);
			proposition.setEmetteur(habitatService.findByCin(proposition.getId().getCin()));
			
			habitatService.delete(proposition);
			return m ;
		}
		else 
		{
			proposition.setEmetteur(habitatService.findByCin(proposition.getId().getCin()));
			habitatService.delete(proposition);
			return null;
		}
		
	}
	@CrossOrigin
	@RequestMapping(value = "/propositions",method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<PropositionMetier> getAllprops() throws Exception{
		
		return habitatService.findAllPropositionMetier();
		
	}
	
		
	
}