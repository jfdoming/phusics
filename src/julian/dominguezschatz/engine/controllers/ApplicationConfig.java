package julian.dominguezschatz.engine.controllers;

/**
 * Class: ApplicationConfig
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: Represents a set of configuration properties used to initialize the application window.
 */
public class ApplicationConfig {

    // default values for the config
    private static final int DEFAULT_WIDTH = 0;
    private static final int DEFAULT_HEIGHT = 0;
    private static final String DEFAULT_TITLE = "";
    private static final boolean DEFAULT_CENTERED = true;
    private static final boolean DEFAULT_USING_ACTIVE_RENDERING = true;
    private static final boolean DEFAULT_USING_SYSTEM_UI = false;

    // the dimensions of the window
    private int width;
    private int height;

    // the text to display in the title bar of the window
    private String title;

    // whether to center the application on screen
    private boolean centered;

    // whether to render actively or passively
    private boolean usingActiveRendering;

    // whether to use the system UI skin
    private boolean usingSystemUI;

    // default constructor; initializes this configuration with the default values
    public ApplicationConfig() {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        title = DEFAULT_TITLE;
        centered = DEFAULT_CENTERED;
        usingActiveRendering = DEFAULT_USING_ACTIVE_RENDERING;
        usingSystemUI = DEFAULT_USING_SYSTEM_UI;
    }

    // getters

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCentered() {
        return centered;
    }

    public boolean isUsingActiveRendering() {
        return usingActiveRendering;
    }

    public boolean isUsingSystemUI() {
        return usingSystemUI;
    }

    // setters

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setUsingActiveRendering(boolean usingActiveRendering) {
        this.usingActiveRendering = usingActiveRendering;
    }

    public void setUsingSystemUI(boolean usingSystemUI) {
        this.usingSystemUI = usingSystemUI;
    }
}
