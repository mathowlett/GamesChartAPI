

package com.gcapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gcapi.Base64;
import com.gcapi.Rot13;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author      Mat Howlett <mat@howletmedia.com>
 * @version     0.0.1              
 * @since       17/11/11
 */
public class GamesChart extends Activity
{
	static FrameLayout f;
	private static LocationManager lm;
	private static Location location=null;
	
	public static final int CLICKED_NOTHING=-1;
	public static final int CLICKED_ICON=0;
	public static final int CLICKED_EXIT=1;
	public static final int CLICKED_THUMB=2;
	public static final int CLICKED_TEXT=3;
	public static final int CLICKED_VIDEOLINK=4;
	public static final int CLICKED_Two=5;
	public static final int CLICKED_One=6;
	public static final int CLICKED_GCLINK=7;
	
	public static final int CLICKED_CHATBOX=12;
	
	static int clicktype=CLICKED_NOTHING;

	Bitmap bmp; 
	URL url = null; 
	JSONObject jsonobj;
		
	String imageUrl="";
     
	private static final int SHOW_LOADING=0;
	private static final int SHOW_ICON=1;
	static final int SHOW_CHART=2;
	static int chartDisplay=SHOW_LOADING;
	
	public static IconManager im ;//= new IconManager();
	public static ChartManager cm ;//= new ChartManager(this.);
	public static ThemeManager tm ;//= new ThemeManager();
	public static StorageManager sm ;//= new StorageManager();
	public static DrawableManager dm ;

	static Intent chartIntent=null;
 	
	public static boolean bConnectionAvailable=false;
	public static boolean bSetupSuccessful=false;
	public static boolean bGameReady=false;
	public static String locString = "";
	
	static Utils utils;// = new Utils();
	
	public static int displayWidth=0;
	public static int displayHeight=0;
	public static int displayOrientation=0;
	public static int shortestSize=0;
	public static final String GCURL="http://gameschart.com/android_client/gateway.php";
	public static final String ERROR_IMAGE="noimage.png";
	public static final String PLAY_IMAGE="arrowbutton.png";
	public static final String VIDEO_IMAGE="YT_icon.png";
	public static final String CLOSE_IMAGE="closebutton.png";
	public static final String GAMESCHART_IMAGE = "GC_link.png";
	public static final String GAMESCHART_ICON = "GC_icon.png";
	
	
	public static final String IMAGE_DIRECTORY_ROOT = "http://gameschart.com/img/android_api/";
	public static final String IMAGE_DIRECTORY_SIZE_SMALLEST = "smallest/";
	public static final String IMAGE_DIRECTORY_SIZE_SMALL = "small/";
	public static final String IMAGE_DIRECTORY_SIZE_MEDIUM = "medium/";
	public static final String IMAGE_DIRECTORY_SIZE_LARGE = "large/";
	public static final String IMAGE_DIRECTORY_SIZE_LARGEST = "largest/";
	public static final String IMAGE_DIRECTORY_ICONS = "icons/";
	
	
	static Context gameContext;
	static String gameID;
	static WindowManager wm;
 	static Display display;
 	
 	public static final int GLOBAL_TOUCH_SIZE=64;
 	public static final int GLOBAL_TOUCH_SIZE_HALF=32;
 	
 	public static int touchSize=64;
 	public static int touchSizeSmall=32;
 	public static int touchSizeSmallest=32;
 	
 	static int currentTouchY=0;
 	static int oldTouchY=0;
	
 // Get instance of Vibrator from current Context
	static Vibrator vib;
	 
	static boolean bAllowClick=true;
 	
	static int progress=0;
	
	static boolean chart1ThumbsLoaded=false;
	static boolean chart2ThumbsLoaded=false;
	
	static boolean fullScreen=false;
	static int statusBarHeight=58;
	
	static int lastTimePlayedYears=0;
	static int lastTimePlayedMonths=0;
	static int lastTimePlayedDays=0;
	static int lastTimePlayedHours=0;
	static int lastTimePlayedMinutes=0;
	static int lastTimePlayedSeconds=0;
	
	
	
 	public static void showChart()
 	{
 		//loadThumbnails();
 		chartDisplay=SHOW_CHART;
 	}
 	
 	public static void hideChart()
 	{	
 		chartDisplay=SHOW_ICON;
 	}
 	
 	static int testclick=0;
 	public static String setupState="waiting 0";
 	
