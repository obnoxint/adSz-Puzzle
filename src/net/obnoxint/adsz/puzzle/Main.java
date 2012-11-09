package net.obnoxint.adsz.puzzle;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class Main {

    /**
     * Debug flag. The application should use the DebugLogger in order to output debug information. Should be set to false before deployment.
     */
    public static final boolean DEBUG = true;
    public static final int VERSION = 0;

    public static final String LOGGER_NAME = "net.obnoxint.adsz.puzzle";

    static final int EXIT_CODE_ERROR = -1;
    static final int EXIT_CODE_OK = 0;

    static final int DISPLAY_WIDTH = 1024;
    static final int DISPLAY_HEIGHT = 768;
    static final int DISPLAY_FPS = 30;
    static final String DISPLAY_TITLE = "adSz - Puzzle";

    public static DebugLogger logger = null;

    private static Main instance = null;

    public static void main(final String[] args) {
        if (instance == null) {
            instance = new Main();
            if (DEBUG) {
                try {
                    logger = new DebugLogger();
                } catch (SecurityException | IOException e) {
                    JOptionPane.showMessageDialog(null, "An exception occured while initializing the DebugLogger: " + e.getMessage() + "\nThe application must exit now.", "Error", JOptionPane.ERROR_MESSAGE);
                    writeStackTrace(e);
                    System.exit(EXIT_CODE_ERROR);
                }
                logger.info("Debugging started.");
            }
            try {
                if (DEBUG) {
                    logger.info("Entering init()");
                }
                instance.init();
            } catch (final LWJGLException e) {
                JOptionPane.showMessageDialog(null, "An exception occured while initializing the application. An error-report will be saved.", "Error during initialization", JOptionPane.ERROR_MESSAGE);
                writeStackTrace(e);
                System.exit(EXIT_CODE_ERROR);
            }

            if (DEBUG) {
                logger.info("Entering run().");
            }
            instance.run();

            if (DEBUG) {
                logger.info("Entering die().");
            }
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

    private Main() {}

    private void die() {
        if (DEBUG) {
            logger.info("Destroying Display");
        }
        Display.destroy();
        // TODO
    }

    private void init() throws LWJGLException {
        if (DEBUG) {
            logger.info("Creating Display with size " + DISPLAY_WIDTH + "x" + DISPLAY_HEIGHT);
        }

        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        Display.setTitle((DEBUG) ? DISPLAY_TITLE + " v" + VERSION : DISPLAY_TITLE); // Set title depending of debug-mode
        Display.create();

        if (DEBUG) {
            logger.info("Initializing OpenGL and defining states.");
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, DISPLAY_WIDTH, DISPLAY_HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
    }

    private void run() {
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);
            if (State.getActiveState() == null) {
                State.setActiveState(State.STATE_INTRO);
            }
            State.getActiveState().drawBackground();
            State.getActiveState().draw();
            // TODO
            Display.update();
            Display.sync(DISPLAY_FPS);
        }
    }

}
