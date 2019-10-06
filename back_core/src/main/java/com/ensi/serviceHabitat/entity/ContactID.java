package com.ensi.serviceHabitat.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

@Embeddable
public class ContactID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	int contact_id;
	String demandeur_id;
	String fournisseur_id;
	
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
	public String getDemandeur_id() {
		return demandeur_id;
	}
	public void setDemandeur_id(String demandeur_id) {
		this.demandeur_id = demandeur_id;
	}
	public String getFournisseur_id() {
		return fournisseur_id;
	}
	public void setFournisseur_id(String fournisseur_id) {
		this.fournisseur_id = fournisseur_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contact_id;
		result = prime * result + ((demandeur_id == null) ? 0 : demandeur_id.hashCode());
		result = prime * result + ((fournisseur_id == null) ? 0 : fournisseur_id.hashCode());
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
		ContactID other = (ContactID) obj;
		if (contact_id != other.contact_id)
			return false;
		if (demandeur_id == null) {
			if (other.demandeur_id != null)
				return false;
		} else if (!demandeur_id.equals(other.demandeur_id))
			return false;
		if (fournisseur_id == null) {
			if (other.fournisseur_id != null)
				return false;
		} else if (!fournisseur_id.equals(other.fournisseur_id))
			return false;
		return true;
	}
	
	
	
	

}
