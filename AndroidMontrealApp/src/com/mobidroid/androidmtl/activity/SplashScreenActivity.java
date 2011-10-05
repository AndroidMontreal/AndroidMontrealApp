package com.mobidroid.androidmtl.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mobidroid.androidmtl.AndroidMontrealAppActivity;
import com.mobidroid.androidmtl.R;

public class SplashScreenActivity extends Activity {
	// CONSTANTS
	private static final String TAG = "VeliMapSplash";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: Get this working to allow disabling of the splashscreen.
		// Get the default shared preferences object for this application. 
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		// Find out if the splash screen is enabled in the preferences [defaults to true]
//		boolean splashEnabled = preferences.getBoolean(VeliMapPreferenceActivity.PREF_SPLASH_ENABLED, true);

//		if( splashEnabled ) {
			Log.d(TAG, "Splash screen enabled.");
			setContentView(com.mobidroid.androidmtl.R.layout.splash_screen);

			// TODO: Change the bike bell to something relevant.
			// Play a little intro sound
			MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bell_v1);
			mediaPlayer.start();

			/*	
			 * Splash title, no subtext for now.
			 */
			TextView splashTitle = (TextView) findViewById(R.id.splash_title);
//			String releaseLabel = String.format(getResources().getString(R.string.splash_versionedTitle), MAJOR,MINOR,RELEASE, "");
			// TODO: Chane text to something relevant.
			splashTitle.setText("Hello world");
			
			
			View view = findViewById(R.id.splash_layout);
			view.postDelayed(new Runnable() {
				public void run() {
					Intent mainIntent = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
					SplashScreenActivity.this.startActivity(mainIntent);
					SplashScreenActivity.this.finish();
				}
			}, 4000 );
			
			// TODO: Reactivate if we want to add more info on splash screen.
//			TextView splashSubtext = (TextView) findViewById(R.id.splash_subtext);
//			splashSubtext.setText(R.string.splash_subtext);
//		}
			// TODO: Re-enable this when we have enabled/disabled splash screen.
		// If not enabled, jump to main right away.
//		else {
//			Log.d(TAG, "Splash is disabled.");
//			startMainActivity();
//		}
		
		
	}
	
	// TODO: Remove this in final version? The idea was to use it for time-out... 
	class FakeLoadingAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
}