 	// static methods that link through to the IconManager from outside
 	public static void update()
 	{
 		setupState="waiting 1";
 		if (bGameReady)
 		{
	 		setupState="starting 0";
	 		Calendar calendar=Calendar.getInstance();
	 		//calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+2);
	 		calendar.getTime();//your date +2 hours
	 		
	 		boolean saveGameplay=false;
	 		
	 		
	 		if ((int)calendar.getTime().getYear() >= lastTimePlayedYears &&
	 			(int)calendar.getTime().getMonth() >= lastTimePlayedMonths &&
	 			(int)calendar.getTime().getDay() >= lastTimePlayedDays &&
	 			(int)calendar.getTime().getHours() >= lastTimePlayedHours && 
	 			(int)calendar.getTime().getMinutes() >= lastTimePlayedMinutes+10)
	 		{
	 			Log.d("TIPE","new time");
	 			lastTimePlayedYears=(int)calendar.getTime().getYear();
		 		lastTimePlayedMonths=(int)calendar.getTime().getMonth();
		 		lastTimePlayedDays=(int)calendar.getTime().getDay();
		 		lastTimePlayedHours=(int)calendar.getTime().getHours();
		 		lastTimePlayedMinutes=(int)calendar.getTime().getMinutes();
		 		lastTimePlayedSeconds=(int)calendar.getTime().getSeconds();
		 		saveGameplay=true;
		 		sm.saveGamePlay();
	 		}

	 		
	 		//Log.d("TIME", "YEARS: "+lastTimePlayedYears);
	 		//Log.d("TIME", "MONTHS: "+lastTimePlayedMonths);
	 		//Log.d("TIME", "DAYS: "+lastTimePlayedDays);
	 		//Log.d("TIME", "HOURS: "+lastTimePlayedHours);
	 		//Log.d("TIME", "MINUTES: "+lastTimePlayedMinutes);
	 		//Log.d("TIME", "SECONDS: "+lastTimePlayedSeconds);
	 		
	 		
	 		
	 		
	 		// always update the layout
	 		updateDisplay();
	 		
	 		if (bConnectionAvailable)
		 	{
	 			setupState="starting 1";
		 		
		 		setupState="starting 2";
		 		
		 		// update icon/chart if we're connected
			 	if (bConnectionAvailable)
			 	{
			 		im.update();
			 		cm.update();
		 		}
			 	setupState="starting 3";
	 		}
	 		else
	 		{
	 			setupState="starting 4";
	 			if (isConnected(gameContext) && isGamesChartServerAvailable())
	 	 		{
	 				setupState="starting 5";
	 	 			bConnectionAvailable=true;
	 	 			setupState="starting 6";
	 	 			Log.d("SAVED PLAYS:",""+sm.getGamePlays());
	 	 			if (!bSetupSuccessful)
	 	 				setupFull(gameContext,gameID);
	 	 			
	 	 			setupState="starting 7";
	 	 			
	 		 		setupState="starting 8";
	 	 		}
	 		}
 		}
 	}
 	
 	public static void updateDisplay()
 	{
 		
 		display = wm.getDefaultDisplay();
 		displayWidth=display.getWidth();
 		displayHeight=display.getHeight();
 		displayOrientation=display.getOrientation();	
 		
 		if (displayWidth<displayHeight)
 			shortestSize=displayWidth;
 		if (displayWidth>displayHeight)
 			shortestSize=displayHeight;
 		
	 	if (chartDisplay!=SHOW_LOADING)
 		{	
	 		if (chartLayout != null)
	 		{
	 			chartLayout.invalidate();
	 		}
	 		if (chartLayoutR != null)
	 		{
	 			chartLayoutR.invalidate();
	 		}
 		}
 	}

