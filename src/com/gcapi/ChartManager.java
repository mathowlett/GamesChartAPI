package com.gcapi;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * @author      Mat Howlett <mat@howletmedia.com>
 * @version     0.0.1              
 * @since       17/11/11
 */
public class ChartManager //extends SurfaceView implements SurfaceHolder.Callback
{
	// some information about our host screen
	Context context;
	Display display;
	Bitmap screenBmp;
	Canvas screenCanvas;
	Paint screenPaint;
	
	Theme mainTheme;
	
	//Bitmap bmp; 
    URL url = null;
    
    // chart types
    
    private static int chartSequence=0; // 0 = One then Two
    //private static int chartSequence=1; // 1 = Two then One
    
    // if One first
    /* 
    static final int CHART_One=0;
    private static final int CHART_Two=1;
    */
    
    static int CHART_One=0;
    static int CHART_Two=1;
    
    
    static int activeChart = CHART_One;
    
    
    private static final int CHART_TYPE2=2;
    private static final int CHART_TYPE3=3;
    private static final int CHART_TYPE4=4;
    
    private int numberOfCharts=2;
   
    private static final int MAX_CHARTS=2;
    private static final int MAX_ENTRIES=10;
    
    private int numberOfEntries=10;
    private int entriesDisplayed=5;
    
    public static Chart [] charts;
    //Chart chartTwo;

    private String gameName;
 	private String gameID;
 	private String gameClicks;
 	private String gameTimeStamp;
 	private String country;
 	private String longitude;
 	private String latitude;
 	
 	private int nClicks;
 	
 	JSONObject jsonobj;

 	// device screen size
 	public  int screenW=0; 
 	public  int screenH=0;
 	public  int screenOrientation=0;
 	public  int oldScreenOrientation=0;
 	
 	// square layout block, relative to screen size
 	private int screenUnit=0; 
 	private int padding=0;
 	
 	// container size for the text
 	private int titletW=0;
 	private int titleH=0;
 	
 	// container size for the text
 	private int slotTextW=0;
 	private int slotTextH=0;
 	
 	// container size for the icon
 	private int iconW=0;
 	private int iconH=0;
 	
 	// close box
 	private int closeBoxSize=32;
 	//private closeIconSize=0;
 	
 	// list section width
 	private int sectionWidth=80;

 	private int navBarHeight;

 	Rect [] rectThumbs;
 	Rect [] rectTitles;
 	Rect [] rectDescriptions;
 	Rect [] rectVideo;
 	Rect [] rectPlayGame;
 	
 	Rect rectNavBar;
 	Rect rectCloseBox;
 	Rect rectOneGames;
 	Rect rectTwoChart;
 	Rect rectGCLink;
 	Rect scrollDown;
 	Rect scrollUp;
 	
 	private int xoffsets[];
 	public int chartYOffset=0;
 	private int chartYTarget=0;
 	
 	Bitmap imgGCLinkIcon;
 	Bitmap closeIcon;
 	
 	static Uri uri_game=null;
	static Uri uri_video=null;
	static Uri uri_gclink=null;
	
	private int x=padding;
	private int y=padding;
	
	private int chartOffset=0;	
	public  int chartX=0;
	public  int chartY=0;
	public  int chartW=0;
	public  int chartH=0;
	
	private static final String GAMEID="00000003";
	
	private String resp="NO DATA AVAILABLE";
	
	private int clicksThisSession=0;
	private int currentTimestamp=0;
	private int lastTimestamp=0;
	
	private int videoIconW=0;
	private int videoIconH=0;
	private int playIconW=0;
	private int playIconH=0;
	
	private static final String strOneLong="Chart One";
	private static final String strTwoLong="Chart Two";
	private static final String strOneShort="One";
	private static final String strTwoShort="Two";
	
	private String strMenuOne=strOneLong;
	private String strMenuTwo=strTwoLong;
	
	// 
	private String strSessionID="";
	private String strTarget="";
	
	// animations
	private boolean bAnimations=true;
	
	// tab
	private boolean bShowTab=true;
	
	String language="";
	String ipAddress="";

	String debugString="";
	String debugString2="";
	String sessionID="";
	String chronKey="";
	String loadedSkin="";
	
	String addressToLaunch="";
	
	private boolean readyToDraw=false;
	
	private byte slotsFilled[][];
	
	
	int progressMax=0;
	int progressInc=0;
	
	
	Bitmap playIcon;
	Bitmap youtubeIcon;
	Bitmap errorIcon;
	Bitmap chartIcon;
	
	int offlinePlays=0;
	
	
	
	public void init(Context c, String gKey, String loc, String lang, String ip, String lng, String lat)
	{
		// set up the display
		context = c;
		
		// the unique game id
		gameID=""+gKey;
		
		// country code
		country=loc;
		
		// long/lat
	 	longitude=lng;
	 	latitude=lat;
	 	
	 	// ip address
	 	ipAddress=ip;
	 	
	 	// lanuage
	 	language = lang;
	 	
	 	// session id
	 	sessionID = "58a3aa96918c3794b0c82ca55c7cd67c"; // temp
	 	chronKey = "222d86ceb80a469c58f44db9600e8d30";
	 	
	 	// offlineplays
	 	offlinePlays=GamesChart.sm.getGamePlays();
	 
		// some variable for orientation handling
		screenOrientation=GamesChart.displayOrientation;
    	oldScreenOrientation=screenOrientation;
    	
    	// basc screeb dimensions
    	screenW=GamesChart.displayWidth;
		screenH=GamesChart.displayHeight;
		progressMax=screenW;
		progressInc=progressMax/40;
		
     	// Canvas Version
     	screenBmp = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);
    	screenCanvas = new Canvas(screenBmp);
     	
     	// use short version or titles for tabs if the screen is below a certain size
     	if(screenW < 360)
     	{
     		strMenuOne=strOneShort;
     		strMenuTwo=strTwoShort;
     	}
     	
     	// limit the number of entries shown for smaller s
     	//if (screenH < 640)
     	//{
     	//	numberOfEntries=2;//10;
     	//}
     	
     	// create the chart objects array
     	charts= new Chart[numberOfCharts];
		
     	// load up the icon, this is also used to check initial connection
     	iconW=GamesChart.getTouchSize();
     	iconH=iconW;
     	
     	videoIconW=GamesChart.getTouchSize();
    	videoIconH=GamesChart.getTouchSize();
    	playIconW=GamesChart.getTouchSize();
    	playIconH=GamesChart.getTouchSize();
    	
     	int closeIconSize=GamesChart.getTouchSizeSmallest();//-(GamesChart.getTouchSizeSmall()>>3);
     	//if (closeIconSize>54)
     	//	closeIconSize=54;
     	
     	if (closeIconSize<28)
     		closeIconSize=28;
     	
     	
     
     	// build the layout
		buildChartLayout(numberOfEntries); // do this for both Canvas and OpenGL versions
		
		// make sure any animations are ready to go
		resetAnimations();
		resetSlots();
		
		// request data from the server and build the chart accordingly,
		// we only need to do this once, so do it here
		//if (!GamesChart.bConnectionAvailable)
		//	createChartsLocal(gameID); // pass our game id here
		//else
		
		
		//createChartsLocal(gameID); 
		
		createChartsFull(gameID);
		
		
		
