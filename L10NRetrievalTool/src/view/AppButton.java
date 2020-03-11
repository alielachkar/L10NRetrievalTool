package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import code.Constants;

/**
 * Custom Swing Button for UI Interface
 * @author ploh
 *
 */
public class AppButton extends JButton{
	private static final long serialVersionUID = 8862645697066324700L;

	public AppButton(String text){
		super(text);
		this.setBackground(Constants.DARKBLUE);
		this.setForeground(Constants.WHITE);
		this.setContentAreaFilled(false);
		this.setOpaque(true);
		this.setFocusPainted(false);
	}

	public AppButton(String text, ImageIcon Icon, int placement) {
		super(text,Icon);
		this.setAlignmentX(placement);
		this.setBackground(Constants.DARKBLUE);
		this.setForeground(Constants.WHITE);
		this.setContentAreaFilled(false);
		this.setOpaque(true);
		this.setFocusPainted(false);
	}
	
	public AppButton(ImageIcon minimizeIcon) {
		super(minimizeIcon);
		this.setContentAreaFilled(false);
		this.setOpaque(false);
		this.setBorder(null);
		this.setFocusPainted(false);
	}
}
