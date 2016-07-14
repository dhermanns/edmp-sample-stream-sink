package de.codecentric;

/**
 * Created by dirk on 14.07.16.
 */
public class TimeService {

    private long startTime;

    public void beginMeasuring() {
        startTime = System.currentTimeMillis();
    }

    public long endMeasuring() {
        return System.currentTimeMillis() - startTime;
    }
}
