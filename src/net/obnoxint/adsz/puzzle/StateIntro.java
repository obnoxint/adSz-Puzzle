package net.obnoxint.adsz.puzzle;

import java.io.IOException;

final class StateIntro extends State {

    private final long started = System.currentTimeMillis();
        
    StateIntro() throws IOException {
        super(STATE_INTRO);
    }

    @Override
    void draw() {
        if (System.currentTimeMillis() > started + 1000 * 5){
            State.setActiveState(STATE_PUZZLESELECTION);
        }else{
            try {
                synchronized (this) {                    
                    wait(50L);
                }
            } catch (InterruptedException e) {}
        }
    }

    @Override
    void handleInput() {
        // TODO Auto-generated method stub

    }

    

}
