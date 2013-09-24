package com.gcapi;

import android.graphics.Color;
/**
 * @author      Mat Howlett <mat@howletmedia.com>
 * @version     0.0.1              
 * @since       17/11/11
 */
public class Theme {

	// colours used to create a theme
	// many of these elements will use the same color, but we'll define every element here
	// just in case we wish to further customise the themes
	
	// main layout elements
	public static int 
		colorColorMain, // background color
		colorOutline, // generic outline
	
		// rows
		colorRowColor1, // row backgound color
		colorRowColor2, // row background color (alternate)
		colorRowTab,	// box holding position number
		colorRowTabText,// box holding position number text
		colorRowGameTitleText, // game title text
		colorRowGameDescriptionText, // game description text
		
		// tabs bar
		colorTabSelected, 
		colorTabSelectedText,
		colorTabUnselected,
		colorTabUnselectedText,
		colorTabLeftBar; // small vertical tab bar
	
	// main layout elements (identifiers)
	public static final int 
		elementMain=0,
		elementOutline=1,
		elementRowColor1=2,
		elementRowColor2=3,
		elementRowTab=4,
		elementRowTabText=5,
		elementRowGameTitleText=6,
		elementRowGameDescriptionText=7,
		elementTabSelected=8,
		elementTabSelectedText=9,
		elementTabUnselected=10,
		elementTabUnselectedText=11,
		elementTabLeftBar=12,
		numberOfElements=13; 

	
	public static int themeColors[]; 
	
	public Theme(int theme)
	{
		themeColors=new int[numberOfElements];
		
	}
	
	public static int alpha=0;
	public static int red=0;
	public static int green=0;
	public static int blue=0;
	
	public static int redcomp = red;
	public static int greencomp = green;
	public static int bluecomp = blue;
	
	private static void limitARGB(int limit)
	{	
		if (redcomp>limit) redcomp=limit;
		if (greencomp>limit) greencomp=limit;
		if (bluecomp>limit) bluecomp=limit;
		if (redcomp<0) redcomp=0;
		if (greencomp<0) greencomp=0;
		if (bluecomp<0) bluecomp=0;
	}
	
	public void setSingleColorTheme(int color)
	{
		alpha = Color.alpha(color);
		red = Color.red(color);
		green = Color.green(color);
		blue = Color.blue(color);

		// set full saturation elements here
		setRowTabColor(Color.argb(alpha, red, green, blue));
		setTabUnselectedColor(Color.argb(alpha, red, green, blue));
		setTabSelectedTextColor(Color.argb(alpha, red, green, blue));
		
		// lightest color
		redcomp = red+200;
		greencomp = green+200;
		bluecomp = blue+200;
		limitARGB(255);
		setMainColor(Color.argb(alpha, redcomp, greencomp, bluecomp));
		
		// slightly darker
		redcomp = red+180;
		greencomp = green+180;
		bluecomp = blue+180;
		limitARGB(220);
		setRow1Color(Color.argb(alpha, redcomp, greencomp, bluecomp));
		
		// darker still
		redcomp = red+160;
		greencomp = green+160;
		bluecomp = blue+160;
		limitARGB(200);
		setRow2Color(Color.argb(alpha, redcomp, greencomp, bluecomp));
		
		// mid shade
		redcomp = red+100;
		greencomp = green+100;
		bluecomp = blue+100;
		limitARGB(255);
		setOutlineColor(Color.argb(alpha, redcomp, greencomp, bluecomp));
		
		// dark shade
		redcomp = red+40;
		greencomp = green+40;
		bluecomp = blue+40;
		limitARGB(255);
		setRowGameTitleTextColor(Color.argb(alpha, redcomp, greencomp, bluecomp));
		setRowGameDescriptionTextColor(Color.argb(alpha, redcomp, greencomp, bluecomp));
		
		// white elements
		setRowTabTextColor(0xffffffff);
		setTabUnselectedTextColor(0xffffffff);
		
		setTabSelectedColor(0xffffffff);
		
		// always red?
		setTabLeftBarColor(0xffff0000);
		
		setThemeColors();
	}
	
	public int [] getSingleColorTheme(int color)
	{
		setSingleColorTheme(color);
		return themeColors;
	}
	
	public void setThemeColors()
	{
		themeColors[elementMain]=colorColorMain;
		themeColors[elementOutline]=colorOutline;
		themeColors[elementRowColor1]=colorRowColor1;
		themeColors[elementRowColor2]=colorRowColor2;
		themeColors[elementRowTab]=colorRowTab;
		themeColors[elementRowTabText]=colorRowTabText;
		themeColors[elementRowGameTitleText]=colorRowGameTitleText;
		themeColors[elementRowGameDescriptionText]=colorRowGameDescriptionText;
		themeColors[elementTabSelected]=colorTabSelected;
		themeColors[elementTabSelectedText]=colorTabSelectedText;
		themeColors[elementTabUnselected]=colorTabUnselected;
		themeColors[elementTabUnselectedText]=colorTabUnselectedText;
		themeColors[elementTabLeftBar]=colorTabLeftBar;
	}
	
	public void setTheme(int [] theme)
	{
		themeColors[elementMain]=0xffffffff;//theme[elementMain];
		themeColors[elementOutline]=theme[elementOutline];
		themeColors[elementRowColor1]=theme[elementRowColor1];
		themeColors[elementRowColor2]=theme[elementRowColor2];
		themeColors[elementRowTab]=theme[elementRowTab];
		themeColors[elementRowTabText]=theme[elementRowTabText];
		themeColors[elementRowGameTitleText]=theme[elementRowGameTitleText];
		themeColors[elementRowGameDescriptionText]=theme[elementRowGameDescriptionText];
		themeColors[elementTabSelected]=theme[elementTabSelected];
		themeColors[elementTabSelectedText]=theme[elementTabUnselected];
		themeColors[elementTabUnselected]=theme[elementTabUnselected];
		themeColors[elementTabUnselectedText]=theme[elementTabUnselectedText];
		themeColors[elementTabLeftBar]=theme[elementTabLeftBar];
		
		//setSingleColorTheme(Color.BLUE);
	}
	
	public void setMainColor(int col){colorColorMain=col;}
	public void setOutlineColor(int col){colorOutline=col;}
	public void setRow1Color(int col){colorRowColor1=col;}
	public void setRow2Color(int col){colorRowColor2=col;}
	public void setRowTabColor(int col){colorRowTab=col;}
	public void setRowTabTextColor(int col){colorRowTabText=col;}
	public void setRowGameTitleTextColor(int col){colorRowGameTitleText=col;}
	public void setRowGameDescriptionTextColor(int col){colorRowGameDescriptionText=col;}
	public void setTabSelectedColor(int col){colorTabSelected=col;}
	public void setTabSelectedTextColor(int col){colorTabSelectedText=col;}
	public void setTabUnselectedColor(int col){colorTabUnselected=col;}
	public void setTabUnselectedTextColor(int col){colorTabUnselectedText=col;}
	public void setTabLeftBarColor(int col){colorTabLeftBar=col;}
}
