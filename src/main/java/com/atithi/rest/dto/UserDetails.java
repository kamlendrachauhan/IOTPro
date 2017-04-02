package com.atithi.rest.dto;
import java.util.List;

import javax.xml.bind.annotation.*;

//import org.apache.cxf.jaxrs.ext.xml.XMLName;

@SuppressWarnings("restriction")
@XmlRootElement
public class UserDetails {
	//Can be Printed Version given to the Customer
	//int ID;
	//Default set to test later can be changed
	String name;
	
	//Default set to test later can be changed
	String pass;	
	//List of CustIDs for this RaspPi
	String[] custID;
	
	
	public String[] getCustID() {
		return custID;
	}
	public void setCustID(String[] custID) {
		this.custID = custID;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}*/
	
}
