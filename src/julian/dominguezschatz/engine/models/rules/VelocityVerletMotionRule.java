package julian.dominguezschatz.engine.models.rules;

import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.utils.Vector2D;

/**
 * Class: VelocityVerletMotionRule
 * Author: Julian Dominguez-Schatz
 * Date: 02/02/2017
 * Description: Represents an implementation of velocity verlet integration, used to
 * integrate w.r.t time to update the state of a body.
 */
public class VelocityVerletMotionRule implements MotionRule {

    @Override
    public void integrate(double deltaTime, Body body) {
        double deltaTimeInSeconds = deltaTime / 1000.0;
        double halfDeltaTimeInSecondsSquared = 0.5 * deltaTimeInSeconds * deltaTimeInSeconds;

        // linear motion
        Vector2D acceleration = body.getAcceleration();

        Vector2D velocity = body.getVelocity();

        double dx = velocity.getX() * deltaTimeInSeconds + acceleration.getX() * halfDeltaTimeInSecondsSquared;
        double dy = velocity.getY() * deltaTimeInSeconds + acceleration.getY() * halfDeltaTimeInSecondsSquared;
        body.translate(dx, dy);

        // It's fine to mutate acceleration, since Body returns a new Vector2D instance.
        // TODO refactor this to not mutate acceleration
        acceleration.scale(deltaTimeInSeconds);
        velocity.add(acceleration);

        // rotational motion
        double angularAcceleration = body.getAngularAcceleration();
        angularAcceleration *= halfDeltaTimeInSecondsSquared;

        // we need to store this for later
        double angularVelocity = body.getAngularVelocity();
        body.setAngularVelocity(angularVelocity + (angularAcceleration * deltaTimeInSeconds));

        double angle = body.getAngle();
        angle += angularVelocity * deltaTimeInSeconds + angularAcceleration * halfDeltaTimeInSecondsSquared;
        body.setAngle(angle);
    }
}
