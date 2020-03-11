// PACKAGE DECLARATIONS
package model;

import java.util.List;
import java.util.Observable;

import code.FileImporter;
import static common.Props.prop;

/**
 * Model Component of the JS Converter
 * @author ploh
 *
 */
public class Model extends Observable {
	private ModelState value = null;
	private int progressValue;
	private String destination;
	private FileImporter fileImporter;
	private String errorMsg;
	
	/**
	 * Model Constructor
	 */
	public Model() {}
	
	/**
	 * Sets the Model State value 
	 * @param modelValue - Enumeration Type of the model state
	 */
	public void setObserverValue(ModelState modelValue) {
		this.value = modelValue;
	}

	/**
	 * Returns the current state of the Model
	 * @return the Enum Value of the Model State
	 */
	public ModelState getObserverValue() {
		return this.value;
	}
	
	/**
	 * Sets the progress value of the conversion execution process
	 * @param i - integer specifying the number of completed tasks
	 */
	public void setProgressValue(int i){
		this.progressValue = i;
	}
	
	/**
	 * Returns the current progress value of the conversion execution process
	 * @return Integer value spcefying the current value of the completed tasks
	 */
	public int getProgressValue(){
		return this.progressValue;
	}
	
	/**
	 * Sets the file destination of the conversion process
	 * @param destination - String representation of the destination location
	 */
	public void setFileDestination(String destination) {
		this.destination = destination;
	}
	
	/**
	 * Returns the specified destination of the conversion process
	 * @return String representation of the destination location
	 */
	public String getFileDestination() {
		return this.destination;
	}
	
	/**
	 * Stores the error message that occurred during the conversion process
	 * @param msg
	 */
	public void setErrorMsg(String msg){
		this.errorMsg = msg;
	}
	
	/**
	 * Returns the error message 
	 * @return Error message from the Exception
	 */
	public String getErrorMsg(){
		return this.errorMsg;
	}
	
	/**
	 * Notifies the View Component that an error was encountered during the conversion process
	 * @param e - The exception that was caught 
	 */
	public void displayError(Exception e) {
		setErrorMsg(e.getMessage());
		setObserverValue(ModelState.ERROR_ENCOUNTERED);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Notifies the View component that no files were processed
	 */
	public void noFilesProcessed() {
		setErrorMsg(prop("L10N_Message.NoFilesProcessed"));
		setObserverValue(ModelState.ERROR_ENCOUNTERED);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Notifies the View component that an invalid directory was processed
	 */
	public void invalidDirectory() {
		setObserverValue(ModelState.INVALID_DIRECTORY);
		setChanged();
		notifyObservers();
	}	
	
	/**
	 * Notifies the View component that the conversion process has been initiated
	 * and executes it using a SwingWorker
	 * @param serverFiles - The file directories in which to process the files
	 * @param filetypes - The file type formats to obtain
	 * @param extract - The option on whether to extract files or not
	 */
	public void conversionInitiated(List<String> serverFiles, List<String> filetypes, boolean extract) {
		setObserverValue(ModelState.CONVERSION_INITIATED);
		setChanged();
		notifyObservers();
		fileImporter = new FileImporter(this, serverFiles, filetypes, extract);
		fileImporter.execute();
	}
	
	/**
	 * Notifies the View component that the conversion process has completed
	 */
	public void conversionCompleted() {
		setObserverValue(ModelState.CONVERSION_COMPLETED);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Notifies the View component to update the progress bar value
	 */
	public void updateProgressBar() {
		setObserverValue(ModelState.UPDATE_PROG_BAR);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Notifies the View Component to update the target destination
	 */
	public void updateDestination() {
		setObserverValue(ModelState.UPDATE_DESTINATION);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Enumeration Types for the Model State
	 * @author ploh
	 */
	public enum ModelState { 
		CONVERSION_INITIATED(0), CONVERSION_COMPLETED(1),
		UPDATE_PROG_BAR(2), UPDATE_DESTINATION(3), INVALID_DIRECTORY(4), 
		ERROR_ENCOUNTERED (5);
		
	    private final int num;       

	    private ModelState(int i) {
	        num = i;
	    }
	    
	    public String toString() {
	       return Integer.toString(num);
	    }
	}
}	