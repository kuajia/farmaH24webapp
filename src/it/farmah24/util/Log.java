package it.farmah24.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Log 
{
	private static final String SPACER = "    ";
	private static final int    PAD    = 20;
	private static final int    TPAD   = 11;
	private static final int    PPAD   = 15;
	
	static final Logger loggerINFO  = LogManager.getLogger("INFO" );
	static final Logger loggerERROR = LogManager.getLogger("ERROR");

	//---------------------------------------------------------------------------------------------
	//	Chiamate REST - entrypoint
	//---------------------------------------------------------------------------------------------
	
	public static void rs(String tag, String path, String method) {
		i( "\n\n"
		 + "=======================================================================\n"
		 + "REST["   + method.toUpperCase() + "] " + tag + path + " ### START");
	}	
	public static void re(String tag, String path, String method) {
		i( "\nREST[" + method.toUpperCase() + "] " + tag + path + " ### END\n"
		 + "=======================================================================\n\n");	
	}	
	public static void ri(String value) {
		i(SPACER + (value == null ? "null" : value ));
	}
	public static void ri(String type, String value) {
		i(SPACER + "[" + type + "] " + (value == null ? "null" : value ));
	}
	public static void ri(String type, boolean value) {
		i(SPACER + "[" + type + "] " + (value ? "TRUE" : "FALSE"));
	}
	public static void rp(String type, String description, String value) {
		i(SPACER + "[" + type + "] " + pad("(String)" ,TPAD) + pad(description,PAD) + ": " + (value == null ? "null" : "'" + value + "'"));
	}
	public static void rp(String type, String description, int value) {
		i(SPACER + "[" + type + "] " + pad("(int)"    ,TPAD) + pad(description,PAD) + ": " + value);
	}
	public static void rp(String type, String description, float value) {
		i(SPACER + "[" + type + "] " + pad("(float)"  ,TPAD) + pad(description,PAD) + ": " + value);
	}
	public static void rp(String type, String description, double value) {
		i(SPACER + "[" + type + "] " + pad("(double)" ,TPAD) + pad(description,PAD) + ": " + value);
	}
	public static void rp(String type, String description, boolean value) {
		i(SPACER + "[" + type + "] " + pad("(boolean)",TPAD) + pad(description,PAD) + ": " + (value ? "TRUE" : "FALSE"));
	}
	public static void rp(String type, String description, Object value) {
		i(SPACER + "[" + type + "] " + pad("(Object)" ,TPAD) + pad(description,PAD) + ": " + value);
	}

	public static void rx(int count) {
		i(SPACER + "Numero elementi trovati: " + count);
	}

	//---------------------------------------------------------------------------------------------

	public static void qs(String method) {
		i("\n" + SPACER+SPACER+ "==> " + method);
	}	
	public static void qq(String query) {
		i(SPACER+SPACER+SPACER + "query:  " + SPACER+SPACER+SPACER + "      " + query );
	}	
	public static void qp(int number, String name, String value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "params: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(String)" ,TPAD) + " " + (value == null ? "null" : "'" + value + "'"));
	}
	public static void qp(int number, String name, int value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "params: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(int)"    ,TPAD) + " " + value);
	}
	public static void qp(int number, String name, float value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "params: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(float)"  ,TPAD) + " " + value);
	}
	public static void qp(int number, String name, double value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "params: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(double)" ,TPAD) + " " + value);
	}
	public static void qp(int number, String name, boolean value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "params: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(boolean)",TPAD) + " " + (value ? "TRUE" : "FALSE"));
	}
	public static void qp(int number, String name, Object value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "params: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(Object)" ,TPAD) + " " + value.toString());
	}
	public static void qr(int number, String name, String value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "result: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(String)" ,TPAD) + " " + (value == null ? "null" : "'" + value + "'"));
	}
	public static void qr(int number, String name, int value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "result: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(int)"    ,TPAD) + " " + value);
	}
	public static void qr(int number, String name, float value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "result: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(float)"  ,TPAD) + " " + value);
	}
	public static void qr(int number, String name, double value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "result: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(double)" ,TPAD) + " " + value);
	}
	public static void qr(int number, String name, boolean value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "result: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(boolean)",TPAD) + " " + (value ? "TRUE" : "FALSE"));
	}
	public static void qr(int number, String name, Object value) {
		i(SPACER+SPACER+SPACER + (number == 1 ? "result: " : "        ") + number + ": " + pad(name, PPAD) + ": " + pad("(Object)" ,TPAD) + " " + value.toString());
	}
	
	//---------------------------------------------------------------------------------------------
	//	Metodi interni
	//---------------------------------------------------------------------------------------------
	
	public static void ms(String code, Object o, String method) {
		Log.i( "\n" + now() + SPACER + "---> " + code + "/" + o.getClass().getSimpleName() + " /" + method + " *** START");
	}
	public static void me(String code, Object o, String method) {
		Log.i( "\n" + now() + SPACER + "<--- " + code + "/" + o.getClass().getSimpleName() + " /" + method + " *** END");
	}

	public static void ms(String method) {
		i( "\n" + SPACER + "---> " + method + " *** START");
	}	
	public static void me(String method) {
		i( "\n" + SPACER + "<--- " + method + " *** END");
	}	
	public static void mi(String value) {
		i(SPACER + SPACER + value);
	}	
	public static void mp(String description, String value) {
		i(SPACER + SPACER + pad("(String)" ,TPAD) + pad(description,PAD) + ": " + (value == null ? "null" : "'" + value + "'"));
	}
	public static void mp(String description, int value) {
		i(SPACER + SPACER + pad("(int)"    ,TPAD) + pad(description,PAD) + ": " + value);
	}
	public static void mp(String description, float value) {
		i(SPACER + SPACER + pad("(float)"  ,TPAD) + pad(description,PAD) + ": " + value);
	}
	public static void mp(String description, double value) {
		i(SPACER + SPACER + pad("(double)" ,TPAD) + pad(description,PAD) + ": " + value);
	}
	public static void mp(String description, boolean value) {
		i(SPACER + SPACER + pad("(boolean)",TPAD) + pad(description,PAD) + ": " + (value ? "TRUE" : "FALSE"));
	}
	public static void mp(String description, Object value) {
		i(SPACER + SPACER + pad("(Object)",TPAD) + pad(description,PAD) + ": (Object) " + value.toString());
	}

	public static void mx(int count) {
		i(SPACER + SPACER + "Numero elementi trovati: " + count);
	}
	public static void mi(String description, boolean value) {
		i(SPACER + SPACER + description + ": " + (value ? "TRUE" : "FALSE"));
	}
	public static void mi(String description, String value) {
		i(SPACER + SPACER + description + ": " + (value == null ? "null" : "'" + value + "'"));
	}

	//---------------------------------------------------------------------------------------------
	public static void e(String name, String error) {
		e("ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR");
		e("Error: " + error);
	}
	public static void e(String name, Exception e) {
		e("ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR");
		e("Exception type: " + name);
		e(ExceptionUtils.getStackTrace(e));
	}

	public static void e(Exception e) {
		loggerINFO.error(e);
	}

	//---------------------------------------------------------------------------------------------

	public static void i(String message) {
		loggerINFO.info(message);
//		System.out.println(message);
	}

	public static void e(String message) {
		loggerERROR.error(message);
//		System.err.println(message);
	}
	
	//---------------------------------------------------------------------------------------------
	
	private static String pad(String s, int len) 
	{
		String result = s;
		while (result.length() < len)
			result += " ";
		return result;
	}

	//---------------------------------------------------------------------------------------------

	private static String now() {
		return Util.getNowDate() + " ";
	}
	}
