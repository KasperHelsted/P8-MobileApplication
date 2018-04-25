package p8project.sw801.ui.Settings.Shopping;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Chain;
import p8project.sw801.data.model.db.Store;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class ShoppingSettingViewModel extends BaseViewModel<ShoppingSettingNavigator> {
    private final ObservableArrayList<Chain> listOfChains = new ObservableArrayList<>();
    public final ObservableBoolean favorite = new ObservableBoolean(false);

    public ShoppingSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
       // setup();
        getListFromDb();
    }
    public void getListFromDb(){

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

    public void RenderList(List<Chain> chainList){
        listOfChains.clear();
        listOfChains.addAll(chainList);
        getNavigator().updateShoppingList();
    }

    public ObservableList<Chain> getChainsObservableList() {
        return listOfChains;
    }

    public void checked()
    {
        favorite.set(true);
    }

    public void unchecked()
    {
        favorite.set(false);
    }

    public void updateChain(Chain chain){
        getCompositeDisposable().add(
                getDataManager().updateChain(chain).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe()
        );
    }


 /*   public void setup()
    {
        Chain NettoChain = new Chain();
        NettoChain.setActive(true);
        NettoChain.setBrandName("Netto");

        Chain fotexChain = new Chain();
        fotexChain.setActive(true);
        fotexChain.setBrandName("Føtex");





        List<Chain> chains = new ArrayList<>();
        chains.add(NettoChain);
        chains.add(fotexChain);



        getCompositeDisposable().add(
                getDataManager().insertAllChains(chains).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe()
        );
    }
    */



}
