package com.oe.sdk.connection;

import com.oe.sdk.config.Config;
import com.oe.sdk.exceptions.SMSCConnectionException;
import com.oe.sdk.exceptions.SMSCException;
import com.oe.sdk.exceptions.SMSCNotConfiguredException;


public class SMSCConnectionFactory 
{
	private Config  config;
	

	public SMSCConnectionFactory(String hostAddress, int hostPort, String proxyAddress, int proxyPort)
	{
		config = new Config();
		config.setHostAddress (hostAddress );
		config.setHostPort    (hostPort    );
		config.setProxyAddress(proxyAddress);
		config.setProxyPort   (proxyPort   );
	}

	/**
	 * The function provides the opening of the connection, implementing a reflection pattern.
	 * 
	 * @param username login username
	 * @param password login password
	 * @throws SMSCNotConfiguredException
	 * @throws SMSCConnectionException
	 */
	public SMSCConnection openConnection(final String username, final String password) throws SMSCException 
	{
		this.config.setUsername(username);
		this.config.setPassword(password);
		
		SMSCHTTPConnection connection = new SMSCHTTPConnection();
		connection.login(config.getHostAddress(), config.getHostPort(), config.getUsername(), config.getPassword(), config.getProxyAddress(), config.getProxyPort());			
		return connection;
	}
}