package com.ensi.serviceHabitat.controller;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensi.serviceHabitat.entity.MessageID;
import com.ensi.serviceHabitat.entity.Msg;
import com.ensi.serviceHabitat.entity.Personne;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;


@RestController
@RequestMapping("/message")
public class MessageController {
	@Autowired
	private ServiceHabitatServiceImpl habitatService;

	
	@CrossOrigin
	@RequestMapping(value = "/ajouterMessage", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Msg ajouterMsg(@RequestParam("cin") String cin,@RequestParam("titre") String titre,@RequestParam("desc") String desc) throws Exception {
		
		Msg msg = new Msg();
		msg.setEmetteur(habitatService.findByCin(cin));
		msg.setTitre(titre);
		msg.setDescription(desc);
		msg.setId(new MessageID());
		return habitatService.saveAndFlush(msg);
		
		
	}
	@CrossOrigin
	@RequestMapping(value = "/consulterMessage", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<ImmutablePair<Msg, Personne>> findEsmeByName() throws Exception {
		
		ArrayList<ImmutablePair<Msg, Personne>> result = new ArrayList<>();
		for(Msg m : habitatService.getMsgWithOutResponse())
			result.add(new ImmutablePair<Msg, Personne>(m, m.getEmetteur()));
		return result;
	}
	
		
	
}
