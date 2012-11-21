package net.obnoxint.adsz.puzzle;

import java.awt.Font;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

final class StateIntro extends State {

    private static final int DELAY = 5000;

    private final long started = System.currentTimeMillis();

    StateIntro() throws IOException {
        super(STATE_INTRO);
    }

    @SuppressWarnings("unchecked")
    private void initializeFont() {
        final UnicodeFont f = new UnicodeFont(new Font("MV Boli", Font.BOLD, 36));
        f.getEffects().add(new ColorEffect(new java.awt.Color(Main.RGB_OCHER_LIGHT[0] + 128, Main.RGB_OCHER_LIGHT[1] + 128, Main.RGB_OCHER_LIGHT[2] + 128)));
        f.addAsciiGlyphs();
        try {
            f.loadGlyphs();
        } catch (final SlickException e) {
            JOptionPane.showMessageDialog(null, "Beim Laden der Schriftart ist ein Fehler aufgetreten: " + e.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            Main.writeStackTrace(e);
            System.exit(Main.EXIT_CODE_ERROR);
        }
        Main.instance.font = f;
    }

    @Override
    void draw() {
        if (Main.instance.font == null) {
            initializeFont();
        }
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
