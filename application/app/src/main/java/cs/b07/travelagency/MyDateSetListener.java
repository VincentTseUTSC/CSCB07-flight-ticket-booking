package cs.b07.travelagency;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class MyDateSetListener implements DatePickerDialog.OnDateSetListener {
  @Override
  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    // getCalender();
    String myMonth = "" + (monthOfYear + 1);
    String myDay = "" + dayOfMonth;
    if (myMonth.length() == 1) {
      myMonth = "0" + myMonth;
    }
    if (myDay.length() == 1) {
      myDay = "0" + myDay;
    }
    SearchActivity.dateEditText.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(year).append("-").append(myMonth).append("-").append(myDay)
    );
  }
}
