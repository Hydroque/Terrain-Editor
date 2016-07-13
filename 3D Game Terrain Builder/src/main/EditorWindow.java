package main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import plugin.data.Tool;

import java.awt.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class EditorWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel toolbar;
	private Canvas canvas;
	
	private JLabel toolbar_fps;
	
	public EditorWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				Main.closeRequested = true;
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnNewMenu.add(mntmNew);
		
		JSeparator separator_2 = new JSeparator();
		mnNewMenu.add(separator_2);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mnNewMenu.add(mntmLoad);
		
		JSeparator separator_1 = new JSeparator();
		mnNewMenu.add(separator_1);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnNewMenu.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnNewMenu.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnNewMenu.add(mntmExit);
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		toolbar = new JPanel();
		contentPane.add(toolbar, BorderLayout.NORTH);
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		toolbar_fps = new JLabel("FPS Listing");
		toolbar.add(toolbar_fps);
		
		JPanel body = new JPanel();
		contentPane.add(body, BorderLayout.CENTER);
		body.setLayout(null);
		
		canvas = new Canvas();
		canvas.setBounds(0, 96, 800, 600);
		body.add(canvas);
	}
	
	public void addTools(ArrayList<Tool> tools) {
		for (final Tool t : tools) {
			switch(t.getToolType()) {
			case BUTTON:
				JButton button = new JButton(t.getText());
				button.addActionListener((e) -> t.getAction().run());
				toolbar.add(button);
				break;
			}
		}
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public JLabel getFPS() {
		return toolbar_fps;
	}
	
}
