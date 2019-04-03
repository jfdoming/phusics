package julian.dominguezschatz.engine.models.rules.collision;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Arrays;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date : 22/04/2017
 * Description: Implements a rule that allows the user to select certain vertices of a polygon
 * based on certain criteria.Generic Object
 */
public class PolygonVertexSelectionRule implements VertexSelectionRule {

    // the shape this rule targets
    private final Path2D.Double shape;

    // constructor
    public PolygonVertexSelectionRule(Path2D.Double shape) {
        this.shape = shape;
    }

    @Override
    public Point2D.Double closest(Point2D.Double point, AffineTransform transform) {
        double distSq = -1;

        // required for using paths
        double[] vertex = new double[6];
        double[] closestVertex = new double[6];
        for (PathIterator iterator = shape.getPathIterator(transform); !iterator.isDone(); iterator.next()){
            int type = iterator.currentSegment(vertex);

            // if the shape is closing, ignore the segment
            if (type == PathIterator.SEG_CLOSE) {
                continue;
            }

            double newDistSq = Math.pow(point.getX() - vertex[0], 2) + Math.pow(point.getY() - vertex[1], 2);
            if (distSq == -1 || newDistSq < distSq) {
                distSq = newDistSq;
                closestVertex = Arrays.copyOf(vertex, vertex.length);
            }
        }
        return new Point2D.Double(closestVertex[0], closestVertex[1]);
    }
}
