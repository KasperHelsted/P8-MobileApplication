package p8project.sw801.ui.Settings.Location;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import p8project.sw801.R;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.Settings.Location.EditLocation.EditLocationSettingActivity;

/**
 * Created by clubd on 20-03-2018.
 */

public class  LocationSettingAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PredefinedLocation> Title;
    private LocationSettingActivity _locationSettingActivity;


    public LocationSettingAdapter(Context context, ArrayList<PredefinedLocation> text1,LocationSettingActivity locationSettingActivity) {
        mContext = context;
        Title = text1;
        _locationSettingActivity = locationSettingActivity;

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
        row = inflater.inflate(R.layout.locationsettinglistlayout, parent, false);
        TextView title;
        title = (TextView) row.findViewById(R.id.row_textview);
        title.setText(Title.get(position).getName());

        //Click event for delete buttons
        ImageView delete = row.findViewById(R.id.imageView_locationsettingdelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PredefinedLocation predefinedLocation = Title.get(position);
                _locationSettingActivity.mLocationViewModel.removePredefinedLocation(predefinedLocation);
                Title.remove(position);
                notifyDataSetChanged();
            }
        });

        //Click event for edit buttons
        ImageView edit = row.findViewById(R.id.imageView_locationsettingedit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, Title.get(position) +" has been changed.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, EditLocationSettingActivity.class);
                //intent.putExtra(locationSettingName, Title.get(position));
                mContext.startActivity(intent);
            }
        });



        return (row);
    }
}