 	@SuppressWarnings({ "static-access", "unused" })
	public static void setup(Context context, String gameid)
 	{
 		Log.d("GAMESCHART", "START");
 		
 		if (bGameReady)return;
 		gameContext=context;
 		gameID=gameid;
 		sm = new StorageManager();
 		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
 		display = wm.getDefaultDisplay();
 		displayWidth=display.getWidth();
 		displayHeight=display.getHeight();
 		
 		boolean fullScreen = false;
 		
 		fullScreen = (((Activity) context).getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;

 		
 		if (fullScreen)
 		{
 			statusBarHeight=0;
 		}
 		else
 		{
 			statusBarHeight=58;
 		}
 		
 		
 		vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
 		//updateDisplay();
 		
 		// attempt to connect to gameschart server
 		bConnectionAvailable=false;
 		
 		//if (isConnected(gameContext) && isGamesChartServerAvailable())
 		//{
 		//	bConnectionAvailable=true;
 		//}
 		//bConnectionAvailable=false;
 		
 		
 		sm.saveGamePlay();
 		
 		
 		
 		
 		f = (FrameLayout) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
 		f.addView(getRelativeLayout(context));
 	}
 	
 	
 	
 	@SuppressWarnings({ "static-access", "unused" })
	public static void setupFull(Context context, String gameID)
 	{	
 		
 		try
 		{
	 		im = new IconManager();
	 		cm = new ChartManager();
	 		tm = new ThemeManager();
	 		
	 		dm = new DrawableManager();
	 		utils = new Utils();
	 		
	 		String lngStr="";
			String latStr="";
			String cntStr="";
 		
			if (bConnectionAvailable)
			{
		 		try
		 		{
					lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
					
					Criteria criteria = new Criteria();
					criteria.setAccuracy(Criteria.ACCURACY_COARSE);
					String provider = lm.getBestProvider(criteria, false);
					Location location =lm.getLastKnownLocation(provider);
					
					
					// Force and exception here, disabling the GamesChart icon
					//String testConn = location.getProvider(); 
					
					
					/*WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
					WifiInfo wifiInfo = wifiManager.getConnectionInfo();
					int ip = wifiInfo.getIpAddress();
					String ipString = String.format( 
						    "%d.%d.%d.%d", 
						    (ip & 0xff), 
						    (ip >> 8 & 0xff),
						    (ip >> 16 & 0xff),
						    (ip >> 24 & 0xff));
					*/
					
					// Initialize the location fields
					if (location != null) 
					{
						
						int lat = (int) (location.getLatitude());
						int lng = (int) (location.getLongitude());
						latStr=""+lat;
						lngStr=""+lng;
						Geocoder geocoder = new Geocoder(context, Locale.getDefault());
						
		
						try 
						{
							List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
							cntStr=addresses.get(0).getAddressLine(3);
						} 
						catch (IOException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						locString = "" +cntStr;
					} 
					else 
					{
						locString = "" +"Provider not available";
						//bConnectionAvailable=false;
					}
					cm.debugString=""+locString;
		 		}
		 		catch (Exception e) 
		 		{	
		 			//bConnectionAvailable=false;
		 		}
			}
			chartDisplay = SHOW_ICON;
			im.init(context);
	 		cm.init(context, gameID, locString, Locale.getDefault().getDisplayLanguage(), getCurrentIP().toString(), lngStr, latStr);
	 		
	 		if (bConnectionAvailable)
	 		{	
		 		// no position passed, use default
		 		//im.init(context);
		 		//cm.init(context, gameID, locString, Locale.getDefault().getDisplayLanguage(), getCurrentIP().toString(), lngStr, latStr);
		 		//tm.setTheme(tm.THEME_DEFAULT);
	 		}
	 		else
	 		{
	 		//	cm.debugString="NO CONNECTION";
	 			
	 		}
		 		
	 		
	 		
	 		bSetupSuccessful=true;
	 		sm.resetGamePlays();
 		}
		catch (Exception e)
		{
			
			bSetupSuccessful=false;
		}
 	}

 	/*public static void draw()
 	{
 		Canvas c = new Canvas();
 		draw(c);
 	}*/
 	
 	/*public static void drawChart(Canvas c)
 	{
 		// calling this internally to minimise the
 		// chances of people forgetting to implement it
 		update(); // check status and update
 		
 		if (bSetupSuccessful && bConnectionAvailable)
 		{
	 		Paint paint = new Paint();
	 		
	 		
	 		switch (chartDisplay)
	 		{
		 		case SHOW_ICON:
		 			if (cm.showTab())
		 				im.draw(c, paint);
		 			break;
		 		case SHOW_CHART:
		 			cm.draw(c, paint);
		 			im.draw(c, paint);
		 			break;
	 		}
 		}
 		
 	}*/

 	private static int clickX=0;
 	private static int clickY=0;
 	
 	public static Intent getIntent(boolean bool)
 	{
 		if (!bConnectionAvailable)
 		{
 			return null;
 		}
 		switch(clicktype)
 		{
 			case CLICKED_THUMB: // clicked on a thumbnail
 				chartIntent=new Intent( Intent.ACTION_VIEW, GamesChart.getGameURI());
				break;
			case CLICKED_VIDEOLINK: // clicked on a youtube link
				chartIntent=new Intent( Intent.ACTION_VIEW, GamesChart.getVideoURI());
				break;
			case CLICKED_GCLINK: // clicked on chat box (not used)
				chartIntent=new Intent( Intent.ACTION_VIEW, GamesChart.getGCLINKURI());
				break;
 		}
 		return chartIntent;
 	}
 	
 	
 	
 	
 	public static boolean checkExit(int x, int y)
 	{
 		if (cm.getActionChart(x, y)==CLICKED_EXIT)
 		{
 			return true;
 		}
 		return false;
 	}
 	
 	static int diff = 0;
 	static boolean bScrollHappened=false;
 	public static boolean touchEventMove(Context c, float x, float y)
 	{
 		
 		// in move zone
 		if (bGameReady)
 		{
	 		currentTouchY=(int)y;
	
	 		if (currentTouchY > cm.getScrollZoneMin() && currentTouchY < cm.getScrollZoneMax())
	 		{
		 		if (oldTouchY < currentTouchY)
		 		{
		 			
		 			diff = currentTouchY-oldTouchY;
		 			Log.d("DOWN: ", ""+diff);
		 			if (diff>getTouchSize()>>2)
		 			{
		 				bAllowRegClick=false;
		 				cm.scrollUp(getEntries());
		 				bScrollHappened=true;
		 			}
		 			return true;
		 		}
		 		else if (oldTouchY > currentTouchY)
		 		{
		 			
		 			diff = oldTouchY-currentTouchY;
		 			Log.d("UP: ", ""+diff);
		 			if (diff>getTouchSize()>>2)
		 			{
		 				bAllowRegClick=false;
		 				cm.scrollDown(getEntries());
		 				bScrollHappened=true;
		 			}
		 			return true;
		 		}
	 		}
 		}
 	 	return false;
 	}
 	
 	
 	static int touchdownx=-1;
 	static int touchupx=-1;
 	static int touchdowny=-1;
 	static int touchupy=-1;
 	public static boolean touchEventDown(Context c, float x, float y)
 	{
 		bAllowRegClick=true;
 		touchdownx=(int)x;
 		touchdowny=(int)y;

 		if (bGameReady)
 		{
 			currentTouchY=(int)y;
 		
 			oldTouchY=currentTouchY;
 			try
 	 	 	{
 	 	 		vib.vibrate(20);
 	 	 		//showChart();
 	 	 		//chartDisplay=SHOW_CHART;
 	 	 		bAllowReleaseVibrate=true;
 	 	 	}
 	 	 	catch (Exception e){}
 		}
 		return false;
 	}
 	
 	static boolean bAllowReleaseVibrate=false;
 	public static boolean touchEventRelease(Context c, float x, float y)
 	{
 		touchupx=(int)x;
 	 	touchupy=(int)y;
 		
 		if (bGameReady)
 		{
	 		if (currentTouchY > cm.getScrollZoneMin() && currentTouchY < cm.getScrollZoneMax())
		 	{
	 			int touchxdiff=Math.abs(touchupx - touchdownx);
	 			int touchydiff=Math.abs(touchupy - touchdowny);
	 			
	 			if (touchydiff < getTouchSize()>>2)
	 			{
	 				currentTouchY=(int)y;
		 	 		oldTouchY=currentTouchY;
		 	 		diff = 0;
		 	 		touchdownx=touchupx=-1;
		 	 	 	
		 	 	 	touchdowny=touchupy=-1;
		 	 	 	
		 	 	 	 
		 	 	 	// Vibrate for 300 milliseconds
		 	 	 	try
		 	 	 	{
		 	 	 		if (bAllowReleaseVibrate)
		 	 	 		{
		 	 	 			vib.vibrate(20);
		 	 	 			bAllowReleaseVibrate=false;
		 	 	 		}
		 	 	 	}
		 	 	 	catch (Exception e)
		 	 	 	{
		 	 	 		
		 	 	 	}
		 			return checkClickGames(c, (int)x, (int)(y-cm.chartYOffset));
	 			}
		 		/*oldTouchY=currentTouchY;
		 		currentTouchY=(int)y;
		 		
		 		diff = Math.abs(oldTouchY-currentTouchY);
		 		
		 		if (diff < getTouchSize()>>2)
		 		{
		 	 		currentTouchY=(int)y;
		 	 		oldTouchY=currentTouchY;
		 	 		diff = 0;
		 			return checkClick(c, (int)x, (int)(y-cm.chartYOffset));
		 		}*/
		 		return false;
		 	}
	 		else
	 		{
	 			try
	 	 	 	{
	 				if (bAllowReleaseVibrate)
	 	 	 		{
	 	 	 			vib.vibrate(20);
	 	 	 			bAllowReleaseVibrate=false;
	 	 	 		}
	 	 	 	}
	 	 	 	catch (Exception e){}
	 			return checkClickLinks(c, (int)x, (int)(y));
	 		}
 		}
 		return false;
 	}
 	
 	
 	public static boolean bAllowRegClick=true;
 	
 	public static boolean checkClickGames(Context c, int x, int y)
 	{
 		boolean sendActivity=false;
 		Log.d("CLICK: ", "SOMETHING");
 		try
 		{
	 		clicktype=cm.getActionIcon(x, y);
	 		clickX=x;
	 	 	clickY=y-cm.chartYOffset;
	
	 	 	switch (chartDisplay)
	 		{
		 		case SHOW_ICON:
		 			Log.d("CLICK: ", "ICON");
		 			// idle
		 			switch(clicktype)
		 	 		{
		 	 			case CLICKED_ICON:
		 	 				showChart();
		 	 				break;
		 	 			default: // nothing else we can do
		 	 				break;
		 	 		}
		 			break;
		 		case SHOW_CHART:
		 			clicktype=cm.getActionChart(x, y);
		 			switch(clicktype)
		 	 		{
		 	 			
		 	 			case CLICKED_THUMB: // clicked on a thumbnail
		 	 				try{gameContext.startActivity(GamesChart.getIntent(true));sendActivity=true;}
		 	 				catch (Exception e){sendActivity=false;}
		 	 				break;
		 	 			case CLICKED_TEXT: // clicked on some text
		 	 				// do nothing, the app will fire the event
		 	 				break;
		 	 			case CLICKED_VIDEOLINK: // clicked on a youtube link
		 	 				try{gameContext.startActivity(GamesChart.getIntent(true));sendActivity=true;}
		 	 				catch (Exception e){sendActivity=false;}
		 	 				
		 	 				break;
		 	 			default: // nothing else we can do
		 	 				break;
		 	 		}
		 			break;
	 		}
 		}
 		catch (Exception e)
 		{
 			clicktype = CLICKED_NOTHING;
 		}
 		return sendActivity;			  
 	}
 	
 	public static boolean checkClickLinks(Context c, int x, int y)
 	{
 		boolean sendActivity=false;
 		
 		try
 		{
	 		clicktype=cm.getActionIcon(x, y);
	 		clickX=x;
	 	 	clickY=y-cm.chartYOffset;
	
	 	 	switch (chartDisplay)
	 		{
		 		case SHOW_ICON:
		 				
		 			// idle
		 			switch(clicktype)
		 	 		{
		 	 			case CLICKED_ICON:
		 	 				showChart();
		 	 				break;
		 	 			default: // nothing else we can do
		 	 				break;
		 	 		}
		 			break;
		 		case SHOW_CHART:
		 			clicktype=cm.getActionChart(x, y);
		 			switch(clicktype)
		 	 		{
		 	 			case CLICKED_EXIT: // clicked on the close [X]
		 	 				hideChart();
		 	 				break;
		 	 			case CLICKED_GCLINK: // clicked on chat box (not used)
		 	 				try{gameContext.startActivity(GamesChart.getIntent(true));sendActivity=true;}
		 	 				catch (Exception e){sendActivity=false;}
		 	 				break;
		 	 				
		 	 			default: // nothing else we can do
		 	 				break;
		 	 		}
		 			break;
	 		}
 		}
 		catch (Exception e)
 		{
 			clicktype = CLICKED_NOTHING;
 		}
 		return sendActivity;			  
 	}
 	
 	public static boolean checkClick(int x, int y)
 	{
 		boolean sendActivity=false;
 		
 		try
 		{
	 		clicktype=cm.getActionIcon(x, y);
	 		clickX=x;
	 	 	clickY=y-cm.chartYOffset;
	
	 	 	switch (chartDisplay)
	 		{
		 		case SHOW_ICON:
		 				
		 			// idle
		 			switch(clicktype)
		 	 		{
		 	 			case CLICKED_ICON:
		 	 				showChart();
		 	 				break;
		 	 			default: // nothing else we can do
		 	 				break;
		 	 		}
		 			break;
		 		case SHOW_CHART:
		 			clicktype=cm.getActionChart(x, y);
		 			switch(clicktype)
		 	 		{
		 	 			case CLICKED_EXIT: // clicked on the close [X]
		 	 				hideChart();
		 	 				
		 	 				break;
		 	 				
		 	 			case CLICKED_THUMB: // clicked on a thumbnail
		 	 				// do nothing, the app will fire the event
		 	 				
		 	 				sendActivity=true;
		 	 				break;
		 	 			
		 	 			case CLICKED_TEXT: // clicked on some text
		 	 				// do nothing, the app will fire the event
		 	 				break;
		 	 				
		 	 			case CLICKED_VIDEOLINK: // clicked on a youtube link
		 	 				// do nothing, the app will fire the event
		 	 				
		 	 				sendActivity=true;
		 	 				break;
		 	 				
		 	 			case CLICKED_CHATBOX: // clicked on chat box (not used)
		 	 				break;
		 	 				
		 	 			case CLICKED_GCLINK: // clicked on chat box (not used)
		 	 				
		 	 				sendActivity=true;
		 	 				break;
		 	 				
		 	 			default: // nothing else we can do
		 	 				break;
		 	 		}
		 			break;
	 		}
 		}
 		catch (Exception e)
 		{
 			clicktype = CLICKED_NOTHING;
 		}
 		return sendActivity;			  
 	}
 	
 	
 	
 	public static boolean openClicked(Context c, int x, int y)
 	{
 		clicktype=cm.getActionIcon(x, y);
 		clickX=x;
 	 	clickY=y-cm.chartYOffset;
 	 	
 	 	if (chartDisplay == SHOW_ICON && clicktype == CLICKED_ICON)
 	 	{
 	 		return true;
 	 	}
 		return false;			  
 	}
 	
 	public static boolean closeClicked(Context c, int x, int y)
 	{
 		clicktype=cm.getActionChart(x, y);
 		clickX=x;
 	 	clickY=y-cm.chartYOffset;
 	 	
 	 	if (chartDisplay == SHOW_CHART && clicktype == CLICKED_EXIT)
 	 	{
 	 		return true;
 	 	}
 		return false;			  
 	}
 	
 	
 	
 	
 	
 	
 	@SuppressWarnings("static-access")
	public static Uri getGameURI()
 	{
 		return cm.uri_game;
 	}
 	
 	@SuppressWarnings("static-access")
	public static Uri getVideoURI()
 	{
 		return cm.uri_video;
 	}
 	
 	@SuppressWarnings("static-access")
	public static Uri getGCLINKURI()
 	{
 		return cm.uri_gclink;
 	}
 	
 	public static void turnOnAnimations()
 	{
 		cm.setAnimation(true);
 	}
 	
 	public static void turnOffAnimations()
 	{
 		cm.setAnimation(false);
 	}
 	
 	public static void showTab()
 	{
 		cm.showTab(true);
 	}
 	
 	public static void hideTab()
 	{
 		cm.showTab(false);
 	}
 	 
 	public static void setThemeColor(int col)
 	{
 		tm.mainTheme.setMainColor(col);
 	}
 	
 	public static void openCharts()
 	{
 		showChart();
 	}
 	
 	public static void closeCharts()
 	{
 		hideChart();
 	}
 	
 	public static String getWifiIp(Context c)
 	{
		String srtIp = "";
 		try
 		{
		 	WifiManager myWifiManager = (WifiManager)c.getSystemService(Context.WIFI_SERVICE);
		    WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
		    
		    int myIp = myWifiInfo.getIpAddress();
		   
		    int intMyIp3 = myIp/0x1000000;
		    int intMyIp3mod = myIp%0x1000000;
		   
		    int intMyIp2 = intMyIp3mod/0x10000;
		    int intMyIp2mod = intMyIp3mod%0x10000;
		   
		    int intMyIp1 = intMyIp2mod/0x100;
		    int intMyIp0 = intMyIp2mod%0x100;
		   
		    srtIp = "" + String.valueOf(intMyIp0)
		      + "." + String.valueOf(intMyIp1)
		      + "." + String.valueOf(intMyIp2)
		      + "." + String.valueOf(intMyIp3);
 		}
 		catch (Exception e)
 		{
 			return "getWifiIP failed";
 		}
	    return srtIp;
 	}
 	
 	
 	public static String getCurrentIP() 
 	{
 	    try 
 	    {
 	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) 
 	        {
 	            NetworkInterface intf = en.nextElement();
 	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) 
 	            {
 	                InetAddress inetAddress = enumIpAddr.nextElement();
 	                if (!inetAddress.isLoopbackAddress()) 
 	                {
 	                    return inetAddress.getHostAddress().toString();
 	                }
 	            }
 	        }
 	    } 
 	    catch (SocketException ex) 
 	    {
 	        //Log.e(LOG_TAG, ex.toString());
 	    }
 	    return null;
 	}
 	
