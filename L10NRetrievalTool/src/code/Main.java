package code;

import java.awt.EventQueue;
import java.io.IOException;

import view.View;
import common.AppLogger;
import controller.Controller;
import model.Model;

/**
 * Sets up the Model-View-Controller of the application. 
 * @author ploh
 *
 */
public class Main {
	
	public Main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppLogger.setup();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("Problems with creating the log files");
				}
				
				try {
					Model model = new Model();
					View view = new View();
					Controller controller = new Controller();
					
					model.addObserver(view);
					view.addModel(model);
					controller.addModel(model);
					controller.addView(view);
					view.setController(controller);
					view.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String [] args) {
		new Main();
	}
}
