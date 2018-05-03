package p8project.sw801.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.ui.event.editevent.triggersList.TriggerListAdapter;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceAdapter;

public final class BindingUtils {
    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addSmartDeviceItems(RecyclerView recyclerView, List<SmartDevice> smartDevices) {
        MySmartDeviceAdapter adapter = (MySmartDeviceAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(smartDevices);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addTriggerItems(RecyclerView recyclerView, List<Trigger> triggers) {
        TriggerListAdapter adapter = (TriggerListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(triggers);
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, Integer drawable) {
        Context context = imageView.getContext();

        Drawable res = ResourcesCompat.getDrawable(context.getResources(), drawable, null);

        imageView.setImageDrawable(res);
    }
}
