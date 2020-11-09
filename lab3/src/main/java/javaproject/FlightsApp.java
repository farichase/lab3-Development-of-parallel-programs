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

    private static Tuple2<Integer, String> makePairs(String line) {
        int commaIndex = line.indexOf(COMMA);
        int airaceID = Integer.parseInt(line.substring(0, commaIndex));
        String airport = line.substring(commaIndex + 1);
        return new Tuple2<>(airaceID, airport);
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
        JavaPairRDD<Integer, String> airportsNames = airportsFile
                .mapToPair(line -> makePairs(line));
        Map<Integer, String> airportDataMap = airportsNames.collectAsMap();
        final Broadcast<Map<Integer, String>> airportsBroadcasted = sc.broadcast(airportDataMap);

        JavaPairRDD<Integer, >
        
    }
}
