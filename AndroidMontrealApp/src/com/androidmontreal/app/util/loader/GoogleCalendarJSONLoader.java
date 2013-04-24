package com.androidmontreal.app.util.loader;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidmontreal.app.util.feed.JSONReader;

import android.content.Context;
import android.database.MatrixCursor;
import android.support.v4.content.AsyncTaskLoader;
import android.text.format.Time;
import android.util.Log;

/**
 * The starting point for this class was the "SampleLoader" code available
 * at http://www.androiddesignpatterns.com/2012/08/implementing-loaders.html
 * 
 * References to "Observers" were removed:  no pressing need to monitor the Calendar for changes.
 * 
 * @author Graeme Campbell
 *
 */
public class GoogleCalendarJSONLoader extends AsyncTaskLoader <MatrixCursor> {
	
	private static final String TAG = GoogleCalendarJSONLoader.class.getName();
	
	private static final String[] columnNames = { "_id", "title", "content", "where", "date", "start", "end", "link", "uid" };
	
	public static final String JSON_TAG_FEED = "feed";
	public static final String JSON_TAG_ENTRY = "entry";
	
	public static final String JSON_TAG_UID = "gCal$uid";
	public static final String JSON_TAG_VALUE = "value";
	public static final String JSON_TAG_TITLE = "title";
	public static final String JSON_TAG_CONTENT = "content";
	public static final String JSON_TAG_TEXT = "$t";
	
	
	public static final String JSON_TAG_WHERE = "gd$where";
	public static final String JSON_TAG_VALUESTRING = "valueString";
	public static final String JSON_TAG_WHEN = "gd$when";
	public static final String JSON_TAG_STARTTIME = "startTime";
	public static final String JSON_TAG_ENDTIME = "endTime";
	private static final String JSON_TAG_LINK = "link";
	private static final String JSON_TAG_HREF = "href";

	public static String mCalendarURL = null;

	// We hold a reference to the Loader’s data here.
	private MatrixCursor mEvents;

	public GoogleCalendarJSONLoader(Context ctx, String url) {
		// Loaders may be used across multiple Activities (assuming they aren't
		// bound to the LoaderManager), so NEVER hold a reference to the context
		// directly. Doing so will cause you to leak an entire Activity's context.
		// The superclass constructor will store a reference to the Application
		// Context instead, and can be retrieved with a call to getContext().
		super(ctx);
		mCalendarURL = url;
	}

	/****************************************************/
	/** (1) A task that performs the asynchronous load **/
	/****************************************************/

	@Override
	public MatrixCursor loadInBackground() {
		// This method is called on a background thread and should generate a
		// new set of data to be delivered back to the client.

		MatrixCursor data = new MatrixCursor(columnNames);

		String rawJsonFeed = JSONReader.readJsonFeed(mCalendarURL);

		if ( "".equals(rawJsonFeed) || rawJsonFeed == null ){
			return null;
		}
		
		try {
			JSONObject rawJsonObject = new JSONObject(rawJsonFeed);
			JSONObject googleCalendarFeed = rawJsonObject.getJSONObject(JSON_TAG_FEED);
			JSONArray calendarEntries = googleCalendarFeed.getJSONArray(JSON_TAG_ENTRY);
			
			for (int i = 0; i < calendarEntries.length(); i++){
				JSONObject currEntry =  calendarEntries.getJSONObject(i);
				
				String uid = currEntry.getJSONObject(JSON_TAG_UID).getString(JSON_TAG_VALUE);
				String title = currEntry.getJSONObject(JSON_TAG_TITLE).getString(JSON_TAG_TEXT);
				String content = currEntry.getJSONObject(JSON_TAG_CONTENT).getString(JSON_TAG_TEXT);
				String where = currEntry.getJSONArray(JSON_TAG_WHERE).getJSONObject(0).getString(JSON_TAG_VALUESTRING);
				String link = currEntry.getJSONArray(JSON_TAG_LINK).getJSONObject(0).getString(JSON_TAG_HREF);
				
				JSONObject currWhen = currEntry.getJSONArray(JSON_TAG_WHEN).getJSONObject(0);
				String startTimeString = currWhen.getString(JSON_TAG_STARTTIME);
				String endTimeString = currWhen.getString(JSON_TAG_ENDTIME);
				
				Time startTime = new Time();
				startTime.parse3339(startTimeString);
				startTime.switchTimezone(Time.getCurrentTimezone());
				
				Time endTime = new Time();
				endTime.parse3339(endTimeString);
				endTime.switchTimezone(Time.getCurrentTimezone());
				
				String date = startTime.format("%b %d");
				String start = startTime.format("%l:%M %p");
				String end = endTime.format("%l:%M %p"); 
				
				//Log.d(TAG, uid);
				data.addRow( new Object[] {i, title, content, where, date, start, end, link, uid} );
			}

			return data;
			
		} catch (JSONException ex) {
			StackTraceElement[] stackTrace = ex.getStackTrace();
			String message = ex.getMessage();
			Log.e( TAG, "getMessage: " + message );
			// note "Verbose" Log level in next few lines, then back to "Error" for the stacktrace.  JSON messages are too damn long!
			Log.v( TAG, " " );
			Log.v( TAG, "Final 200 chars of above Error Message:" );
			Log.v( TAG, ex.getLocalizedMessage().substring(   message.length() - 200   ) );

			for (int i=0; i<stackTrace.length; i++) {
				Log.e( TAG, "  " + stackTrace[i].toString() );
			}
		}

		return null;
	}

