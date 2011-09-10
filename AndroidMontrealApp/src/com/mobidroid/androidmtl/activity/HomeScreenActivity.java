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

	public void onSuggestClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	public void onMapClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	public void onContactClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	public void onEventClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	public void onNewsClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	public void onAboutClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

	public void onFacebookClick(View v) {
		startActivity(new Intent(this, HomeScreenActivity.class));
	}

}
