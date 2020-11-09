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
        
    }
}
