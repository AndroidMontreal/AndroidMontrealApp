package com.androidmontreal.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.androidmontreal.app.R;
import com.androidmontreal.app.content.PageContent;
import com.androidmontreal.app.util.web.LocalWebView;

/**
 * This fragment is the main view
 * 
 * @author francois.legare1
 * 
 */
public class PageDetailFragment extends Fragment {

	private static final String TAG = PageDetailFragment.class.getName();

	public static final String ARG_PAGE_ID = "item_id";

	PageContent.PageItem mItem;

	private View rootView;

	private WebView wv;

	private FrameLayout viewPlaceHolder;

	/**
	 * This empty constructor is required per documentation
	 */
	public PageDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getArguments().containsKey(ARG_PAGE_ID)) {
			mItem = PageContent.ITEM_MAP.get(getArguments().getInt(ARG_PAGE_ID));
		}
				
	}
		
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_PAGE_ID, mItem.id);
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_page_detail, container,false);

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
		case PageContent.HOME_PAGE_ID:
			loadHomePage();
			break;
		case PageContent.EVENT_PAGE_ID:
			loadEventsPage();
			break;
		case PageContent.NEWS_PAGE_ID:
//			TODO:  Implement this page
//			loadNewsPage();
			break;
		case PageContent.PRESENT_PAGE_ID:
//			TODO:  Implement this page
//			loadPresentPage();
			break;
		case PageContent.ABOUT_PAGE_ID:
			loadLocalWebPage("about-en.html");
			break;
		case PageContent.SPONSORS_PAGE_ID:
			loadLocalWebPage("sponsor-en.html");
			break;
		default:
			loadHomePage();
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
		viewPlaceHolder = ((FrameLayout) rootView.findViewById(R.id.mainContentPlaceHolder));
		// viewPlaceHolder.setVisibility(View.INVISIBLE);

		// Initialize the WebView if necessary
		if (wv == null) {
			wv = new LocalWebView(getActivity());
			wv.setAlpha(0);
			wv.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					wv.setAlpha(1);
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

	private void loadHomePage() {

		// Add it to the container
		FrameLayout viewPlaceHolder = ((FrameLayout) rootView
				.findViewById(R.id.mainContentPlaceHolder));
		viewPlaceHolder.inflate(getActivity(), R.layout.home_page,
				viewPlaceHolder);

		// Build a page...

	}
	
	private void loadEventsPage() {
		FrameLayout viewPlaceHolder = ((FrameLayout) rootView
				.findViewById(R.id.mainContentPlaceHolder));
		viewPlaceHolder.inflate(getActivity(), R.layout.event_display_abstract,
				viewPlaceHolder);
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
