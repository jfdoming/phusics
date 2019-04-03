package julian.dominguezschatz.engine.views;

import java.awt.Component;

/**
 * Class: PassiveRenderingStrategy
 * Author: Julian Dominguez-Schatz
 * Date: 14/02/2017
 * Description: Represents a strategy for passively rendering the application views.
 */
public class PassiveRenderingStrategy implements RenderingStrategy {

    private Component component;

    public PassiveRenderingStrategy(Component component) {
        this.component = component;
    }

    @Override
    public void render(Renderer renderer) {
        component.repaint();
    }

    @Override
    public void dispose() {
    }
}
