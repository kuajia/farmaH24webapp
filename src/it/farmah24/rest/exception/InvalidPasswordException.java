package it.farmah24.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.farmah24.util.Log;

public class InvalidPasswordException extends WebApplicationException {

	private static final long serialVersionUID = 1512464861006845313L;

	public InvalidPasswordException() {
        super(Response.status(Response.Status.BAD_REQUEST)
        			  .entity("invalid password")
        			  .type(MediaType.TEXT_PLAIN)
        			  .build());

  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("InvalidPasswordException"				           );
        Log.e("invalid password"								   );
  	  	Log.e("===================================================");
  	  	Log.e("\n\n");
    }
}
