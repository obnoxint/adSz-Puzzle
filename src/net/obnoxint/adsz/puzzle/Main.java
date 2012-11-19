package net.obnoxint.adsz.puzzle;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class Main {

    public static final int VERSION = 0;

    static final int EXIT_CODE_ERROR = -1;
    static final int EXIT_CODE_OK = 0;

    static final int DISPLAY_WIDTH = 1024;
    static final int DISPLAY_HEIGHT = 768;
    static final int DISPLAY_FPS = 30;
    static final String DISPLAY_TITLE = "adSz - Puzzle";

    static final String FILE_NAME_RESSOURCEFOLDER = "res";
    static final String FILE_NAME_PUZZLEFOLDER = "puzzle";
    static final String FILE_EXT_PROPERTIES = ".properties";
    static final String FILE_EXT_PNG = ".png";

    static final byte[] RGB_OCHER_LIGHT = { 127, 87, -55 };

    static final String TEXTURE_TYPE_PNG = "PNG";

    static Main instance = null;

    public static void main(final String[] args) {
        if (instance == null) {
            instance = new Main();
            try {
                instance.init();
            } catch (final LWJGLException e) {
                JOptionPane.showMessageDialog(null, "An exception occured while initializing the application. An error-report will be saved.", "Error during initialization", JOptionPane.ERROR_MESSAGE);
                writeStackTrace(e);
                System.exit(EXIT_CODE_ERROR);
            }
            instance.run();
            instance.die();
            System.exit(EXIT_CODE_OK);
        }
    }

    static void writeStackTrace(final Throwable throwable) {
        final File f = new File("error_" + System.currentTimeMillis());
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            throwable.printStackTrace(pw);
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to store stacktrace to this file:\n" + f.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private File ressourceFolder = null;
    private File puzzleFolder = null;
    private Puzzle[] puzzles = null;

    // State variables for mouse cursor position, movement and button state
    int mouse_abs_x = 0;
    int mouse_abs_y = 0;
    int mouse_dyn_x = 0;
    int mouse_dyn_y = 0;
    boolean mouse_but_l = false;
    boolean mouse_but_r = false;

    private Main() {}

    private void die() {
        Display.destroy();
    }

    private void init() throws LWJGLException {

        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        Display.setTitle(DISPLAY_TITLE);
        Display.create();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, DISPLAY_WIDTH, DISPLAY_HEIGHT, 0, 1, -1);
        glDisable(GL_DEPTH_TEST);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void pollMouse() {
        mouse_abs_x = Mouse.getX();
        mouse_abs_y = Display.getHeight() - Mouse.getY(); // Y-coordinate is based on the "bottom".
        mouse_dyn_x = Mouse.getDX();
        mouse_dyn_y = -Mouse.getDY();
        mouse_but_l = Mouse.isButtonDown(0);
        mouse_but_r = Mouse.isButtonDown(1);
    }

    private void run() {
        while (!Display.isCloseRequested()) {
            if (Display.isActive()) {
                glClear(GL_COLOR_BUFFER_BIT);
                if (State.getActiveState() == null) {
                    State.setActiveState(State.STATE_INTRO);
                } else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    State.setActiveState(State.STATE_PUZZLESELECTION);
                }
                pollMouse();
                State.getActiveState().handleInput();
                State.getActiveState().drawBackground();
                State.getActiveState().draw();
            } else {
                try {
                    Thread.sleep(50);
                } catch (final InterruptedException e) {}
            }
            Display.update();
            Display.sync(DISPLAY_FPS);
        }
    }

    File getPuzzleFolder() {
        if (puzzleFolder == null) {
            puzzleFolder = new File(FILE_NAME_PUZZLEFOLDER);
        }
        return puzzleFolder;
    }

    Puzzle[] getPuzzles() {
        if (puzzles == null) {
            final String[] files = getPuzzleFolder().list(new FilenameFilter() {

                @Override
                public boolean accept(final File dir, final String name) {
                    return name.endsWith(FILE_EXT_PROPERTIES);
                }

            });
            puzzles = new Puzzle[files.length];
            for (int i = 0; i < files.length; i++) {
                final String n = files[i];
                puzzles[i] = new Puzzle(n.substring(0, n.length() - FILE_EXT_PROPERTIES.length()));
            }
        }
        return puzzles;
    }

    File getRessourceFolder() {
        if (ressourceFolder == null) {
            ressourceFolder = new File(FILE_NAME_RESSOURCEFOLDER);
        }
        return ressourceFolder;
    }

}
