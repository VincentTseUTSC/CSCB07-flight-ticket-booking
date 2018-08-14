package src.flight;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static src.application.Constants.FORMAT;

/**
 * class that represents the itinerary.
 */
public class Itinerary implements Serializable {
  // private variables
  private double totalCost;
  private long totalTravelTime;
  private ArrayList<Flight> itinerary;

  /**
   * initial the variable.
   */
  // constructor
  public Itinerary() {
    itinerary = new ArrayList<>();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String result = "";
    // gets every flight's string representation
    for (Flight flight: itinerary) {
      result += flight.toString() + "\n";
    }
    // add the totalCost and travelTime to the end
    // totalCost needs exact two decimals and travelTime needs a exact formant
    return result + String.format("%.2f", totalCost) + "\n" + displayTotalTravelTime() + "\n";
  }

  /**
   * add the given flight to the itinerary.
   * @param flight the flight to add to the itinerary
   */
  public void addFlight(Flight flight) {
    itinerary.add(flight);
    // update the cost and travelTime
    updateTotalCost(flight.getCost());
    updateTotalTravelTime();
  }

  /**
   * return true if the given flight arrives a city that has already visited.
   * @param flight the flight that compares to.
   * @return boolean
   */
  public boolean hasFlown(Flight flight) {
    // check the given flight's destination with every flight's origin 
    // in the itinerary.
    for (Flight fli: itinerary) {
      if (fli.getOrigin().equals(flight.getDestination())) {
        return true;
      }
    }
    return false;
  }

  /**
   * add all the flights from the given itinerary to this itinerary.
   * @param itinerary the itinerary to add
   */
  public void extendItinerary(Itinerary itinerary) {
    this.itinerary.addAll(itinerary.getItinerary());
    // also update the Cost and travel time
    updateTotalCost(itinerary.getTotalCost());
    if (this.itinerary.size() > 0) {
      updateTotalTravelTime();
    }
  }

  /**
   * gets the itinerary.
   * @return the itinerary
   */
  public ArrayList<Flight> getItinerary() {
    return itinerary;
  }

  /**
   * gets the totalCost.
   * @return the totalCost
   */
  public double getTotalCost() {
    return totalCost;
  }

  /**
   * return the string representation of the totalTravelTime with specific formant.
   * @return the totalTravelTime
   */
  private String displayTotalTravelTime() {
    // converts the TotalTravelTime to minutes and hours from millisecond
    long totalTravelTime = this.totalTravelTime / 60000;
    long hours = (((int) totalTravelTime) / 60);
    long minutes = totalTravelTime % 60;
    // formalize the string representation
    String travelTime = "";
    if ((hours / 10) < 1) {
      travelTime += "0";
    }
    travelTime += hours + ":";
    if ((minutes / 10) < 1) {
      travelTime += "0";
    }
    travelTime += minutes;
    return travelTime;
  }

  /**
   * add the given cost to the total cost.
   * @param cost the totalCost to set
   */
  public void updateTotalCost(double cost) {
    totalCost += cost;
  }

  /**
   * update the total travel time.
   */
  public void updateTotalTravelTime() {
    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(FORMAT);
    Date depTime;
    Date arrTime;
    // recalculate the totalTravelTime
    // takes the arrivalTime of the last flight and subtract the departureTime of the first flight.
    try {
      depTime = format.parse(itinerary.get(0).getDepartureDateTime());
      arrTime = format.parse(itinerary.get(itinerary.size() - 1).getArrivalDateTime());
      totalTravelTime = arrTime.getTime() - depTime.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * gets the totalTravelTime.
   * @return the totalTravelTime
   */
  public long getTotalTravelTime() {
    return totalTravelTime;
  }

  /**
   * decrease seats per flight.
   */
  public void book() {
    for (Flight flight: itinerary) {
      flight.book();
    }
  }

  /**
   * increase seats per flight.
   */
  public void unBook() {
    for (Flight flight: itinerary) {
      flight.unBook();
    }
  }
}
