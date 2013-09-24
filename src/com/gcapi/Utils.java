package com.gcapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Utils {
	
	Bitmap bmp; 
	URL url = null; 
	static InputStream is;

	public Utils(){}
	
	public Bitmap loadImage(String path, int width, int height)
 	{
 	    try
 	    { 
 	    	url = new URL(path); 
 	    } 
 	    catch(MalformedURLException e) 
 	    { 
 	    	e.printStackTrace(); 
 	    } 
 	    try
 	    { 
 	        HttpURLConnection conn =  (HttpURLConnection)url.openConnection(); 
 	        conn.setDoInput(true); 
 	        conn.connect(); 
 	        int length = conn.getContentLength(); 
 	        int[] bitmapData =new int[length]; 
 	        byte[] bitmapData2 =new byte[length]; 
 	        InputStream is = conn.getInputStream(); 

 	        Bitmap b=resizeBitMap(BitmapFactory.decodeStream(is), width, height);
			//GamesChart.bConnectionAvailable=true;
			return b;
 	    } 
 	    catch (IOException e) 
 	    { 
 	    	//GamesChart.bConnectionAvailable=false;
 	    	e.printStackTrace(); 
 	    } 
 	    return null;
 	     
 	}
	
	public Bitmap loadImageLocal(String path, int width, int height)
	{
		is = getClass().getClassLoader().getResourceAsStream(path);
		Bitmap b=resizeBitMap(BitmapFactory.decodeStream(is), width, height);
		return b;
	}
	
 	private Bitmap resizeBitMap(Bitmap bitmapIn, int neww, int newh)
 	{
        int width = bitmapIn.getWidth();
        int height = bitmapIn.getHeight();
        int newWidth = neww;
        int newHeight = newh;
       
        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
       
        // createa matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        
		// rotation is easy using matrix - not needed right now though
		// matrix.postRotate(45);
 
        // recreate the new Bitmap
        Bitmap bitmapOut = Bitmap.createBitmap(bitmapIn, 0, 0, width, height, matrix, true);
   
        return bitmapOut;
 	}
	
}
