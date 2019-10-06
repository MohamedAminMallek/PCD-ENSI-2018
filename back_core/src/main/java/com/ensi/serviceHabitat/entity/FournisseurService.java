package com.ensi.serviceHabitat.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="fournisseur_Service")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "fournisseur_Service")
public class FournisseurService extends Personne implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private boolean disponibilite;
	@ManyToOne(targetEntity = Metier.class,fetch=FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinColumn(name="id_metier")
	@JsonBackReference
	private Metier metier;
	@OneToMany(targetEntity=DemandeContact.class,mappedBy = "fournisseurService",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonManagedReference
	private Set<DemandeContact> demandeContacts = new HashSet<>();
	
	public FournisseurService() {
		super();
	}
	public FournisseurService(String cin, String nom, String prenom, int tel, Date dateNaissance, String adresse,
			String positionGps, int score, byte[] photo,Metier metier,boolean dispo,String username,String pass,Date banned,double etoile) {
		super(cin, nom, prenom, tel, dateNaissance, adresse, positionGps, score, photo,username,pass,banned,etoile);
		this.metier = metier;
		this.disponibilite = dispo;
	}
	
	public Set<DemandeContact> getDemandeContacts() {
		return demandeContacts;
	}
	public void setDemandeContacts(Set<DemandeContact> demandeContacts) {
		this.demandeContacts = demandeContacts;
	}
	public Metier getMetier() {
		return metier;
	}
	public void setMetier(Metier metier) {
		this.metier = metier;
	}
	public boolean isDisponibilite() {
		return disponibilite;
	}
	public void setDisponibilite(boolean disponibilite) {
		this.disponibilite = disponibilite;
	}
	
	
	
	
}
