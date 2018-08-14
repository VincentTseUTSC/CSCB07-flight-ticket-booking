package src.application;

import java.io.Serializable;

/**
 * class that contains the constants variables.
 */
public class Constants implements Serializable {
  //The format of Date time.
  public static final String FORMAT = "yyyy-MM-dd HH:mm";
  //The waiting time between flights
  public static final long WAIT_TIME_MAX = 21600000;
  public static final long WAIT_TIME_MIN = 1800000;
  // flight and client file name
  public static final String CLIENT_FILENAME = "clients_data.txt";
  public static final String FLIGHT_FILENAME = "flights_data.txt";

}
