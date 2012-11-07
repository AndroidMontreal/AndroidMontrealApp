package com.androidmontreal.app.util.files;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility file related to file compression/unzip
 * 
 * @author francois.legare1
 *
 */
public class ZipUtil {

	/**
	 * Unzip the specified file from a file 
	 * 
	 * Be sure to set : <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	 * 
	 * Modified from:  
	 * Source: http://stackoverflow.com/questions/3382996/how-to-unzip-files-programmatically-in-android
	 * 
	 * @param zip target zip file
	 * @param unpackPath path where file should be unpacked
	 * @return
	 */
	public static boolean unpackZip(File zip, String unpackPath) {
							
		InputStream is;
		ZipInputStream zis;
		try {
								
			String filename;					
			is = new FileInputStream(zip);
			zis = new ZipInputStream(new BufferedInputStream(is));
			ZipEntry ze;
			byte[] buffer = new byte[1024];
			int count;

			while ((ze = zis.getNextEntry()) != null) {
				
				filename = ze.getName();
				FileOutputStream fout = new FileOutputStream(unpackPath + filename);

				while ((count = zis.read(buffer)) != -1) {
					fout.write(buffer, 0, count);
				}

				fout.close();
				zis.closeEntry();
			}

			zis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Unzip the specified file from a file 
	 * 
	 * Be sure to set : <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	 * 
	 * Modified from:  
	 * Source: http://stackoverflow.com/questions/3382996/how-to-unzip-files-programmatically-in-android
	 * 
	 * @param file input stream 
	 * @param unpackPath path where file should be unpacked
	 * @return
	 */
	public static boolean unpackZip(InputStream zipStream, String unpackPath) {
							
		InputStream is;
		ZipInputStream zis;
		try {
								
			String filename;								
			zis = new ZipInputStream(new BufferedInputStream(zipStream));
			ZipEntry ze;
			byte[] buffer = new byte[1024];
			int count;

			while ((ze = zis.getNextEntry()) != null) {
				
				filename = ze.getName();
				FileOutputStream fout = new FileOutputStream(unpackPath + filename);

				while ((count = zis.read(buffer)) != -1) {
					fout.write(buffer, 0, count);
				}

				fout.close();
				zis.closeEntry();
			}

			zis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
