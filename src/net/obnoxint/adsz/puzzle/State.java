package net.obnoxint.adsz.puzzle;

import java.util.HashMap;
import java.util.Map;

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
        }
        return r;
    }

    static State getActiveState() {
        return activeState;
    }

    static void setActiveState(final int id) {
        activeState = getState(id);
    }

    final int id;

    protected State(final int id) {
        this.id = id;
    }

    abstract void draw();

    abstract void finish();

    abstract void handleInput();

}
