package it.farmah24.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.farmah24.util.Log;

public class ImageConversionException extends WebApplicationException {

	private static final long serialVersionUID = 3010099219758043599L;

	public ImageConversionException(Exception ex) 
	{
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        			  .entity("image conversion error, ex = " + ex)
        			  .type(MediaType.TEXT_PLAIN)
        			  .build());

  	  	Log.e("\n\n");
  	  	Log.e("===================================================");
  	  	Log.e("ImageConversionException"				           );
        Log.e("image conversion error, ex = " + ex				   );
  	  	Log.e("===================================================");
  	  	Log.e("\n\n");
    }
}
