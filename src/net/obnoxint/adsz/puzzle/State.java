package net.obnoxint.adsz.puzzle;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3b;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.lwjgl.util.Point;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

abstract class State {

    private static final String FILE_BG_PREFIX = "bg_";

    private static Map<Integer, State> states = new HashMap<>();
    private static State activeState = null;

    static final int STATE_INTRO = 0;
    static final int STATE_PUZZLESELECTION = 1;
    static final int STATE_PIECESIZESELECTION = 2;
    static final int STATE_PUZZLEPLAY = 3;

    protected static State getState(final int id) {
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
        activeState = getState(id);
        if (id == STATE_PUZZLEPLAY) {
            ((StatePuzzlePlay) activeState).init();
        }
    }

    final int id;
    final Texture bg;

    protected Box outlined = null;

    protected State(final int id) throws IOException {
        this.id = id;
        try (FileInputStream fis = new FileInputStream(new File(Main.instance.getRessourceFolder(), FILE_BG_PREFIX + id + Main.FILE_EXT_PNG))) {
            this.bg = TextureLoader.getTexture(Main.TEXTURE_TYPE_PNG, fis);
        }
    }

    protected void drawOutline() {
        if (outlined != null) {
            glDisable(GL_TEXTURE_2D);
            glLineWidth(2f);
            glColor3b(Main.RGB_OCHER_LIGHT[0], Main.RGB_OCHER_LIGHT[1], Main.RGB_OCHER_LIGHT[2]);
            for (int i = 0; i < 4; i++) {
                final Point[] p = outlined.getEdge(i, 3);
                glBegin(GL_LINE_STRIP);
                {
                    glVertex2i(p[0].getX(), p[0].getY());
                    glVertex2i(p[1].getX(), p[1].getY());
                }
                glEnd();
            }
            glEnable(GL_TEXTURE_2D);
            glColor3f(1f, 1f, 1f);
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
            glTexCoord2f(1f, bg.getHeight());
            glVertex2i(Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT);
            glTexCoord2f(0f, bg.getHeight());
            glVertex2i(0, Main.DISPLAY_HEIGHT);
        }
        glEnd();
    }

    abstract void handleInput();

}
