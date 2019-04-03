package julian.dominguezschatz.engine.models;

import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Class: Body
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-29
 * Description: Represents a body in the physics simulation.
 */
public class Body {

    public static final double INFINITE_MASS = 0;

    // the center of this entity
    protected final Point2D.Double position;

    // the velocity of this entity, in m/s
    protected final Vector2D velocity;

    // the acceleration of this entity, in m/s^2
    protected final Vector2D acceleration;

    // the sum of all forces acting on this body, in N
    protected final Vector2D netForce;

    // the sum of all forces acting on this body that are continuous from frame to frame, in N
    protected final Vector2D persistentNetForce;

    // the angle this entity is rotated to, in degrees
    protected double angle;

    // the angle this entity is rotated to, in radians
    protected double angleRadians;

    // the angular velocity of this entity, in deg/s
    protected double angularVelocity;

    // the angular acceleration of this entity, in deg/s^2
    protected double angularAcceleration;

    protected double torque;

    // the scale of this body, as a factor
    protected double scale;

    // whether this body is dead
    protected boolean dead;

    // the id of this body in the world
    protected int idInWorld;

    // non-modifiable properties
    protected double mass;
    protected double reciprocalMass;
    protected boolean destroyOnDeath;

    // represents this body's form in the physics simulation
    protected Collider collider;

    // list of collisions that occurred this frame
    protected final ArrayList<Body> frameCollisions;

    // cached transform
    private AffineTransform transform;

    // the world this body is situated in, or null if it has not been added to a world
    protected World world;

    private boolean positionOutdated = true;
    private boolean angleOutdated = true;
    private boolean scaleOutdated = true;

    /**
     * Default constructor.
     */
    private Body() {
        position = new Point2D.Double();
        velocity = new Vector2D();
        acceleration = new Vector2D();
        netForce = new Vector2D();
        persistentNetForce = new Vector2D();

        angle = 0;
        angleRadians = 0;
        angularVelocity = 0;
        angularAcceleration = 0;
        torque = 0;

        scale = 1;

        dead = false;
        destroyOnDeath = true;

        // a non-positive mass cannot exist!
        mass = 1;

        // world starts off null to signify "no world"
        world = null;
        frameCollisions = new ArrayList<>();

        transform = new AffineTransform();
        updateTransform();
    }

    /**
     * Applies a force to this body. This performs the vector addition between the net force and the
     * applied force. This force will be applied for the duration of one cycle, i.e. it must be applied
     * every cycle.
     * @param force the force to apply
     */
    public void applyForce(Vector2D force) {
        netForce.add(force);
    }

    /**
     * Applies a force to this body. This performs the vector addition between the net force and the applied force.
     * @param force the force to apply
     */
    public void applyPersistentForce(Vector2D force) {
        persistentNetForce.add(force);
    }

    /**
     * Resets the net force vector to a 0-vector.
     */
    protected void clearForces() {
        netForce.clear();
        netForce.add(persistentNetForce);
    }

    /**
     * @param world the world this body is being added to
     */
    void addToWorld(World world, int idInWorld) {
        this.world = world;
        this.idInWorld = idInWorld;
    }

    /**
     * Registers a collision occurring this frame, so it will not be computed twice.
     *
     * @param body the body being collided with
     */
    void addFrameCollision(Body body) {
        frameCollisions.add(body);
    }

    /**
     * Determines whether a collision response should take place.
     *
     * @param body the body being collided with
     * @return whether a collision should occur
     */
    boolean shouldCollide(Body body) {
        return !frameCollisions.contains(body);
    }

    /**
     * Resets the collisions that have occurred this frame, so that two bodies may collide next frame.
     */
    void clearFrameCollisions() {
        frameCollisions.clear();
    }

    void updateTransform() {
        if (positionOutdated || angleOutdated || scaleOutdated) {
            transform.setToTranslation(position.getX(), position.getY());
            transform.rotate(angleRadians);
            transform.scale(scale, scale);
        }

        /*if (positionOutdated) {
            transform.setToTranslation(position.getX(), position.getY());
            positionOutdated = false;
        }

        if (angleOutdated) {
            transform.setToRotation(angleRadians);
            angleOutdated = false;
        }

        if (scaleOutdated) {
            transform.setToScale(scale, scale);
            scaleOutdated = false;
        }*/
    }

    void removeFromWorld() {
        world = null;
    }

    // getters

    public Vector2D getAcceleration() {
        acceleration.set(netForce);
        acceleration.scale(reciprocalMass);
        return acceleration;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public double getAngle() {
        return angle;
    }

    public double getAngleRadians() {
        return angleRadians;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    public double getTorque() {
        return torque;
    }

    public double getScale() {
        return scale;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isDestroyOnDeath() {
        return destroyOnDeath;
    }

    public double getMass() {
        return mass;
    }

    public double getReciprocalMass() {
        return reciprocalMass;
    }

    public World getWorld() {
        return world;
    }

    public int getIDInWorld() {
        return idInWorld;
    }

    public Collider getCollider() {
        return collider;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    // setters

    public void setPosition(double x, double y) {
        position.setLocation(x, y);
        positionOutdated = true;
    }

    public void setPosition(Point2D.Double position) {
        if (position == null) {
            return;
        }

        this.position.setLocation(position);
        positionOutdated = true;
    }

    public void translate(double dx, double dy) {
        position.x += dx;
        position.y += dy;
        positionOutdated = true;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.angleRadians = Math.toRadians(angle);
        angleOutdated = true;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void setAngularAcceleration(double angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setScale(double scale) {
        this.scale = scale;
        scaleOutdated = true;
    }

    /**
     * Used to create new bodies. This is a nicer way of writing a constructor.
     */
    public static class Builder {

        private boolean destroyOnDeath;
        private double mass;
        private double reciprocalMass;
        private Material material;
        private Shape shape;
        private Point2D.Double position;

        public Builder() {
            setMass(1);
            setDestroyOnDeath(false);
        }

        public Builder setDestroyOnDeath(boolean destroyOnDeath) {
            this.destroyOnDeath = destroyOnDeath;
            return this;
        }

        public Builder setMass(double mass) {
            if (mass < 0) {
                throw new IllegalArgumentException("Mass of a body must be >= 0!");
            }

            this.mass = mass;

            // calculate the reciprocal of the mass, to be cached
            if (mass == INFINITE_MASS) {
                // 1 / 0 is undefined, so leave the value as INFINITE_MASS
                reciprocalMass = INFINITE_MASS;
            } else {
                this.reciprocalMass = 1.0 / mass;
            }
            return this;
        }

        public Builder setMaterial(Material material) {
            this.material = material;
            return this;
        }

        public Builder setShape(Shape shape) {
            this.shape = shape;
            return this;
        }

        public Builder setPosition(double initialX, double initialY) {
            this.position = new Point2D.Double(initialX, initialY);
            return this;
        }

        private void setupBody(Body body) {
            body.collider = new Collider(shape, material);
            body.destroyOnDeath = destroyOnDeath;
            body.mass = mass;
            body.reciprocalMass = reciprocalMass;
            body.setPosition(position);
            body.updateTransform();
        }

        public Body build() {
            Body body = new Body();
            setupBody(body);
            return body;
        }
    }
}
