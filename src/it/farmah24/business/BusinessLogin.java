package it.farmah24.business;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import it.farmah24.obj.LoginBean;
import it.farmah24.rest.exception.InternalServerErrorException;
import it.farmah24.sdk.SdkMysql;
import it.farmah24.util.Log;

public class BusinessLogin extends SdkMysql 
{
	public BusinessLogin() {
		super();
	}

	// ---------------------------------------------------------------------------------------------

	public LoginBean doLogin(final String username, final String password) 
	{
		Log.ms("BusinessLogin", this, "doLogin(final String username, final String password) ");
		Log.mp("username" , username );
		Log.mp("password" , password );

		Connection 		  conn = null;
		CallableStatement stmt = null;
		ResultSet         rs   = null;

		try {
			String query = "{ CALL f2_wa_login(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			conn = this.getConnection();
			stmt = conn.prepareCall(query);

			
			stmt.setString(1, username);
			stmt.setString(2, password  );

			
			stmt.registerOutParameter(14, java.sql.Types.INTEGER); // resultCode
			stmt.registerOutParameter(15, java.sql.Types.VARCHAR); // resultMsg

			stmt.execute();

			// extract the output parameters

			LoginBean bean = null;

			int resultCode = stmt.getInt(14);

	        if (resultCode == 1)
	        {
		        bean = LoginBean.create(stmt.getInt(3),stmt.getString(4),stmt.getString(5) ,stmt.getString(6),stmt.getString(7)
		        					   ,stmt.getInt(8),stmt.getString(9),stmt.getString(10),stmt.getInt(11)  ,stmt.getString(12)
		        					   ,stmt.getString(13));
   	        }

	        stmt.close();

			return bean;

		} catch (NamingException e) {			
			throw new InternalServerErrorException(e);				
		} catch (SQLException e) {
			throw new InternalServerErrorException(e);
		} finally {
			this.close(rs, stmt, conn);
		}
	}
	
	// ---------------------------------------------------------------------------------------------

		public boolean doLogout(final String loginId, final String token ) 
		{
			Log.ms("BusinessLogin", this, "doLogout(final String loginId, final String token)");
			Log.mp("username" , loginId );
			Log.mp("password" , token );

			Connection 		  conn = null;
			CallableStatement stmt = null;
			ResultSet         rs   = null;

			try {
				String query = "{ CALL f2_wa_logout(?,?,?,?) }";

				conn = this.getConnection();
				stmt = conn.prepareCall(query);

				
				stmt.setString(1, loginId);
				stmt.setString(2, token  );

				
				stmt.registerOutParameter(3, java.sql.Types.INTEGER); // resultCode
				stmt.registerOutParameter(4, java.sql.Types.VARCHAR); // resultMsg

				stmt.execute();

				// extract the output parameters

				int resultCode = stmt.getInt(3);
		        stmt.close();

		        return (resultCode == 1);
	
			} catch (NamingException e) {			
				throw new InternalServerErrorException(e);				
			} catch (SQLException e) {
				throw new InternalServerErrorException(e);
			} finally {
				this.close(rs, stmt, conn);
			}
		}
		
		// ---------------------------------------------------------------------------------------------
	
		public boolean isTokenValid(final int loginId, final String token ) 
		{
			Log.ms("BusinessLogin", this, "isTokenValid(final String loginId, final String token)");
			Log.mp("loginId" , loginId );
			Log.mp("token"   , token );
	
			Connection 		  conn = null;
			CallableStatement stmt = null;
			ResultSet         rs   = null;
	
			try {
				String query = "{ CALL f2_isTokenValid(?,?,?,?) }";
	
				conn = this.getConnection();
				stmt = conn.prepareCall(query);
	
				
				stmt.setInt   (1, loginId);
				stmt.setString(2, token  );
	
				stmt.registerOutParameter(3, java.sql.Types.INTEGER); // resultCode
				stmt.registerOutParameter(4, java.sql.Types.VARCHAR); // resultMsg

				stmt.execute();

				// extract the output parameters

				int resultCode = stmt.getInt(3);
		        stmt.close();

		        return (resultCode == 1);
	
			} catch (NamingException e) {			
				throw new InternalServerErrorException(e);				
			} catch (SQLException e) {
				throw new InternalServerErrorException(e);
			} finally {
				this.close(rs, stmt, conn);
			}
		}
}
