package src.database;

import java.io.Serializable;
import java.util.ArrayList;

import src.flight.Flight;
import src.flight.Itinerary;


/**
 * a class use to store the Flight objects.
 */
public class FlightsDataBase implements Serializable {
  //A static DataBase to store all uploaded flights.
  private static ArrayList<Flight> flights = new ArrayList<>();
  private static ArrayList<Flight> searchedFlights = new ArrayList<>();
  private static ArrayList<Itinerary> searchedItineraries = new ArrayList<>();


  /**
   * gets the flights database.
   *
   * @return ArrayList of flights
   */
  public static ArrayList<Flight> getFlights() {
    return flights;
  }

  public static Flight getFlight(String flightNum) {
    for (Flight flight : flights) {
      if (flight.getFlightNumber().equals(flightNum)) {
        return flight;
      }
    }
    return null;
  }

  /**
   * set the Database to the given list of flights.
   *
   * @param flights list of flights
   */
  public static void setFlights(ArrayList<Flight> flights) {
    FlightsDataBase.flights = flights;
  }


  // NOT USED FOR PHASE TWO

  /**
   * upload the given Flight object to the database.
   *
   * @param newFlight a Flight object
   */
  public static void uploadFlight(Flight newFlight) {
    // check if the newClient already exist in the database
    // instead update the client if it exists
    ArrayList<Flight> flightsCopy = new ArrayList<>();
    flightsCopy.addAll(flights);
    for (Flight flight : flightsCopy) {
      if (flight.getFlightNumber().equals(newFlight.getFlightNumber())) {
        flights.remove(flight);
      }
    }
    flights.add(newFlight);
  }

  public static ArrayList<Itinerary> getSearchedItineraries() {
    return searchedItineraries;
  }

  /**
   * sorts searched itineraries by cost.
   */
  public static void sortByTotalCost() {
    Itinerary temp;
    // bubble sort
    for (int i = 0; i < searchedItineraries.size() - 1; i++) {
      for (int j = i + 1; j < searchedItineraries.size(); j++) {
        if (searchedItineraries.get(j).getTotalCost()
                <= searchedItineraries.get(i).getTotalCost()) {
          // swap the position
          temp = searchedItineraries.get(i);
          searchedItineraries.set(i, searchedItineraries.get(j));
          searchedItineraries.set(j, temp);
        }
      }
    }
  }


  /**
   * sorts searched itineraries by time.
   */
  public static void sortByTotalTravelTime() {
    Itinerary temp;
    // bubble sort
    for (int i = 0; i < searchedItineraries.size() - 1; i++) {
      for (int j = i + 1; j < searchedItineraries.size(); j++) {
        if (searchedItineraries.get(j).getTotalTravelTime()
                <= searchedItineraries.get(i).getTotalTravelTime()) {
          // swap the position
          temp = searchedItineraries.get(i);
          searchedItineraries.set(i, searchedItineraries.get(j));
          searchedItineraries.set(j, temp);
        }
      }
    }
  }


  /**
   * displays searched itineraries with specific format.
   * @return string representation of itineraries.
   */
  public static String displayItineraries2() {
    String itineraries = "";
    // one itinerary is after another
    for (Itinerary itinerary : searchedItineraries) {
      itineraries += itinerary.toString();
    }
    // remove the last newline character when returning
    return itineraries.substring(0, itineraries.length() - 1);
  }
  /**
   * displays searched itineraries with specific format.
   * @return string representation of itineraries.
   */
  public static ArrayList<String> displayItineraries() {
    ArrayList<String> itineraries = new ArrayList<>();
    // one itinerary is after another
    for (Itinerary itinerary : searchedItineraries) {
      itineraries.add(itinerary.toString());
    }
    return itineraries;
  }

  /**
   * set the searched Itineraries.
   * @param searchedItineraries searchedItineraries
   */
  public static void setSearchedItineraries(ArrayList<Itinerary> searchedItineraries) {
    FlightsDataBase.searchedItineraries = searchedItineraries;
  }
  /**
   * set the searched Flights.
   * @param searchedFlights searchedFlights
   */
  public static void setSearchedFlights(ArrayList<Flight> searchedFlights) {
    FlightsDataBase.searchedFlights = searchedFlights;
  }

  /**
   * convert a flight to an itineraries.
   */
  public static void makeItinerary() {
    ArrayList<Itinerary> temp = new ArrayList<>();
    for (Flight flight : searchedFlights) {
      Itinerary itinerary = new Itinerary();
      itinerary.addFlight(flight);
      temp.add(itinerary);
    }
    setSearchedItineraries(temp);
  }
}
