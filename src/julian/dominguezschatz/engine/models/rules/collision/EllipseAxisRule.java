package julian.dominguezschatz.engine.models.rules.collision;

import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.geom.Point2D;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date : 23/04/2017
 * Description: Implements a rule that determines axes of an ellipse for use in the SAT algorithm.Generic Object
 */
public class EllipseAxisRule implements AxisRule {

    // constructor
    public EllipseAxisRule() {
    }

    /**
     * This rule only applies to circles, i.e. ellipses with equal width and height.
     *
     * @param body1 the first body
     * @param body2 the second body
     * @return a list of axes for use in the SAT algorithm
     */
    @Override
    public Vector2D[] getAxes(Body body1, Body body2) {
        Point2D.Double position = body1.getPosition();

        VertexSelectionRule vertexSelectionRule = body2.getCollider().getVertexSelectionRule();

        Point2D.Double closestVertex = vertexSelectionRule.closest(position, body2.getTransform());
        Vector2D result = Vector2D.connect(position, closestVertex);
        result.normalize();
        return new Vector2D[] {result};
    }
}
