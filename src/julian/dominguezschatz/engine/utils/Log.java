package julian.dominguezschatz.engine.utils;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class: Log
 * Author: Julian Dominguez-Schatz
 * Date: 30/08/2017
 * Description: Implements a versatile logging facility. This class is not implemented as an object, so that
 *              it can support log filtering. This allows the developer to easily enable and disable different
 *              levels of logging at runtime, which is useful both for filtering through messages as well as
 *              easily creating release versions.
 */
public class Log {

    public enum Level {
        NONE(true), ERROR(true), WARN(true), INFO(false), DEBUG(false), VERBOSE(false), ALL(false);

        // whether to use System.err when outputting logging messages
        private final boolean error;

        Level(boolean error) {
            this.error = error;
        }

        /**
         * @param other the level to compare against
         * @return whether this level is finer than another level
         */
        public boolean isFinerThan(Level other) {
            return other.ordinal() < this.ordinal();
        }

        /**
         * Returns the {@link java.io.PrintStream PrintStream} that should be used with this logging level.
         *
         * @return the PrintStream to use
         */
        PrintStream getOut() {
            return error ? System.err : System.out;
        }
    }

    // used in the Java 7 implementation to format a timestamp
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy hh:mm:ss a");

    // the format used in all logging output
    private static final String LOG_FORMAT = "[%s] [%s] [%s]: ";

    // the current minimum level of messages that will be output
    private static Level logLevel = Level.DEBUG;

    /**
     * Sets the lowest logging level that will be output.
     *
     * @param logLevel the new minimum level
     */
    public static void setLevel(Level logLevel) {
        Log.logLevel = logLevel;
    }

    /**
     * Outputs a logging message at the logging level VERBOSE.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     */
    public static void v(String tag, String message) {
        write(Level.VERBOSE, tag, message);
    }

    /**
     * Outputs a logging message at the logging level DEBUG.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     */
    public static void d(String tag, String message) {
        write(Level.DEBUG, tag, message);
    }

    /**
     * Outputs a logging message at the logging level INFO.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     */
    public static void i(String tag, String message) {
        write(Level.INFO, tag, message);
    }

    /**
     * Outputs a logging message at the logging level WARN.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     */
    public static void w(String tag, String message) {
        write(Level.WARN, tag, message);
    }

    /**
     * Outputs a logging message at the logging level WARN.
     *
     * @param tag the tag to use in the logging message
     * @param t a throwable to include in the message
     */
    public static void w(String tag, Throwable t) {
        write(Level.WARN, tag, t);
    }

    /**
     * Outputs a logging message at the logging level WARN.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     * @param t a throwable to include in the message
     */
    public static void w(String tag, String message, Throwable t) {
        write(Level.WARN, tag, message, t);
    }

    /**
     * Outputs a logging message at the logging level ERROR.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     */
    public static void e(String tag, String message) {
        write(Level.ERROR, tag, message);
    }

    /**
     * Outputs a logging message at the logging level ERROR.
     *
     * @param tag the tag to use in the logging message
     * @param t a throwable to include in the message
     */
    public static void e(String tag, Throwable t) {
        write(Level.ERROR, tag, t);
    }

    /**
     * Outputs a logging message at the logging level ERROR.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     * @param t a throwable to include in the message
     */
    public static void e(String tag, String message, Throwable t) {
        write(Level.ERROR, tag, message, t);
    }

    /**
     * Outputs a logging message at the highest logging level.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     */
    public static void wtf(String tag, String message) {
        write(Level.ERROR, tag, message);
    }


    /**
     * Outputs a logging message at the highest logging level.
     *
     * @param tag the tag to use in the logging message
     * @param t a throwable to include in the message
     */
    public static void wtf(String tag, Throwable t) {
        write(Level.ERROR, tag, t);
    }


    /**
     * Outputs a logging message at the highest logging level.
     *
     * @param tag the tag to use in the logging message
     * @param message the contents of the logging message
     * @param t a throwable to include in the message
     */
    public static void wtf(String tag, String message, Throwable t) {
        write(Level.ERROR, tag, message, t);
    }

    // utility methods

    private static void write(Level level, String tag, String message) {
        write(level, tag, message, null);
    }

    private static void write(Level level, String tag, Throwable t) {
        write(level, tag, null, t);
    }

    private static void write(Level level, String tag, String message, Throwable t) {
        // the message will not be output if it is not coarse enough
        if (level.isFinerThan(logLevel)) {
            return;
        }

        // predetermine certain logging attributes
        PrintStream out = level.getOut();
        String logPrefix = getLogPrefix(level, tag);

        if (message != null) {
            // output the provided message
            out.println(logPrefix.concat(message));
        }

        if (t != null) {
            // log the provided throwable
            out.println(logPrefix.concat(t.toString()));

            StackTraceElement[] stackTrace = t.getStackTrace();
            String logTracePrefix = logPrefix.concat("at ");
            for (StackTraceElement element : stackTrace) {
                out.println(logTracePrefix.concat(element.toString()));
            }
        }
    }


    /**
     * Generates a prefix for a logging message based on the current level and a tag.
     *
     * @param level the logging level to use
     * @param tag the tag to include
     * @return the generated prefix
     */
    private static String getLogPrefix(Level level, String tag) {
        return String.format(LOG_FORMAT, getTimestamp(), level, tag);
    }

    /**
     * Determines a timestamp based on the current time.
     *
     * @return a string representation of the current time
     */
    private static String getTimestamp() {
        /* Java 8:
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM);
        ZonedDateTime currentDate = ZonedDateTime.now();
        return dateTimeFormatter.format(currentDate);
        */

        /* Java 7: */
        return DATE_FORMAT.format(new Date());
    }

}
