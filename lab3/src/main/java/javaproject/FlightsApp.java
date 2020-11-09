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

    private static Tuple2<Integer, String> makeAirportPairs(String line) {
        int commaIndex = line.indexOf(COMMA);
        int airaceID = Integer.parseInt(line.substring(0, commaIndex));
        String airport = line.substring(commaIndex + 1);
        return new Tuple2<>(airaceID, airport);
    }
    private static Tuple2<Integer, Integer> makeFlightPairs(String line) {
        String[] columns = line.split(COMMA);
        int originAirportId = Integer.parseInt(columns[0]);
        int destAirportId = Integer.parseInt(columns[1]);
        return new Tuple2<>();
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

        JavaPairRDD<Integer, Integer> flightsData = flightsFile
                .mapToPair(line -> makeFlightPairs(line));
        
    }
}
