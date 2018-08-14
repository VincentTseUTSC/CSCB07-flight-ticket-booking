package src.flight;

import java.io.Serializable;

/**
 * class represents the flight.
 */
public class Flight implements Serializable {
  private String flightNumber;
  private String departureDateTime;
  private String arrivalDateTime;
  private String airline;
  private String origin;
  private String destination;
  private double cost;
  private int seats;

  /**
   * Contractor, initial the variables.
   * @param flightNumber the flight number of the flight
   * @param depDateTime the departure date and time of the flight
   * @param arrDateTime the flight number of the flight
   * @param airline the airline of the flight
   * @param origin the origin of the flight
   * @param destination the destination of the flight
   * @param cost the cost of the flight
   */
  public Flight(String flightNumber, String depDateTime, String arrDateTime, String airline,
          String origin, String destination, String cost, String seats) {
    this.flightNumber = flightNumber;
    this.departureDateTime = depDateTime;
    this.arrivalDateTime = arrDateTime;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.cost = Double.parseDouble(cost);
    this.seats = Integer.parseInt(seats);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return flightNumber + "," + departureDateTime + "," + arrivalDateTime + "," + airline + "," 
      + origin + "," + destination;
  }
  /**
   *  display the flight with given format.
   * @return string representation of flight
   */
  public String displayFlight() {
    return flightNumber + "," + departureDateTime + "," + arrivalDateTime + "," + airline + "," 
        + origin + "," + destination + "," + String.format("%.2f", cost);
  }
  public String saveFlight() {
    return flightNumber + "," + departureDateTime + "," + arrivalDateTime + "," + airline + ","
            + origin + "," + destination + "," + String.format("%.2f", cost) + "," + seats;
  }

  /**
   * Return a boolean if the given departure date is same as this flight departure date.
   * @param departureDate given a departure date
   * @return boolean
   */
  public boolean sameDepDate(String departureDate) {
    return departureDateTime.substring(0, 10).equals(departureDate);
  }

  /**
   * gets the flightNumber.
   * @return the flightNumber
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  /**
   * gets the departureDateTime.
   * @return the departureDateTime
   */
  public String getDepartureDateTime() {
    return departureDateTime;
  }

  /**
   * gets the arrivalDateTime.
   * @return the arrivalDateTime
   */
  public String getArrivalDateTime() {
    return arrivalDateTime;
  }

  /**
   * gets the origin.
   * @return the origin
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * gets the destination.
   * @return the destination
   */
  public String getDestination() {
    return destination;
  }

  /**
   * gets the cost.
   * @return the cost
   */
  public double getCost() {
    return cost;
  }

  /**
   * check if there still seats remain
   * @return has seats
   */
  public boolean hasSeats() {
    return seats > 0;
  }

  /**
   * decrease the seats.
   */
  public void book() {
    seats -= 1;
  }

  /**
   * increase the seats.
   */
  public void unBook() {
    seats += 1;
  }

  /**
   * sets flight number.
   * @param flightNumber the flightNumber to set
   */
  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  /**
   * sets departure Date Time.
   * @param departureDateTime the departureDateTime to set
   */
  public void setDepartureDateTime(String departureDateTime) {
    this.departureDateTime = departureDateTime;
  }

  /**
   * sets arrival Date Time.
   * @param arrivalDateTime the arrivalDateTime to set
   */
  public void setArrivalDateTime(String arrivalDateTime) {
    this.arrivalDateTime = arrivalDateTime;
  }

  /**
   * sets airline.
   * @param airline the airline to set
   */
  public void setAirline(String airline) {
    this.airline = airline;
  }

  /**
   * sets origin.
   * @param origin the origin to set
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * sets destination.
   * @param destination the destination to set
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * sets cost.
   * @param cost the cost to set
   */
  public void setCost(String cost) {
    this.cost = Double.parseDouble(cost);
  }

  /**
   * sets seats.
   * @param seats seats
   */
  public void setSeats(String seats) {
    this.seats = Integer.parseInt(seats);
  }
}
