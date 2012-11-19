package net.obnoxint.adsz.puzzle;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.lwjgl.util.Point;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

final class StatePuzzlePlay extends State {

    static final Box SOLVE_AREA = new Box(new Point(112, 84), new Point(912, 684));

    PuzzleGame game;
    long started = -1L;

    StatePuzzlePlay() throws IOException {
        super(STATE_PUZZLEPLAY);
    }

    private void drawPieces() {
        final SpriteSheet s = game.sheet;
        game.updatePieceList();
        s.startUse();
        for (final Piece p : game.pieceList) {
            s.renderInUse(p.getUpperLeft().getX(), p.getUpperLeft().getY(), p.x, p.y);
        }
        s.endUse();
    }

    private void drawPuzzleBackground() {
        final Texture t = game.puzzle.background;
        if (t != null) {
            t.bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(0f, 0f);
                glVertex2i(SOLVE_AREA.getUpperLeft().getX(), SOLVE_AREA.getUpperLeft().getY());
                glTexCoord2f(t.getWidth(), 0f);
                glVertex2i(SOLVE_AREA.getUpperLeft().getX() + SOLVE_AREA.getWidth(), SOLVE_AREA.getUpperLeft().getY());
                glTexCoord2f(t.getWidth(), t.getHeight());
                glVertex2i(SOLVE_AREA.getUpperLeft().getX() + SOLVE_AREA.getWidth(), SOLVE_AREA.getUpperLeft().getY() + SOLVE_AREA.getHeight());
                glTexCoord2f(0f, t.getHeight());
                glVertex2i(SOLVE_AREA.getUpperLeft().getX(), SOLVE_AREA.getUpperLeft().getY() + SOLVE_AREA.getHeight());
            }
            glEnd();
        }
    }

    private Piece findHoveredSprite(final int mx, final int my) {
        outlined = null;
        Piece r = null;
        final List<Piece> hovered = new ArrayList<>();
        for (final Piece p : game.pieceList) {
            if (!p.snapped && p.isInside(mx, my)) {
                hovered.add(p);
            }
        }
        if (!hovered.isEmpty()) {
            r = hovered.get(hovered.size() - 1);
            outlined = r;
        }
        return r;
    }

    private boolean isGameWon() {
        for (final Piece p : game.pieceList) {
            if (!p.snapped)
                return false;
        }
        return true;
    }

    private void win() {
        final long t = System.currentTimeMillis() - started;
        final int a = JOptionPane.showConfirmDialog(null, "Glückwunsch! Du hast das Puzzle innerhalb von "
                + Math.round(t / 1000f)
                + " Sekunden gelöst!\n\nMöchtest du dieses Puzzle noch einmal lösen? Klicke auf \"Ja\" wenn du dieses Puzzle noch einmal lösen möchtest oder auf \"Nein\", um zur Puzzleauswahl zurückzukehren.", "Du hast es geschafft! "
                + game.puzzle.title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (a == JOptionPane.YES_OPTION) {
            init();
        } else {
            State.setActiveState(STATE_PUZZLESELECTION);
        }
    }

    @Override
    void draw() {
        drawPuzzleBackground();
        drawPieces();
        drawOutline();
    }

    @Override
    void handleInput() {
        if (isGameWon()) {
            win();
        }
        game.updatePieceList();
        final int mx = Main.instance.mouse_abs_x;
        final int my = Main.instance.mouse_abs_y;
        final int mdx = Main.instance.mouse_dyn_x;
        final int mdy = Main.instance.mouse_dyn_y;
        final boolean mbl = Main.instance.mouse_but_l;
        final Piece p = findHoveredSprite(mx, my);
        final Piece s = game.selected;
        if (s != null) {
            game.checkSnap(s);
        }
        if (p != null) {
            if (mbl) {
                if (!p.snapped) {
                    p.lastClicked = System.currentTimeMillis();
                    game.selected = p;
                    p.move(mdx, mdy);
                    p.checkPositioning();
                }
            }
        } else {
            if (!mbl) {
                game.selected = null;
            }
        }

    }

    void init() {
        game = new PuzzleGame(Main.instance.getPuzzles()[((StatePuzzleSelection) State.getState(STATE_PUZZLESELECTION)).selected],
                PuzzleDifficulty.getById(((StatePieceSizeSelection) State.getState(STATE_PIECESIZESELECTION)).selected));
        game.updatePieceList();
        started = System.currentTimeMillis();
    }

}
