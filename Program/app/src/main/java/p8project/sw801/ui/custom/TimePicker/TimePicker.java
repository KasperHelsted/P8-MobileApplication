package p8project.sw801.ui.custom.TimePicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import p8project.sw801.R;

public class TimePicker extends LinearLayout {
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}