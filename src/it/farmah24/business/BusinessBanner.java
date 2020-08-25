package it.farmah24.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.core.util.Base64;

import it.farmah24.obj.Picture;
import it.farmah24.sdk.SdkMysql;
import it.farmah24.util.Log;
import it.farmah24.util.Util;


public class BusinessBanner extends SdkMysql
{
	private BusinessOperatori businessUtenti;

	
	public BusinessBanner() {
		super();

		businessUtenti = new BusinessOperatori();
	}

	//----------------------------------------------------------------------------------------------
	
	public Picture getBannerImage(final int bannerId)
	{
		IStatement statement = new IStatement() 
		{
			@Override
			public PreparedStatement build(Connection conn) throws SQLException 
			{	
				String query = "SELECT imageType, imageFile"
						 	 + "  FROM banner"
						 	 + " WHERE id = ?";
	
				Log.qs("getBannerImage(final int bannerId)");
				Log.qq(query);
				Log.qp(1, "bannerId", bannerId);

				
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt (1, bannerId);
				
				return stmt;
			}
			public Object createElement(ResultSet rs) throws SQLException 
			{
				String type = getString(rs, "imageType");
				String file = getString(rs, "imageFile");
				
				Picture picture = new Picture();
				picture.setByteArray(file == null ? null : Base64.decode(file));
				picture.setMimeType (type);
				return picture;
			}
		};
	
		Picture picture = (Picture) this.getElement(statement);
		return picture;
	}

	//----------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public List<Integer> getBannerList(final int loginId, final String token)
	{
		IStatement statement = new IStatement() 
		{
			@Override
			public PreparedStatement build(Connection conn) throws SQLException 
			{	
				String query = "SELECT id"
					         + "  FROM banner"
							 + " WHERE IsTokenValid(?,?)"
					         + "   AND provinciaId = GetProvinciaId(?)"
					         + " ORDER BY id";

				Log.qs("getBannerList(final int loginId, final String token)");
				Log.qq(query);
				Log.qp(1, "loginId"    , loginId	);
				Log.qp(2, "token"      , token	    );
				Log.qp(3, "loginId"    , loginId    );

							
				PreparedStatement stmt = conn.prepareStatement(query);				
				stmt.setInt   (1, loginId	 );
				stmt.setString(2, token  	 );
				stmt.setInt   (3, loginId	 );
				return stmt;
			}
			public Object createElement(ResultSet rs) throws SQLException 
			{
				Integer id = getInt   (rs, "id");
				return id;
			}
		};
		
		List<Integer> list = this.getList(statement);		
		return list;
	}

	//----------------------------------------------------------------------------------------------
	
	public boolean bannerCreate(final int loginId, final String token, final JSONObject data)
	{
		final int utenteProvinciaId = businessUtenti.getProvinciaId(loginId, token);
		if (!Util.isValidId(utenteProvinciaId))
			return false;
		
		String imageType = getString(data, "imageType");
		String imageFile = getString(data, "imageFile");

		if (imageType == null || imageFile == null) {
			Log.i("imageType o imageFile null");
			return false;
		}

	
		String query = "INSERT INTO banner"
				 + "   (provinciaId, imageType, imageFile)"
				 + "     VALUES"
				 + "   (?,?,?)";
		
		Connection 		  conn = null;
		PreparedStatement stmt = null;
		
		try {			
			Log.qs("bannerCreate(final int loginId, final String token, final JSONObject data)");
			Log.qq(query);
			Log.qp( 1, "provinciaId", utenteProvinciaId);
			Log.qp( 2, "imageType"	, imageType        );
			Log.qp( 3, "imageFile"	, imageFile        );

			conn = this.getConnection();
			stmt = conn.prepareStatement(query); //, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt   (1, utenteProvinciaId);
			stmt.setString(2, imageType 	   );
			stmt.setString(3, imageFile 	   );

			stmt.execute();
			close(stmt);
			
			return true;

		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			return false;				
		} finally {
			close(conn);
		}						
	}

	//----------------------------------------------------------------------------------------------
	
	public boolean bannerUpdate(final int loginId, final String token, final int bannerId, final JSONObject data)
	{
		String imageType = getString(data, "imageType");
		String imageFile = getString(data, "imageFile");

		if (imageType == null || imageFile == null) {
			Log.i("imageType o imageFile null");
			return false;
		}


		String query = "UPDATE banner"
					 + "   SET imageType = ?"
					 + "     , imageFile = ?"
					 + " WHERE IsTokenValid(?,?)"
					 + "   AND id = ?"
					 + "   AND provinciaId = GetProvinciaId(?)"
					 ;
		
		Connection 		  conn = null;
		PreparedStatement stmt = null;

		try {			
			Log.qs("bannerUpdate(final int loginId, final String token, final int bannerId, final JSONObject data)");
			Log.qq(query);
			Log.qp(1, "imageType", imageType);
			Log.qp(2, "imageFile", imageFile);
			Log.qp(3, "loginId"  , loginId	);
			Log.qp(4, "token"    , token	);
			Log.qp(5, "bannerId" , bannerId	);
			Log.qp(6, "loginId"  , loginId	);

			conn = this.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setString(1, imageType);
			stmt.setString(2, imageFile);
			stmt.setInt   (3, loginId  );
			stmt.setString(4, token    );
			stmt.setDouble(5, bannerId );
			stmt.setInt   (6, loginId  );
			
			stmt.execute();			
			close(stmt);
			
			return true;

		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			return false;				
		} finally {
			close(conn);
		}						
	}

	//----------------------------------------------------------------------------------------------
	
	public boolean bannerDelete(final int loginId, final String token, final int bannerId)
	{
		String query = "DELETE FROM banner"
				 	 + " WHERE IsTokenValid(?,?)"
				 	 + "   AND id = ?"
				 	 + "   AND provinciaId = GetProvinciaId(?)";
		
		Connection 		  conn = null;
		PreparedStatement stmt = null;

		boolean result = false;

		try {			
			Log.qs("bannerDelete(final int loginId, final String token, final int bannerId)");
			Log.qq(query);
			Log.qp(1, "loginId" , loginId );
			Log.qp(2, "token"   , token	  );
			Log.qp(3, "bannerId", bannerId);
			Log.qp(4, "loginId" , loginId );


			conn = this.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt   (1, loginId  );
			stmt.setString(2, token    );
			stmt.setDouble(3, bannerId );
			stmt.setInt   (4, loginId  );

			stmt.execute();
			close(stmt);
			
			result = true;

		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
		} finally {
			close(conn);
		}						

		return result;
	}	
}
