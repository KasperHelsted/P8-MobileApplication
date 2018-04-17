package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;



public class MyEventsFragmentViewModel extends BaseViewModel<MyEventsFragmentNavigator> {
    private final ObservableArrayList<Event> t = new ObservableArrayList<>();

    public MyEventsFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        //Creating list to render in view
        getListFromDb();
    }

    public void addNewEvent(){getNavigator().addNewEvent();}

    public void getListFromDb(){
        List<Event> a = new ArrayList<>();
        ArrayList<Event> arrayList = null;

        //Fetch list from database
        getCompositeDisposable().add(
            getDataManager().getAllEvents().subscribeOn(
                    getSchedulerProvider().io()
            ).observeOn(getSchedulerProvider().ui())
                    .subscribe(list -> {
                        RenderList(list);
            })
        );
    }

    public void RenderList(List<Event> e){
        t.clear();
        t.addAll(e);
        getNavigator().updatelist();
    }

    public ObservableList<Event> getEventObservableList() {
        return t;
    }

    public void deleteEvent(Event event){
        getCompositeDisposable().add(
                getDataManager().deleteEvent(event).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                .subscribe(response ->{})
        );

        t.remove(event);
        getNavigator().updatelist();
    }

    private void temp(){

        Event e = new Event();
        e.setAlarmId("1");
        e.setIntentId("1");
        e.setName("1");

        Event r = new Event();
        r.setName("2");
        r.setAlarmId("2");
        r.setIntentId("2");



        getCompositeDisposable().add(
                getDataManager().insertAllEvents(
                        e,r
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                })
        );

    }


}