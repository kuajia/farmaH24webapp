package it.farmah24.sdk;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import it.farmah24.business.BusinessBanner;
import it.farmah24.business.BusinessLogin;
import it.farmah24.business.BusinessMessaggio;
import it.farmah24.business.BusinessPush;
import it.farmah24.obj.LoginBean;
import it.farmah24.obj.Messaggio;
import it.farmah24.obj.Picture;



public class SdkMysqlWebapp extends SdkMysql implements ISdkWebapp
{	
	private static SdkMysqlWebapp   		 instance;
	
	private static BusinessBanner            businessBanner;
	private static BusinessLogin    		 businessLogin;
	private static BusinessPush			 	 businessPush;
	private static BusinessMessaggio  		 businessMessaggio;
	
	private SdkMysqlWebapp () 
	{		
		super();
		
		businessBanner            = new BusinessBanner();
		businessLogin    		  = new BusinessLogin();
		businessPush			  = new BusinessPush();
		businessMessaggio  		  = new BusinessMessaggio();

	}

	public static ISdkWebapp getInstance() 
	{
		if (instance == null)
			synchronized(SdkMysqlWebapp.class) {
				if (instance == null)
					instance = new SdkMysqlWebapp();
			}
		
		return instance;		
	}
	
	//=============================================================================================
	//=============================================================================================

	@Override
	public LoginBean doLogin(final String username, final String password) {
		return businessLogin.doLogin(username, password);
	}
	
	@Override
	public boolean doLogout(String loginId, String token) {
		return businessLogin.doLogout(loginId, token);
	}

	//----------------------------------------------------------------------------------------------

	@Override
	public Picture getBannerImage(final int bannerId) {
		return businessBanner.getBannerImage(bannerId);
	}

	@Override
	public List<Integer> getBannerList(final int loginId, final String token) {
		return businessBanner.getBannerList(loginId, token);
	}

	@Override
	public boolean bannerCreate(final int loginId, final String token, final JSONObject data) {
		return businessBanner.bannerCreate(loginId, token, data);
	}

	@Override
	public boolean bannerUpdate(final int loginId, final String token, final int bannerId, final JSONObject data) {
		return businessBanner.bannerUpdate(loginId, token, bannerId, data);
	}

	@Override
	public boolean bannerDelete(final int loginId, final String token, final int bannerId) {
		return businessBanner.bannerDelete(loginId, token, bannerId);
	}

	//----------------------------------------------------------------------------------------------
	
	@Override
	public boolean pushSend(final int loginId, final String token, final int provinciaId, final Messaggio message) {
		return businessPush.pushSend(loginId, token, provinciaId, message);
	}
	
	@Override
	public List<Messaggio> pushList(final int loginId, final String token, final int provinciaId) {
		return businessPush.pushList(loginId, token, provinciaId);
	}

	@Override
	public boolean isTokenValid(int loginId, String token) {
		return businessLogin.isTokenValid(loginId, token);
	}

	//==============================================================================================
	
	@Override
	public List<Messaggio> getListMessaggi(final int loginId, final String token, final int provinciaId) {		
		return businessMessaggio.getListMessaggi(loginId, token, provinciaId);
	}
	
	public boolean crudMessaggio(final int loginId, final String token, final String operazione, final Messaggio data)
	{
		return businessMessaggio.CRUDMessaggio(loginId, token, operazione, data);
	}

}
