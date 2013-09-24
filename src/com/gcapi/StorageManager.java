package com.gcapi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

public class StorageManager extends Activity {

	// gameplays
	public int iGamePlays=0;
	public String strGamePlays="";
		
	public StorageManager()
	{
		//Log.d("STORAGE MANAGER", "INITIALISE");
		iGamePlays=0;
		strGamePlays=""+iGamePlays;
	}
	
	public void resetGamePlays()
	{
		//Log.d("STORAGE MANAGER", "RESET GAMEPLAYS");
		iGamePlays=0;
		strGamePlays=""+iGamePlays;
		try
		{
			SharedPreferences sharedPreferences = this.getSharedPreferences("GCPREFS", MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("GAMEPLAYS", strGamePlays);
			editor.commit();
		}
		catch (Exception e)
		{
			
		}
		
	}
	
	public void saveGamePlay()
	{	
		//Log.d("STORAGE MANAGER", "SAVING GAMEPLAY");
		try
		{
			loadGamePlays(); // see if we have any stored
			iGamePlays++;
			strGamePlays=""+iGamePlays;
			SharedPreferences sharedPreferences = this.getSharedPreferences("GCPREFS", MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("GAMEPLAYS", strGamePlays);
			editor.commit();
		}
		catch (Exception e)
		{
			
		}
	}
 	
 	public void loadGamePlays()
 	{
 		//Log.d("STORAGE MANAGER", "LOAD GAMEPLAYS");
 		try
 		{
	 		SharedPreferences sharedPreferences = getSharedPreferences("GCPREFS", MODE_PRIVATE);
	 		strGamePlays = sharedPreferences.getString("GAMEPLAYS", ""); 
	 	    iGamePlays=Integer.parseInt(strGamePlays);
 		}
 		catch (Exception e) // non stored, default the values
 		{
 			iGamePlays=0;
 			strGamePlays=""+iGamePlays;
 		}
 	}
 	
 	public int getGamePlays()
 	{
 		return iGamePlays;
 	}
	
}
