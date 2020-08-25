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

import java.util.Date;
import java.util.Map;

@SuppressWarnings("deprecation")
public class PushyAPI 
{
    public static ObjectMapper mapper = new ObjectMapper();
    
    // Insert your Secret API Key here
    public static final String API_KEY_AND = "9b81f7657d0f8f22dd6fcf36a2c5d82d1ea5e6cf58f7e2f0ae5b1d4059c8f1fc";
    public static final String API_KEY_IOS = "9b81f7657d0f8f22dd6fcf36a2c5d82d1ea5e6cf58f7e2f0ae5b1d4059c8f1fc";
    public static final String URL         = "https://api.pushy.me/push?api_key=";


    @SuppressWarnings({ "unchecked", "resource" })
	public static void sendPushy(final PushRequest req, final String system) throws Exception 
    {
        // Get custom HTTP client
		HttpClient client = new DefaultHttpClient();

		String url = URL;
        // Setting api_key from system
        if      (system.equals("AND")) url += API_KEY_AND;
        else if (system.equals("IOS")) url += API_KEY_IOS;
        else throw new Exception("Invalid system (\"" + system + "\") - System must be AND for Android or IOS for iOS");

    	Log.i("\n### PushyAPI.sendPushy to url\n    " + url);
    	
    	long startTime = new Date().getTime();
    	// Create POST request
        HttpPost request = new HttpPost(url);

        // Set content type to JSON
        request.addHeader("Content-Type", "application/json");

        // Convert post data to JSON
        String json = mapper.writeValueAsString(req);

        // Send post data as string
        request.setEntity(new StringEntity(json));

        // Execute the request
        HttpResponse response = null;
        try {
        	response = client.execute(request, new BasicHttpContext());
        } catch(Exception e) {
			Log.e(e.getClass().getName(), e);
        	Log.e("### PushyAPI.sendPushy - exception");
        	throw new Exception(e);
        }

    	long endTime = new Date().getTime();
    	Log.i("### PushyAPI.sendPushy - execution time: " + (endTime - startTime) + "ms");
        
        // Get response JSON as string
        String responseJSON = EntityUtils.toString(response.getEntity());
    	Log.i("### PushyAPI.sendPushy - responseJSON: " + responseJSON);

        // Convert JSON response into HashMap
        Map<String, Object> map = mapper.readValue(responseJSON, Map.class);

        
        // Got an error?
        if (map.containsKey("error")) {
            // Throw it
        	Log.i("### PushyAPI.sendPushy - ERROR");
            throw new Exception(map.get("error").toString());
        }
    }
}