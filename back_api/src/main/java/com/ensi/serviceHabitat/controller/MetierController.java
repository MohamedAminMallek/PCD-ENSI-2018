package com.ensi.serviceHabitat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensi.serviceHabitat.entity.Metier;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;


@RestController
@RequestMapping("/metier")
public class MetierController {
	@Autowired
	private ServiceHabitatServiceImpl habitatService;

	@CrossOrigin
	@RequestMapping(value = "/ajouterMetier", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Metier ajouterMetier(@RequestParam("titre") String t,@RequestParam("description") String d) throws Exception {
		if (habitatService.findByTitre(t)==null) {
			Metier metier = new Metier();
			metier.setDescription(d);
			metier.setTitre(t);
			return habitatService.saveAndFlush(metier);
		}
		else return null;
		
	}
	@CrossOrigin
	@RequestMapping(value = "/consulter", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public java.util.List<Metier> findAllMsg() throws Exception {
		return habitatService.findAllMetier();
	}
	
		
	
}
