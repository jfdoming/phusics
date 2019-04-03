package julian.dominguezschatz.engine.utils;

import java.util.HashMap;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-29
 * Description: Represents the current state of all application controls (keyboard, mouse, etc.).
 *
 * This object is a model object.
 */
public class ControlState {

    /*
     * Keyboard state is stored by mapping a key code to a boolean value.
     * This boolean represents whether or not a certain key has been consumed,
     * i.e. whether the application has yet noticed that said key was pressed.
     */
    private final HashMap<Integer, Boolean> keyStates;

    public ControlState() {
        keyStates = new HashMap<>();
    }

    public synchronized boolean isKeyPressed(int key) {
        return keyStates.containsKey(key);
    }

    /**
     * @param key the key to check
     * @return true if a) the key is pressed and b) the key has not yet been consumed
     */
    public synchronized boolean canConsumeKey(int key) {
        // only consumable if a key exists and has not yet been consumed
        boolean consumable = false;
        if (isKeyPressed(key)) {
            consumable = keyStates.get(key);
        }
        if (consumable) {
            keyStates.put(key, false);
        }
        return consumable;
    }

    /**
     * @param key the key to set
     * @param pressed whether the key is pressed
     */
    public synchronized void setKeyPressed(int key, boolean pressed) {
        if (pressed) {
            if (keyStates.containsKey(key)) {
                return;
            }
            keyStates.put(key, true);
        } else {
            keyStates.remove(key);
        }
    }
}
