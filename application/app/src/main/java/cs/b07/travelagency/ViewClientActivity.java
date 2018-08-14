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
import android.widget.TextView;

import src.application.Application;

import java.io.File;

public class ViewClientActivity extends AppCompatActivity {
  private Application app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_client);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // go back button
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
    File filePath = this.getApplicationContext().getFilesDir();
    File clientFile = new File(filePath, CLIENT_FILENAME);
    File flightFile = new File(filePath, FLIGHT_FILENAME);
    app.uploadFlightInfo(flightFile);
    app.uploadClientInfo(clientFile);


  }

  /**
   * search the Client with given email.
   * @param view view
   */
  public void searchClient(View view) {
    // gets the input
    EditText clientEditText = (EditText) findViewById(R.id.client_field);
    TextView clientTextView = (TextView) findViewById(R.id.display_client);
    String email = clientEditText.getText().toString();
    if (email.isEmpty()) {
      clientTextView.setText(R.string.enter_email);
    } else {
      if (app.getClient(email) == null) {
        clientTextView.setText(R.string.client_not_found);
      } else {
        // starts a new activity, which is the same page as when a client is logged in
        Intent intent = new Intent(this, ClientActivity.class);
        intent.putExtra(LogInActivity.USER_KEY, email);
        intent.putExtra(LogInActivity.APP_KEY, app);
        intent.putExtra(LogInActivity.ADMIN_KEY, true);
        startActivity(intent);
      }
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

  @Override
  public void onResume() {  // After a pause OR at startup
    super.onResume();
    //Refresh your stuff here
    EditText clientEditText = (EditText) findViewById(R.id.client_field);
    clientEditText.setText("");
  }
}
