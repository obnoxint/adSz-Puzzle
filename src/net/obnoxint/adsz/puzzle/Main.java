package net.obnoxint.adsz.puzzle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public final class Main {

    public static final class DebugLogger extends Logger {

        private DebugLogger() throws SecurityException, IOException {
            super(LOGGER_NAME_DEBUG, null);
            addHandler(new FileHandler("debug.log", true) {

                private Formatter f = null;

                @Override
                public Formatter getFormatter() {
                    if (f == null) {
                        f = new Formatter() {

                            @Override
                            public String format(final LogRecord record) {
                                final StringBuilder sb = new StringBuilder()
                                        .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(record.getMillis()))).append(" ")
                                        .append(record.getLevel()).append(" ")
                                        .append(record.getThreadID()).append(" ")
                                        .append(record.getSourceClassName()).append(" ")
                                        .append(record.getSourceMethodName()).append(" ")
                                        .append(record.getMessage());
                                return sb.toString();
                            }

                        };
                    }
                    return f;
                }
            });
        }

    }

    /**
     * Debug flag. The application should use the DebugLogger in order to output debug information. Should be set to false before deployment.
     */
    public static final boolean DEBUG = true;

    public static final String LOGGER_NAME = "net.obnoxint.adsz.puzzle";
    public static final String LOGGER_NAME_DEBUG = LOGGER_NAME + ".debug";

    private static final int EXIT_CODE_ERROR = -1;
    private static final int EXIT_CODE_OK = 0;

    public static DebugLogger debugLogger = null;

    private static Main instance = null;

    public static void main(final String[] args) {
        if (instance == null) {
            instance = new Main();
            if (DEBUG) {
                try {
                    debugLogger = new DebugLogger();
                } catch (SecurityException | IOException e) {
                    JOptionPane.showMessageDialog(null, "An exception occured while initializing the DebugLogger: " + e.getMessage() + "\nThe application must exit now.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(EXIT_CODE_ERROR);
                }
                debugLogger.info("Debugging started.");
            }
            instance.init();
            instance.run();
            instance.die();
            System.exit(EXIT_CODE_OK);
        }
    }

    private void die() {
        // TODO

    }

    private void init() {
        // TODO

    }

    private void run() {
        // TODO

    }

}
