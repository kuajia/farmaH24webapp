package it.farmah24.business;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import it.farmah24.common.Common;
import it.farmah24.obj.Messaggio;
import it.farmah24.sdk.SdkMysql;
import it.farmah24.util.Log;

public class BusinessMessaggio extends SdkMysql 
{
	
	public BusinessMessaggio() {
		super();
	}
	
	public List<Messaggio> getListMessaggi(final int loginId, final String token, final int provinciaId)
	{
		Log.ms(Common.APP_CODE, this, "getListMessaggi(final int loginId, final String token, final int provinciaId)");
		Log.mp("loginId"    , loginId    );
		Log.mp("token"      , token   	 );
		Log.mp("provinciaId", provinciaId);

		Connection        conn = null;
		CallableStatement stmt = null;
		ResultSet         rs   = null;

		try {
			String query = "{ CALL f2_wa_getMessaggiList(?,?,?,?,?) }";

			Log.qs("getListMessaggi(...)");
			Log.qq(query);
			Log.qp(1, "loginId"		 , loginId 		);
			Log.qp(2, "token"        , token        );
			Log.qp(3, "provinciaId"  , provinciaId  );

			conn = this.getConnection();
			stmt = conn.prepareCall(query);

			stmt.setInt   (1, loginId      );
			stmt.setString(2, token		   );
			stmt.setInt   (3, provinciaId  );

			stmt.registerOutParameter(4, java.sql.Types.INTEGER); // p_resultCode
			stmt.registerOutParameter(5, java.sql.Types.VARCHAR); // p_resultMsg

			stmt.execute();

			int    resultCode = stmt.getInt   (4);

			
			List<Messaggio> list = new ArrayList<>();

			if (resultCode == 1) 
			{
				ResultSet rset = stmt.getResultSet();

				while (rset.next()) {
					Messaggio messaggio = Messaggio.create(rset);
					list.add(messaggio);
				}
				rset.close();
			}

			stmt.close();


			return list;

		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			this.close(rs, stmt, conn);
			Log.me(Common.APP_CODE, this, "getCalledList(final int deviceId, final int limit)");
		}
	}
	
	public List<Messaggio> getListMessaggi(final int provinciaId)
	{
		Log.ms(Common.APP_CODE, this, "getListMessaggi(final int provinciaId)");
		Log.mp("provinciaId", provinciaId);

		Connection        conn = null;
		CallableStatement stmt = null;
		ResultSet         rs   = null;

		try {
			String query = "{ CALL f2_app_getMessaggiList(?,?,?) }";

			Log.qs("getListMessaggi(...)");
			Log.qq(query);
			Log.qp(1, "provinciaId"  , provinciaId  );

			conn = this.getConnection();
			stmt = conn.prepareCall(query);

			stmt.setInt   (1, provinciaId  );

			stmt.registerOutParameter(2, java.sql.Types.INTEGER); // p_resultCode
			stmt.registerOutParameter(3, java.sql.Types.VARCHAR); // p_resultMsg

			stmt.execute();

			int    resultCode = stmt.getInt   (2);

			
			List<Messaggio> list = new ArrayList<>();

			if (resultCode == 1) 
			{
				ResultSet rset = stmt.getResultSet();

				while (rset.next()) {
					Messaggio messaggio = Messaggio.create(rset);
					list.add(messaggio);
				}
				rset.close();
			}

			stmt.close();


			return list;

		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			this.close(rs, stmt, conn);
			Log.me(Common.APP_CODE, this, "getCalledList(final int deviceId, final int limit)");
		}
	}
	
	
	public boolean CRUDMessaggio(final int loginId, final String token, final String operation, final Messaggio data)
	{
		Log.ms(Common.APP_CODE, this, "CRUDMessaggio(final int loginId, final String token, final Messaggio data)");
		Log.mp("loginId"     , loginId    			);
		Log.mp("token"       , token   	 			);
		Log.mp("codOpe"      , operation    		);
		Log.mp("messaggioId" , data.getId() 		);
		Log.mp("titolo"      , data.getTitolo()		);
		Log.mp("messaggio"   , data.getMessaggio() );
		Log.mp("visibileDa"  , data.getVisibileDa() );
		Log.mp("visibileA"   , data.getVisibileA()  );
		Log.mp("enabled"     , data.getEnabled()    );

		Connection        conn = null;
		CallableStatement stmt = null;
		ResultSet         rs   = null;

		try {
			String query = "{ CALL f2_wa_messaggioManage(?,?,?,?,?,?,?,?,?,?,?) }";

			Log.qs("createMessaggio(...)");
			Log.qq(query);
			Log.qp(1, "loginId"     , loginId    		   );
			Log.qp(2, "token"       , token   	 		   );
			Log.qp(3, "codOpe"      , operation   		   );
			Log.qp(4, "messaggioId" , data.getId() 		   );
			Log.qp(5, "messaggio"   , data.getMessaggio() );
			Log.qp(6, "visibileDa"  , data.getVisibileDa() );
			Log.qp(7, "visibileA"   , data.getVisibileA()  );
			Log.qp(8, "enabled"     , data.getEnabled()    );
			
			conn = this.getConnection();
			stmt = conn.prepareCall(query);

			stmt.setInt   (1, loginId      			);
			stmt.setString(2, token		   			);
			stmt.setString(3, operation        		);
			stmt.setInt   (4, data.getId()      	);
			stmt.setString(5, data.getTitolo()      );
			stmt.setString(6, data.getMessaggio()	);
			stmt.setString(7, data.getVisibileDa()	);
			stmt.setString(8, data.getVisibileA()	);
			stmt.setInt   (9, data.getEnabled()     );

			stmt.registerOutParameter(10, java.sql.Types.INTEGER); // p_resultCode
			stmt.registerOutParameter(11, java.sql.Types.VARCHAR); // p_resultMsg

			stmt.execute();

			int    resultCode = stmt.getInt   (10);

			return (resultCode == 1) ;

		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			this.close(rs, stmt, conn);
			Log.me(Common.APP_CODE, this, "CRUDMessaggio(final int loginId, final String token, final Messaggio data)");
		}
	}

	
	
}
