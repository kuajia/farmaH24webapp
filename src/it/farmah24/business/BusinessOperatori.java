package it.farmah24.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.farmah24.sdk.SdkMysql;
import it.farmah24.util.Log;
import it.farmah24.util.Util;


public class BusinessOperatori extends SdkMysql 
{
	public BusinessOperatori() {
		super();
	}

	//----------------------------------------------------------------------------------------------
	
	public int getProvinciaId(final int loginId, final String token)
	{
		IStatement statement = new IStatement() 
		{
			@Override
			public PreparedStatement build(Connection conn) throws SQLException 
			{	
				String query = "SELECT provinciaId"
							 + "  FROM operatore"
							 + " WHERE IsTokenValid(?,?)"
							 + "   AND id = ?"
					     	 ;

				Log.qs("getProvinciaId(final int loginId, final String token)");
				Log.qq(query);
				Log.qp(1, "loginId", loginId);
				Log.qp(2, "token"  , token  );
				Log.qp(3, "loginId", loginId);

				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt   (1, loginId);
				stmt.setString(2, token  );
				stmt.setInt   (3, loginId);
				return stmt;
			}
			public Object createElement(ResultSet rs) throws SQLException 
			{
				return Util.getInt(rs, "provinciaId");
			}
		};
		
		int provinciaId = (int) this.getElement(statement);
		Log.i("provinciaId: " + provinciaId);
		return provinciaId;
	}
}
