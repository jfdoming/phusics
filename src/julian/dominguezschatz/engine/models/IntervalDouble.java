package julian.dominguezschatz.engine.models;

/**
 * Assignment: Generic Object
 * Author: Julian Dominguez-Schatz
 * Date: 22/04/2017
 * Description: Represents a numeric interval that has a distinct minimum and maximum.Generic Object
 */
public class IntervalDouble {

    // the minimum value
    private final double min;

    // the maximum value
    private final double max;

    /**
     * Constructor.
     * @param min the minimum value
     * @param max the maximum value
     */
    public IntervalDouble(double min, double max) {
        if (min > max) {
            this.max = min;
            this.min = max;
        } else {
            this.min = min;
            this.max = max;
        }
    }

    /**
     * Copy constructor.
     * @param interval the interval to copy
     */
    public IntervalDouble(IntervalDouble interval) {
        this.min = interval.min;
        this.max = interval.max;
    }

    /**
     * @param interval the interval to compare with
     * @return whether two intervals overlap
     */
    public boolean overlaps(IntervalDouble interval) {
        return !(this.min > interval.max || interval.min > this.max);
    }

    /**
     * @param interval the interval to test
     * @return the amount of overlap between this and a given interval
     */
    public double getOverlap(IntervalDouble interval) {
        return Math.min(this.max, interval.max) - Math.max(this.min, interval.min);
    }

    /**
     * Limits a given value to be in the range of this interval.
     * @param value the value to clamp
     * @return the clamped value
     */
    public double clamp(double value) {
        if (value <= max && value >= min) {
            return value;
        } else if (max < value) {
            return max;
        } else {
            return min;
        }
    }

    @Override
    public String toString() {
        return "Interval{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
