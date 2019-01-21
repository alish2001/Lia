package supply.exige.lia;

import supply.exige.lia.IDE.IDE;
import supply.exige.lia.modules.ResourceLoader;

import javax.swing.*;

public class Lia {

    public static IDE ide;

    public static void main(String[] args) throws InterruptedException {
        showSplash();
        ide = new IDE();
        ide.startIDE();
    }

    private static void showSplash() throws InterruptedException {
        JWindow splash = new JWindow();
        splash.getContentPane().add(new JLabel(ResourceLoader.getImageIcon("LiaSplashScreen.png")));
        splash.pack();
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        splash.requestFocus();
        Thread.sleep(2000);
        splash.dispose();
    }
}