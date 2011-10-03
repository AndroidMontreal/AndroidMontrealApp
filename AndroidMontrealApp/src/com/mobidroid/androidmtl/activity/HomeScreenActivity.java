package com.mobidroid.androidmtl.activity;

import com.mobidroid.androidmtl.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreenActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
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
