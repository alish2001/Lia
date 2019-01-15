package supply.exige.lia;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class IDETextArea extends JScrollPane {
	
	private static JTextArea inputArea = new JTextArea();
	
	public IDETextArea() {
		super(inputArea,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		inputArea.setFont(new Font("Consolas", Font.PLAIN, 18));
		inputArea.setForeground(Color.BLACK);
		inputArea.setBackground(Color.WHITE);
	}

	public IDETextArea(Font font, Color fgColor, Color bgColor) {
		super(inputArea,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		inputArea.setFont(font);
		inputArea.setForeground(fgColor);
		inputArea.setBackground(bgColor);
	}

}