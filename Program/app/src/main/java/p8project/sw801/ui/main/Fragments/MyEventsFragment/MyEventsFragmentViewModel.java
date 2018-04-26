package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;


public class MyEventsFragmentViewModel extends BaseViewModel<MyEventsFragmentNavigator> {
    private final ObservableArrayList<Event> eventArrayList = new ObservableArrayList<>();

    public MyEventsFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        fetchFromDatabase();
    }

    /**
     * Calls the addNewEvent function from MyEventFragment
     */
    public void addNewEvent() {
        Event e = new Event();
        e.setName("TESTER");
        e.setActive(true);

        getCompositeDisposable().add(
                getDataManager().insertEvent(
                        e
                ).subscribeOn(getSchedulerProvider().io()).subscribe(test -> {
                    fetchFromDatabase();
                })
        );

        //getNavigator().addNewEvent();
    }

    /**
     * Method used to fetch the list of events from the database and calling a method to update the view
     */
    public void fetchFromDatabase() {
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
     * Method used to update the view by calling updatelist function from MyEventFragment
     *
     * @param e
     */
    public void RenderList(List<Event> e) {
        eventArrayList.clear();
        eventArrayList.addAll(e);
        getNavigator().updatelist();
    }

    /**
     * Returns the observable list of events
     *
     * @return t
     */
    public ObservableList<Event> getEventObservableList() {
        return eventArrayList;
    }

    /**
     * Deletes an event from the database and calls a method to update the view
     *
     * @param event
     */
    public void deleteEvent(Event event) {
        getNavigator().deleteEvent(event);
        System.out.println("HMM?");
//        getCompositeDisposable().add(
//                getDataManager().deleteEvent(event).subscribeOn(
//                        getSchedulerProvider().io()
//                ).observeOn(getSchedulerProvider().ui())
//                .subscribe(response ->{})
//        );
//
//        eventArrayList.remove(event);
//        getNavigator().updatelist();
    }

    /**
     * Updates an event in the database and calls a method to update the view
     *
     * @param event
     * @param condition
     */
    public void updateEvent(Event event, Boolean condition) {
        event.setActive(condition);

        getCompositeDisposable().add(
                getDataManager().updateEvent(event).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                })
        );
        fetchFromDatabase();
    }

    private void deleteWhens(Integer id) {
        getCompositeDisposable().add(
                getDataManager().deleteWhenByEventId(
                        id
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe());
    }

    private void deleteTriggers(Integer id) {
        getCompositeDisposable().add(
                getDataManager().deleteTriggerByEventId(
                        id
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe());
    }

    void deleteEventFromDatabase(Event event) {
        Integer id = event.getId();

        getCompositeDisposable().add(
                getDataManager().deleteEvent(
                        event
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(deleted -> {
                    this.deleteTriggers(id);
                    this.deleteWhens(id);

                    this.fetchFromDatabase();
                }));

    }
}