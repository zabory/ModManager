package modManager;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.SpringLayout;
import javax.swing.Timer;
import javax.swing.border.Border;

public class modPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3658750723915586007L;
	modFrame frame;
	JProgressBar progressBar;
	modManager mm;
	//DefaultListModel<JLabel> modListModel;
	DefaultListModel<String> logListModel;
	JPanel modPane;
	
	
	
	
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
//		logListModel.addElement("test");
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
	}
	
	public void addButtons() {
		
		JPanel buttonPane = new JPanel();
		
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
		
		Border insideBorder;
		
		JPanel listPane = new JPanel();
		listPane.setBounds(0, 0, 500, 50);
		listPane.setPreferredSize(new Dimension(frame.getWidth(), 400));
		GridLayout listLayout = new GridLayout(1,0);
		listLayout.setVgap(15);
		listLayout.setHgap(100);
		listPane.setLayout(listLayout);
		
		
		modPane = new JPanel();
		modPane.setSize(listPane.getWidth(), listPane.getHeight());
		
		SpringLayout layout = new SpringLayout();
		modPane.setLayout(layout);
		
		
		JScrollPane modScrollList = new JScrollPane(modPane);
		
		
	   
	    
	    Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	    insideBorder = BorderFactory.createLineBorder(Color.BLACK);
	    modScrollList.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
	    
		listPane.add(modScrollList);
		
		logListModel = new DefaultListModel<String>();
		JList<String> logListJList = new JList<String>(logListModel);
	    JScrollPane logScrollList = new JScrollPane(logListJList);
	    
	
	    logScrollList.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
	    
	    listPane.add(logScrollList);
	    
	   
	    
	    
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
	
	public void addModToList(String name) {
		SpringLayout layout = (SpringLayout) modPane.getLayout();
		int jumpHeight = 20;
		
		JLabel newLabel = new JLabel(name);
		newLabel.setName(name + "label");
		JCheckBox newBox = new JCheckBox();
		newBox.setName(name);
		layout.putConstraint(SpringLayout.WEST, newLabel, 5, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, newBox, modPane.getWidth() - 125, SpringLayout.WEST, frame.getContentPane());
		
		
		layout.putConstraint(SpringLayout.NORTH,  newLabel, (modPane.getComponents().length / 2) * jumpHeight, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH,  newBox, (modPane.getComponents().length / 2) * jumpHeight, SpringLayout.NORTH, frame.getContentPane());
		
		modPane.add(newLabel);
		modPane.add(newBox);
		modPane.setPreferredSize(new Dimension(modPane.getWidth(), modPane.getHeight() + jumpHeight));
		modPane.setSize(new Dimension(modPane.getWidth(), modPane.getHeight() + jumpHeight));
	}
	
	public Component getComponent(String name) {
		Component f = null;
		for(Component x : modPane.getComponents()) {
			if(x.getName().equals(name)){
				f = x;
			}
		}
		return f;
	}
}
