package com.ensi.serviceHabitat.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Note")
public class Note implements Serializable {

	/**
	 * Mallek Mohamed Amin
	 */
	private static final long serialVersionUID = 791L;
	
	
	@AttributeOverrides({
	      @AttributeOverride(name="contact_id", column = @Column(name="contact_id")),
	      @AttributeOverride(name="autheur", column = @Column(name="autheur",length=10)),
	      @AttributeOverride(name="sujet", column = @Column(name="sujet",length=10))
	})
	@EmbeddedId
	private NoteID id;
	
	
	@MapsId("autheur")
	@JoinColumn(name="autheur")
	@ManyToOne(targetEntity = Personne.class,fetch=FetchType.EAGER ,cascade = CascadeType.ALL)
	private Personne autheur;
	
	
	@MapsId("sujet")
	@JoinColumn(name="sujet")
	@ManyToOne(targetEntity = Personne.class,fetch=FetchType.EAGER ,cascade = CascadeType.ALL)
	private Personne sujet;
	
	@MapsId("contact_id")
	@JoinColumns({
		@JoinColumn(name="contact_id", referencedColumnName="contact_id"),
		@JoinColumn(name="demandeur_id", referencedColumnName="demandeur_id"),
		@JoinColumn(name="fournisseur_id", referencedColumnName="fournisseur_id")
		
	})
	@ManyToOne(targetEntity = DemandeContact.class,fetch=FetchType.EAGER ,cascade = CascadeType.ALL)
	private DemandeContact contact;
	
	
	
	
	
	public Note(String commentaire) {
		super();
		this.commentaire = commentaire;
	}


	private double score;
	
	private String commentaire;
	
	
	public Note() {
		super();
	}

	
	public Personne getAutheur() {
		return autheur;
	}


	public void setAutheur(Personne autheur) {
		this.autheur = autheur;
	}


	public Personne getSujet() {
		return sujet;
	}


	public void setSujet(Personne sujet) {
		this.sujet = sujet;
	}


	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}


	public String getCommentaire() {
		return commentaire;
	}


	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public DemandeContact getContact() {
		return contact;
	}


	public void setContact(DemandeContact contact) {
		this.contact = contact;
	}

	public Note(Personne autheur, Personne sujet, DemandeContact contact, int score, String commentaire) {
		super();
		this.autheur = autheur;
		this.sujet = sujet;
		this.contact = contact;
		this.score = score;
		this.commentaire = commentaire;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autheur == null) ? 0 : autheur.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((sujet == null) ? 0 : sujet.hashCode());
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
		Note other = (Note) obj;
		if (autheur == null) {
			if (other.autheur != null)
				return false;
		} else if (!autheur.equals(other.autheur))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (sujet == null) {
			if (other.sujet != null)
				return false;
		} else if (!sujet.equals(other.sujet))
			return false;
		return true;
	}


	public NoteID getId() {
		return id;
	}


	public void setId(NoteID id) {
		this.id = id;
	}
	
	
	
	
}
