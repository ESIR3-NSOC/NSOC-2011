package org.kevoree.service.knx;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NSLookup {

		   
	    public static String IPAddress(String hostname) {
	        InetAddress inetHost = null;
	        try {
	            inetHost = InetAddress.getByName(hostname);
	        } catch (UnknownHostException ex) {
	            Logger.getLogger(NSLookup.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        if (inetHost == null) {
	            return "Invalid hostname: "+hostname;
	        } else {
	            return inetHost.getHostAddress();
	        }
	    }

}
