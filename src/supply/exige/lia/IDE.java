package supply.exige.lia;

import java.awt.Dimension;

import javax.swing.JFrame;

public class IDE extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public final static int WIDTH = 1000;
	public final static int HEIGHT = WIDTH / 16 * 9;
	
	private IDETextArea input = new IDETextArea();
	
	public IDE() {
		// Create IDE window
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size); // Set window dimensions
		setResizable(true); // Enable resize
		pack(); // Fill entire window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close When the exit button is pressed
		setLocationRelativeTo(null); // Center window
		setTitle("Lia IDE | IN-DEV v0.0");
		initializeElements();
		
		setVisible(true); // Show window
	}
	
	private void initializeElements() {
		add(input);
	}
	
	

}
