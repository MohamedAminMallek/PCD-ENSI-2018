package com.ensi.serviceHabitat.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="Proposition_Metier")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Proposition_Metier")
public class PropositionMetier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@AttributeOverrides({
		
	      @AttributeOverride(name="id",column = @Column(name="id")),
	      @AttributeOverride(name="cin", column = @Column(name="cin"))
	})
	@EmbeddedId
	private PropositionMetierID id;
	@MapsId("cin")
	@JoinColumn(name="cin")
	@ManyToOne(targetEntity = Personne.class,fetch=FetchType.LAZY)
	@JsonBackReference
	private Personne emetteur;
	
	private String titre;
	private String description;
	private boolean reponse = false;
	public PropositionMetier() {
		super();
	}
	
	
	public boolean isReponse() {
		return reponse;
	}


	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PropositionMetier other = (PropositionMetier) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public PropositionMetierID getId() {
		return id;
	}


	public void setId(PropositionMetierID id) {
		this.id = id;
	}


	public Personne getEmetteur() {
		return emetteur;
	}


	public void setEmetteur(Personne emetteur) {
		this.emetteur = emetteur;
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
