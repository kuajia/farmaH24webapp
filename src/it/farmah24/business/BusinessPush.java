package it.farmah24.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import it.farmah24.obj.Messaggio;
import it.farmah24.obj.PushRegistrationBean;
import it.farmah24.pushy.GoogleAPI;
import it.farmah24.pushy.PushRequest;
import it.farmah24.pushy.PushyAPI;
import it.farmah24.sdk.SdkMysql;
import it.farmah24.util.Log;
import it.farmah24.util.Util;



public class BusinessPush extends SdkMysql
{
private BusinessOperatori businessOperatori;
	
	
	public BusinessPush() {
		super();
		businessOperatori = new BusinessOperatori();
	}

	//----------------------------------------------------------------------------------------------
	
	public boolean pushSend(final int loginId, final String token, final int provinciaId, final Messaggio message)
	{	
		final int utenteProvinciaId = businessOperatori.getProvinciaId(loginId, token);
		if (!Util.isValidId(utenteProvinciaId))
			return false;

		
		//recordMessaggio(provinciaId, message);
		
		
		List<PushRegistrationBean> regIDGoogleAnd = getPushRegistrationIdList(provinciaId, "G", "AND");
		List<PushRegistrationBean> regIDGoogleIos = getPushRegistrationIdList(provinciaId, "G", "IOS");

		send(message, regIDGoogleAnd, "G", "AND");
		send(message, regIDGoogleIos, "G", "IOS");

		return true;
	}

	//---------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public List<Messaggio> pushList(final int loginId, final String token, final int provinciaId)
	{
		IStatement statement = new IStatement() 
		{
			@Override
			public PreparedStatement build(Connection conn) throws SQLException 
			{	
				String query = "SELECT id, messaggio, dataInvio"
						     + "  FROM messaggio"
						     + " WHERE IsTokenValid(?,?)"
						     + "   AND provinciaId = GetProvinciaId(?)"
							 + " ORDER BY dataInvio DESC";

				Log.qs("pushList(final int loginId, final String token, final int provinciaId)");
				Log.qq(query);
				Log.qp(1, "loginId"    , loginId	);
				Log.qp(2, "token"      , token	    );
				Log.qp(3, "provinciaId", provinciaId);

				
				PreparedStatement stmt = conn.prepareStatement(query);				
				stmt.setInt   (1, loginId	 );
				stmt.setString(2, token  	 );
				stmt.setInt   (3, provinciaId);
				return stmt;
			}
			@Override
			public Object createElement(ResultSet rs) throws SQLException 
			{
				return Messaggio.create(rs);		
			}
		};
		
		
		List<Messaggio> list = this.getList(statement);		
		Log.mx(list.size());
		return list;
	}
	
	//---------------------------------------------------------------------------------------------
	//		METODI PRIVATI
	//---------------------------------------------------------------------------------------------
	
	

	@SuppressWarnings("unchecked")
	private List<PushRegistrationBean> getPushRegistrationIdList(final int provinciaId, final String type, final String system)
	{
		IStatement statement = new IStatement() 
		{
			@Override
			public PreparedStatement build(Connection conn) throws SQLException 
			{	
				String query = "SELECT registrationId" 
							 + "  FROM pushRegistration"
						     + " WHERE provinciaId = ?"
							 + "   AND type   	   = ?"
							 + "   AND system 	   = ?"
							 + "   AND canSend     = 1"
							 ;
				
				Log.qs("getPushRegistrationIdList(final int provinciaId, final String type, final String system)");
				Log.qq(query);
				Log.qp( 1, "provinciaId", provinciaId);
				Log.qp( 2, "type"		, type		 );
				Log.qp( 3, "system"		, system	 );

				
				PreparedStatement st = conn.prepareStatement(query);
				st.setInt   (1, provinciaId);
				st.setString(2, type  	   );
				st.setString(3, system	   );
				return st;
			}
			@Override
			public Object createElement(ResultSet rs) throws SQLException 
			{
				return PushRegistrationBean.create(rs);
			}
		};
		
		List<PushRegistrationBean> list = this.getList(statement);
		Log.mx(list.size());
		return list;
	}
	
	//----------------------------------------------------------------------------------------------

	private void send(final Messaggio message, final List<PushRegistrationBean> registrationIDs, final String type, final String system)
	{
		if (registrationIDs == null || registrationIDs.size() == 0) {
			Log.i("Nothing to send! Size is 0");			
		} else {
			Log.i("sending messages to: ");
			
			
			String[] messagesIDs = new String[registrationIDs.size()];
			int idx = -1;
			for (PushRegistrationBean item : registrationIDs) {
				Log.i("    " + item.getRegistrationId());
				messagesIDs[++idx] = item.getRegistrationId();
			}
		
			Map<String, String> payload = new HashMap<String, String>();
			payload.put("body" , message.getMessaggio() );
			payload.put("title", message.getTitolo   () );
			// Prepare the push request
			PushRequest push = new PushRequest(payload, messagesIDs);
		
			try {				
				if (type.equals("G")) {
					Log.i("  -- sending: SENDING to GoogleAPI"  );
					List<String> tokensToDelete = GoogleAPI.sendPushMessage(push, system);
					deleteTokens(tokensToDelete);
				}				
			} catch (Exception e) {			
				Log.e(e.getClass().getName(), e);
			}
		}
	}
	
	//----------------------------------------------------------------------------------------------

	private void deleteTokens(final List<String> tokensToDelete)
	{
		if (tokensToDelete != null)
		{
			for (String token : tokensToDelete)
			{
				Log.i("    - deleting token " + token);
				
				String query = "DELETE FROM pushRegistration"
							 + " WHERE registrationId = ?"
							 ;
				
				Connection 		  conn = null;
				PreparedStatement stmt = null;
	
				try {			
					conn = this.getConnection();
					stmt = conn.prepareStatement(query);				
					stmt.setString(1, token);
					stmt.execute();
					
					close(stmt);

				} catch (NamingException | SQLException e) {			
					Log.e(e.getClass().getName(), e);
					throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
				} finally {
					close(conn);
				}
			}
		}
	}
}