package com.ensi.serviceHabitat.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensi.serviceHabitat.entity.Administrateur;
import com.ensi.serviceHabitat.entity.DemandeurService;
import com.ensi.serviceHabitat.entity.FournisseurService;
import com.ensi.serviceHabitat.entity.Metier;
import com.ensi.serviceHabitat.entity.Personne;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private ServiceHabitatServiceImpl habitatServiceImpl;

	@CrossOrigin
	@RequestMapping(value = "/createPersonne", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Personne createPersonne(@RequestBody final Personne personne) throws Exception {
		
		
		Personne p = habitatServiceImpl.findOnePersonne(personne.getCin());
		//p.setBannedUntil(new Date());
		
		if(p == null)
			//if(habitatServiceImpl.authentification(p.getUserName(), p.getPassWord())!=null)
				//return null;
			//else
				return habitatServiceImpl.saveAndFlush(personne);
		else
			return null;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/createDemandeur", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public DemandeurService createDemandeur(@RequestParam("cin") String cin,@RequestParam("profession") String profession) throws Exception {
		
		DemandeurService d = habitatServiceImpl.findOneDemandeurService(cin);
		if(d == null) {
			
			habitatServiceImpl.inscriptionDemandeur(cin, profession);
			return habitatServiceImpl.findOneDemandeurService(cin);
		}else
			return null;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/createFournisseur", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public FournisseurService createDemandeur(@RequestParam("cin") String cin,@RequestParam("id_metier") Long id_metier) throws Exception {
		
		FournisseurService d = habitatServiceImpl.findOneFournisseurService(cin);
		if(d == null) {
			
			FournisseurService f = new FournisseurService();
			f.setCin(cin);
			f.setDisponibilite(true);
			f.setMetier(habitatServiceImpl.findOneMetier(id_metier));
			habitatServiceImpl.inscription(f);
			return habitatServiceImpl.findOneFournisseurService(cin);
		}else
			return null;
	}
	@CrossOrigin
	@RequestMapping(value = "authentification", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public Personne authentification(@RequestParam("username") String username,@RequestParam("password")String password) throws Exception {
		
		Date toDay = new Date();
		Personne p = habitatServiceImpl.authentification(username, password);
		System.out.println(p.getBannedUntil().compareTo(toDay));
		/*if(p.getBannedUntil().compareTo(toDay)>0)
			return null;
		*/
		return p;
	}
	
	@CrossOrigin
	@RequestMapping(value = "userByCin", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public Personne userByCin(@RequestParam("cin") String cin	) throws Exception {
		return habitatServiceImpl.findByCin(cin);
	}
	
	@CrossOrigin
	@RequestMapping(value = "bannir", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Personne bannir(@RequestParam("cin") String cin,@RequestParam("jours") int jours) throws Exception {
		
		Personne personne = habitatServiceImpl.findByCin(cin);
			
		habitatServiceImpl.bannir(personne, jours);
		return personne;
	}
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public java.util.List<Personne> findAll() throws Exception {
		return habitatServiceImpl.findAllPersonne();
	}
	
	@CrossOrigin
	@RequestMapping(value = "listFournisseur", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public java.util.List<FournisseurService> findFrsByMetier(@RequestParam("id") long IDmetier) throws Exception {
		
		return habitatServiceImpl.findByMetier(habitatServiceImpl.findOneMetier(IDmetier));
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "fournisseur", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public FournisseurService findFrsByCin(@RequestParam("cin") String cin) throws Exception {
		
		return habitatServiceImpl.findOneFournisseurService(cin);
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "demandeur", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public DemandeurService findDemandeurByCin(@RequestParam("cin") String cin) throws Exception {
		
		return habitatServiceImpl.findOneDemandeurService(cin);
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "updateFrs", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public FournisseurService updatefrs(@RequestParam("cin") String cin,@RequestParam("disponibilite") boolean dis) throws Exception {
		
		FournisseurService frs = habitatServiceImpl.findOneFournisseurService(cin);
		frs.setDisponibilite(dis);
		habitatServiceImpl.updateDis(cin, dis);
		return frs;
	}
	

}
