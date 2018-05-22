package p8project.sw801.ui.main.Fragments.HomeFragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.FragmentHomeBinding;
import p8project.sw801.ui.Settings.SettingsActivity;
import p8project.sw801.ui.base.BaseFragment;
import p8project.sw801.ui.event.addevent.AddEvent;
import p8project.sw801.ui.main.MainActivity;


public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFragmentViewModel> implements HomeFragmentNavigator {
    public static final String TAG = HomeFragment.class.getSimpleName();
    @Inject
    HomeFragmentViewModel mHomeFragmentViewModel;
    private FragmentHomeBinding mFragmentHomeBinding;

    /**
     * Constructor for the fragment.
     *
     * @return
     */
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * On create view method for HomeFragment. Instantiates and sets up all required fields for the page.
     *
     * @param inflater           The infater used from the activity to inflate this fragment.
     * @param container          The container containing this fragment.
     * @param savedInstanceState The saved instance state if there is one.
     * @return The inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mFragmentHomeBinding = getViewDataBinding();
        mHomeFragmentViewModel.setNavigator(this);

        View view = mFragmentHomeBinding.getRoot();

        return view;
    }

    /**
     * Gets the binding variable.
     *
     * @return The binding variable.
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Get id for the layout for this page.
     *
     * @return Layout id.
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    /**
     * Get the instance of the view model.
     *
     * @return Instance of the view model.
     */
    @Override
    public HomeFragmentViewModel getViewModel() {
        return mHomeFragmentViewModel;
    }

    /**
     * Method used when the user clicks on the create event button.
     * This starts a new Create Event activity.
     */
    @Override
    public void buttonCreateEventOnClick() {
        Intent intent = new Intent(getActivity(), AddEvent.class);
        startActivity(intent);
    }

    /**
     * Method used when the user clicks on the My Events button.
     * This changes the active fragment in Main activity to the My Events fragment.
     */
    @Override
    public void buttonMyEventsOnClick() {
        ((MainActivity) getActivity()).setView(1);
    }

    /**
     * Method used when the user clicks on the My Smart Devices button.
     * This changes the active fragment in Main activity to the My Smart Devices fragment.
     */
    @Override
    public void buttonMySmartDevicesOnClick() {
        ((MainActivity) getActivity()).setView(2);
    }

    /**
     * Method used when the user clicks on the create event button.
     * This starts a new Settings activity.
     */
    @Override
    public void buttonSettingsOnClick() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }
}