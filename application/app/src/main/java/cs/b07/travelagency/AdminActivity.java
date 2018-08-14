package cs.b07.travelagency;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import src.application.Application;

import java.util.ArrayList;



public class AdminActivity extends AppCompatActivity {
  private Application app;

  private String email;
  ListView lv;
  ArrayAdapter<String> listAdapter;
  ArrayList<String> list = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
    email = (String) intent.getSerializableExtra(LogInActivity.USER_KEY);
    lv = (ListView) findViewById(R.id.adminListView);
    listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
    lv.setAdapter(listAdapter);
    list.add("Search Client");
    list.add("Edit Flight");
    list.add("Upload Flights");
    list.add("Upload Clients");
    list.add("Log Out");


    lv.setOnItemClickListener(new ListView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
          viewClientInfo();
        } else if (position == 1) {
          editFlightInfo();
        } else if (position == 2) {
          uploadFlight();
        } else if (position == 3) {
          uploadClient();
        } else if (position == 4) {
          onBackPressed();
        }
      }
    });
  }

  /**
   * starts a new activity to search a client.
   */
  public void viewClientInfo() {
    Intent intent = new Intent(this, ViewClientActivity.class);
    intent.putExtra(LogInActivity.APP_KEY, app);
    intent.putExtra(LogInActivity.USER_KEY, email);
    startActivity(intent);
  }

  /**
   * starts a new activity to edit flight information.
   */
  public void editFlightInfo() {
    Intent intent = new Intent(this, EditFlightActivity.class);
    intent.putExtra(LogInActivity.APP_KEY, app);
    startActivity(intent);
  }

  /**
   * starts a new activity to upload flight.
   */
  public void uploadFlight() {
    Intent intent = new Intent(this, UploadFlightsActivity.class);
    intent.putExtra(LogInActivity.APP_KEY, app);
    startActivity(intent);
  }

  /**
   * starts a new activity to upload client.
   */
  public void uploadClient() {
    Intent intent = new Intent(this, UploadClientsActivity.class);
    intent.putExtra(LogInActivity.APP_KEY, app);
    startActivity(intent);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
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
}
