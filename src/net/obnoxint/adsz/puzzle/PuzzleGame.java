package net.obnoxint.adsz.puzzle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.Point;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

final class PuzzleGame {

    final Puzzle puzzle;
    final PuzzleDifficulty difficulty;
    final SpriteSheet sheet;
    final List<Piece> pieceList = new LinkedList<>();

    Piece selected;

    private final Random random = new Random();

    private static final Box[] EDGES = {
            new Box(new Point(0, 0), new Point(Main.DISPLAY_WIDTH - 20, 64)),                         // Top
            new Box(new Point(932, 64), new Point(Main.DISPLAY_WIDTH - 20, 684)),                     // Right
            new Box(new Point(0, 684), new Point(Main.DISPLAY_WIDTH - 20, Main.DISPLAY_HEIGHT - 20)), // Bottom
            new Box(new Point(0, 64), new Point(92, 684))                                             // Left
    };

    PuzzleGame(final Puzzle puzzle, final PuzzleDifficulty difficulty) {
        final int h = difficulty.hCount;
        final int v = difficulty.vCount;
        final int t = difficulty.size;

        this.puzzle = puzzle;
        this.difficulty = difficulty;
        this.sheet = new SpriteSheet(new Image(puzzle.texture), t, t);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < v; j++) {
                pieceList.add(new Piece(this, i, j, getRandomPosition()));
            }
        }
    }

    private Point getRandomPosition() {
        Point r;
        final Box edge = EDGES[random.nextInt(EDGES.length)];
        while (!edge.isInside((r = new Point(random.nextInt(Main.DISPLAY_WIDTH), random.nextInt(Main.DISPLAY_HEIGHT))))) {}
        return r;
    }

    private Point getSnapPoint(final Piece p) {
        final int s = difficulty.size;
        final Point sam = StatePuzzlePlay.SOLVE_AREA.getUpperLeft(); // margin between solve area and screen border
        return new Point((p.x * s) + sam.getX(), (p.y * s) + sam.getY());
    }

    void checkSnap(final Piece p) {
        final Point sp = getSnapPoint(p);
        if (p.getDistanceToPoint(sp) <= difficulty.snapDistance) {
            p.moveTo(sp.getX(), sp.getY());
            p.snapped = true;
        }
    }

    void updatePieceList() {
        Collections.sort(pieceList);
    }

}
