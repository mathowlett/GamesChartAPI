package com.gcapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
/**
 * @author      Mat Howlett <mat@howletmedia.com>
 * @version     0.0.1              
 * @since       17/11/11
 */
public class IconManager {

	static InputStream is;
	private static boolean useTileSheetVersion=true;
	private double scaleFactor;
    private static Bitmap animatedIcon[];
    private static Bitmap animatedIconSheet;
    private static Bitmap customIcon;
    private static Bitmap staticIcon;
    private static int frame=0;
    
    public int gcIconX;
    public int gcIconY;
    public int gcIconW;
    public int gcIconH;
    public int gcIconSmallestSide;
    public int gcIconPadding;
    public int iconState=0; // still
    public int iconDelay=20;
    public int iconTicker=0;
    public int frameDelay=2;
    public int frameDelayTicker=0;
    public int loadAnimatedDelayTicker=0;
    public int loadAnimatedDelay=5;
    public int iconsLoaded=0;
    
    public int frameSkip=0;
    public int srcIconW=64;
    public int srcIconH=64;
    
    Rect rectIcon;
    Context context;
    Display display;
    
    private int screenW;
    private int screenH;
    private int screenOrientation=0;
 	private int oldScreenOrientation=0;
    private boolean useCustomIcon=false;
    private boolean useAnimatedIcon=false;
    
    private boolean readyToDraw=false;
    
    private int [][] iconCoords =
    	{ 
    {1, 1}, 
    {1, 67}, 
    {1, 133}, 
    {1, 199}, 
    {1, 265}, 
    {1, 331}, 
    {1, 397}, 
    {67, 1}, 
    {133, 1}, 
    {199, 1}, 
    {265, 1}, 
    {331, 1}, 
    {397, 1}, 
    {67, 67}, 
    {67, 133}, 
    {67, 199}, 
    {67, 265}, 
    {67, 331}, 
    {67, 397}, 
    {133, 67}, 
    {199, 67}, 
    {265, 67}, 
    {331, 67}, 
    {397, 67}, 
    {133, 133}, 
    {133, 199}, 
    {133, 265}, 
    {133, 331}, 
    {133, 397}, 
    {199, 133}, 
    {265, 133}, 
    {331, 133}, 
    {397, 133}, 
    {199, 199}, 
    {199, 265}, 
    {199, 331}, 
    {199, 397}, 
    {265, 199}, 
    {331, 199}, 
    {397, 199}, 
    {265, 265}, 
    {265, 331}
    };
    public void init(Context c)
    {
    	// set up the display
		context = c;
		animatedIcon=new Bitmap[42];
		screenOrientation=GamesChart.displayOrientation;
    	oldScreenOrientation=screenOrientation;
    	
    	screenW=GamesChart.displayWidth;
     	screenH=GamesChart.displayHeight;
     	Log.d("SCREEN W: ", ""+screenW);
		Log.d("SCREEN H: ", ""+screenH);
     	gcIconW=GamesChart.getIconSize();
     	gcIconH=GamesChart.getIconSize();
     	Log.d("ICON W: ", ""+gcIconW);
		Log.d("ICON H: ", ""+gcIconH);
		scaleFactor = (double)(gcIconW/srcIconW);
		Log.d("ICON SCALE: ", ""+scaleFactor);
     	setDefaultIcon();
     	
     	if (!useTileSheetVersion)
     	{
	     	// frame skipping, only used for frame-by-frame download animated version
	     	frameSkip=3;
	     	if (frameSkip == 1) {frameDelay=2;}
	     	else if (frameSkip == 2){frameDelay=4;}
	     	else if(frameSkip == 3){frameDelay=6;}
	     	else{frameDelay=1;}
     	}
     	
     	readyToDraw=true;
     	
     	
     	
    }
    
