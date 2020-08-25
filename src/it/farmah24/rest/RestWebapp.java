package it.farmah24.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import it.farmah24.FarmaturniFactory;
import it.farmah24.obj.LoginBean;
import it.farmah24.obj.Messaggio;
import it.farmah24.obj.Picture;
import it.farmah24.rest.exception.MissingMandatoryParameterException;
import it.farmah24.sdk.ISdkWebapp;
import it.farmah24.util.Common;
import it.farmah24.util.Log;
import it.farmah24.util.Util;


@Path(Common.WEBAPP_PATH)
public class RestWebapp 
{
	private static final String TAG = Common.WEBAPP_PATH; 

	private static final String CHARSET_CODE   = "utf-8";
	private static final String CHARSET_OPTION = ";charset=" + CHARSET_CODE;

	private ISdkWebapp   sdk;
	
	static final Logger logger = LogManager.getLogger(RestWebapp.class.getName());
	
	
	public RestWebapp() {
		this.sdk = FarmaturniFactory.getSdkWebapp();
	}
	
	//=============================================================================================
	//=============================================================================================

	@GET
	@Path("/test")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public boolean test() 
	{
		Log.rs(TAG, "/test", "GET");
		Log.re(TAG, "/test", "GET");

		return true;
	}

	//---------------------------------------------------------------------------------------------
	
	@PUT
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public LoginBean doLogin( @HeaderParam("username") String username
								   , @HeaderParam("password") String password
			                       )
	{
		Log.rs(TAG, "/login"  , "PUT"   );
		Log.rp("H", "username", username);
		Log.rp("H", "password", "***"   );

		
		if (Util.nvl(username).length() == 0) throw new MissingMandatoryParameterException("username");
		if (Util.nvl(password).length() == 0) throw new MissingMandatoryParameterException("password");


		LoginBean bean = this.sdk.doLogin(username, Util.MD5(password));		
		Log.re(TAG, "/login", "PUT");
		return bean;
	}

	//---------------------------------------------------------------------------------------------
	
	@PUT
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public boolean doLogout ( @HeaderParam("loginid") String loginId
						    , @HeaderParam("token")   String token
			                )
	{
		Log.rs(TAG, "/logout"  , "PUT"   );
		Log.rp("H", "loginId" , loginId );
		Log.rp("H", "token"   , token   );

		
		if (Util.nvl(loginId).length() == 0) throw new MissingMandatoryParameterException("loginid");
		if (Util.nvl(token)  .length() == 0) throw new MissingMandatoryParameterException("token");


		boolean response = this.sdk.doLogout(loginId, token);		
		Log.re(TAG, "/logout", "PUT");
		return response;
	}
	
	//---------------------------------------------------------------------------------------------

