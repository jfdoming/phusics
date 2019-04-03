package julian.dominguezschatz.engine.controllers;

import julian.dominguezschatz.engine.models.FramerateTracker;
import julian.dominguezschatz.engine.utils.ControlState;
import julian.dominguezschatz.engine.utils.TimeKeeper;
import julian.dominguezschatz.engine.views.DebugTextRenderer;
import julian.dominguezschatz.engine.views.Renderer;

import java.awt.event.KeyEvent;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-29
 * Description: Represents a controller that translates user input into actions.Generic Object
 *
 *
 * Useful links:
 *
 * Physics:
 * http://buildnewgames.com/gamephysics/
 * http://www.cplusplus.com/forum/beginner/89388/
 * http://gafferongames.com/game-physics/integration-basics/
 * https://www.toptal.com/game/video-game-physics-part-i-an-introduction-to-rigid-body-dynamics
 * http://stackoverflow.com/questions/3251561/good-2d-collision-response-references
 *
 * Subpixel coordinates in Swing:
 * http://stackoverflow.com/a/27166209
 *
 * MVC Summary:
 * - controller receives events, tells model how to update
 * - model updates itself, notifies observers
 * - view observes model, requests model data
 */
public abstract class Controller {

    private static final int TARGET_FRAME_RATE = 60;
    private static final int MINIMUM_FRAME_RATE = 5;

    private static final String DEBUG_FRAME_RATE_FORMAT = "Frame Rate: %.0f";
    private static final String DEBUG_COPYRIGHT = "(C) 2017 Julian Dominguez-Schatz.";

    // time-related objects
    protected TimeKeeper timeKeeper;
    private FramerateTracker framerateTracker;

    // represents the state of the keyboard at any given moment
    private ControlState controlState;

    protected Renderer renderer;
    private volatile boolean showDebugText;

    /**
     * Constructor.
     */
    public Controller() {
        showDebugText = true;

        // initialize helper objects such as time keepers and input maps
        timeKeeper = new TimeKeeper(TARGET_FRAME_RATE, MINIMUM_FRAME_RATE);
        framerateTracker = new FramerateTracker();
        controlState = new ControlState();
    }

    /**
     * Runs when this controller gains control of the window.
     */
    final void onControlGained(Renderer renderer) {
        this.renderer = renderer;
        timeKeeper.resume();

        initialize();
    }

    /**
     * Called when this controller gains control of the window.
     */
    protected void initialize() {
    }

    /**
     * Runs when this controller loses control of the window.
     */
    protected void onControlLost() {
        this.renderer = null;
        timeKeeper.pause();
    }

    /**
     * Updates this controller. Should be called once per frame.
     */
    public void update() {
        timeKeeper.update();
        framerateTracker.update(timeKeeper.getFrameElapsedTime());

        updateModel();
        updateView();
    }

    /**
     * Updates the models as per the simulation being run.
     */
    protected void updateModel() {
        // update the model based on user input (done once per frame)
        mapInput(controlState);
    }

    protected void mapInput(ControlState controlState) {
        // press F1 to toggle debug text
        if (controlState.canConsumeKey(KeyEvent.VK_F1)) {
            showDebugText = !showDebugText;
        }
    }

    /**
     * Updates the views with the latest information from the models.
     */
    protected void updateView() {
        // the code below would be somewhat difficult to implement, so for now it will stay commented out
        /*
         * Here we calculate multipliers for previous and current physics states; this is to implement
         * smoothing between frames. The formula to calculate the smoothed value looks as follows:
         *
         * smoothed value = (current value * multiplier) + (previous value * (1.0 - multiplier))
         *
         * This formula will be applied to all values before they are sent to the renderer to be rendered.

        float currentAlpha = timeKeeper.calculateAlpha();
        float previousAlpha = 1.0F - currentAlpha;
         */

        if (showDebugText) {
            Renderer uncastedDebugRenderer = renderer.getRenderer(0);
            if (uncastedDebugRenderer instanceof DebugTextRenderer) {
                DebugTextRenderer debugRenderer = (DebugTextRenderer) uncastedDebugRenderer;
                debugRenderer.addDebugText(DEBUG_COPYRIGHT);

                double frameRate = framerateTracker.getFrameRate();
                debugRenderer.addDebugText(String.format(DEBUG_FRAME_RATE_FORMAT, frameRate));
            }
        }
    }

    protected void keyPressed(KeyEvent e) {
        controlState.setKeyPressed(e.getKeyCode(), true);
    }

    protected void keyReleased(KeyEvent e) {
        controlState.setKeyPressed(e.getKeyCode(), false);
    }

    protected void keyTyped(KeyEvent e) {
        // we don't care about key typed events
    }
}
