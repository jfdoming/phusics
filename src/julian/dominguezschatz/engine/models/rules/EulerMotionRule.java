package julian.dominguezschatz.engine.models.rules;

import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.geom.Point2D;

/**
 * Class: EulerMotionRule
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-29
 * Description: Represents an implementation of euler integration, used to
 * integrate delta time to update the state of a body.
 */
public class EulerMotionRule implements MotionRule {

    @Override
    public void integrate(double deltaTime, Body body) {
        double deltaTimeInSeconds = deltaTime / 1000.0;

        // linear motion
        Vector2D acceleration = body.getAcceleration();
        acceleration.scale(deltaTimeInSeconds);

        Vector2D velocity = body.getVelocity();
        velocity.add(acceleration);

        final double dx = velocity.getX() * deltaTimeInSeconds;
        final double dy = velocity.getY() * deltaTimeInSeconds;
        body.translate(dx, dy);

        // rotational motion
        double angularAcceleration = body.getAngularAcceleration();
        angularAcceleration *= deltaTimeInSeconds;

        double angularVelocity = body.getAngularVelocity();
        angularVelocity += angularAcceleration * deltaTimeInSeconds;
        body.setAngularVelocity(angularVelocity);

        double angle = body.getAngle();
        angle += angularVelocity * deltaTimeInSeconds;
        body.setAngle(angle);
    }
}
