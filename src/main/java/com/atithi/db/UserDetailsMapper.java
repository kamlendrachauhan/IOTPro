package com.atithi.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.atithi.rest.dto.UserDetails;

public class UserDetailsMapper {

	public static void update(UserDetails original, UserDetails updated) {
		 if(!original.getName().equals(updated.getName())) {
			 original.setName(updated.getName());
		 }
		 if(!original.getPass().equals(updated.getPass())) {
			 original.setName(updated.getName());
		 }
		 
		 HashSet<String> union  = new HashSet<String>();
		 if(original.getCustID()!=null) {
		   List<String> listOriginal = Arrays.asList(original.getCustID());
		   union.addAll(listOriginal);
		 }
		 
		 List<String> newTokens = Arrays.asList(updated.getCustID()); 
		 union.addAll(newTokens);
		 
		 final String[] unionArray= union.toArray(new String[union.size()]);
		 original.setCustID(unionArray);
		//return null;
	}
	
}
