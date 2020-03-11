// PACKAGE DECLARATIONS
package view;

// JAVA LIBRARIES
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import code.Constants;
import code.HelpPanel;
import code.SettingsPanel;
import model.Model;
import model.Model.ModelState;
// PACKAGE IMPORTS
import static common.Props.prop;
import common.Utils;

/**
 * Features
 *	- Importing files from Server locations to a specific directory
 *	- Extracting Files from their compressed state (ZIP)
 *	- Logging of progress
 * 	- Generating of branch names
 * @author ploh
 *
 */
public class View implements Observer {
	public MainWindow mainPanel;
	public SettingsPanel settingsPanel;
	private HelpPanel helpPanel = null;
	protected JFrame frmLnTestingTool;
	private JMenuBar menuBar; 
	private AppButton settingsMenu;
	private AppButton helpMenu;
	private AppButton closeBtn;
	private AppButton minimizeBtn;
	private ActionListener actionListener;
	private Model model;
	private int pX, pY = 0;
	private boolean settingsPanelLaunched = false;
	private boolean helpPanelLaunched = false;

	/**
	 * View Constructor
	 */
	public View() {
		setUIColorDark();
//		setUIColorLight();
		initialize();
	}

	/**
	 * @return The JFrame component of the application
	 */
	public JFrame getFrame() {
		return this.frmLnTestingTool;
	}

	/**
	 * Initializes the UI Component of the Application
	 */
	private void initialize() {
		Utils.setUIFont(Constants.BASEFONT);
		frmLnTestingTool = new JFrame();
		frmLnTestingTool.setUndecorated(true);
		frmLnTestingTool.setTitle(prop("L10N_String.Title"));
		frmLnTestingTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmLnTestingTool.setJMenuBar(createMenuBar());
		mainPanel = new MainWindow();
		frmLnTestingTool.getContentPane().add(mainPanel.mainPanel, BorderLayout.CENTER);
		ComponentResizer cr = new ComponentResizer();
		cr.registerComponent(frmLnTestingTool);
		cr.setSnapSize(new Dimension(10, 10));
		frmLnTestingTool.pack();
		frmLnTestingTool.setLocationRelativeTo(null);
	}

