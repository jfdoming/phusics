package julian.dominguezschatz.engine.views;

import julian.dominguezschatz.engine.controllers.ApplicationConfig;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.HeadlessException;

/**
 * Class: ApplicationWindow
 * Author: Julian Dominguez-Schatz
 * Date: 09/01/2017
 * Description: Represents the window that the application is rendered into. It configures the application window
 *              and starts the running loop automatically; it needs to be instantiated and shown
 *              via setVisible(true) on the AWT Event Queue.
 */
public class ApplicationWindow extends JFrame {

    /**
     * Default constructor
     * Throws HeadlessException if the peripherals necessary to display and
     * interact with a window are not available.
     */
    public ApplicationWindow(ApplicationConfig config) throws HeadlessException {
        initializeWindow(config);
    }

    /**
     * Configures the application window and prepares it to be displayed.
     */
    private void initializeWindow(ApplicationConfig config) {
        // configurable properties
        setTitle(config.getTitle());
        setSize(config.getWidth(), config.getHeight());
        if (config.isCentered()) {
            setLocationRelativeTo(null);
        }

        // non-configurable setup
        setResizable(false);
        setIgnoreRepaint(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
}
