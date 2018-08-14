package cs.b07.travelagency;


import static src.application.Constants.CLIENT_FILENAME;
import static src.application.Constants.FLIGHT_FILENAME;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import src.application.Application;

import java.io.File;




public class EditFlightActivity extends AppCompatActivity {
  private Application app;
  private String flightNum;
  private EditText flightNumEditText;
  private EditText departureEditText;
  private EditText arrivalEditText;
  private EditText airlineEditText;
  private EditText originEditText;
  private EditText destinationEditText;
  private EditText priceEditText;
  private EditText seatsEditText;
  private boolean searched = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_flight);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    // go back button
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
    flightNumEditText = (EditText) findViewById(R.id.flight_number_field);
    departureEditText = (EditText) findViewById(R.id.departure_field);
    arrivalEditText = (EditText) findViewById(R.id.arrival_field);
    airlineEditText = (EditText) findViewById(R.id.airline_field);
    originEditText = (EditText) findViewById(R.id.origin_field_2);
    destinationEditText = (EditText) findViewById(R.id.destination_field_2);
    priceEditText = (EditText) findViewById(R.id.price_field);
    seatsEditText = (EditText) findViewById(R.id.seats_field);

  }

  /**
   * search a flight given a flight number as input.
   * @param view view
   */
  public void searchFlightNum(View view) {
    EditText flightEditText = (EditText) findViewById(R.id.search_flight);
    flightNum = flightEditText.getText().toString();
    // check if the input is invalid or on flight is searched
    if (flightNum.equals("")) {
      resetEditText();
      Toast.makeText(EditFlightActivity.this, "Missing a Flight Number", Toast.LENGTH_LONG).show();
    } else if (app.getFlight(flightNum) == null) {
      resetEditText();
      Toast.makeText(EditFlightActivity.this, "No Flights Found", Toast.LENGTH_LONG).show();
    } else {
      // display the flight information to the corresponding text view
      String[] flightInfo = app.getFlight(flightNum).saveFlight().split(",");
      searched = true;
      flightNumEditText.setText(flightInfo[0]);
      departureEditText.setText(flightInfo[1]);
      arrivalEditText.setText(flightInfo[2]);
      airlineEditText.setText(flightInfo[3]);
      originEditText.setText(flightInfo[4]);
      destinationEditText.setText(flightInfo[5]);
      priceEditText.setText(flightInfo[6]);
      seatsEditText.setText(flightInfo[7]);
    }
  }

  private void resetEditText() {
    searched = false;
    flightNumEditText.setText("");
    departureEditText.setText("");
    arrivalEditText.setText("");
    airlineEditText.setText("");
    originEditText.setText("");
    destinationEditText.setText("");
    priceEditText.setText("");
    seatsEditText.setText("");
  }

  public void editFlight(View view) {
    if (searched) {
      if (hasFilledAll()) {
        app.getFlight(flightNum).setFlightNumber(flightNumEditText.getText().toString());
        app.getFlight(flightNum).setDepartureDateTime(departureEditText.getText().toString());
        app.getFlight(flightNum).setArrivalDateTime(arrivalEditText.getText().toString());
        app.getFlight(flightNum).setAirline(airlineEditText.getText().toString());
        app.getFlight(flightNum).setOrigin(originEditText.getText().toString());
        app.getFlight(flightNum).setDestination(destinationEditText.getText().toString());
        app.getFlight(flightNum).setCost(priceEditText.getText().toString());
        app.getFlight(flightNum).setSeats(seatsEditText.getText().toString());
        File filePath = this.getApplicationContext().getFilesDir();
        File flightFile = new File(filePath, FLIGHT_FILENAME);
        app.saveFlight(flightFile);
        File clientFile = new File(filePath, CLIENT_FILENAME);
        app.saveClient(clientFile);
        Toast.makeText(EditFlightActivity.this, "Change Made", Toast.LENGTH_LONG).show();
        onBackPressed();
      } else {
        Toast.makeText(EditFlightActivity.this, "Please fill out", Toast.LENGTH_LONG).show();
      }
    } else {
      Toast.makeText(EditFlightActivity.this, "Please Search A Flight", Toast.LENGTH_LONG).show();
    }

  }

  private boolean hasFilledAll() {
    if (flightNumEditText.getText().toString().isEmpty()) {
      return false;
    } else if (departureEditText.getText().toString().isEmpty()) {
      return false;
    } else if (arrivalEditText.getText().toString().isEmpty()) {
      return false;
    } else if (airlineEditText.getText().toString().isEmpty()) {
      return false;
    } else if (originEditText.getText().toString().isEmpty()) {
      return false;
    } else if (departureEditText.getText().toString().isEmpty()) {
      return false;
    } else if (priceEditText.getText().toString().isEmpty()) {
      return false;
    } else if (seatsEditText.getText().toString().isEmpty()) {
      return false;
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        this.onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