	/********************************************************/
	/** (2) Deliver the results to the registered listener **/
	/********************************************************/

	@Override
	public void deliverResult(MatrixCursor data) {
		if (isReset()) {
			// The Loader has been reset; ignore the result and invalidate the data.
			releaseResources(data);
			return;
		}

		// Hold a reference to the old data so it doesn't get garbage collected.
		// We must protect it until the new data has been delivered.
		MatrixCursor oldData = mEvents;
		mEvents = data;

		if (isStarted()) {
			// If the Loader is in a started state, deliver the results to the
			// client. The superclass method does this for us.
			super.deliverResult(data);
		}

		// Invalidate the old data as we don't need it any more.
		if (oldData != null && oldData != data) {
			releaseResources(oldData);
		}
	}

	/*********************************************************/
	/** (3) Implement the Loader’s state-dependent behavior **/
	/*********************************************************/

	@Override
	protected void onStartLoading() {
		if (mEvents != null) {
			// Deliver any previously loaded data immediately.
			deliverResult(mEvents);
		}
		forceLoad();
	}

	@Override
	protected void onStopLoading() {
		// The Loader is in a stopped state, so we should attempt to cancel the 
		// current load (if there is one).
		cancelLoad();

		// Note that we leave the observer as is. Loaders in a stopped state
		// should still monitor the data source for changes so that the Loader
		// will know to force a new load if it is ever started again.
	}

	@Override
	protected void onReset() {
		// Ensure the loader has been stopped.
		onStopLoading();

		// At this point we can release the resources associated with 'mData'.
		if (mEvents != null) {
			releaseResources(mEvents);
			mEvents = null;
		}

		/** Deleted "OBSERVER" code from this section */
	}

	@Override
	public void onCanceled(MatrixCursor data) {
		// Attempt to cancel the current asynchronous load.
		super.onCanceled(data);

		// The load has been canceled, so we should release the resources
		// associated with 'data'.
		releaseResources(data);
	}

	private void releaseResources(MatrixCursor data) {
		// For a simple List, there is nothing to do. For something like a Cursor, we 
		// would close it in this method. All resources associated with the Loader
		// should be released here.
	}

	/*********************************************************************/
	/** (4) Observer which receives notifications when the data changes **/
	/*********************************************************************/

	// NOTE: Implementing an observer is outside the scope of this post (this example
	// uses a made-up "SampleObserver" to illustrate when/where the observer should 
	// be initialized). 

	// The observer could be anything so long as it is able to detect content changes
	// and report them to the loader with a call to onContentChanged(). For example,
	// if you were writing a Loader which loads a list of all installed applications
	// on the device, the observer could be a BroadcastReceiver that listens for the
	// ACTION_PACKAGE_ADDED intent, and calls onContentChanged() on the particular 
	// Loader whenever the receiver detects that a new application has been installed.
	// Please don’t hesitate to leave a comment if you still find this confusing! :)

	/** Ignoring "SampleObserver" code, but leaving it and the above explanation for reference
	private SampleObserver mObserver;
	 */
	
	
}
