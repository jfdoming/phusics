package julian.dominguezschatz.engine.models.rules.collision;

import julian.dominguezschatz.engine.models.IntervalDouble;
import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date : 22/04/2017
 * Description: Implements a rule that projects an ellipse onto an axis.Generic Object
 */
public class EllipseProjectionRule implements ProjectionRule {

    // the shape this rule targets
    private final Ellipse2D.Double shape;

    /**
     * Constructor.
     * @param shape the shape to project
     */
    public EllipseProjectionRule(Ellipse2D.Double shape) {
        this.shape = shape;
    }

    /**
     * Projects an ellipse onto an axis. The projection of a circle is simply
     * the projection of its center +/- its radius.
     * @param transform the transform on the shape
     * @param axis the axis to project onto
     * @return the projection of an ellipse an the axis
     */
    public IntervalDouble project(AffineTransform transform, Vector2D axis) {
        // determine the center of the shape
        Point2D.Double position = new Point2D.Double();
        transform.transform(position, position);

        // construct a vector from the origin to the center of the shape
        Vector2D positionVector = new Vector2D(position);

        // project the position onto the axis
        double projection = axis.dot(positionVector);

        // apply the transform to the radius
        Point2D.Double radiusPoint = new Point2D.Double((shape.getWidth() / 2.0), 0);
        transform.transform(radiusPoint, radiusPoint);
        radiusPoint.x -= position.x;
        radiusPoint.y -= position.y;

        double radius = radiusPoint.getX();

        // project the rest of the ellipse
        double min = projection - radius;
        double max = projection + radius;

        return new IntervalDouble(min, max);
    }

}
