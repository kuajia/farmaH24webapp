package it.farmah24.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.farmah24.util.Log;

public class InvalidDateFormatException extends WebApplicationException {

	private static final long serialVersionUID = -680678066336559534L;

	public InvalidDateFormatException(String date) { 
		this(date, false);
	}
	
	public InvalidDateFormatException(String date, boolean internalServerError) 
	{
        super(Response.status(internalServerError ? Response.Status.INTERNAL_SERVER_ERROR : Response.Status.BAD_REQUEST)
        			  .entity("invalid date format " + date)
        			  .type(MediaType.TEXT_PLAIN)
        			  .build());

  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("InvalidDateFormatException"				           );
        Log.e("invalid date format " + date						   );
  	  	Log.e("===================================================");
  	  	Log.e("\n\n");
    }
}
