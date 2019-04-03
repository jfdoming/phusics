package julian.dominguezschatz.engine.controllers;

import julian.dominguezschatz.engine.models.UIComponentState;
import julian.dominguezschatz.engine.utils.Log;
import julian.dominguezschatz.engine.views.ActiveRenderingStrategy;
import julian.dominguezschatz.engine.views.PassiveRenderingStrategy;
import julian.dominguezschatz.engine.views.Renderer;
import julian.dominguezschatz.engine.views.RenderingStrategy;
import julian.dominguezschatz.engine.views.SolidBackgroundRenderer;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date: 06/02/2017
 * Description: Implements the main loop of the program.
 *
 * Note: this class is package-private to prevent the user from unnecessarily starting threads.
 */
class ApplicationLoop implements Runnable, KeyListener {

    private Thread applicationLoopThread;

    private HashMap<Integer, Controller> controllers;
    private Controller currentController;

    private Renderer renderer;
    private RenderingStrategy renderingStrategy;

    // whether a controller has yet been set or a message saying so has been printed
    private boolean controllerWarningLogged = false;

    ApplicationLoop() {
        // prepare the application loop thread (a potentially expensive operation
        // that should occur before the application window is opened)
        applicationLoopThread = new Thread(this, "running-loop");

        renderer = new SolidBackgroundRenderer();
        controllers = new HashMap<>();
        currentController = null;
    }

    void runForSurface(Canvas surfaceToDrawTo, boolean useActiveRendering) {
        // determine the type of rendering to use
        if (useActiveRendering) {
            renderingStrategy = new ActiveRenderingStrategy(surfaceToDrawTo);
        } else {
            renderingStrategy = new PassiveRenderingStrategy(surfaceToDrawTo);
        }

        // initialize the main view
        renderer.initialize(new UIComponentState(surfaceToDrawTo));
        surfaceToDrawTo.addKeyListener(this);

        // start the application loop thread
        applicationLoopThread.start();
    }

    void stop() {
        try {
            applicationLoopThread.interrupt();
            applicationLoopThread.join();
        } catch (InterruptedException e) {
            // should never happen
        }
    }

    @Override
    public void run() {
        // our application loop; runs as fast as possible
        while (!Thread.interrupted()) {
            if (currentController == null) {
                if (!controllerWarningLogged) {
                    controllerWarningLogged = true;
                    Log.w("Application", "The current controller is not set, therefore the application logic will not run.");
                }
            } else {
                currentController.update();
            }
            renderingStrategy.render(renderer);

            // TODO wait for Java to add a vsync capability
        }

        // dispose of our rendering strategy; we don't need it any longer
        renderingStrategy.dispose();
    }

    public boolean registerController(int id, Controller controller) {
        controllers.put(id, controller);
        return true;
    }

    public void setCurrentController(int id) {
        if (currentController != null) {
            currentController.onControlLost();
        }

        currentController = controllers.get(id);
        if (currentController != null) {
            currentController.onControlGained(renderer);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        currentController.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentController.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentController.keyReleased(e);
    }
}
