// PACKAGE DECLARATION
package common;

// IMPORT DECLARATIONS
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;


// APACHE LIBRARIES
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import code.Constants;

/** This class is meant to contain common utility methods for the applications classes.
 * @author ploh
 * @version 1.0
 */

public class Utils {

	/**
	 * Overrides the applications font property values
	 * @param f - the font parameters for the application
	 */
	public static void setUIFont (FontUIResource f) {
		Enumeration<Object> fontKeys = UIManager.getDefaults().keys();

		while (fontKeys.hasMoreElements()) {
			Object key = fontKeys.nextElement();
			Object value = UIManager.get (key);
			if (value != null && value instanceof FontUIResource) {
				UIManager.put (key, f);
			}
		}
	}

	/**
	 * Deletes a directory and all its contents
	 * @param file - The path of the directory to delete
	 */
	public static void deleteFile(File file) {
		if (!file.exists()) {
			return;
		} else if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				deleteFile(subFile);
			}
		}
		file.delete();
	}

	/**
	 * Checks if a particular directory exists
	 * @param dir - The path of the directory
	 * @return Returns true if the directory exists and false otherwise
	 */
	public static boolean isDir (File file) {
		if (file.exists() && file.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Execute the command given via the Windows Command Prompt
	 * @param commands - The command to execute
	 * @return The return code from the command executed
	 */
	public static String executeCommand(String command) throws Exception {
		StringBuffer outputBuffer = new StringBuffer();
		Process process = Runtime.getRuntime().exec(command.split("/"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String outputLine = "";			

		process.waitFor();
		if (process.exitValue()!=0) {
			return "Error in executing process:" + command + Constants.NEWLINE;
		}
		while ((outputLine = reader.readLine().trim())!= null) {
			outputBuffer.append(outputLine + Constants.NEWLINE);
		}
		return outputBuffer.toString();
	}

	/**
	 * Traverses through all directories contained in the root directory passed 
	 * and return a list containing all the files that match the specified extension
	 * @param rootDir - The rootDir which to traverse from
	 * @param extension - The files to grab with the particular extension
	 * @param fetchedFiles - The array list to populate
	 * @return the populated array list with files matching the specified extension
	 */
	public static ArrayList<File> fetchFilesFromAllDir(File rootDir, String extension, ArrayList <File> fetchedFiles) {
		if (rootDir.toString().endsWith(extension)) {
			fetchedFiles.add(rootDir);
		}
		if (rootDir.isDirectory()) {
			String[] subNodes = rootDir.list();
			for (String filename : subNodes) {
				fetchFilesFromAllDir(new File(rootDir, filename), extension, fetchedFiles);
			}
		}
		return fetchedFiles;
	}

	/**
	 * Filters the current directory for the specified file types 
	 * @param rootDir - The directory to look into for the files
	 * @param extension - The extension of the files to grab
	 * @return the populated array list with files matching the specified extension
	 */
	public static ArrayList<File> fetchFilesFromCurrentDir(File rootDir, String extension) {
		final String fileTypeExt = extension;
		File[] matchedFiles = null;

		// Filters the files in the specified directory by its extension
		FilenameFilter fileTypeFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(fileTypeExt)) {
					return true;
				} else {
					return false;
				}
			}
		};

		// Greedy Operator will copy all files in the specified directory with any sort of extension
		if (extension.equals(".*")) {
			matchedFiles = rootDir.listFiles();
		} else {
			matchedFiles = rootDir.listFiles(fileTypeFilter);
		}
		return (new ArrayList<File> (Arrays.asList(matchedFiles)));
	}


	/**
	 * The purpose of this method is to copy a file from the given source to its destination
	 * @param source
	 * @param destination
	 * @return true - if completed successfully and false if otherwise
	 * @throws IOException 
	 */
	public static boolean copyFile(File source, String destination) throws IOException {
		// Using Apache Commons IO to copy files as it allows preserving of file metadata
		FileUtils.copyFile(source, new File (String.format(destination+"\\%s", source.toString().substring(source.toString().lastIndexOf('\\') + 1))), true);
		return true; 
	}

	/**
	 * Unzips the file specified to the output Folder
	 * @param zipFile - The file to unzip
	 * @param outputFolder - The folder in which to place the extracted files
	 * @return the File Object of the unzipped folder
	 * @throws IOException - Signals that an I/O exception has occurred
	 */
	public static File unzipFile(File zipFile, String outputFolder) throws IOException {
		byte[] buffer = new byte[1024];
		ZipInputStream zis = null;
		ZipEntry ze = null;
		FileOutputStream fos = null;
		File outputZipFolder = new File(outputFolder, FilenameUtils.removeExtension(zipFile.getName()));

		/* Creates a directory for the zip extraction */
		if (!isDir(outputZipFolder)) {
			outputZipFolder.mkdir();
		}
		zis = new ZipInputStream(new FileInputStream(zipFile)); 	// Get the zip file content
		ze = zis.getNextEntry();									// Get the zipped file list entry
		while (ze!=null) {
			String fileName = ze.getName();
			File newFile = new File(outputZipFolder + File.separator + fileName);
			File tempFile = new File(newFile.getParent());
			tempFile.mkdirs();

			if (ze.isDirectory()) { 
				String temp = newFile.getCanonicalPath();
				new File(temp).mkdir();
			} else {
				fos = new FileOutputStream(newFile);             
				int len;
				while ((len = zis.read(buffer))>0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
			}
			zis.closeEntry();
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		fos.close();
		return outputZipFolder;
	}    
}