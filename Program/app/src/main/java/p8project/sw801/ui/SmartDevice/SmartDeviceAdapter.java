package p8project.sw801.ui.SmartDevice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import p8project.sw801.data.model.Others.UserPreference;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.R;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

public class SmartDeviceAdapter extends BaseAdapter {
    private Context mContext;
    private List<SmartDevice> smartDevices;
    private String deviceName;


    public SmartDeviceAdapter(Context context, List<SmartDevice> smartDevices_) {
        mContext = context;
        smartDevices = smartDevices_;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return smartDevices.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View row = inflater.inflate(R.layout.mysmartdeviceslistlayout, parent, false);
        final TextView title = (TextView) row.findViewById(R.id.row_textview);

        final SmartDevice smartDevice = smartDevices.get(position);

        title.setText(smartDevice.toString());

        //Click event for delete buttons
        ImageView delete = row.findViewById(R.id.imageView_mysmartdevicedelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreference.removeFromSmartDeviceList(
                        mContext,
                        smartDevice
                );
                notifyDataSetChanged();
            }
        });

        //Click event for edit buttons
        ImageView edit = row.findViewById(R.id.imageView_mysmartdeviceedit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditSmartDeviceActivity.class);
                intent.putExtra(
                        "device_id",
                        smartDevice.getId()
                );

                mContext.startActivity(intent);
            }
        });

        return (row);
    }
}