package julian.dominguezschatz.engine.views;

import julian.dominguezschatz.engine.models.UIComponentState;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class: SolidBackgroundRenderer
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-30
 * Description: This object is used to render a solid background.
 */
public class SolidBackgroundRenderer extends ShapeRenderer {

    // the shape this renderer targets
    private Color clearColour;

    /**
     * Default constructor.
     */
    public SolidBackgroundRenderer() {
        this.clearColour = Color.BLACK;
    }

    @Override
    public void onRender(Graphics2D g) {
        g.setColor(clearColour);

        UIComponentState UIComponentState = getUIComponentState();
        g.fillRect(UIComponentState.getLeft(), UIComponentState.getTop(),
                UIComponentState.getWidth(), UIComponentState.getHeight());
    }

    // setters

    public SolidBackgroundRenderer setClearColour(Color clearColour) {
        this.clearColour = clearColour;
        return this;
    }
}
