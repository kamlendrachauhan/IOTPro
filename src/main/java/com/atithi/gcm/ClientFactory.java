package com.atithi.gcm;

import java.util.HashMap;
import java.util.Map;

public class ClientFactory {
	
	private ClientFactory () {};
	
	private static ClientFactory factoryInstance;
	
	public static ClientFactory getInstance() {
		
		if(factoryInstance==null) {
		 factoryInstance = new ClientFactory();
		}
		return factoryInstance;
	}
	
	public static final Map<String,Object> FACTORYREPO = new HashMap<String, Object>();
	
	static {
		
		
		 
		FACTORYREPO.put(GCMClient.class.getName(),new GCMClient());
		
	}
	
	
	
	
}
