package p8project.sw801.ui.event.locationpicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.databinding.FragmentLocationPickerBinding;
import p8project.sw801.ui.Settings.Location.AddLocation.AddLocationSettingActivity;
import p8project.sw801.ui.base.BaseFragment;
import p8project.sw801.ui.custom.FragmentCallback;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;

public class LocationPicker extends BaseFragment<FragmentLocationPickerBinding, LocationPickerViewModel> implements LocationPickerNavigator {
    public static final String TAG = LocationPicker.class.getSimpleName();
    @Inject
    LocationPickerViewModel mLocationPickerViewModel;

    public static LocationPicker newInstance() {
        Bundle args = new Bundle();

        LocationPicker fragment = new LocationPicker();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_location_picker;
    }

    @Override
    public LocationPickerViewModel getViewModel() {
        return mLocationPickerViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationPickerViewModel.setNavigator(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void createNewPredefinedLocation() {
        Intent intent = new Intent(getActivity(), AddLocationSettingActivity.class);
        getActivity().startActivityForResult(intent, 2);
    }

    @Override
    public void returnPredefinedLocation(PredefinedLocation predefinedLocation) {
        if (getActivity() instanceof FragmentCallback) {
            ((FragmentCallback) getActivity()).fragmentCallback(
                    1,
                    new Gson().toJson(predefinedLocation)
            );
        }
    }

    @Override
    public void createFromMap() {
        Intent intent = CreateEventMap.newIntent(getContext());
        getActivity().startActivityForResult(intent, 0);
    }
}
