package julian.dominguezschatz.engine.models.rules.collision;

import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.utils.Vector2D;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date : 23/04/2017
 * Description: Implements a rule that determines axes for use in the SAT algorithm.Generic Object
 */
public interface AxisRule {

    Vector2D[] getAxes(Body body1, Body body2);
}
