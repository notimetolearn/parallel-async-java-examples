package com.notimetolearn.java.util;

public class LoggerUtil {

    public static void log(String message) {
        String logMessage = String.format("[ %s ] - %s", Thread.currentThread().getName(), message);
        System.out.println(logMessage);
    }
}
