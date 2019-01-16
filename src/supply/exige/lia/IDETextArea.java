package supply.exige.lia;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

public class IDETextArea extends JTextArea {

	public IDETextArea() {
		setFont(new Font("Consolas", Font.PLAIN, 18));
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setEditable(true);
	}

	public IDETextArea(Color fgColor, Color bgColor, boolean editable) {
		setFont(new Font("Consolas", Font.PLAIN, 18));
		setForeground(fgColor);
		setBackground(bgColor);
		setEditable(editable);
	}

}