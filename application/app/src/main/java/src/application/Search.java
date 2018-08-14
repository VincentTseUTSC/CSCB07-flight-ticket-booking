package src.application;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import src.database.ClientsDataBase;
import src.database.FlightsDataBase;
import src.flight.Flight;
import src.flight.Itinerary;
import src.user.Client;

import static src.application.Constants.FORMAT;
import static src.application.Constants.WAIT_TIME_MAX;
import static src.application.Constants.WAIT_TIME_MIN;


/**
 * A class the does all the search functions.
 */
public class Search implements Serializable {
  // variables
  private HashMap<Flight, ArrayList<Flight>> itineraries;
  private ArrayList<Flight> searchedFlights;
  private ArrayList<Itinerary> searchedItineraries;
  
  /**
   * search the specific client given email.
   * @param email of the client
   * @return the string representation of the client.
   */
  public String searchClient(String email) {
    // check each client in the database 
    for (Client client: ClientsDataBase.getClients()) {
      if (client.getEmail().equals(email)) {
        return client.toString(); 
      }
    }
    return "";
  }

  /**
   * check if flight2 is a valid flight to be added.
   * flight2 departs after flight1 arrives
   * flight2 departs no longer than 6 hours between flight1
   * @param flight1 the last flight in the itinerary
   * @param flight2 the flight to be add
   * @return true if flight2 is available to add.
   */
  private boolean isAvailable(Flight flight1, Flight flight2) {
    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(FORMAT);
    long departureWaitTime = 0;
    try {
      // calculate waiting time between two flights
      Date departureTime = format.parse(flight2.getDepartureDateTime());
      Date arrivalTime = format.parse(flight1.getArrivalDateTime());
      departureWaitTime = departureTime.getTime() - arrivalTime.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    // return if flight2 reach the condition
    return ((flight1.getDestination().equals(flight2.getOrigin()))
        && (departureWaitTime >= WAIT_TIME_MIN  && departureWaitTime <= WAIT_TIME_MAX));
  }
  
  /**
   * Initialize the Graph for searching.
   * key is a flight.
   * value is a list of flights that depart from where the key arrives.
   * all flights in the value are guaranteed valid flights.
   */
  private void createGraph() {
    itineraries = new HashMap<>();
    // initialize the graph, creating keys and empty list as value
    for (int i = 0; i < FlightsDataBase.getFlights().size(); i++) {
      if (FlightsDataBase.getFlights().get(i).hasSeats()) {
        itineraries.put(FlightsDataBase.getFlights().get(i), new ArrayList<Flight>());
        // check if the flight is available to be added to the value
        // if so, add new values into the key
        for (int j = 0; j < FlightsDataBase.getFlights().size(); j++) {
          if ((i != j) && isAvailable(FlightsDataBase.getFlights().get(i),
                  FlightsDataBase.getFlights().get(j)) && FlightsDataBase.
                  getFlights().get(j).hasSeats()) {
            itineraries.get(FlightsDataBase.getFlights().get(i)).add(
                    FlightsDataBase.getFlights().get(j));
          }
        }
      }
    }
  }

  /**
   * find all flights that have the right origin.
   * @param departureDate date which it departs
   * @param origin city of departure
   * @return ArrayList of flights.
   */
  public ArrayList<Flight> searchStartingFlights(String departureDate, String origin) {
    ArrayList<Flight> startingFlights = new ArrayList<>();
    // add flights that have the correct origin to the return list
    for (Flight flight : itineraries.keySet()) { // for key in keys
      if ((flight.getOrigin().equals(origin)) && flight.sameDepDate(departureDate)) {
        startingFlights.add(flight);
      }
    }
    return startingFlights;
  }

  /**
   * The search Process, add the expected itinerary to the searchedItinerary list.
   * @param flights list of keys from the graph
   * @param tempItinerary temporary Itinerary to be keep tracked
   * @param destination the final destination
   */
  private void searchItineraryProcess(ArrayList<Flight> flights, Itinerary tempItinerary,
      String destination) {
    if (!flights.isEmpty()) { // base case, check if the key is empty
      for (Flight flight : flights) { // loop through every key is the list of key
        if (!tempItinerary.hasFlown(flight)) { // check if the flight visit repeated city
          // create new itinerary combining tracked tempItinerary and the new flight
          Itinerary newItinerary = new Itinerary(); 
          newItinerary.extendItinerary(tempItinerary);
          newItinerary.addFlight(flight);
          // add the wanted itinerary to the searchedItinerary list
          if (flight.getDestination().equals(destination)) {
            searchedItineraries.add(newItinerary);
          // otherwise, re-do the process recursively
          // the list of flight from the value will be the new list of keys 
          // the new itinerary create will be keep tracked
          } else {
            searchItineraryProcess(itineraries.get(flight), newItinerary, destination);
          }
        }
      }
    }
  }

  /**
   * search itineraries with given departure date, origin, and destination.
   * @param departureDate date of departure
   * @param origin city of departure
   * @param destination city of arrival
   */
  public void searchItinerary(String departureDate, String origin, String destination) {
    createGraph(); // create a graph for searching and a list for storing
    searchedItineraries = new ArrayList<>();
    // gets all flights starting from the origin
    ArrayList<Flight> startingFlights = searchStartingFlights(departureDate, origin);
    Itinerary tempItinerary = new Itinerary();
    // search all possible itineraries
    searchItineraryProcess(startingFlights, tempItinerary, destination);
    // return the searched itineraries with specific string representation
    FlightsDataBase.setSearchedItineraries(searchedItineraries);
  }

  /**
   * search flights with given departure date, origin, and destination.
   * @param departureDate date of departure
   * @param origin city of departure
   * @param destination city of arrival
   */
  public void searchFlights(String departureDate, String origin, String destination) {
    searchedFlights = new ArrayList<>(); // create list for storing
    // loop through every flight in the database and add wanted flights to the list
    for (Flight flight: FlightsDataBase.getFlights()) {
      if ((flight.getOrigin().equals(origin)) && (flight.getDestination().equals(destination)) 
          && (flight.sameDepDate(departureDate)) && flight.hasSeats()) {
        searchedFlights.add(flight);
      }
    }
    FlightsDataBase.setSearchedFlights(searchedFlights);
    // return the searched itineraries with specific string representation
  }

  /**
   * displays searched flights with specific format.
   * @return string representation of flights.
   */
  public String displayFlights() {
    String flights = "";
    // each line is a flight
    for (Flight flight: searchedFlights) {
      flights += flight.displayFlight() + "\n";
    }
    // remove the last newline character when returning
    return flights.substring(0, flights.length() - 1);
  }
}
