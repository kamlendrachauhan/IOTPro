package com.atithi.db;

import com.atithi.rest.dto.Customer;

public class CustomerMapper {

	public static void update(Customer original, Customer newer) {
		
		if(!original.getPass().equals(newer.getPass())) {
			
			original.setPass(newer.getPass());
		}
		if(original.getToken()==null || 
				!(original.getToken().equals(newer.getToken())) ) {
			original.setToken(newer.getToken());
		}
		
	}
	
}
