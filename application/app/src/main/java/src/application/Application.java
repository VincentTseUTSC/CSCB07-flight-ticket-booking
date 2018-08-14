package src.application;

import src.database.ClientsDataBase;
import src.database.FlightsDataBase;
import src.flight.Flight;
import src.flight.Itinerary;
import src.user.Client;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * The Application class.
 */
public class Application implements Serializable {
  private static final long serialVersionUID = -1995039742365021132L;

  /**
   * Uploads client information to the application from the file at the
   * given path.
   *
   * @param file the path to an input csv file of client information with
   *             lines in the format:
   *             LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
   *             (the ExpiryDate is stored in the format YYYY-MM-DD)
   */
  public void uploadClientInfo(File file) {
    try {
      FileOperation fo = new FileOperation(file);
      fo.readClients();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Uploads flight information to the application from the file at the
   * given path.
   *
   * @param file the path to an input csv file of flight information with
   *             lines in the format:
   *             Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price
   *             (the dates are in the format YYYY-MM-DD; the price has exactly two
   *             decimal places)
   */
  public void uploadFlightInfo(File file) {
    try {
      FileOperation fo = new FileOperation(file);
      fo.readFlights();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the information stored for the client with the given email.
   *
   * @param email the email address of a client
   * @return the information stored for the client with the given email
   * in this format:
   * LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
   * (the ExpiryDate is stored in the format YYYY-MM-DD)
   */
  public String retrieveClient(String email) {
    Search search = new Search();
    return search.searchClient(email);
  }

  /**
   * Returns all flights that depart from origin and arrive at destination on
   * the given date.
   *
   * @param date        a departure date (in the format YYYY-MM-DD)
   * @param origin      a flight origin
   * @param destination a flight destination
   * @return the flights that depart from origin and arrive at destination
   * on the given date formatted with one flight per line in exactly this
   * format:
   * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price
   * (the dates are in the format YYYY-MM-DD; the price has exactly two
   * decimal places)
   */
  public String retrieveFlights(String date, String origin, String destination) {
    Search search = new Search();
    search.searchFlights(date, origin, destination);
    return search.displayFlights();
  }

  /**
   * Returns all itineraries that depart from origin and arrive at
   * destination on the given date. If an itinerary contains two consecutive
   * flights F1 and F2, then the destination of F1 should match the origin of
   * F2. To simplify our task, if there are more than 6 hours between the
   * arrival of F1 and the departure of F2, then we do not consider this
   * sequence for a possible itinerary (we judge that the stopover is too
   * long).
   *
   * @param date        a departure date (in the format YYYY-MM-DD)
   * @param origin      a flight original
   * @param destination a flight destination
   * @return itineraries that depart from origin and arrive at
   * destination on the given date with stopovers at or under 6 hours.
   * Each itinerary in the output should contain one line per flight,
   * in the format:
   * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   * followed by total price (on its own line, exactly two decimal places),
   * followed by total duration (on its own line, in format HH:MM).
   */
  public String retrieveItineraries(String date, String origin, String destination) {
    Search search = new Search();
    search.searchItinerary(date, origin, destination);
    return FlightsDataBase.displayItineraries2();
  }

  /**
   * Returns the same itineraries as getItineraries produces, but sorted according
   * to total itinerary cost, in non-decreasing order.
   *
   * @param date        a departure date (in the format YYYY-MM-DD)
   * @param origin      a flight original
   * @param destination a flight destination
   * @return itineraries (sorted in non-decreasing order of total itinerary cost)
   * that depart from origin and arrive at
   * destination on the given date with stopovers at or under 6 hours.
   * Each itinerary in the output should contain one line per flight,
   * in the format:
   * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   * followed by total price (on its own line, exactly two decimal places),
   * followed by total duration (on its own line, in format HH:MM).
   */
  public String retrieveItinerariesSortedByCost(String date, String origin, String destination) {
    Search search = new Search();
    search.searchItinerary(date, origin, destination);
    FlightsDataBase.sortByTotalCost();
    return FlightsDataBase.displayItineraries2();
  }

  /**
   * Returns the same itineraries as getItineraries produces, but sorted according
   * to total itinerary travel time, in non-decreasing order.
   *
   * @param date        a departure date (in the format YYYY-MM-DD)
   * @param origin      a flight original
   * @param destination a flight destination
   * @return itineraries (sorted in non-decreasing order of travel itinerary travel time)
   * that depart from origin and arrive at
   * destination on the given date with stopovers at or under 6 hours.
   * Each itinerary in the output should contain one line per flight,
   * in the format:
   * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   * followed by total price (on its own line, exactly two decimal places),
   * followed by total duration (on its own line, in format HH:MM).
   */
  public String retrieveItinerariesSortedByTime(String date, String origin, String destination) {
    Search search = new Search();
    search.searchItinerary(date, origin, destination);
    FlightsDataBase.sortByTotalTravelTime();
    return FlightsDataBase.displayItineraries2();
  }

  /**
   * sort itineraries by cost and display it.
   *
   * @return string representation of itineraries
   */
  public ArrayList<String> displayItinerariesByCost() {
    FlightsDataBase.sortByTotalCost();
    return FlightsDataBase.displayItineraries();
  }

  /**
   * sort itineraries by cost and display it.
   *
   * @return string representation of itineraries
   */
  public ArrayList<String> displayItinerariesByTime() {
    FlightsDataBase.sortByTotalTravelTime();
    return FlightsDataBase.displayItineraries();
  }

  /**
   * return client given email.
   *
   * @param email email of the client
   * @return client
   */
  public Client getClient(String email) {
    return ClientsDataBase.getClient(email);
  }

  /**
   * return flight given flight number.
   *
   * @param flightNum flight number of the flight
   * @return flight
   */
  public Flight getFlight(String flightNum) {
    return FlightsDataBase.getFlight(flightNum);
  }

  /**
   * return itinerary from the itineraries list given an index.
   *
   * @param index index of the itineraries list
   * @return itinerary
   */
  public Itinerary getItinerary(int index) {
    return FlightsDataBase.getSearchedItineraries().get(index);
  }

  /**
   * search flight given date, origin, and destination, then make it as an itinerary.
   *
   * @param date        date
   * @param origin      origin
   * @param destination destination
   */
  public void searchDirectItinerary(String date, String origin, String destination) {
    Search search = new Search();
    search.searchFlights(date, origin, destination);
    FlightsDataBase.makeItinerary();
  }

  /**
   * search itinerary given date, origin, and destination.
   *
   * @param date        date
   * @param origin      origin
   * @param destination destination
   */
  public void searchItinerary(String date, String origin, String destination) {
    Search search = new Search();
    search.searchItinerary(date, origin, destination);
  }

  /**
   * find the client given email and add the itinerary into the booked list.
   *
   * @param email     email of client
   * @param itinerary itinerary
   */
  public void bookItinerary(String email, Itinerary itinerary) {
    Client client = getClient(email);
    itinerary.book();
    client.addBookedItinerary(itinerary);
  }

  /**
   * find the client given email and remove the itinerary from the booked list.
   *
   * @param email email of client
   * @param index index of the booked list
   */
  public void unBookItinerary(String email, int index) {
    Client client = getClient(email);
    client.getBookedItinerary().get(index).unBook();
    client.unBookItinerary(index);
  }

  /**
   * write all the client information into the file.
   *
   * @param file client file
   */
  public void saveClient(File file) {
    try {
      FileOperation fo = new FileOperation(file);
      fo.saveClient();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * write all the flight information into the file.
   *
   * @param file flight file
   */
  public void saveFlight(File file) {
    try {
      FileOperation fo = new FileOperation(file);
      fo.saveFlight();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * return rather search succeed.
   *
   * @return true if at least one itinerary is search.
   */
  public boolean hasItinerary() {
    return FlightsDataBase.getSearchedItineraries().size() > 0;
  }
}