 	public static ChartManager getChartManager()
 	{
 		return cm;
 	}

 	public static void forceClick()
 	{
 		cm.swapCharts();
 	}
 	
 	static int alphapulse=32;
 	static int pulsedir=0;
 	static LinearLayout chartLayout;
 	
 	public static LinearLayout getLayout(Context c)
 	{
 		chartLayout = new LinearLayout(c);
 		chartLayout.setBackgroundDrawable(new Drawable() {
 			
            @Override
            public void draw(Canvas canvas) 
            {
            	Paint paint = new Paint();
            	
            	update();
            	
            	if (bConnectionAvailable)
        	 	{
	            	
	         		
	         		switch (chartDisplay)
	         		{
	         			case SHOW_LOADING:
	         				paint.setColor(Color.WHITE);
	         				canvas.drawRect(80, 80, 160, 160,paint);
	         				break;
	        	 		case SHOW_ICON:
	        	 			if (cm.showTab())
	        	 				im.draw(canvas, paint);
	        	 			break;
	        	 		case SHOW_CHART:
	        	 			im.draw(canvas, paint);
	        	 			cm.draw(canvas, paint);
	        	 			break;
	         		}
        	 	}
            	else
        	 	{
            		/*try
            		{
            			paint.setColor(Color.WHITE);
            			paint.setAlpha(alphapulse);
            			paint.setAntiAlias(true);
            			int pad=getIconSize()>>2;
            			int barw=getIconSize()*3;
            			int bary=pad;
            			int barh=getIconSize();
            			int barx=displayWidth-(barw+pad);
            			
            			
         				canvas.drawRect(barx, bary, barx+barw, bary+barh, paint);
         				paint.setAlpha(244);
         				paint.setColor(Color.GRAY);

         				canvas.drawText("Loading", barx+2, bary+14, paint);
         				canvas.drawText("GamesChart...", barx+2, bary+28, paint);
            		}
            		catch (Exception e){}
            		
            		try
            		{
            			paint.setColor(Color.GREEN);
            			canvas.drawRect(0, displayHeight-100, progress, displayHeight, paint);
            		}
            		catch (Exception e){}
            		*/
        	 	}
            	
            }
            
            @Override
            public int getOpacity() {
                    return 0;
            }
            
            @Override
            public void setAlpha(int alpha) {
                    
            }
            
            @Override
            public void setColorFilter(ColorFilter cf) {
                    
            }
 		});	
 		return chartLayout;
 	}
 	
