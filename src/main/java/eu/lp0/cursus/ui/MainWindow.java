/*
 cursus - Race series management program
 Copyright 2011  Simon Arlott

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.lp0.cursus.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import eu.lp0.cursus.app.Main;
import eu.lp0.cursus.db.Database;
import eu.lp0.cursus.util.Constants;
import eu.lp0.cursus.util.Messages;

public class MainWindow extends JFrame implements Executor {
	private final Executor background = Executors.newSingleThreadExecutor();
	private final Main main;

	private final DatabaseManager dbMgr = new DatabaseManager(this);

	private JMenuBar menuBar;
	private JMenu mnuFile;
	private JMenuItem mnuFileNew;
	private JMenuItem mnuFileOpen;
	private JMenuItem mnuFileSave;
	private JMenuItem mnuFileSaveAs;
	private JMenuItem mnuFileClose;
	private JSeparator mnuFileSeparator1;
	private JMenuItem mnuFileExit;
	private JMenu mnuHelp;
	private JMenuItem mnuHelpAbout;

	private JTabbedPane mainTabs;
	private JPanel pilotsTab;
	private JPanel classesTab;
	private JPanel lapsTab;
	private JPanel resultsTab;

	private JSplitPane classesSplitPane;
	private JList classesList;
	private ListModel classesListModel;
	private JScrollPane classesScrollPane;
	private JPanel classPilots;

	private JSplitPane lapsSplitPane;
	private JTree raceList;
	private JPanel raceLaps;

	public MainWindow(Main main, final String[] args) {
		super();
		this.main = main;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				background.execute(new Runnable() {
					@Override
					public void run() {
						MainWindow.this.startup(args);
					}
				});
			}

			@Override
			public void windowClosing(WindowEvent e) {
				background.execute(new Runnable() {
					@Override
					public void run() {
						shutdown();
					}
				});
			}
		});

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		initGUI();

		databaseClosed();
		enableStartupGUI(false);
	}

	Main getMain() {
		return main;
	}

	Database getDatabase() {
		return main.getDatabase();
	}

	@Override
	public void execute(Runnable command) {
		background.execute(command);
	}

	private void startup(String[] args) {
		try {
			if (args.length == 0) {
				dbMgr.newDatabase();
			} else if (args.length == 1) {
				// TODO open file
			} else {
				// TODO error message
			}
		} finally {
			enableStartupGUI(true);
		}
	}

	private void shutdown() {
		if (dbMgr.trySaveDatabase(Messages.getString("menu.file.exit"))) { //$NON-NLS-1$
			dispose();
		}
	}

	private void initGUI() {
		setTitle(Constants.APP_NAME);

		mainTabs = new JTabbedPane();
		getContentPane().add(mainTabs, BorderLayout.CENTER);

		pilotsTab = new JPanel();
		mainTabs.addTab(Messages.getString("tab.pilots"), null, pilotsTab, null); //$NON-NLS-1$
		mainTabs.setMnemonicAt(0, Messages.getKeyEvent("tab.pilots")); //$NON-NLS-1$

		classesTab = new JPanel();
		mainTabs.addTab(Messages.getString("tab.classes"), null, classesTab, null); //$NON-NLS-1$
		mainTabs.setMnemonicAt(1, Messages.getKeyEvent("tab.classes")); //$NON-NLS-1$
		classesTab.setLayout(new BorderLayout(0, 0));

		classesSplitPane = new JSplitPane();
		classesTab.add(classesSplitPane, BorderLayout.CENTER);

		classesList = new JList();
		classesList.setMinimumSize(new Dimension(150, 0));
		classesListModel = new DefaultComboBoxModel(new String[] { "Class 2", "Class 5" }); //$NON-NLS-1$ //$NON-NLS-2$
		classesList.setModel(classesListModel);
		classesSplitPane.setLeftComponent(classesList);

		classesScrollPane = new JScrollPane();
		classesSplitPane.setRightComponent(classesScrollPane);

		classPilots = new JPanel();
		classesScrollPane.setViewportView(classPilots);

		lapsTab = new JPanel();
		mainTabs.addTab(Messages.getString("tab.laps"), null, lapsTab, null); //$NON-NLS-1$
		mainTabs.setMnemonicAt(2, Messages.getKeyEvent("tab.laps")); //$NON-NLS-1$
		lapsTab.setLayout(new BorderLayout(0, 0));

		lapsSplitPane = new JSplitPane();
		lapsTab.add(lapsSplitPane);

		raceList = new JTree();
		raceList.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Series") { //$NON-NLS-1$
					{
						DefaultMutableTreeNode node_1;
						node_1 = new DefaultMutableTreeNode("Event 1"); //$NON-NLS-1$
						node_1.add(new DefaultMutableTreeNode("Race 1")); //$NON-NLS-1$
						node_1.add(new DefaultMutableTreeNode("Race 2")); //$NON-NLS-1$
						node_1.add(new DefaultMutableTreeNode("Race 3")); //$NON-NLS-1$
						add(node_1);
						node_1 = new DefaultMutableTreeNode("Event 2"); //$NON-NLS-1$
						node_1.add(new DefaultMutableTreeNode("Race 4")); //$NON-NLS-1$
						add(node_1);
						node_1 = new DefaultMutableTreeNode("Event 3"); //$NON-NLS-1$
						node_1.add(new DefaultMutableTreeNode("Race 5")); //$NON-NLS-1$
						add(node_1);
						node_1 = new DefaultMutableTreeNode("Event 4"); //$NON-NLS-1$
						node_1.add(new DefaultMutableTreeNode("Race 6")); //$NON-NLS-1$
						node_1.add(new DefaultMutableTreeNode("Race 7")); //$NON-NLS-1$
						node_1.add(new DefaultMutableTreeNode("Race 8")); //$NON-NLS-1$
						add(node_1);
					}
				}));
		raceList.setMinimumSize(new Dimension(150, 0));
		lapsSplitPane.setLeftComponent(raceList);

		raceLaps = new JPanel();
		lapsSplitPane.setRightComponent(raceLaps);

		resultsTab = new JPanel();
		mainTabs.addTab(Messages.getString("tab.results"), null, resultsTab, null); //$NON-NLS-1$
		mainTabs.setMnemonicAt(3, Messages.getKeyEvent("tab.results")); //$NON-NLS-1$

		setSize(800, 600);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnuFile = new JMenu();
		menuBar.add(mnuFile);
		mnuFile.setText(Messages.getString("menu.file")); //$NON-NLS-1$
		mnuFile.setMnemonic(Messages.getKeyEvent("menu.file")); //$NON-NLS-1$

		mnuFileNew = new JMenuItem();
		mnuFile.add(mnuFileNew);
		mnuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnuFileNew.setText(Messages.getString("menu.file.new")); //$NON-NLS-1$
		mnuFileNew.setMnemonic(Messages.getKeyEvent("menu.file.new")); //$NON-NLS-1$
		mnuFileNew.setActionCommand(DatabaseManager.Commands.NEW.toString());
		mnuFileNew.addActionListener(dbMgr);

		mnuFileOpen = new JMenuItem();
		mnuFile.add(mnuFileOpen);
		mnuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnuFileOpen.setText(Messages.getString("menu.file.open")); //$NON-NLS-1$
		mnuFileOpen.setMnemonic(Messages.getKeyEvent("menu.file.open")); //$NON-NLS-1$
		mnuFileOpen.setActionCommand(DatabaseManager.Commands.OPEN.toString());
		mnuFileOpen.addActionListener(dbMgr);

		mnuFileSave = new JMenuItem();
		mnuFile.add(mnuFileSave);
		mnuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnuFileSave.setText(Messages.getString("menu.file.save")); //$NON-NLS-1$
		mnuFileSave.setMnemonic(Messages.getKeyEvent("menu.file.save")); //$NON-NLS-1$
		mnuFileSave.setActionCommand(DatabaseManager.Commands.SAVE.toString());
		mnuFileSave.addActionListener(dbMgr);

		mnuFileSaveAs = new JMenuItem();
		mnuFile.add(mnuFileSaveAs);
		mnuFileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnuFileSaveAs.setText(Messages.getString("menu.file.save-as")); //$NON-NLS-1$
		mnuFileSaveAs.setMnemonic(Messages.getKeyEvent("menu.file.save-as")); //$NON-NLS-1$
		mnuFileSaveAs.setActionCommand(DatabaseManager.Commands.SAVE_AS.toString());
		mnuFileSaveAs.addActionListener(dbMgr);

		mnuFileClose = new JMenuItem();
		mnuFile.add(mnuFileClose);
		mnuFileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mnuFileClose.setText(Messages.getString("menu.file.close")); //$NON-NLS-1$
		mnuFileClose.setMnemonic(Messages.getKeyEvent("menu.file.close")); //$NON-NLS-1$
		mnuFileClose.setActionCommand(DatabaseManager.Commands.CLOSE.toString());
		mnuFileClose.addActionListener(dbMgr);

		mnuFileSeparator1 = new JSeparator();
		mnuFile.add(mnuFileSeparator1);

		mnuFileExit = new JMenuItem();
		mnuFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowEvent wev = new WindowEvent(MainWindow.this, WindowEvent.WINDOW_CLOSING);
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
			}
		});
		mnuFile.add(mnuFileExit);
		mnuFileExit.setText(Messages.getString("menu.file.exit")); //$NON-NLS-1$
		mnuFileExit.setMnemonic(Messages.getKeyEvent("menu.file.exit")); //$NON-NLS-1$

		mnuHelp = new JMenu();
		menuBar.add(mnuHelp);
		mnuHelp.setText(Messages.getString("menu.help")); //$NON-NLS-1$
		mnuHelp.setMnemonic(Messages.getKeyEvent("menu.help")); //$NON-NLS-1$

		mnuHelpAbout = new JMenuItem();
		mnuHelp.add(mnuHelpAbout);
		mnuHelpAbout.setText(Messages.getString("menu.help.about")); //$NON-NLS-1$
		mnuHelpAbout.setMnemonic(Messages.getKeyEvent("menu.help.about")); //$NON-NLS-1$
	}

	public void databaseOpened() {
		setTitle(Constants.APP_NAME + Constants.EN_DASH + main.getDatabase().getName());
		syncGUI(true);
	}

	public void databaseClosed() {
		syncGUI(false);
		setTitle(Constants.APP_DESC);
	}

	private void enableStartupGUI(boolean enabled) {
		mnuFileNew.setEnabled(enabled);
		mnuFileOpen.setEnabled(enabled);
	}

	private void syncGUI(boolean open) {
		mainTabs.setVisible(open);
		enableStartupGUI(true);
		mnuFileSave.setEnabled(open);
		mnuFileSaveAs.setEnabled(open);
		mnuFileClose.setEnabled(open);
		getRootPane().validate();
	}
}