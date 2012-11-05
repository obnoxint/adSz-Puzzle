package net.obnoxint.adsz.puzzle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

final class DebugLogger extends Logger {

    DebugLogger() throws SecurityException, IOException {
        super(Main.LOGGER_NAME, null);
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
                                    .append(record.getMessage()).append("\n");
                            return sb.toString();
                        }

                    };
                }
                return f;
            }
        });
    }

}