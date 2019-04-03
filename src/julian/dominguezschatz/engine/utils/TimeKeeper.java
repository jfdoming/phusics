package julian.dominguezschatz.engine.utils;

/**
 * Class: TimeKeeper
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-29
 * Description: Keeps track of elapsed time per frame and provides utilities for frame rate-independent motion.
 */
public class TimeKeeper {

    public static final int MILLIS_PER_SECOND = 1000;

    // the time the previous frame occurred at
    private long previousTime;

    // the amount of time that has elapsed during the last frame
    private double frameElapsedTime;

    // the total amount of time the simulation has been running for
    private long elapsedTime;

    // the target number of frames per second
    private long frameRateTarget;

    // the target time each frame should take
    private double frameTimeTarget;

    // the minimum number of frames per second
    private int minimumFrameRate;

    // the maximum time each frame should take
    private double maximumFrameTime;

    // the amount of time that has passed since the last physics step
    private double accumulator;

    private boolean running;

    /**
     * Constructor.
     *
     * @param frameRateTarget the target framerate
     * @param minimumFrameRate the lowest framerate allowed before steps are dropped
     */
    public TimeKeeper(int frameRateTarget, int minimumFrameRate) {
        this.frameRateTarget = frameRateTarget;
        this.frameTimeTarget = MILLIS_PER_SECOND / (1.0 * this.frameRateTarget);

        this.minimumFrameRate = minimumFrameRate;
        this.maximumFrameTime = MILLIS_PER_SECOND / (1.0 * this.minimumFrameRate);

        frameElapsedTime = 0;
        elapsedTime = 0;
        accumulator = 0;
        previousTime = 0;

        running = false;
    }

    /**
     * Updates this timekeeper for the current time.
     */
    public void update() {
        if (!running) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        // determine the time that has elapsed since the previous frame
        frameElapsedTime = currentTime - previousTime;
        if (frameElapsedTime > maximumFrameTime) {
            frameElapsedTime = maximumFrameTime;
        }

        previousTime = currentTime;
        accumulator += frameElapsedTime;
    }

    public void pause() {
        running = false;

        frameElapsedTime = 0;
        elapsedTime = 0;
        previousTime = 0;
    }

    public void resume() {
        running = true;
        previousTime = System.currentTimeMillis();
    }

    /**
     * @return whether or not the simulation can cycle
     */
    public boolean canCycle() {
        if (accumulator < frameTimeTarget) {
            return false;
        }

        accumulator -= frameTimeTarget;
        elapsedTime += frameTimeTarget;
        return true;
    }

    // getters

    public long getElapsedTime() {
        return elapsedTime;
    }

    public double getFrameElapsedTime() {
        return frameElapsedTime;
    }

    public double getDeltaTime() {
        return frameTimeTarget;
    }
}
