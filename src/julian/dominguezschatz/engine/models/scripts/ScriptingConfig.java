package julian.dominguezschatz.engine.models.scripts;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-08-30
 * Description: Represents configuration information used when executing a script.
 */
public class ScriptingConfig {

    public boolean forcePartialMatch;
    public boolean ignoreExtraParameters;
    public boolean abortOnEmptyCommandSet;

    public ScriptingConfig() {
        forcePartialMatch = false;
        ignoreExtraParameters = false;
        abortOnEmptyCommandSet = true;
    }
}
