package javaproject;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class FlightsApp {
    private static final String comma = ",";
    private static Tuple2<Integer, String> makePairs(String line) {
        int commaIndex = line.indexOf()
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
                .mapToPair(makePairs);
    }
}