    public void loadAnimatedSheet()
    {
    	String iconimg="icons.png";
    	int tries=0;
		try 
		{
			/*
			try {
			    // Create a URL for the desired page
			    URL url = new URL(GamesChart.IMAGE_DIRECTORY_ROOT+GamesChart.IMAGE_DIRECTORY_ICONS+"icons.txt");

			    // Read all the text returned by the server
			    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			    String str;
			    int line=0;
			    while ((str = in.readLine()) != null) {
			        // str is one line of text; readLine() strips the newline character(s)
			    	String[] tokens = str.split(", ");
			        int tokenCount=0;
			        
			        Log.d("LINE:", str+" at line "+tokens);
			    	for(int i=0; i<tokens.length; i++) {
			            String t = tokens[i];
			            if (tokenCount==1)
			            {
			            	iconCoords[line][0]=Integer.parseInt( t );
			            	Log.d("LINE X:", t);
			            	
			            }
			            else if (tokenCount==2)
			            {
			            	iconCoords[line][1]=Integer.parseInt( t );
			            	Log.d("LINE Y:", t);
			            }
			            tokenCount++;
			        }
			    	line++;
			    }
			    in.close();
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}*/
			/*try
			{
				animatedIconSheet=GamesChart.cm.loadImage(GamesChart.IMAGE_DIRECTORY_ROOT+GamesChart.IMAGE_DIRECTORY_ICONS+iconimg, (int)(512*scaleFactor), (int)(512*scaleFactor));
				Log.d("GCFAIL:", "Failed to load sheet");
			}
			catch (Exception e)
			{
				animatedIconSheet=GamesChart.cm.loadImage("http://www.howlettmedia.com/test/icon/icons.png", (int)(512*scaleFactor), (int)(512*scaleFactor));
				Log.d("HMTRY:", "Load sheet");
			}*/
			
			animatedIconSheet=GamesChart.cm.loadImage(GamesChart.IMAGE_DIRECTORY_ROOT+GamesChart.IMAGE_DIRECTORY_ICONS+iconimg, (int)(512*scaleFactor), (int)(512*scaleFactor));
			
			
			useAnimatedIcon=true;
		} 
		catch (Exception e) 
		{
			useAnimatedIcon=false; // not available
		}
    }
    
    public void loadIconFrame()
    {	
    	// load a frame at a time
		String iconimg=""+iconsLoaded+".png";
		try {
			animatedIcon [iconsLoaded]=GamesChart.cm.loadImage(GamesChart.IMAGE_DIRECTORY_ROOT+GamesChart.IMAGE_DIRECTORY_ICONS+iconimg, gcIconW, gcIconW);
		} catch (Exception e) {
			is = getClass().getClassLoader().getResourceAsStream("GC_icon.png");//iconimg);
			animatedIcon [iconsLoaded]=GamesChart.cm.resizeBitMap(BitmapFactory.decodeStream(is), gcIconW, gcIconW);
		}
		loadAnimatedDelayTicker=0;
    }
    
    public void setDefaultIcon()
    {
    	// offline
    	if(GamesChart.bConnectionAvailable)
    	{
	    	if (!useAnimatedIcon)
			{
	    		Log.d("ICON", "STATIC");
				is = getClass().getClassLoader().getResourceAsStream("GC_icon.png");
				staticIcon=GamesChart.cm.resizeBitMap(BitmapFactory.decodeStream(is), gcIconW, gcIconW);
	    		//is = getClass().getClassLoader().getResourceAsStream("gcicon.png");
				//staticIcon=BitmapFactory.decodeStream(is);
				
				//staticIcon = GamesChart.cm.loadImageLocal("gcicon.png");
				//staticIcon=GamesChart.cm.loadImage("http://www.howlettmedia.com/test/icon/GC_icon.png", gcIconW, gcIconW);
				
				// get the dimensions
				//gcIconW=staticIcon.getWidth();
				//gcIconH=staticIcon.getHeight();
			}
			else
			{
				/*Log.d("ICON", "ANIMATED");
				animatedIcon=new Bitmap[42];
						
				for (int i=0; i<animatedIcon.length-1; i++)
				{
					
					String iconimg=""+i+".png";
					try {
						animatedIcon [i]=GamesChart.cm.loadImage(GamesChart.IMAGE_DIRECTORY_ROOT+GamesChart.IMAGE_DIRECTORY_ICONS+iconimg, gcIconW, gcIconW);
					} catch (Exception e) {
						
						is = getClass().getClassLoader().getResourceAsStream("GC_icon.png");//iconimg);
						animatedIcon [i]=GamesChart.cm.resizeBitMap(BitmapFactory.decodeStream(is), gcIconW, gcIconW);
					}
				}*/
			}
    	}
		
		// establish the smallest side
		gcIconSmallestSide=gcIconH;
		if(gcIconW<gcIconH)
			gcIconSmallestSide=gcIconW;
		
		// create padding
		gcIconPadding=gcIconSmallestSide>>2;
		
		gcIconX=screenW-(gcIconW+(gcIconPadding));
		gcIconY=0+gcIconPadding;
		
		rectIcon=new Rect(gcIconX, gcIconY, gcIconX+gcIconW, gcIconY+gcIconH);
    }
    
