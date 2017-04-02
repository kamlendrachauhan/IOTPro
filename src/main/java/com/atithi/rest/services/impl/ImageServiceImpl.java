package com.atithi.rest.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.atithi.db.DBUtil;
import com.atithi.db.MongoManager;
import com.atithi.gcm.GCMClient;
import com.atithi.rest.services.ImageService;

public class ImageServiceImpl implements ImageService {

	//
	private static final String SERVER_IP ="http://54.200.239.59:8080";
	//server Path
	public static final String PATH_TO_STORE ="/opt/apache-tomcat-8.0.33/webapps/ImageManager/imageStore/";
			//"E:/Sunbeam/apache-tomcat-8.0.33/webapps/ImageManager/imageStore/";
			//
			
			
	public static final String NOTIFICATION_URL_BASE =SERVER_IP+"/ImageManager/imageStore/";
			//"E:/Sunbeam/apache-tomcat-8.0.33/webapps/ImageManager/imageStore/";//
	
    @Override
	public Response storeImage(final InputStream istream,final String fileName, final String userID) throws IOException {
		// TODO Auto-generated method stub
		final String filepath = PATH_TO_STORE+fileName;
		writeFile(istream, filepath);
		
		final Response resp =Response.created(URI.create("/imageService/store/"+fileName)).build();
		//Call GCM Client
	    System.out.println("UserID----------> " + userID);
	    final String file_url = NOTIFICATION_URL_BASE+fileName; 
	    final List<String> tokens= DBUtil.getTokensFromUserName(userID);
		System.out.println("Got the token------>"+tokens.get(0));
	    //Call GCM Client with list of tokens
		final GCMClient client = new GCMClient();
		
		
		
		//try sending it to fist token
		//file url
		if(tokens!=null && !tokens.isEmpty()) {
			for (String string : tokens) {
				    client.connect();
					System.out.println("SHOULD SEND AT LEAST ONE MESSAGE");
					System.out.println("file URL-------->"+file_url);
					client.send(string, file_url);
				
			}
		}
		
		return resp;
	}
	
	public static void writeFile(InputStream istream, String filepath) throws IOException {
		final File file = new File(filepath);
		if(!file.createNewFile()) {
			throw new WebApplicationException("Unable To Create File");
		}
		final OutputStream ostream = new FileOutputStream(file);
		int read =0;
		byte[] bytes = new byte[1024];
		while((read=istream.read(bytes))!=-1) {
			
			ostream.write(bytes, 0, read);
		}
		
		ostream.flush();
		ostream.close();
		istream.close();
		System.out.println("-------------File has been Stored--------------");
	}
	
	

}
