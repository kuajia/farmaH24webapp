package it.farmah24.rest.exception;

import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.farmah24.util.Log;

public class DeviceLicenseExpiredException extends WebApplicationException {

	private static final long serialVersionUID = 3474058009065739382L;

	public DeviceLicenseExpiredException(String deviceKey, Date dateLicensed) 
	{
        super(Response.status(Response.Status.BAD_REQUEST)
        			  .entity("device license expired, deviceKey = " + deviceKey + ",dateLicensed = " + dateLicensed)
        			  .type(MediaType.TEXT_PLAIN)
        			  .build());

  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("DeviceLicenseExpiredException"				       );
        Log.e("device license expired, deviceKey = " + deviceKey + ",dateLicensed = " + dateLicensed );
  	  	Log.e("===================================================");  	  	
  	  	Log.e("\n\n");
    }	
}
