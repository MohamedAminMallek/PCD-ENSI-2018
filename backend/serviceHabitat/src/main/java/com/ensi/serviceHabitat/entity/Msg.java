package com.ensi.serviceHabitat.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Message")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Message")
public class Msg implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@AttributeOverrides({
		
	      @AttributeOverride(name="message_id",column = @Column(name="message_id")),
	      @AttributeOverride(name="emetteur_id", column = @Column(name="personne_id"))
	})
	@EmbeddedId
	private MessageID id;
	@MapsId("emetteur_id")
	@JoinColumn(name="emetteur_id")
	@ManyToOne(targetEntity = Personne.class,fetch=FetchType.EAGER)
	@JsonBackReference
	private Personne emetteur;
	private String titre;
	private String description;
	
	
	
	
	
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
	public MessageID getId() {
		return id;
	}
	public void setId(MessageID id) {
		this.id = id;
	}
	public Personne getEmetteur() {
		return emetteur;
	}
	public void setEmetteur(Personne emetteur) {
		this.emetteur = emetteur;
	}
	
	
	
	
	

}
