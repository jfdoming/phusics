package julian.dominguezschatz.engine.models.scripts.impl;

import julian.dominguezschatz.engine.models.scripts.Command;
import julian.dominguezschatz.engine.models.scripts.CommandInfo;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-08-28
 * Description: Represents the meta command.
 */
public class Meta extends Command {

    private static final String COMMAND_REGEX = "meta";

    public Meta() {
        super(new CommandInfo.Builder()
                .addTokenRegex(COMMAND_REGEX)
                .addChild(new MetaType())
                .build()
        );
    }
}
