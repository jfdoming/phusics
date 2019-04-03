package julian.dominguezschatz.engine.models;

import julian.dominguezschatz.engine.utils.TimeKeeper;

/**
 * Class: FramerateTracker
 * Author: Julian Dominguez-Schatz
 * Date: 06/04/2017
 * Description: Keeps track of the frames passed and calculates the program framerate.
 */
public class FramerateTracker {

    private static final int MILLIS_PER_FRAME_RECALCULATION = 1000;

    // frames per second (averaged)
    private double frameRateAverage;

    // the time that has elapsed since the last time the frame rate was calculated
    private double elapsedTimeSinceLastFrameRateCalculation;

    // the number of frames since the last frame rate calculation
    private int frameCountSinceLastFrameRateCalculation;

    public FramerateTracker() {
        frameRateAverage = 0;
        frameCountSinceLastFrameRateCalculation = 0;
        elapsedTimeSinceLastFrameRateCalculation = 0;
    }

    public void update(double frameElapsedTime) {
        // calculate the frame rate
        frameCountSinceLastFrameRateCalculation++;
        elapsedTimeSinceLastFrameRateCalculation += frameElapsedTime;

        /*
         * This framerate calculation waits for a specific amount of time and then
         * calculates the framerate over that time by dividing totalFrames / time.
         */
        if (elapsedTimeSinceLastFrameRateCalculation > MILLIS_PER_FRAME_RECALCULATION) {
            frameRateAverage = frameCountSinceLastFrameRateCalculation /
                    (elapsedTimeSinceLastFrameRateCalculation / TimeKeeper.MILLIS_PER_SECOND);

            frameCountSinceLastFrameRateCalculation = 0;
            elapsedTimeSinceLastFrameRateCalculation = 0;
        }
    }

    public double getFrameRate() {
        return frameRateAverage;
    }

}
