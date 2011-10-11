package com.mobidroid.androidmtl.activity;

import com.mobidroid.androidmtl.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class HomeScreenActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// TODO: v1.x [cleanup] Should try to have a local copy of the android ic drawables being currently used. 
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

	@Override
	/*
	 * <P>This is called when a menu item is selected, switch-case on type of menu-item. 
	 * <P>Another option would be to register listeners on each menu item at the time of the inflation of the menu from XML.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		// This is to clear the TapMode if user chooses another menu option.
//		stationOverlay.setTapMode(TapMode.NONE);

		switch (item.getItemId()) {
			case R.id.menu_preferences :
				// Simple statement of intent.
				Intent prefIntent = new Intent(this, AndroidMtlSetting.class);
				this.startActivity(prefIntent);
				return true ;
		}

		// Call parent to give a chance to handle menu items we might not know about.
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Suggest a topic for the next meeting
	 */
	public void onSuggestClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	/*
	 * Find us on the map
	 */
	public void onMapClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	/*
	 * Contact us
	 */
	public void onContactClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	/*
	 * View events (calendar?)
	 */
	public void onEventClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	/*
	 * News feed from FrAndroid
	 */
	public void onNewsClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	/*
	 * About Android Montreal Group
	 */
	public void onAboutClick(View v) {
		startActivity(new Intent(this, AndroidMtlSetting.class));
	}

	/*
	 * Settings (there is no button currently, we have too many buttons)
	 */
	public void onSettingsClick(View v) {
		startActivity(new Intent(this, AndroidMtlSetting.class));
	}

}
