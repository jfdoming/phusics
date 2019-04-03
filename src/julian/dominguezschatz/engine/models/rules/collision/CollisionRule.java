package julian.dominguezschatz.engine.models.rules.collision;

import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.models.CollisionResult;
import julian.dominguezschatz.engine.utils.Vector2D;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date: 2017-04-06
 * Description: Represents a rule that handles collisions between bodies.Generic Object
 */
public interface CollisionRule {

    CollisionResult isCollision(Body first, Body second);
    void resolveCollision(Body first, Body second, Vector2D collisionNormal);

}
