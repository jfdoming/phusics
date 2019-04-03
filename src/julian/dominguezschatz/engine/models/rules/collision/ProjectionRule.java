package julian.dominguezschatz.engine.models.rules.collision;

import julian.dominguezschatz.engine.models.IntervalDouble;
import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.geom.AffineTransform;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date: 22/04/2017
 * Description: Implements a rule that projects a shape onto an axis.Generic Object
 */
public interface ProjectionRule {

    IntervalDouble project(AffineTransform transform, Vector2D axis);
}
