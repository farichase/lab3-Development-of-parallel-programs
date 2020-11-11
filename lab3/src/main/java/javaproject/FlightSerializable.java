package javaproject;

import java.io.Serializable;

public class FlightSerializable implements Serializable {

    private float maxDelay;
    private int delayFlights;
    private int cancelledFlights;
    private int countOfFlights;

    private static final float ZERO_DELAY = 0.f;
    public FlightSerializable(){
    }
    public FlightSerializable(float maxDelay, int delayFlights, int cancelledFlights, int countOfFlights){
        this.maxDelay = maxDelay;
        this.delayFlights = delayFlights;
        this.cancelledFlights = cancelledFlights;
        this.countOfFlights = countOfFlights;
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
    public void setCountOfFlights(int countOfFlights) {
        this.countOfFlights = countOfFlights;
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
    public int getCountOfFlights() {
        return this.countOfFlights;
    }
    public static FlightSerializable addValue(FlightSerializable item1, AirportSerializable item2){
        boolean isDelayed = false;
        boolean isCancelled = item2.getIsCancelled();
        if (item2.getDelayTime() > ZERO_DELAY || item2.getIsCancelled()) isDelayed = !isDelayed;
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
