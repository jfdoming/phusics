package julian.dominguezschatz.engine.models.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-03-15
 * Description: Provides tools for taking a string of text and turning it into a list of tokens.
 */
public class Tokenizer {

    private final ScriptingConfig config;

    /**
     * Constructor.
     */
    Tokenizer(ScriptingConfig config) {
        if (config == null) {
            this.config = new ScriptingConfig();
        } else {
            this.config = config;
        }
    }

    Script tokenize(File source) {
        // used to store all tokenized lines read from file
        List<Line> lines = new ArrayList<>();

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(source));
            String rawLine;

            // read in all lines (null means there are no more lines)
            while ((rawLine = fileReader.readLine()) != null) {
                // create a Line object for token generation
                Line currentLine = new Line(rawLine);

                // generate the tokens for the current line
                currentLine.tokenize();

                // if the line is not blank or a comment, store it
                if (currentLine.isExecutable()) {
                    lines.add(currentLine);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open source file!");
        } catch (IOException e) {
            System.err.println("Failed to read source file!");
        }

        return new Script(config, lines.toArray(new Line[lines.size()]));
    }
}
