package com.atithi.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class Customer {

//	int ID;
	String name;
	String pass;
	String token;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	//to maintain many to many relationship
	//Rasp-Pi details provided by customer
	
	@XmlElement(name="details")
	UserDetails details;
	
	String[] userIDs;
	
	public UserDetails getDetails() {
		return details;
	}
	public void setDetails(UserDetails details) {
		this.details = details;
	}
	
	public String[] getUserIDs() {
		return userIDs;
	}
	public void setUserIDs(String[] userIDs) {
		this.userIDs = userIDs;
	}
	
	/*public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
		
}
