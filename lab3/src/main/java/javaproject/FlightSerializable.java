package javaproject;

import java.io.Serializable;

public class FlightSerializable implements Serializable {

    private float maxDelay;
    private float delayFlights;
    private float cancelledFlights;
    private float countOfFlights;

    private static final float ZERO_DELAY = 0.f;
    public FlightSerializable(){
    }
    public FlightSerializable(float maxDelay, float delayFlights, float cancelledFlights, float countOfFlights){
        this.maxDelay = maxDelay;
        this.delayFlights = delayFlights;
        this.cancelledFlights = cancelledFlights;
        this.countOfFlights = countOfFlights;
    }
    public void setMaxDelay(float maxDelay) {
        this.maxDelay = maxDelay;
    }
    public void setDelayFlights(float delayFlights) {
        this.delayFlights = delayFlights;
    }
    public void setCancelledFlights(float cancelledFlights) {
        this.cancelledFlights = cancelledFlights;
    }
    public void setCountOfFlights(float countOfFlights) {
        this.countOfFlights = countOfFlights;
    }
    public float getMaxDelay() {
        return this.maxDelay;
    }
    public float getDelayFlights() {
        return this.delayFlights;
    }
    public float getCancelledFlights() {
        return this.cancelledFlights;
    }
    public float getCountOfFlights() {
        return this.countOfFlights;
    }
    public static FlightSerializable addValue(FlightSerializable item1, AirportSerializable item2){
        boolean isDelayed = false;
        boolean isCancelled = item2.isCancelled();
        if (item2.getDelayTime() > ZERO_DELAY || item2.isCancelled()) isDelayed = !isDelayed;
        return new FlightSerializable(
                Math.max(item1.getMaxDelay(), item2.getDelayTime()),
                isDelayed ? item1.getDelayFlights() + 1 : item1.getDelayFlights(),
                isCancelled ? item1.getCancelledFlights() + 1 : item1.getCancelledFlights(),
                item1.getCountOfFlights() + 1
        );
    }
    public static FlightSerializable add (FlightSerializable item1, FlightSerializable item2) {
        return new FlightSerializable(
            item1.getMaxDelay() + item2.getMaxDelay(),
            item1.getDelayFlights() + item2.getDelayFlights(),
            item1.getCancelledFlights() + item2.getCancelledFlights(),
            item1.getCountOfFlights() + item2.getCountOfFlights()
        );
    }
}
