package p8project.sw801.ui.event.addeventsmartdevice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;

public class customSDAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<SmartDevice> Title;

    public customSDAdapter(Context context, ArrayList<SmartDevice> text1) {
        mContext = context;
        Title = text1;
    }

    @Override
    public int getCount() {
        return Title.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row;
        row = inflater.inflate(R.layout.addeventsmartdevicelist, parent, false);
        TextView t = row.findViewById(R.id.textview_addEventSmartDeviceList);
        t.setText(Title.get(position).getDeviceName());
        return (row);
    }
}