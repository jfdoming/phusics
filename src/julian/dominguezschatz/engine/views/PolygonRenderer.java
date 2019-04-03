package julian.dominguezschatz.engine.views;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 * Class: PolygonRenderer
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-30
 * Description: This object is used to render a polygon.
 */
public class PolygonRenderer extends ShapeRenderer {

    // the shape this renderer targets
    private Path2D.Double renderShape;

    /**
     * Constructor.
     *
     * @param renderShape the shape this renderer targets
     */
    public PolygonRenderer(Path2D.Double renderShape) {
        this.renderShape = renderShape;
    }

    @Override
    public void onRender(Graphics2D g) {
        g.fill(renderShape);
    }
}
