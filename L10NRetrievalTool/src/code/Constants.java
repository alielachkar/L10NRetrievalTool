// PACKAGE DECLARATION
package code;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.FontUIResource;

import view.View;

/**
 * Hold the applications global constants
 * @author ploh
 * @version 1.0
 */

public final class Constants {
	// Use a newline constant to allow compatibility with various platforms
	public static final String NEWLINE = System.getProperty("line.separator");
	
	/* COLOURS */
	public static final Color WHITE = new Color(255,255,255);
	public static final Color DARKBLUE = new Color(0,90,140);
	public static final Color DARKGRAY = new Color(50,50,50);
	public static final Color LIGHTGRAY = new Color(70,70,70);
	public static final Color LIGHTBLUE = new Color(200,220,250);
	public static final Color GREEN = new Color(100,180,100);
	
	/* FONTS */
	public static final FontUIResource BASEFONT = new FontUIResource("Segoe UI Semibold", Font.PLAIN, 12);
	public static final Font TITLEFONT = new Font("Segoe UI", Font.PLAIN, 15);
	
	/* IMAGE ICONS */
	public static ImageIcon SETTINGSICONSMALL = new ImageIcon(View.class.getResource("images/settings_small.png"));
	public static ImageIcon HELPICONSMALL = new ImageIcon(View.class.getResource("images/help.png"));
	public static ImageIcon SETTINGSICON = new ImageIcon(View.class.getResource("images/settings.png"));
	public static ImageIcon CLOSEICON = new ImageIcon(View.class.getResource("images/close_ICON.png"));
	public static ImageIcon MINIMIZEICON = new ImageIcon(View.class.getResource("images/minimize_ICON.png"));
	public static ImageIcon LOGOICON = new ImageIcon(View.class.getResource("images/logo_ICON_Rev.png"));
	
	/* BORDERS */
	public static Border EMPTY5PXBORDER = new EmptyBorder(5,5,5,5);
	public static Border BORDERCONSTANT = new CompoundBorder(new MatteBorder(0,0,2,0, Constants.LIGHTGRAY),new EmptyBorder(5,5,5,5));
}
