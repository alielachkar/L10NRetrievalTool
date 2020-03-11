package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Custom JLabel for UI Interface
 * @author ploh
 *
 */
public class AppLabel extends JLabel {
	private static final long serialVersionUID = -4341233574937807561L;

	public AppLabel(String text) {
		super(text);
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}

	public AppLabel(String prop, ImageIcon helpSmall, int trailing) {
		super(prop, helpSmall, trailing);
	}
}
