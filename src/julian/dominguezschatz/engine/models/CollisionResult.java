package julian.dominguezschatz.engine.models;

import julian.dominguezschatz.engine.utils.Vector2D;

/**
 * Class: CollisionResult
 * Author: Julian Dominguez-Schatz
 * Date: 22/04/2017
 * Description: Represents information about a collision.
 */
public class CollisionResult {

    // whether a collision occurred
    private final boolean collision;

    // a vector sufficient to push the two shapes apart
    private final Vector2D mtv;

    /**
     * Constructor.
     *
     * @param collision whether a collision occurred
     * @param mtv a vector sufficient to push the two shapes apart
     */
    public CollisionResult(boolean collision, Vector2D mtv) {
        this.collision = collision;
        this.mtv = mtv;
    }

    // getters

    public boolean isCollision() {
        return collision;
    }

    public Vector2D getMTV() {
        return mtv;
    }
}
