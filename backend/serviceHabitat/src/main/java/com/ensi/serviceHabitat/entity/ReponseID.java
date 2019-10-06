package com.ensi.serviceHabitat.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class ReponseID implements Serializable{
	
	MessageID messgae_id;
	@GeneratedValue(strategy=GenerationType.AUTO)
	int reponse_id;
	
	
	
	public MessageID getMessgae_id() {
		return messgae_id;
	}
	public void setMessgae_id(MessageID messgae_id) {
		this.messgae_id = messgae_id;
	}
	public int getReponse_id() {
		return reponse_id;
	}
	public void setReponse_id(int reponse_id) {
		this.reponse_id = reponse_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messgae_id == null) ? 0 : messgae_id.hashCode());
		result = prime * result + reponse_id;
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
		ReponseID other = (ReponseID) obj;
		if (messgae_id == null) {
			if (other.messgae_id != null)
				return false;
		} else if (!messgae_id.equals(other.messgae_id))
			return false;
		if (reponse_id != other.reponse_id)
			return false;
		return true;
	}
	

}
