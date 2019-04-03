package julian.dominguezschatz.engine.utils;

import java.awt.geom.Point2D;

/**
 * Class: Vector2D
 * Author: Julian Dominguez-Schatz
 * Date: 27/01/2017
 * Description: Implements a mathematical vector. This implementation supports both cartesian and polar coordinates.
 */
public class Vector2D implements Cloneable {

    // constants used to choose vector storage type
    public static final int POLAR = 0;
    public static final int CARTESIAN = 1;

    // the number of degrees in a full circle
    private static final int DEGREES_PER_CIRCLE = 360;

    // polar
    private double magnitude;
    private double direction;
    private double directionInRadians;

    //cartesian
    private double x;
    private double y;

    // whether a particular type of storage method is outdated relative to the other one
    private boolean cartesianOutdated;
    private boolean polarOutdated;

    // default constructor
    public Vector2D() {
        this(POLAR, 0, 0);
    }

    /**
     * @param type the type of coordinates provided
     * @param param1 the first argument
     * @param param2 the second argument
     */
    public Vector2D(int type, double param1, double param2) {
        if (type == POLAR) {
            setPolar(param1, param2);
        } else if (type == CARTESIAN) {
            setCartesian(param1, param2);
        } else {
            throw new IllegalArgumentException("Unknown type!");
        }
    }

    /**
     * Copy constructor.
     * @param other the vector to copy
     */
    public Vector2D(Vector2D other) {
        set(other);
    }

    /**
     * Point constructor. This takes a point and represents it as a vector from the origin.
     * @param point the point to copy
     */
    public Vector2D(Point2D.Double point) {
        setCartesian(point.x, point.y);
    }

    /**
     * Updates the cartesian coordinates of this vector.
     */
    private void updateCartesian() {
        if (cartesianOutdated) {
            cartesianOutdated = false;

            this.x = Math.cos(this.directionInRadians) * this.magnitude;
            this.y = Math.sin(this.directionInRadians) * this.magnitude;
        }
    }

    /**
     * Updates the polar coordinates of this vector.
     */
    private void updatePolar() {
        if (polarOutdated) {
            polarOutdated = false;

            this.magnitude = Math.sqrt((this.x * this.x) + (this.y * this.y));
            this.directionInRadians = Math.atan2(this.y, this.x);
            this.direction = Math.toDegrees(this.directionInRadians);

            if (direction < 0) {
                direction += DEGREES_PER_CIRCLE;
            }
        }
    }

    // getters

    public double getMagnitude() {
        updatePolar();
        return magnitude;
    }

    public double getMagnitudeSq() {
        updateCartesian();
        return (this.x * this.x) + (this.y * this.y);
    }

    public double getDirection() {
        updatePolar();
        return direction;
    }

    public double getDirectionRadians() {
        updatePolar();
        return directionInRadians;
    }

    public double getX() {
        updateCartesian();
        return x;
    }

    public double getY() {
        updateCartesian();
        return y;
    }

    // setters

    public void setMagnitude(double magnitude) {
        updatePolar();

        this.magnitude = magnitude;
        cartesianOutdated = true;
    }

    public void setDirection(double direction) {
        updatePolar();

        this.direction = direction % DEGREES_PER_CIRCLE;
        if (this.direction < 0) {
            this.direction += DEGREES_PER_CIRCLE;
        }
        this.directionInRadians = Math.toRadians(this.direction);

        cartesianOutdated = true;
    }

    public void setX(double x) {
        updateCartesian();
        this.x = x;
        polarOutdated = true;
    }

    public void setY(double y) {
        updateCartesian();
        this.y = y;
        polarOutdated = true;
    }

    public void setCartesian(double x, double y) {
        updateCartesian();

        this.x = x;
        this.y = y;

        polarOutdated = true;
    }

    public void setPolar(double magnitude, double direction) {
        updatePolar();

        this.magnitude = magnitude;
        setDirection(direction);
    }

