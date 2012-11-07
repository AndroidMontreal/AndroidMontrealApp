package com.androidmontreal.app;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.androidmontreal.app.services.SynchService;
import com.androidmontreal.app.tasks.AssetCopierTask;
import com.androidmontreal.app.tasks.AssetCopierTask.CallBack;
import com.androidmontreal.app.tasks.UnpackTask;

/**
 * Main application singleton mainly act as a service controller to keep the
 * application in synch
 * 
 * @author francois.legare1
 * 
 */
public class AndroidMontrealApp extends Application {

	private static final String TAG = AndroidMontrealApp.class.getName();

	public static final String PREF = TAG + ".pref";
	public static final String PK_TWITTER_FEED = TAG + ".twitter.lastfeed";
	
	private static final String PK_FIRST_RUN = TAG + ".pref.firstrun";
	


	private static final String CONTENT_DIRECTORY_NAME = "content";
	
	private static final String STUB_ASSET_HTML_DIR = "www";

	protected static final String PK_STUB_AVAILABLE = TAG + ".stubAvailable";

	@Override
	public void onCreate() {
		super.onCreate();

		if (isFirstTimeExecution()) {
			//executeFirstLaunchLogic();
		}

		startSynchService();
	}

	/**
	 * This snippet will be executed the first time the application is launched
	 * on the device
	 */
	private void executeFirstLaunchLogic() {

		// Unpack static html stubs
		deployHtmlStubContent();

		// Flag static content ready to use

		// ...

	}

	/**
	 * Stub html file should have been unpacked
	 */
	private UnpackTask.CallBack unpackStubCb = new UnpackTask.CallBack() {
		@Override
		public void onFileUnpacked(boolean success) {
			getSharedPreferences(PREF, MODE_PRIVATE).edit()
					.putBoolean(PK_STUB_AVAILABLE, success).commit();
			if (!success) {
				reportError("unable to unpack stubs");
			}
		}
	};

	/**
	 * We copy assets into the content of the app Based from:
	 * http://stackoverflow
	 * .com/questions/4447477/android-how-to-copy-files-in-assets-to-sdcard
	 */
	private void deployHtmlStubContent() {
		
		AssetCopierTask assetCopier = new AssetCopierTask(this, new CallBack() {						
			@Override
			public void onFileUnpacked(boolean success) {			
				Log.d(TAG, "Shit is done");				
			}
		});
		
		assetCopier.execute("not mather");
		
	}
	
	/**
	 * Is this the first time the application run on this device?
	 * 
	 * @return
	 */
	public boolean isFirstTimeExecution() {
		return getSharedPreferences(PREF, MODE_PRIVATE).getBoolean(
				PK_FIRST_RUN, true);
	}

	/**
	 * Is stub successfully unpacked?
	 * 
	 * @return
	 */
	public boolean isStubAvailable() {
		return getSharedPreferences(PREF, MODE_PRIVATE).getBoolean(
				PK_STUB_AVAILABLE, false);
	}

	/**
	 * Start the sync process that will fetch new static content if require and
	 * update data
	 */
	private void startSynchService() {

		// TODO Make sure we have connectivity
		// TODO Make sure we need to check update using date
		Log.v(TAG, "Request synch service");
		final Intent intent = new Intent(this, SynchService.class);
		startService(intent);

		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	/**
	 * Error occured while running background operation
	 * 
	 * @param msg
	 */
	private void reportError(String msg) {
		// FIXME make a more efficient way to report errors
		Log.e(TAG, msg);
	}

}
