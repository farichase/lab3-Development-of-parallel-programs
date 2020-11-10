package javaproject;

import java.io.Serializable;

public class FlightSerializable implements Serializable {

    private float maxDelay;
    private int delayFlights;
    private int cancelledFlights;
    private static final float ZERO_DELAY = 0.f;
    public FlightSerializable(){
    }
    public FlightSerializable(float maxDelay, int delayFlights, int cancelledFlights){
        this.maxDelay = maxDelay;
        this.delayFlights = delayFlights;
        this.cancelledFlights = cancelledFlights;
    }
    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }
    public void setDelayFlights(int delayFlights) {
        this.delayFlights = delayFlights;
    }
    public void setCancelledFlights(int cancelledFlights) {
        this.cancelledFlights = cancelledFlights;
    }
    public float getMaxDelay() {
        return this.maxDelay;
    }
    public int getDelayFlights() {
        return this.delayFlights;
    }
    public int getCancelledFlights() {
        return this.cancelledFlights;
    }
    public FlightSerializable mergeFunction(FlightSerializable item1, AirportSerializable item2){
        boolean isDelayed = false;
        if (item2.getDelayTime() > ZERO_DELAY || item2.getIsCancelled()) isDelayed = !isDelayed;
        return new FlightSerializable(
                Math.max(item1.getMaxDelay(), item2.getDelayTime()),
                isDelayed ? item1.getDelayFlights() + 1 : item1.getDelayFlights(),
                item1.
        );
    }
}
