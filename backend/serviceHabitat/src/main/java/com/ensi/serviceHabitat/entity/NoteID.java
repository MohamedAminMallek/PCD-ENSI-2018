package com.ensi.serviceHabitat.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NoteID implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContactID contact_id;
	private String autheur;
	private String sujet;
	public ContactID getContact_id() {
		return contact_id;
	}
	public void setContact_id(ContactID contact_id) {
		this.contact_id = contact_id;
	}
	public String getAuteur() {
		return autheur;
	}
	public void setAuteur(String auteur) {
		this.autheur = auteur;
	}
	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autheur == null) ? 0 : autheur.hashCode());
		result = prime * result + ((contact_id == null) ? 0 : contact_id.hashCode());
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
		NoteID other = (NoteID) obj;
		if (autheur == null) {
			if (other.autheur != null)
				return false;
		} else if (!autheur.equals(other.autheur))
			return false;
		if (contact_id == null) {
			if (other.contact_id != null)
				return false;
		} else if (!contact_id.equals(other.contact_id))
			return false;
		if (sujet == null) {
			if (other.sujet != null)
				return false;
		} else if (!sujet.equals(other.sujet))
			return false;
		return true;
	}
	
	

}
