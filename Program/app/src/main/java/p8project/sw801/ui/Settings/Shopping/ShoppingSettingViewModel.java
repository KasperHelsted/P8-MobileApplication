package p8project.sw801.ui.Settings.Shopping;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Chain;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class ShoppingSettingViewModel extends BaseViewModel<ShoppingSettingNavigator> {
    public final ObservableBoolean favorite = new ObservableBoolean(false);
    /**
     * Observable fields for chain data and the favourites
     */
    private final ObservableArrayList<Chain> listOfChains = new ObservableArrayList<>();

    /**
     * Constructor for the viewmodel
     * Updates the view with data from the database
     *
     * @param dataManager       the database
     * @param schedulerProvider class that controls which scheduler is used
     */
    public ShoppingSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getListFromDb();
    }

    /**
     * Receives the data from the database
     */
    public void getListFromDb() {

        //Fetch list from database
        getCompositeDisposable().add(
                getDataManager().getAllChains().subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(list -> {
                            RenderList(list);
                        })
        );
    }

    /**
     * Seends the list of chains to the activity to render
     *
     * @param chainList list of chains
     */
    public void RenderList(List<Chain> chainList) {
        listOfChains.clear();
        listOfChains.addAll(chainList);
        getNavigator().updateShoppingList();
    }

    /**
     * Observable that contains the current list of chains
     *
     * @return
     */
    public ObservableList<Chain> getChainsObservableList() {
        return listOfChains;
    }

    /**
     * Observable for the favourites - Checked
     */
    public void checked() {
        favorite.set(true);
    }

    /**
     * Observable for the favourites - unchecked
     */
    public void unchecked() {
        favorite.set(false);
    }

    /**
     * Update method for a chain
     *
     * @param chain one chain to update
     */
    public void updateChain(Chain chain) {
        getCompositeDisposable().add(
                getDataManager().updateChain(chain).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe()
        );
    }
}
