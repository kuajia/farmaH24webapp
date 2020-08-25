package it.farmah24.sdk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import it.farmah24.util.Log;


public class SdkMysql 
{
	protected static final int    SQL_ERROR_CODE_DUPLICATE_KEY = 1062;	
	protected static final String JDBC_DATASOURCE = "jdbc/farmaturni";

	protected DataSource ds;

	protected SdkMysql () 
	{	
		javax.naming.Context initCtx;
		try {
			initCtx = new InitialContext();
			javax.naming.Context envCtx = (javax.naming.Context) initCtx.lookup("java:comp/env");
			this.ds = (DataSource) envCtx.lookup(JDBC_DATASOURCE); 
		} catch (NamingException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}	
		
	}

	protected interface IStatement 
	{
		PreparedStatement build(Connection connection) throws SQLException;
		Object createElement(ResultSet rs) throws SQLException; 
	}


	//=========================================================================

	public static int getInt(ResultSet rs, String fieldName) {
		return getInt(rs, fieldName, 0);
	}
	public static int getInt(ResultSet rs, String fieldName, int defaultValue)
	{
		try {
			return rs.getInt(fieldName);
		} catch(Exception e ) {
			return defaultValue;
		}
	}

	public static String getString(ResultSet rs, String fieldName) {
		return getString(rs, fieldName, null);
	}
	public static String getString(ResultSet rs, String fieldName, String defaultValue)
	{
		try {
			return rs.getString(fieldName);
		} catch(Exception e ) {
			return defaultValue;
		}
	}

	public static Double getDouble(ResultSet rs, String fieldName) {
		return getDouble(rs, fieldName, null);
	}
	public static Double getDouble(ResultSet rs, String fieldName, Double defaultValue)
	{
		try {
			return rs.getDouble(fieldName);
		} catch(Exception e ) {
			return defaultValue;
		}
	}

	public static Date getDate(ResultSet rs, String fieldName) {
		return getDate(rs, fieldName, null);
	}
	public static Date getDate(ResultSet rs, String fieldName, Date defaultValue)
	{
		try {
			return rs.getDate(fieldName);
		} catch(Exception e ) {
			return defaultValue;
		}
	}

	public static Time getTime(ResultSet rs, String fieldName) {
		return getTime(rs, fieldName, null);
	}
	public static Time getTime(ResultSet rs, String fieldName, Time defaultValue)
	{
		try {
			return rs.getTime(fieldName);
		} catch(Exception e ) {
			return defaultValue;
		}
	}

	public static Timestamp getTimestamp(ResultSet rs, String fieldName) {
		return getTimestamp(rs, fieldName, null);
	}
	public static Timestamp getTimestamp(ResultSet rs, String fieldName, Timestamp defaultValue)
	{
		try {
			return rs.getTimestamp(fieldName);
		} catch(Exception e ) {
			return defaultValue;
		}
	}

	//=========================================================================
	
	public static int getInt(JSONObject jo, String key) {
		return getInt(jo, key, 0);
	}
	public static int getInt(JSONObject jo, String key, int defaultValue)
	{
		try {
			return jo.getInt(key);
		} catch (JSONException e) {
			return defaultValue;
		}
	}
	
	public static Double getDouble(JSONObject jo, String key) {
		return getDouble(jo, key, null);
	}
	public static Double getDouble(JSONObject jo, String key, Double defaultValue)
	{
		try {
			return jo.getDouble(key);
		} catch (JSONException e) {
			return defaultValue;
		}
	}
	
