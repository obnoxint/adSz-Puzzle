package net.obnoxint.adsz.puzzle;

import org.lwjgl.util.Point;

final class Piece extends Box implements Comparable<Piece> {

    private static final int BORDER_MARGIN = 10;

    final int x, y;

    boolean snapped = false;
    long lastClicked = -1;

    Piece(final PuzzleGame game, final int x, final int y, final Point upperLeft) {
        super(upperLeft, new Point(upperLeft.getX() + game.difficulty.size, upperLeft.getY() + game.difficulty.size));
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(final Piece o) {
        return lastClicked == o.lastClicked ? 0 : lastClicked > o.lastClicked ? 1 : -1;
    }

    void checkPositioning() {
        final int rm = Main.DISPLAY_WIDTH - BORDER_MARGIN;  // right margin
        final int bm = Main.DISPLAY_HEIGHT - BORDER_MARGIN; // bottom margin
        if (getLowerRight().getX() < BORDER_MARGIN) {
            moveTo(BORDER_MARGIN - getWidth(), getUpperLeft().getY());
        }
        if (getLowerRight().getY() < BORDER_MARGIN) {
            moveTo(getUpperLeft().getX(), BORDER_MARGIN - getHeight());
        }
        if (getUpperLeft().getX() > rm) {
            moveTo(rm, getUpperLeft().getY());
        }
        if (getUpperLeft().getY() > bm) {
            moveTo(getUpperLeft().getX(), bm);
        }
    }

}
