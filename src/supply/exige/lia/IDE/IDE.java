package supply.exige.lia.IDE;

import supply.exige.lia.Runtime;
import supply.exige.lia.modules.ResourceLoader;

import javax.swing.*;
import java.awt.*;

public class IDE extends JFrame {

    public static final double VERSION = 1.0;

    public final static int WIDTH = 1000;
    public final static int HEIGHT = WIDTH / 16 * 9;

    // Text areas
    private IDETextArea input = new IDETextArea();
    private IDETextArea output = new IDETextArea(Color.GREEN, Color.BLACK, false);
    private IDETextArea help = new IDETextArea(Color.MAGENTA, Color.WHITE, false);
    private IDETextArea processingOutput = new IDETextArea(Color.RED, Color.BLACK, false);

    public IDE() {
        // Create IDE window
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size); // Set window dimensions
        setResizable(true); // Enable resize
        pack(); // Fill entire window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close When the exit button is pressed
        setLocationRelativeTo(null); // Center window
        setIconImage(ResourceLoader.getImageIcon("LiaLogo.png").getImage());
        setTitle("Lia IDE | v" + VERSION);


        // Main panel for input and output
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        // Right panel for help screen
        JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Create ScrollPanes out of IDETextAreas
        JScrollPane inputPane = new JScrollPane(input, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane outputPane = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane helpPane = new JScrollPane(help, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane processingOutputPane = new JScrollPane(processingOutput, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        rightPane.setDividerLocation(HEIGHT / 2 - 60); // Split the right pane into 2 with an offset
        rightPane.setTopComponent(helpPane); // Add help area
        rightPane.setBottomComponent(processingOutputPane); // Add processing output

        // Add I/O panes
        mainPanel.add(inputPane);
        mainPanel.add(outputPane);

        // Add run button
        JButton button = new JButton("Run");
        button.addActionListener(e -> Runtime.run(input.getDocument())); // Execute runtime on launch using lambda expression
        mainPanel.add(button);

        mainSplitPane.setDividerLocation(WIDTH - 300); // Set left and right hand position
        mainSplitPane.setRightComponent(rightPane);
        mainSplitPane.setLeftComponent(mainPanel);

        // Help text
        String help = "Welcome to the Lia IDE!\n" +
                "Lia is a basic but capable programming language\n" +
                "intended to teach ordinary people the basics of programming!\n" +
                "Feel free to explore!\n" +
                "Made with <3 by Ali Shariatmadari | alish2001\n" +
                "------------------------------------------------------------\n\n" +

                "Syntax:\n" +

                "Assign, reassign, and append variables. Identifiers must start with letters.\n" +
                "var <string|int> <identifier> = <value>(.)<another value>\n" +
                "Sample: var string level = \"Level\".2 // Appends Level and 2 together\n" +
                "        var int math = {2+2} // Calculates the expression 2+2\n" +
                "        math = {math*3} // Calculates the value of variable math*3\n" +
                "------------------------------------------------------------\n\n" +

                "Outputs values to the Output console\n" +
                "print(<variable|string|int>)\n" +
                "------------------------------------------------------------\n\n" +

                "Process mathematical expressions.\n" +
                "{ expression }\n" +
                "------------------------------------------------------------\n\n" +

                "Loops the code by the number of times specified\n" +
                "loop(<number of iterations>)\n" +
                "// Some code\n" +
                "endLoop\n" +
                "Sample: loop(3)\n" +
                "        print(\"hi\")\n" +
                "        endLoop // Prints \"Hi\" 3 times\n" +
                "------------------------------------------------------------\n\n" +

                "Rules:\n" +
                "- ONE statement per line\n" +
                "- No nested loops\n" +
                "- No Doubles! Everything will be output/stored in integers.";
        // Set default values
        setHelp(help);
        input.setText("// Type some code :)");
        clear();
        clearProcessing();
        add(mainSplitPane); // Add the final pane into the JFrame
    }

    public void startIDE() {
        setVisible(true); // Show window
        requestFocus();
    }

    /**
     * Outputs a line through the IDE.
     *
     * @param str
     */
    public void outputLine(String str) {
        output.append(str + "\n");
    }

    /**
     * Outputs a line through the IDE's processing backend.
     *
     * @param str
     */
    public void outputProcessing(String str) {
        processingOutput.append(str + "\n");
    }

    public void setHelp(String str) {
        help.setText(str);
    }

    /**
     * Clears output console.
     */
    public void clear() {
        output.setText("<Lia Interpreter v1.0>\n");
    }

    /**
     * Clears output backend console.
     */
    public void clearProcessing() {
        processingOutput.setText("<Lia Interpreter Backend>\n");
    }
}
