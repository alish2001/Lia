package supply.exige.lia;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class IDE extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public final static int WIDTH = 1000;
	public final static int HEIGHT = WIDTH / 16 * 9;
	
	private JPanel mainPanel = new JPanel();
	
	private IDETextArea input = new IDETextArea();
	private IDETextArea output = new IDETextArea(Color.GREEN, Color.BLACK, false);
	
	public IDE() {
		// Create IDE window
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size); // Set window dimensions
		setResizable(true); // Enable resize
		pack(); // Fill entire window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close When the exit button is pressed
		setLocationRelativeTo(null); // Center window
		setTitle("Lia IDE | IN-DEV v0.1");
		
		startIDE();
		
		setVisible(true); // Show window
	}
	
	private void startIDE() {
		//https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
		outputLine("<Lia Interpreter v1.0>\n");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		JScrollPane inputPane = new JScrollPane(input, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane outputPane = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(inputPane);
		mainPanel.add(outputPane);
		JButton button = new JButton("Run");
		button.addActionListener(e -> Runtime.run(input.getDocument())); // Execute runtime on launch
		button.setSize(new Dimension(100, 10));
        button.setFocusPainted(false);
		mainPanel.add(button);
		add(mainPanel);
	}

	public void outputLine(String str) {
        output.append(str + "\n");
    }

    public void clear(){
	    output.setText("<Lia Interpreter v1.0>\n");
    }
}
