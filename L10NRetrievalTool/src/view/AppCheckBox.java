package view;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import code.Constants;

/**
 * Custom Check Box for UI Interface
 * @author ploh
 *
 */
public class AppCheckBox extends JCheckBox {
	private static final long serialVersionUID = 6527978325339618859L;

	public AppCheckBox(String text) {
		super(text);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBorderPainted(false);
		this.setBackground(Constants.DARKBLUE);
		this.setForeground(Constants.WHITE);
		this.setContentAreaFilled(false);
		this.setOpaque(true);
		this.setFocusPainted(false);
	}
	
	public AppCheckBox() {
		super();
	}
}
