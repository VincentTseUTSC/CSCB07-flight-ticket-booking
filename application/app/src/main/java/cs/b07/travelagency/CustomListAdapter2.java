package cs.b07.travelagency;

import static cs.b07.travelagency.R.layout.list2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CustomListAdapter2 extends ArrayAdapter<String> {

  private final Activity context;
  private final String[] itinerary;

  /**
   * contractor, the class that customise the list.
   * @param context context
   * @param itinerary itinerary
   */
  public CustomListAdapter2(Activity context, String[] itinerary) {
    super(context, list2, itinerary);

    this.context = context;
    this.itinerary = itinerary;
  }

  @Override
  public View getView(final int position, View view, ViewGroup parent) {
    LayoutInflater inflater = context.getLayoutInflater();
    @SuppressLint("ViewHolder") View rowView = inflater.inflate(list2, null, true);

    TextView itineraryView = (TextView) rowView.findViewById(R.id.itineraryText2);
    final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox2);

    //add checkbox listener
    checkBox.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        ViewBookedActivity.is_checked[position] = checkBox.isChecked();
      }
    });
    itineraryView.setText(itinerary[position]);
    return rowView;
  }
}
