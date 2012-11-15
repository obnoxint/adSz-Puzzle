package net.obnoxint.adsz.puzzle;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3b;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.IOException;

import org.lwjgl.util.Point;

final class StatePieceSizeSelection extends State {

    private static final Box[] DIFFICULTIES = {
            new Box(new Point(312, 200), new Point(762, 250)), // Easiest
            new Box(new Point(312, 250), new Point(762, 300)), // Very easy
            new Box(new Point(312, 300), new Point(762, 350)), // Easy
            new Box(new Point(312, 350), new Point(762, 400)), // Normal
            new Box(new Point(312, 400), new Point(762, 450)), // Hard
            new Box(new Point(312, 450), new Point(762, 500)), // Very hard
            new Box(new Point(312, 500), new Point(762, 550))  // Insane
    };

    int selected = 0;

    StatePieceSizeSelection() throws IOException {
        super(STATE_PIECESIZESELECTION);
    }

    private void strikeUnsupportedDifficulties() {
        final int strike = Main.instance.getPuzzles()[((StatePuzzleSelection) State.getState(STATE_PUZZLESELECTION)).selected].maxDifficulty.id + 1;
        glDisable(GL_TEXTURE_2D);
        glLineWidth(3f);
        glColor3b(Main.RGB_OCHER_LIGHT[0], Main.RGB_OCHER_LIGHT[1], Main.RGB_OCHER_LIGHT[2]);
        for (int i = strike; i < DIFFICULTIES.length; i++) {
            final Box b = DIFFICULTIES[i];
            final int y = b.getUpperLeft().getY() + (b.getHeight() / 2);
            final Point from = new Point(b.getUpperLeft().getX(), y);
            final Point to = new Point(b.getLowerRight().getX(), y);
            glBegin(GL_LINE_STRIP);
            {
                glVertex2i(from.getX(), from.getY());
                glVertex2i(to.getX(), to.getY());
            }
            glEnd();
        }
        glEnable(GL_TEXTURE_2D);
        glColor3f(1f, 1f, 1f);
    }

    @Override
    void draw() {
        strikeUnsupportedDifficulties();
        drawOutline();
    }

    @Override
    void handleInput() {
        final int mx = Main.instance.mouse_abs_x;
        final int my = Main.instance.mouse_abs_y;
        final boolean mb = Main.instance.mouse_but_l;
        final int maxDifficulty = Main.instance.getPuzzles()[((StatePuzzleSelection) State.getState(STATE_PUZZLESELECTION)).selected].maxDifficulty.id;
        outlined = null; // reset outlined
        for (int i = 0; i <= maxDifficulty; i++) {
            if (DIFFICULTIES[i].isInside(mx, my)) {
                outlined = DIFFICULTIES[i];
                if (mb) {
                    selected = i;
                    State.setActiveState(STATE_PUZZLEPLAY);
                }
                break;
            }
        }
    }

}
