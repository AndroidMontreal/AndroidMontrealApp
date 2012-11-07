package com.androidmontreal.app.util.web;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

public class LocalWebView extends WebView {
		
	private static final String TAG = LocalWebView.class.getName();

	public LocalWebView(Context context) {
		super(context);
	}
	
	/**
	 * Set all required properties for the web view
	 */
	private void configureWebView() {
		
		this.setHorizontalScrollBarEnabled(true);
		this.setKeepScreenOn(true);
		this.getSettings().setBuiltInZoomControls(true);
		this.getSettings().setJavaScriptEnabled(true);
		this.getSettings().setUseWideViewPort(true);
		this.setInitialScale(1);
		this.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		
	}
	
}
