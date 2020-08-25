package it.farmah24.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.farmah24.util.Log;

public class InvalidParameterException extends WebApplicationException 
{
	private static final long serialVersionUID = 3512464861006845313L;

	public InvalidParameterException(String paramName) 
	{
        super(Response.status(Response.Status.BAD_REQUEST)
  			  		  .entity("invalid parameter: " + paramName)
  			  		  .type(MediaType.TEXT_PLAIN)
  			  		  .build());

  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("InvalidParameterException"				           );
        Log.e("invalid parameter: " + paramName					   );
  	  	Log.e("===================================================");
  	  	Log.e("\n\n");
	}
}
