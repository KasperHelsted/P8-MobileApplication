package p8project.sw801.ui.AddEvent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import p8project.sw801.R;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.ui.event.addevent.AddEvent;


/**
 * Created by cheec on 21-03-2018.
 */

public class AddEventAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Trigger> Title;


    public AddEventAdapter(Context context, ArrayList<Trigger> text1) {
        mContext = context;
        Title = text1;
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

        if (!Title.get(position).getNotification()) {
            row = inflater.inflate(R.layout.addeventlistlayout, parent, false);
        } else {
            row = inflater.inflate(R.layout.activity_add_event_list_smartdevices_layout, parent, false);
        }

        TextView title;
        title = (TextView) row.findViewById(R.id.addEventTrigger);

        title.setText(Title.get(position).getNotificationText());

        TextView t = row.findViewById(R.id.addEventTrigger);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddEvent) mContext).deleteNotificationOrTrigger(position);
            }
        });
        return (row);
    }

}