    /**
     * Sets the value of this vector to that of another vector.
     * @param other the vector to copy
     */
    public void set(Vector2D other) {
        this.magnitude = other.magnitude;
        this.direction = other.direction;
        this.directionInRadians = other.directionInRadians;

        this.x = other.x;
        this.y = other.y;

        /*
         * Some coordinates may be outdated after copying from the original vector. In order to
         * completely preserve vector state, these booleans must also be copied.
         */
        this.cartesianOutdated = other.cartesianOutdated;
        this.polarOutdated = other.polarOutdated;
    }

    /**
     * Sets the magnitude and direction of this vector to 0.
     */
    public void clear() {
        setPolar(0, 0);
    }

    /**
     * Adds a vector to this vector.
     *
     * @param other the vector to add
     */
    public void add(Vector2D other) {
        updateCartesian();
        other.updateCartesian();

        this.x += other.x;
        this.y += other.y;

        polarOutdated = true;
    }

    /**
     * Subtracts a vector from this vector.
     *
     * @param other the vector to subtract
     */
    public void subtract(Vector2D other) {
        updateCartesian();
        other.updateCartesian();

        this.x -= other.x;
        this.y -= other.y;

        polarOutdated = true;
    }

    /**
     * @param scalar the scalar quantity to scale this vector by
     */
    public void scale(double scalar) {
        updatePolar();

        // negative values point the vector in the opposite direction
        if (scalar < 0) {
            setDirection(direction - 180);
        }

        this.magnitude *= Math.abs(scalar);
        cartesianOutdated = true;
    }

    /**
     * Determines the dot product of this vector and another vector.
     *
     * @param other the vector to perform the dot product with
     * @return the dot product of the two vectors
     */
    public double dot(Vector2D other) {
        updateCartesian();
        other.updateCartesian();

        return (this.x * other.x) + (this.y * other.y);
    }

    /**
     * Determines the dot product of this vector and another vector, using polar coordinates.
     * This method exists for optimization purposes.
     *
     * @param other the vector to perform the dot product with
     * @return the dot product of the two vectors
     */
    public double dotPolar(Vector2D other) {
        updatePolar();
        other.updatePolar();

        return this.magnitude * other.magnitude * Math.cos(this.directionInRadians - other.directionInRadians);
    }

    /**
     * Determines the cross product between this vector and another vector. Since cross product is not defined
     * for 2D vectors, this method treats them as 3D vectors with a z-component of 0.
     *
     * See http://stackoverflow.com/questions/243945/calculating-a-2d-vectors-cross-product for more information.
     *
     * @param other the vector to perform the cross product with
     * @return the cross product of this vector with another vector
     */
    public double cross(Vector2D other) {
        updateCartesian();
        other.updateCartesian();
        return (this.x * other.y) - (this.y * other.x);
    }

    /**
     * Changes this vector to a unit vector (normalization).
     */
    public void normalize() {
        updatePolar();
        this.magnitude = 1;

        cartesianOutdated = true;
    }

    public void perp() {
        updatePolar();
        setDirection(direction + 90);
        cartesianOutdated = true;
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        updatePolar();

        return "Vector2D{" +
                "magnitude=" + magnitude +
                ", direction=" + direction +
                '}';
    }

    /**
     * Duplicates this vector.
     */
    @Override
    public Vector2D clone() {
        try {
            return (Vector2D) super.clone();
        } catch (CloneNotSupportedException e) {
            // should never occur
            System.err.println("Cloning not supported!");
        }
        return null;
    }

    // static utility methods

    /**
     * @param to the first vertex
     * @param from another vertex
     * @return a Vector2D representing the movement from one vertex to the other
     */
    public static Vector2D connect(double[] to, double[] from) {
        return new Vector2D(Vector2D.CARTESIAN, to[0] - from[0], to[1] - from[1]);
    }

    /**
     * @param to the first vertex
     * @param from another vertex
     * @return a Vector2D representing the movement from one vertex to the other
     */
    public static Vector2D connect(Point2D.Double to, Point2D.Double from) {
        return new Vector2D(Vector2D.CARTESIAN, to.getX() - from.getX(), to.getY() - from.getY());
    }
}
