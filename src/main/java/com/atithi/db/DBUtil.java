package com.atithi.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.atithi.db.MongoManager.Collections;
import com.atithi.rest.dto.Customer;
import com.atithi.rest.dto.UserDetails;

public class DBUtil {
	
	public static List<String> getTokensFromUserName(final String userName) {
		
		final MongoManager mongoManager = new MongoManager();
		//get User ID Object
		final List<String> userNameList = new ArrayList<String>(); 
		userNameList.add(userName);
		//Got the userDetail object
		final List<Object> userDetailsList = mongoManager.get(userNameList, Collections.USER_DETAILS);
		final List<String> customerIDList = getCustomerIdListfromUserDetails(userDetailsList);
	    final List<String> tokens = getTokensfromCustomerIDs(customerIDList);
		System.out.println("first Token is----->"+tokens.get(0));
	    return tokens;
	}
	
	private static List<String> getCustomerIdListfromUserDetails(final List<Object> userDetailList) {
		final List<String> customerIDList = new ArrayList<String>();
		final Set<String> union = new HashSet<String>();
		if(userDetailList!=null && !userDetailList.isEmpty()) {
		       	
			for (Object obj : userDetailList) {
				
				final UserDetails userDetail= (UserDetails)obj;
				final String[] custIDs = userDetail.getCustID();
				union.addAll(Arrays.asList(custIDs));
				
			}
						
		}
		
		customerIDList.addAll(union);		
		return customerIDList;
	}
	
	
	private static List<String> getTokensfromCustomerIDs(final List<String> customerIDs) {
		MongoManager manager = new MongoManager();
		final List<String> tokens = new ArrayList<String>();
		final List<Object>customers = manager.get(customerIDs, Collections.Customer);
		for (final Object object : customers) {
			
			final Customer customer =(Customer)object; 
			tokens.add(customer.getToken());
		}
		
		return tokens;
	}

//	public static Connection getConnection() throws NamingException{
//		Connection connection = null;
//		final Context context = new InitialContext();
//		final Context envContext = (Context) context.lookup("java:/comp/env");
//		final DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle"); 
//		try {
//			connection = ds.getConnection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return connection;
//	}
//	
//	public static void storeUserDetails(final Connection conn, final UserDetails user) {
//		
//		
//		
//	}
	
	
	
}
