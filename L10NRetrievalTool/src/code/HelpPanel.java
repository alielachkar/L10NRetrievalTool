package code;

import static common.Props.prop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import view.AppButton;
import view.View;

/**
 * Help Panel for UI Application
 * @author ploh
 *
 */
public class HelpPanel {
	public JFrame helpFrame;
	private JTextArea helptextArea;
	private JLabel helpPageHeader;
	private int pX2, pY2 = 0;
	private JMenuBar helpMenu;

	public HelpPanel() {
		initialize();
	}

	private void initialize() {
		ImageIcon helpIcon = new ImageIcon(View.class.getResource("images/help.png"));
		
		helpFrame = new JFrame();
		helpFrame.setUndecorated(true);
		helpFrame.setResizable(false);
		helpFrame.setAlwaysOnTop(true);
		helpFrame.setTitle(prop("L10N_String.Help"));

		helpFrame.setJMenuBar(createMenuBar());

		JPanel helpMainPanel = new JPanel();
		helpMainPanel.setLayout(new BorderLayout());
		helpFrame.add(helpMainPanel);
		helpMainPanel.setBorder(Constants.EMPTY5PXBORDER);

		JPanel helpHeader = new JPanel();
		helpHeader.setLayout(new BorderLayout());
		helpMainPanel.add(helpHeader, BorderLayout.NORTH);
		helpHeader.setBorder(Constants.EMPTY5PXBORDER);

		JLabel helpTitle = new JLabel(prop("L10N_String.Help"), helpIcon, SwingConstants.TRAILING);
		helpHeader.add(helpTitle, BorderLayout.WEST);
		helpHeader.setBackground(Constants.DARKBLUE);
		helpTitle.setForeground(Constants.WHITE);
		helpTitle.setFont(Constants.TITLEFONT);

		JPanel helpTable = new JPanel();
		helpTable.setLayout(new GridLayout(6,2));
		helpMainPanel.add(helpTable, BorderLayout.CENTER);
		helpTable.setBorder(Constants.EMPTY5PXBORDER);
		
		JPanel helpTreeDir = new JPanel();
		JPanel helpDisplay = new JPanel();
		
		// Create the root node, I'm assuming that the delimited strings will have
		// different string value at index 0
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Tool Documentation");

		// Create the tree model and add the root node to it
		DefaultTreeModel model = new DefaultTreeModel(root);

		// Create the tree with the new model
		final JTree tree = new JTree(model);
		
		// Build the tree from the various string samples
		buildTreeFromString(model, "Retrieval Tool Help/Summary");
		buildTreeFromString(model, "Retrieval Tool Help/Features");
		buildTreeFromString(model, "Retrieval Tool Help/Pre-Requisites");
		buildTreeFromString(model, "Retrieval Tool Help/Architecture");
		buildTreeFromString(model, "Retrieval Tool Help/Using the Retrieval Tool");
		buildTreeFromString(model, "Retrieval Tool Help/Buttons Guide");
		buildTreeFromString(model, "Retrieval Tool Help/File Types");
		buildTreeFromString(model, "Retrieval Tool Help/Indexing Options");
		buildTreeFromString(model, "Retrieval Tool Help/Settings Panel");
		buildTreeFromString(model, "Retrieval Tool Help/Extraction Functionality");	
		buildTreeFromString(model, "Retrieval Tool Help/Bug Reporting");
		
		final JScrollPane scrollPane2 = new JScrollPane(tree);
		
		helpTreeDir.setLayout(new BorderLayout());
		helpTreeDir.add(scrollPane2, BorderLayout.CENTER);
		helpTreeDir.setOpaque(true);
		
		JSplitPane helpSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, helpTreeDir, helpDisplay);
		helpSplitPane.setBorder(Constants.EMPTY5PXBORDER);
		helpSplitPane.setDividerLocation(250);
		helpSplitPane.setEnabled(true);
		helpSplitPane.setDividerSize(3);
		helpMainPanel.add(helpSplitPane);
		
		helpDisplay.setOpaque(true);
		helpDisplay.setLayout(new BorderLayout());

