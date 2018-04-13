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
import p8project.sw801.ui.event.editevent.EditEvent;

/**
 * Created by cheec on 16-03-2018.
 */

public class MyEventAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> Title;


    public MyEventAdapter(Context context, ArrayList<String> text1) {
        mContext = context;
        Title = text1;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Title.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row;
        row = inflater.inflate(R.layout.myeventslistlayout, parent, false);
        TextView title;
        title = (TextView) row.findViewById(R.id.textViewmyeventlist);
        title.setText(Title.get(position));
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
                Toast.makeText(mContext, Title.get(position) +" has been deleted from the list.", Toast.LENGTH_SHORT).show();
                Title.remove(position);
                notifyDataSetChanged();

            }
        });


        eventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked==true)
                {
                    Toast.makeText(mContext, Title.get(position) +" has been turned on", Toast.LENGTH_SHORT).show();
                }
                if(isChecked==false)
                {
                    Toast.makeText(mContext, Title.get(position) +" has been turned off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return (row);
    }

}
