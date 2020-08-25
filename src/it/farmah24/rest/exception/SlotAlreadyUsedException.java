package it.farmah24.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.farmah24.util.Log;

public class SlotAlreadyUsedException extends WebApplicationException {
	
	private static final long serialVersionUID = 4679613308975061619L;

	public SlotAlreadyUsedException(int slotId) {
        super(Response.status(Response.Status.BAD_REQUEST)
        			  .entity("slot already used, slotId " + slotId)
        			  .type(MediaType.TEXT_PLAIN)
        			  .build());

  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("SlotAlreadyUsedException"				           );
        Log.e("slot already used, slotId " + slotId                );
  	  	Log.e("===================================================");
  	  	Log.e("\n\n");
    }	
}