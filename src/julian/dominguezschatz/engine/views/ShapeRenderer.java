package julian.dominguezschatz.engine.views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: ShapeRenderer
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-30
 * Description: This object is used to render a shape.
 */
public abstract class ShapeRenderer extends Renderer {

    /**
     * Constructs a renderer for a shape based on its type.
     *
     * @param shape the shape to set up a renderer for
     * @return the renderer for the shape
     */
    public static ShapeRenderer create(Shape shape) {
        if (shape instanceof Ellipse2D.Double) {
            return new CircleRenderer((Ellipse2D.Double) shape);
        }

        if (shape instanceof Path2D.Double) {
            return new PolygonRenderer((Path2D.Double) shape);
        }

        if (shape instanceof Rectangle2D) {
            return new PolygonRenderer(new Path2D.Double(shape));
        }

        throw new IllegalArgumentException("Unknown shape type!");
    }

    // the color of the shape
    private Color renderColor;

    /**
     * Constructor.
     */
    public ShapeRenderer() {
        renderColor = Color.WHITE;
    }

    // setters used to update the renderer with model information

    public void setRenderColor(Color renderColor) {
        this.renderColor = renderColor;
    }

    @Override
    protected void prerender(Graphics2D g) {
        super.prerender(g);
        g.setColor(renderColor);
    }
}
