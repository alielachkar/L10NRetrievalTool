package code;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import view.AppButton;
import common.Props;
import static common.Props.prop;

/**
 * Settings Panel for UI Application
 * @author ploh
 *
 */
public class SettingsPanel {

	public JFrame settingsFrame;
	public JCheckBox indexCurrentDir;
	public JCheckBox traverseAllDir;
	private JTextField option1Field;
	private JTextField option2Field;
	private JTextField option3Field;
	private JTextField option4Field;
	private AppButton okSettingsBtn;
	private AppButton applySettingsBtn;
	private AppButton cancelSettingsBtn;
	private AppButton defaultSettingsBtn;
	private JMenuBar settingsMenuBar;

	private int pX2, pY2 = 0;
	
	public SettingsPanel() {
		initialize();
	}

	public void initialize() {
		settingsFrame = new JFrame();
		settingsFrame.setUndecorated(true);
		settingsFrame.setResizable(false);
		settingsFrame.setVisible(true);
		settingsFrame.setAlwaysOnTop(true);
		settingsFrame.setTitle(prop("L10N_String.SettingsPanel"));

		JPanel settingsMainPanel = new JPanel();
		settingsMainPanel.setLayout(new BorderLayout());
		settingsMainPanel.setBorder(new LineBorder(Constants.DARKBLUE, 1, true));

		JLabel settingsTitle = new JLabel(prop("L10N_String.Settings").toUpperCase(), Constants.SETTINGSICON, SwingConstants.TRAILING);
		settingsTitle.setFont(Constants.TITLEFONT);
		settingsTitle.setForeground(Constants.WHITE);

		JPanel settingsHeader = new JPanel();
		settingsHeader.setLayout(new BorderLayout());
		settingsHeader.setBorder(Constants.EMPTY5PXBORDER);
		settingsHeader.add(settingsTitle, BorderLayout.WEST);
		settingsHeader.setBackground(Constants.DARKBLUE);

		JPanel settingsTable = new JPanel();
		settingsTable.setLayout(new GridLayout(6,2));
		settingsTable.setBorder(Constants.EMPTY5PXBORDER);

		JLabel option1 = new JLabel(prop("L10N_String.Filetype1").toUpperCase());
		JLabel option2 = new JLabel(prop("L10N_String.Filetype2").toUpperCase());
		JLabel option3 = new JLabel(prop("L10N_String.Filetype3").toUpperCase());
		JLabel option4 = new JLabel(prop("L10N_String.DefaultDestination").toUpperCase());
		JLabel indexingOption = new JLabel(prop("L10N_String.IndexingOption").toUpperCase());

		option1Field = new JTextField(prop("L10N_String.filetype1"));
		option2Field = new JTextField(prop("L10N_String.filetype2"));
		option3Field = new JTextField(prop("L10N_String.filetype3"));
		option4Field = new JTextField(prop("L10N_Config.DefaultDestination"));

		indexCurrentDir = new JCheckBox(prop("L10N_String.IndexOnlyCurrentDirectory"), Boolean.parseBoolean(prop("L10N_Config.indexCurrentDir")));
		traverseAllDir = new JCheckBox(prop("L10N_String.TraverseAllDirectories"), Boolean.parseBoolean(prop("L10N_Config.traverseAllDir")));

		settingsTable.add(option1);
		settingsTable.add(option1Field);
		settingsTable.add(option2);
		settingsTable.add(option2Field);
		settingsTable.add(option3);
		settingsTable.add(option3Field);
		settingsTable.add(option4);
		settingsTable.add(option4Field);
		settingsTable.add(indexingOption);
		settingsTable.add(indexCurrentDir);
		settingsTable.add(new JLabel()); // Empty JLabel  
		settingsTable.add(traverseAllDir);

		indexCurrentDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (indexCurrentDir.isSelected()) {
					traverseAllDir.setSelected(false);
				}
			}
		});

		traverseAllDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (traverseAllDir.isSelected()) {
					indexCurrentDir.setSelected(false);
				}
			}
		});

		JPanel settingsBtns = new JPanel();
		settingsBtns.setLayout(new GridLayout(1,4));
		settingsBtns.setBorder(Constants.EMPTY5PXBORDER);
		settingsBtns.setBorder(Constants.EMPTY5PXBORDER);

		okSettingsBtn = new AppButton(prop("L10N_String.OK").toUpperCase());
		okSettingsBtn.setActionCommand("okSettingsPanel");
		applySettingsBtn = new AppButton(prop("L10N_String.Apply").toUpperCase());
		applySettingsBtn.setActionCommand("applySettingsPanel");
		cancelSettingsBtn = new AppButton(prop("L10N_String.Cancel").toUpperCase());
		cancelSettingsBtn.setActionCommand("cancelSettingsPanel");
		defaultSettingsBtn = new AppButton(prop("L10N_String.Default").toUpperCase());
		defaultSettingsBtn.setActionCommand("defaultSettingsBtn");

		settingsBtns.add(okSettingsBtn);
		settingsBtns.add(applySettingsBtn);
		settingsBtns.add(cancelSettingsBtn);
		settingsBtns.add(defaultSettingsBtn);

		settingsMainPanel.add(settingsHeader, BorderLayout.NORTH);
		settingsMainPanel.add(settingsTable, BorderLayout.CENTER);
		settingsMainPanel.add(settingsBtns, BorderLayout.SOUTH);
		settingsFrame.add(settingsMainPanel);
		settingsFrame.setJMenuBar(createMenuBar());
		settingsFrame.pack();
	}
	
	private JMenuBar createMenuBar(){
		settingsMenuBar = new JMenuBar();
		settingsMenuBar.setBorder(Constants.EMPTY5PXBORDER);
		settingsMenuBar.setLayout(new BorderLayout());
		settingsFrame.setJMenuBar(settingsMenuBar);

		settingsMenuBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// Get x,y and store them
				pX2= me.getX();
				pY2= me.getY();
			}
		});

		// Add MouseMotionListener for detecting drag
		settingsMenuBar.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				settingsFrame.setLocation(settingsFrame.getLocation().x+me.getX()-pX2,settingsFrame.getLocation().y+me.getY()-pY2);
			}
		});

		AppButton closeBtn = new AppButton(Constants.CLOSEICON);
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//settingsPanelLaunched = false;
				settingsFrame.setVisible(false);
			}
		});	

		JPanel frameBtns = new JPanel();
		frameBtns.setLayout(new GridLayout(1,3));
		frameBtns.add(closeBtn);
		frameBtns.setOpaque(false);
		settingsMenuBar.add(frameBtns, BorderLayout.EAST);
		
		return settingsMenuBar;
	}
	
	public void updatePanelLoc(Point loc) {
		int x = (int) (loc.getX() + 40);
		int y = (int) (loc.getY() + 120);
		settingsFrame.setLocation(x,y);
	}

	public void loadSettings() {
		option1Field.setText(prop("L10N_String.filetype1"));
		option2Field.setText(prop("L10N_String.filetype2"));
		option3Field.setText(prop("L10N_String.filetype3"));
		option4Field.setText(prop("L10N_Config.DefaultDestination"));
	}

	// Resets all fields with the predefined settings
	public void resetAllSettings() {
		option1Field.setText("ZIP");
		option2Field.setText("PDF");
		option3Field.setText("GZ");
		option4Field.setText(" ");
	}

	// Writes the updated user settings to the properties file
	public void applySettings() {
		if (!(option1Field.getText().trim().equals(""))) {
			Props.propChange("L10N_String.filetype1",option1Field.getText());
		}
		if (!(option2Field.getText().trim().equals(""))) {
			Props.propChange("L10N_String.filetype2",option2Field.getText());
		}
		if (!(option3Field.getText().trim().equals(""))) {
			Props.propChange("L10N_String.filetype3",option3Field.getText());
		}
		Props.propChange("L10N_Config.DefaultDestination", option4Field.getText().trim());
	}
	
	public void updateIndexingOptions(boolean one, boolean two) {
		Props.propChange("L10N_Config.traverseAllDir", String.valueOf(one));
		Props.propChange("L10N_Config.indexCurrentDir", String.valueOf(two));
	}
	
	public void setController(ActionListener controller) {
		okSettingsBtn.addActionListener(controller);
		applySettingsBtn.addActionListener(controller);
		cancelSettingsBtn.addActionListener(controller);
		defaultSettingsBtn.addActionListener(controller);
	}

	public void setVisible(boolean b) {
		settingsFrame.setVisible(b);
	}
}