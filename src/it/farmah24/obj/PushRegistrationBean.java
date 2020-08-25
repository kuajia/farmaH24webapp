package it.farmah24.obj;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.farmah24.util.Util;

public class PushRegistrationBean {
	private String registrationId;

	//----------------------------------------------------------------------------------------------
	
	public static PushRegistrationBean create(ResultSet rs) throws SQLException 
	{		
		PushRegistrationBean bean = new PushRegistrationBean();

		bean.setRegistrationId(Util.getString(rs, "registrationId"));
		
		return bean;
	}


	//----------------------------------------------------------------------------------------------
	
	public String getRegistrationId() {
		return registrationId;
	}
	
	
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	//----------------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "PushRegistrationBean [registrationId=" + registrationId + "]";
	}
}
