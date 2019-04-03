package julian.dominguezschatz.engine.models;

import julian.dominguezschatz.engine.models.rules.MotionRule;
import julian.dominguezschatz.engine.models.rules.VelocityVerletMotionRule;
import julian.dominguezschatz.engine.models.rules.collision.CollisionRule;
import julian.dominguezschatz.engine.models.rules.collision.SATDiscreteCollisionRule;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class: World
 * Author: Julian Dominguez-Schatz
 * Date: 2017-04-03
 * Description: Represents a world in which bodies may interact.
 */
public class World {

    private static final MotionRule motionRule = new VelocityVerletMotionRule();
    private static final CollisionRule collisionRule = new SATDiscreteCollisionRule();

    // the bodies in this world
    private ArrayList<Body> bodies;

    // the bodies that were removed from the world this frame
    private ArrayList<Body> removedBodies;

    // dimensions of the world
    private final double width;
    private final double height;

    // the latest assigned ID
    private int latestId;

    /**
     * Default constructor.
     * @param width the width of the world
     * @param height the height of the world
     */
    public World(double width, double height) {
        this.width = width;
        this.height = height;

        bodies = new ArrayList<>();
        removedBodies = new ArrayList<>();
    }

    /**
     * Spawns a body within the world. This means it will be automatically updated and will collide
     * with other world objects.
     * @param body the body to add
     */
    public void addBody(Body body) {
        body.addToWorld(this, latestId++);
        bodies.add(body);
    }

    /**
     * Steps the physics simulation. This includes integrating time and resolving collisions (and later constraints).
     *
     * @param deltaTime the elapsed time in milliseconds
     */
    public void step(double deltaTime) {
        Iterator<Body> iterator = bodies.iterator();

        while (iterator.hasNext()) {
            Body body = iterator.next();
            if (body.isDead() && body.isDestroyOnDeath()) {
                body.removeFromWorld();

                removedBodies.add(body);
                iterator.remove();
                continue;
            }

            body.updateTransform();
            motionRule.integrate(deltaTime, body);
            body.updateTransform();
        }

        for (Body body : bodies) {
            for (Body secondBody : bodies) {
                // the same body cannot collide with itself
                if (secondBody.equals(body)) {
                    continue;
                }

                // two bodies should only collide once per frame
                if (!body.shouldCollide(secondBody)) {
                    continue;
                }
                if (!secondBody.shouldCollide(body)) {
                    continue;
                }

                // ensure these two bodies will not collide again
                secondBody.addFrameCollision(body);
                body.addFrameCollision(secondBody);

                // test for a collision
                CollisionResult result = collisionRule.isCollision(body, secondBody);
                if (result.isCollision()) {
                    collisionRule.resolveCollision(body, secondBody, result.getMTV());
                }
            }
        }

        // prepare the bodies for next frame
        for (Body body : bodies) {
            body.clearForces();
            body.clearFrameCollisions();
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }

    public ArrayList<Body> getRemovedBodies() {
        return removedBodies;
    }

    public void clearRemovedBodies() {
        removedBodies.clear();
    }
}
