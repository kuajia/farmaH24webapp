package it.farmah24.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.farmah24.util.Log;

public class InvalidTimeFormatException extends WebApplicationException {

	private static final long serialVersionUID = 6689373610501755827L;

	public InvalidTimeFormatException(String day) {
        super(Response.status(Response.Status.BAD_REQUEST)
        			  .entity("invalid format time " + day)
        			  .type(MediaType.TEXT_PLAIN)
        			  .build());

  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("InvalidTimeFormatException"				           );
        Log.e("invalid format time " + day			               );
  	  	Log.e("===================================================");
  	  	Log.e("\n\n");
    }
}
