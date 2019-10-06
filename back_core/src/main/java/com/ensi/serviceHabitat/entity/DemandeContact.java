package com.ensi.serviceHabitat.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Demande_Contact")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Demande_Contact")
public class DemandeContact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1528L;
	
	@AttributeOverrides({
	      @AttributeOverride(name="contact_id", column = @Column(name="contact_id")),
	      @AttributeOverride(name="demandeur_id", column = @Column(name="demandeur_id",length=10)),
	      @AttributeOverride(name="founisseur_id", column = @Column(name="fournisseur_id",length=10))
	})
	@EmbeddedId
	private ContactID id;
	
	
	@MapsId("fournisseur_id")
	@JoinColumn(name="fournisseur_id",columnDefinition="varchar(10)")
	@ManyToOne(targetEntity = FournisseurService.class,fetch=FetchType.EAGER ,cascade = CascadeType.ALL)
	@JsonBackReference
	private FournisseurService fournisseurService;
	
	
	@MapsId("demandeur_id")
	@JoinColumn(name="demandeur_id",columnDefinition="varchar(10)",referencedColumnName="personne_id")
	@ManyToOne(targetEntity = DemandeurService.class,fetch=FetchType.EAGER ,cascade = CascadeType.ALL)
	@JsonBackReference
	private DemandeurService demandeurService;
	
	private String titre;
	private String description;
	private Date date;
	@Enumerated(EnumType.ORDINAL)
	@Column(columnDefinition="varchar(25)")
	private String etat;
	
	public DemandeContact() {
		super();
	}
	
	public DemandeContact(ContactID id, DemandeurService demandeurService, FournisseurService fournisseurService,
			String titre, String description, Date date, String etat) {
		super();
		this.id = id;
		this.demandeurService = demandeurService;
		this.fournisseurService = fournisseurService;
		this.titre = titre;
		this.description = description;
		this.date = date;
		this.etat = etat;
	}

	public ContactID getId() {
		return id;
	}

	public void setId(ContactID id) {
		this.id = id;
	}

	public DemandeurService getDemandeurService() {
		return demandeurService;
	}
	public void setDemandeurService(DemandeurService demandeurService) {
		this.demandeurService = demandeurService;
	}
	public FournisseurService getFournisseurService() {
		return fournisseurService;
	}
	public void setFournisseurService(FournisseurService fournisseurService) {
		this.fournisseurService = fournisseurService;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	
	
}
