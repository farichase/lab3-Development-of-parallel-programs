package javaproject;

import java.io.Serializable;

public class FlightSerializable implements Serializable {

    private float maxDelay;
    private int delayFlights;
    private int countOfFlights;

    private static final float ZERO_DELAY = 0.f;
    public FlightSerializable(){
    }
    public FlightSerializable(float maxDelay, int delayFlights, int cancelledFlights){
        this.maxDelay = maxDelay;
        this.delayFlights = delayFlights;
        this.countOfFlights = cancelledFlights;
    }
    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }
    public void setDelayFlights(int delayFlights) {
        this.delayFlights = delayFlights;
    }
    public void setCountOfFlights(int countOfFlights) {
        this.countOfFlights = countOfFlights;
    }
    public float getMaxDelay() {
        return this.maxDelay;
    }
    public int getDelayFlights() {
        return this.delayFlights;
    }
    public int getCountOfFlights() {
        return this.countOfFlights;
    }
    public static FlightSerializable addValue(FlightSerializable item1, AirportSerializable item2){
        boolean isDelayed = false;
        if (item2.getDelayTime() > ZERO_DELAY || item2.getIsCancelled()) isDelayed = !isDelayed;
        return new FlightSerializable(
                Math.max(item1.getMaxDelay(), item2.getDelayTime()),
                isDelayed ? item1.getDelayFlights() + 1 : item1.getDelayFlights(),
                item1.getCountOfFlights() + 1
        );
    }
}
