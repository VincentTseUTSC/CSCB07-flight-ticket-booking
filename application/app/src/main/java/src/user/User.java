package src.user;

import java.io.Serializable;

import src.application.Application;

public abstract class User implements Serializable {
  private String email;
  /**
   * initial the variable.
   * @param userEmail the email of the user
   */
  public User(String userEmail) {
    this.email = userEmail;
  }
  
  /**
   * Gets the user's email.
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  public void launchApp() {
    Application app = new Application();
  }
  /**
   * Sets the user's email to the given email.
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }
  
}
