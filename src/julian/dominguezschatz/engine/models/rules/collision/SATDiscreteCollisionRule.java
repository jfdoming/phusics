package julian.dominguezschatz.engine.models.rules.collision;

import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.models.CollisionResult;
import julian.dominguezschatz.engine.models.IntervalDouble;
import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.geom.Point2D;

/**
 * Class: SATDiscreteCollisionRule
 * Author: Julian Dominguez-Schatz
 * Date: 22/04/2017
 * Description: Represents a rule that handles collisions between bodies using discrete collision detection.
 *
 * Solution adapted from:
 * http://www.phailed.me/2011/02/polygonal-collision-detection/
 * http://www.sevenson.com.au/actionscript/sat/
 */
public class SATDiscreteCollisionRule implements CollisionRule {

    /**
     * @param axes the list of axes to test
     * @param body1 the first body
     * @param body2 the second body
     * @return whether the two bodies overlap on all the axes provided
     */
    private Vector2D shapesOverlapForAxes(Vector2D[] axes, Body body1, Body body2) {
        ProjectionRule projectionRule1 = body1.getCollider().getProjectionRule();
        ProjectionRule projectionRule2 = body2.getCollider().getProjectionRule();

        Vector2D mtv = new Vector2D();
        boolean mtvSet = false;

        for (Vector2D axis : axes) {
            IntervalDouble interval1 = projectionRule1.project(body1.getTransform(), axis);
            IntervalDouble interval2 = projectionRule2.project(body2.getTransform(), axis);
            if (!interval1.overlaps(interval2)) {
                return null;
            }

            // If the new pushback is less than the current one, update the MTV.
            double dist = interval1.getOverlap(interval2);
            if (!mtvSet || dist < mtv.getMagnitude()) {
                mtvSet = true;
                mtv.setPolar(dist, axis.getDirection());
            }
        }
        return mtv;
    }

    private Vector2D[] obtainAxes(Body body1, Body body2) {
        AxisRule axisRule = body1.getCollider().getAxisRule();
        return axisRule.getAxes(body1, body2);
    }

    /**
     * Determines whether two bodies are colliding.
     *
     * The algorithm used involves the Separating Axis Theorem (SAT), which states that two convex polygons overlap if
     * and only if they are not separated along any axis. Furthermore, in 2D, the only axes necessary to test are
     * perpendicular to the edges of the shapes being tested.
     *
     * @param body1 the first body
     * @param body2 the second body
     * @return collision information
     */
    public CollisionResult isCollision(Body body1, Body body2) {
        Vector2D[] axes1 = obtainAxes(body1, body2);
        Vector2D[] axes2 = obtainAxes(body2, body1);

        Vector2D collisionResult1 = shapesOverlapForAxes(axes1, body1, body2);
        Vector2D collisionResult2 = shapesOverlapForAxes(axes2, body1, body2);

        Vector2D mtv = null;
        boolean collision = (collisionResult1 != null && collisionResult2 != null);
        if (collision) {
            if (collisionResult1.getMagnitude() > collisionResult2.getMagnitude()) {
                mtv = collisionResult2;
            } else {
                mtv = collisionResult1;
            }

            // make sure the vector is pointing from body1 to body2
            Point2D.Double c1 = body1.getPosition();
            Point2D.Double c2 = body2.getPosition();
            Vector2D cToc = Vector2D.connect(c1, c2);
            if (cToc.dot(mtv) < 0) {
                // negate the MTV if it is not
                mtv.setDirection(mtv.getDirection() + 180);
            }
        }

        return new CollisionResult(collision, mtv);
    }

    private double getPercentMass(Body first, Body second) {
        if (second.getMass() == Body.INFINITE_MASS) {
            return 1;
        }
        return first.getMass() / second.getMass();
    }

    /**
     * @param first the first body
     * @param second the second body
     * @param mtv the minimum translation vector
     */
    public void resolveCollision(Body first, Body second, Vector2D mtv) {
        double percent1 = getPercentMass(first, second);
        double percent2 = getPercentMass(second, first);

        // push the objects apart so they are tangent
        first.translate(percent1 * mtv.getX(), percent1 * mtv.getY());
        second.translate(percent2 * -mtv.getX(), percent2 * -mtv.getY());

        Vector2D relativeVelocity = first.getVelocity().clone();
        relativeVelocity.subtract(second.getVelocity());

        Vector2D normal = mtv;
        normal.normalize();

        double velocityAlongNormal = relativeVelocity.dotPolar(normal);

        // get lowest elasticity
        double firstRestitution = first.getCollider().getMaterial().getRestitution();
        double secondRestitution = second.getCollider().getMaterial().getRestitution();
        double restitution = Math.min(firstRestitution, secondRestitution);

        // apply the impulse
        normal.scale(velocityAlongNormal);
        normal.scale(-(1 + restitution));

        Vector2D aImpulse = normal.clone();
        aImpulse.scale(first.getReciprocalMass() / (first.getReciprocalMass() + second.getReciprocalMass()));
        Vector2D bImpulse = normal.clone();
        bImpulse.scale(second.getReciprocalMass() / (first.getReciprocalMass() + second.getReciprocalMass()));

        first.getVelocity().add(aImpulse);
        second.getVelocity().subtract(bImpulse);
    }

}
