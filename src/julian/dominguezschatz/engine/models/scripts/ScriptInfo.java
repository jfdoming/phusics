package julian.dominguezschatz.engine.models.scripts;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-08-28
 * Description: Represents information about a script loaded from file.
 */
public class ScriptInfo {

    private Script.Type type;

    public ScriptInfo() {
        type = null;
    }

    public Script.Type getType() {
        return type;
    }

    public void setType(Script.Type type) {
        this.type = type;
    }
}
