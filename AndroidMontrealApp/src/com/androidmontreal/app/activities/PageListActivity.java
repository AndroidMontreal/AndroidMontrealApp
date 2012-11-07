package com.androidmontreal.app.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.androidmontreal.app.R;
import com.androidmontreal.app.content.PageContent;
import com.androidmontreal.app.fragments.PageDetailFragment;
import com.androidmontreal.app.fragments.PageListFragment;

/**
 * This main fragment activity list the available page content
 * for the application. Depending on the device we display
 * one pane or two pane. In the case of single pane, the 
 * action lead to a stand alone acitvity containing the content.
 * On two pane mode, we display on the left the list of available
 * pages and on click change the content pane on the right. 
 * 
 * The app implement the interface Callback defined in our list
 * fragment. This allow the activity to receive the call when 
 * user clicked a item from the list and react accordingly
 * 
 * Based from the sample fragement project made by google. More information here:
 * http://developer.android.com/training/implementing-navigation/descendant.html
 *
 * @author francois.legare1
 *
 */
public class PageListActivity extends FragmentActivity
        implements PageListFragment.Callbacks {

    private boolean mTwoPane;
    
	private int activePageId = -1;
    
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        //This ressource have a alias leading to a single or two pane
        //layout depending on the device configuration. The next step
        //is to detect if we are in two pane or not to trigger the list
        setContentView(R.layout.activity_page_list);

        if (findViewById(R.id.page_detail_container) != null) {
            mTwoPane = true;
            //enable the selection of page list, no need in single
            //pane since we will use intent to trigger the action
            ((PageListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.page_list))
                    .setActivateOnItemClick(true);
                        
            Bundle arguments = new Bundle();
            arguments.putInt(PageDetailFragment.ARG_PAGE_ID, PageContent.HOME_PAGE_ID);
            PageDetailFragment fragment = new PageDetailFragment();
            fragment.setArguments(arguments);
            
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();            
            ft.replace(R.id.page_detail_container, fragment);
            ft.commit();
        }
    }
    
    @Override
    public void onAttachFragment(Fragment fragment) {    
    	super.onAttachFragment(fragment);    	  
    }
       
    /*
     * Depending on the user choice, we launch a new activity 
     * in single view or change the pageDetail fragment content
     * in a two pane view.
     * 
     * (non-Javadoc)
     * @see com.androidmontreal.app.fragments.PageListFragment.Callbacks#onItemSelected(java.lang.String)
     */
    @Override
    public void onItemSelected(int id) {
    	
    	if(id==activePageId && id!=-1)
    		return;
    	
    	activePageId = id;
    	    	    	    	   
        if (mTwoPane) {
        	        	       
            Bundle arguments = new Bundle();
            arguments.putInt(PageDetailFragment.ARG_PAGE_ID, id);
                       
        	PageDetailFragment fragment = new PageDetailFragment();
            fragment.setArguments(arguments);
            
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();              
            //ft.setCustomAnimations(R.anim.flip_in,R.anim.flip_out);
            ft.replace(R.id.page_detail_container, fragment);
            ft.commit();                
                                  
        } else {
            Intent detailIntent = new Intent(this, PageDetailActivity.class);
            detailIntent.putExtra(PageDetailFragment.ARG_PAGE_ID, id);
            startActivity(detailIntent);
        }
    }
}