	/**
	 * Creates the Menu Bar of the Main Window
	 * @return The initialized JMenuBar component
	 */
	public JMenuBar createMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setLayout(new BorderLayout());
		menuBar.setBorder(new CompoundBorder(new LineBorder(Constants.DARKGRAY,1), Constants.EMPTY5PXBORDER));
		menuBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// Get x,y and store them
				pX= me.getX();
				pY= me.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				frmLnTestingTool.setLocation(frmLnTestingTool.getLocation().x+me.getX()-pX,frmLnTestingTool.getLocation().y+me.getY()-pY);
			}
		});
		settingsMenu = new AppButton(prop("L10N_String.Settings"), Constants.SETTINGSICONSMALL, SwingConstants.TRAILING);
		settingsMenu.setActionCommand("openSettingsPanel");
		helpMenu = new AppButton(prop("L10N_String.Help"), Constants.HELPICONSMALL, SwingConstants.TRAILING);
		helpMenu.setActionCommand("openHelpPanel");
		closeBtn = new AppButton(Constants.CLOSEICON);
		closeBtn.setActionCommand("close");
		minimizeBtn = new AppButton(Constants.MINIMIZEICON);
		minimizeBtn.setActionCommand("minimize");
		JPanel frameBtns = new JPanel();
		frameBtns.setLayout(new GridLayout(1,3, 5,0));
		frameBtns.add(minimizeBtn);
		frameBtns.add(closeBtn);
		frameBtns.setOpaque(false);
		JPanel shortcuts = new JPanel();
		shortcuts.setLayout(new GridLayout(1,2));
		shortcuts.add(settingsMenu);
		shortcuts.add(helpMenu);
		menuBar.add(shortcuts, BorderLayout.WEST);
		menuBar.add(frameBtns, BorderLayout.EAST);
		return menuBar;
	}

	/**
	 * Adds a model reference to the View Component
	 * @param m - the model component to add
	 */
	public void addModel(Model m) {
		this.model = m;
	}

	/**
	 * Sets the Action Listener of the applications buttons
	 * @param controller - the action listener component
	 */
	public void setController(ActionListener controller) {
		this.actionListener = controller;
		settingsMenu.addActionListener(controller);
		helpMenu.addActionListener(controller);
		closeBtn.addActionListener(controller);
		minimizeBtn.addActionListener(controller);
		mainPanel.setController(controller);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (model.getObserverValue().equals(ModelState.CONVERSION_INITIATED)) {
			mainPanel.initProgressBar();
			mainPanel.disableButtons();
			mainPanel.progressBar.setValue(0);
			mainPanel.progressBar.setMaximum(mainPanel.getServerFiles().size());
			MainWindow.logger.info(mainPanel.getServerFiles().toString());
		} else if (model.getObserverValue().equals(ModelState.CONVERSION_COMPLETED)) {
			mainPanel.enableButtons();
			mainPanel.display(prop("L10N_Message.OperationComplete"), Level.INFO);
		} else if (model.getObserverValue().equals(ModelState.UPDATE_PROG_BAR)) {
			mainPanel.progressBar.setValue(model.getProgressValue());
		} else if (model.getObserverValue().equals(ModelState.UPDATE_DESTINATION)) {
			mainPanel.display(prop("L10N_Message.InvalidDirectorySpecified"), Level.WARNING);
			mainPanel.setDestinationField(model.getFileDestination());
		} else if (model.getObserverValue().equals(ModelState.INVALID_DIRECTORY)){
			mainPanel.display(prop("L10N_Message.ServerDirectoryInvalid"), Level.WARNING);
		} else if (model.getObserverValue().equals(ModelState.ERROR_ENCOUNTERED)){
			mainPanel.display(model.getErrorMsg(), Level.SEVERE);
			displayNotification("Error Encountered", model.getErrorMsg(), JOptionPane.WARNING_MESSAGE);
			mainPanel.enableButtons();
		}
	}

	/**
	 * Sets the visibility of the Application Frame
	 * @param visbility - Sets the status of the frame
	 */
	public void setVisible(boolean visbility) {
		getFrame().setVisible(visbility);
	}
	
	/**
	 * Minimizes the application JFrame
	 */
	public void minimizeWindow(){
		getFrame().setState(Frame.ICONIFIED);
	}
	
	/**
	 * Shuts down the application
	 */
	public void closeApplication(){
		System.exit(0);
	}

	/**
	 * Clears the server JTextArea
	 */
	public void clearServerLocations(){
		if (mainPanel.getTextArea().isEditable()) {
			mainPanel.getTextArea().setText("");
		}
	}

	/**
	 * Launches the JFileChooser
	 */
	public void openFileBrowser(){
		mainPanel.openFileBrowser();
	}

	/**
	 * Validates the conversation destination location
	 */
	public void checkDestinationLocation(){
		if (mainPanel.getDestinationFolder().equals("")) {
			mainPanel.display(prop("L10N_Message.InvalidDirectorySpecified"), Level.WARNING);
			mainPanel.setDestinationField(System.getProperty("user.dir"));
		} else {
			mainPanel.display(mainPanel.getDestinationFolder(), Level.INFO);
		}
	}

	/**
	 * Sets the UI to a dark theme
	 */
	public void setUIColorDark() {
		UIManager.put("Panel.background", Constants.DARKGRAY);
		UIManager.put("Panel.foreground", Constants.WHITE);
		UIManager.put("TextField.background", Constants.WHITE);
		UIManager.put("TextField.foreground", Constants.DARKGRAY);
		UIManager.put("Label.foreground", Constants.WHITE);
		UIManager.put("Label.background", Constants.DARKGRAY);
		UIManager.put("CheckBox.background", Constants.DARKGRAY);
		UIManager.put("CheckBox.foreground", Constants.WHITE);
		UIManager.put("InternalFrame.background", Constants.DARKGRAY);
		UIManager.put("OptionPane.background", Constants.DARKGRAY);
		UIManager.put("OptionPane.foreground", Constants.WHITE);
		UIManager.put("OptionPane.messageForeground", Constants.WHITE);
		UIManager.put("ProgressBar.background", Constants.DARKGRAY);
		UIManager.put("ProgressBar.foreground", Constants.GREEN);
		UIManager.put("MenuBar.background", Constants.LIGHTGRAY);
		UIManager.put("MenuBar.foreground", Constants.WHITE);
	}

	/**
	 * Sets the UI to a light theme
	 */
	public void setUIColorLight() {
		UIManager.put("Panel.background", Constants.WHITE);
		UIManager.put("Panel.foreground", Constants.DARKGRAY);
		UIManager.put("TextField.background", Constants.WHITE);
		UIManager.put("TextField.foreground", Constants.DARKGRAY);
		UIManager.put("Label.foreground", Constants.DARKGRAY);
		UIManager.put("Label.background", Constants.WHITE);
		UIManager.put("CheckBox.background", Constants.WHITE);
		UIManager.put("CheckBox.foreground", Constants.DARKGRAY);
		UIManager.put("InternalFrame.background", Constants.WHITE);
		UIManager.put("OptionPane.background", Constants.WHITE);
		UIManager.put("OptionPane.foreground", Constants.DARKGRAY);
		UIManager.put("OptionPane.messageForeground", Constants.DARKGRAY);
		UIManager.put("ProgressBar.background", Constants.WHITE);
		UIManager.put("ProgressBar.foreground", Constants.GREEN);
		UIManager.put("MenuBar.background", Constants.LIGHTGRAY);
		UIManager.put("MenuBar.foreground", Constants.WHITE);
	}

	/**
	 * Displays a message dialog with a specific priority to the user 
	 * @param message - the message to display
	 * @param value - the integer value of the message type
	 */
	public void displayNotification(String title, String message, int value) {
		final JDialog dialog = new JDialog(getFrame(), "");
		JOptionPane op = new JOptionPane(message, value);
		JLabel headerPanel = new JLabel(title);
		JPanel bodyPanel = new JPanel();

		op.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if ("value".equals(name)) {
					dialog.dispose();
				}
			}
		});
		
		headerPanel.setBorder(Constants.BORDERCONSTANT);
		headerPanel.setBackground(Constants.LIGHTGRAY);
		headerPanel.setOpaque(true);
		bodyPanel.add(op);
		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());
		dialog.add(headerPanel, BorderLayout.NORTH);
		dialog.add(bodyPanel, BorderLayout.CENTER);
		dialog.pack();
		dialog.setLocationRelativeTo(getFrame());
		dialog.setVisible(true);
	}

	/**
	 * Launches the settings Panel of the application
	 */
	public void showSettingsPanel(){
		if (!settingsPanelLaunched && (settingsPanel==null)) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						settingsPanel = new SettingsPanel();
						settingsPanel.updatePanelLoc(getFrame().getLocation());
						settingsPanel.setController(actionListener);
						settingsPanel.settingsFrame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						settingsPanelLaunched = true;
					}
				}
			});
		} else {
			settingsPanel.updatePanelLoc(getFrame().getLocation());
			settingsPanel.settingsFrame.setVisible(true);
			settingsPanel.loadSettings();
		}
	}

	/**
	 * Launches the help panel of the application
	 */
	public void showHelpPanel(){
		if (!helpPanelLaunched && (helpPanel==null)) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						helpPanel = new HelpPanel();
						helpPanel.updatePanelLoc(getFrame().getLocation());
						helpPanel.helpFrame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						helpPanelLaunched = true;
					}
				}
			});
		} else {
			helpPanel.helpFrame.setVisible(true);
		}
	}

	/**
	 * Saves the users desired settings of the Settings Panel
	 */
	public void applySettings(){
		settingsPanel.applySettings();
		settingsPanel.updateIndexingOptions(settingsPanel.traverseAllDir.isSelected(), settingsPanel.indexCurrentDir.isSelected());
		mainPanel.reinitializeUIString();
	}

	/**
	 * Hides the settings panel
	 */
	public void closeSettingsPanel(){
		settingsPanel.setVisible(false);
	}

	/**
	 * Restores the default settings of the application
	 */
	public void resetSettings(){
		settingsPanel.resetAllSettings();
	}

	/**
	 * Checks if there is any text in the JTextArea
	 * NOTE: Does not validate the links
	 * @return boolean status of the presence of text in the JTextArea
	 */
	public boolean checkLinksPresent(){
		if (mainPanel.getTextArea().getText().equals("")) {
			displayNotification(prop("L10N_Message.InvalidDestination"),prop("L10N_Message.ServerDirectoryInvalid"),JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Displays the destination folder chosen using the OS native application file handler
	 */
	public void openDestinationFolder(){
		if (!(mainPanel.getDestinationFolder().trim().equals(""))) {
			File dirfile = new File (mainPanel.getDestinationFolder());
			if (Utils.isDir(dirfile)) {
				try {
					Desktop.getDesktop().open(dirfile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(getFrame(),
						prop("L10N_Message.InvalidDestinationMessage"),
						prop("L10N_Message.InvalidDestination"),
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(getFrame(),
					prop("L10N_Message.InvalidDestinationMessage"),
					prop("L10N_Message.InvalidDestination"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Displays the log file using the OS native application file handler
	 */
	public void openLogFile(){
		MainWindow.logger.info(prop("L10N_Message.OpenLogFile"));
		try {
			Desktop.getDesktop().open(new File("Logging.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}