 	static RelativeLayout chartLayoutR;
 	
 	public static RelativeLayout getRelativeLayout(Context c)
 	{
 		chartLayoutR = new RelativeLayout(c);
 		chartLayoutR.setBackgroundDrawable(new Drawable() {
 			
            @Override
            public void draw(Canvas canvas) 
            {
            	if (GamesChart.bGameReady)
            		update();
            	
            	if (bConnectionAvailable)
        	 	{
	            	Paint paint = new Paint();
	         		
	         		switch (chartDisplay)
	         		{
	         			case SHOW_LOADING:
	         				paint.setColor(Color.WHITE);
	         				canvas.drawRect(80, 80, 160, 160,paint);
	         				break;
	        	 		case SHOW_ICON:
	        	 			if (cm.showTab())
	        	 				im.draw(canvas, paint);
	        	 			break;
	        	 		case SHOW_CHART:
	        	 			im.draw(canvas, paint);
	        	 			cm.draw(canvas, paint);
	        	 			break;
	         		}
        	 	}
            }
            
            @Override
            public int getOpacity() {
                    return 0;
            }
            
            @Override
            public void setAlpha(int alpha) {
                    
            }
            
            @Override
            public void setColorFilter(ColorFilter cf) {
                    
            }
 		});	
 		return chartLayoutR;
 	}
 	public static RelativeLayout getBlankRelativeLayout(Context c)
 	{
 		chartLayoutR = new RelativeLayout(c);
 		chartLayoutR.setBackgroundDrawable(new Drawable() {
 			
            @Override
            public void draw(Canvas canvas) 
            {
            	
            }
            
            @Override
            public int getOpacity() {
                    return 0;
            }
            
            @Override
            public void setAlpha(int alpha) {
                    
            }
            
            @Override
            public void setColorFilter(ColorFilter cf) {
                    
            }
 		});	
 		return chartLayoutR;
 	}
 	
 	
 	
