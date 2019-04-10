package modManager;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class modPanel extends JPanel implements ActionListener{

	modFrame frame;
	JProgressBar progressBar;
	modManager mm;
	DefaultListModel<JLabel> modListModel;
	DefaultListModel<String> logListModel;
	
	
	
	
	modPanel(modFrame f) throws InterruptedException{
		frame = f;
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		setLayout(new GridBagLayout());
		addProgressBar(c);
		addButtons(c);
		addLists(c);
		
		
		mm = new modManager();
		mm.start();
		
		Timer timer = new Timer(5, this);
		timer.start();
		
		setFocusable(true);
		
		
		

		
		
		
	}

	
	void update(){
		if(!progressBar.getString().equals("Current task progress")) {
			progressBar.setString(progressBar.getString().replace("" + (int)Math.round(progressBar.getPercentComplete()* 100), "") + (int)Math.round(progressBar.getPercentComplete()* 100));
		}
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
	}
	
	public void addButtons(GridBagConstraints c) {
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 1;
		JButton update;
		update = new JButton("Update");
		update.setBounds(0, 0, 50, 50);
		update.setFocusable(false);
		//update.setEnabled(false);
		
		this.add(update, c);
		
		
		c.gridx = 0;
		c.gridy = 1;
		JButton enable;
		enable = new JButton("Enable");
		enable.setBounds(0, 0, 50, 50);
		enable.setFocusable(false);
		this.add(enable, c);
		

		c.gridx = 1;
		c.gridy = 1;
		JButton disable;
		disable = new JButton("Disable");
		disable.setBounds(0, 0, 50, 50);
		disable.setFocusable(false);
		this.add(disable, c);
		

		c.gridx = 3;
		c.gridy = 1;
		JButton cfu;
		cfu = new JButton("Check for updates");
		cfu.setBounds(0, 0, 50, 50);
		cfu.setFocusable(false);
		this.add(cfu, c);
		
		
		
		
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
	
	public void addLists(GridBagConstraints c) {
		
		JPanel test;
		test = new JPanel();
		test.setLayout(new GridBagLayout());
		test.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		GridBagConstraints d = new GridBagConstraints();
		modListModel = new DefaultListModel<JLabel>();
		JList<JLabel> modListJList = new JList<JLabel>(modListModel);
		d.anchor = GridBagConstraints.FIRST_LINE_START;
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridx = 0;
		d.gridy = 0;
		
		JLabel blue = new JLabel("testing");
		
		test.add(blue, d);
		d.gridx = 1;
		test.add(new JCheckBox(), d);
		d.gridx = 0;
		d.gridy = 1;
		test.add(new JLabel("yeet"), d);
		d.gridx = 1;
		test.add(new JCheckBox(), d);
		d.gridx = 0;
		d.gridy = 2;
		test.add(new JLabel("yeet"), d);
		d.gridx = 1;
		test.add(new JCheckBox(), d);
		d.gridx = 0;
		d.gridy = 3;
		test.add(new JLabel("yeet"), d);
		d.gridx = 1;
		test.add(new JCheckBox(), d);
		d.gridx = 0;
		d.gridy = 4;
		test.add(new JLabel("yeet"), d);
		d.gridx = 1;
		test.add(new JCheckBox(), d);
		d.gridx = 0;
		d.gridy = 5;
		test.add(new JLabel("yeet"), d);
		d.gridx = 1;
		test.add(new JCheckBox(), d);
		
		
		test.add(modListJList);
		
	    JScrollPane modScrollList = new JScrollPane(test);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		modScrollList.setBounds(50, 50, 5, 5);
		modScrollList.setPreferredSize(new Dimension(100,125));
		this.add(modScrollList, c);
		
		logListModel = new DefaultListModel<String>();
		JList<String> logListJList = new JList<String>(logListModel);
	    JScrollPane logScrollList = new JScrollPane(logListJList);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 2;
		logScrollList.setBounds(0, 0, 500, 50);
		this.add(logScrollList, c);
	}
	
	public void addProgressBar(GridBagConstraints c) {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 5;
		progressBar = new JProgressBar(0, 100);
	    progressBar.setValue(0);
	    progressBar.setBounds(0, 0, 50, 50);
		progressBar.setStringPainted(true);
		progressBar.setString("Current task progress");
		this.add(progressBar, c);
	}
}
