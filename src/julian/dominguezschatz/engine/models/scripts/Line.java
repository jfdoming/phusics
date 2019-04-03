package julian.dominguezschatz.engine.models.scripts;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-08-27
 * Description: Represents a line in a script.
 */
public class Line extends NavigableList<Token> {

    // the regex used to separate tokens
    private static final String TOKEN_SEPARATOR = "\\s+";

    // the prefix used to start comment lines
    private static final String COMMENT_PREFIX = "//";

    private String rawLine;

    // whether or not this line contains characters that could potentially be executed as commands
    private boolean executable;

    /**
     * Constructor.
     *
     * @param rawLine the String that this line will represent
     */
    Line(String rawLine) {
        this.rawLine = rawLine;

        executable = false;
    }

    String getRawLine() {
        return rawLine;
    }

    void tokenize() {
        // remove leading and trailing whitespace
        rawLine = rawLine.trim();

        // ignore empty lines
        if (rawLine.isEmpty()) {
            return;
        }

        // ignore comments
        if (rawLine.startsWith(COMMENT_PREFIX)) {
            return;
        }

        String[] rawTokens = rawLine.split(TOKEN_SEPARATOR);
        elements = new Token[rawTokens.length];
        for (int i = 0; i < rawTokens.length; i++) {
            elements[i] = new Token(rawTokens[i]);
        }
        executable = true;
    }

    boolean isExecutable() {
        return executable;
    }
}
