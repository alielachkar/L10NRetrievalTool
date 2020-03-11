==========================================
LOCALIZATION RETRIEVAL TOOL DOCUMENTATION
==========================================

Summary
=======
-   This tool is meant to replace the use of *.bat files in order to facilitate the process of 
    obtaining packages from a particular server location. This tool enables users to drag and drop
    paths and choose the particular files extensions that they would like to obtain.

Features
========
-   Copying files from a server location to a particular directory
-   Extracting ZIP Files from their compressed state to a particular directory
-   Generating similar branches of server locations (Under-DEV)

Pre-requisites
==================
1. Ensure that a JRE is running on the system and Environment Paths are well defined. (JRE 6 and above)
2. Location of the files to be copied are indeed valid and are accessible

Architecture
=============
- This tool was developed with backward compatibility of up to Java 6 Runtime Environments. 
- Runs on java library and Apache Commons IO 2.4.

How to use the Retrieval Tool
=============================
1. Specify the locations of the directories which contain the files in the "Server Locations" text field 
   and place them each on its own line. 
2. Specify a directory for the destination of where the file should be placed. 
3. Ensure that the relevant file type check boxes which are to be copied are selected. 
4. Should the specific extension be needed, populate the *.ext field and select its respective check box.
   - Note: More than one file type can be selected.
5. Hit Run to initiate the copying process. 
6. The process will execute and the user will be notified in the log screen when the process has been completed.
7. Click the "Open" button to display an explorer window of the location of the files. 

Buttons Guide
==============
1. Browse - Displays a file browser window to select the destination of the files which are to be copied
2. Open - Displays an explorer window to the destination directory
3. Run - Initiates the copying process
4. Clear - Clears the server location text field
5. Log - Open the log file of the tool with the systems default text viewer
6. Generate - When a directory is placed in the server location window, clicking the generate button would append similar language branches in the server location field
    
File types
==========
-   Predefined File types 
    * ZIP 
    * PDF 
    * GZ (Gunzip)
    
-   Custom File types
    * Users are able to enter any file type extension/keyword in the "*.ext" field. If a blank
      field is submitted, the program will ignore the extension field when processing the command.
    * Users can use this field to process all files from a particular directory by entering the 
      following text in the menu ".*" (without quotations)
    * Using the custom field allows a user to distinguish same file types by their name
      E.g. A directory containing ZIP files contains files ending with ".lhp.zip" and ".zip"
      In the event that the user would like to have only the ".lhp.zip" file, using the custom field 
      would allow this to be achieved.

Indexing Options
================
- This tool allows the user to select 2 different types of indexing options
    * Current Directory
        - This option tells the tool to search for files with the extension in the directory specified. Sub-folders in this directory will NOT be traversed for containing the extension.
    * Entire Directory
        - This option would traverse the directory and its sub-folders to identify all files with the given extension. 
        
- NOTE: Be aware that when using the "Entire Directory" option, the time taken may/may not be increased by a substantial amount
        
Settings Panel
===============
- The settings panel allows the user to customize settings which would be saved upon subsequent launches of the tool. 
   Users are able to define:
    - Predefined file type extensions
    - The default destination of where the files are to be copied
    - The indexing options of the tool

- To save the user settings, hit Apply/OK. 

Extracting Functionality
========================
- Currently the tool only allows extracting of *.zip files
- Files with a 2 level file compression will not be completely extracted.
    - i.e. files with "tar.zip" will only result in an extraction from its ZIP state
    
Known Limitations/Issues
=========================
- Generate functionality is sluggish and may/may not work as expected for all branch types. 
    
QUESTIONS/COMMENTS/BUG FIXES
=============================
- Please do not hesitate to send a mail to ploh@opentext.com / Call +1-613-238-1761
- HAVE FUN! :)

