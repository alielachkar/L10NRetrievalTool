package code;
import static common.Props.prop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import model.Model;
import common.Utils;

/**
 * Swing Worker for dealing with File Importing of Application
 * @author ploh
 */
public class FileImporter extends SwingWorker<Boolean,Integer> {
	private Model model;
	private List<String> serverFiles;
	private List<String> filetypes;
	private boolean extract;
	private int fileProcessed = 0;
	private int failedProcessed = 0;
	private int counter = 0;
	
	/* Initiates the copying process from the file location based 
	 * on the packaging types selected to the specified destination directory
	 */
	public FileImporter(Model model, List<String> serverFiles, List<String> filetypes, boolean extract) {
		this.model = model;
		this.serverFiles = serverFiles;
		this.filetypes = filetypes;
		this.extract = extract;
	}

	@Override
	protected void process(List<Integer> chunks) {
		int i = chunks.get(chunks.size()-1);
		model.setProgressValue(i);
		model.updateProgressBar();
	}
	
	/**
	 * Performs the file retrieval operation based on the users desired options
	 */
	@Override
	protected Boolean doInBackground() {
		boolean traverseAllDirs = Boolean.parseBoolean(prop("L10N_Config.traverseAllDir"));
		boolean indexCurrentDir = Boolean.parseBoolean(prop("L10N_Config.indexCurrentDir"));
		
		if (!Utils.isDir(new File(model.getFileDestination()))) {
			model.setFileDestination(System.getProperty("user.dir"));
			model.updateDestination();
		}
		
		for (String serverfile: serverFiles) {
			String trimmedFile = serverfile.trim();
			if (Utils.isDir(new File(trimmedFile))) {
				try {
					ArrayList<File> matchedFiles = new ArrayList<File>();
					for (String filetype : filetypes) {
						matchedFiles.clear();
						if (traverseAllDirs) {
							matchedFiles = Utils.fetchFilesFromAllDir(new File(trimmedFile), filetype.toLowerCase(), matchedFiles);
						} else if (indexCurrentDir) {
							matchedFiles = Utils.fetchFilesFromCurrentDir(new File(trimmedFile), filetype.toLowerCase());
						}
						
						for (File matchedFile: matchedFiles) {
							if (Utils.copyFile(matchedFile, model.getFileDestination())) {
								fileProcessed = fileProcessed + 1;
							}
							if (extract && matchedFile.toString().toLowerCase().endsWith("zip")) {
								Utils.unzipFile(matchedFile, model.getFileDestination());
							}
						}
					}
				} catch (IOException e) {
					model.displayError(e);
					failedProcessed = failedProcessed + 1;
				} 
			} else {
				model.invalidDirectory();
			}
			failedProcessed = failedProcessed + 1;
			counter = counter + 1;
			publish(counter);
		}
		return false;
	}

	@Override
	protected void done() {
		if (!(fileProcessed == 0)) {
			model.conversionCompleted();
		} else {
			model.noFilesProcessed();
		}
	}
}	