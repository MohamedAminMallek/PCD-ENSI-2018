package com.ensi.serviceHabitat.controller;

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
import com.ensi.serviceHabitat.entity.Reponse;
import com.ensi.serviceHabitat.entity.ReponseID;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;


@RestController
@RequestMapping("/reponse")
public class ReponseController {
	@Autowired
	private ServiceHabitatServiceImpl habitatService;

	@RequestMapping(value = "/ajouterReponse", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void createEsme(@RequestBody final Reponse rp) throws Exception {
		
		habitatService.saveAndFlush(rp);
		
	}
	@CrossOrigin
	@RequestMapping(value = "/repondre", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void repondre(@RequestParam("message_id") long msgId,
							@RequestParam("emetteur_id") String cin,
							@RequestParam("description") String description,
							@RequestParam("titre") String titre) throws Exception {

		
		Reponse p = new Reponse();
		p.setDescription(description);
		p.setTitre(titre);
		ReponseID pp = new ReponseID();
		//pp.setReponse_id(1);
		MessageID mm = new MessageID();
		mm.setEmetteur_id(cin);
		mm.setMessage_id(msgId);
		pp.setMessgae_id(mm);
		p.setId(pp);
		habitatService.saveAndFlush(p);
		
		
		
		
		
	}

	@RequestMapping(value = "/consulterReponse", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public java.util.List<Reponse> getReponse(@RequestParam("cin") String cin) throws Exception {
		
		return habitatService.findReponseByCin(cin);
	}
	
		
	
}