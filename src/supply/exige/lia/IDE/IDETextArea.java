package supply.exige.lia.IDE;

import javax.swing.*;
import java.awt.*;

/**
 * An object for holding text area properties of the Lia IDE.
 *
 * @Author Ali Shariatmadari
 */
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