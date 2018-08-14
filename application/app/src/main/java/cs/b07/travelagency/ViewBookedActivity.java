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
import android.widget.Toast;

import src.application.Application;
import src.flight.Itinerary;

import java.io.File;

import java.util.ArrayList;


public class ViewBookedActivity extends AppCompatActivity {
  private Application app;
  private String email;
  private ArrayList<Itinerary> bookedItineraries;
  private String[] itinerary;
  private int numRow;
  private ListView listView;
  public static boolean[] is_checked;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_booked);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // go back button
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    listView = (ListView) findViewById(R.id.booked_list);
    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
    email = (String) intent.getSerializableExtra(LogInActivity.USER_KEY);
    bookedItineraries = app.getClient(email).getBookedItinerary();
    numRow = bookedItineraries.size();
    // array to keep track of each row item in the listView
    itinerary = new String[numRow];
    is_checked = new boolean[numRow];
    // get the information
    for (int i = 0; i < numRow; i++) {
      itinerary[i] = bookedItineraries.get(i).toString();
    }
    // customise the list
    CustomListAdapter2 adapter = new CustomListAdapter2(this, itinerary);
    listView.setAdapter(adapter);

  }

  /**
   * un-book itineraries from the client's storage and update into the file.
   * refresh the list.
   * @param view view
   */
  public void unBookItinerary(View view) {
    // un-book itinerary according to which check box is checked
    boolean unbooked = false;
    for (int i = 0; i < is_checked.length; i++) {
      if (is_checked[i]) {
        app.unBookItinerary(email, i);
        unbooked = true;
      }
    }
    bookedItineraries = app.getClient(email).getBookedItinerary();
    numRow = bookedItineraries.size();
    is_checked = new boolean[numRow];
    // get the information again to refresh the list
    itinerary = new String[numRow];
    for (int i = 0; i < numRow; i++) {
      itinerary[i] = bookedItineraries.get(i).toString();
    }
    // refresh the list, customise it again
    CustomListAdapter2 adapter = new CustomListAdapter2(this, itinerary);
    listView.setAdapter(adapter);
    // update both into client and flight file for the change
    File filePath = this.getApplicationContext().getFilesDir();
    File clientFile = new File(filePath, CLIENT_FILENAME);
    app.saveClient(clientFile);
    File flightFile = new File(filePath, FLIGHT_FILENAME);
    app.saveFlight(flightFile);
    // if successfully un-booked, toast a message
    if (unbooked) {
      Toast.makeText(ViewBookedActivity.this, "UnBooked", Toast.LENGTH_LONG).show();
    }
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



