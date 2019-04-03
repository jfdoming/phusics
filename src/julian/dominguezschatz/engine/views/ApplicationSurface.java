package julian.dominguezschatz.engine.views;

import java.awt.Canvas;

/**
 * Class: ApplicationSurface
 * Author: Julian Dominguez-Schatz
 * Date: 2017-02-11
 * Description: Represents the surface the application is rendered on to.
 */
public class ApplicationSurface extends Canvas {

    /**
     * Default constructor.
     */
    public ApplicationSurface() {
        setIgnoreRepaint(true);
    }
}
