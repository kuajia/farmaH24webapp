package it.farmah24.sdk;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import it.farmah24.obj.LoginBean;
import it.farmah24.obj.Messaggio;
import it.farmah24.obj.Picture;




public interface ISdkWebapp 
{
	public static       String CRYPT_PASSWORD                  = "ApZj-TEa!5s+^yypHP3,52dU=h!]!^wDAzsmHrV5XKFg)Z3KDvs3JD5)+5{vNPq[";
	
	//=============================================================================================

	public LoginBean           	 doLogin (String password, String provincia);
	public boolean           	 doLogout(String loginId, String token);

	
	public Picture 				 getBannerImage		   (int bannerId);
	public List<Integer>  		 getBannerList    	   (int loginId, String token);
	public boolean 				 bannerCreate          (int loginId, String token, JSONObject data);
	public boolean 				 bannerUpdate          (int loginId, String token, int bannerId, JSONObject data);
	public boolean 				 bannerDelete          (int loginId, String token, int bannerId);
	
	
	public boolean               isTokenValid          (int loginId, String token);

	//----------------------------------------------------------------------------------------------
	
	public boolean 				 pushSend	   		   (int loginId, String token, int provinciaId, Messaggio message);
	public List<Messaggio> 		 pushList			   (int loginId, String token, int provinciaId);
	
	public List<Messaggio> 	     getListMessaggi(final int loginId, final String token, final int provinciaId);
	public boolean 				 crudMessaggio  (final int loginId, final String token, final String operazione, final Messaggio data);

	
}
