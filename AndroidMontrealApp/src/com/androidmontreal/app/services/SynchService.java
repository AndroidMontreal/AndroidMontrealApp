package com.androidmontreal.app.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.androidmontreal.app.AndroidMontrealApp;
import com.androidmontreal.app.util.feed.JSONReader;
import com.androidmontreal.app.util.rest.RestService;

/**
 * This service keep application content updated
 * @author francois.legare1
 *
 */
public class SynchService extends IntentService {
	
	private static final String TAG = SynchService.class.getName();
		
	String API_KEY = "AIzaSyCm45dmJ9surAmFlEelLHG87VOM5hu7xNw";

	/** Below constants no longer needed:  Calendar events brought in with LoaderManager instead */
//	String BASE_URL = "https://www.googleapis.com/calendar/v3/";
//	String GET_EVENTS = BASE_URL + "androidmontreal.com@gmail.com/acl";
	
	private static final String TWITTER_FEED_SOURCE = "http://twitter.com/statuses/user_timeline/androidmontreal.json";
	

		
	String WS_URL = "http://quiziwiki.org/";
	String GET_VERSION = WS_URL + "ws/androidmtl/";
	
	private RestService restVersionService;
	
	public SynchService() {		
		super("Android mtl synch"); //FIXME Replace with resources		
		Log.v(TAG, "Synch service created");		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
				
		boolean internetAvailable = false;
				
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			internetAvailable = true;
			Log.v(TAG, "Internet connection found");
		} 
		
		if(internetAvailable){
			
			// TODO Fetch static page version
			getVersionsAndUpdate();
						
			updateTwitterFeedInfo();
												
			// TODO Launch update if required
			
			// TODO Fetch new events
			
			// TODO Fetch new news
			
		}
		
	}
	
    /**
     * Get the twitter feed and store it in the preferences
     */
    private void updateTwitterFeedInfo() {    	
    	String twitterFeed = JSONReader.readJsonFeed(TWITTER_FEED_SOURCE);
    	//FIXME easy code, but not optimal in term of performances
    	//(flat file would be more efficient)
    	Editor ed = getSharedPreferences(AndroidMontrealApp.PREF, 0).edit();
    	ed.putString(AndroidMontrealApp.PK_TWITTER_FEED, twitterFeed).commit();    	    		
	}

	//Receive page version and process it if required
    private final Handler mVersionHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		
    			Log.v(TAG, "Received version state: " + msg.obj);
    			
    			//TODO Compare with current version
    			
    			//TODO Download new versions
    			    			
    			//TODO Save new version using handler
    			
    			//TODO what to do with the stuff?
    			//t_query1.setText((String) msg.obj);
    		}		
    };


	/**
	 * Request version of pages and update if neeeded
	 */
	private void getVersionsAndUpdate() {
		
		//Create new rest service for get version
        restVersionService = new RestService(mVersionHandler, this, GET_VERSION, RestService.GET);         
        restVersionService.addHeader("Content-Type","application/json"); //Add headers to request 
        
		try {
			restVersionService.execute(); //Executes the request with the HTTP GET verb
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
