package cs.b07.travelagency;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import src.application.Application;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {
  private Application app;
  private String email;
  public static final String ORIGIN_KEY = "origin";
  public static final String DESTINATION_KEY = "destination";
  public static final String DATE_KEY = "date";
  public static final String DIRECT_KEY = "direct";
  public static EditText dateEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // go back button
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
    email = (String) intent.getSerializableExtra(LogInActivity.USER_KEY);

    Button btn = (Button) findViewById(R.id.select_date_button);
    dateEditText = (EditText) findViewById(R.id.date_field);
    // create a dialog when button clicked
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int myYear = calendar.get(Calendar.YEAR);
        int myMonth = calendar.get(Calendar.MONTH);
        int myDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(SearchActivity.this,
                new MyDateSetListener(), myYear, myMonth, myDay);
        dialog.show();
      }
    });
  }


  /**
   * search flight or itinerary with given inputs, origin, destination, and date
   * go to the display activity to display.
   * @param view view
   */
  public void search(View view) {
    // gets the inputs
    EditText originEditText = (EditText) findViewById(R.id.origin_field);
    EditText destinationEditText = (EditText) findViewById(R.id.destination_field);
    EditText dateEditText = (EditText) findViewById(R.id.date_field);
    String origin = originEditText.getText().toString();
    String destination = destinationEditText.getText().toString();
    String date = dateEditText.getText().toString();
    TextView searchFailure = (TextView) findViewById(R.id.search_failure);
    CheckBox directFlightCheck = (CheckBox) findViewById(R.id.check_direct_flight);
    Boolean checked = directFlightCheck.isChecked();
    // check if every input is valid
    if (origin.isEmpty()) {
      searchFailure.setText(R.string.enter_origin);
    } else if (destination.isEmpty()) {
      searchFailure.setText(R.string.enter_des);
    } else if (date.isEmpty()) {
      searchFailure.setText(R.string.enter_date);
    } else {
      // search flight or itinerary base on the check box
      String departureDate = dateEditText.getText().toString();
      app.searchItinerary(departureDate, origin, destination);
      if (checked) {
        app.searchDirectItinerary(departureDate, origin, destination);
      } else {
        app.searchItinerary(departureDate, origin, destination);
      } // if successfully searched itinerary then starts a new activity to display
      if (app.hasItinerary()) {
        Intent intent = new Intent(this, DisplayItineraryActivity.class);
        intent.putExtra(ORIGIN_KEY, origin);
        intent.putExtra(DESTINATION_KEY, destination);
        intent.putExtra(DATE_KEY, departureDate);
        intent.putExtra(DIRECT_KEY, checked);
        intent.putExtra(LogInActivity.APP_KEY, app);
        intent.putExtra(LogInActivity.USER_KEY, email);
        startActivity(intent);
      } else {
        Toast.makeText(SearchActivity.this, "No Itineraries Found", Toast.LENGTH_LONG).show();
      }

    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        this.onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onResume() {  // After a pause OR at startup
    super.onResume();
    //Refresh stuff here
    EditText originEditText = (EditText) findViewById(R.id.origin_field);
    EditText destinationEditText = (EditText) findViewById(R.id.destination_field);
    EditText dateEditText = (EditText) findViewById(R.id.date_field);
    final CheckBox directCheckBox = (CheckBox) findViewById(R.id.check_direct_flight);
    originEditText.setText("");
    destinationEditText.setText("");
    dateEditText.setText("");
    directCheckBox.setChecked(false);
  }
}