		readyToDraw=true;

	}
	
	
    private void resetSlots()
    {
    	slotsFilled = new byte [MAX_CHARTS][MAX_ENTRIES];
		for (int i=0; i<MAX_CHARTS; i++)
			for (int j=0; j<MAX_ENTRIES; j++)
				slotsFilled[i][j]=0;
    }
    @SuppressWarnings("static-access")
    
    private void createChartsLocal(String gameid)
    {
    	numberOfEntries=10;
		
		// create 2 tables, One first, then Two
		for (int ctype=0; ctype<2; ctype++)
		{
			charts[ctype].id = "blaa";
			
			// fill the local chart data arrays
		    for (int i=0; i<numberOfEntries; i++)
		    {    	
		    	// get the image path
		    	charts[ctype].images[i]=null;//GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE;	
		    	
		    	
		    	// get the game title
		    	charts[ctype].titles[i]="Your Game Here";
		    	
		    	charts[ctype].gameid[i]="GameID"+i;
		    	
		    	// get the game description
		    	charts[ctype].descripions[i]="Click her to find out how..";
		    	
		    	// get the game url
		    	charts[ctype].gameurls[i]="http://www.gameschart.com";
		    	
		    	// get the youtube url
		    	charts[ctype].videourls[i]="http://www.youtube.com/watch?v=o3RR05KDAcY";
		    	
		    	// create the thumbnails
		    	charts[ctype].thumbs[i] = null;//loadImage(GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE, iconW, iconH-2);
		    	
		    	// get the youtube icon
		    	charts[ctype].videothumbs[i] = null;//loadImage(GamesChart.getImageDirectory()+GamesChart.VIDEO_IMAGE,iconW>>1, iconH>>1);
		    	
		    	// get the arrow icon
		    	charts[ctype].playthumbs[i] = null;//loadImage(GamesChart.getImageDirectory()+GamesChart.PLAY_IMAGE, iconW>>1, iconH>>1);
		    }
		}
		//loadedSkin = ""+jArrData.getString("skin");
		sessionID = "0203020302320";
		
		GamesChart.im.useAnimatedIcon(false);
		
		
		// get the arrow icon
		closeIcon = null;//loadImage(GamesChart.getImageDirectory()+GamesChart.CLOSE_IMAGE, closeIconSize, closeIconSize);
     	imgGCLinkIcon = null;//loadImage(GamesChart.getImageDirectory()+GamesChart.GAMESCHART_IMAGE, GamesChart.getTouchSizeSmallest()*4, GamesChart.getTouchSizeSmallest());
		
    	debugString="LOADED DATA OK";
    
    	if (loadedSkin.equalsIgnoreCase("DEFAULT"))
    	{
    		GamesChart.tm.setTheme(GamesChart.tm.THEME_DEFAULT);
    	}
    	createChartsFull(gameID);
    }
    
    
    
    
	void createChartsFull(final String gameid)
    {
		Log.d("CREATE CHARTS FULL", "GO");
		
		
    	// create the 2 chart objects
    	charts[CHART_One] =  new Chart(numberOfEntries);
		charts[CHART_Two] =  new Chart(numberOfEntries);

    	String strTarget="onGameLoad"; // the command passed to the server
    	String appdata=""; // the data passed to the server
    	
    	JSONObject 	jobj_data = new JSONObject(); // json object for the data
    	JSONObject	jobj_payload = new JSONObject();
    	JSONObject  jobj_capabilities = new JSONObject();
    	
		// attempt to build ths request data
		try 
		{
	    	jobj_capabilities.put("supports64BitProcesses", "");
	    	jobj_capabilities.put("supports32BitProcesses", "");
	    	jobj_capabilities.put("hasIME", "");
	    	jobj_capabilities.put("language", language);
	    	jobj_capabilities.put("manufacturer", "MANU");//android.os.Build.MANUFACTURER);
	    	jobj_capabilities.put("cpuArchitecture", android.os.Build.PRODUCT);
	    	jobj_capabilities.put("os", ""+ android.os.Build.VERSION.RELEASE);
	    	jobj_capabilities.put("isEmbeddedInAcrobat", "");
	    	jobj_capabilities.put("maxLevelIDC", "");
	    	jobj_capabilities.put("windowlessDisable", "");
	    	jobj_capabilities.put("localFileReadDisable", "");
	    	jobj_capabilities.put("avHardwareDisable", "");
	    	jobj_capabilities.put("playerType", "");
	    	jobj_capabilities.put("isDebugger", "");
	    	jobj_capabilities.put("hasScreenBroadcast", "");
	    	jobj_capabilities.put("hasScreenPlayback", "");
	    	jobj_capabilities.put("hasPrinting", "");
	    	jobj_capabilities.put("hasEmbeddedVideo", "");
	    	jobj_capabilities.put("hasStreamingVideo", "");
	    	jobj_capabilities.put("hasStreamingAudio", "");
	    	jobj_capabilities.put("version", "ANDROID:"+android.os.Build.DEVICE);
	    	jobj_capabilities.put("hasAudio", "");
	    	jobj_capabilities.put("hasMP3", "");
	    	jobj_capabilities.put("hasAudioEncoder", "");
	    	jobj_capabilities.put("hasVideoEncoder", "");
	    	jobj_capabilities.put("hasTLS", "");
	    	jobj_capabilities.put("screenResolutionX", ""+screenW);
	    	jobj_capabilities.put("screenResolutionY", ""+screenH);
	    	jobj_capabilities.put("screenDPI", "72");
	    	jobj_capabilities.put("screenColor", "color");
	    	jobj_capabilities.put("pixelAspectRatio", "1");
	    	jobj_capabilities.put("hasAccessibility", "");
	    	
	    	// add the capabilities to the payload
	    	jobj_payload.put("capabilities", jobj_capabilities);
	    	
	    	//gameID = ""+gameid;//aa8c48f436ff422466b8f3eb2c4cdaaa";
	    	// add the other stuff
	    	jobj_payload.put("chronKey", chronKey);
	    	jobj_payload.put("gameIDHash", ""+gameid);
	    	jobj_payload.put("apiType", "AS2-API");
	    	jobj_payload.put("brainVersion", "0.0.1");
	    	jobj_payload.put("apiVersion", ""+"ee");//R.string.APIVERSION);
	    	jobj_payload.put("swfURL", "");
	    	jobj_payload.put("offlinePlays", offlinePlays);
	    	
	    	offlinePlays=0;
	    	GamesChart.sm.resetGamePlays();
	    	
	    	// build the data object
	    	jobj_data.put("target", strTarget);
	    	jobj_data.put("payload", jobj_payload);
    	
	    	Log.d("UNENCODED", ""+jobj_payload);
	    	// turn it into an encoded string
	    	appdata=Rot13.rot13(Base64.encodeToString(jobj_data.toString().getBytes(), Base64.DEFAULT));
	    	
	    	//appdata =jobj_data.toString();
	    	
	    	// bingo, we have our data encoded and ready to send !
		} 
		catch (JSONException e1) 
		{
		    e1.printStackTrace(); // oops!
		    debugString="NO CONNECTION";
		}

		
		
		
		Log.d(GamesChart.GCURL, appdata);
		
		
    	//"http://gameschart.com/flash_client/gateway2.php"
    	//"http://www.howlettmedia.com/test/gcprocess.php"

		// pass the data to the server and get the result
		
		//String response = processAppData("http://www.howlettmedia.com/test/gctest.php", appdata);
		//String response = processAppData("http://www.howlettmedia.com/test/gcprocess.php", appdata);
		String response = "";
		try
		{
			response=processAppData(GamesChart.GCURL, appdata);
			
			Log.d("APPDATA", ""+response);
			//debugString2="http://test.gameschart.com/android_client/gateway.php"+appdata;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	    	debugString2="NO CONNECTION";
	    	//GamesChart.bConnectionAvailable=false;
			e.printStackTrace();
		}
		//debugString = response;
		//DEBUG=true;
    	////String response = processAppData("http://www.howlettmedia.com/test/gctest.php", appdata);
		int test=0;
    	// attempt to decode and use the data that we have received
    	// to build the charts
    	try 
	    {
	    	// create the data object
			JSONObject jArrData = new JSONObject(response);
			test=1;
			// exctract the tabs object array
			JSONArray jArrTabs = jArrData.getJSONArray("tabs");
			test=2;
			debugString2=jArrTabs.toString();
			int entriesTwo=0;
			int entriesOne=0;
			for (int ctype=0; ctype<2; ctype++)
			{
				// extract game data
				JSONObject jArrGameData = jArrTabs.getJSONObject(ctype);
				JSONArray jArrRows = jArrGameData.getJSONArray("rows");
				
				if (ctype==0)
					entriesOne = jArrRows.length();
				else
					entriesTwo = jArrRows.length();
			}
			test=3;
			
			numberOfEntries=entriesTwo;
			if(entriesTwo>entriesOne)
				numberOfEntries=entriesOne;
			
			
			
			numberOfEntries=10;
			
			// create 2 tables, One first, then Two
			for (int ctype=0; ctype<2; ctype++)
			{
				// extract game data
				JSONObject jArrGameData = jArrTabs.getJSONObject(ctype);
				JSONArray jArrRows = jArrGameData.getJSONArray("rows");
				
				if (ctype==0)
					strMenuOne=jArrGameData.getString("name");
				else 
					strMenuTwo=jArrGameData.getString("name");
				
				charts[ctype].id = jArrGameData.getString("id");
				
				// loop through
				JSONObject jObjRow;
				
			
				//if (ctype==0)
				//	numberOfEntries = jArrRows.length();
				//else
				//	numberOfEntries = 2;
				
					
				
				// fill the local chart data arrays
			    for (int i=0; i<numberOfEntries; i++)
			    { 
			    	try
			    	{	
			    		
			    		jObjRow = jArrRows.getJSONObject(i);
			    		
			    		// get the image path
				    	try
				    	{
				    		//Bitmap testBmp=loadImage(jObjRow.getString("thumbnail_loc").toString(), iconW, iconH);
				    		
				    		//charts[ctype].images[i]=""+jObjRow.getString("thumbnail_loc").toString();	
				    		
				    		String img = jObjRow.getString("thumbnail_loc").toString();
				    		if (img.equalsIgnoreCase("game_thumb.jpg") || img.equalsIgnoreCase("game_thumb.png"))
				    		{
				    			img=GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE;
				    		}
				    		
				    		Log.d("image: "+i+" : ", img);
				    		
				    		
				    		
				    		charts[ctype].images[i]=img;//GamesChart.ERROR_IMAGE;
				    	}
				    	catch (Exception e)
				    	{
				    		charts[ctype].images[i]=GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE;
				    	}
				    	String ext = charts[ctype].images[i].substring(charts[ctype].images[i].length()-3, charts[ctype].images[i].length());
				    	debugString=ext;
				    	
				    	if (ext.equalsIgnoreCase("gif"))
				    	{
				    		charts[ctype].images[i]=GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE;	
				    	}

				    	// get the game title
				    	charts[ctype].titles[i]=""+jObjRow.getString("name").toString();
				    	
				    	
				    	
				    	
				    	
				    	
				    	/*try {
							//byte[] utf8 = charts[ctype].titles[i].getBytes("ISO-8859-1");
							charts[ctype].titles[i]=new String(charts[ctype].titles[i].getBytes("ISO-8859-1"), "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
				    	
				    	
				    	
				    	Log.d("Title: ", charts[ctype].titles[i]);
				    	
				    	charts[ctype].gameid[i]=""+jObjRow.getString("id").toString();
				    	
				    	// get the game description
				    	charts[ctype].descripions[i]=""+jObjRow.getString("primary_cat").toString();
				    	
				    	/*
				    	try {
							byte[] utf8 = charts[ctype].descripions[i].getBytes("ISO-8859-1");
							charts[ctype].descripions[i]=new String(utf8, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    	*/
				    	
				    	// get the game url
				    	try
				    	{
				    		charts[ctype].gameurls[i]=""+jObjRow.getString("game_link").toString();
				    	}	
				    	catch (Exception e)
				    	{
				    		charts[ctype].gameurls[i]=""+"http://www.gameschart.com";
				    	}
				    	
				    	
				    	// get the youtube url
				    	charts[ctype].videourls[i]="http://www.youtube.com/watch?v="+jObjRow.getString("video_url").toString();
				    	
				    	// create the thumbnails
				    	try
				    	{
				    		//if (ctype == 0)
				    		//	charts[ctype].thumbs[i] = loadImage(""+charts[ctype].images[i], iconW, iconH-2);
				    		//else
				    			charts[ctype].thumbs[i] = chartIcon;// loadImage(""+charts[ctype].images[i], iconW, iconH-2);
				    		//charts[ctype].thumbs[i] = errorIcon;
				    	}
				    	catch (Exception e)
				    	{
				    		charts[ctype].thumbs[i] = errorIcon;//loadImage(GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE, iconW, iconH);
				    	}
				    	// get the youtube icon
				    	charts[ctype].videothumbs[i] = youtubeIcon;
				    	//charts[ctype].videothumbs[i] = loadImage(GamesChart.getImageDirectory()+GamesChart.VIDEO_IMAGE,iconW>>1, iconH>>1);
				    	
				        
				        // get the arrow icon
				    	charts[ctype].playthumbs[i] = playIcon;
				    	//charts[ctype].playthumbs[i] = loadImage(GamesChart.getImageDirectory()+GamesChart.PLAY_IMAGE, iconW>>1, iconH>>1);
					    
			    	}
			    	catch (Exception e)
			    	{
				    	charts[ctype].images[i]=GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE;
				    	

				    	// get the game title
				    	charts[ctype].titles[i]="Your Game Here";
				    	
				    	
				    	charts[ctype].gameid[i]="0000"+i;
				    	
				    	// get the game description
				    	charts[ctype].descripions[i]="Click here to find out how..";
				    	
				    	charts[ctype].gameurls[i]=""+"http://test.gameschart.com/your-game.php";
				    	
				    	// get the youtube url
				    	charts[ctype].videourls[i]=null;
				    	
				    	//charts[ctype].thumbs[i] = loadImage(GamesChart.getImageDirectory()+GamesChart.GAMESCHART_ICON, iconW, iconH);
				    	charts[ctype].thumbs[i] = chartIcon;
				    	
				    	// get the youtube icon
				    	charts[ctype].videothumbs[i] = youtubeIcon;//loadImage(GamesChart.getImageDirectory()+GamesChart.VIDEO_IMAGE,iconW>>1, iconH>>1);
				    	//charts[ctype].videothumbs[i] = loadImage(GamesChart.getImageDirectory()+GamesChart.VIDEO_IMAGE,iconW>>1, iconH>>1);
				    	
				    	// get the arrow icon
				    	charts[ctype].playthumbs[i] = playIcon;//loadImage(GamesChart.getImageDirectory()+GamesChart.PLAY_IMAGE, iconW>>1, iconH>>1);
				    	//charts[ctype].playthumbs[i] = loadImage(GamesChart.getImageDirectory()+GamesChart.PLAY_IMAGE, iconW>>1, iconH>>1);
			    		
			    	}
			    	
			    	if (GamesChart.progress<300)
			    		GamesChart.progress+=20;
			    	GamesChart.updateDisplay();
			    	
			    }
			}
			test=4;
			loadedSkin = ""+jArrData.getString("skin");
			sessionID = ""+jArrData.getString("sessionID");
			
			chartSequence = 0;// get this from the data
			
			if (chartSequence == 1)
			{
				CHART_One=1;
				CHART_Two=0;
				strMenuOne=strTwoLong;
		    	strMenuTwo=strOneLong;
				activeChart = 1;
			}
			
			
			GamesChart.im.useAnimatedIcon(false);
			int closeIconSize=GamesChart.getTouchSizeSmallest();
			// get the arrow icon
			closeIcon = loadImage(GamesChart.getImageDirectory()+GamesChart.CLOSE_IMAGE, closeIconSize, closeIconSize);
	     	imgGCLinkIcon = loadImage(GamesChart.getImageDirectory()+GamesChart.GAMESCHART_IMAGE, GamesChart.getTouchSizeSmallest()*4, GamesChart.getTouchSizeSmallest());
			debugString2="OK AT " + test;
	    } 
	    catch (JSONException e) 
	    {
	    	// failed to create from data, load test data
	    	//loadTestData();
			// TODO Auto-generated catch block
	    	debugString2="OH BUGGER["+test+"]";
			e.printStackTrace();
			
		}
    	//debugString="LOADED DATA OK - SWEET!";
	    // set a theme
	    try // try and load one remotely
	    {
	    	if (loadedSkin.equalsIgnoreCase("DEFAULT"))
	    	{
	    		GamesChart.tm.setTheme(GamesChart.tm.THEME_DEFAULT);
	    	}
	    	else if (loadedSkin.equalsIgnoreCase("RED"))
	    	{
	    		GamesChart.tm.mainTheme.setSingleColorTheme(0xff440000);
	    	}
	    	else if (loadedSkin.equalsIgnoreCase("GREEN"))
	    	{
	    		GamesChart.tm.mainTheme.setSingleColorTheme(0xff004400);
	    	}
	    	else if (loadedSkin.equalsIgnoreCase("BLUE"))
	    	{
	    		GamesChart.tm.mainTheme.setSingleColorTheme(0xff000044);
	    	}
	    	//GamesChart.tm.mainTheme.setSingleColorTheme(0xff440000);
	    }
	    catch (Exception e)
	    {	
	    	debugString="FAILED 1";
	    }
		   
	    GamesChart.progress = progressMax;
    }
    
	public void loadThumbnails(int type)
	{
		for (int i=0; i<1; i++)
			loadThumbnail(type, i);
	}
	
	public void loadThumbnail(int type, int id)
	{
		try
		{
			charts[type].thumbs[id] = loadImage(""+charts[type].images[id], iconW, iconH-2);
		}
		catch (Exception e){}
	}
	
	
	
    private void loadTestData()
    {
		for (int ctype=0; ctype<2; ctype++)
		{

			charts[ctype].id = "ID"+ctype;

			// fill the local chart data arrays
		    for (int i=0; i<numberOfEntries; i++)
		    { 
		    	// get the image path
		    	charts[ctype].images[i]="";

		    	// get the game title
		    	charts[ctype].titles[i]="TITLE " + i;
		    	
		    	charts[ctype].gameid[i]="GAME " + i;
		    	
		    	// get the game description
		    	charts[ctype].descripions[i]=" DESCRIPTION " + i;
		    	
		    	// get the game url
		    	charts[ctype].gameurls[i]="http://www.gameschart.com";
		    	
		    	// get the youtube url
		    	charts[ctype].videourls[i]="http://www.youtube.com/watch?v=y9cxrSp7TQ8";
		    	
		    	// create the thumbnails
		    	//charts[ctype].thumbs[i] = errorIcon;
		    	charts[ctype].thumbs[i] = loadImage(GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE, iconW, iconH-2);
		    	
		    	// get the youtube icon
		    	charts[ctype].videothumbs[i] = youtubeIcon;
		    	//charts[ctype].videothumbs[i] = loadImage(GamesChart.getImageDirectory()+GamesChart.VIDEO_IMAGE,iconW>>1, iconH>>1);
		    	
		    	// get the arrow icon
		    	charts[ctype].playthumbs[i] = playIcon;
		    	//charts[ctype].playthumbs[i] = loadImage(GamesChart.getImageDirectory()+GamesChart.PLAY_IMAGE, iconW>>1, iconH>>1);
				   
		    }
		}
		loadedSkin ="DEFAULT";
		sessionID = "0000000000000000";
		GamesChart.im.useAnimatedIcon(false);
		
		// get the arrow icon
		closeIcon = loadImage(GamesChart.getImageDirectory()+GamesChart.CLOSE_IMAGE, GamesChart.getTouchSizeSmall(), GamesChart.getTouchSizeSmall());
    }
    
    
    // Send click event to server
    private String registerGameClick(String gameid, String rank)
    {
		//////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////
		// click data creation
		//////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////
    	String strTarget="onGameClicked";
    	String appdata="";
    	JSONObject 	jobj_data = new JSONObject();
    	JSONObject	jobj_payload = new JSONObject();
    	//gameID = "aa8c48f436ff422466b8f3eb2c4cdaaa";
		// attempt to build ths request data
		try 
		{
			jobj_payload.put("gid", gameid);
			jobj_payload.put("sid", sessionID);
			jobj_payload.put("chronKey", chronKey);
			jobj_payload.put("gameRank", rank);
			jobj_payload.put("chartID", charts[activeChart].id);
			
			// build the data object
			jobj_data.put("target", strTarget);
			jobj_data.put("payload", jobj_payload);
			
			// turn it into an encoded string
			//appdata=jobj_data.toString();
			appdata=Rot13.rot13(Base64.encodeToString(jobj_data.toString().getBytes(), Base64.DEFAULT));
		} 
		catch (JSONException e1) 
		{
			e1.printStackTrace(); // oops!
		}
		//String response = processAppData("http://www.howlettmedia.com/test/gctest.php", appdata);
		String response = processAppData(GamesChart.GCURL, appdata);
		
		//DEBUG=true;
		Log.d("RESPONSE", response);
		
		return response;
		
    }
   
    private String processAppData(String url, String data)
    {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost; 

		httppost = new HttpPost(url);
	
		try
		{
		    // Add your data
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		 
		    // Add two POST request variables
		    // nameValuePairs.add(new BasicNameValuePair("GCAPIKey", "GCKEY"));
		    nameValuePairs.add(new BasicNameValuePair("data", data));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		 
		    // Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost);
		    HttpEntity entity = response.getEntity();
		 
		    // Get the return string (JSON data)
		    String temp = EntityUtils.toString(entity);
		    return temp;
		    
		} 
		// couple of catches
		catch (ClientProtocolException e) { return "FAIL 1";} 
		catch (IOException e) {return "FAIL 2";}
    }
    
    private boolean registerClick(int tstamp)
    {
    	clicksThisSession++;
    	
    	// test - always allow
    	return true;
    }
   
    
    public boolean showTab()
    {
    	if (bShowTab)
    		return true;
    	
    	return false;
    }
    
    public int getActionIcon(int x, int y)
    {
    	// check this first
    	if (GamesChart.im.rectIcon.contains(x, y))
    	{
    		if (bShowTab)
    			return GamesChart.CLICKED_ICON;
    		else
    			return GamesChart.CLICKED_NOTHING;
    	}
    	return GamesChart.CLICKED_NOTHING;
    }
    
    public void swapCharts()
    {
    	if (activeChart==CHART_Two)
    		activeChart=CHART_One;
    	else
    		activeChart=CHART_Two;
    	
    	// force a redraw

		buildChartLayout(numberOfEntries); // do this for both Canvas and OpenGL versions
		
		// make sure any animations are ready to go
		resetAnimations();
    }
    
  
    public int getActionChart(int x, int y)
    {
    	// then the rest
    	if (rectCloseBox.contains(x, y))
    	{
    		resetAnimations();
    		return GamesChart.CLICKED_EXIT;
    	}
    	else if (rectGCLink.contains(x, y))
    	{
    		uri_gclink=Uri.parse("http://www.gameschart.com");
    		return GamesChart.CLICKED_GCLINK;
    	}
    	else if (rectTwoChart.contains(x, y))
    	{  
    	    activeChart=CHART_Two;
    	    resetAnimations();
    		return GamesChart.CLICKED_Two;
    	}
    	else if (rectOneGames.contains(x, y))
    	{
    		activeChart=CHART_One;
    		resetAnimations();
    		return GamesChart.CLICKED_One;
    	}
    	else
    	{
	    	for (int i=0; i<numberOfEntries; i++)
			{
	    		if (rectVideo[i].contains(x, y))
	    		{	
	    			
	    			try
	    			{
	    				uri_video=Uri.parse(charts[activeChart].videourls[i]);
	    				
	    			}
	    			catch (Exception e)
	    			{
	    				//return GamesChart.CLICKED_NOTHING;
	    				uri_video=Uri.parse("http://www.youtube.com/watch?v=LXuJW-pCXks");
	    			}
	    			
	    			return GamesChart.CLICKED_VIDEOLINK;
	    			//if (activeChart==CHART_One)
	    				
	    			//else
	    				
	    		}
	    		else if (rectThumbs[i].contains(x, y) || rectPlayGame[i].contains(x, y) || rectDescriptions[i].contains(x, y))
	    		{
	    			if (GamesChart.chartDisplay==GamesChart.SHOW_CHART)
	    			{
		    			if (GamesChart.bAllowRegClick)
		    			{
		    				GamesChart.bAllowRegClick=false;
		    				
		    				resp=registerGameClick(charts[activeChart].gameid[i], ""+(i+1));
		    			}
		    			if (resp.equalsIgnoreCase("OK"))
		    			{
		    				try
		    				{
		    					uri_game=Uri.parse(charts[activeChart].gameurls[i]);
			    			}
			    			catch (Exception e)
			    			{
			    				uri_game=Uri.parse("http://www.gameschart.com");
			    			}
		    				Log.d("GAME URL: ", ""+uri_game);
		    			}
	    			}
	    			return GamesChart.CLICKED_THUMB;
	    		}
			}
    	}
    	return GamesChart.CLICKED_NOTHING;
    }
    
    
    private void buildChartLayout(int numberofentries)
    {
    	
    	GamesChart.updateDisplay();
    	
    	screenW=GamesChart.displayWidth;
		screenH=GamesChart.displayHeight;
		iconW=GamesChart.getTouchSize(); // needs to be static finger-size
		iconH=GamesChart.getTouchSize(); // needs to be static finger-size
		chartYOffset=0;
		chartYTarget=0;
		if (GamesChart.shortestSize>240)
		{
			if(screenH>screenW)
	    	{ 
	    		// portrait
	    		screenUnit=screenW>>5; 
	    	}
	    	else
	    	{ 
	    		// landscape
	    		screenUnit=screenH>>5;	
	    	}
		}
		else
		{
			screenUnit=8;
		}
		chartOffset=8;//GamesChart.im.gcIconPadding+(GamesChart.im.gcIconW>>1);
		
		Log.d("BUILD: SCREEN W: ", "" + screenW );
		Log.d("BUILD: SCREEN H: ", "" + screenH );
		videoIconW=iconW;
    	videoIconH=iconW;
    	
    	playIconW=iconW;
    	playIconH=iconW;
     	
    	
    	if (GamesChart.shortestSize>240)
    	{
    		closeBoxSize=GamesChart.getTouchSizeSmall();
    		navBarHeight=closeBoxSize-(closeBoxSize>>3);
    	}
    	else
    	{
    		closeBoxSize=GamesChart.getTouchSizeSmall();
    		navBarHeight=closeBoxSize-(closeBoxSize>>3);
    	}
    	// padding
    	padding=screenUnit>>2;
    	
    	// text slot
    	slotTextW=iconW*2;
     	slotTextH=screenUnit*4;
     	
     	titletW=iconW*4;
     	titleH=iconH*4;

    	chartW=screenW-(chartOffset*2);
    	if (GamesChart.shortestSize>240)
    	{
    		chartH=((navBarHeight*2)+((iconH+padding)*GamesChart.getEntries()))+(padding*2);
    	}
    	else
    	{
    		chartH=((navBarHeight*2)+((iconH+padding)*GamesChart.getEntries()))+(padding);
    	}
				
    	
     	Log.d("BUILD W: ", ""+chartW);
     	Log.d("BUILD H: ", ""+chartH);
     	

     	chartX=chartOffset; 	
     	chartY=chartOffset;
     	
     	
     	chartY=navBarHeight;//(screenH/2)-(chartH/2);
     	
     	//chartY=((screenH>>1)-(chartH>>1));
     	
     	navBarHeight=closeBoxSize;
     	
     	sectionWidth=screenW/3;

     	/*if (screenW > screenH)
    	{
    		while (chartH > (screenH-(navBarHeight*2)))
    		{
    			numberOfEntries--; // drop one until it fits nicely
    			chartH=((navBarHeight*2)+((iconH+padding)*numberOfEntries))+(padding*2);
    		}
    		
    	}*/
     	debugString="create rects";
     	
        // create the Rects
     	xoffsets = new int[numberofentries];
    	rectThumbs = new Rect[numberofentries];
     	rectTitles = new Rect[numberofentries];
     	rectDescriptions = new Rect[numberofentries];
     	rectVideo = new Rect[numberofentries];
     	rectPlayGame = new Rect[numberofentries];
     	
     	
     	rectNavBar = new Rect(chartX, chartY, (chartX+chartW)-closeBoxSize, chartY+navBarHeight);
     	rectCloseBox = new Rect(rectNavBar.right, rectNavBar.top, rectNavBar.right+closeBoxSize, rectNavBar.top+closeBoxSize);
     	
     	
     	rectOneGames = new Rect(rectNavBar.left+(padding*2), rectNavBar.top, rectNavBar.left+sectionWidth, chartY+navBarHeight);
     	rectTwoChart = new Rect(rectOneGames.right, rectNavBar.top, rectOneGames.right+sectionWidth, chartY+navBarHeight);
     	debugString="create rects ok";
     	
     	debugString="create charts";
     	y=chartY+navBarHeight;

		for (int i=0; i<numberOfEntries; i++)
		{
			xoffsets[i]=-screenW;
	     	rectThumbs[i] = new Rect(chartX+(iconW>>1), y, (chartX+(iconW>>1))+iconW, y+iconH);
	     	rectTitles[i] = new Rect(rectThumbs[i].right+(padding*2), rectThumbs[i].top, rectThumbs[i].right+padding+titletW, rectThumbs[i].bottom);
	     	rectDescriptions[i] = new Rect(rectThumbs[i].right+padding, rectThumbs[i].top, rectThumbs[i].right+padding+slotTextW, rectThumbs[i].bottom);

	     	rectPlayGame[i] = new Rect((chartX+chartW)-(iconW), rectDescriptions[i].top, (chartX+chartW), rectDescriptions[i].top+iconH);
	     	
	     	rectVideo[i] = new Rect(rectPlayGame[i].left-iconW, rectDescriptions[i].top, rectPlayGame[i].left, rectDescriptions[i].top+iconH);
	     	
	     	y+=iconH+padding;
		}
		debugString="create charts ok";
		//rectGCLink=new Rect(0, 0, 64, 64);//(chartX+chartW)-(imgGCLinkIcon.getWidth()+padding), (chartY+chartH)-(imgGCLinkIcon.getHeight()+padding), ((chartX+chartW)-(imgGCLinkIcon.getWidth()+padding))+imgGCLinkIcon.getWidth(), ((chartY+chartH)-(imgGCLinkIcon.getHeight()+padding))+imgGCLinkIcon.getHeight());
		
		int icw=GamesChart.getTouchSizeSmallest()*4;
		int ich=GamesChart.getTouchSizeSmallest();
		
		
		rectGCLink=new Rect((chartX+chartW)-(icw+(padding*2)), (chartY+chartH)-(ich+padding), ((chartX+chartW)-(icw+padding))+icw, ((chartY+chartH)-(ich+padding))+ich);

		scrollDown=new Rect(chartX, (chartY+chartH)-(GamesChart.getTouchSizeSmallest()+padding), chartX+GamesChart.getTouchSizeSmallest(), (chartY+chartH)-(GamesChart.getTouchSizeSmallest()+padding)+GamesChart.getTouchSizeSmallest());
	 	scrollUp=new Rect(scrollDown.right, scrollDown.top, scrollDown.right+GamesChart.getTouchSizeSmallest(),scrollDown.bottom);
	 	
	 	playIcon = loadImage(GamesChart.getImageDirectory()+GamesChart.PLAY_IMAGE, iconW>>1, iconH>>1);
    	youtubeIcon = loadImage(GamesChart.getImageDirectory()+GamesChart.VIDEO_IMAGE,iconW>>1, iconH>>1);
    	//errorIcon = loadImage(GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE, iconW, iconH);
    	chartIcon = loadImage(GamesChart.getImageDirectory()+GamesChart.GAMESCHART_ICON, iconW, iconH);
    }
    
    
    int loadTicker1=0;
    int thumbsLoaded1=0;
    int loadTicker2=0;
    int thumbsLoaded2=0;
    public void update()
    {
    	screenOrientation=GamesChart.displayOrientation;
    	screenW=GamesChart.displayWidth;
     	screenH=GamesChart.displayHeight;
     	
    	if(screenOrientation!=oldScreenOrientation)
    	{
    		Log.d("REBUILD:", "ORIENTATION");
    		// rebuild layout
    		// Canvas Version
         	screenBmp = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);
        	screenCanvas = new Canvas(screenBmp);
         	
        	buildChartLayout(numberOfEntries);
    		oldScreenOrientation=screenOrientation;
    	}
    	// check for wrong orientation
    	//if(screenW>screenH && chartH >= screenH)
    	//{
    		//GamesChart.updateDisplay();
    		//buildChartLayout(numberOfEntries);
    	//}
    	
    	if (readyToDraw) 
    	{
    		
    		if (thumbsLoaded1<10)
    		{
	    		loadTicker1++;
	    		if (loadTicker1 > 5)
	    		{
	    			loadThumbnail(0, thumbsLoaded1);
	    			thumbsLoaded1++;
	    			loadTicker1=0;
	    		}
    		}
    		else
    		{
	    		if (thumbsLoaded2<10)
	    		{
		    		loadTicker2++;
		    		if (loadTicker2 > 5)
		    		{
		    			loadThumbnail(1, thumbsLoaded2);
		    			thumbsLoaded2++;
		    			loadTicker2=0;
		    		}
	    		}
    		}
    		
    		//loadThumbnails(0);
    	}
    }
    
    public void drawDebug(Canvas c, Paint p)
	{
    	Rect debugrect = new Rect(20, 20, screenW-20, screenH-80);
    	
    	// draw the screen fade
		c.drawARGB(200, 255, 255, 255);
		
		// draw the rotated text
		p.setAntiAlias(true);
		p.setColor(Color.BLACK);
	
		p.setTextSize(10);
		p.setStyle(Paint.Style.FILL);

    	drawTextInRect( c, p, debugrect, ""+this.debugString);
		
	}
    
    public void drawDebugSmall(Canvas c, Paint p)
   	{
       	Rect debugrect = new Rect(20, 20, screenW-20, screenH-20);
       	
       	// draw the screen fade
   		c.drawARGB(200, 255, 255, 255);
   		
   		// draw the rotated text
   		p.setAntiAlias(true);
   		p.setColor(Color.BLACK);
      	p.setTextSize(10);

       	drawTextInRect( c, p, debugrect, ""+this.debugString + 	"|" + debugString2 + ":" + GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementMain]);
   	}
    
    
    private static boolean DEBUG=false;
    //private static boolean DEBUG=true;
    
	@SuppressWarnings("static-access")
	
	
	public void draw()
	{
		//Canvas 
	}
	
	public static int alphafade=0;
	
	public void draw(Canvas cp, Paint p)
	{
		if (readyToDraw)
		{
			if(GamesChart.bConnectionAvailable) // connection
	    	{
				drawConnected(cp, p);
	    	}
			else
			{
				drawLocal(cp, p);
			}
		}
		else
		{
			p.setColor(Color.WHITE);
			cp.drawRect(0, 0,screenW, screenH, p);
			p.setColor(Color.BLACK);
			p.setTextSize(12);
			p.setTypeface(Typeface.DEFAULT_BOLD);
			cp.drawText("CONNECT TO THE INTERNET", 12, 24, p);	
			cp.drawText("DEBUG: "+debugString+"w:"+ screenW, 12, 44, p);	
		}
	}
	
	public void drawLocal(Canvas c, Paint p)
	{
		// basc screeb dimensions
    	screenW=GamesChart.displayWidth;
		screenH=GamesChart.displayHeight;

		p.setColor(Color.WHITE);
		c.drawRect(0, 0,screenW, screenH, p);
		p.setColor(Color.BLACK);
		p.setTextSize(12);
		p.setTypeface(Typeface.DEFAULT_BOLD);
		c.drawText("CONNECT TO THE INTERNET", 12, 24, p);	
		c.drawText("DEBUG: "+debugString+"w:"+ screenW, 12, 44, p);
	}
		
	public int getScrollZoneMin()
	{
		return rectNavBar.bottom;
	}
	public int getScrollZoneMax()
	{
		return scrollDown.top;
	}
	
	String s = "No title";
	String d = "No description";
	int playx = 0;
	float midy=0;
	public void drawConnected(Canvas cp, Paint p)
	{	
		Canvas c = screenCanvas;
		if (DEBUG)
		{
			drawDebug(c, p);
		}
		else
		{
			try
			{
				updateScroll();
				if (bAnimations)
				{
					updateAnimations();
				}
				
				if (alphafade<8)alphafade+=2;
				// draw the screen fade
				c.drawARGB(alphafade, 255, 255, 255);
		
				// draw the rotated text
				p.setTextSize(10);
				p.setStyle(Paint.Style.FILL);
				
				// draw chart background
				p.setAntiAlias(true);
				c.drawARGB(alphafade, 120, 120, 120);

				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementMain]);
				c.drawRect(chartX, chartY, chartX+chartW, chartY+chartH, p);
				midy=rectOneGames.centerY()+(p.getTextSize()/2);
				
				// One Games / Two Chart
				for (int i=0; i<10;/*GamesChart.getEntries();*/ i++)
				{
					
					if (rectThumbs[i].bottom+chartYOffset > rectOneGames.bottom+(padding*4) &&
						rectThumbs[i].bottom+chartYOffset < scrollUp.bottom)
					{
						//if (i%2==1)
						//{
							int tint=0;
							for (int line=0; line<(rectThumbs[i].bottom-rectThumbs[i].top); line++)
							{
								
								if (line%2==0)
									tint++;
								int mingrey=255-tint;
								if (mingrey<220)mingrey=220;
								p.setARGB(255, mingrey, mingrey, mingrey);
								c.drawRect((chartX+xoffsets[i]), rectThumbs[i].top+1+chartYOffset+line, chartX+chartW, rectThumbs[i].top+1+chartYOffset+line+1, p);
								//p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowColor1]);	
							}
							
							//p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowColor1]);
							
						//}
						//else
						//{
						//	p.setColor(Color.WHITE);//GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowColor1]);
						//	c.drawRect((chartX+xoffsets[i]), rectThumbs[i].top+1+chartYOffset, (chartX+xoffsets[i])+chartW, rectThumbs[i].bottom+chartYOffset, p);
						//}
						int tabx=0;//rectThumbs[i].left-(padding+iconW);
						int taby=rectThumbs[i].top+2;
						
						// draw the chart position tab
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowTab]);
						c.drawRect(tabx+xoffsets[i], taby+chartYOffset, (rectThumbs[i].left-(padding))+xoffsets[i], rectThumbs[i].bottom+chartYOffset, p);
	
						if (rectThumbs[i].bottom+chartYOffset < scrollUp.top)
						{
							// draw the position number
							p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowTabText]);
							p.setTextSize(getPositionFontSize());
							p.setTypeface(Typeface.DEFAULT_BOLD);
							midy=rectThumbs[i].centerY()+(p.getTextSize()/2);
							c.drawText(""+(i+1), padding+xoffsets[i], midy+chartYOffset-2, p);
						}
						// draw the icon
						if (charts[activeChart].thumbs[i]!=null)
							c.drawBitmap(charts[activeChart].thumbs[i], rectThumbs[i].left+xoffsets[i], (rectThumbs[i].top+chartYOffset)+2, p);
						
						
						playx = chartW-(charts[activeChart].playthumbs[i].getWidth()+(padding*2))+xoffsets[i];
						
						if(charts[activeChart].playthumbs[i]!=null)
							c.drawBitmap(charts[activeChart].playthumbs[i], playx, (rectPlayGame[i].centerY()-(charts[activeChart].playthumbs[i].getHeight()>>1))+chartYOffset, p);
						
						//Log.d("VIDEO URL: ", ""+charts[activeChart].videourls[i]);
						//if (activeChart == CHART_One/* && screenW>240*/)
						//{
							if(charts[activeChart].videothumbs[i]!=null && charts[activeChart].videourls[i]!=null && charts[activeChart].videourls[i].length() > 32)
								c.drawBitmap(charts[activeChart].videothumbs[i], playx-((rectVideo[i].width()>>1)+(padding*2)), (rectVideo[i].centerY()-(charts[activeChart].videothumbs[i].getHeight()>>1))+chartYOffset, p);
						//}
						
						// draw the description
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowGameTitleText]);
						p.setTextSize(getTitleFontSize());
						p.setTypeface(Typeface.DEFAULT_BOLD);
						midy=rectTitles[i].centerY()+(p.getTextSize()/2);
						
						float textoffsety=(p.getTextSize()/2)+2;
						
						try{s = charts[activeChart].titles[i]+" ";}
						catch (Exception e){}
						
						//Log.d("TITLE SORTED: ", ""+GamesChart.getMaxTitleChars());
						
						if (s.toString().length()>GamesChart.getMaxTitleChars())
							s=s.substring(0,GamesChart.getMaxTitleChars())+"...";
						
						c.drawText(s, rectTitles[i].left+xoffsets[i], midy-textoffsety+chartYOffset, p);

						p.setTypeface(Typeface.DEFAULT);
						p.setTextSize(getDescrptionFontSize());
						s = "No description";
						d = "";
						try{s=charts[activeChart].descripions[i]+" ";}
						catch (Exception e){}
						
						if(s.toString().length()>GamesChart.getMaxDecriptionChars())
							s=charts[activeChart].descripions[i].substring(0,GamesChart.getMaxDecriptionChars())+"...";
						

						c.drawText(s, rectTitles[i].left+xoffsets[i], midy+textoffsety+chartYOffset, p);
						
						
						
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementOutline]);
						//c.drawRect((chartX+xoffsets[i]), rectThumbs[i].top+chartYOffset, (chartX+xoffsets[i])+chartW, rectThumbs[i].top+1+chartYOffset, p);
						c.drawRect((chartX+xoffsets[i]), rectThumbs[i].bottom+chartYOffset, (chartX+xoffsets[i])+chartW, rectThumbs[i].bottom+1+chartYOffset, p);
					}
				}
				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementMain]);
				c.drawRect(chartX, chartY, chartX+chartW, chartY+rectNavBar.height(), p);
				
				

				// draw the nav bar
				c.drawRect(rectNavBar.left, rectNavBar.top, rectNavBar.right, rectNavBar.bottom, p);
				
				if (closeIcon!=null)
					c.drawBitmap(closeIcon, rectCloseBox.centerX()-(closeIcon.getWidth()>>1), rectCloseBox.centerY()-(closeIcon.getHeight()>>1), p);
				
				// the top left red bar
				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabLeftBar]);
				c.drawRect(rectOneGames.left-(padding*2), rectOneGames.top+(padding*2), rectOneGames.left, rectOneGames.bottom-(padding*2), p);
				p.setAntiAlias(true);
				p.setTextSize(getNavFontSize());
				p.setTypeface(Typeface.DEFAULT_BOLD);
				
				midy=rectOneGames.centerY()+(p.getTextSize()/2);
				
				if (activeChart == CHART_One)
				{
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementOutline]);
					c.drawRect(rectOneGames.left-1, rectOneGames.top+(padding*2)-1, rectOneGames.right+1, rectOneGames.bottom-(padding*2), p);
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabSelected]);
					c.drawRect(rectOneGames.left, rectOneGames.top+(padding*2), rectOneGames.right, rectOneGames.bottom-(padding*2), p);
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabSelectedText]);
					c.drawText(strMenuOne, rectOneGames.left+(padding*3), midy, p);
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabUnselected]);
					c.drawRect(rectTwoChart.left, rectTwoChart.top+(padding*2), rectTwoChart.right, rectTwoChart.bottom-(padding*2), p); 
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabUnselectedText]);
					c.drawText(strMenuTwo, rectTwoChart.left+(padding*3), midy, p);
				}
				else if (activeChart == CHART_Two)
				{
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabUnselected]);
					c.drawRect(rectOneGames.left, rectOneGames.top+(padding*2), rectOneGames.right, rectOneGames.bottom-(padding*2), p);
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabUnselectedText]);
					c.drawText(strMenuOne, rectOneGames.left+(padding*3), midy, p);
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementOutline]);
					c.drawRect(rectTwoChart.left-1, rectTwoChart.top+(padding*2)-1, rectTwoChart.right+1, rectTwoChart.bottom-(padding*2), p);
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabSelected]);
					c.drawRect(rectTwoChart.left, rectTwoChart.top+(padding*2), rectTwoChart.right, rectTwoChart.bottom-(padding*2), p);
					p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabSelectedText]);
					c.drawText(strMenuTwo, rectTwoChart.left+(padding*3), midy, p);	
				}
					
				/*switch (activeChart)
				{
					case CHART_One:
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementOutline]);
						c.drawRect(rectOneGames.left-1, rectOneGames.top+(padding*2)-1, rectOneGames.right+1, rectOneGames.bottom-(padding*2), p);
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabSelected]);
						c.drawRect(rectOneGames.left, rectOneGames.top+(padding*2), rectOneGames.right, rectOneGames.bottom-(padding*2), p);
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabSelectedText]);
						c.drawText(strMenuOne, rectOneGames.left+(padding*3), midy, p);
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabUnselected]);
						c.drawRect(rectTwoChart.left, rectTwoChart.top+(padding*2), rectTwoChart.right, rectTwoChart.bottom-(padding*2), p); 
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabUnselectedText]);
						c.drawText(strMenuTwo, rectTwoChart.left+(padding*3), midy, p);
						break;
					case CHART_Two:
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabUnselected]);
						c.drawRect(rectOneGames.left, rectOneGames.top+(padding*2), rectOneGames.right, rectOneGames.bottom-(padding*2), p);
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabUnselectedText]);
						c.drawText(strMenuOne, rectOneGames.left+(padding*3), midy, p);
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementOutline]);
						c.drawRect(rectTwoChart.left-1, rectTwoChart.top+(padding*2)-1, rectTwoChart.right+1, rectTwoChart.bottom-(padding*2), p);
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabSelected]);
						c.drawRect(rectTwoChart.left, rectTwoChart.top+(padding*2), rectTwoChart.right, rectTwoChart.bottom-(padding*2), p);
						p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementTabSelectedText]);
						c.drawText(strMenuTwo, rectTwoChart.left+(padding*3), midy, p);	
						break;
					case CHART_TYPE2:
						break;
					case CHART_TYPE3:
						break;
					case CHART_TYPE4:
						break;
				}*/
				p.setAntiAlias(true);
				
				
				p.setTypeface(Typeface.DEFAULT);
				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowGameTitleText]);
				p.setTextSize(10);

				
				
				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementMain]);
				c.drawRect(scrollDown.left, scrollDown.top-padding, scrollDown.left+chartW, scrollDown.bottom+padding*2, p);
				
				// draw the cursors
				
				/*midy=scrollDown.centerY()+(p.getTextSize()/2);
				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowColor1]);
				c.drawRect(scrollDown.left, scrollDown.top, scrollDown.right, scrollDown.bottom, p);
				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowGameTitleText]);
				c.drawText("down", scrollDown.left+padding, midy, p);	
				
				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowColor2]);
				c.drawRect(scrollUp.left, scrollUp.top, scrollUp.right, scrollUp.bottom, p);
				p.setColor(GamesChart.tm.mainTheme.themeColors[GamesChart.tm.mainTheme.elementRowGameTitleText]);
				c.drawText("up", scrollUp.left+padding, midy, p);*/
				
				// draw the icon
				if (imgGCLinkIcon!=null)
					c.drawBitmap(imgGCLinkIcon, rectGCLink.left, rectGCLink.top, p);
				
				debugString2="OK ";
			}
			catch (Exception e){debugString="Failed to draw : ";}
		}
		//drawDebugSmall(c, p);
		
		
		//p.setColor(Color.WHITE);
		//c.drawRect(0, 0, 240, 64, p);
		//p.setColor(Color.BLACK);
		//c.drawText("SCROLLY: " + GamesChart.currentTouchY,  2, 14, p);
		//c.drawText("OLD TOUCH: " + GamesChart.oldTouchY,  2, 28, p);
		//c.drawText("DIFF: " + GamesChart.diff,  2, 42, p);
		
		//c.drawText("SW: "+screenW + " SH: " + screenH, 2, 14, p);
		//c.drawText("CW: "+chartW + " CH: " + chartH, 2, 28, p);
		//c.drawText("FY: "+GamesChart.flingY, 2, 42, p);
		cp.drawBitmap(screenBmp, 0, 0, p);
	}
	
	
	
	public static InputStream fromString(String str) throws UnsupportedEncodingException
	{
		byte[] bytes = str.getBytes("UTF-8");
		return new ByteArrayInputStream(bytes);
	}
	
	
	public void showTab(boolean show)
	{
		bShowTab=show;
	}

	private void resetAnimations()
	{
		for (int i=0; i<numberOfEntries; i++)
		{
			if(!bAnimations)
			{
				xoffsets[i]=0;
			}
			else
			{
				xoffsets[i]=-screenW;
			}
		}
		alphafade=0;
	}
	
	public void setAnimation(boolean anims)
	{
		bAnimations=anims;
		resetAnimations();
	}
	
	private static final int SCROLL_NONE=0;
	private static final int SCROLL_UP=1;
	private static final int SCROLL_DOWN=2;
	
	private static String dirs [] = {"NONE", "UP", "DOWN"};
	
	private int scrollDir=SCROLL_NONE;
	
	
	public void scrollUpABit(int amount)
	{
		if (scrollDir==SCROLL_NONE && chartYOffset<0)
		{
			scrollDir=SCROLL_UP;
			chartYTarget+=amount;
		}
	}
	public void scrollDownABit(int amount)
	{
		if (scrollDir==SCROLL_NONE && chartYOffset<0)
		{
			scrollDir=SCROLL_DOWN;
			chartYTarget-=amount;
		}
	}
	
	public void scrollUp(int positions)
	{
		if (!slidingIn())
		{
			if (scrollDir==SCROLL_NONE && chartYOffset<0)
			{
				scrollDir=SCROLL_UP;
				chartYTarget+=positions*(iconH+padding);
			}
		}
	}
	
	
	
	public void scrollDown(int positions)
	{
		// don't scroll if maximum entries displayed (really only applies to tablets)
		if (GamesChart.getEntries()>=numberOfEntries)
			return;
			
		int moveby = (positions*(iconH+padding));
		int maxmoveby = ((numberOfEntries-2)*(iconH+padding));
	
		if (!slidingIn())
		{
			if (GamesChart.getEntries() < numberOfEntries/2)
			{
				if (scrollDir==SCROLL_NONE && chartYOffset>-maxmoveby)
				{
					scrollDir=SCROLL_DOWN;
					chartYTarget-=moveby;
				}
			}
			else
			{
				if (scrollDir==SCROLL_NONE && chartYOffset>-moveby)
				{
					scrollDir=SCROLL_DOWN;
					chartYTarget-=moveby;
				}
			}
		}	
		
		/*if (scrollDir==SCROLL_NONE && chartYOffset >= -(numberOfEntries-(GamesChart.getEntries()+1))*moveby)
		{
			scrollDir=SCROLL_DOWN;
			
			//if (chartYTarget-(positions*(iconH+padding)) >= -(GamesChart.getEntries()*(iconH+padding)))
			{
				chartYTarget-=moveby;	
			}
		}*/
	}
	
	
	private void updateScroll()
	{

		// don't scroll if maximum entries displayed (really only applies to tablets)
		if (GamesChart.getEntries()>=numberOfEntries)
			return;
		int speed=1;
		
		switch (scrollDir)
		{
			case SCROLL_DOWN:
				if (chartYOffset>chartYTarget) // need to scroll up
				{
					speed=Math.abs(chartYOffset-chartYTarget)>>1;
					if (speed<1)
						speed=1;
					chartYOffset-=speed;
					
					if (chartYOffset<=chartYTarget)
					{
						chartYOffset=chartYTarget;
						scrollDir=SCROLL_NONE;
					}
				}
				break;
			case SCROLL_UP:
				if (chartYOffset<chartYTarget)
				{
					speed=Math.abs(chartYTarget-chartYOffset)>>1;
					if (speed<1)
						speed=1;
					chartYOffset+=speed;
					if (chartYOffset>=chartYTarget)
					{
						chartYOffset=chartYTarget;
						scrollDir=SCROLL_NONE;
					}
				}
				break;
		}
	}
	
	private boolean slidingIn()
	{
		for (int i=0; i<numberOfEntries; i++)
		{
			if (xoffsets[i]<0)
			{
				return true;
			}
		}
		return false;
	}	
	
	private void updateAnimations()
	{
		for (int i=0; i<numberOfEntries; i++)
		{
			int speed=Math.abs(xoffsets[i]>>1);
			speed+=(speed>>1);
			if (xoffsets[i]<0)
			{
				if (i==0)
				{
					speed=Math.abs(xoffsets[i]>>1);
					xoffsets[i]+=speed;
				}
				else
				{
					if (xoffsets[i-1]>-(screenW>>1))
					{
						speed=Math.abs(xoffsets[i]>>1);
						xoffsets[i]+=speed;
					}
				}
			}
			
			if (xoffsets[i]>0)
			{
				xoffsets[i]=0;
			}
		}
	}
	
	
	public Bitmap loadImage(String path, int width, int height)
	{
		return resizeBitMap(GamesChart.dm.getBitmap(path), width, height);
	}
	
	public Bitmap loadImages(String path, int width, int height)
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
			return b;
 	    } 
 	    catch (IOException e) 
 	    { 
 	    	e.printStackTrace(); 
 	    } 
 	    return null;
 	     
 	}
 	
 	public Bitmap resizeBitMap(Bitmap bitmapIn, int neww, int newh)
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
 	
 	private static int getPositionFontSize()
 	{
 		int size=28;
 		switch (GamesChart.getDeviceClass())
 		{
 			case GamesChart.DEVICE_SIZE_SMALLEST:
 				size=16;
 				break;
 			case GamesChart.DEVICE_SIZE_SMALL:
 				size=18;
 	 			break;
 			case GamesChart.DEVICE_SIZE_MEDIUM:
 				size=32;
 	 			break;
 			case GamesChart.DEVICE_SIZE_LARGE:
 				size=36;
 	 			break;
 			case GamesChart.DEVICE_SIZE_LARGEST:
 				size=32;
 	 			break;
 	 	}
 		return size;
 	}
 	
 	private static int getNavFontSize()
 	{
 		int size=14;
 		switch (GamesChart.getDeviceClass())
 		{
 			case GamesChart.DEVICE_SIZE_SMALLEST:
 				size=10;
 				break;
 			case GamesChart.DEVICE_SIZE_SMALL:
 				size=14;
 	 			break;
 			case GamesChart.DEVICE_SIZE_MEDIUM:
 				size=16;
 	 			break;
 			case GamesChart.DEVICE_SIZE_LARGE:
 				size=18;
 	 			break;
 			case GamesChart.DEVICE_SIZE_LARGEST:
 				size=20;
 	 			break;
 	 	}
 		return size;
 	}
 	
 	private static int getTitleFontSize()
 	{
 		int size=14;
 		switch (GamesChart.getDeviceClass())
 		{
 			case GamesChart.DEVICE_SIZE_SMALLEST:
 				size=12;
 				break;
 			case GamesChart.DEVICE_SIZE_SMALL:
 				size=16;
 	 			break;
 			case GamesChart.DEVICE_SIZE_MEDIUM:
 				size=20;
 	 			break;
 			case GamesChart.DEVICE_SIZE_LARGE:
 				size=22;
 	 			break;
 			case GamesChart.DEVICE_SIZE_LARGEST:
 				size=22;
 	 			break;
 	 	}
 		
 		return size;
 	}
 	
 	private static int getDescrptionFontSize()
 	{
 		int size=12;
 		switch (GamesChart.getDeviceClass())
 		{
 			case GamesChart.DEVICE_SIZE_SMALLEST:
 				size=12;
 				break;
 			case GamesChart.DEVICE_SIZE_SMALL:
 				size=16;
 	 			break;
 			case GamesChart.DEVICE_SIZE_MEDIUM:
 				size=20;
 	 			break;
 			case GamesChart.DEVICE_SIZE_LARGE:
 				size=22;
 	 			break;
 			case GamesChart.DEVICE_SIZE_LARGEST:
 				size=22;
 	 			break;
 	 	}
 		
 		return size;
 	}
 	
 	 public void drawTextInRect( Canvas canvas, Paint paint, Rect r, CharSequence text ) 
	 { 
		int start = 0; 
		int end   = text.length() - 1; 
		float     x = r.left; 
		float     y = r.top; 
		int allowedWidth = r.width();  // constrain text block within this width in pixels 
		
		if( allowedWidth < 64 ) // arbitrary minimum, 64 pixels is sensible
		{ 
			allowedWidth=64;
		} 
		
		// get the distance in pixels between two lines of text 
		int lineHeight = paint.getFontMetricsInt( null ); 
		
		// emit one line at a time, as much as will fit, with word wrap on whitespace. 
		while( start < end )
		{ 
			int charactersRemaining = end - start + 1; 
			int charactersToRenderThisPass = charactersRemaining;
			int extraSkip = 0; 
			
			while( charactersToRenderThisPass > 0 && paint.measureText(text, start, start +charactersToRenderThisPass ) > allowedWidth ) 
			{ 
				// remaining text won't fit, cut one character from the end and check again 
				charactersToRenderThisPass--; 
			} 
			
			// charactersToRenderThisPass would definitely fit, but could be in the middle of a word 
			int thisManyWouldDefinitelyFit = charactersToRenderThisPass; 
					
			if( charactersToRenderThisPass < charactersRemaining ) 
			{ 
				while( charactersToRenderThisPass > 0 && !Character.isWhitespace( text.charAt( start +charactersToRenderThisPass-1) )) 
				{ 
					charactersToRenderThisPass--; // good bye character that would have fit! 
				} 
			} 
			
			// Now wouldn't it be nice to be able to put in line breaks? 
			int i; 
			for( i=0; i<charactersToRenderThisPass; i++ ) 
			{ 
				if( text.charAt( start+i ) == '\n' ) 
				{  
					// um, what's unicode for 'isLineBreak' or '\n'? 
					// cool, lets stop this line early 
					charactersToRenderThisPass = i; 
					extraSkip = 1;  // so we don't start next line with the lineBreak character 
					break; 
				} 
			} 
			if( charactersToRenderThisPass < 1 && (extraSkip == 0)) 
			{ 
				// no spaces found, must be a really long word. 
				// show as much as would fit, breaking the word in the middle 
				charactersToRenderThisPass = thisManyWouldDefinitelyFit; 
			} 
			
			// Emit this line of characters and advance our offsets for the next line 
			if( charactersToRenderThisPass > 0 ) 
			{ 
				canvas.drawText( text, start, start+charactersToRenderThisPass, x, y, paint ); 
			} 
			
			start += charactersToRenderThisPass + extraSkip; 
			y += lineHeight; 
					
			// start had better advance each time through the while, or we've invented an infinite loop 
			if( (charactersToRenderThisPass + extraSkip) < 1 ) 
			{ 
				return;
			} 
		} 
	} 

 	String updateTicker="";
}



