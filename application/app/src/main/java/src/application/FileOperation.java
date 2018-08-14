
package src.application;

import src.database.ClientsDataBase;
import src.database.FlightsDataBase;
import src.flight.Flight;
import src.flight.Itinerary;
import src.user.Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;




/**
 * A class that can reads the stored flights and clients text file.
 */
public class FileOperation implements Serializable {
  private static final long serialVersionUID = -1995039742365022232L;

  private File file;

  /**
   * initial the path.
   * @param file file
   */
  //constructor
  public FileOperation(File file) throws IOException {
    this.file = file;
  }

  /**
   * This method reads the flight text file and
   * upload the Flight to the flights database.
   */
  public void readFlights() throws FileNotFoundException {
    Scanner sc = new Scanner(file);
    while (sc.hasNextLine()) {  // keep reading the file until the end
      String line = sc.nextLine();
      String[] flightInfo = line.split(",");
      // create flight object by assigning corresponding FlightInfo from the list
      Flight flight = new Flight(flightInfo[0], flightInfo[1], flightInfo[2], flightInfo[3],
              flightInfo[4], flightInfo[5], flightInfo[6], flightInfo[7]);
      // add the Flight object to the data base
      //FlightsDataBase.uploadFlight(flight);
      FlightsDataBase.uploadFlight(flight);
    }
    sc.close();
  }

  /**
   * This method reads the clients text file and
   * upload the Client to the clients database.
   */
  public void readClients() throws FileNotFoundException {
    Scanner sc = new Scanner(file);
    while (sc.hasNextLine()) {  // keep reading the file until the end
      String line = sc.nextLine();
      String[] clientInfo = line.split(",");
      // create client object by assigning corresponding ClientInfo from the list
      Client client = new Client(clientInfo[0], clientInfo[1], clientInfo[2],
              clientInfo[3], clientInfo[4], clientInfo[5]);
      if (clientInfo.length == 7) {
        String[] bookedItineraries = clientInfo[6].split("/");
        for (String flights : bookedItineraries) {
          String[] bookedFlights = flights.split("-");
          Itinerary itinerary = new Itinerary();
          for (String flightNum : bookedFlights) {
            Flight flight = FlightsDataBase.getFlight(flightNum);
            itinerary.addFlight(flight);
          }
          client.addBookedItinerary(itinerary);
        }
      }
      // add the Client object to the data base
      ClientsDataBase.uploadClients(client);
    }
    sc.close();
  }

  /**
   * write the client information into the file.
   */
  public void saveClient() throws FileNotFoundException {
    PrintWriter outPutStream = new PrintWriter(file.getPath());
    for (Client client : ClientsDataBase.getClients()) {
      outPutStream.println(client.saveClient());
    }
    outPutStream.close();
  }

  /**
   * write the flight information into the file.
   */
  public void saveFlight() throws FileNotFoundException {
    PrintWriter outPutStream = new PrintWriter(file.getPath());
    for (Flight flight : FlightsDataBase.getFlights()) {
      outPutStream.println(flight.saveFlight());
    }
    outPutStream.close();
  }
}
