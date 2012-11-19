package net.obnoxint.adsz.puzzle;

import java.io.IOException;

final class StateIntro extends State {

    private static final int DELAY = 5000;

    private final long started = System.currentTimeMillis();

    StateIntro() throws IOException {
        super(STATE_INTRO);
    }

    @Override
    void draw() {
        if (System.currentTimeMillis() > started + DELAY) {
            State.setActiveState(STATE_PUZZLESELECTION);
        } else {
            try {
                synchronized (this) {
                    wait(50L);
                }
            } catch (final InterruptedException e) {}
        }
    }

    @Override
    void handleInput() {}

}
