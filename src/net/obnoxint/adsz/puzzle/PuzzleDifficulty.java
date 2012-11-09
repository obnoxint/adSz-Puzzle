package net.obnoxint.adsz.puzzle;

enum PuzzleDifficulty {

    EASIEST(0, 200, 4, 3, 20),
    VERY_EASY(1, 100, 8, 6, 10),
    EASY(2, 50, 16, 12, 10),
    NORMAL(3, 40, 20, 15, 5),
    HARD(4, 25, 32, 24, 3),
    VERY_HARD(5, 20, 40, 30, 3),
    INSANE(6, 10, 80, 60, 2);

    final int id;
    final int size;
    final int hCount;
    final int vCount;
    final int snapDistance;

    private PuzzleDifficulty(final int id, final int size, final int hCount, final int vCount, final int snapDistance) {
        this.id = id;
        this.size = size;
        this.hCount = hCount;
        this.vCount = vCount;
        this.snapDistance = snapDistance;
    }

    int count() {
        return hCount * vCount;
    }
}
