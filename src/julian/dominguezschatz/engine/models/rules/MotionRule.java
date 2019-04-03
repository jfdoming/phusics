package julian.dominguezschatz.engine.models.rules;

import julian.dominguezschatz.engine.models.Body;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date: 02/02/2017
 * Description: Represents a rule that will integrate delta time to update the state of a body.
 *
 * Children of this class are rule objects (part of the model).
 */
public interface MotionRule {

    void integrate(double deltaTime, Body body);

}
