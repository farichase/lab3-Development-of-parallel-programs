package javaproject;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Iterator;
import java.util.Map;

public class FlightsApp {
    private static final String COMMA = ",";
    private static final int ORIGIN_AIRPORT_ID = 11;
    private static final int DEST_AIRPORT_ID = 14;
    private static final int DELAY_ID = 18;
    private static final int IS_CANCELLED_ID = 19;
    private static final float ZERO_DELAY = 0.f;
    private static final String QUOTES = "\"";
    private static final String EMPTY_STRING = "";

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
    private static JavaRDD<String> removeQuotes(JavaRDD<String> file) {
        file = file.map(line -> line.replaceAll(QUOTES, EMPTY_STRING));
        return file;
    }
    private static final Function2 removeHeader = new Function2<Integer, Iterator<String>, Iterator<String>>() {
        @Override
        public Iterator<String> call(Integer ind, Iterator<String> iterator) throws Exception{
            if (ind==0 && iterator.hasNext()) {
                iterator.next();
                return iterator;
            } else return iterator;
        }
    };
    public static void main(String[] args) {
        if (args.length != 3) {
            System.exit(-1);
        }
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        String flights = args[0];
        String airports = args[1];
        String output = args[2];
        JavaRDD<String> flightsFile = sc
                .textFile(flights)
                .mapPartitionsWithIndex(removeHeader, false);
        JavaRDD<String> airportsFile = sc
                .textFile(airports)
                .mapPartitionsWithIndex(removeHeader, false);
        flightsFile = removeQuotes(flightsFile);
        airportsFile = removeQuotes(airportsFile);
        JavaPairRDD<Integer, String> airportsData = airportsFile
                .mapToPair(line -> makeAirportPairs(line));
        Map<Integer, String> airportDataMap = airportsData.collectAsMap();
        final Broadcast<Map<Integer, String>> airportsBroadcasted = sc.broadcast(airportDataMap);

        JavaPairRDD<Tuple2<Integer, Integer>, AirportSerializable> flightsData = flightsFile
                .mapToPair(line -> makeFlightPairs(line));

        JavaPairRDD<Tuple2<Integer, Integer>, FlightSerializable> reducedFlights = flightsData
                .combineByKey(
                        item -> new FlightSerializable(item.getDelayTime(),
                                item.getDelayTime() > ZERO_DELAY ? 1 : 0,
                                item.getIsCancelled() ? 1 : 0,
                                1),
                        FlightSerializable::addValue,
                        FlightSerializable::add
                );
        JavaRDD<String> result = reducedFlights
                .map(
                        item -> {
                            Map<Integer, String> airportId = airportsBroadcasted.value();
                            Tuple2<Integer, Integer> key = item._1();
                            float maxDelay = item._2().getMaxDelay();
                            int delayFlights = item._2.getDelayFlights();
                            int cancelledFlights  = item._2.getCancelledFlights();
                            int countOfFlights = item._2.getCountOfFlights();
                            String originAirport = airportId.get(key._1());
                            String destAirport = airportId.get(key._2());
                            String outputResult = originAirport + " -> " + destAirport +
                                    " maxDelay: " + maxDelay + "\n" + "percentage of late + canceled flights: "
                                    + (delayFlights + cancelledFlights) / countOfFlights;
                            return outputResult;
                        }
                );
        result.saveAsTextFile("hdfs://localhost:9000/user/farida/output");
    }
}
