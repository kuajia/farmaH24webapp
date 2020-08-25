package it.farmah24.pushy;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.farmah24.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("deprecation")
public class GoogleAPI 
{
    public static ObjectMapper mapper = new ObjectMapper();
    
    // Insert your Secret API Key here
    public static final String API_KEY_AND_TEST = "AAAAEjbK_V8:APA91bGuzlpjpwVqxlWEDCFV6TVnJ_f5W8xKpVfxbmaKoEaDqIvgTcC5JB-6Dq4jHFg_2TtooyqL5pVPr7c0ji6x9Yp0QdvQq874YvprPihw5CV6fLVav6j8TEQaCrlHPlJ6QMb2m5L0";
    public static final String API_KEY_AND_PROD = "AAAAEjbK_V8:APA91bGuzlpjpwVqxlWEDCFV6TVnJ_f5W8xKpVfxbmaKoEaDqIvgTcC5JB-6Dq4jHFg_2TtooyqL5pVPr7c0ji6x9Yp0QdvQq874YvprPihw5CV6fLVav6j8TEQaCrlHPlJ6QMb2m5L0";
        
    public static final String API_KEY_IOS_TEST = "AAAAEjbK_V8:APA91bGuzlpjpwVqxlWEDCFV6TVnJ_f5W8xKpVfxbmaKoEaDqIvgTcC5JB-6Dq4jHFg_2TtooyqL5pVPr7c0ji6x9Yp0QdvQq874YvprPihw5CV6fLVav6j8TEQaCrlHPlJ6QMb2m5L0";
    public static final String API_KEY_IOS_PROD = "AAAAEjbK_V8:APA91bGuzlpjpwVqxlWEDCFV6TVnJ_f5W8xKpVfxbmaKoEaDqIvgTcC5JB-6Dq4jHFg_2TtooyqL5pVPr7c0ji6x9Yp0QdvQq874YvprPihw5CV6fLVav6j8TEQaCrlHPlJ6QMb2m5L0";
    
    public static final String API_KEY_AND = API_KEY_AND_PROD;
    public static final String API_KEY_IOS = API_KEY_IOS_PROD;

    
    public static final String URL = "https://fcm.googleapis.com/fcm/send";


    @SuppressWarnings({"unchecked", "resource" })
	public static List<String> sendPushMessage(final PushRequest req, final String system) throws Exception 
    {
        // Get custom HTTP client and Create POST request
		HttpClient client = new DefaultHttpClient();
        HttpPost request  = new HttpPost(URL);
        request.addHeader("Content-Type" , "application/json");
        
    	Log.i("\n### PushyAPI.sendFirebase to url - " + URL);
    	
    	long startTime = new Date().getTime();

        // Setting api_key from system
        if      (system.equals("AND")) request.addHeader("Authorization", "key=" + API_KEY_AND);
        else if (system.equals("IOS")) request.addHeader("Authorization", "key=" + API_KEY_IOS);
        else throw new Exception("Invalid system (\"" + system + "\") - System must be AND for Android or IOS for iOS");

        
        // Convert post data to JSON
        String json = mapper.writeValueAsString(req);
    	Log.i("\n### PushyAPI.sendFirebase json - " + json);

        // Send post data as string
        request.setEntity(new StringEntity(json));

        // Execute the request
        HttpResponse response;
        try {
        	response = client.execute(request, new BasicHttpContext());
		} catch (Exception e) {			
			Log.e(e.getClass().getName(), e);
        	Log.e("### PushyAPI.sendFirebase - exception");
        	throw new Exception(e);
        }

    	long endTime = new Date().getTime();
    	Log.i("### PushyAPI.sendPushy - execution time: " + (endTime - startTime) + "ms");

        // Get response JSON as string	
        String responseJSON = EntityUtils.toString(response.getEntity());
    	Log.i("### PushyAPI.sendPushy - responseJSON: " + responseJSON);

        // Convert JSON response into HashMap
        Map<String, Object> map = mapper.readValue(responseJSON, Map.class);

        int    success = (int)    map.get("success");
        int    failure = (int)    map.get("failure");

        Log.i("success: " + success);
        Log.i("failure: " + failure);

        
        List<LinkedHashMap<String, String>> results = (List<LinkedHashMap<String, String>>) map.get("results");        
        List<String> tokensToDelete = null;
    
        
        if (failure > 0)	// there are errors 
        {
        	Log.i("### PushyAPI.sendPushy - ERROR - not all messages sent!");
        	        	
        	tokensToDelete = new ArrayList<String>();
        	        	
        	for (int idx=0; idx < results.size(); idx++)
        	{
        		LinkedHashMap<String, String> item = results.get(idx);

            	if (item.containsKey("error") && item.get("error").equalsIgnoreCase("NotRegistered"))
            	{
            		String token = req.registration_ids[idx];
            		tokensToDelete.add(token);
            		Log.i("###                    - (" + idx + ") - " + token);
				}
        	}

        	Log.i(" ");
        }
        
        return tokensToDelete;
    }
}