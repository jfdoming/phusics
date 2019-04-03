package julian.dominguezschatz.engine.views;

/**
 * Interface: RenderingStrategy
 * Author: Julian Dominguez-Schatz
 * Date: 14/02/2017
 * Description: Represents a strategy for rendering the application views.
 */
public interface RenderingStrategy {

    void render(Renderer renderer);
    void dispose();
}
