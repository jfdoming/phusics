package julian.dominguezschatz.engine.models.scripts;

import julian.dominguezschatz.engine.models.scripts.impl.Meta;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-03-15
 * Description: Represents a script loaded from file.
 */
public class Script extends NavigableList<Line> {

    private static final Command metaCommand = new Meta();

    public enum Type {
        MAP, CONFIG
    }

    private final ScriptingConfig config;
    private final ScriptInfo info;

    Script(ScriptingConfig config, Line[] lines) {
        this.config = config;
        this.elements = lines;
        info = new ScriptInfo();
    }

    void processMetadata() {
        Line currentLine = next();

        Command fileTypeCommand = metaCommand.findCommand(config, currentLine);
        if (fileTypeCommand == null) {
            System.err.println("Warning: unknown script type!");
            return;
        }

        currentLine.advance();
        currentLine.savePosition();
        fileTypeCommand.execute(currentLine, info);
        currentLine.loadPosition();

        advance();
        waitAdvance();
    }

    public void execute(CommandSet commands) {
        if ((commands == null) || (config.abortOnEmptyCommandSet && commands.isEmpty())) {
            System.err.println("No commands provided to executing script.");
            return;
        }

        while (hasNext()) {
            Line currentLine = next();

            Command commandToExecute = commands.findCommand(config, currentLine);
            if (commandToExecute == null) {
                System.err.printf("No command found matching the line \"%s\".%n", currentLine.getRawLine());
                continue;
            }

            commandToExecute.execute(currentLine, info);
        }
    }

    public ScriptInfo getInfo() {
        return info;
    }
}
