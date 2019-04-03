package julian.dominguezschatz.engine.models.rules.collision;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date: 23/04/2017
 * Description: Implements a rule that allows the user to select certain vertices of a shape
 * based on certain criteria.Generic Object
 */
public interface VertexSelectionRule {

    Point2D.Double closest(Point2D.Double point, AffineTransform transform);
}
