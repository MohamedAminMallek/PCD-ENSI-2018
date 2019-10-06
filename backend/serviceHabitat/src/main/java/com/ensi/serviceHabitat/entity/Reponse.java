package com.ensi.serviceHabitat.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Reponse")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Reponse")

public class Reponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ReponseID id;
	@MapsId("messgae_id")
    @JoinColumns({
        @JoinColumn(name="emetteur_id", referencedColumnName="emetteur_id"),
        @JoinColumn(name="message_id", referencedColumnName="message_id")
    })
    @OneToOne(targetEntity = Msg.class,fetch=FetchType.EAGER)
	@JsonBackReference
    private Msg message;
	private String titre;
	private String description;
	
	public Reponse()
	{
		
	}
	
	public Reponse(String titre, String description) {
		super();
		this.titre = titre;
		this.description = description;
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
	public ReponseID getId() {
		return id;
	}
	public void setId(ReponseID id) {
		this.id = id;
	}
	public Msg getMessage() {
		return message;
	}
	public void setMessage(Msg message) {
		this.message = message;
	}
	
	
}

