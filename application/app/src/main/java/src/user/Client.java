
package src.user;

import java.io.Serializable;
import java.util.ArrayList;

import src.flight.Flight;
import src.flight.Itinerary;

/**
 * class that contains client Info.
 * @author Vincent, Huan
 */
public class Client extends User implements Serializable {
  private static final long serialVersionUID = -1995039742365555552L;
  private String lastName;
  private String firstName;
  private String email;
  private String address;
  private String creditCardNumber;
  private String expiryDate;
  private ArrayList<Itinerary> bookedItinerary;

  /**
   * initial all the variables.
   *
   * @param lastName         the last name of the client
   * @param firstName        the first name of the client
   * @param email            the email of the client
   * @param address          the address of the client
   * @param creditCardNumber the credit card number of the client
   * @param expiryDate       the expire date of the client
   */
  // constructor
  public Client(String lastName, String firstName, String email, String address,
                String creditCardNumber, String expiryDate) {
    super(email);
    this.lastName = lastName;
    this.firstName = firstName;
    this.address = address;
    this.creditCardNumber = creditCardNumber;
    this.expiryDate = expiryDate;
    this.bookedItinerary = new ArrayList<>();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return lastName + "," + firstName + "," + getEmail() + "," + address + ","
            + creditCardNumber + "," + expiryDate;
  }

  public String saveClient() {
    if (bookedItinerary.size() > 0) {
      return toString() + "," + flightNums();
    }
    return toString();
  }

  public void addBookedItinerary(Itinerary itinerary) {
    this.bookedItinerary.add(itinerary);
  }

  public void unBookItinerary(int i) {
    this.bookedItinerary.remove(i);
  }
  public ArrayList<Itinerary> getBookedItinerary() {
    return bookedItinerary;
  }

  private String flightNums() {
    String result = "";
    for (Itinerary itinerary : bookedItinerary) {
      for (Flight flight : itinerary.getItinerary()) {
        result += flight.getFlightNumber() + "-";
      }
      result = result.substring(0, result.length() - 1);
      result += "/";
    }
    result = result.substring(0,result.length() - 1);
    return result;
  }
  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @param creditCardNumber the creditCardNumber to set
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  /**
   * @param expiryDate the expiryDate to set
   */
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }
}

