package utils;

import java.util.Date;
import java.util.logging.*;

public class MyLogger {

    private MyLogger instance = new MyLogger();

    private static Logger logger = Logger.getLogger("global");

    private MyLogger(){
        Level level = Level.SEVERE;

        logger.setLevel(level);
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        });
        handler.setLevel(level);
        logger.addHandler(handler);
    }

    public static Logger getInstance(){
        return logger;
    }
}
