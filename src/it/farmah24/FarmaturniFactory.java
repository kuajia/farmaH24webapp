package it.farmah24;

import it.farmah24.sdk.ISdkWebapp;
import it.farmah24.sdk.SdkMysqlWebapp;

public class FarmaturniFactory 
{
	public static ISdkWebapp getSdkWebapp() {
		return SdkMysqlWebapp.getInstance();
	}
	
}
