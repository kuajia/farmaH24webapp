package it.farmah24.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.farmah24.util.Log;

public class MissingMandatoryParameterException extends WebApplicationException {
	
	private static final long serialVersionUID = -4458429797565584354L;

	public MissingMandatoryParameterException(String paramName) 
	{
        super(Response.status(Response.Status.BAD_REQUEST)
                      .entity("missing mandatory parameter " + paramName)
                      .type(MediaType.TEXT_PLAIN)
                      .build());
        
  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("MissingMandatoryParameterException"				  );
  	  	Log.e("missing mandatory parameter: " + paramName		  );
  	  	Log.e("===================================================");
  	  	Log.e("\n\n");
    }
}