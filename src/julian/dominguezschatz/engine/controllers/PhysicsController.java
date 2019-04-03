package julian.dominguezschatz.engine.controllers;

import julian.dominguezschatz.engine.models.Body;
import julian.dominguezschatz.engine.models.World;
import julian.dominguezschatz.engine.views.Renderer;
import julian.dominguezschatz.engine.views.ShapeRenderer;

/**
 * Class: PhysicsController
 * Author: Julian Dominguez-Schatz
 * Date: 2017-09-25
 * Description: This is an application controller that simulates the physical world.
 */
public class PhysicsController extends Controller {

    // the world physics objects live in
    private World world;

    public PhysicsController(double worldWidth, double worldHeight) {
        this.world = new World(worldWidth, worldHeight);
    }

    public void addBody(Body body) {
        world.addBody(body);
        renderer.putRenderer(body.getIDInWorld(), ShapeRenderer.create(body.getCollider()
                        .getShape()));
    }

    @Override
    protected void updateModel() {
        super.updateModel();

        while (timeKeeper.canCycle()) {
            // update the physics in our world
            world.step(timeKeeper.getDeltaTime());
        }
    }

    @Override
    protected void updateView() {
        super.updateView();

        // stop rendering bodies that have been removed from the world
        for (Body body : world.getRemovedBodies()) {
            renderer.removeRenderer(body.getIDInWorld());
        }
        world.clearRemovedBodies();

        // update all views with data from the model
        for (Body body : world.getBodies()) {
            Renderer renderer = this.renderer.getRenderer(body.getIDInWorld());

            renderer.setRenderPosition(body.getPosition());
            renderer.setRenderAngle(body.getAngleRadians());
            renderer.setRenderScale(body.getScale());
        }
    }
}
