package com.notimetolearn.java.util;

import org.apache.commons.lang3.time.StopWatch;

public class CommonUtil {

    public static StopWatch watch = new StopWatch();

    public static void delay(long delayMilliSeconds)  {

        try {
            Thread.sleep(delayMilliSeconds);
        } catch (InterruptedException e) {
            LoggerUtil.log("Exception is: "+ e.getMessage());
        }
    }

    public String transform(String s) {
        delay(500);
        return s.toUpperCase();
    }

    public static void startTimer(){
        watch.start();
    }

    public static void timeTaken(){
        watch.stop();
        LoggerUtil.log("Total time taken : "+ watch.getTime());
    }

    public static void stopWatchReset(){
        watch.reset();
    }

    public static int noOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }
}