 	public static boolean isConnected(Context context) {
 	    boolean connected = false;

 	    ConnectivityManager cm = 
 	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

 	    if (cm != null) {
 	        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

 	        for (NetworkInfo ni : netInfo) {
 	            if ((ni.getTypeName().equalsIgnoreCase("WIFI")
 	                    || ni.getTypeName().equalsIgnoreCase("MOBILE"))
 	                    && ni.isConnected() && ni.isAvailable()) {
 	                connected = true;
 	            }

 	        }
 	    }
 	    return connected;
 	}
 	
 	public static boolean isGamesChartServerAvailable() 
 	{
	 	try 
	 	{
	 		URL url = new URL(GCURL);
	 		HttpURLConnection con = (HttpURLConnection) url.openConnection();
	 		readStream(con.getInputStream());
	 		return true;
	 	} 
	 	catch (Exception e) 
	 	{
	 		return false;
	 		
	 	}
 	}
 	
 	private static void readStream(InputStream in) throws IOException {
 		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
 		String line = "";
 		while ((line = reader.readLine()) != null) {
 			System.out.println(line);
 		}
 	}
 	
 	public static void gameStarted(boolean startThread)
 	{
 		if (!bGameReady)
 		{
	 		bGameReady=true;
	 		if (startThread)
	 		{
	 			Starter t = new Starter();
	 			t.run();
	 		}
 		}
 	}
 	