    public void setPosition(int x, int y)
    {
    	gcIconX=x;
		gcIconY=y;
		rectIcon=new Rect(gcIconX, gcIconY, gcIconX+gcIconW, gcIconY+gcIconH);
    }
    
    public void setCustomStaticIcon(String iconURL, int w, int h)
    {
    	if(GamesChart.bConnectionAvailable) // no connection
    	{
			useCustomIcon=true;
			try
			{
				customIcon=GamesChart.cm.loadImage(iconURL, w, h);
			}
			catch (Exception e)
			{
				customIcon=GamesChart.cm.loadImage(GamesChart.getImageDirectory()+GamesChart.ERROR_IMAGE, w, h);
			}
			gcIconW=customIcon.getWidth();
			gcIconH=customIcon.getHeight();
			// establish the smallest side
			gcIconSmallestSide=gcIconH;
			if(gcIconW<gcIconH)
				gcIconSmallestSide=gcIconW;
			
			// create padding
			gcIconPadding=gcIconSmallestSide>>2;
			
			if (gcIconW > srcIconW || gcIconH > srcIconH)
				gcIconPadding=2;
			
			gcIconX=screenW-(gcIconW+(gcIconPadding));
			gcIconY=0+gcIconPadding;
			
			rectIcon=new Rect(gcIconX, gcIconY, gcIconX+gcIconW, gcIconY+gcIconH);
		
    	}
    	
    }
    
    public void useAnimatedIcon(boolean animated)
    {
    	//useAnimatedIcon=animated;
    	useAnimatedIcon=false;//animated;
    	setDefaultIcon();
    }
    
