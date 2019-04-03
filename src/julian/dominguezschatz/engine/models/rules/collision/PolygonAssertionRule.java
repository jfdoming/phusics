package julian.dominguezschatz.engine.models.rules.collision;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date : 2017-04-22
 * Description: Implements a rule that provides various methods for asserting certain properties of polygons.Generic Object
 */
public class PolygonAssertionRule {

    public void assertPolygonalSegment(int type) {
        if (type != PathIterator.SEG_LINETO && type != PathIterator.SEG_MOVETO) {
            throw new IllegalArgumentException("Non-polygonal shapes are not supported!");
        }
    }

    public void assertPolygonalShape(Path2D.Double shape) {
        double[] vertex = new double[6];
        for (PathIterator iterator = shape.getPathIterator(null); !iterator.isDone(); iterator.next()){
            int type = iterator.currentSegment(vertex);
            if (type == PathIterator.SEG_CLOSE) {
                continue;
            }
            assertPolygonalSegment(type);
        }
    }
}
