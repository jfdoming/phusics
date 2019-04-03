package julian.dominguezschatz.engine.views;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Class: CircleRenderer
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-30
 * Description: This object is used to render a circle.
 */
public class CircleRenderer extends ShapeRenderer {

    // the shape this renderer targets
    private Ellipse2D.Double renderShape;

    /**
     * Constructor.
     *
     * @param renderShape the shape this renderer targets
     */
    public CircleRenderer(Ellipse2D.Double renderShape) {
        this.renderShape = renderShape;
    }

    @Override
    public void onRender(Graphics2D g) {
        g.translate(renderShape.getX(), renderShape.getY());
        g.fillOval(0, 0, (int) renderShape.getWidth(), (int) renderShape.getHeight());
    }
}
