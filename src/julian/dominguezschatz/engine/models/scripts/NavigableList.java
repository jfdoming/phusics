package julian.dominguezschatz.engine.models.scripts;

import java.util.Stack;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-08-28
 * Description: Represents a list of elements that can be jumped and stepped through.
 */
public class NavigableList<T> {

    protected T[] elements;

    private int index;
    private boolean autoAdvance;

    private class State {
        final int index;
        final boolean autoAdvance;

        public State(int index, boolean autoAdvance) {
            this.index = index;
            this.autoAdvance = autoAdvance;
        }
    }

    private final Stack<State> states;

    public NavigableList() {
        elements = null;
        states = new Stack<>();

        reset();
    }

    public T next() {
        if (elements == null) {
            return null;
        }

        if (autoAdvance) {
            advance();
        } else {
            autoAdvance = true;
        }
        return getNext();
    }

    public T getNext() {
        if (elements == null) {
            return null;
        }

        if (!hasOne()) {
            return null;
        }

        return elements[index];
    }

    public void advance() {
        index++;
    }

    public void waitAdvance() {
        autoAdvance = false;
    }

    public boolean hasOne() {
        if (elements == null) {
            return false;
        }
        return index < elements.length;
    }

    public boolean hasNext() {
        if (elements == null) {
            return false;
        }
        return (index + 1) < elements.length;
    }

    public void reset() {
        index = 0;
        autoAdvance = false;
    }

    public void savePosition() {
        states.push(new State(index, autoAdvance));
    }

    public void loadPosition() {
        State state = states.pop();
        index = state.index;
        autoAdvance = state.autoAdvance;
    }

    public void removePosition() {
        states.pop();
    }
}
