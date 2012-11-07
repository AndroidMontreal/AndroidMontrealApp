package com.androidmontreal.app.content;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

public class PageContent {
	
	public static final int HOME_PAGE_ID = 0;
	public static final int EVENT_PAGE_ID = 1;
	public static final int NEWS_PAGE_ID = 2;
	public static final int PRESENT_PAGE_ID = 3;
	public static final int SPONSORS_PAGE_ID = 4;
	public static final int ABOUT_PAGE_ID = 5;
	
    public static class PageItem {

        public int id;
        public String pageTitle;
		private boolean isWeb;

        public PageItem(int id, String pageTitle, boolean isWeb) {
            this.id = id;
            this.pageTitle = pageTitle;
            this.isWeb = false;
        }

        @Override
        public String toString() {
            return pageTitle;
        }
        
    }

    public static List<PageItem> AVAILABLE_PAGES = new ArrayList<PageItem>();
    public static SparseArray<PageItem> ITEM_MAP = new SparseArray<PageItem>();
       
    static {
        addItem(new PageItem(HOME_PAGE_ID, "Home", false));
        addItem(new PageItem(EVENT_PAGE_ID, "Events", false));
        addItem(new PageItem(NEWS_PAGE_ID, "News", false));        
        addItem(new PageItem(PRESENT_PAGE_ID, "Present", false));
        addItem(new PageItem(SPONSORS_PAGE_ID, "Sponsors", true));
        addItem(new PageItem(ABOUT_PAGE_ID, "About", true));
    }

    private static void addItem(PageItem item) {
        AVAILABLE_PAGES.add(item);
        ITEM_MAP.put(new Integer(item.id), item);
    }

	public static boolean requestedIdIsWeb(int id) {		
		return ITEM_MAP.get(id).isWeb;				
	}
    
}
