package p8project.sw801.ui.splash;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void startSeeding() {
        /*
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseQuestions()
                .flatMap(aBoolean -> getDataManager().seedDatabaseOptions())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                    decideNextActivity();
                }, throwable -> {
                    decideNextActivity();
                }));
        */
    }

    private void decideNextActivity() {
        if (getDataManager().getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType())
        {
            //getNavigator().openLoginActivity();
        } else {
            getNavigator().openMainActivity();
        }
    }
}
