package com.ensi.serviceHabitat.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Metier")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Metier")
public class Metier implements Serializable {


	private static final long serialVersionUID = 1L;	
	@Id
	@Column(name="id_metier")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String titre;
	private String description;
	/*@OneToMany(targetEntity=FournisseurService.class,mappedBy="metier",fetch=FetchType.EAGER,cascade =  CascadeType.ALL)
	@JsonManagedReference
	private Set<FournisseurService> fournisseurServices;
	*/
	
	public Metier(Long id, String titre, String description) {
		super();
		this.id = id;
		this.titre = titre;
		this.description = description;
	}
	public Metier() {
		super();
	}
	
	/*
	public Set<FournisseurService> getFournisseurServices() {
		return fournisseurServices;
	}
	public void setFournisseurServices(Set<FournisseurService> fournisseurServices) {
		this.fournisseurServices = fournisseurServices;
	}
	*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
}
