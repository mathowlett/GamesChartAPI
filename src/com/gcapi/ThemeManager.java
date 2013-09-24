package com.gcapi;

import android.graphics.Color;
/**
 * @author      Mat Howlett <mat@howletmedia.com>
 * @version     0.0.1              
 * @since       17/11/11
 */
public class ThemeManager {

	public static final int THEME_DEFAULT=0;
	public static final int THEME_WHITE=1;
	public static final int THEME_BLACK=2;
	public static final int THEME_CREAM=3;
	public static final int THEME_TEAL=4;
	public static final int THEME_PURPLE=5;
	public static final int THEME_DUSK=6;
	public static final int THEME_DAYBREAK=7;
	public static final int NUMBER_OF_INTERNAL_THEMES=8;
	
	public static final int MAX_THEMES=4;
	
	// this is the default theme, the use may not change this
	private static int [][] DEFAULT_THEME_STORE=
	{
		// DEAULT THEME
		{Color.WHITE,
		0xffcccccc,
		0xffeeeeee,
		0xffdddddd,
		Color.DKGRAY,	
		Color.WHITE,
		Color.GRAY,
		Color.GRAY,
		Color.WHITE,
		Color.DKGRAY,	
		Color.DKGRAY,	
		Color.WHITE,
		Color.RED},
	};
	
	// the currently displayed theme
	Theme mainTheme;
	
	// the extra loaded themes
	Theme [] extraThemes;
	
	// create a manager
	public ThemeManager()
	{
		// set all themes to the default state and colour
		resetAllThemes();
	}
	
	public void setTheme(int theme)
	{
		// the user hasn't told us which theme to change, so we'll just change the main theme
		mainTheme.setTheme(DEFAULT_THEME_STORE[theme]);
	}
	
	public void setTheme(int id, int theme)
	{
		// the user has told us which theme to change
		extraThemes[id].setTheme(DEFAULT_THEME_STORE[theme]);
		mainTheme=extraThemes[id]; // use it as the main theme
	}
	
	public void restoreDefaultTheme()
	{
		mainTheme.setTheme(DEFAULT_THEME_STORE[THEME_DEFAULT]);	
	}

	public void resetAllThemes()
	{
		// always create the default theme
		mainTheme = new Theme(THEME_DEFAULT);
		mainTheme.setTheme(DEFAULT_THEME_STORE[THEME_DEFAULT]);	
		
		// create extra themes for the user to play with
		extraThemes = new Theme[MAX_THEMES];
		for (int i=0; i<extraThemes.length; i++)
		{
			extraThemes[i] = new Theme(THEME_DEFAULT);
		}
	}

}
