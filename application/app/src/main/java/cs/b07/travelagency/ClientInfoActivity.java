package cs.b07.travelagency;

import static src.application.Constants.CLIENT_FILENAME;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import src.application.Application;

import java.io.File;

public class ClientInfoActivity extends AppCompatActivity {
  private String email;
  private Application app;
  private EditText lastNameEditText;
  private EditText firstNameEditText;
  private EditText emailEditText;
  private EditText addressEditText;
  private EditText cardNumEditText;
  private EditText expiryDateEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_info);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    email = (String) intent.getSerializableExtra(LogInActivity.USER_KEY);
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
    String[] clientInfo = app.getClient(email).toString().split(",");
    // set the text on the text views
    lastNameEditText = (EditText) findViewById(R.id.last_name_field);
    lastNameEditText.setText(clientInfo[0]);
    firstNameEditText = (EditText) findViewById(R.id.first_name_field);
    firstNameEditText.setText(clientInfo[1]);
    emailEditText = (EditText) findViewById(R.id.email_field);
    emailEditText.setText(clientInfo[2]);
    addressEditText = (EditText) findViewById(R.id.address_field);
    addressEditText.setText(clientInfo[3]);
    cardNumEditText = (EditText) findViewById(R.id.card_number_field);
    cardNumEditText.setText(clientInfo[4]);
    expiryDateEditText = (EditText) findViewById(R.id.expiry_date_field);
    expiryDateEditText.setText(clientInfo[5]);
  }

  /**
   * edit client information and update in the file.
   * @param view view
   */
  public void editClient(View view) {
    if (hasFilledAll()) {
      // set the information.
      app.getClient(email).setLastName(lastNameEditText.getText().toString());
      app.getClient(email).setFirstName(firstNameEditText.getText().toString());
      app.getClient(email).setEmail(emailEditText.getText().toString());
      app.getClient(email).setAddress(addressEditText.getText().toString());
      app.getClient(email).setCreditCardNumber(cardNumEditText.getText().toString());
      app.getClient(email).setExpiryDate(expiryDateEditText.getText().toString());
      File filePath = this.getApplicationContext().getFilesDir();
      File clientFile = new File(filePath, CLIENT_FILENAME);
      // save it to file
      app.saveClient(clientFile);
      onBackPressed();
      Toast.makeText(ClientInfoActivity.this, "Change Made", Toast.LENGTH_LONG).show();
    } else {
      // edit text is not all filled.
      Toast.makeText(ClientInfoActivity.this, "Please fill out", Toast.LENGTH_LONG).show();
    }
  }

  /**
   * check if every edit text is filled.
   * @return true if every edit text is filled.
   */
  private boolean hasFilledAll() {
    if (lastNameEditText.getText().toString().isEmpty()) {
      return false;
    } else if (firstNameEditText.getText().toString().isEmpty()) {
      return false;
    } else if (emailEditText.getText().toString().isEmpty()) {
      return false;
    } else if (addressEditText.getText().toString().isEmpty()) {
      return false;
    } else if (cardNumEditText.getText().toString().isEmpty()) {
      return false;
    } else if (emailEditText.getText().toString().isEmpty()) {
      return false;
    }
    return true;
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
