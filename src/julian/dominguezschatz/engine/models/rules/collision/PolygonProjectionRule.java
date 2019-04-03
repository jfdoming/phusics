package julian.dominguezschatz.engine.models.rules.collision;

import julian.dominguezschatz.engine.models.IntervalDouble;
import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date : 22/04/2017
 * Description: Implements a rule that projects a polygon onto an axis.Generic Object
 */
public class PolygonProjectionRule implements ProjectionRule {

    // the shape this rule targets
    private final Path2D.Double shape;

    /**
     * Constructor.
     * @param shape the shape to project
     */
    public PolygonProjectionRule(Path2D.Double shape) {
        this.shape = shape;
    }

    /**
     * Projects a polygon onto an axis.
     * @param transform the transform on the shape
     * @param axis the axis to project onto
     * @return the projection of a polygon onto an axis
     */
    public IntervalDouble project(AffineTransform transform, Vector2D axis) {
        // min and max are the start and finish points
        boolean isMinSet = false;
        double min = 0;
        double max = 0;

        // required for using paths
        // vertex[0] is the x value, vertex[1] is the y value
        double[] vertex = new double[6];

        for (PathIterator iterator = shape.getPathIterator(transform); !iterator.isDone(); iterator.next()) {
            int type = iterator.currentSegment(vertex);

            // if the shape is closing, ignore the segment
            if (type == PathIterator.SEG_CLOSE) {
                continue;
            }

            double projection = axis.dot(new Vector2D(Vector2D.CARTESIAN, vertex[0], vertex[1]));

            // check if the min/max should be altered
            if (!isMinSet) {
                // the first projection is automatically the min and max
                isMinSet = true;
                min = projection;
                max = min;
            }

            // if the new projection is out of range, expand the range to include it
            if (projection < min) {
                min = projection;
            } else if (projection > max) {
                max = projection;
            }
        }
        return new IntervalDouble(min, max);
    }

}
