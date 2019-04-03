package julian.dominguezschatz.engine.models;

/**
 * Class: Material
 * Author: Julian Dominguez-Schatz
 * Date: 06/04/2017
 * Description: Represents a certain material with specific properties.
 */
public class Material {

    // the default material
    public static final Material DEFAULT = new Material(1, 1);

    // how "bouncy" this material is
    private final double restitution;

    // the density of this material, in kg/m^2
    private final double density;

    /**
     * Constructor.
     * @param restitution how "bouncy" this material is
     * @param density the density of this material, in kg/m^2
     */
    public Material(double restitution, double density) {
        this.restitution = restitution;
        this.density = density;
    }

    // getters

    public double getRestitution() {
        return restitution;
    }

    public double getDensity() {
        return density;
    }
}
