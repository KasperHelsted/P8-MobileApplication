package p8project.sw801.ui.Settings.GlobalMuteSetting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import p8project.sw801.R;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.ui.Settings.EditGlobalMuteSetting.EditGlobalMuteSettingActivity;

/**
 * Created by clubd on 20-03-2018.
 */

public class GlobalMuteSettingAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<GlobalMute> globalMuteArrayList;
    private Integer globalSettingName;


    public GlobalMuteSettingAdapter(Context context, ArrayList<GlobalMute> globalMutes) {
        this.mContext = context;
        globalMuteArrayList = globalMutes;
    }

    public int getCount() {
        return globalMuteArrayList.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    private String timeConverter(long input) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(input));

        return String.format("%d:%02d",
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE)
        );
    }

    private String stringFormatting(String name, String timeFormattedString, int spaces) {
        String finalString = name;
        while (finalString.length() != spaces) {
            finalString = finalString + " ";
        }
        finalString = finalString + timeFormattedString;
        return finalString;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row;
        row = inflater.inflate(R.layout.globalmutelistlayout, parent, false);
        final TextView title;
        final TextView time;
        String formattedString = String.format(timeConverter(globalMuteArrayList.get(position).getStartTime()) + " - " + timeConverter(globalMuteArrayList.get(position).getEndTime()));
        title = (TextView) row.findViewById(R.id.row_textview);
        time = row.findViewById(R.id.row_time);
        String name = globalMuteArrayList.get(position).getName();
        title.setText(name);
        //stringFormatting(name,formattedString,50)
        time.setText(formattedString);

        //Click event for delete buttons
        ImageView delete = row.findViewById(R.id.imageView_globalmutedelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GlobalMuteSettingActivity) mContext).deleteGlobalMute(globalMuteArrayList.get(position));
            }
        });

        //Click event for edit buttons
        ImageView edit = row.findViewById(R.id.imageView_globalmuteedit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditGlobalMuteSettingActivity.class);
                intent.putExtra("id", globalMuteArrayList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        return (row);
    }
}
