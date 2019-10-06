package com.ensi.serviceHabitat.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="Personne")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "Personne")
public class Personne implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="personne_id",length=10)
	private String cin;
	private String nom;
	private String prenom;
	private int tel;
	private Date dateNaissance = new Date();
	private String adresse;
	private String positionGps;
	private int score;
	private double etoile;
	private byte[] photo;
	private String userName;
	private String passWord;
	private Date bannedUntil = new Date();
	
	/*@OneToMany(targetEntity=Msg.class,mappedBy = "emetteur",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonManagedReference
	private Set<Msg> messages = new HashSet<Msg>();
	*/
	@OneToMany(targetEntity=PropositionMetier.class,mappedBy="emetteur",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<PropositionMetier> propositionMetiers = new HashSet<>();
	
	
	
	public double getEtoile() {
		return etoile;
	}
	public void setEtoile(double etoile) {
		this.etoile = etoile;
	}
	public Personne() {
		super();
	}
	public Personne(String cin, String nom, String prenom, int tel, Date dateNaissance, String adresse,
			String positionGps, int score, byte[] photo,String userName,String password,Date banned,double etoile) {
		super();
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.positionGps = positionGps;
		this.score = score;
		this.photo = photo;
		this.userName = userName;
		this.passWord = password;
		this.bannedUntil = banned;
		this.etoile = etoile;
	}
	
	
	
	public Set<PropositionMetier> getPropositionMetiers() {
		return propositionMetiers;
	}
	public void setPropositionMetiers(Set<PropositionMetier> propositionMetiers) {
		this.propositionMetiers = propositionMetiers;
	}
	public Date getBannedUntil() {
		return bannedUntil;
	}
	public void setBannedUntil(Date bannedUntil) {
		this.bannedUntil = bannedUntil;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	/*
	public Set<Msg> getMessages() {
		return messages;
	}
	public void setMessages(Set<Msg> messages) {
		this.messages = messages;
	}
	*/
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getPositionGps() {
		return positionGps;
	}
	public void setPositionGps(String positionGps) {
		this.positionGps = positionGps;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cin == null) ? 0 : cin.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personne other = (Personne) obj;
		if (cin == null) {
			if (other.cin != null)
				return false;
		} else if (!cin.equals(other.cin))
			return false;
		return true;
	}
	
	
	
}
