package com.androidmontreal.app.fragments;

import com.androidmontreal.app.R;
import com.androidmontreal.app.util.loader.GoogleCalendarJSONLoader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


/**
 * This class started its life as a regular ListFragment.  The "loader" related logic (and comments)
 * were initially copied from the "SampleListActivity" code available at 
 * http://www.androiddesignpatterns.com/2012/07/understanding-loadermanager.html
 * 
 * @author Graeme Campbell
 *
 */
public class EventsPageFragment extends ListFragment 
implements LoaderManager.LoaderCallbacks<MatrixCursor> {

	
	private static final String TAG = EventsPageFragment.class.getName();
	
	private static final String TEST_URL_FOR_NOW_MAKE_THIS_A_PROPER_CONFIGURABLE_ELEMENT_LATER = "http://www.google.com/calendar/feeds/535sl46t1ujrgs1ig8nrms9954@group.calendar.google.com/public/full?alt=json";
	
	private static final int CURSOR_LINK_POSITION = 7;
	private static final int CURSOR_UID_POSITION = 8;
	private static final String[] from = { "title", "content", "where", "date", "start", "end" };
	private static final int[] to = {R.id.event_title, R.id.event_content, R.id.event_where, R.id.event_date, R.id.event_start, R.id.event_end};
	
	// The loader's unique id. Loader ids are specific to the Activity or
	// Fragment in which they reside.
	private static final int LOADER_ID = 1;

	// The callbacks through which we will interact with the LoaderManager.
	private LoaderManager.LoaderCallbacks<MatrixCursor> mCallbacks;

	// The adapter that binds our data to the ListView
	private SimpleCursorAdapter mAdapter;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	    // Initialize the adapter. Note that we pass a 'null' Cursor as the
	    // third argument. We will pass the adapter a Cursor only when the
	    // data has finished loading for the first time (i.e. when the
	    // LoaderManager delivers the data to onLoadFinished). Also note
	    // that we have passed the "0" flag as the last argument. This
	    // prevents the adapter from registering a ContentObserver for the
	    // Cursor (the CursorLoader will do this for us!).
	    mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.events_list_fragment, null, from, to, 0);
	    
		// Associate the (now empty) adapter with the ListView.
		setListAdapter(mAdapter);

		// The Activity (which implements the LoaderCallbacks<Cursor>
		// interface) is the callbacks object through which we will interact
		// with the LoaderManager. The LoaderManager uses this object to
		// instantiate the Loader and to notify the client when data is made
		// available/unavailable.
		mCallbacks = this;

		// Initialize the Loader with id '1' and callbacks 'mCallbacks'.
		// If the loader doesn't already exist, one is created. Otherwise,
		// the already created Loader is reused. In either case, the
		// LoaderManager will manage the Loader across the Activity/Fragment
		// lifecycle, will receive any new loads once they have completed,
		// and will report this new data back to the "mCallbacks" object.
		LoaderManager lm = this.getLoaderManager();
		lm.initLoader(LOADER_ID, null, mCallbacks);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor = mAdapter.getCursor();
		if (cursor != null){
			
			// initially I tried just launching a browser with the link Google supplies in JSON.
			// This works, but...  sucks.  Anyways, it's below:
			String link = cursor.getString(CURSOR_LINK_POSITION);
			Uri webpage = Uri.parse(link);
			Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
			startActivity(webIntent);
			
			// I would much rather get this working:  Launch the actual Calendar app.  
			// TODO:  Need to figure out a contradiction:  Google JSON sends eventId as a string (or maybe it's a GUID), but launching Calendar intent expects a 'long' 
/**
			String eventId = cursor.getString(CURSOR_UID_POSITION);
			
			Uri uri = Calendars.withAppendedId(Events.CONTENT_URI, eventId);
			Intent intent = new Intent(Intent.ACTION_VIEW)
			   .setData(uri);
			startActivity(intent);
*/			
			
		}
	    
	}

	@Override
	public Loader<MatrixCursor> onCreateLoader(int id, Bundle args) {
		// Create a new CursorLoader with the following query parameters.
		return new GoogleCalendarJSONLoader(getActivity(), TEST_URL_FOR_NOW_MAKE_THIS_A_PROPER_CONFIGURABLE_ELEMENT_LATER );
	}

	@Override
	public void onLoadFinished(Loader<MatrixCursor> loader,
			MatrixCursor data) {
		// A switch-case is useful when dealing with multiple Loaders/IDs
		switch (loader.getId()) {
		case LOADER_ID:
			// The asynchronous load is complete and the data
			// is now available for use. Only now can we associate
			// the queried Cursor with the SimpleCursorAdapter.

			mAdapter.swapCursor(data);  

			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<MatrixCursor> loader) {
		// // For whatever reason, the Loader's data is now unavailable.
		// Remove any references to the old data by replacing it with
		// a null Cursor.
		mAdapter.swapCursor(null);


	}
}