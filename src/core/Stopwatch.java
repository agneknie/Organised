package core;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which emulates a stopwatch, which tracks elapsed time.
 */
public class Stopwatch {
    private long startTime = 0;
    private long endTime = 0;
    private boolean running = false;

    /**
     * Method which starts the stopwatch.
     */
    public void start(){
        // If stopwatch is already running, throws an exception
        if(running) throw new RuntimeException("The stopwatch is already running.");

        // If stopwatch is started for the first time, assigns start time
        if(isReset()) startTime = System.nanoTime();
        // If stopwatch isn't started for the first time, notes the break
        else startTime = startTime+(System.nanoTime()-endTime);

        // Starts the stopwatch
        running = true;
    }

    /**
     * Method which stops the stopwatch.
     */
    public void stop(){
        // If stopwatch is not running, throws an exception
        if(!running) throw new RuntimeException("The stopwatch is not running.");

        // Stops the stopwatch
        running = false;
        endTime = System.nanoTime();
    }

    /**
     * Resets the stopwatch.
     */
    public void reset(){
        startTime = 0;
        endTime = 0;
        running = false;
    }

    /**
     * Returns true if stopwatch is reset.
     * @return true if stopwatch doesn't have any previous values.
     */
    private boolean isReset(){
        return startTime == 0 && endTime == 0 && !running;
    }

    /**
     * Method which gets the elapsed time of a stopwatch in seconds.
     * Rounds up the seconds.
     *
     * @return time in seconds
     */
    public int getSeconds(){
        long elapsedTime;

        // Acts differently depending whether the stopwatch is running
        if(running) elapsedTime = System.nanoTime() - startTime;
        else elapsedTime = endTime - startTime;

        return (int)Math.round(elapsedTime/1000000000.0);
    }

    /**
     * Method which gets the elapsed time of a stopwatch in minutes.
     * Does not round up the minutes.
     *
     * @return time in minutes
     */
    public int getMinutes(){
        return (int)(this.getSeconds()/60.0);
    }

    /**
     * Method which gets the elapsed time of a stopwatch in hours.
     * Does not round up the hours.
     *
     * @return time in hours
     */
    public int getHours(){
        return (int)(this.getMinutes()/60.0);
    }

    /**
     * Method which returns the elapsed time as a string to display.
     *
     * @return time as a string
     */
    public String getElapsedTime(){
        String seconds, minutes, hours;

        // Deals with hours
        if(this.getHours()-10<0) hours = "0"+this.getHours();
        else hours = Integer.toString(this.getHours());

        // Deals with minutes
        int min = this.getMinutes()-this.getHours()*60;
        if(min-10<0) minutes = "0"+min;
        else minutes = Integer.toString(min);

        // Deals with seconds
        int sec = this.getSeconds()-this.getHours()*3600-this.getMinutes()*60;
        if(sec-10<0) seconds = "0"+sec;
        else seconds = Integer.toString(sec);

        // Constructs the string
        return hours+":"+minutes+":"+seconds;
    }
}
