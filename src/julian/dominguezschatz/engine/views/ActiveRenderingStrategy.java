package julian.dominguezschatz.engine.views;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 * Class: ActiveRenderingStrategy
 * Author: Julian Dominguez-Schatz
 * Date: 14/02/2017
 * Description: Represents a strategy for actively rendering the application views.
 */
public class ActiveRenderingStrategy implements RenderingStrategy {

    private static final int NUMBER_OF_BUFFERS = 2;

    private BufferStrategy bufferStrategy;

    /**
     * Constructor. Takes a strongly-typed Canvas as a parameter due to Canvas being
     * one of the only AWT/Swing components that provide public buffering capabilities.
     */
    public ActiveRenderingStrategy(Canvas canvas) {
        canvas.createBufferStrategy(NUMBER_OF_BUFFERS);
        this.bufferStrategy = canvas.getBufferStrategy();
    }

    @Override
    public void render(Renderer renderer) {
        Graphics2D g = null;
        try {
            g = (Graphics2D) bufferStrategy.getDrawGraphics();
            renderer.render(g);
        } finally {
            if (g != null) {
                g.dispose();
            }
        }

        if (!bufferStrategy.contentsLost()) {
            bufferStrategy.show();
        }
    }

    @Override
    public void dispose() {
        bufferStrategy.dispose();
    }
}
