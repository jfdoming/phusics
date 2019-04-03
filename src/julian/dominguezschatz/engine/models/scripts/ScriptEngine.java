package julian.dominguezschatz.engine.models.scripts;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

/**
 * Assignment: ICS
 * Author: Julian Dominguez-Schatz
 * Date: 2017-03-15
 * Description: This class scans the resource folder for .ice scripts and sorts them based on script type.
 */
public class ScriptEngine {

    private static final String FILE_SUFFIX = ".ice";

    private final HashMap<Script.Type, HashMap<String, Script>> scripts;
    private final Tokenizer tokenizer;

    /**
     * Default constructor.
     */
    public ScriptEngine() {
        this(null);
    }

    /**
     * Default constructor.
     */
    public ScriptEngine(ScriptingConfig config) {
        tokenizer = new Tokenizer(config);

        // initialize the script list
        scripts = new HashMap<>();
        for (Script.Type type : Script.Type.values()) {
            scripts.put(type, new HashMap<String, Script>());
        }
    }

    /**
     * Performs the scanning operation and sorts the resulting files.
     *
     * @param base the directory containing the scripts to load
     */
    public void apply(String base) {
        File[] files = scan(base);
        sort(files);
    }

    private File[] scan(String base) {
        try {
            URL baseURL = ScriptEngine.class.getResource(base);
            if (baseURL == null) {
                System.err.println("Failed to locate base directory for scanning!");
                return null;
            }

            File baseDirectory = new File(baseURL.toURI());
            return baseDirectory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(FILE_SUFFIX);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new File[0];
    }

    private void sort(File[] files) {
        for (File file : files) {
            Script script = tokenizer.tokenize(file);
            script.processMetadata();
            scripts.get(script.getInfo().getType()).put(file.getName(), script);
        }
    }

    public Script get(Script.Type type, String filename) {
        return scripts.get(type).get(filename);
    }
}
