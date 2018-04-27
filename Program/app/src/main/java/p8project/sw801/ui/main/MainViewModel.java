package p8project.sw801.ui.main;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Store;
import p8project.sw801.data.seeding.Chains.BaseChain;
import p8project.sw801.data.seeding.Seeder;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
public class MainViewModel extends BaseViewModel<MainNavigator> {
    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void firstRunSeeding() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences((MainActivity) getNavigator());

        if (!prefs.getBoolean("firstTime", false)) {
            Seeder seeder = new Seeder();

            for (BaseChain baseChain : seeder.baseChains) {
                this.insertChain(baseChain);
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }
    }

    private void insertChain(BaseChain baseChain) {
        getCompositeDisposable().add(
                getDataManager().insertChain(
                        baseChain.chain()
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(inserted -> {
                    this.insertStores(baseChain);
                })
        );
    }

    private void insertStores(BaseChain baseChain) {
        getCompositeDisposable().add(
                getDataManager().getChainbyName(
                        baseChain.chain().getBrandName()
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(chain -> {
                    for (Store store : baseChain.stores((MainActivity) getNavigator())) {
                        store.setChainId(chain.getId());
                        this.insertStore(store);
                    }
                })
        );
    }


    private void insertStore(Store store) {
        getCompositeDisposable().add(
                getDataManager().insertStore(
                        store
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe()
        );
    }

    public void buttonMyEventsOnClick() {
        getNavigator().buttonMyEventsOnClick();
    }

    public void buttonMySmartDevicesOnClick() {
        getNavigator().buttonMySmartDevicesOnClick();
    }

}