 	public static void gameStarted()
 	{
 		//Log.d("GAME STARTED", ""+1);
 		try
 		{
 			gameStarted(true);
 		}
 		catch (Exception e)
 		{
 			gameStarted(false);
 		}
 	}
 	
 	public static boolean isPortrait()
 	{
 		if (displayWidth>displayHeight)
 			return false;
 		else
 			return true;
 	}
 	public static boolean isLandscape()
 	{
 		if (displayHeight>displayWidth)
 			return false;
 		else
 			return true;
 	}
 	
 	public static int getTouchSize()
 	{
 		if (shortestSize <= 240)
 		{
 			touchSize=40;
 		}
 		else if (shortestSize >= 480)
 		{
 			touchSize=96;
 		}
 		else
 		{
 			touchSize=64;
 		}
 		return touchSize;	
 	}
 	
 	public static int getTouchSizeSmall()
 	{
 		//updateDisplay();
 		if (shortestSize <= 240)
 		{
 			touchSize=32;
 		}
 		else if (shortestSize >= 480)
 		{
 			touchSizeSmall=64;
 		}
 		else
 		{
 			touchSizeSmall=48;
 		}
 		return touchSizeSmall;	
 	}
 	
 	public static int getIconSize()
 	{
 		//updateDisplay();
 		if (shortestSize <= 240)
 		{
 			touchSize=32;
 		}
 		else if (shortestSize >= 480)
 		{
 			touchSizeSmall=64;
 		}
 		else
 		{
 			touchSizeSmall=48;
 		}
 		return touchSizeSmall;	
 	}
 	
 	public static int getTouchSizeSmallest()
 	{
 		//updateDisplay();
 		if (shortestSize <= 240)
 		{
 			touchSizeSmallest=24;
 		}
 		else if (shortestSize >= 480)
 		{
 			touchSizeSmallest=40;
 		}
 		else
 		{
 			touchSizeSmallest=36;
 		}
 		return touchSizeSmallest;	
 	}
 	
 	public static int getEntries()
 	{
 		int entries=5;
 		
 		if (displayHeight <= 240 && isLandscape())
 			entries=3;//--;
 		
 		//if (displayHeight)
 			
 		if (shortestSize > 640 && isPortrait()) // it's a pad, big enough for all 10
 		{
 			entries=10;
 		}
 		if (shortestSize>240 && shortestSize<640 && isLandscape())
 		{
 			entries=2;	
 		}
 		
 		
 		return entries;
 	}
 	
 	public static final int DEVICE_SIZE_SMALLEST=0; // Wildfire up tp 240
 	public static final int DEVICE_SIZE_SMALL=1; // up to 320
 	public static final int DEVICE_SIZE_MEDIUM=2; // up to 480
 	public static final int DEVICE_SIZE_LARGE=3; // up to 800
 	public static final int DEVICE_SIZE_LARGEST=4; // over 800
 	
 	public static int getDeviceClass()
 	{
 		if (shortestSize<=240)
 		{
 			return DEVICE_SIZE_SMALLEST;
 		}
 		else if (shortestSize > 240 && shortestSize <= 320)
 		{
 			return DEVICE_SIZE_SMALL;
 		}
 		else if (shortestSize > 320 && shortestSize <= 480)
 		{
 			return DEVICE_SIZE_MEDIUM;
 		}
 		else if (shortestSize > 480 && shortestSize < 800)
 		{
 			return DEVICE_SIZE_LARGE;
 		}
 		else if (shortestSize >= 800) // tablets
 		{
 			return DEVICE_SIZE_LARGEST;
 		}
 		return DEVICE_SIZE_MEDIUM; // just in case !
 	}
 	
 	public static int getMaxDecriptionChars()
 	{
 		int length=32;
 		switch (GamesChart.getDeviceClass())
 		{
 			case GamesChart.DEVICE_SIZE_SMALLEST: // HTC Wildfire
 				//Log.d("DEVICE: ", "SMALLEST");
 				if (isPortrait())
 					length=16;
 				else
 					length=24;
 				break;
 			case GamesChart.DEVICE_SIZE_SMALL: // HTC Wildfire S
 				//Log.d("DEVICE: ", "SMALL");
 				if (isPortrait())
 					length=16;
 				else
 					length=32;
 				break;
 			case GamesChart.DEVICE_SIZE_MEDIUM: // HTC Desire
 				//Log.d("DEVICE: ", "MEDIUM");
 				if (isPortrait())
 					length=18;
 				else
 					length=24;
 				break;
 			case GamesChart.DEVICE_SIZE_LARGE: // small tablet
 				//Log.d("DEVICE: ", "LARGE");
 				if (isPortrait())
 					length=56;
 				else
 					length=64;
 				break;
 			case GamesChart.DEVICE_SIZE_LARGEST: // large tablet
 				//Log.d("DEVICE: ", "LARGEST");
 				if (isPortrait())
 					length=40;
 				else
 					length=64;
 				break;
 	 	}
 		//if (cm.activeChart==cm.CHART_One)
 		//	return length-4;
 		return length;
 	}
 	
