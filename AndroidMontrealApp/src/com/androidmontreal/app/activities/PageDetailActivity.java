package com.androidmontreal.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
 
import com.androidmontreal.app.R;
import com.androidmontreal.app.content.PageContent;
import com.androidmontreal.app.fragments.PageDetailFragment;

/**
 * This fragment activity display main content according to the
 * ID received in the intent. It can live as a stand alone 
 * activity on right side pane. This activity is mainly called 
 * by small resolution mobile devices and act as a stand alone 
 * content display activity 
 * 
 * 
 * Based from the sample fragment project made by google. More information here:
 * http://developer.android.com/training/implementing-navigation/descendant.html
 *
 * @author francois.legare1
 *
 */
public class PageDetailActivity extends FragmentActivity {

	private static final String TAG = PageDetailActivity.class.getName();
	
    private PageDetailFragment mDetailfragment;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);

        //Set whether home should be displayed as an "up" affordance.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Check if this is a new load, if so fetch the content
        if (savedInstanceState == null) {
        	
        	//We extract the requested page id 
        	int pageIdRequested = getIntent().getIntExtra(PageDetailFragment.ARG_PAGE_ID, PageContent.HOME_PAGE_ID);     
        	//We will package this page id in a bundle to be consumed by the fragment
            Bundle arguments = new Bundle();
            arguments.putInt(PageDetailFragment.ARG_PAGE_ID,pageIdRequested);
            
            //Create the content fragment
            mDetailfragment = new PageDetailFragment();
            mDetailfragment.setArguments(arguments);
                                   
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();                      
            ft.add(R.id.page_detail_container, mDetailfragment);
            ft.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	//If user pick "home" from the action bar, we bring him back
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, PageListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);    	
    	getSupportFragmentManager().putFragment(outState, PageDetailFragment.class.getName(), mDetailfragment);
    }

}
