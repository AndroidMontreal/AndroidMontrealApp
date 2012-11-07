package com.androidmontreal.app.tasks;

import java.io.File;
import java.io.InputStream;

import android.os.AsyncTask;

import com.androidmontreal.app.util.files.ZipUtil;

/**
 * A simple task that unpack a file to specified path
 * Parameters:
 * 
 *  File[SOURCE_ZIP] : Input stream of zip file
 *  File[UNPACK_DIR] : Destination directory
 * 
 * @author francois.legare1
 *
 */
public class UnpackTask extends AsyncTask<Object, String, Boolean>{
	
	private static final int SOURCE_ZIP = 0;
	private static final int UNPACK_DIR = 1;
		
	private CallBack mCallback;
	
	public UnpackTask(CallBack cb) {		
		mCallback = cb;
	}
	
	/**
     * Simple callback
     */
    public interface CallBack {
        public void onFileUnpacked(boolean success);
    }

	@Override
	protected Boolean doInBackground(Object... params) {
		
		if(!(params[SOURCE_ZIP] instanceof InputStream))
			throw new IllegalArgumentException("Must provide a input stream for first parameters");
		
		if(!(params[UNPACK_DIR] instanceof File))
			throw new IllegalArgumentException("Must provide a file for second parameters");

		InputStream sourceZip = (InputStream)params[SOURCE_ZIP];
		File unpackDest = (File)params[UNPACK_DIR];
						
		return ZipUtil.unpackZip(sourceZip, unpackDest.getPath());
		
	}
	
	@Override
	protected void onPostExecute(Boolean result) {	
		mCallback.onFileUnpacked(result.booleanValue());
	}
		
}
