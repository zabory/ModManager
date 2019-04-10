package modManager;

import java.awt.Color;

import javax.swing.JFrame;

public class modFrame extends JFrame{
	
	modPanel shp;
	
	modFrame() throws InterruptedException{
		setSize(971, 521);
		setTitle("Mod update manager");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		shp = new modPanel(this);
		add(shp);
		setVisible(true);
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new modFrame();
	}

}
