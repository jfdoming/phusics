package julian.dominguezschatz.engine.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Class: DebugTextRenderer
 * Author: Julian Dominguez-Schatz
 * Date: 2017-01-30
 * Description: This object is used to render debug text displayed on-screen
 */
public class DebugTextRenderer extends Renderer {

    // a list of all the text to render this frame
    private ArrayList<String> debugText;

    // style properties
    private Font renderFont;
    private Color renderColor;

    /**
     * Constructor.
     */
    public DebugTextRenderer() {
        debugText = new ArrayList<>();
        renderColor = Color.WHITE;
        renderFont = new Font("Arial", Font.PLAIN, 12);
    }

    // setters

    public void addDebugText(String text) {
        debugText.add(text);
    }

    public void setRenderFont(Font renderFont) {
        this.renderFont = renderFont;
    }

    public void setRenderColor(Color renderColor) {
        this.renderColor = renderColor;
    }

    @Override
    public void onRender(Graphics2D g) {
        g.setFont(renderFont);
        g.setColor(renderColor);
        for (int index = 0; index < debugText.size(); index++) {
            String frameRateText = debugText.get(index);
            g.drawString(frameRateText, 0, (g.getFont().getSize() * (index + 1)));
        }
        debugText.clear();
    }
}
