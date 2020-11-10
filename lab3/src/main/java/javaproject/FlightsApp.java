package javaproject;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;

public class FlightsApp {
    private static final String COMMA = ",";
    private static final int ORIGIN_AIRPORT_ID = 11;
    private static final int DEST_AIRPORT_ID = 14;
    private static final int DELAY_ID = 18;
    private static final int IS_CANCELLED_ID = 19;
    private static final float ZERO_DELAY = 0.f;

    private static Tuple2<Integer, String> makeAirportPairs(String line) {
        int commaIndex = line.indexOf(COMMA);
        int airaceID = Integer.parseInt(line.substring(0, commaIndex));
        String airport = line.substring(commaIndex + 1);
        return new Tuple2<>(airaceID, airport);
    }
    private static Tuple2<Tuple2<Integer, Integer>, AirportSerializable> makeFlightPairs(String line) {
        String[] columns = line.split(COMMA);
        int originAirportId = Integer.parseInt(columns[ORIGIN_AIRPORT_ID]);
        int destAirportId = Integer.parseInt(columns[DEST_AIRPORT_ID]);
        float delay = columns[DELAY_ID].isEmpty() ? ZERO_DELAY : Float.parseFloat(columns[DELAY_ID]);
        boolean isCancelled = columns[IS_CANCELLED_ID].isEmpty();
        return new Tuple2<>(
                new Tuple2<>(originAirportId, destAirportId),
                new AirportSerializable(originAirportId, destAirportId, delay, isCancelled)
        );
    }
    public static void main(String[] args) {
        if (args.length != 3) {
            System.exit(-1);
        }
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        String flights = args[0];
        String airports = args[1];
        String output = args[2];
        JavaRDD<String> flightsFile = sc.textFile(flights);
        JavaRDD<String> airportsFile = sc.textFile(airports);
        JavaPairRDD<Integer, String> airportsData = airportsFile
                .mapToPair(line -> makeAirportPairs(line));
        Map<Integer, String> airportDataMap = airportsData.collectAsMap();
        final Broadcast<Map<Integer, String>> airportsBroadcasted = sc.broadcast(airportDataMap);

        JavaPairRDD<Tuple2<Integer, Integer>, AirportSerializable> flightsData = flightsFile
                .mapToPair(line -> makeFlightPairs(line));

        JavaPairRDD<Tuple2<Integer, Integer>, FlightSerializable> reducedFlights = flightsData
                .combineByKey(
                        item -> new FlightSerializable(item.getDelayTime())
                )

    }
}
