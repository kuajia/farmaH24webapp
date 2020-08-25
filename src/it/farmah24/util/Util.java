package it.farmah24.util;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import it.farmah24.rest.exception.InvalidDateFormatException;

public class Util {

	private static final String DATE_FORMAT_YMD   = "yyyy-MM-dd";
	private static final String DATE_FORMAT_DMY   = "dd/MM/yyyy";
	private static final String TIME_FORMAT_HM    = "HH:mm";
	private static final String TIME_FORMAT_HMS   = "HH:mm:ss";
	private static final String DATE_FORMAT_SQL   = "%d-%m-%Y %H:%i:%s";
	private static final String DAY_FORMAT_SQL    = "%d-%m-%Y";
	private static final String TIME_FORMAT_SQL   = "%H:%i:%s";
	private static final String COMPLETE_FORMAT	  = "yyyyMMdd-HHmmssSSS";

	
	private static SimpleDateFormat formatterDay  	  = new SimpleDateFormat(DATE_FORMAT_YMD);
	private static SimpleDateFormat formatterDate 	  = new SimpleDateFormat(DATE_FORMAT_DMY);
	private static SimpleDateFormat formatterTime     = new SimpleDateFormat(TIME_FORMAT_HM );
	private static SimpleDateFormat formatterComplete = new SimpleDateFormat(COMPLETE_FORMAT);
	
	public static boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
	
	public static Date convertDay(String day) {
		try {
			return formatterDay.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new InvalidDateFormatException(day, true);
		}
	}
	
	public static String dateToYMD(Date date) {
		return formatterDay.format(date);
	}
	
	public static java.sql.Date convertSQLDay(String day) {
		try {
			return new java.sql.Date(formatterDay.parse(day).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new InvalidDateFormatException(day, true);
		}
	}

	public static Date convertTime(String time) {
		try {
			return formatterTime.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new InvalidDateFormatException(time, true);
		}
	}

	public static java.sql.Time convertSQLTimeShort(String time) {
		try {
			return new java.sql.Time(formatterTime.parse(time).getTime());
		} catch (ParseException e) {
			throw new InvalidDateFormatException(time, true);
		}
	}
	
	public static boolean checkDay(String day) {
		
		boolean ok = true;
		try {
			formatterDay.parse(day);
		} catch (ParseException e) {
			ok = false;
		}
		return ok;
		
	}

	public static boolean checkTime(String time) {
		
		boolean ok = true;
		try {
			formatterTime.parse(time);
		} catch (ParseException e) {
			ok = false;
		}
		return ok;
	}

	public static String getNow() {
		return formatterComplete.format(new Date());
	}
	
	public static String getNowDate() {
		return formatDate(new Date());
	}

	public static String toYMDHMSm(Date date) {
		return formatterComplete.format(date);
	}
	
	public static String getNowYMD() {
		return formatDay(new Date());
	}

	public static String getNowTime() {
		return formatTime(new Date());
	}
			
	public static String formatDay(Date day) {
		return formatterDay.format(day);
	}
	
	public static String formatDate(Date date) {
	return formatterDate.format(date);
	}
		
	public static String formatTime(Date date) {
		return formatterTime.format(date);
	}
	
	public static String formatTime(Time time) {
	return formatterTime.format(time);
	}

    //----------------------------------------------------------------------------------------------

    public static Date parseDateYMD(String s, Date defaultValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YMD);
        try {
            Date date = dateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static Date parseTimeHM(String s, Date defaultValue) { return parseDateHM(s, defaultValue); }
    public static Date parseDateHM(String s, Date defaultValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_HM);
        try {
            Date date = dateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static Date parseTimeHMS(String s, Date defaultValue) { return parseDateHMS(s, defaultValue); }
    public static Date parseDateHMS(String s, Date defaultValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_HMS);
        try {
            Date date = dateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    //----------------------------------------------------------------------------------------------

    public static java.sql.Date convertDateToSQL(java.util.Date date)
    {
        if (date != null)
            return new java.sql.Date(date.getTime());
        else return null;
    }

    //----------------------------------------------------------------------------------------------
	
	public static String getSqlFormatDate() {
		return DATE_FORMAT_SQL;
	}
	
	public static String getSqlFormatDay() {
		return DAY_FORMAT_SQL;
	}
	
	public static String getSqlFormatTime() {
		return TIME_FORMAT_SQL;
	}

	public static String buildBookCode(String prefix, int prog) {
		return (prefix==null?"":prefix) + String.format("%03d", prog);
	}
	
	public static String buildTicketCode(String prefix, int prog) {
		return (prefix==null?"":prefix) + String.format("%03d", prog);
	}	
	
	//-------------------------------------------------------------------------
	
	public static int getDOW(Date date)
	{
		Calendar cData = Calendar.getInstance();
		cData.setTime(date);
		return (cData.get(Calendar.DAY_OF_WEEK) + 5) % 7;	// correzione per lun=0, mar=1, ... , dom=6		
	}

	public static Date getFirstDOW(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1*getDOW(date));
		return new Date(c.getTimeInMillis());
	}

	public static Date getLastDOW(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 6-getDOW(date));
		return new Date(c.getTimeInMillis());
	}
	
	//-------------------------------------------------------------------------
	
	public static Date addDays(Date date, int days2add)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days2add);
		return new Date(c.getTimeInMillis());
	}
	
