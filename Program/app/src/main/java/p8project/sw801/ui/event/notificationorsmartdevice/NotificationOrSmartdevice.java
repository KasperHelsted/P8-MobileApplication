package p8project.sw801.ui.event.notificationorsmartdevice;

import android.os.Bundle;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.FragmentNotificationOrSmartdeviceBinding;
import p8project.sw801.ui.base.BaseFragment;

public class NotificationOrSmartdevice extends BaseFragment<FragmentNotificationOrSmartdeviceBinding, NotificationOrSmartdeviceViewModel> implements NotificationOrSmartdeviceNavigator {

    public static final String TAG = NotificationOrSmartdevice.class.getSimpleName();
    NotificationOrSmartdeviceViewModel mNotificationOrSmartdeviceViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationOrSmartdeviceViewModel.setNavigator(this);
    }

    public static NotificationOrSmartdevice newInstance() {
        Bundle args = new Bundle();
        NotificationOrSmartdevice fragment = new NotificationOrSmartdevice();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notification_or_smartdevice;
    }

    @Override
    public NotificationOrSmartdeviceViewModel getViewModel() {
        return mNotificationOrSmartdeviceViewModel;
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification_or_smartdevice, container, false);
        return v;
    }
    */

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }
}
