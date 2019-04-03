package julian.dominguezschatz.engine.models.scripts;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-08-30
 * Description: Represents a single token in a line.
 */
public class Token {

    private final String rawToken;

    /**
     * Constructor.
     *
     * @param rawToken the String that this token will represent
     */
    Token(String rawToken) {
        this.rawToken = rawToken;
    }

    boolean matches(String regex) {
        return rawToken.matches(regex);
    }

    @Override
    public String toString() {
        return rawToken;
    }

    public int toInt() {
        try {
            return Integer.parseInt(rawToken);
        } catch (NumberFormatException e) {
            System.err.printf("Token %s is not an integer.%n", rawToken);
            return 0;
        }
    }

    public long toLong() {
        try {
            return Long.parseLong(rawToken);
        } catch (NumberFormatException e) {
            System.err.printf("Token %s is not an integer.%n", rawToken);
            return 0;
        }
    }

    public float toFloat() {
        try {
            return Float.parseFloat(rawToken);
        } catch (NumberFormatException e) {
            System.err.printf("Token %s is not a number.%n", rawToken);
            return 0;
        }
    }

    public double toDouble() {
        try {
            return Double.parseDouble(rawToken);
        } catch (NumberFormatException e) {
            System.err.printf("Token %s is not a number.%n", rawToken);
            return 0;
        }
    }
}
