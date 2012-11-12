package net.obnoxint.adsz.puzzle;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

abstract class State {

    private static Map<Integer, State> states = new HashMap<>();
    private static State activeState = null;

    static final int STATE_INTRO = 0;
    static final int STATE_PUZZLESELECTION = 1;
    static final int STATE_PIECESIZESELECTION = 2;
    static final int STATE_PUZZLEPLAY = 3;

    private static State getState(final int id) {
        State r = null;
        if (states.containsKey(id)) {
            r = states.get(id);
        } else {
            try {
                switch (id) {
                case STATE_INTRO:
                    r = new StateIntro();
                break;
                case STATE_PUZZLESELECTION:
                    r = new StatePuzzleSelection();
                break;
                case STATE_PIECESIZESELECTION:
                    r = new StatePieceSizeSelection();
                break;
                case STATE_PUZZLEPLAY:
                    r = new StatePuzzlePlay();
                break;
                }
                if (r != null) {
                    states.put(r.id, r);
                }
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to initialize state " + id + ": " + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Main.writeStackTrace(e);
                System.exit(Main.EXIT_CODE_ERROR);
            }
        }
        return r;
    }

    static State getActiveState() {
        return activeState;
    }

    static void setActiveState(final int id) {
        Main.logger.info("Activating state " + id + ".");
        activeState = getState(id);
    }

    final int id;
    final Texture bg;

    protected State(final int id) throws IOException {
        this.id = id;
        try (FileInputStream fis = new FileInputStream(new File(Main.instance.getRessourceFolder(), "bg_" + id + ".png"))){
            this.bg = TextureLoader.getTexture(Main.TEXTURE_TYPE_PNG, fis);
        }
    }

    abstract void draw();

    final void drawBackground() {
        bg.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0f, 0f);
            glVertex2i(0, 0);
            glTexCoord2f(1f, 0f);
            glVertex2i(Main.DISPLAY_WIDTH, 0);
            glTexCoord2f(1f, (float) Main.DISPLAY_HEIGHT / (float) Main.DISPLAY_WIDTH);
            glVertex2i(Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT);
            glTexCoord2f(0f, (float) Main.DISPLAY_HEIGHT / (float) Main.DISPLAY_WIDTH);
            glVertex2i(0, Main.DISPLAY_HEIGHT);
        }
        glEnd();
    }

    abstract void handleInput();

}