	//-------------------------------------------------------------------------
	
	public static void main(String[] args) {
		Log.i(getNowTime());
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


	public static Blob getBlob(ResultSet rs, String fieldName) {
		try {
			return rs.getBlob(fieldName);
		} catch(Exception e ) {
			return null;
		}
	}

	public static byte[] getBytes(Blob blob) {
		try {
			return blob.getBytes(1L, (int)(blob.length())); 
		} catch (SQLException e) {
			return null;
		}
	}
	
	//==============================================================================================
	
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
			} catch (JSONException e) {}
			return defaultValue;
		}
		
		public static Date getDate(JSONObject jo, String key){
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			String strDate = getString(jo, key, null);
            try {
				return formatter.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            return null;

		}

		public static JSONObject getJSONObject(JSONObject jo, String key) 
		{
			try {
				if (!jo.isNull(key))
					return jo.getJSONObject(key);
			} catch (JSONException e) {}
			return null;
		}

		public static JSONObject getJSONObject(JSONArray ja, int index) 
		{
			try {
				return ja.getJSONObject(index);
			} catch (JSONException e) {}
			return null;
		}

		public static JSONArray getJSONArray(JSONObject jo, String key) 
		{
			try {
				if (!jo.isNull(key))
					return jo.getJSONArray(key);
			} catch (JSONException e) {}
			return null;
		}
		
		//=========================================================================
    public static boolean toBool(JSONObject o, String paramName, boolean defaultBool)
    {
        try {
            return o.getBoolean(paramName);
        } catch (JSONException e) {
            return defaultBool;
        }
    }

    public static Integer toInt(JSONObject o, String paramName, Integer defaultInt)
    {
        try {
            return o.getInt(paramName);
        } catch (JSONException e) {
            return defaultInt;
        }
    }

    public static Long toLong(JSONObject o, String paramName, Long defaultLong)
    {
        try {
            return o.getLong(paramName);
        } catch (JSONException e) {
            return defaultLong;
        }
    }

    public static Float toFloat(JSONObject o, String paramName, Float defaultFloat)
    {
        try {
            return Double.valueOf(o.getDouble(paramName)).floatValue();
        } catch (JSONException e) {
            return defaultFloat;
        }
    }

    public static Double toDouble(JSONObject o, String paramName, Double defaultDouble)
    {
        try {
            return o.getDouble(paramName);
        } catch (JSONException e) {
            return defaultDouble;
        }
    }

    public static String toString(JSONObject o, String paramName, String defaultString)
    {
        try {
            String s = o.getString(paramName);

            if (s != null && (s.trim().length() == 0 || s.equalsIgnoreCase("null")))
                s = null;

            if (s == null)
                return defaultString;
            else
                return s;

        } catch (JSONException e) {
//          e.printStackTrace();
            return defaultString;
        }
    }

    public static Date toDate(JSONObject o, String paramName, Date defaultDate)
    {
        try {
            String s = o.getString(paramName);

            if (s != null && (s.trim().length() == 0 || s.equalsIgnoreCase("null")))
                s = null;

            if (s == null)
                return defaultDate;

            Date date = formatterDay.parse(s);
            return date;

        } catch (JSONException e) {
            return defaultDate;
        } catch (ParseException e) {
            return defaultDate;
        }
    }

    public static Date toTime(JSONObject o, String paramName, Date defaultTime)
    {
        try {
            String s = o.getString(paramName);

            if (s != null && (s.trim().length() == 0 || s.equalsIgnoreCase("null")))
                s = null;

            if (s == null)
                return defaultTime;

            SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_HMS);
            Date date = dateFormat.parse(s);
            return date;

        } catch (JSONException e) {
            return defaultTime;
        } catch (ParseException e) {
            return defaultTime;
        }
    }

    public static JSONObject toObject(JSONObject o, String paramName)
    {
        try {
            return o.getJSONObject(paramName);
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONObject toObject(JSONArray a, int index)
    {
        try {
            return a.getJSONObject(index);
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONArray toArray(JSONObject o, String paramName)
    {
        try {
            return o.getJSONArray(paramName);
        } catch (JSONException e) {
            return null;
        }
    }

	//==============================================================================================
	
	public static boolean isValidId(int id) {
		return id > 0;
	}

	public static String nvl(String value) 
	{
		if (value == null)
			 return value;
		else return value.trim();
	}
	
	//=========================================================================

	public static String MD5(String md5) 
	{
	   try {
	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(md5.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	       }
	        return sb.toString();
	    } catch (java.security.NoSuchAlgorithmException e) {
			Log.e(e.getClass().getName(), e);	    	
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);				
	    }
	}

	//---------------------------------------------------------------------------------------------

	public static String encrypt(String password, String plainText)
	{
		try {
			AESCrypt aes = new AESCrypt(password);
			return aes.encrypt(plainText);
		} catch(Exception e) {
			return null;
		}
	}
	
	public static String decrypt(String password, String encrypted)
	{
		try {
			AESCrypt aes = new AESCrypt(password);
			return aes.decrypt(encrypted);
		} catch(Exception e) {
			return null;
		}
	}
	
	public static String getNowDay() {
		return formatDay(new Date());
	}
}
