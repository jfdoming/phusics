package julian.dominguezschatz.engine.models.scripts;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-03-15
 * Description: Represents a command used in the scripting system.
 */
public abstract class Command {

    private final CommandInfo info;

    public Command(CommandInfo info) {
        this.info = info;
    }

    public Command findCommand(ScriptingConfig config, Line lineToSearch) {
        String[] tokenRegexes = info.getTokenRegexes();

        if (tokenRegexes.length == 0) {
            // no point in checking this command; the user clearly made a mistake
            System.err.println("No name hints specified for command! (maybe you forgot...)");
            return null;
        }

        int index = 0;
        int count = (info.isVArgs() ? (tokenRegexes.length - 1) : tokenRegexes.length);
        lineToSearch.savePosition();
        while (lineToSearch.hasNext() && index < count) {
            Token token = lineToSearch.next();

            // every parameter must match a parameter guideline set out when the command is declared
            if (!token.matches(tokenRegexes[index])) {
                return null;
            }

            index++;
        }

        if (index > count) {
            // we ran out of tokens, check whether we should force the command to handle the case of missing parameters
            if (config.forcePartialMatch) {
                lineToSearch.loadPosition();
                return this;
            } else {
                System.err.printf("Missing parameters in line \"%s\".%n", lineToSearch.getRawLine());
                return null;
            }
        }

        if (index == count && (info.isVArgs() || !lineToSearch.hasNext())) {
            // we had exactly the right number of tokens, hooray!!
            lineToSearch.loadPosition();
            return this;
        }

        if (info.getChildren() == null) {
            // the user supplied extra parameters, check if we should warn the user
            if (!info.isVArgs() && !config.ignoreExtraParameters) {
                System.err.printf("Extra parameters supplied in line \"%s\".%n", lineToSearch.getRawLine());
            }
            lineToSearch.loadPosition();
            return this;
        }

        lineToSearch.removePosition();

        // otherwise, more unprocessed tokens exist, and we need to check child commands
        // search through this command's children to find a matching command
        return findInChildren(config, lineToSearch);
    }

    private Command findInChildren(ScriptingConfig config, Line lineToSearch) {
        for (Command child : info.getChildren()) {
            lineToSearch.savePosition();

            Command command = child.findCommand(config, lineToSearch);
            if (command != null) {
                return command;
            }

            lineToSearch.loadPosition();
        }
        return null;
    }

    /**
     * Runs when a command should be executed. Subclasses should override this method to implement a command.
     */
    public void execute(Line line) {}

    /**
     * Runs when a command should be executed. Subclasses should override this method to implement a command.
     *
     * Provides modify access to the information about a script.
     */
    public void execute(Line line, ScriptInfo scriptInfo) {
        execute(line);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command command = (Command) o;

        if (command.info == this.info) {
            return true;
        }

        return info.equals(command.info);
    }

    @Override
    public int hashCode() {
        return info != null ? info.hashCode() : 0;
    }
}