		helptextArea = new JTextArea();
		helptextArea.setEditable(false);
		helptextArea.setLineWrap(true);
		helptextArea.setWrapStyleWord(true);

		DefaultCaret caret = (DefaultCaret)helptextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		// Caret is not updating to the last line of the file

		JScrollPane scrollPane = new JScrollPane(helptextArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		helpDisplay.add(scrollPane, BorderLayout.CENTER);
		
		helpPageHeader = new JLabel();
		helpPageHeader.setText(prop("L10N_String.Help"));
		helpDisplay.add(helpPageHeader, BorderLayout.NORTH);
		
		
		// Selection Listeners for leafs of the Help Tree Directory Structure to load Respective Help Segments
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (tree.getSelectionPath().getLastPathComponent().toString().equals("Summary")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.Summary"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Features")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.Features"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Pre-Requisites")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.PreReq"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Architecture")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.Architecture"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Using the Retrieval Tool")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.UsingTheTool"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Buttons Guide")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.ButtonsGuide"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("File Types")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.Filetypes"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Indexing Options")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.Indexing"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Settings Panel")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.SettingsPanel"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Extraction Functionality")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.Extraction"));
				} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("Bug Reporting")) {
					helpPageHeader.setText(tree.getSelectionPath().getLastPathComponent().toString());
					helptextArea.setText(prop("L10N_Help.BugReporting"));
				}
			}
		});
		
		helpFrame.pack();
		helpFrame.setVisible(true);
	}

	private JMenuBar createMenuBar(){
		helpMenu = new JMenuBar();
		helpMenu.setBorder(Constants.EMPTY5PXBORDER);
		helpMenu.setLayout(new BorderLayout());
		helpFrame.setJMenuBar(helpMenu);

		helpMenu.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// Get x,y and store them
				pX2= me.getX();
				pY2= me.getY();
			}
		});

		// Add MouseMotionListener for detecting drag
		helpMenu.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				helpFrame.setLocation(helpFrame.getLocation().x+me.getX()-pX2,helpFrame.getLocation().y+me.getY()-pY2);
			}
		});

		AppButton closeBtn = new AppButton(Constants.CLOSEICON);
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//settingsPanelLaunched = false;
				helpFrame.setVisible(false);
			}
		});	

		JPanel frameBtns = new JPanel();
		frameBtns.setLayout(new GridLayout(1,3));
		frameBtns.add(closeBtn);
		frameBtns.setOpaque(false);
		helpMenu.add(frameBtns, BorderLayout.EAST);
		
		return helpMenu;
	}
	
	public void updatePanelLoc(Point loc) {
		int x = (int) (loc.getX() + 40);
		int y = (int) (loc.getY() + 120);
		helpFrame.setLocation(x,y);
	}
	
	private static void buildTreeFromString(final DefaultTreeModel model, final String str) {
		// Fetch the root node
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

		// Split the string around the delimiter
		String [] strings = str.split("/");

		// Create a node object to use for traversing down the tree as it 
		// is being created
		DefaultMutableTreeNode node = root;

		// Iterate of the string array
		for (String s: strings) {
			// Look for the index of a node at the current level that
			// has a value equal to the current string
			int index = childIndex(node, s);

			// Index less than 0, this is a new node not currently present on the tree
			if (index < 0) {
				// Add the new node
				DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(s);
				node.insert(newChild, node.getChildCount());
				node = newChild;
			} else {
				node = (DefaultMutableTreeNode) node.getChildAt(index);
			}
		}
	}

	/**
	 * Returns the index of a child of a given node, provided its string value.
	 * 
	 * @param node The node to search its children
	 * @param childValue The value of the child to compare with
	 * @return The index
	 */
	private static int childIndex(final DefaultMutableTreeNode node, final String childValue) {
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> children = node.children();
		DefaultMutableTreeNode child = null;
		int index = -1;

		while (children.hasMoreElements() && index < 0) {
			child = children.nextElement();
			if (child.getUserObject() != null && childValue.equals(child.getUserObject())) {
				index = node.getIndex(child);
			}
		}
		return index;
	}
}
