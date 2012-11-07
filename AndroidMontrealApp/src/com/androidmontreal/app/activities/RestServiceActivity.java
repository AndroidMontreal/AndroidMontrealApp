package com.androidmontreal.app.activities;

import android.app.Activity;

public class RestServiceActivity extends Activity{
	
	/*
	
	TextView t_query1 = null, t_query2 = null, t_query3;
	RestService restServiceG, restServiceP, restServicePut;
	

	String API_KEY = "AIzaSyCm45dmJ9surAmFlEelLHG87VOM5hu7xNw";
	String BASE_URL = "https://www.googleapis.com/calendar/v3/";
	String GET_EVENTS = BASE_URL + "androidmontreal.com@gmail.com/acl";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testrest);
        
        restServiceG = new RestService(mHandlerGet, this, GET_EVENTS, RestService.GET); //Create new rest service for get
        restServiceG.addParam("key", API_KEY);  //Add params to request
        restServiceG.addHeader("Content-Type","application/json"); //Add headers to request
                     
        restServiceP = new RestService(mHandlerPost, this, "http://10.0.2.2:3000/bars/", RestService.POST); //Create new rest service for post
        restServiceP.addParam("location", "brooklyn"); //Add params to request
        restServiceP.addParam("bar[address]","123"); //Format for a typical form encoded nested attribute
        restServiceP.addParam("bar[rating]","123");
        restServiceP.addParam("bar[name]","123");
        
        restServicePut = new RestService(mHandlerPut, this, "http://10.0.2.2:3000/bars/", RestService.PUT); //Create new rest service for post
        restServicePut.addParam("location", "brooklyn"); //Add params to request
        restServicePut.addParam("bar[address]","123"); //Format for a typical form encoded nested attribute
        restServicePut.addParam("bar[rating]","123");
        restServicePut.addParam("bar[name]","123");

        
        t_query1 = (TextView) findViewById(R.id.t_query1); //Setup Views and Button for response
        t_query1.setMovementMethod(new ScrollingMovementMethod());
        t_query2 = (TextView) findViewById(R.id.t_query2); //Setup Views and Button for response
        t_query2.setMovementMethod(new ScrollingMovementMethod());
        t_query3 = (TextView) findViewById(R.id.t_query3); //Setup Views and Button for response
        t_query3.setMovementMethod(new ScrollingMovementMethod());
        
        Button b_query1 = (Button) findViewById(R.id.b_query1);
    	b_query1.setOnClickListener(new View.OnClickListener(){
    		public void onClick(View view){
    			try {
					restServiceG.execute(); //Executes the request with the HTTP GET verb
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	});
    	
        Button b_query2 = (Button) findViewById(R.id.b_query2);
    	b_query2.setOnClickListener(new View.OnClickListener(){
    		public void onClick(View view){
    			try {
					restServiceP.execute(); //Executes the request with the HTTP GET verb
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	});
    	
        Button b_query3 = (Button) findViewById(R.id.b_query3);
    	b_query3.setOnClickListener(new View.OnClickListener(){
    		public void onClick(View view){
    			try {
					restServicePut.execute(); //Executes the request with the HTTP GET verb
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	});
    }
    
    //Overridden handler to process incoming response. Response string is attached as msg.obj.
    private final Handler mHandlerGet = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    			t_query1.setText((String) msg.obj);
    		}		
    };
    
    private final Handler mHandlerPost = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    			t_query2.setText((String) msg.obj);
    		}		
    };
    
    private final Handler mHandlerPut = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    			t_query3.setText((String) msg.obj);
    		}	
    };
    
    
    */

}

