package julian.dominguezschatz.engine.models;

import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;

/**
 * Class: UIComponentState
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-30
 * Description: Provides information about the dimensions and left-right offsets of a component.
 */
public class UIComponentState {

    // offsets of the component
    private int left;
    private int top;

    // dimensions of the component
    private int width;
    private int height;

    // center of the component
    private double centerX;
    private double centerY;

    public UIComponentState(Component component) {
        this.left = 0;
        this.top = 0;
        this.width = component.getWidth();
        this.height = component.getHeight();

        // if a container is provided, it might have insets we need to account for
        if (component instanceof Container) {
            Container container = (Container) component;
            Insets windowInsets = container.getInsets();

            this.left += windowInsets.left;
            this.top += windowInsets.top;
            this.width -= (windowInsets.left + windowInsets.right);
            this.height -= (windowInsets.top + windowInsets.bottom);
        }

        // determine the center of the component
        this.centerX = this.width / 2.0;
        this.centerY = this.height / 2.0;
    }

    // getters

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }
}
