package it.farmah24.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;

import it.farmah24.util.Log;


public class InternalServerErrorException extends WebApplicationException 
{
	private static final long serialVersionUID = 3512464861006845313L;

	public InternalServerErrorException(Exception e) 
	{
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
  			  		  .entity("Internal Server Error: " + e.toString())
  			  		  .type(MediaType.TEXT_PLAIN)
  			  		  .build());
  
  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("InternalServerErrorException"				       );
		Log.e(ExceptionUtils.getStackTrace(e)					   );
  	  	Log.e("===================================================");
  	  	Log.e("\n\n");
	}
}
