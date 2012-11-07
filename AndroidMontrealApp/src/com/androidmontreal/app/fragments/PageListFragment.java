package com.androidmontreal.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidmontreal.app.content.PageContent;
import com.androidmontreal.app.content.PageContent.PageItem;

/**
 * This fragment list the available pages. We use the ListFragment
 * which displays a list of items by binding to a data source such as an array
 * or Cursor, and exposes event handlers when the user selects an item.
 * 
 * 
 * Based from the sample fragment project made by google. More information here:
 * http://developer.android.com/training/implementing-navigation/descendant.html
 *
 * @author francois.legare1
 *
 */
public class PageListFragment extends ListFragment {
		
	private static final String TAG = PageListFragment.class.getName();
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;    
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Simple callback interface which is called 
     * when user picked a item in our list. 
     */
    public interface Callbacks {
        public void onItemSelected(int id);
    }
    
    
    /**
     * This empty constructor is required per documentation
     */
    public PageListFragment() {
	}

    /**
     * This dummy call back Will be used to prevent null pointer when
     * fragment is detached, other wise we might wakeup invalid activity
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int id) {
        	//simply ignore the call
        	Log.v(TAG, "onItemSelected received, but fragment is detached");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        //We create a new list adapter containing our pages        
        ArrayAdapter<PageItem> pageItemAdapter = new ArrayAdapter<PageContent.PageItem>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                PageContent.AVAILABLE_PAGES);
        
        //Our fragment list need this adpater to display the list
        setListAdapter(pageItemAdapter);
        
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        //We restore last pick position if already set
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        //We insure the activity attaching this fragment implements
        //the callback interface, copy pasta protection :)
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        //Set ou callback listener 
        mCallbacks = (Callbacks) activity;
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onDetach()
     */
    @Override
    public void onDetach() {
        super.onDetach();
        //Set the dummy call back to prevent invalid call       
        mCallbacks = sDummyCallbacks;
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        //We indicate our activity that an item has beed selected
        mCallbacks.onItemSelected(PageContent.AVAILABLE_PAGES.get(position).id);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //We save selected position on the list if available
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Enable selection in the list (called by the dual pane activity) 
     * @param activateOnItemClick
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {    	
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    /**
     * Restor checked state
     * @param position
     */
    public void setActivatedPosition(int position) {
    	
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
