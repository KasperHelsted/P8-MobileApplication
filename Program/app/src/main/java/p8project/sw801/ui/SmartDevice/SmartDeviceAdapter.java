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

import com.google.gson.Gson;

import java.util.List;

import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.ui.SmartDevice.EditSmartDevice.EditSmartDeviceActivity;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceFragment;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

public class SmartDeviceAdapter extends BaseAdapter {
    private Context mContext;
    private List<SmartDevice> smartDevices;
    private MySmartDeviceFragment mySmartDeviceFragment;


    public SmartDeviceAdapter(Context context, List<SmartDevice> smartDevices_, MySmartDeviceFragment f) {
        mContext = context;
        smartDevices = smartDevices_;
        mySmartDeviceFragment = f;
    }

    public int getCount() {
        return smartDevices.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View row = inflater.inflate(R.layout.mysmartdeviceslistlayout, parent, false);
        final TextView title = (TextView) row.findViewById(R.id.row_textview);

        final SmartDevice smartDevice = smartDevices.get(position);

        title.setText(smartDevice.getDeviceName());

        //Click event for delete buttons
        ImageView delete = row.findViewById(R.id.imageView_mysmartdevicedelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mySmartDeviceFragment.deleteSmartDevice(smartDevice);
            }
        });

        //Click event for edit buttons
        ImageView edit = row.findViewById(R.id.imageView_mysmartdeviceedit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, EditSmartDeviceActivity.class);
                intent.putExtra("device", new Gson().toJson(smartDevice));
                mySmartDeviceFragment.startActivityForResult(intent, 1);
            }
        });

        return (row);

    }
}