// PACKAGE DECLARATIONS
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.View;
import model.Model;

/**
 * Controller Component of the JS Converter
 * @author ploh
 */
public class Controller implements ActionListener {

	private View view;
	private Model model;

	/**
	 * Controller Constructor
	 */
	public Controller() {}

	/**
	 * Initializes the View Component
	 * @param v - An instance of a view object
	 */
	public void addView(View v) {
		this.view = v;
	}

	/**
	 * Initializes the Model Component
	 * @param v - An instance of a model object
	 */
	public void addModel(Model m) {
		this.model = m;
	} 

	/**
	 * Handles all action events initiated by the view component
	 * @param e - the ActionEvent component
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("minimize")) {
			view.minimizeWindow();
		} else if (e.getActionCommand().equals("close")) {
			view.closeApplication();
		} else if (e.getActionCommand().equals("run")) {
			view.checkDestinationLocation();
			if (view.checkLinksPresent()) {
				model.setFileDestination(view.mainPanel.getDestinationFolder());
				model.conversionInitiated(view.mainPanel.getServerFiles(), view.mainPanel.getFileTypes(), view.mainPanel.getExtractSelected());
			}
		} else if (e.getActionCommand().equals("clear")) {
			view.clearServerLocations();
		} else if (e.getActionCommand().equals("log")) {
			view.openLogFile();
		} else if (e.getActionCommand().equals("otherFieldCheck")) {
			view.mainPanel.enableOtherField();
		} else if (e.getActionCommand().equals("browse")) {
			view.openFileBrowser();
		} else if (e.getActionCommand().equals("open")) {
			view.openDestinationFolder();
		} else if (e.getActionCommand().equals("openSettingsPanel")) {
			view.showSettingsPanel();
		} else if (e.getActionCommand().equals("openHelpPanel")) {
			view.showHelpPanel();
		} else if (e.getActionCommand().equals("okSettingsPanel")) {
			view.applySettings();
			view.closeSettingsPanel();
		} else if (e.getActionCommand().equals("applySettingsPanel")) {
			view.applySettings();
		} else if (e.getActionCommand().equals("cancelSettingsPanel")) {
			view.closeSettingsPanel();
		} else if (e.getActionCommand().equals("defaultSettingsBtn")) {
			view.resetSettings();
		}
	}
}
