package p8project.sw801.ui.custom.TimePicker;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import p8project.sw801.R;

public class TimePicker extends LinearLayout {
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private int currentTime = 0;

    private EditText mEditText;

    public TimePicker(Context context) {
        super(context);
        initializeViews(context);
    }

    public TimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public TimePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.timepicker, this);
    }

    private void setDefaultTime() {
        if (getCurrentTime() != 0) {
            Date startDate = new Date(getCurrentTime());

            setDefaultTime(startDate);
            return;
        }
        setDefaultTime(getCalender().get(Calendar.HOUR_OF_DAY), getCalender().get(Calendar.MINUTE));
    }

    private void setDefaultTime(Integer hourOfDay, Integer minute) {
        try {
            Date startDate = timeFormat.parse(
                    String.format("%d:%02d", hourOfDay, minute)
            );

            setDefaultTime(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("DefaultLocale")
    private void setDefaultTime(Date test) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(test);

        mEditText.setText(String.format("%d:%02d",
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE)
        ));

        setCurrentTime((int) test.getTime());
    }

    protected Calendar getCalender() {
        return Calendar.getInstance();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mEditText = (EditText) this.findViewById(R.id.timePickerElement);
        mEditText.setOnClickListener(v -> {
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(getContext(), (view, hourOfDay, minute1) -> {
                setDefaultTime(hourOfDay, minute1);
            },
                    getCalender().get(Calendar.HOUR_OF_DAY),
                    getCalender().get(Calendar.MINUTE),
                    false
            );

            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        setDefaultTime();
    }

    public void setCurrentTime(int time) {
        this.currentTime = time;
    }

    public int getCurrentTime() {
        return this.currentTime;
    }
}