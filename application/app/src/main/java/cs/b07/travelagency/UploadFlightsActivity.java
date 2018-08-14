package cs.b07.travelagency;

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


public class UploadFlightsActivity extends AppCompatActivity {
  private Application app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload_flights);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // go back button
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);
    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
  }

  /**
   * read the file with given path and upload flights into the system.
   * @param view view
   */
  public void upload(View view) {
    // gets the input
    EditText uploadEditText = (EditText) findViewById(R.id.upload_flight);
    // gets the path
    File filePath = this.getApplicationContext().getFilesDir();
    String fileName = uploadEditText.getText().toString();
    File uploadFlight = new File(filePath, fileName);
    // read and upload flights
    app.uploadFlightInfo(uploadFlight);
    File flightFile = new File(filePath, FLIGHT_FILENAME);
    app.saveFlight(flightFile);
    Toast.makeText(UploadFlightsActivity.this, "Upload Made", Toast.LENGTH_LONG).show();
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

