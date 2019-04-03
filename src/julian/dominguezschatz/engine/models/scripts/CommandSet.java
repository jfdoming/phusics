package julian.dominguezschatz.engine.models.scripts;

import java.util.HashSet;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-08-28
 * Description: Represents an immutable set of commands used in a script.
 */
public class CommandSet {

    private final HashSet<Command> commands;

    private int cachedHash;

    private CommandSet(HashSet<Command> commands) {
        this.commands = commands;

        computeHashCode();
    }

    Command findCommand(ScriptingConfig config, Line lineToSearch) {
        for (Command commandToTest : commands) {
            lineToSearch.savePosition();
            Command result = commandToTest.findCommand(config, lineToSearch);
            if (result != null) {
                lineToSearch.removePosition();
                return result;
            }
            lineToSearch.loadPosition();
        }
        return null;
    }

    boolean isEmpty() {
        return commands.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandSet that = (CommandSet) o;

        return cachedHash == that.cachedHash;
    }

    @Override
    public int hashCode() {
        return cachedHash;
    }

    private void computeHashCode() {
        cachedHash = commands.hashCode();
    }

    public static class Builder {

        private final HashSet<Command> commands;

        /**
         * Constructor.
         */
        public Builder() {
            commands = new HashSet<>();
        }

        public Builder add(Command command) {
            commands.add(command);
            return this;
        }

        public CommandSet build() {
            return new CommandSet(commands);
        }

    }
}
