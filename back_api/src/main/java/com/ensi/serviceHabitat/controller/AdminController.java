package com.ensi.serviceHabitat.controller;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ensi.serviceHabitat.entity.Administrateur;
import com.ensi.serviceHabitat.service.ServiceHabitatServiceImpl;


@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ServiceHabitatServiceImpl habitatService;

	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Administrateur createEsme(@RequestBody final Administrateur testHBC) throws Exception {
		return habitatService.saveAndFlush(testHBC);
	}

	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public java.util.List<Administrateur> findEsmeByName() throws Exception {
		return habitatService.findAll();
	}
	@CrossOrigin
	@RequestMapping(value = "/authentification", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Administrateur authentification(@RequestBody final Administrateur testHBC) throws Exception {
		String pw =testHBC.getPassWord();
		String un =testHBC.getUserName();
		if (habitatService.findOne(un, pw)==null) {
			return (null);
		}
		else {
			return (testHBC);
		}
	}
	
	
}
