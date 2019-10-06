package com.ensi.serviceHabitat.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.hibernate.annotations.GenericGenerator;

@Embeddable
public class MessageID implements Serializable{
	String emetteur_id;
	@GeneratedValue(strategy=GenerationType.AUTO)
	long message_id;
	public String getEmetteur_id() {
		return emetteur_id;
	}
	public void setEmetteur_id(String emetteur_id) {
		this.emetteur_id = emetteur_id;
	}
	
	public long getMessage_id() {
		return message_id;
	}
	public void setMessage_id(long message_id) {
		this.message_id = message_id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageID other = (MessageID) obj;
		if (emetteur_id != other.emetteur_id)
			return false;
		if (message_id != other.message_id)
			return false;
		return true;
	}
	
	
}
