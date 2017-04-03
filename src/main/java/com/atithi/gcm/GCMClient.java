package com.atithi.gcm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
//import org.jivesoftware.smack.PacketCollector.Configuration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.util.StringUtils;
import org.json.simple.JSONValue;

public class GCMClient {

	public static final String SENDER_ID ="627066340321";
	//Included in HTTP
	private static final String SERVER_API_KEY ="AAAAkgARA-E:APA91bFokq1Fs0UogZa4WNWLec7kouYmtltD0GqAEDimby-Q_SIoDEr24D77KccSG7HM6EcFxubP8Cjfe2oiX33FFzybww_-Ja5QKIHrgKIEEX4DWTx8issicXQ6pl0qV8zglXXQBzuh9t02umeOmlbrNy1f7_5T5g";
	
	//Whom to send
	//May be INSTANCE_ID
	//Ideally these elements should be in DB
	//Store Update and retrieve
	private static final String APPLICATION_ID = "";
	private static final String REGISTRATION_TOKENS ="";
	
	//this should be unique  to each XMPP message sent
	private static String messageID =""; 
	
	private static final String ENDPOINT_HOST ="gcm.googleapis.com";//"gcm-preprod.googleapis.com";
	private static final int ENDPOINT_HOST_PORT =5235;
	//Sender ID is required for the app server to GCM
	//connection: user@domain/resource      user@gcm-preprod.googleapis.com/865254607161
	//XML Stanzas <message> <presense> <iq>(get ot post request)
	//<message from='user@domain/resource' to="">
	//<body>some text</body>
	//</message>
	private static final String XMLNS_NAME = "google:mobile:data";
	private static final String TAG_NAME = "gcm";

	private static XMPPConnection xmppconnection;
	private static ConnectionConfiguration config;
	private static Random RANDOM = new Random();
	
	//connect function
	
	
	
	
	public static void connect () {
		
		if(config == null) {
		config = new ConnectionConfiguration(ENDPOINT_HOST, ENDPOINT_HOST_PORT);
		config.setSecurityMode(SecurityMode.disabled);
		config.setReconnectionAllowed(true);
		config.setRosterLoadedAtLogin(false);
		config.setSendPresence(false);
		config.setDebuggerEnabled(false);
		config.setSocketFactory(SSLSocketFactory.getDefault());
		}
		if(xmppconnection == null) {
		xmppconnection = new XMPPConnection(config);
		}
		
		try {
			if(!xmppconnection.isConnected()) {
			xmppconnection.connect();
		//Connection listener
			xmppconnection.addConnectionListener(new ConnectionListener() {
				@Override
				public void reconnectionSuccessful() {
					// TODO Auto-generated method stub
					System.out.println("Opened Connection :-)");
					
				}
				@Override
				public void reconnectionFailed(Exception paramException) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void reconnectingIn(int paramInt) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void connectionClosedOnError(Exception paramException) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void connectionClosed() {
					// TODO Auto-generated method stub
					System.out.println("Closed Connection :-(");
					
				}
			});
			//add Packet Listner
			xmppconnection.addPacketListener(new PacketListener() {
				
				@Override
				public void processPacket(Packet packet) {
					// TODO Auto-generated method stub
					//called when packets are sent Assumption
					System.out.println("*****Received a Packet*****");
					//System.out.println(packet.toXML());
					final Message message = (Message) packet;
					final PacketExtension extension = message.getExtension(XMLNS_NAME);
					String xml=extension.toXML();
					String name = extension.toString();
					
					System.out.println("----------->"+name);
					System.out.println("----------->"+xml);
				}
			}, new PacketTypeFilter(Message.class) 
			);

			}//end of if
			
		}
		catch(XMPPException ex){
			ex.printStackTrace();
		}
		
				
		try {
		if(!xmppconnection.isAuthenticated())//changed
		xmppconnection.login(SENDER_ID+"@"+ENDPOINT_HOST, SERVER_API_KEY);
		
		}
		catch(XMPPException ex) {
			ex.printStackTrace();
		}

		
	}
	
	private static String createJsonMessage(String toRegId,String messageId,Map<String, String> payload,String collapseKey,
            Long timeToLive, boolean delayWhileIdle) {
		final Map<String, Object> map = createAttributeMap(toRegId, messageId, payload, collapseKey, timeToLive, delayWhileIdle);
		return JSONValue.toJSONString(map);
		
	}
	
	/*
	 * {
    	"to" : "APA91bHun4MxP5egoKMwt2KZFBaFUH-1RYqx...",
    	"notification" : {
      	"body" : "great match!",
      	"title" : "Portugal vs. Denmark",
      	"icon" : "myicon"
    	},
    	"data" : {
      	"Nick" : "Mario",
      	"Room" : "PortugalVSDenmark"
    	}
  	}
	 * 
	 * 
	 * 
	 * */
	
