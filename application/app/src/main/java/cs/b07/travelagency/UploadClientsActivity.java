package cs.b07.travelagency;

import static src.application.Constants.CLIENT_FILENAME;

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


public class UploadClientsActivity extends AppCompatActivity {
  private Application app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload_clients);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    // go back button
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    app = (Application) intent.getSerializableExtra(LogInActivity.APP_KEY);
  }

  /**
   * rend file with given path, and upload clients into the system.
   * @param view view
   */
  public void upload(View view) {
    // gets the input
    EditText uploadEditText = (EditText) findViewById(R.id.upload_client);
    // gets the path
    File filePath = this.getApplicationContext().getFilesDir();
    String fileName = uploadEditText.getText().toString();
    File uploadClient = new File(filePath, fileName);
    // read and upload
    app.uploadClientInfo(uploadClient);
    File clientFile = new File(filePath, CLIENT_FILENAME);
    app.saveClient(clientFile);
    Toast.makeText(UploadClientsActivity.this, "Upload Made", Toast.LENGTH_LONG).show();
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