	public static String getString(JSONObject jo, String key) {
		return getString(jo, key, null);
	}
	public static String getString(JSONObject jo, String key, String defaultValue)
	{
		try {
			if (!jo.isNull(key))
				return jo.getString(key);
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	public static JSONObject getJSONObject(JSONObject jo, String key) 
	{
		try {
			if (!jo.isNull(key))
				return jo.getJSONObject(key);
		} catch (JSONException e) {
		}
		return null;
	}

	public static JSONObject getJSONObject(JSONArray ja, int index) 
	{
		try {
			return ja.getJSONObject(index);
		} catch (JSONException e) {
		}
		return null;
	}

	public static JSONArray getJSONArray(JSONObject jo, String key) 
	{
		try {
			if (!jo.isNull(key))
				return jo.getJSONArray(key);
		} catch (JSONException e) {
		}
		return null;
	}




	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getList(IStatement statement) 
	{
		List elements = new ArrayList<Object>();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
				
		try {
			conn = this.getConnection();
			
			st = statement.build(conn);
			rs = st.executeQuery();
			while (rs.next()) {
				elements.add(statement.createElement(rs));
			}			
			return elements;	
		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
		} finally {
			this.close(rs, st, conn);
		}		
		
	}
	
	protected Object getElement(IStatement statement) {
		return this.getElement(statement, false);
	}
	
	protected Object getElement(IStatement statement, boolean notFoundReturnsNull) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
				
		try {
			conn = this.getConnection();
			
			st = statement.build(conn);
			rs = st.executeQuery();
			if (rs.first()) {
				Object element = statement.createElement(rs);
				return element;
			} else {
				if (notFoundReturnsNull) {
					return null;
				} else {
					throw new WebApplicationException(Response.Status.NOT_FOUND);									
				}
			}
		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
		} finally {
			this.close(rs, st, conn);
		}		
		
	}
	
	protected java.sql.Date convertDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());			
		} else {
			return null;
		}
	}

	//-------------------------------------------------------------------------
	
	protected Connection getConnection() throws NamingException, SQLException {
		Connection connection = this.ds.getConnection();	
		connection.setAutoCommit(true);
		return connection;
	}
	

	protected int selectCount(String query, int intParam) {
		
		Log.i("selectCount started");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
				
		try {
			conn = this.getConnection();
			Log.i("query = " + query);
			st = conn.prepareStatement(query);
			st.setInt(1, intParam);
			rs = st.executeQuery();
			if (rs.first()) {
				return rs.getInt("counter");
			} else {
				return 0;
			}
		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
		} finally {
			this.close(rs, st, conn);
		}										
	}

	protected int selectCount(String query, String stringParam) {
		
		Log.i("selectCount started");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
				
		try {
			conn = this.getConnection();
			Log.i("query = " + query);
			st = conn.prepareStatement(query);
			st.setString(1, stringParam);
			rs = st.executeQuery();
			if (rs.first()) {
				return rs.getInt("counter");
			} else {
				return 0;
			}
		} catch (NamingException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
		} finally {
			this.close(rs, st, conn);
		}										
	}

	protected void updateWithId(String query, final int id) {
		
		Log.i("updateWithId started, id = " + id);
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
				
		try {
			conn = this.getConnection();
			Log.i("query = " + query);
			st = conn.prepareStatement(query);
			int index = 0;
			st.setInt(++index, id);
			st.executeUpdate();
		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
		} finally {
			this.close(rs, st, conn);
		}						
				
	}

	protected void updateWithIdAndParam1(String query, final int id, int param1) {
		
		Log.i("updateWithId started, id = " + id + ",param1 = " + param1);
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
				
		try {
			conn = this.getConnection();
			Log.i("query = " + query);
			st = conn.prepareStatement(query);
			int index = 0;
			st.setInt(++index, param1);
			st.setInt(++index, id);
			st.executeUpdate();
		} catch (NamingException | SQLException e) {			
			Log.e(e.getClass().getName(), e);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
		} finally {
			this.close(rs, st, conn);
		}						
				
	}

	protected boolean isDuplicateKey(SQLException ex) {
		return ex.getErrorCode() == SQL_ERROR_CODE_DUPLICATE_KEY;
	}
	
	protected Date getNow() {
		return new Date();
	}
	

	//-------------------------------------------------------------------------
/*
	private IntValue createIntValue(ResultSet rs) throws SQLException 
	{
		IntValue bean = new IntValue();
		bean.setValue(getInt(rs, "value"));
		return bean;
	}
*/

	//-------------------------------------------------------------------------
	
	protected void close(PreparedStatement st) {	
		close(null, st, null);
	}
	
	protected void close(ResultSet rs) {	
		close(rs, null, null);
	}
	
	protected void close(ResultSet rs, PreparedStatement st) {	
		close(rs, st, null);
	}
	
	protected void close(Connection conn) {
		close(null, null, conn);
	}
	
	protected void close(ResultSet rs, PreparedStatement st, Connection conn) 
	{	
		if (rs   != null) try { rs.close();   } catch(SQLException e) { Log.e("\n\n\n######### resultSet  NOT CLOSED\n\n\n"); }
		if (st   != null) try { st.close();   } catch(SQLException e) { Log.e("\n\n\n######### statement  NOT CLOSED\n\n\n"); }
		if (conn != null) try { conn.setAutoCommit(true);
		                        conn.close(); } catch(SQLException e) { Log.e("\n\n\n######### connection NOT CLOSED\n\n\n"); }
	}
}