 	public static int getMaxTitleChars()
 	{
 		return getMaxDecriptionChars()-4;
 	}
 	

 	public static int flingY=0;
 	public static void fling(int x, int y) 
 	{
		
		flingY=y;
		
 	}
 	
 	public static String getImageDirectory()
 	{
 		String dir = IMAGE_DIRECTORY_SIZE_MEDIUM;
 			
 		switch (GamesChart.getDeviceClass())
 		{
 			case GamesChart.DEVICE_SIZE_SMALLEST: // HTC Wildfire
 				dir = IMAGE_DIRECTORY_SIZE_SMALLEST;
 				break;
 			case GamesChart.DEVICE_SIZE_SMALL: // HTC Wildfire S
 				dir = IMAGE_DIRECTORY_SIZE_SMALL;
 				break;
 			case GamesChart.DEVICE_SIZE_MEDIUM: // HTC Desire
 				dir = IMAGE_DIRECTORY_SIZE_MEDIUM;
 				break;
 			case GamesChart.DEVICE_SIZE_LARGE: // small tablet
 				dir = IMAGE_DIRECTORY_SIZE_LARGE;
 				break;
 			case GamesChart.DEVICE_SIZE_LARGEST: // large tablet
 				dir = IMAGE_DIRECTORY_SIZE_LARGEST;
 				break;
 	 	}	
 		return IMAGE_DIRECTORY_ROOT+dir;
 	}
 	
 	
 	public static boolean openChart(final MotionEvent event)
 	{
   		// pause your game here
 		if (chartDisplay>SHOW_LOADING && bGameReady && bConnectionAvailable && bSetupSuccessful )
 		{
	        if (event.getAction() == MotionEvent.ACTION_UP)
	        {
	        	if (openClicked(gameContext, (int)event.getX(),(int)event.getY()))
	        		return true;
	        }
 		}
 		return false;
   	}
   	
   	public static boolean closeChart(final MotionEvent event)
 	{
   		// un-pause your game here
   		if (chartDisplay>SHOW_LOADING && bGameReady && bConnectionAvailable && bSetupSuccessful )
 		{
		   	 if (event.getAction() == MotionEvent.ACTION_UP)
		     {
		   		if (closeClicked(gameContext, (int)event.getX(),(int)event.getY()))
	        		return true;
		     }
 		}
   		return false;
   	}
 	
   	public static void refreshCharts()
 	{
 		chartDisplay=SHOW_LOADING;
 		bConnectionAvailable=false;
 		bSetupSuccessful=false;
 		bGameReady=false;
 		gameStarted();
 		
 	}
   	public static void loadThumbnails()
   	{
   		if (!chart1ThumbsLoaded && ChartManager.activeChart == 0)
   		{
   			cm.loadThumbnails(0);
   			chart1ThumbsLoaded=true;
   		}
   		if (!chart2ThumbsLoaded && ChartManager.activeChart == 1)
   		{
   			cm.loadThumbnails(1);
   			chart2ThumbsLoaded=true;
   		}
   	}
   	
   	public static void gameStopped()
 	{
   		pauseApp();
 	}
   	
 	public static void pauseApp()
 	{
 		chartDisplay=SHOW_LOADING;
 		bConnectionAvailable=false;
 		bSetupSuccessful=false;
 		bGameReady=false;
 		
 	}
 	
 	public static void resumeApp()
 	{
 		gameStarted();
 	}
 	
 	
 	public static void checkEvents(Context c, final MotionEvent event)
 	{
 		if (!bAllowClick)
 			return;
 		
 		//bAllowClick=false;
 		if (!bGameReady)
 			GamesChart.gameStarted(); // if not already started
 		
 		if (chartDisplay>SHOW_LOADING && bGameReady && bConnectionAvailable && bSetupSuccessful )
 		{
	 		if (event.getAction() == MotionEvent.ACTION_DOWN) 
	        	GamesChart.touchEventDown(c, event.getX(), event.getY()-statusBarHeight);   
	        if (event.getAction() == MotionEvent.ACTION_MOVE) 
	        	GamesChart.touchEventMove(c, event.getX(), event.getY()-statusBarHeight);
	        if (event.getAction() == MotionEvent.ACTION_UP)
	        	GamesChart.touchEventRelease(c, event.getX(),event.getY()-statusBarHeight);
 		}
 	}
 	public static void checkEvents(final MotionEvent event)
 	{
 		if (!bGameReady)
 			GamesChart.gameStarted(); // if not already started
 		
 		if (chartDisplay>SHOW_LOADING && bGameReady && bConnectionAvailable && bSetupSuccessful )
 		{
	 		if (event.getAction() == MotionEvent.ACTION_DOWN) 
	        	GamesChart.touchEventDown(gameContext, event.getX(), event.getY()-statusBarHeight);   
	        if (event.getAction() == MotionEvent.ACTION_MOVE) 
	        	GamesChart.touchEventMove(gameContext, event.getX(), event.getY()-statusBarHeight);
	        if (event.getAction() == MotionEvent.ACTION_UP)
	        	GamesChart.touchEventRelease(gameContext, event.getX(),event.getY()-statusBarHeight);
 		}
 	}

}
class Starter extends TimerTask {
	    private Handler mHandler = new Handler(Looper.getMainLooper());

	    @Override
	    public void run() {
	       // ...
	       mHandler.post(new Runnable() {
	          public void run() {
	              GamesChart.update();
	          }
	       });
	       // ...
	     }
	}


