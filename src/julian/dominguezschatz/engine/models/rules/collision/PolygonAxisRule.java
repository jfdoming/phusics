package julian.dominguezschatz.engine.models.rules.collision;

import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date : 22/04/2017
 * Description: Implements a rule that determines axes of a polygon for use in the SAT algorithm.Generic Object
 */
public class PolygonAxisRule implements AxisRule {

    // constructor
    public PolygonAxisRule() {
    }

    /**
     * @param body1 the first body
     * @param body2 the second body
     * @return a list of axes for use in the SAT algorithm
     */
    @Override
    public Vector2D[] getAxes(Body body1, Body body2) {
        // required for using paths
        double[] vertex = new double[6];
        double[] initialVertex = new double[6];
        double[] oldVertex = null;

        ArrayList<Vector2D> axes = new ArrayList<>();
        for (PathIterator iterator = body1.getCollider().getShape().getPathIterator(body1.getTransform());
             !iterator.isDone(); iterator.next()) {
            int type = iterator.currentSegment(vertex);

            // if the shape is closing, ignore the segment
            if (type == PathIterator.SEG_CLOSE) {
                continue;
            }

            if (oldVertex == null) {
                initialVertex = Arrays.copyOf(vertex, vertex.length);
            } else {
                // determine the normalized axis
                Vector2D axis = Vector2D.connect(vertex, oldVertex);
                axis.perp();
                axis.normalize();
                axes.add(axis);
            }
            oldVertex = Arrays.copyOf(vertex, vertex.length);
        }

        // determine the final normalized axis
        Vector2D axis = Vector2D.connect(initialVertex, oldVertex);
        axis.perp();
        axis.normalize();
        axes.add(axis);
        return axes.toArray(new Vector2D[axes.size()]);
    }
}
