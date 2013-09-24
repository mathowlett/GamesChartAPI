package com.gcapi;

import android.graphics.Bitmap;

/**
 * @author      Mat Howlett <mat@howletmedia.com>
 * @version     0.0.1              
 * @since       17/11/11
 */
public class Chart {

	
	String id;
 	String descripions[];
    String images[];
    String titles[];
    String gameurls[];
    String videourls[];
    String gameid[];
    
    Bitmap thumbs[];
    Bitmap videothumbs[];
    Bitmap playthumbs[];
    
    int entries;
    
    public Chart(int ent)
    {
    	// initialise the data arrays
    	entries = ent;
    	id = "";
		descripions = new String[entries];
		images = new String[entries];
		thumbs = new Bitmap[entries];
		videothumbs = new Bitmap[entries];
		playthumbs = new Bitmap[entries];
		titles = new String[entries];
		gameurls = new String[entries];
	    videourls = new String[entries];
	    gameid = new String[entries];
    }
	
}
