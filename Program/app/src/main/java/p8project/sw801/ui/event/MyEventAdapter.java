package p8project.sw801.ui.event;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import p8project.sw801.R;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.ui.event.editevent.EditEvent;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragment;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragmentViewModel;

/**
 * Created by cheec on 16-03-2018.
 */

public class MyEventAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Event> Title;
    private MyEventsFragment myEventsFragment;


    public MyEventAdapter(Context context, ArrayList<Event> text1, MyEventsFragment m) {
        mContext = context;
        Title = text1;
        myEventsFragment = m;
    }

    public int getCount() {
        return Title.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row;
        row = inflater.inflate(R.layout.myeventslistlayout, parent, false);
        TextView title;
        title = (TextView) row.findViewById(R.id.textViewmyeventlist);
        title.setText(Title.get(position).getName());
        ImageView edit = row.findViewById(R.id.addEventAddCondition);
        ImageView delete = row.findViewById(R.id.MyEventDelete);
        Switch eventSwitch = row.findViewById(R.id.eventSwitch);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, Title.get(position) +" has been renamed.", Toast.LENGTH_SHORT).show();
                Intent editEvent = new Intent(mContext,EditEvent.class);
                mContext.startActivity(editEvent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEventsFragment.deleteEvent(Title.get(position));
            }
        });


        eventSwitch.setChecked(Title.get(position).getActive());


        eventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked==true)
                {
                    myEventsFragment.updateEvent(Title.get(position), isChecked);
                }
                if(isChecked==false)
                {
                    myEventsFragment.updateEvent(Title.get(position), isChecked);
                }
            }
        });

        return (row);
    }

}