	@GET
	@Path("/api/webapp/isTokenValid")
	@Consumes( MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON + CHARSET_OPTION})
	public boolean  getTokenValid( @HeaderParam("loginId"    ) int        loginId
			                     		   , @HeaderParam("token"      ) String     token
			                   			   ) 
	{
		Log.rs(TAG, "/api/webapp/isTokenValid", "GET");
		Log.rp("H", "loginId"    , loginId    );
		Log.rp("H", "token"      , token      );
 
		
		if (loginId     <= 0   ) throw new MissingMandatoryParameterException("loginId"    );
		if (token       == null) throw new MissingMandatoryParameterException("token"      );

		boolean result = this.sdk.isTokenValid(loginId, token);
		Log.re(TAG, "/api/webapp/isTokenValid", "GET");
		return result;
	}
	
	//==============================================================================================	
	//		BANNER
	//==============================================================================================

	@GET
	@Path("/api/webapp/banner/{bannerId}")
	@Produces("image/*")
	public Response getBanner( @PathParam("bannerId") int bannerId ) 
	{
		Log.rs(TAG, "/banner/{id}", "GET");
		Log.rp("P", "bannerId", bannerId);

		if (bannerId <= 0) throw new MissingMandatoryParameterException("bannerId");

		
		Picture picture = this.sdk.getBannerImage(bannerId);

		ResponseBuilder response = Response.ok((Object) picture.getByteArray()).type(picture.getMimeType());
		Log.re(TAG, "/banner/{id}", "GET");
		return response.build();
	}

	
	@GET
	@Path("/api/webapp/banner/list")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public List<Integer> getBannerList( @HeaderParam("loginId") int    loginId
			   						  , @HeaderParam("token"  ) String token 
			   						  ) 
	{
		Log.rs(TAG, "/banner/list", "GET");
		Log.rp("H", "loginId"    , loginId    );
		Log.rp("H", "token"      , token      );

		
		if (loginId <= 0   ) throw new MissingMandatoryParameterException("loginId");
		if (token   == null) throw new MissingMandatoryParameterException("token"  );

		
		List<Integer> list = this.sdk.getBannerList(loginId, token);
		Log.rx(list.size());
		Log.re(TAG, "/banner/list", "GET");
		return list;
	}

	//----------------------------------------------------------------------------------------------
	
	@POST
	@Path("/api/webapp/banner/create")
	@Consumes( MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON + CHARSET_OPTION})
	public boolean bannerCreate( @HeaderParam("loginId") int        loginId
			                   , @HeaderParam("token"  ) String     token
		                       , @FormParam  ("data"   ) JSONObject data
			                   ) 
	{
		Log.rs(TAG, "/banner/create", "POST");
		Log.rp("H", "loginId", loginId);
		Log.rp("H", "token"  , token  );
		Log.rp("F", "data"   , data   );

		
		if (loginId <= 0   ) throw new MissingMandatoryParameterException("loginId");
		if (token   == null) throw new MissingMandatoryParameterException("token"  );
		if (data    == null) throw new MissingMandatoryParameterException("data"   );


		boolean result = this.sdk.bannerCreate(loginId, token, data);
		Log.re(TAG, "/banner/create", "POST");
		return result;
	}

	//----------------------------------------------------------------------------------------------

	@POST
	@Path("/api/webapp/banner/{bannerId}/update")
	@Consumes( MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON + CHARSET_OPTION})
	public boolean bannerUpdate( @HeaderParam("loginId" ) int        loginId
			                   , @HeaderParam("token"   ) String     token
							   , @PathParam  ("bannerId") int        bannerId
		                       , @FormParam  ("data"    ) JSONObject data
			                   ) 
	{
		Log.rs(TAG, "/banner/{bannerId}/update", "POST");
		Log.rp("H", "loginId" , loginId);
		Log.rp("H", "token"   , token  );
		Log.rp("P", "bannerId", bannerId);
		Log.rp("F", "data"    , data   );
 
		
		if (loginId  <= 0   ) throw new MissingMandatoryParameterException("loginId" );
		if (token    == null) throw new MissingMandatoryParameterException("token"   );
		if (bannerId <= 0   ) throw new MissingMandatoryParameterException("bannerId");
		if (data     == null) throw new MissingMandatoryParameterException("data"    );


		boolean result = this.sdk.bannerUpdate(loginId, token, bannerId, data);
		Log.re(TAG, "/banner/{bannerId}/update", "POST");
		return result;
	}

	//----------------------------------------------------------------------------------------------
	
	@DELETE
	@Path("/api/webapp/banner/{bannerId}/delete")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public boolean bannerDelete( @HeaderParam("loginId" ) int    loginId
			   				   , @HeaderParam("token"   ) String token
			   				   , @PathParam  ("bannerId") int    bannerId
			   				   ) 
	{
		Log.rs(TAG, "/banner/{bannerId}/delete", "DELETE");
		Log.rp("H", "loginId" , loginId );
		Log.rp("H", "token"   , token   );
		Log.rp("P", "bannerId", bannerId);
		
		
		if (loginId  <= 0   ) throw new MissingMandatoryParameterException("loginId" );
		if (token    == null) throw new MissingMandatoryParameterException("token"   );
		if (bannerId <= 0   ) throw new MissingMandatoryParameterException("bannerId");


		boolean result = this.sdk.bannerDelete(loginId, token, bannerId);
		Log.re(TAG, "/banner/{bannerId}/delete", "DELETE");
		return result;
	}
	
	//==============================================================================================	
	//		MESSAGGI PUSH
	//==============================================================================================
	
		
	@GET
	@Path("/api/webapp/push/{provinciaId}/list")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public List<Messaggio> pushList	 ( @HeaderParam("loginId"    ) int    loginId
		   					   	     , @HeaderParam("token"      ) String token
		   					   	     , @PathParam  ("provinciaId") int    provinciaId
		   					   	     ) 
	{
		Log.rs(TAG, "/pushy/{provinciaId}/list" , "PUT");
		Log.rp("H", "loginId"    , loginId    );
		Log.rp("H", "token"      , token      );
		Log.rp("P", "provinciaId", provinciaId);
		
		
		if (loginId     <= 0   ) throw new MissingMandatoryParameterException("loginId"    );
		if (token       == null) throw new MissingMandatoryParameterException("token"      );
		if (provinciaId <= 0   ) throw new MissingMandatoryParameterException("provinciaId");
		

		List<Messaggio> list = this.sdk.pushList(loginId, token, provinciaId);
		Log.re(TAG, "/pushy/{provinciaId}/list" , "PUT");
		return list;
	}

	//==============================================================================================	
	//	GESTIONE MESSAGGI
	//==============================================================================================	
	
	@PUT
	@Path("/messaggi/list")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public List<Messaggio> getListMessaggi( @HeaderParam("loginid"    ) int    loginId
			   						   		  , @HeaderParam("token"      ) String token
			   						   		  , @HeaderParam("provinciaid") int    provinciaId
					                          ) 
	{
		Log.rs(TAG, "/messaggi/list", "PUT");
		Log.rp("H", "loginId" , loginId);
		Log.rp("H", "token"   , token  );
		Log.rp("H", "provinciaid" , provinciaId);

		if (loginId     == 0    ) throw new MissingMandatoryParameterException("loginId"    );
		if (token       == null ) throw new MissingMandatoryParameterException("token"      );
		if (provinciaId == 0    ) throw new MissingMandatoryParameterException("provinciaid");

		List<Messaggio> result = this.sdk.getListMessaggi(loginId, token, provinciaId);
		Log.re(TAG, "/messaggi/list", "PUT");
		return result;
	}
	@PUT
	@Path("/create/messaggio")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public boolean createMessaggio( @HeaderParam("loginid"    ) int        loginId
			   						   		  , @HeaderParam("token"      ) String     token
			   						   		  , @FormParam  ("data"       ) JSONObject data
					                          ) 
	{
		Log.rs(TAG, "/create/messaggio", "PUT");
		Log.rp("H", "loginId" , loginId );
		Log.rp("H", "token"   , token   );
		Log.rp("F", "data"    , data    );

		if (loginId     == 0    ) throw new MissingMandatoryParameterException("loginId"    );
		if (token       == null ) throw new MissingMandatoryParameterException("token"      );
		if (data        == null ) throw new MissingMandatoryParameterException("data"       );
		Messaggio params = Messaggio.fromJSON(data);

		boolean result = this.sdk.crudMessaggio(loginId, token, "C", params);
		Log.re(TAG, "/create/messaggio", "PUT");
		if(result)
		{
			this.sdk.pushSend(loginId, token, 3, params);
		}
		return result;
	}
	
	@PUT
	@Path("/update/messaggio")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public boolean updateMessaggio( @HeaderParam("loginid"    ) int        loginId
   						   		  , @HeaderParam("token"      ) String     token
   						   		  , @FormParam  ("data"       ) JSONObject data
		                          ) 
	{
		Log.rs(TAG, "/update/messaggio", "PUT");
		Log.rp("H", "loginId" , loginId );
		Log.rp("H", "token"   , token   );
		Log.rp("F", "data"    , data    );

		if (loginId     == 0    ) throw new MissingMandatoryParameterException("loginId"    );
		if (token       == null ) throw new MissingMandatoryParameterException("token"      );
		if (data        == null ) throw new MissingMandatoryParameterException("data"       );
		Messaggio params = Messaggio.fromJSON(data);

		boolean result = this.sdk.crudMessaggio(loginId, token, "U", params);
		Log.re(TAG, "/update/messaggio", "PUT");
		if(result)
		{
			this.sdk.pushSend(loginId, token, 3, params);
		}
		return result;
	}
	
	@PUT
	@Path("/delete/messaggio")
	@Produces({ MediaType.APPLICATION_JSON + CHARSET_OPTION })
	public boolean deleteMessaggio( @HeaderParam("loginid"    ) int        loginId
   						   		  , @HeaderParam("token"      ) String     token
   						   		  , @FormParam  ("data"       ) JSONObject data
		                          ) 
	{
		Log.rs(TAG, "/delete/messaggio", "PUT");
		Log.rp("H", "loginId" , loginId );
		Log.rp("H", "token"   , token   );
		Log.rp("F", "data"    , data    );

		if (loginId     == 0    ) throw new MissingMandatoryParameterException("loginId"    );
		if (token       == null ) throw new MissingMandatoryParameterException("token"      );
		if (data        == null ) throw new MissingMandatoryParameterException("data"       );
		Messaggio params = Messaggio.fromJSON(data);

		boolean result = this.sdk.crudMessaggio(loginId, token, "D", params);
		Log.re(TAG, "/delete/messaggio", "PUT");
		return result;
	}
}
