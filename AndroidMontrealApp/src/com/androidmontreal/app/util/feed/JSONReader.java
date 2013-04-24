package com.androidmontreal.app.util.feed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONReader {

	
	private static final String TAG = JSONReader.class.getName();
	//private static final String FEED_SOURCE = "http://pipes.yahoo.com/pipes/pipe.run?_id=d2c6600b98b9934f090af6c76ec8ac36&_render=json";
	
	

	public static JSONArray readFeed(String url) {

		String rawJsonFeed = readJsonFeed(url);

		try {
			JSONArray jsonArray = new JSONArray(rawJsonFeed);
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	

	public static String readJsonFeed(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(TAG, "Failed to download file.  URL  " + url );
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
