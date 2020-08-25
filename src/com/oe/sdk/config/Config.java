package com.oe.sdk.config;

/**
 * 
 * @author Alessandro Bonetti
 *
 */
public final class Config 
{
	private String hostAddress;
	private int    hostPort = 80;
	private String proxyAddress;
	private int    proxyPort;
	private String connectionType = "HTTP";
	private String username;
	private String password;

	//----------------------------------------------------------------------------------------------

	public String getHostAddress() {
		return hostAddress;
	}
	public int getHostPort() {
		return hostPort;
	}
	public String getProxyAddress() {
		return proxyAddress;
	}
	public int getProxyPort() {
		return proxyPort;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	
	
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}
	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//----------------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Config [hostAddress=" + hostAddress + ", hostPort=" + hostPort + ", proxyAddress=" + proxyAddress
				+ ", proxyPort=" + proxyPort + ", connectionType=" + connectionType + ", username=" + username
				+ ", password=" + password + "]";
	}
}