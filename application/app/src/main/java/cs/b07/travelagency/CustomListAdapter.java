package cs.b07.travelagency;

import static cs.b07.travelagency.R.layout.list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;



public class CustomListAdapter extends ArrayAdapter<String> {

  private final Activity context;
  private final String[] title;
  private final String[] itinerary;

  /**
   * contractor.
   * @param context context
   * @param title title
   * @param itinerary itinerary
   */
  public CustomListAdapter(Activity context, String[] title, String[] itinerary) {
    super(context, list, title);

    this.context = context;
    this.title = title;
    this.itinerary = itinerary;
  }

  /**
   * set every row view in the list View.
   * @param position position
   * @param view view
   * @param parent parent
   * @return view
   */
  public View getView(final int position, View view, ViewGroup parent) {
    LayoutInflater inflater = context.getLayoutInflater();
    @SuppressLint("ViewHolder") View rowView = inflater.inflate(list, null, true);

    TextView titleView = (TextView) rowView.findViewById(R.id.titleText);
    final TextView itineraryView = (TextView) rowView.findViewById(R.id.itineraryText);
    final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);

    //add checkbox listener
    checkBox.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        DisplayItineraryActivity.is_checked[position] = checkBox.isChecked();
      }
    });

    titleView.setText(title[position]);
    titleView.setBackgroundColor(Color.parseColor("#11152e"));
    itineraryView.setText(itinerary[position]);

    return rowView;
  }
}