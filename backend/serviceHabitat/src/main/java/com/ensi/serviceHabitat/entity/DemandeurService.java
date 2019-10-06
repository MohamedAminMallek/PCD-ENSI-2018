package com.ensi.serviceHabitat.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="Demandeur_Service")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Demandeur_Service")
public class DemandeurService extends Personne implements Serializable {

	private static final long serialVersionUID = 1L;
	private String profession;
	@OneToMany(targetEntity=DemandeContact.class,mappedBy = "demandeurService", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<DemandeContact> lesDemandes = new HashSet<>();
	
	
	public DemandeurService() {
		super();
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public Set<DemandeContact> getLesDemandes() {
		return lesDemandes;
	}
	public void setLesDemandes(Set<DemandeContact> lesDemandes) {
		this.lesDemandes = lesDemandes;
	}
	
	
}
