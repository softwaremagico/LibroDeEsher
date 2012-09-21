package com.softwaremagico.files;
/*
 * #%L
 * KendoTournamentGenerator
 * %%
 * Copyright (C) 2008 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero.
 * Jorge Hortelano Otero <softwaremagico@gmail.com>
 * C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class Log {

    private static final Logger logger = Logger.getLogger("KendoLog");
    private static final Level logLevel = Level.ALL; //INFO, OFF, ALL, ... 
    private static final int maxBytes = 10000000;
    private static final int numLogFiles = 10;

    static {
        try {
            FileHandler fh = new FileHandler(Path.returnLogFile(), maxBytes, numLogFiles, true);
            logger.addHandler(fh);
            logger.setLevel(logLevel);
            //fh.setFormatter(new SimpleFormatter());
            fh.setFormatter(getCustomFormatter());
        } catch (IOException | SecurityException ex) {
            MessageManager.basicErrorMessage("Logger failed. Probably the log file can not be created. Error Message: " + ex.getMessage(), "Logger");
        }
    }

    /**
     * Defines our own formatter.
     */
    public static Formatter getCustomFormatter() {
        return new Formatter() {
            //StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

            @Override
            public String format(LogRecord record) {
                String text = record.getLevel() + " [" + new Date() + "] " + " - " + record.getMessage() + "\n";
                return text;
            }
        };
    }

    private Log() {
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void config(String message) {
        logger.config(message);
    }

    public static void warning(String message) {
        logger.warning(message);
    }

    public static void debug(String message) {
        logger.finest(message);
    }

    public static void severe(String message) {
        logger.severe(message);
    }

    public static void fine(String message) {
        logger.fine(message);
    }

    public static void finer(String message) {
        logger.finer(message);
    }

    public static void finest(String messsage) {
        logger.finest(messsage);
    }
}
