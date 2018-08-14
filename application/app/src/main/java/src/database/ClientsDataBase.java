package src.database;

import java.io.Serializable;
import java.util.ArrayList;

import src.user.Client;

/**
 * a class use to store the Client objects.
 */
public class ClientsDataBase implements Serializable{
  // A static DataBase to store all uploaded Clients
  private static ArrayList<Client> clients = new ArrayList<>();

  /**
   * gets the clients database.
   * @return ArrayList of clients
   */
  public static ArrayList<Client> getClients() {
    return ClientsDataBase.clients;
  }

  public static Client getClient(String email) {
    for (Client client: clients) {
      if (client.getEmail().equals(email)) {

        return client;
      }
    }
    return null;
  }
  
  // NOT USED FOR PHASE TWO
  /**
   * uploads the given Client object to the database.
   * @param newClient a Client object
   */
  public static void uploadClients(Client newClient) {
    // check if the newClient already exist in the database
    // instead update the client if it exists
	ArrayList<Client> clientsCopy = new ArrayList<>();
	clientsCopy.addAll(clients);
    for (Client client: clientsCopy) {
      if (client.getEmail().equals(newClient.getEmail())) {
        clients.remove(client);
      }
    }
    clients.add(newClient);
  }
}
