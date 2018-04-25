package p8project.sw801.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceAdapter;

public final class BindingUtils {
    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addBlogItems(RecyclerView recyclerView, List<SmartDevice> smartDevices) {
        MySmartDeviceAdapter adapter = (MySmartDeviceAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(smartDevices);
        }
    }

}
