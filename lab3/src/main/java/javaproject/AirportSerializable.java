package javaproject;

import java.io.Serializable;

public class AirportSerializable implements Serializable {

    private int originAirportId;
    private int destAirportId;
    private float delayTime;
    private boolean isCancelled;

    public AirportSerializable(){
    }
    public AirportSerializable(int originAirportId, int destAirportId, float delayTime, boolean isCancelled){
        this.originAirportId = originAirportId;
        this.destAirportId = destAirportId;
        this.delayTime = delayTime;
        this.isCancelled = isCancelled;
    }
    public void setOriginAirportId(int originAirportId) {
        this.originAirportId = originAirportId;
    }
    public void setDestAirportId(int destAirportId) {
        this.destAirportId = destAirportId;
    }
    public void setDelayTime(float delayTime) {
        this.delayTime = delayTime;
    }
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
    public int getOriginAirportId() {
        return originAirportId;
    }
    public int getDestAirportId() {
        return destAirportId;
    }
    public float getDelayTime() {
        return delayTime;
    }
    public boolean getIsCancelled() {
        return isCancelled;
    }
}