    public void update()
    {
    	// check for change of orientation
    	screenOrientation=GamesChart.displayOrientation;
    	screenW=GamesChart.displayWidth;
     	screenH=GamesChart.displayHeight;
     	
     	// it's changed, so we need to re-position the icon
    	if(screenOrientation!=oldScreenOrientation)
    	{	
    		gcIconX=screenW-(gcIconW+(gcIconPadding));
    		gcIconY=0+gcIconPadding;
    		
    		rectIcon=new Rect(gcIconX, gcIconY, gcIconX+gcIconW, gcIconY+gcIconH);
    		
    		oldScreenOrientation=screenOrientation;
    	}
    	if (useCustomIcon) 
		{
    		// just static for now, so no update
    		// we'll deal with custom animated later
		}
    	else
    	{
	    	// deal with any animations
	    	if (useAnimatedIcon)
	    	{
		    	if (GamesChart.cm.alphafade==0)
		    		iconTicker++; // just tick per frame, frame compensate to do
				
		    	// ready to animate?
				if (iconTicker > iconDelay)
				{
					iconTicker=iconDelay; // clamp it here while we playthe anim
				}
				
				// ready to change frame
				if (iconTicker==iconDelay)
				{
					// play the anim
					if (frame<40)
					{
						if (useTileSheetVersion)
	    				{
							 if (frameDelayTicker < frameDelay)
						    {
						    	frameDelayTicker++;
						    }
						    else
						    {
						    	frame++;
						    	frameDelayTicker=0;
						    }
							
	    				}
						else
						{
						    if (frameDelayTicker < frameDelay)
						    {
						    	frameDelayTicker++;
						    }
						    else
						    {
						    	frame+=(frameSkip+1);
						    	frameDelayTicker=0;
						    }
						}
					    	
					}
					else
					{
						// reset and set a new ticker limit
						int min = 90;
						int max = 270;
						Random r = new Random();
						frame=0;
						iconTicker=0;
						iconDelay=max;//r.nextInt(max - min + 1) + min;	
					}
				}
	    	}
	    	else
	    	{
	    		if (loadAnimatedDelayTicker<loadAnimatedDelay)
	    		{
	    			loadAnimatedDelayTicker++;
	    		}
	    		else
	    		{	
	    			if (!useAnimatedIcon)
	    			{
	    				if (useTileSheetVersion)
	    				{
	    					loadAnimatedSheet();
	    				}
	    				else // using individual images, downloaded frame by frame
	    				{
		    				if (iconsLoaded >= 42)
		    				{
		    					useAnimatedIcon=true;//animated;
		    				}
		    				else
		    				{
		    					loadIconFrame();
		    					iconsLoaded+=(frameSkip+1);
		    				}
	    				}
	    			}

	    		}	
	    	}
    	}
    }
    private int alpha=0;
    Rect sourceRect=null;

	Rect destRect = null;
	public void draw(Canvas c, Paint p)
	{	
		
		if (readyToDraw)
		{
			Paint Transparentpainthack = new Paint();
			if (alpha<100)
				alpha++;
			
			if (alpha==50)
				// play bloop sound
			Transparentpainthack.setAlpha(alpha);
			
			if(GamesChart.bConnectionAvailable) // connection
	    	{
				try
				{
					if (useCustomIcon)
					{
						// draw custom icon
						c.drawBitmap(customIcon, gcIconX, gcIconY, Transparentpainthack);
					}
					else
					{
						if (!useAnimatedIcon)
			    		{
							c.drawBitmap(staticIcon, gcIconX, gcIconY, null);
			    		}
						else
						{
							if (useTileSheetVersion)
		    				{
								
								sourceRect = new Rect((int)(iconCoords[0][0]*scaleFactor), (int)(iconCoords[0][0]*scaleFactor), gcIconW, gcIconH);
								sourceRect.left = (int)(iconCoords[frame][0]*scaleFactor);
								sourceRect.right = (int)(iconCoords[frame][0]*scaleFactor)+gcIconW;
								sourceRect.top = (int)(iconCoords[frame][1]*scaleFactor);
								sourceRect.bottom = (int)(iconCoords[frame][1]*scaleFactor)+gcIconH;
								//ourceRect.right = this.sourceRect.left + spriteWidth;

								destRect = new Rect( gcIconX, gcIconY, gcIconX + gcIconW, gcIconY + gcIconH);
							
								c.drawBitmap(animatedIconSheet, sourceRect, destRect, null);

								//c.drawBitmap(animatedIconSheet, gcIconX, gcIconY, null);
		    				}
							else
							{
								c.drawBitmap(animatedIcon[frame], gcIconX, gcIconY, null);
							}
						}
						
					}
				}
				catch (Exception e)
				{
					//p.setColor(Color.BLUE);
					//c.drawRect(200, 2, 264, 64, p);
				}
	    	}
		}
	}
	
	URL url = null;
	
	public static Bitmap loadImageLocal(String path, int w, int h) throws IOException
    {
    	AssetManager assetManager = GamesChart.cm.context.getAssets();
    	InputStream istr = assetManager.open(path);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return resizeBitMap(bitmap, w, h);
    }
	
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
 	
 	private static Bitmap resizeBitMap(Bitmap bitmapIn, int neww, int newh)
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
