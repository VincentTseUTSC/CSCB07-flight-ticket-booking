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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import src.application.Application;

import java.io.File;
import java.util.ArrayList;

public class DisplayItineraryActivity extends AppCompatActivity {
  private Application app;
  private String email;
  public static boolean[] is_checked;
  ArrayList<String> itineraries = new ArrayList<>();
  private String[] title;
  private String[] itinerary;
  private int numRow;
  private ListView listView;
  private String origin;
  private String destination;
  private String date;
  private boolean isDirect;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_itinerary);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // go back button
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
    email = (String) intent.getSerializableExtra(LogInActivity.USER_KEY);
    origin = (String) intent.getSerializableExtra(SearchActivity.ORIGIN_KEY);
    destination = (String) intent.getSerializableExtra(SearchActivity.DESTINATION_KEY);
    date = (String) intent.getSerializableExtra(SearchActivity.DATE_KEY);
    isDirect = (boolean) intent.getSerializableExtra(SearchActivity.DIRECT_KEY);
    itineraries = app.displayItinerariesByCost();
    numRow = itineraries.size();
    // array to keep track of each row item in the listView
    is_checked = new boolean[numRow];
    listView = (ListView) findViewById(R.id.listView2);

    title = new String[numRow];
    itinerary = new String[numRow];
    costTitle(numRow);
    // customise the list
    CustomListAdapter adapter = new CustomListAdapter(this, title, itinerary);
    listView.setAdapter(adapter);
    RadioButton costRadio = (RadioButton) findViewById(R.id.cost_radio);
    costRadio.setChecked(true);
  }

  /**
   * make the Title stores cost.
   * @param numRow number of rows
   */
  private void costTitle(int numRow) {
    for (int i = 0; i < numRow; i++) {
      itinerary[i] = itineraries.get(i);
      String[] info = itineraries.get(i).split("\n");
      title[i] = info[info.length - 2];
    }
  }

  /**
   * make the Title stores time.
   * @param numRow number of rows
   */
  private void timeTitle(int numRow) {
    for (int i = 0; i < numRow; i++) {
      itinerary[i] = itineraries.get(i);
      String[] info = itineraries.get(i).split("\n");
      title[i] = info[info.length - 1];
    }
  }

  /**
   * refresh the list sorted by cost.
   * @param view view
   */
  public void displayByCost(View view) {
    RadioButton timeRadio = (RadioButton) findViewById(R.id.time_radio);
    timeRadio.setChecked(false);
    itineraries = app.displayItinerariesByCost();
    costTitle(numRow);
    CustomListAdapter adapter = new CustomListAdapter(this, title, itinerary);
    listView.setAdapter(adapter);
  }

  /**
   * refresh the list sorted by cost.
   * @param view view
   */
  public void displayByTime(View view) {
    RadioButton costRadio = (RadioButton) findViewById(R.id.cost_radio);
    costRadio.setChecked(false);
    itineraries = app.displayItinerariesByTime();
    timeTitle(numRow);
    CustomListAdapter adapter = new CustomListAdapter(this, title, itinerary);
    listView.setAdapter(adapter);
  }

  /**
   * book all selected itineraries, add them into the client's booked list
   * update the seats of the flights and research the flight or itineraries and display it
   * update the changes to clients and flights into the file.
   * refresh the list sorted by cost.
   * @param view view
   */
  public void bookItinerary(View view) {
    // book itineraries according to which are selected
    for (int i = 0; i < is_checked.length; i++) {
      if (is_checked[i]) {
        app.bookItinerary(email, app.getItinerary(i));
      }
    }
    // research the flights
    if (isDirect) {
      app.searchDirectItinerary(date, origin, destination);
    } else {
      app.searchItinerary(date, origin, destination);
    }
    // refresh the list view and display the flights
    RadioButton costRadio = (RadioButton) findViewById(R.id.cost_radio);
    costRadio.setChecked(true);
    RadioButton timeRadio = (RadioButton) findViewById(R.id.time_radio);
    timeRadio.setChecked(false);
    itineraries = app.displayItinerariesByCost();
    numRow = itineraries.size();
    is_checked = new boolean[numRow];
    title = new String[numRow];
    itinerary = new String[numRow];
    costTitle(numRow);
    // re-customise the list
    CustomListAdapter adapter = new CustomListAdapter(this, title, itinerary);
    listView.setAdapter(adapter);
    // update the changes into the files
    File filePath = this.getApplicationContext().getFilesDir();
    File clientFile = new File(filePath, CLIENT_FILENAME);
    app.saveClient(clientFile);
    File flightFile = new File(filePath, FLIGHT_FILENAME);
    app.saveFlight(flightFile);
    Toast.makeText(DisplayItineraryActivity.this, "Booked", Toast.LENGTH_LONG).show();

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        this.onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}