	public static Map<String,Object> createAttributeMap(String to, String messageId, Map<String, String> payload, String collapseKey, Long timeToLive, Boolean delayWhileIdle) {
        Map<String, Object> message = new HashMap<String, Object>();
        if (to != null) {
            message.put("to", to);
        }
        if (collapseKey != null) {
            message.put("collapse_key", collapseKey);
        }
        if (timeToLive != null) {
            message.put("time_to_live", timeToLive);
        }
        if (delayWhileIdle != null && delayWhileIdle) {
            message.put("delay_while_idle", true);
        }
        if (messageId != null) {
            message.put("message_id", messageId);
        }
        message.put("data", payload); //have to add Notification Part later
        message.put("notification", payload);
        return message;
    }
	
	private void sendMessage(final String json) {
		
		final MypacketEntension myExtension= new MypacketEntension(json);
		final Packet packet = myExtension.toPacket();
		xmppconnection.sendPacket(packet);
		
	}
	
	class MypacketEntension extends DefaultPacketExtension {
		String json;

        public MypacketEntension(final String json) {
            super(TAG_NAME,XMLNS_NAME);
            this.json = json;
        }

        public String getJson() {
            return json;
        }

        @Override
        public String toXML() {
            return String.format("<%s xmlns=\"%s\">%s</%s>", TAG_NAME,
                    XMLNS_NAME, json, TAG_NAME);
        }

        @SuppressWarnings("unused")
        public Packet toPacket() {
        	
            return new Message() {
                // Must override toXML() because it includes a <body>
               @Override
                public String toXML() {

                    StringBuilder buf = new StringBuilder();
                    buf.append("<message");
                    if (getXmlns() != null) {
                        buf.append(" xmlns=\"").append(getXmlns()).append("\"");
                    }
                    
                    //if (getLanguage() != null) {
                   //     buf.append(" xml:lang=\"").append(getLanguage()).append("\"");
                    //}
                    if (getPacketID() != null) {
                        buf.append(" id=\"").append(getPacketID()).append("\"");
                    }
                    if (getTo() != null) {
                        buf.append(" to=\"").append(StringUtils.escapeForXML(getTo())).append("\"");
                    }
                    if (getFrom() != null) {
                        buf.append(" from=\"").append(StringUtils.escapeForXML(getFrom())).append("\"");
                    }
                    buf.append(">");
                    buf.append(MypacketEntension.this.toXML()); //getting JSonBody with <gcm xmlns="google:mobile:data"> tag 
                    buf.append("</message>");
                    System.out.println("******JUST PRINTING SENT MESSAGE FOR CONFIRMATION OF FORMAT****");
                    System.out.println(buf.toString());
                    return buf.toString();
               		}
                };
            
        }
	}//end of MyPacketExtension
	
	public void send(final String toRegId, String file_URL) {
		
		long messageId_1 = RANDOM.nextLong();//ccsClient.getRandomMessageId();
        final String messageId = "m-"+Long.toString(messageId_1);
        final Map<String, String> payload = new HashMap<String, String>();
        //test Message
        payload.put("message", file_URL);//"http://www.hdwallpapersact.com/wp-content/uploads/2012/05/sachin115.jpg"
        payload.put("title", "New Message from IOT");
        payload.put("body", "New Picture");
        final String collapseKey = "sample";
        Long timeToLive = 10000L;
        Boolean delayWhileIdle = true;
        final String jsonBody= createJsonMessage(toRegId, messageId, payload, collapseKey,
                timeToLive, delayWhileIdle);
		
        System.out.println("This is json Body inside <gcm xmlns=\"google:mobile:data\">");
        System.out.println(jsonBody.toString());
        sendMessage(jsonBody);
		
      //  xmppconnection.disconnect();
		
	}
	
	
	
	/*public static void main(String[] argv)  {
		//String toRegId = argv[0];		
		
//		System.out.println(GCMClient.class.getName());
//		
		connect();
		new GCMClient().send("eZWyz09bORs:APA91bGlaA26NuGpX7zad9q-FfS2YpIbNeNeAbrvX8iuXL8meEruzm0xEXILn5QzAcdnxOnwlVsgzcqbCAyLWjkp3L9ZxXzu0VqvTosfALDfPoaOQh0SN9S429MVf4GXU3dClGVYLTC8","test");
		
		
		// Send a sample hello downstream message to a device.
		
                
        
        
		//xmppconnection.sendPacket();
		//xmppconnection.disconnect();
		
		
		
	} */ 
}


