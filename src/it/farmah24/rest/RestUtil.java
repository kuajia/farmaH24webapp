package it.farmah24.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.sun.jersey.core.util.Base64;

import it.farmah24.util.Log;

public class RestUtil {

	private static RestUtil instance;
	
//	private String imageServerName;
	
	@SuppressWarnings("unused")
	private RestUtil() {		
		Context env;
		try {
			env = (Context)new InitialContext().lookup("java:comp/env");
//			this.imageServerName = (String)env.lookup("cqImageServer");
//			if (this.imageServerName.isEmpty()) {
//			}
		} catch (NamingException e) {			
			Log.e(e.getClass().getName(), e);
			Log.e("environment context fail, e = " + e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	public static RestUtil getInstance() {
		
		if (instance == null) {
			synchronized(RestUtil.class) {
				if (instance == null) {
					instance = new RestUtil();
				}
			}
		}
		
		return instance;
		
	}
	
	public String buildMessageFromThrowable(Throwable t) {
		
		String message = "";
		if (t != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);     
			message = sw.toString();      			
		}
		Log.e("message =  " + message);
		return message;
		
	}		

	//if there is an error, returns null
/*
	public String retrieveImageInBase64(String imageName) {
		
		if (imageName.indexOf('.') == -1) {
			imageName = imageName + ".bmp";
		}
		Log.i("retrieveImageInBase64 started, imageName = " + imageName);
		String imageInBase64 = null;
		InputStream imageIs = this.retrieveImage(imageName);
		if (imageIs != null) {
			imageInBase64 = this.convertFileIntoBase64(imageIs);			
		} 
		return imageInBase64;
		
	}

	private InputStream retrieveImage(String imageName) {
		
		try {
			String urlOfImage = this.imageServerName + "/cqimages/images/site/tm/" + imageName;
			Log.i("retrieveImage by url, urlOfImage = " + urlOfImage);
			URL url = new URL(urlOfImage);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			return conn.getInputStream();
		} catch (IOException e) {
			Log.e(e.getClass().getName(), e);
			Log.e("retrieveImage fail, e = " + e);
			return null;
			//throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);			
		}
		
	}
*/
	public String convertFileIntoBase64 (InputStream is) {
		
		String base64 = null;		
		byte[] bytes;
		try {
			bytes = IOUtils.toByteArray(is);
			base64 = new String(Base64.encode(bytes));
		} catch (IOException e) {
			Log.e(e.getClass().getName(), e);
			Log.e("error in image conversion, e = " + e);
			return null;
			//throw new ImageConversionException(e);
		}
		
		return base64;
	}
	

	public static String getPreparedQuery(PreparedStatement st)
	{
		String prepared = st.toString();
		return prepared.substring(prepared.indexOf(" ")+1);
	}
}
