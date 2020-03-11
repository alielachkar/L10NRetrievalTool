package view;

import static common.Props.prop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultEditorKit;

import code.Constants;

/**
 * Main Components of UI Interface of Application
 * @author ploh
 *
 */
public class MainWindow extends JPanel {
	private static final long serialVersionUID = 7148341317928667262L;
	
	public final static Logger logger = Logger.getLogger(MainWindow.class.getName());
	public JPanel mainPanel;
	public JProgressBar progressBar;
	
	private JPanel panelHeader;
	
	private JTextField dirField;
	private JCheckBox extractBox;
	
	private AppButton browseBtn;
	private AppButton openBtn;
	private AppButton runBtn;
	private AppButton clearBtn;
	private AppButton logBtn;
	
	private AppCheckBox file1Btn;
	private AppCheckBox file2Btn;
	private AppCheckBox file3Btn;
	private AppCheckBox otherFieldEnabled;
	
	private JTextField otherField;
	private JTextArea logtextArea;
	private JTextArea servertextArea;
	
	public MainWindow() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new CompoundBorder(Constants.EMPTY5PXBORDER, new LineBorder(Constants.LIGHTGRAY,1,true)));
		mainPanel.add(createRightPanel(), BorderLayout.EAST);
		mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);
	}

	private JPanel createHeaderPanel() {
		panelHeader = new JPanel();
		panelHeader.setBorder(new EmptyBorder(5,5,10,5));
		panelHeader.setLayout(new BorderLayout());

		AppLabel destinationLabel = new AppLabel(prop("L10N_String.DestinationDir"));
		dirField = new JTextField(prop("L10N_Config.DefaultDestination"));
		
		browseBtn = new AppButton(prop("L10N_String.Browse"));
		browseBtn.setActionCommand("browse");
		openBtn = new AppButton(prop("L10N_String.Open"));
		openBtn.setActionCommand("open");
		extractBox = new AppCheckBox(prop("L10N_String.Extract"));
		extractBox.setBackground(Constants.DARKGRAY);
		//extractBox.setBackground(Constants.WHITE);
		
		JPanel fileBrowserBtns = new JPanel();
		fileBrowserBtns.setLayout(new GridLayout(1,2));
		fileBrowserBtns.add(browseBtn);
		fileBrowserBtns.add(openBtn);
		
		JPanel filebrowser = new JPanel();
		filebrowser.setLayout(new BorderLayout());
		filebrowser.setBorder(Constants.EMPTY5PXBORDER);
		filebrowser.add(dirField, BorderLayout.CENTER);
		filebrowser.add(fileBrowserBtns, BorderLayout.EAST);
		
		panelHeader.add(destinationLabel, BorderLayout.WEST);
		panelHeader.add(filebrowser, BorderLayout.CENTER);
		panelHeader.add(extractBox, BorderLayout.EAST);
		return panelHeader;
	}
	
	public void initProgressBar(){ 
		this.progressBar.setStringPainted(true);
		this.progressBar.setIndeterminate(false);
	}
	
	private JPanel createCenterPanel() {
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());
		panelCenter.setBorder(Constants.EMPTY5PXBORDER);

		JPanel styxPanel = new JPanel();
		styxPanel.setLayout(new BorderLayout());

		AppLabel styxPanelTitle = new AppLabel(prop("L10N_String.ServerLoc"));
		styxPanel.add(styxPanelTitle, BorderLayout.NORTH);

		servertextArea = new JTextArea();
		servertextArea.setEditable(true);
		servertextArea.addMouseListener(new MouseAdapter() {
			public void mouseReleased(final MouseEvent e) {
				if (e.isPopupTrigger()) {
					final JPopupMenu menu = new JPopupMenu();
					JMenuItem item = new JMenuItem(new DefaultEditorKit.CopyAction());
					item.setText("Copy");
					item.setEnabled(servertextArea.getSelectionStart() != servertextArea.getSelectionEnd());
					JMenuItem item2 = new JMenuItem(new DefaultEditorKit.PasteAction());
					item2.setText("Paste");
					menu.add(item);
					menu.add(item2);
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		servertextArea.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = -2818708818900569751L;

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						servertextArea.append(file.getAbsolutePath() + Constants.NEWLINE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error when trying to import file links!", "Notification", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JScrollPane scrollPaneStyx = new JScrollPane(servertextArea);
		scrollPaneStyx.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneStyx.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneStyx.setPreferredSize(new Dimension(500, 275));
		styxPanel.add(scrollPaneStyx, BorderLayout.CENTER);

		JPanel logPanel = new JPanel();
		logPanel.setLayout(new BorderLayout());
		
		AppLabel logPanelTitle = new AppLabel(prop("L10N_String.LogStatus"));
		logPanel.add(logPanelTitle, BorderLayout.NORTH);

		logtextArea = new JTextArea();
		logtextArea.setEditable(false);

		DefaultCaret caret = (DefaultCaret)logtextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane(logtextArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(500, 120));
		logPanel.add(scrollPane, BorderLayout.CENTER);
		
		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, styxPanel, logPanel);
		mainSplitPane.setEnabled(true);
		mainSplitPane.setDividerSize(3);
		
		panelCenter.add(mainSplitPane, BorderLayout.CENTER);
		return panelCenter;
	}
	
	private JPanel createRightPanel() {
		JPanel panelRight = new JPanel();
		panelRight.setBorder(new EmptyBorder(5,10,5,10));
		panelRight.setPreferredSize(new Dimension(120, 10));
		panelRight.setLayout(new GridLayout(14, 1, 0, 5));
		
		AppLabel fileTypeLabel = new AppLabel(prop("L10N_String.Filetype"));
		AppLabel commandsLabel = new AppLabel(prop("L10N_String.Commands"));
		
		file1Btn = new AppCheckBox(prop("L10N_String.filetype1"));
		file2Btn = new AppCheckBox(prop("L10N_String.filetype2"));
		file3Btn = new AppCheckBox(prop("L10N_String.filetype3"));
		file1Btn.setSelected(true);
		
		otherField = new JTextField();
		otherField.setText(prop("L10N_String.Extension"));
		otherField.setEnabled(false);
		
		otherFieldEnabled = new AppCheckBox();
		otherFieldEnabled.setActionCommand("otherFieldCheck");
		
		JPanel otherFieldPanel = new JPanel();
		otherFieldPanel.setLayout(new BorderLayout());	
		otherFieldPanel.add(otherFieldEnabled, BorderLayout.WEST);
		otherFieldPanel.add(otherField);

		runBtn = new AppButton(prop("L10N_String.Run"));
		clearBtn = new AppButton(prop("L10N_String.Clear"));
		logBtn = new AppButton(prop("L10N_String.Log"));
		runBtn.setActionCommand("run");
		clearBtn.setActionCommand("clear");
		logBtn.setActionCommand("log");
		
		panelRight.add(fileTypeLabel);
		panelRight.add(file1Btn);
		panelRight.add(file2Btn);
		panelRight.add(file3Btn);
		panelRight.add(otherFieldPanel);
		panelRight.add(new JLabel());
		panelRight.add(commandsLabel);
		panelRight.add(runBtn);
		panelRight.add(clearBtn);
		panelRight.add(logBtn);
		return panelRight;
	}
	
	private JPanel createFooterPanel() {
		progressBar = new JProgressBar();
		progressBar.setBorder(new EmptyBorder(10,5,5,5));
		progressBar.setVisible(true);
		
		JPanel panelFooter = new JPanel();
		panelFooter.setLayout(new BorderLayout());
		panelFooter.setBorder(Constants.EMPTY5PXBORDER);
		panelFooter.add(progressBar, BorderLayout.WEST);
		
		JLabel logoLabel = new JLabel(Constants.LOGOICON);
		
		panelFooter.add(logoLabel, BorderLayout.EAST);
		return panelFooter;
	}
	
	// Sets the controller for the buttons
	public void setController(ActionListener controller) {
		runBtn.addActionListener(controller);
		clearBtn.addActionListener(controller);
		logBtn.addActionListener(controller);
		browseBtn.addActionListener(controller);
		openBtn.addActionListener(controller);
		otherFieldEnabled.addActionListener(controller);
	}
	
	// Launches the JFileChooser to specify the destination directory
	public void browseContent() {
		final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showDialog(panelHeader, prop("L10N_String.Select"));
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			setDestinationField(file.getAbsolutePath());
		}
	}

	// Refreshes the UI Components to reload the data from the properties file of the buttons
	public void reinitializeUIString() {
		file1Btn.setText(prop("L10N_String.filetype1"));
		file2Btn.setText(prop("L10N_String.filetype2"));
		file3Btn.setText(prop("L10N_String.filetype3"));
		dirField.setText(prop("L10N_Config.DefaultDestination"));
	}
	
	// This method is called to write the text to the UI Interface and the Logger
	public void display(String message, Level level ) {
		logtextArea.append(message + Constants.NEWLINE);
		logger.log(level, message);
	}
	
	/**
	 * Fetches the server files that need to be searched
	 * @return list of the server locations
	 */
	public List<String> getServerFiles() {
		// Splits the files at each newline
		List<String> serverFiles = Arrays.asList(servertextArea.getText().split("\n"));
		return serverFiles;
	}
	
	/**
	 * Fetches the filetypes that need to be converted
	 * @return list of the filetypes to search from the directories
	 */
	public List<String> getFileTypes() {
		List<String> filetypes = new ArrayList<String>();
		if (file1Btn.isSelected()) {
			filetypes.add(file1Btn.getText());
		}
		if (file2Btn.isSelected()) {
			filetypes.add(file2Btn.getText());
		}
		if (file3Btn.isSelected()) {
			filetypes.add(file3Btn.getText());
		}
		if (getOtherSelected()) {
			if(!((otherField.getText()).equals(prop("L10N_String.Extension")) || (otherField.getText().trim().equals(""))) && otherFieldEnabled.isSelected()){
				filetypes.add(otherField.getText());
			}
		}
		return filetypes;
	}
	
	public boolean getExtractSelected() {
		return (extractBox.isSelected());
	}
	
	public boolean getOtherSelected() {
		return otherFieldEnabled.isSelected();
	}
	
	public void enableButtons() {
		// Enables and grays out the styx field
		servertextArea.setEditable(true);
		servertextArea.setEnabled(true);

		// Enables common buttons
		clearBtn.setEnabled(true);
		runBtn.setEnabled(true);
		browseBtn.setEnabled(true);
		
		// Enables changing of the directory and notifies the user of the location
		dirField.setEditable(true);

		// Unlocks all platform buttons
		file1Btn.setEnabled(true);
		file2Btn.setEnabled(true);
		file3Btn.setEnabled(true);
	}
	
	public void disableButtons() {
		// Disables and greys out the styx field
		servertextArea.setEditable(false);
		servertextArea.setEnabled(false);

		// Disables common buttons
		browseBtn.setEnabled(false);
		clearBtn.setEnabled(false);
		runBtn.setEnabled(false);

		// Disables changing of the directory and notifies the user of the location
		dirField.setEditable(false);

		// Locks all platform buttons
		file1Btn.setEnabled(false);
		file2Btn.setEnabled(false);
		file3Btn.setEnabled(false);
	}
	
	public void setDestinationField(String destination) {
		dirField.setText(destination);
	}
	
	public String getDestinationFolder() {
		return dirField.getText();
	}
	
	public JTextArea getTextArea() {
		return this.servertextArea;
	}
	
	public void enableOtherField() {
		if (!otherFieldEnabled.isSelected()) {
			otherField.setEnabled(false);
		} else {
			otherField.setEnabled(true);
		}
	}
	
	public void openFileBrowser() {
		browseBtn.setEnabled(false);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				browseContent();
				browseBtn.setEnabled(true);
			}
		});
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
}
