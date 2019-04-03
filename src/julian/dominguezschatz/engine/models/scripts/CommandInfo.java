package julian.dominguezschatz.engine.models.scripts;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dolphish on 2016-10-28.
 */
public class CommandInfo {

    private final String[] tokenRegexes;
    private final Command[] children;
    private final boolean vargs;

    private CommandInfo(Builder builder) {
        this.tokenRegexes = builder.tokenRegexes.toArray(new String[builder.tokenRegexes.size()]);
        this.children = builder.children.toArray(new Command[builder.children.size()]);
        this.vargs = builder.vargs;
    }

    String[] getTokenRegexes() {
        return tokenRegexes;
    }

    Command[] getChildren() {
        return children;
    }

    boolean isVArgs() {
        return vargs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        CommandInfo that = (CommandInfo) o;

        if (vargs != that.vargs) {
            return false;
        }

        if (!Arrays.equals(tokenRegexes, that.tokenRegexes)) {
            return false;
        }

        return Arrays.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(tokenRegexes);
        result = 31 * result + Arrays.hashCode(children);
        result = 31 * result + (vargs ? 1 : 0);
        return result;
    }

    public static class Builder {

        private ArrayList<String> tokenRegexes;
        private ArrayList<Command> children;
        private boolean vargs;

        public Builder() {
            this.tokenRegexes = new ArrayList<>();
            this.children = new ArrayList<>();
            this.vargs = false;
        }

        public Builder addTokenRegex(String tokenRegex) {
            tokenRegexes.add(tokenRegex);
            return this;
        }

        public Builder addChild(Command child) {
            children.add(child);
            return this;
        }

        public Builder setVArgs(boolean vargs) {
            this.vargs = vargs;
            return this;
        }

        public CommandInfo build() {
            return new CommandInfo(this);
        }

    }
}
