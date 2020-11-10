package javaproject;

import java.io.Serializable;

public class FlightSerializable implements Serializable {

    private float maxDelay;
    private int delayFlights;
    private int cancelledFlights;

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
    public void mergeFunction(){
        
    }
}
