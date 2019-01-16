package supply.exige.lia;

import javax.swing.*;
import java.awt.*;

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