package julian.dominguezschatz.engine.models;

import julian.dominguezschatz.engine.models.rules.collision.AxisRule;
import julian.dominguezschatz.engine.models.rules.collision.EllipseAxisRule;
import julian.dominguezschatz.engine.models.rules.collision.EllipseProjectionRule;
import julian.dominguezschatz.engine.models.rules.collision.EllipseVertexSelectionRule;
import julian.dominguezschatz.engine.models.rules.collision.PolygonAssertionRule;
import julian.dominguezschatz.engine.models.rules.collision.PolygonAxisRule;
import julian.dominguezschatz.engine.models.rules.collision.PolygonProjectionRule;
import julian.dominguezschatz.engine.models.rules.collision.PolygonVertexSelectionRule;
import julian.dominguezschatz.engine.models.rules.collision.ProjectionRule;
import julian.dominguezschatz.engine.models.rules.collision.VertexSelectionRule;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: Collider
 * Author: Julian Dominguez-Schatz
 * Date: 22/04/2017
 * Description: Represents a collider for a body. This constitutes a body's material and shape.
 */
public class Collider {

    // a rule used to verify aspects of a polygon's shape
    private static final PolygonAssertionRule POLYGON_ASSERTION_RULE = new PolygonAssertionRule();

    // rules used for collision detection that vary based on shape
    private final ProjectionRule projectionRule;
    private final AxisRule axisRule;
    private final VertexSelectionRule vertexSelectionRule;

    // the material this collider is made of
    private Material material;

    // the shape of this collider
    private Shape shape;

    /**
     * Constructor.
     * @param shape the shape of this collider
     * @param material the material this collider is made of
     */
    public Collider(Shape shape, Material material) {
        // if a null shape is given, assume the shape
        if (shape == null) {
            shape = new Ellipse2D.Double(0, 0, 0, 0);
        }
        this.shape = shape;

        // determine, based on the type of shape, what rules to use
        if (shape instanceof Ellipse2D.Double) {
            Ellipse2D.Double ellipticalShape = (Ellipse2D.Double) shape;
            projectionRule = new EllipseProjectionRule(ellipticalShape);
            axisRule = new EllipseAxisRule();
            vertexSelectionRule = new EllipseVertexSelectionRule(ellipticalShape);
        } else if (shape instanceof Path2D.Double) {
            // make sure that our polygon only has straight edges
            POLYGON_ASSERTION_RULE.assertPolygonalShape((Path2D.Double) shape);

            projectionRule = new PolygonProjectionRule((Path2D.Double) shape);
            axisRule = new PolygonAxisRule();
            vertexSelectionRule = new PolygonVertexSelectionRule((Path2D.Double) shape);
        } else if (shape instanceof Rectangle2D) {
            Path2D.Double shapePath = new Path2D.Double(shape);
            projectionRule = new PolygonProjectionRule(shapePath);
            axisRule = new PolygonAxisRule();
            vertexSelectionRule = new PolygonVertexSelectionRule(shapePath);
        } else {
            throw new IllegalArgumentException("Unknown shape!");
        }

        // if a null material is given, assume the material
        if (material == null) {
            material = Material.DEFAULT;
        }
        this.material = material;
    }

    // getters

    public Material getMaterial() {
        return material;
    }

    public Shape getShape() {
        return shape;
    }

    public ProjectionRule getProjectionRule() {
        return projectionRule;
    }

    public AxisRule getAxisRule() {
        return axisRule;
    }

    public VertexSelectionRule getVertexSelectionRule() {
        return vertexSelectionRule;
    }
}
