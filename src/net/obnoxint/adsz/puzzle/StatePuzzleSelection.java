package net.obnoxint.adsz.puzzle;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.lwjgl.util.Point;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

final class StatePuzzleSelection extends State {

    private static final String RES_ARROW_PREV = "n" + Main.FILE_EXT_PNG;
    private static final String RES_ARROW_NEXT = "p" + Main.FILE_EXT_PNG;

    private static final Box ARROW_PREV = new Box(new Point(81, 325), new Point(131, 525));
    private static final Box ARROW_NEXT = new Box(new Point(893, 325), new Point(943, 525));
    private static final Box PUZZLE = new Box(new Point(212, 200), new Point(812, 650));

    private boolean switchCooldown = false;
    private Texture arrowPrev = null;
    private Texture arrowNext = null;

    int selected = 0;

    StatePuzzleSelection() throws IOException {
        super(STATE_PUZZLESELECTION);
    }

    private void drawArrowNext() {
        final Texture t = getArrowNext();
        t.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0f, 0f);
            glVertex2i(ARROW_NEXT.getUpperLeft().getX(), ARROW_NEXT.getUpperLeft().getY());
            glTexCoord2f(t.getWidth(), 0f);
            glVertex2i(ARROW_NEXT.getUpperLeft().getX() + ARROW_NEXT.getWidth(), ARROW_NEXT.getUpperLeft().getY());
            glTexCoord2f(t.getWidth(), t.getHeight());
            glVertex2i(ARROW_NEXT.getUpperLeft().getX() + ARROW_NEXT.getWidth(), ARROW_NEXT.getUpperLeft().getY() + ARROW_NEXT.getHeight());
            glTexCoord2f(0f, t.getHeight());
            glVertex2i(ARROW_NEXT.getUpperLeft().getX(), ARROW_NEXT.getUpperLeft().getY() + ARROW_NEXT.getHeight());
        }
        glEnd();
    }

    private void drawArrowPrev() {
        final Texture t = getArrowPrev();
        t.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0f, 0f);
            glVertex2i(ARROW_PREV.getUpperLeft().getX(), ARROW_PREV.getUpperLeft().getY());
            glTexCoord2f(t.getWidth(), 0f);
            glVertex2i(ARROW_PREV.getUpperLeft().getX() + ARROW_PREV.getWidth(), ARROW_PREV.getUpperLeft().getY());
            glTexCoord2f(t.getWidth(), t.getHeight());
            glVertex2i(ARROW_PREV.getUpperLeft().getX() + ARROW_PREV.getWidth(), ARROW_PREV.getUpperLeft().getY() + ARROW_PREV.getHeight());
            glTexCoord2f(0f, t.getHeight());
            glVertex2i(ARROW_PREV.getUpperLeft().getX(), ARROW_PREV.getUpperLeft().getY() + ARROW_PREV.getHeight());
        }
        glEnd();
    }

    private void drawPuzzle() {
        final Texture t = Main.instance.getPuzzles()[selected].texture;
        t.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0f, 0f);
            glVertex2i(PUZZLE.getUpperLeft().getX(), PUZZLE.getUpperLeft().getY());
            glTexCoord2f(t.getWidth(), 0f);
            glVertex2i(PUZZLE.getUpperLeft().getX() + PUZZLE.getWidth(), PUZZLE.getUpperLeft().getY());
            glTexCoord2f(t.getWidth(), t.getHeight());
            glVertex2i(PUZZLE.getUpperLeft().getX() + PUZZLE.getWidth(), PUZZLE.getUpperLeft().getY() + PUZZLE.getHeight());
            glTexCoord2f(0f, t.getHeight());
            glVertex2i(PUZZLE.getUpperLeft().getX(), PUZZLE.getUpperLeft().getY() + PUZZLE.getHeight());
        }
        glEnd();
    }

    private Texture getArrowNext() {
        if (arrowNext == null) {
            try (FileInputStream fis = new FileInputStream(new File(Main.instance.getRessourceFolder(), RES_ARROW_NEXT))) {
                arrowNext = TextureLoader.getTexture(Main.TEXTURE_TYPE_PNG, fis);
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Could not load texture: n.png", "Error", JOptionPane.ERROR_MESSAGE);
                Main.writeStackTrace(e);
                System.exit(Main.EXIT_CODE_ERROR);
            }
        }
        return arrowNext;
    }

    private Texture getArrowPrev() {
        if (arrowPrev == null) {
            try (FileInputStream fis = new FileInputStream(new File(Main.instance.getRessourceFolder(), RES_ARROW_PREV))) {
                arrowPrev = TextureLoader.getTexture(Main.TEXTURE_TYPE_PNG, fis);
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Could not load texture: p.png", "Error", JOptionPane.ERROR_MESSAGE);
                Main.writeStackTrace(e);
                System.exit(Main.EXIT_CODE_ERROR);
            }
        }
        return arrowPrev;
    }

    private void selectNext() {
        if (selected == Main.instance.getPuzzles().length - 1) {
            setSelected(0);
        } else {
            setSelected(selected + 1);
        }
    }

    private void selectPrevious() {
        if (selected == 0) {
            setSelected(Main.instance.getPuzzles().length - 1);
        } else {
            setSelected(selected - 1);
        }
    }

    private void setSelected(final int selected) {
        if (!switchCooldown) {
            this.selected = selected;
            new Thread() {

                @Override
                public void run() {
                    try {
                        switchCooldown = true;
                        sleep(500);
                    } catch (final InterruptedException e) {} finally {
                        switchCooldown = false;
                    }
                }
            }.start();
        }
    }

    @Override
    void draw() {
        drawPuzzle();
        drawArrowNext();
        drawArrowPrev();
        drawOutline();
    }

    @Override
    void handleInput() {
        final int mx = Main.instance.mouse_abs_x;
        final int my = Main.instance.mouse_abs_y;
        final boolean mb = Main.instance.mouse_but_l;
        if (ARROW_NEXT.isInside(mx, my)) {
            outlined = ARROW_NEXT;
            if (mb) {
                selectNext();
            }
        } else if (ARROW_PREV.isInside(mx, my)) {
            outlined = ARROW_PREV;
            if (mb) {
                selectPrevious();
            }
        } else if (PUZZLE.isInside(mx, my)) {
            outlined = PUZZLE;
            if (mb) {
                State.setActiveState(State.STATE_PIECESIZESELECTION);
                try { // Try to prevent accidently selecting a difficulty in the next state.
                    Main.instance.mouse_but_l = false;
                    Thread.sleep(200);
                } catch (final InterruptedException e) {}
            }
        } else {
            outlined = null;
        }
    }

}
