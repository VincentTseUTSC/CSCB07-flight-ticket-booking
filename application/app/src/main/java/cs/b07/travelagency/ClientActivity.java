package cs.b07.travelagency;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import src.application.Application;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity {
  private Application app;
  private String email;
  ListView lv;
  ArrayAdapter<String> listAdapter;
  ArrayList<String> list = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);
    // gets the intents
    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
    email = (String) intent.getSerializableExtra(LogInActivity.USER_KEY);
    final boolean isAdmin = (boolean) intent.getSerializableExtra(LogInActivity.ADMIN_KEY);
    // initialize the list
    lv = (ListView) findViewById(R.id.listView);
    listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
    lv.setAdapter(listAdapter);
    list.add("Search Flights");
    list.add("View Profile");
    list.add("View Booked Flights");
    list.add("Log Out");
    // if this activity is started by an admin, there is no log out
    if (isAdmin) {
      list.remove("Log Out");
    }

    lv.setOnItemClickListener(new ListView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
          search();
        } else if (position == 1) {
          viewClientInfo();
        } else if (position == 2) {
          viewBookedItineraries();
        } else if (position == 3) {
          onBackPressed();
        }
      }
    });
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
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  /**
   * starts a new activity to view and edit client's information.
   */
  public void viewClientInfo() {
    Intent intent = new Intent(this, ClientInfoActivity.class);
    intent.putExtra(LogInActivity.APP_KEY, app);
    intent.putExtra(LogInActivity.USER_KEY, email);
    startActivity(intent);
  }

  /**
   * starts a new activity to search flights or itineraries.
   */
  public void search() {
    Intent intent = new Intent(this, SearchActivity.class);
    intent.putExtra(LogInActivity.APP_KEY, app);
    intent.putExtra(LogInActivity.USER_KEY, email);
    startActivity(intent);
  }

  /**
   * starts a new activity to view or un book booked itineraries.
   */
  public void viewBookedItineraries() {
    Intent intent = new Intent(this, ViewBookedActivity.class);
    intent.putExtra(LogInActivity.APP_KEY, app);
    intent.putExtra(LogInActivity.USER_KEY, email);
    startActivity(intent);
  }
}
