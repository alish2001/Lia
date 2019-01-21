package supply.exige.lia;

import supply.exige.lia.IDE.IDE;
import supply.exige.lia.modules.ResourceLoader;

import javax.swing.*;

/**
 * The main class of the Lia IDE.
 * Course: ICS4U - Culminating Project
 * Teacher: Mr.Kordbacheh
 * 
 * @author Ali Shariatmadari
 */
public class Lia {

    public static IDE ide; // Declare Development Environment

    public static void main(String[] args) throws InterruptedException {
        showSplash(); // Show splash screen
        ide = new IDE(); // Instantiate IDE
        ide.startIDE(); // Start development environment
    }

    private static void showSplash() throws InterruptedException {
        JWindow splash = new JWindow(); // Create JWindow with LiaSplashScreen.png
        splash.getContentPane().add(new JLabel(ResourceLoader.getImageIcon("LiaSplashScreen.png")));
        splash.pack();
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        splash.requestFocus();
        Thread.sleep(2000); // Show for 2 seconds
        splash.dispose(); // Close window
    }
}