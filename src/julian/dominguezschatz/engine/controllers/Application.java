package julian.dominguezschatz.engine.controllers;

import julian.dominguezschatz.engine.utils.Log;
import julian.dominguezschatz.engine.views.ApplicationSurface;
import julian.dominguezschatz.engine.views.ApplicationWindow;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class: Application
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: Represents the entire application. This class provides methods for controlling the
 *              application lifecycle.
 */
public class Application {

    private final ApplicationConfig config;
    private ApplicationLoop runningLoop;

    public Application(ApplicationConfig config) {
        this.config = config;
    }

    /**
     * Starts this application. This opens the window and starts the background threads.
     */
    public void start() {
        // instantiate the application loop to be started as soon as the window opens
        runningLoop = new ApplicationLoop();

        // open our window on the event dispatch thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (config.isUsingSystemUI()) {
                    enableSystemUI();
                }

                // create and initialize the application window
                final JFrame applicationWindow = new ApplicationWindow(config);

                // add to the window the surface that all views are rendered onto
                final ApplicationSurface surface = new ApplicationSurface();
                applicationWindow.add(surface);

                // listen for window events (opening, closing, etc.)
                applicationWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        // attempt to capture focus
                        applicationWindow.requestFocus();

                        // start the game loop
                        runningLoop.runForSurface(surface, config.isUsingActiveRendering());
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        // tell the game loop to stop (blocks until it stops)
                        runningLoop.stop();

                        // close the window
                        applicationWindow.dispose();
                    }
                });

                // open the application window
                applicationWindow.setVisible(true);
            }
        });
    }

    public boolean registerController(int id, Controller controller) {
        if (runningLoop == null) {
            Log.e("Application", "The Application must be started before controllers can be registered!");
            return false;
        }

        return runningLoop.registerController(id, controller);
    }

    public void setCurrentController(int id) {
        runningLoop.setCurrentController(id);
    }

    private void enableSystemUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to locate system UI class!");
        } catch (InstantiationException e) {
            System.err.println("Failed to instantiate system UI class!");
        } catch (IllegalAccessException e) {
            System.err.println("Failed to access system UI class!");
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("System UI class invalid!");
        }
    }
}
