package com.androidmontreal.app.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

/**
 * A simple task that copy file from a folder of assets to the internal storage
 * 
 * Parameters: File[UNPACK_DIR] : Destination directory
 * 
 * @author francois.legare1
 * 
 */
public class AssetCopierTask extends AsyncTask<String, String, Boolean> {

	private static final String TAG = AssetCopierTask.class.getName();

	private static final int ASSET_SUB_DIR = 0;
	private static final int UNPACK_DIR = 1;

	private static final String DEFAULT_STUB_ASSET_DIR = "www";

	private final static String TARGET_BASE_PATH = Environment.getExternalStorageDirectory() +
												   File.separator +
												   "testing123" + 
												   File.separator;

	private CallBack mCallback;
	private Context mContext;

	public AssetCopierTask(Context c, CallBack cb) {
		mContext = c;
		mCallback = cb;
	}

	/**
	 * Simple callback
	 */
	public interface CallBack {
		public void onFileUnpacked(boolean success);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		// Launch the copy of the assets
		copyFileOrDir("");
		return new Boolean(true);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		mCallback.onFileUnpacked(result.booleanValue());
	}
	
	private void copyFilesToSdCard() {
		copyFileOrDir(""); // copy all files in assets folder in my project
	}

	private void copyFileOrDir(String path) {
		AssetManager assetManager = mContext.getAssets();
		String assets[] = null;
		try {
			Log.i("tag", "copyFileOrDir() " + path);
			assets = assetManager.list(path);
			if (assets.length == 0) {
				copyFile(path);
			} else {
				String fullPath = TARGET_BASE_PATH + path;
				Log.i("tag", "path=" + fullPath);
				File dir = new File(fullPath);
				if (!dir.exists() && !path.startsWith("images")
						&& !path.startsWith("sounds")
						&& !path.startsWith("webkit"))
					
					if (!dir.mkdirs());				
						Log.i("tag", "could not create dir " + fullPath);
						
				for (int i = 0; i < assets.length; ++i) {
					String p;
					if (path.equals(""))
						p = "";
					else
						p = path + "/";

					if (!path.startsWith("images")
							&& !path.startsWith("sounds")
							&& !path.startsWith("webkit"))
						copyFileOrDir(p + assets[i]);
				}
			}
		} catch (IOException ex) {
			Log.e("tag", "I/O Exception", ex);
		}
	}

	/**
	 * 
	 * @param filename
	 */
	private void copyFile(String filename) {
		
		AssetManager assetManager = mContext.getAssets();

		InputStream in = null;
		OutputStream out = null;
		String newFileName = null;
		try {
			Log.i("tag", "copyFile() " + filename);
			in = assetManager.open(filename);
			if (filename.endsWith(".jpg")) // extension was added to avoid
											// compression on APK file
				newFileName = TARGET_BASE_PATH
						+ filename.substring(0, filename.length() - 4);
			else
				newFileName = TARGET_BASE_PATH + filename;
			out = new FileOutputStream(newFileName);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			Log.e("tag", "Exception in copyFile() of " + newFileName);
			Log.e("tag", "Exception in copyFile() " + e.toString());
		}

	}

}
