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
    private int currentTime;

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

        Calendar cal = Calendar.getInstance();
        currentTime = calToInt(cal);
    }

    private int calToInt(Calendar cal) {
        try {
            Date date = timeFormat.parse(
                    String.format("%d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
            );

            return (int) date.getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEditText = this.findViewById(R.id.timePickerElement);
        mEditText.setOnClickListener(v -> {
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(getContext(), (view, hourOfDay, minute1) -> {
                setTime(hourOfDay, minute1);
            },
                    getCalender().get(Calendar.HOUR_OF_DAY),
                    getCalender().get(Calendar.MINUTE),
                    false
            );

            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        updateTextOnField();
    }

    private void updateTextOnField() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(getCurrentTime()));

        mEditText.setText(
                String.format("%d:%02d",
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE)
                )
        );
    }

    @SuppressLint("DefaultLocale")
    private void setTime(Integer hourOfDay, Integer minute) {
        try {
            Date startDate = timeFormat.parse(
                    String.format("%d:%02d", hourOfDay, minute)
            );

            setCurrentTime((int) startDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected Calendar getCalender() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(currentTime));

        return cal;
    }

    public void setCurrentTime(int time) {
        if (time == 0)
            return;

        this.currentTime = time;
        updateTextOnField();

        if (mListener != null)
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