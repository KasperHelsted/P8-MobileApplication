package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;



public class MyEventsFragmentViewModel extends BaseViewModel<MyEventsFragmentNavigator> {
    private final ObservableArrayList<Event> t = new ObservableArrayList<>();
    private final ObservableArrayList<Trigger> triggerList = new ObservableArrayList<>();

    public MyEventsFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        //temp();
        //Creating list to render in view
        getListFromDb();
    }

    /**
     * Calls the addNewEvent function from MyEventFragment
     */
    public void addNewEvent(){getNavigator().addNewEvent();}

    /**
     * Method used to fetch the list of events from the database and calling a method to update the view
     */
    private void getListFromDb(){
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

    /**
     *Method used to update the view by calling updatelist function from MyEventFragment
     * @param e
     */
    public void RenderList(List<Event> e){
        t.clear();
        t.addAll(e);
        getNavigator().updatelist();
    }

    /**
     * Returns the observable list of events
     * @return t
     */
    public ObservableList<Event> getEventObservableList() {
        return t;
    }

    /**
     *Deletes an event from the database and calls a method to update the view
     * @param event
     */
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

    /**
     *Updates an event in the database and calls a method to update the view
     * @param event
     * @param condition
     */
    public void updateEvent(Event event, Boolean condition){
        event.setActive(condition);

        getCompositeDisposable().add(
                getDataManager().updateEvent(event).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response ->{})
        );
        getListFromDb();
    }


    private void temp(){

        Event e = new Event();
        e.setName("1");
        e.setAlarmId("1");
        e.setIntentId("1");
        e.setActive(false);

        Event r = new Event();
        r.setName("2");
        r.setAlarmId("2");
        r.setIntentId("2");
        r.setActive(true);



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