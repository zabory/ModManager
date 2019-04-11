package modManager;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class modPanel extends JPanel implements ActionListener{

	modFrame frame;
	JProgressBar progressBar;
	modManager mm;
	//DefaultListModel<JLabel> modListModel;
	DefaultListModel<String> logListModel;
	
	
	
	
	modPanel(modFrame f) throws InterruptedException{
		frame = f;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		addProgressBar();
		addButtons();
		addLists();
		
		
		mm = new modManager();
		mm.start();
		
		Timer timer = new Timer(5, this);
		timer.start();
		
		setFocusable(true);	
		frame.setSize(frame.getWidth() + 30, frame.getHeight() + 1);
	}

	
	void update(){
		if(!progressBar.getString().equals("Current task progress")) {
			progressBar.setString(progressBar.getString().replace("" + (int)Math.round(progressBar.getPercentComplete()* 100) + "%", "") + (int)Math.round(progressBar.getPercentComplete()* 100)+ "%");
		}
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
	}
	
	public void addButtons() {
		
		JPanel buttonPane = new JPanel();
		;
		buttonPane.setPreferredSize(new Dimension(frame.getWidth(), 50));

		GridLayout buttonLayout = new GridLayout(1,0);
		buttonLayout.setVgap(15);
		buttonLayout.setHgap(10);
		buttonPane.setLayout(buttonLayout);
		
		
		JButton enable;
		enable = new JButton("Enable");
		enable.setBounds(5, 5, 5, 5);
		enable.setPreferredSize(new Dimension(5,5));
		enable.setFocusable(false);
		buttonPane.add(enable);
		

		
		JButton disable;
		disable = new JButton("Disable");
		disable.setPreferredSize(new Dimension(5,5));
		disable.setBounds(0, 0, 50, 5);
		disable.setFocusable(false);
		buttonPane.add(disable);
		
		
		JButton cfu;
		cfu = new JButton("Check for updates");
		cfu.setPreferredSize(new Dimension(5,5));
		cfu.setBounds(0, 0, 50, 5);
		cfu.setFocusable(false);
		buttonPane.add(cfu);
		
		JButton update;
		update = new JButton("Update");
		update.setBounds(0, 0, 50, 5);
		update.setPreferredSize(new Dimension(5,5));
		update.setFocusable(false);
		update.setEnabled(false);
		buttonPane.add(update);
		
		this.add(buttonPane);
		
		
		update.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0)
			{
				progressBar.setString("Searching for mods:");
				progressBar.setValue(progressBar.getValue() + 1);
			}
		});
	}
	
	public void addLists() {
		
		JPanel listPane = new JPanel();
		listPane.setBounds(0, 0, 500, 50);
		listPane.setPreferredSize(new Dimension(frame.getWidth(), 400));
		GridLayout listLayout = new GridLayout(1,0);
		listLayout.setVgap(15);
		listLayout.setHgap(100);
		listPane.setLayout(listLayout);
		
		
		JPanel modPane = new JPanel();
		modPane.setPreferredSize(new Dimension(frame.getWidth() / 2 - 150, 40));
		modPane.setLayout(new GridLayout(0, 2));
		
		JPanel modListPane = new JPanel();
		modListPane.setLayout(new BoxLayout(modListPane, BoxLayout.PAGE_AXIS));
		modListPane.setSize(new Dimension(modPane.getWidth() - 25, 40));
		modListPane.setPreferredSize(new Dimension(modPane.getWidth() - 25, 40));
		Border insideBorder = BorderFactory.createLineBorder(Color.BLACK);
		modListPane.setBorder(insideBorder);
		modPane.add(modListPane);
		
		JPanel checkListPane = new JPanel();
		checkListPane.setSize(new Dimension(25,40));
		checkListPane.setPreferredSize(new Dimension(25,40));
		checkListPane.setLayout(new BoxLayout(checkListPane, BoxLayout.PAGE_AXIS));
		insideBorder = BorderFactory.createLineBorder(Color.RED);
		checkListPane.setBorder(insideBorder);
		modPane.add(checkListPane);
		
		
		JScrollPane modScrollList = new JScrollPane(modPane);
		
		
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		checkListPane.add(new JCheckBox());
		
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		modListPane.add(new JLabel("test"));
		
		
	    
		
	   
	    
	    Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	    insideBorder = BorderFactory.createLineBorder(Color.BLACK);
	    modScrollList.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
	    
		listPane.add(modScrollList);
		
		logListModel = new DefaultListModel<String>();
		JList<String> logListJList = new JList<String>(logListModel);
	    JScrollPane logScrollList = new JScrollPane(logListJList);
	    
	
	    logScrollList.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
	    
	    listPane.add(logScrollList);
	    
	    logListModel.addElement("test");
	   
	    
	    add(listPane);
	   
		
	}
	
	public void addProgressBar() {
		progressBar = new JProgressBar(0, 100);
	    progressBar.setValue(0);
	    Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	    Border insideBorder = BorderFactory.createLineBorder(Color.BLACK);
	    progressBar.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
	    progressBar.setPreferredSize(new Dimension(900, 50));
		progressBar.setStringPainted(true);
		progressBar.setString("Current task progress");
		add(progressBar);
	}
}
