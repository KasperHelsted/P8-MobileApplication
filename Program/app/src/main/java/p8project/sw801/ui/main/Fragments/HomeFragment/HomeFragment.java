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
    @Inject
    HomeFragmentViewModel mHomeFragmentViewModel;
    private FragmentHomeBinding mFragmentHomeBinding;

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mFragmentHomeBinding = getViewDataBinding();
        mHomeFragmentViewModel.setNavigator(this);

        View view = mFragmentHomeBinding.getRoot();

        return view;
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeFragmentViewModel getViewModel() {
        return mHomeFragmentViewModel;
    }

    @Override
    public void buttonCreateEventOnClick() {
        Intent intent = new Intent(getActivity(), AddEvent.class);
        startActivity(intent);
    }

    @Override
    public void buttonMyEventsOnClick() {
        ((MainActivity) getActivity()).setView(1);
    }

    @Override
    public void buttonMySmartDevicesOnClick() {
        ((MainActivity) getActivity()).setView(2);
    }

    @Override
    public void buttonSettingsOnClick() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }
}