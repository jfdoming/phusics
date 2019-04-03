package julian.dominguezschatz.engine.models.scripts.impl;

import julian.dominguezschatz.engine.models.scripts.Command;
import julian.dominguezschatz.engine.models.scripts.CommandInfo;
import julian.dominguezschatz.engine.models.scripts.Line;
import julian.dominguezschatz.engine.models.scripts.Script;
import julian.dominguezschatz.engine.models.scripts.ScriptInfo;
import julian.dominguezschatz.engine.models.scripts.Token;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-08-28
 * Description: Represents the type sub-command of the meta command.
 */
public class MetaType extends Command {

    private static final String COMMAND_REGEX = "type";
    private static final String PARAM1_REGEX = "\\w+";

    public MetaType() {
        super(new CommandInfo.Builder()
                .addTokenRegex(COMMAND_REGEX)
                .addTokenRegex(PARAM1_REGEX)
                .build()
        );
    }

    /**
     * Runs when a command should be executed.
     */
    @Override
    public void execute(Line line, ScriptInfo scriptInfo) {
        if (line.hasNext()) {
            Token next = line.next();
            scriptInfo.setType(Script.Type.valueOf(next.toString().toUpperCase()));
        }
    }
}
