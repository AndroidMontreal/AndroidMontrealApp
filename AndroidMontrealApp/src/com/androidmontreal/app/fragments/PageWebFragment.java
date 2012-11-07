package com.androidmontreal.app.fragments;

import com.androidmontreal.app.R;
import com.androidmontreal.app.content.PageContent;
import com.androidmontreal.app.content.PageContent.PageItem;
import com.androidmontreal.app.util.web.LocalWebView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.FrameLayout;

public class PageWebFragment extends WebViewFragment {

	private static final String TAG = PageWebFragment.class.getName();

	private PageItem mItem;

	private View rootView;

	private FrameLayout viewPlaceHolder;

	private LocalWebView wv;

	public PageWebFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(PageDetailFragment.ARG_PAGE_ID)) {
			mItem = PageContent.ITEM_MAP.get(getArguments().getInt(
					PageDetailFragment.ARG_PAGE_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_page_detail, container,
				false);

		if (mItem != null) {
			// There is many way to implement the following content logic
			// loader, this may be improved in the future
			loadPageContentFromId(mItem.id);
		}

		return rootView;
	}

	/**
	 * Apply the main content view
	 * 
	 * @param pageid
	 */
	private void loadPageContentFromId(int id) {

		Log.v(TAG, "should load page id:" + id);

		switch (id) {
		case PageContent.ABOUT_PAGE_ID:
			loadLocalWebPage("about-en.html");
			break;
		case PageContent.SPONSORS_PAGE_ID:
			loadLocalWebPage("sponsor-en.html");
			break;
		default:
			loadLocalWebPage("sponsor-en.html");
			break;
		}

	}

	/**
	 * This load the required page, by first looking in updated folder, then in
	 * the internal assets for the initial stubs if available.
	 * 
	 * @param pageName
	 */
	private void loadLocalWebPage(String pageName) {

		// TODO check if most recent version is available

		// Add it to the container
		viewPlaceHolder = ((FrameLayout) rootView
				.findViewById(R.id.mainContentPlaceHolder));
		// viewPlaceHolder.setVisibility(View.INVISIBLE);

		// Initialize the WebView if necessary
		if (wv == null) {
			wv = new LocalWebView(getActivity());
			// wv.setAlpha(0);
			wv.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					// wv.setAlpha(1);
					// wv.setVisibility(View.VISIBLE);
					// wv.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.flip_in));
				}
			});

			// wv.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/testing123/www/html/sponsor-en.html");
		}

		viewPlaceHolder.addView(wv);
		wv.loadUrl("file:///android_asset/www/html/" + pageName);
	}

	private void showWebView() {
		// viewPlaceHolder.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop ID" + getId());
	}

}
