package net.obnoxint.adsz.puzzle;

import java.util.ArrayList;
import java.util.List;

abstract class State {

    private static List<State> states = new ArrayList<>();
    private static State activeState = null;

    static final int STATE_INTRO = 0;
    static final int STATE_PUZZLESELECTION = 1;
    static final int STATE_PIECESIZESELECTION = 2;
    static final int STATE_PUZZLEPLAY = 3;

    private static State getState(final int id) {
        State r = null;
        if (states.contains(id)) {
            r = states.get(states.indexOf(id));
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
            states.add(r);
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

    @Override
    public final boolean equals(final Object obj) {
        if (obj != null && obj instanceof State && ((State) obj).id == id)
            return true;
        return false;
    }

    abstract void draw();

    abstract void finish();

    abstract void handleInput();

}
