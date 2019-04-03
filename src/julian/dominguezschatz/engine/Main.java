package julian.dominguezschatz.engine;

import julian.dominguezschatz.engine.controllers.Application;
import julian.dominguezschatz.engine.controllers.ApplicationConfig;
import julian.dominguezschatz.engine.controllers.PhysicsController;
import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.utils.Log;
import julian.dominguezschatz.engine.utils.Vector2D;

import java.awt.Rectangle;

/**
 * Class: Main
 * Author: Julian Dominguez-Schatz
 * Date: 09/01/2017
 * Description: The entry point to our application.
 */
public class Main {

    // constants related to application initialization
    private static final int APP_WIDTH = 800;
    private static final int APP_HEIGHT = 600;
    private static final String GAME_TITLE = "Engine-Base";

    // runs on program startup
    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle(GAME_TITLE);
        config.setSize(APP_WIDTH, APP_HEIGHT);

        Application application = new Application(config);
        application.start();

        PhysicsController controller = new PhysicsController(800, 600);
        if (!application.registerController(0, controller)) {
            Log.wtf("Main", "Failed to register controller, exiting...");
            return;
        }
        application.setCurrentController(0);

        Body slowMovingRect = new Body.Builder()
                .setMass(2)
                .setPosition(APP_WIDTH / 2.0, 50)
                .setShape(new Rectangle(50, 50))
                .build();
//        slowMovingRect.applyPersistentForce(new Vector2D(Vector2D.POLAR, 980, 90));
        slowMovingRect.getVelocity().setPolar(50, 90);
        controller.addBody(slowMovingRect);

        Body movingRect = new Body.Builder()
                .setPosition(APP_WIDTH / 2.0, 150)
                .setShape(new Rectangle(50, 50))
                .build();
//        movingRect.applyPersistentForce(new Vector2D(Vector2D.POLAR, 980, 90));
        controller.addBody(movingRect);

        Body ground = new Body.Builder()
                .setMass(Body.INFINITE_MASS)
                .setPosition(0, APP_HEIGHT - 100)
                .setShape(new Rectangle(0, 0, APP_WIDTH, 50))
                .build();
        controller.addBody(ground);

        Body ceiling = new Body.Builder()
                .setMass(Body.INFINITE_MASS)
                .setPosition(0, 0)
                .setShape(new Rectangle(0, 0, APP_WIDTH, 50))
                .build();
        controller.addBody(ceiling);
    }

}
