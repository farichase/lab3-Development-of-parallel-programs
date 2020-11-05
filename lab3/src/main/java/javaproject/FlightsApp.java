package javaproject;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class FlightsApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.exit(-1);
        }
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        String flights = args[0];
        String airports = args[1];
        String output = args[2];
        
    }
}
