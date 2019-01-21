package supply.exige.lia.modules;

import javax.swing.*;
import java.awt.*;

/**
 * ResourceLoader Class.
 * Allows for resources to be loaded in a static call by using the java @{@link Toolkit}
 *
 * @author Ali Shariatmadari
 */

public class ResourceLoader {

    public static ResourceLoader loader = new ResourceLoader(); // Create static instance of this class


    public static ImageIcon getImageIcon(String path) {
        // Gets the ImageIcon using the java toolkit loader and resource address
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(loader.getClass().getClassLoader().getResource(path)));
    }
}