package julian.dominguezschatz.engine.views;

import julian.dominguezschatz.engine.models.UIComponentState;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Class: Renderer
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-30
 * Description: Represents any object that can be used to draw to the screen.
 */
public abstract class Renderer implements Comparable<Renderer> {

    // the child renderers
    private final HashMap<Integer, Renderer> renderers;
    private final ArrayList<Renderer> sortedRenderers;

    private UIComponentState UIComponentState;
    private int zIndex;

    // transform properties
    private Point2D.Double renderPosition;
    private double renderAngle; // in radians
    private double renderScale;

    // used for rendering only
    private AffineTransform oldTransform;

    private Renderer parent;

    /**
     * Constructor.
     */
    public Renderer() {
        renderers = new HashMap<>();
        sortedRenderers = new ArrayList<>();

        renderPosition = new Point2D.Double();
        renderAngle = 0;
        renderScale = 1;
    }

    /**
     * Initializes this view.
     *
     * @param UIComponentState information about the dimensions and left-right offsets of a component
     */
    public void initialize(UIComponentState UIComponentState) {
        this.UIComponentState = UIComponentState;

        for (Renderer renderer : sortedRenderers) {
            renderer.initialize(UIComponentState);
        }
    }

    /**
     * Adds this renderer to a parent renderer.
     *
     * @param parent the parent this renderer is being added to
     */
    void addTo(Renderer parent) {
        this.parent = parent;
    }

    void reSortChildren() {
        Collections.sort(sortedRenderers);
    }

    /**
     * Renders the target of this renderer to the screen.
     *
     * @param g the graphics object
     */
    public final void render(Graphics2D g) {
        prerender(g);

        onRender(g);
        for (Renderer renderer : sortedRenderers) {
            renderer.render(g);
        }

        postrender(g);
    }

    /**
     * Called to render the target of this renderer to the screen.
     *
     * @param g the graphics object
     */
    public abstract void onRender(Graphics2D g);

    /**
     * Called before a subclass renders its target shape to apply the appropriate transforms.
     *
     * @param g the graphics object
     */
    protected void prerender(Graphics2D g) {
        oldTransform = g.getTransform();

        // apply transforms
        g.translate(renderPosition.getX(), renderPosition.getY());
        g.rotate(renderAngle);
        g.scale(renderScale, renderScale);
    }

    /**
     * Called after a subclass renders its target shape to restore the default transform.
     *
     * @param g the graphics object
     */
    protected void postrender(Graphics2D g) {
        g.setTransform(oldTransform);
    }

    @Override
    public int compareTo(Renderer other) {
        return Integer.compare(zIndex, other.zIndex);
    }

    public UIComponentState getUIComponentState() {
        return UIComponentState;
    }

    // setters used to update the renderer with model information

    public void setRenderPosition(Point2D.Double position) {
        renderPosition.setLocation(position);
    }

    public void setRenderAngle(double renderAngle) {
        this.renderAngle = renderAngle;
    }

    public void setRenderScale(double renderScale) {
        this.renderScale = renderScale;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
        parent.reSortChildren();
    }

    public void putRenderer(int id, Renderer renderer) {
        renderer.addTo(this);

        renderers.put(id, renderer);
        sortedRenderers.add(renderer);

        reSortChildren();
    }

    public void removeRenderer(int id) {
        Renderer removed = renderers.remove(id);
        sortedRenderers.remove(removed);

        // Not needed; removing from a sorted list doesn't change the sorted property.
//        reSortChildren();
    }

    // getters
    public Renderer getRenderer(int id) {
        return renderers.get(id);
    }

}
