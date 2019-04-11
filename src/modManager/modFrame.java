package modManager;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class modFrame extends JFrame{
	
	modPanel shp;
	
	modFrame() throws InterruptedException{
		setSize(1000, 521);
		setTitle("Mod update manager");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		shp = new modPanel(this);
		add(shp);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new modFrame();
	}

}
