package cs.b07.travelagency;


import static src.application.Constants.CLIENT_FILENAME;
import static src.application.Constants.FLIGHT_FILENAME;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import src.application.Application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LogInActivity extends AppCompatActivity {
  public static final String USER_KEY = "userKey";
  public static final String APP_KEY = "appKey";
  private static final String PW_FILENAME = "passwords.txt";
  public static final String ADMIN_KEY = "admin";
  private Map<String, ArrayList<String>> accounts;
  private Application app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log_in);

    //get the path of the files
    File filePath = this.getApplicationContext().getFilesDir();
    File pwFile = new File(filePath, PW_FILENAME);
    final File clientFile = new File(filePath, CLIENT_FILENAME);
    final File flightFile = new File(filePath, FLIGHT_FILENAME);
    // reads the passwords.txt and create a map
    accounts = new HashMap<>();
    try {
      Scanner scanner = new Scanner(pwFile);
      String[] record;
      while (scanner.hasNextLine()) {
        record = scanner.nextLine().split(",");
        ArrayList<String> accInfo = new ArrayList<>();
        accInfo.add(record[1]);
        accInfo.add(record[2]);
        accounts.put(record[0], accInfo);
      }
      scanner.close();
    } catch (IOException e) {
      System.out.println("Cannot find passwords.txt file.");
    }
    app = new Application();
    app.uploadFlightInfo(flightFile);
    app.uploadClientInfo(clientFile);
  }

  /**
   * log in if the email and password inputted matches as in the passwords.txt
   * start different activity according to log in as client or admin.
   * @param view view
   */
  public void logIn(View view) {
    // gets the inputs
    EditText emailEditText = (EditText) findViewById(R.id.email);
    EditText passwordEditText = (EditText) findViewById(R.id.password);
    String email = emailEditText.getText().toString();
    String password = passwordEditText.getText().toString();
    TextView logInFailure = (TextView) findViewById(R.id.login_failure_message);
    // check if the email or password is correct and rather its a client or admin.
    if (!accounts.containsKey(email)) {
      logInFailure.setText("The email you entered does not exist.\nPlease try again.");
    } else {
      String expectedPassword = accounts.get(email).get(0);
      if (!expectedPassword.equals(password)) {
        logInFailure.setText("The password you entered is not correct.\nPlease try again.");
      } else {
        String userType = accounts.get(email).get(1);
        // if its a client, go to the responding activity
        if (userType.equals("C")) {
          Intent intent = new Intent(this, ClientActivity.class);
          intent.putExtra(USER_KEY, email);
          intent.putExtra(APP_KEY, app);
          intent.putExtra(ADMIN_KEY, false);
          startActivity(intent);
        } else {
          // if its an admin, go to the responding activity
          Intent intent = new Intent(this, AdminActivity.class);
          intent.putExtra(USER_KEY, email);
          intent.putExtra(APP_KEY, app);
          startActivity(intent);
        }

      }
    }
  }

  @Override
  public void onResume() {  // After a pause OR at startup
    super.onResume();
    //Refresh your stuff here
    EditText emailEditText = (EditText) findViewById(R.id.email);
    EditText passwordEditText = (EditText) findViewById(R.id.password);
    TextView logInFailure = (TextView) findViewById(R.id.login_failure_message);
    logInFailure.setText("");
    emailEditText.setText("");
    passwordEditText.setText("");
  }
}
