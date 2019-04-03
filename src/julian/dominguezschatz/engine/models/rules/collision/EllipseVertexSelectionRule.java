package julian.dominguezschatz.engine.models.rules.collision;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date : 22/04/2017
 * Description: Implements a rule that allows the user to select the center of an ellipse.Generic Object
 */
public class EllipseVertexSelectionRule implements VertexSelectionRule {

    // the shape this rule targets
    private final Ellipse2D.Double shape;

    // constructor
    public EllipseVertexSelectionRule(Ellipse2D.Double shape) {
        this.shape = shape;
    }

    @Override
    public Point2D.Double closest(Point2D.Double point, AffineTransform transform) {
        Point2D.Double position = new Point2D.Double();
        return (Point2D.Double) transform.transform(position, position);
    }
}
