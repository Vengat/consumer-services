package com.bang.util;

import java.io.BufferedReader;
import
java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class SendSMS {
	
	public static void sendSMSToCustomer(String phoneNumber, String message) {
		try {
		Date mydate = new
				Date(System.currentTimeMillis());
				String data = "";
				data += "method=sendMessage";
				data += "&userid=2000147314"; // your loginId
				data += "&password=" +
				URLEncoder.encode("omelee12", "UTF-8"); // your password
				data += "&msg=" + URLEncoder.encode(""+message+"" + mydate.toString(), "UTF-8");
				data += "&send_to=" +
				URLEncoder.encode(phoneNumber, "UTF-8"); // a valid 10 digit phone no.
				data += "&v=1.1" ;
				data += "&msg_type=TEXT"; // Can by "FLASH" or "UNICODE_TEXT" or “BINARY”
				
				data += "&auth_scheme=PLAIN";
				
				URL url = new
						URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?" + data);
						HttpURLConnection conn =
						(HttpURLConnection)url.openConnection();
						conn.setRequestMethod("GET");
						conn.setDoOutput(true);
						conn.setDoInput(true);
						conn.setUseCaches(false);
						conn.connect();
						BufferedReader rd = new BufferedReader(new
						InputStreamReader(conn.getInputStream()));
						String line;
						StringBuffer buffer = new StringBuffer();
						while ((line = rd.readLine()) != null){
						buffer.append(line).append("\n");
						}
						System.out.println(buffer.toString());
						rd.close();
						conn.disconnect();
						}
						catch(Exception e){
						e.printStackTrace();
						}
						
   }
	

}
