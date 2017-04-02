package com.atithi.rest.services.impl;

import java.sql.Connection;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.atithi.db.DBUtil;
import com.atithi.db.MongoManager;
import com.atithi.db.MongoManager.Collections;
import com.atithi.gcm.GCMClient;
import com.atithi.rest.dto.Customer;
import com.atithi.rest.dto.UserDetails;
import com.atithi.rest.services.DBService;

public class DBServiceImpl implements DBService{

	@Override
	public boolean postUser(UserDetails userDetails) {
		// TODO Auto-generated method stub		
		MongoManager manager = new MongoManager();
		return manager.store(userDetails, MongoManager.Collections.USER_DETAILS);				
	}

	@Override
	public UserDetails getUser(UserDetails userDetails) {
		
	//We are not planning to expose this methode for now
		throw new UnsupportedOperationException("NOT SUPPORTED IN v1.0.0");
	}
	
	@Override
	public boolean updateUser(final UserDetails userDetails) {
		// TODO Auto-generated method stub
		boolean result= true;
		MongoManager manager = new MongoManager();
		manager.update(userDetails, Collections.USER_DETAILS);
		return result;
	}

	//Registration Service
	@Override
	public Response postCustomer(Customer custDetails) {
		// TODO Auto-generated method stub
		System.out.println("Reached POSTCUSTOMER METHOD");
		MongoManager manager = new MongoManager();
		// It means user has been authenticated internally if true
	    boolean result = manager.store(custDetails, MongoManager.Collections.Customer);
	    
	    if(!result) {
	    //if not validated then return
         return Response.status(Status.UNAUTHORIZED).build();	
        }
	   //if registration is successful send user a token for next operation
	    /*
	     * Send Notification
	     * GARBAGE CODE REMOVE IT ASAP
	     * 
	     * */
	    /* 
	    final GCMClient client = new GCMClient();
	    client.connect();
	    final List<String> tokens= DBUtil.getTokensFromUserName(custDetails.getName());
	    System.out.println("TOKEN IS-------->"+tokens.get(0));
	    client.send(tokens.get(0),);
	    */
	    return Response.ok(GCMClient.SENDER_ID,MediaType.TEXT_PLAIN).build();
	}

	@Override
	public void getCustomer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomer(Customer cust) {
		// TODO Auto-generated method stub
		MongoManager manager = new MongoManager();
		manager.update(cust, Collections.Customer);		
	}
	
	/*public static void main(String[] args) {
		
		
		
	}*/
}
