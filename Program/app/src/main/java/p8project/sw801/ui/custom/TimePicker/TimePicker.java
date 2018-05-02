package p8project.sw801.ui.custom.TimePicker;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import p8project.sw801.R;

@InverseBindingMethods({
        @InverseBindingMethod(
                type = TimePicker.class,
                attribute = "currentTime",
                event = "android:textAttrChanged",
                method = "getCurrentTime"
        ),
})
public class TimePicker extends LinearLayout {
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private int currentTime = -1;

    private EditText mEditText;

    static InverseBindingListener mListener;


    public TimePicker(Context context) {
        super(context);
        initializeViews(context);
    }

    public TimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        inflater.inflate(R.layout.timepicker, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEditText = this.findViewById(R.id.timePickerElement);
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

    private void setDefaultTime() {
        setDefaultTime(new Date(getCurrentTime()));
    }

    @SuppressLint("DefaultLocale")
    private void setDefaultTime(Integer hourOfDay, Integer minute) {
        try {
            Date startDate = timeFormat.parse(
                    String.format("%d:%02d", hourOfDay, minute)
            );

            setCurrentTime((int) startDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("DefaultLocale")
    private void setDefaultTime(Date test) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(test);

        mEditText.setText(
                String.format("%d:%02d",
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE)
                )
        );
    }

    protected Calendar getCalender() {
        Calendar cal = Calendar.getInstance();

        if (getCurrentTime() != -1)
            cal.setTime(new Date(currentTime));

        return cal;
    }

    public void setCurrentTime(int time) {
        this.currentTime = time;
        setDefaultTime();

        mListener.onChange();
    }

    public int getCurrentTime() {
        return this.currentTime;
    }

    @BindingAdapter("android:textAttrChanged")
    public static void setTextWatcher(View view, final InverseBindingListener textAttrChanged) {
        mListener = textAttrChanged;
    }
}