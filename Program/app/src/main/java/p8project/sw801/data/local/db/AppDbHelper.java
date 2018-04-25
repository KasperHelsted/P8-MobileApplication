package p8project.sw801.data.local.db;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbRGB;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    //<editor-fold desc="Start Coordinate helper">
    @Override
    public Observable<List<Coordinate>> getAllCoordinates() {
        return Observable.fromCallable(() -> mAppDatabase.coordinateDao().getAll());
    }

    @Override
    public Observable<Coordinate> getCoordinateById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.coordinateDao().loadById(id));
    }

    @Override
    public Observable<List<Coordinate>> getCoordinatesById(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.coordinateDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Coordinate> getLast() {
        return Observable.fromCallable(() -> mAppDatabase.coordinateDao().getLast());
    }

    @Override
    public Observable<List<Coordinate>> getLast(Integer limit) {
        return Observable.fromCallable(() -> mAppDatabase.coordinateDao().getLast(limit));
    }


    @Override
    public Observable<Integer> getCoordinateCount() {
        return Observable.fromCallable(() -> mAppDatabase.coordinateDao().count());
    }

    @Override
    public Observable<Boolean> isCoordinateEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.coordinateDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insertCoordinate(final Coordinate coordinate) {
        return Observable.fromCallable(() -> {
            mAppDatabase.coordinateDao().insert(coordinate);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllCoordinates(final Coordinate... coordinates) {
        return Observable.fromCallable(() -> {
            mAppDatabase.coordinateDao().insertAll(coordinates);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateCoordinate(final Coordinate coordinate) {
        return Observable.fromCallable(() -> {
            mAppDatabase.coordinateDao().update(coordinate);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteCoordinate(final Coordinate coordinate) {
        return Observable.fromCallable(() -> {
            mAppDatabase.coordinateDao().delete(coordinate);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Start Event helper">
    @Override
    public Observable<List<Event>> getAllEvents() {
        return Observable.fromCallable(() -> mAppDatabase.eventDao().getAll());
    }

    @Override
    public Observable<Event> getEventById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.eventDao().loadById(id));
    }

    @Override
    public Observable<List<Event>> getEventsByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.eventDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Integer> getEventCount() {
        return Observable.fromCallable(() -> mAppDatabase.eventDao().count());
    }

    @Override
    public Observable<Boolean> isEventEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.eventDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insertEvent(final Event event) {
        return Observable.fromCallable(() -> {
            mAppDatabase.eventDao().insert(event);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllEvents(final Event... events) {
        return Observable.fromCallable(() -> {
            mAppDatabase.eventDao().insertAll(events);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateEvent(final Event event) {
        return Observable.fromCallable(() -> {
            mAppDatabase.eventDao().update(event);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteEvent(final Event event) {
        return Observable.fromCallable(() -> {
            mAppDatabase.eventDao().delete(event);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Start Global Mute helper">
    @Override
    public Observable<List<GlobalMute>> getAllGlobalMutes() {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().getAll());
    }

    @Override
    public Observable<GlobalMute> getGlobalMuteById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().loadById(id));
    }

    @Override
    public Observable<List<GlobalMute>> getGlobalMutesByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Integer> getGlobalMuteCount() {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().count());
    }

    @Override
    public Observable<Boolean> isGlobalMuteEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insertGlobalMute(final GlobalMute globalMute) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().insert(globalMute);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllGlobalMutes(final GlobalMute... globalMutes) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().insertAll(globalMutes);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateGlobalMute(final GlobalMute globalMute) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().update(globalMute);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteGlobalMute(final GlobalMute globalMute) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().delete(globalMute);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Start Predefined Location helper">
    @Override
    public Observable<List<PredefinedLocation>> getAllPredefinedLocations() {
        return Observable.fromCallable(() -> mAppDatabase.predefinedLocationDao().getAll());
    }

    @Override
    public Observable<PredefinedLocation> getPredefinedLocationById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.predefinedLocationDao().loadById(id));
    }

    @Override
    public Observable<List<PredefinedLocation>> getPredefinedLocationsByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.predefinedLocationDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Integer> getPredefinedLocationCount() {
        return Observable.fromCallable(() -> mAppDatabase.predefinedLocationDao().countPredefinedLocations());
    }

    @Override
    public Observable<Boolean> isPredefinedLocationEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.predefinedLocationDao().countPredefinedLocations() == 0);
    }

    @Override
    public Observable<Boolean> insertPredefinedLocation(final PredefinedLocation predefinedLocation) {
        return Observable.fromCallable(() -> {
            mAppDatabase.predefinedLocationDao().insert(predefinedLocation);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllPredefinedLocation(final PredefinedLocation... predefinedLocations) {
        return Observable.fromCallable(() -> {
            mAppDatabase.predefinedLocationDao().insertAll(predefinedLocations);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updatePredefinedLocation(final PredefinedLocation predefinedLocation) {
        return Observable.fromCallable(() -> {
            mAppDatabase.predefinedLocationDao().update(predefinedLocation);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deletePredefinedLocation(final PredefinedLocation predefinedLocation) {
        return Observable.fromCallable(() -> {
            mAppDatabase.predefinedLocationDao().delete(predefinedLocation);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Start Smart Device helper">
    @Override
    public Observable<List<SmartDevice>> getAllSmartDevices() {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().getAll());
    }


    @Override
    public Observable<SmartDevice> getSmartDeviceById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().loadById(id));
    }

    @Override
    public Observable<SmartDevice> getLastSmartDevice() {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().getLast());
    }

    @Override
    public Observable<List<SmartDevice>> getSmartDevicesByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Integer> getSmartDeviceCount() {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().count());
    }

    @Override
    public Observable<Boolean> isSmartDeviceEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insertSmartDevice(final SmartDevice smartDevice) {
        return Observable.fromCallable(() -> {
            mAppDatabase.smartDeviceDao().insert(smartDevice);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllSmartDevices(final SmartDevice... smartDevices) {
        return Observable.fromCallable(() -> {
            mAppDatabase.smartDeviceDao().insertAll(smartDevices);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateSmartDevice(final SmartDevice smartDevice) {
        return Observable.fromCallable(() -> {
            mAppDatabase.smartDeviceDao().update(smartDevice);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteSmartDevice(final SmartDevice smartDevice) {
        return Observable.fromCallable(() -> {
            mAppDatabase.smartDeviceDao().delete(smartDevice);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Start Trigger helper">
    @Override
    public Observable<List<Trigger>> getAllTriggers() {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().getAll());
    }

    @Override
    public Observable<Trigger> getTriggerById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().loadById(id));
    }

    @Override
    public Observable<List<Trigger>> getTriggersByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().loadAllByIds(ids));
    }

    @Override
    public Observable<List<Trigger>> getTriggersByEventId(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().loadAllByEventId(id));
    }

    @Override
    public Observable<Integer> getTriggerCount() {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().count());
    }

    @Override
    public Observable<Boolean> isTriggerEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insertTrigger(final Trigger trigger) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().insert(trigger);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllTriggers(final Trigger... triggers) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().insertAll(triggers);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateTrigger(final Trigger trigger) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().update(trigger);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteTrigger(final Trigger trigger) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().delete(trigger);
            return true;
        });
    }

    @Override
    public Observable<List<Trigger>> getTriggersBySmartDeviceId(final Integer id){
        return Observable.fromCallable(() ->mAppDatabase.triggerDao().loadAllBySmartDeviceId(id));
    }


    //</editor-fold>

    //<editor-fold desc="Start When helper">
    @Override
    public Observable<List<When>> getAllWhens() {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().getAll());
    }

    @Override
    public Observable<When> getWhenById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().loadById(id));
    }

    @Override
    public Observable<List<When>> getWhensByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Integer> getWhenCount() {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().count());
    }

    @Override
    public Observable<Boolean> isWhenEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insertWhen(final When when) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().insert(when);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllWhen(final When... whens) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().insertAll(whens);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateWhen(final When when) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().update(when);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteWhen(final When when) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().delete(when);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Accessories">

    @Override
    public Observable<List<HueLightbulbWhite>> getLightsByBridgeId(final Integer id){
        return Observable.fromCallable(()-> mAppDatabase.accessoriesDao().getLightsByBridgeIdDB(id));
    }

    @Override
    public Observable<List<HueLightbulbRGB>> getRGBLightsByBridgeId(final Integer id){
        return  Observable.fromCallable(()-> mAppDatabase.accessoriesDao().getRGBLightsByBridgeIdDB(id));
    }

    @Override
    public Observable<List<NestThermostat>> getNestByHubId(final Integer id){
        return  Observable.fromCallable(()-> mAppDatabase.accessoriesDao().getNestByHubIdDB(id));
    }

    @Override
    public Observable<Boolean> insertAllHueLights(final HueLightbulbWhite... hueLightbulbWhites){
        return Observable.fromCallable(() -> {
            mAppDatabase.accessoriesDao().insertAllHue(hueLightbulbWhites);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertHueLight(final HueLightbulbWhite hueLightbulbWhite){
        return Observable.fromCallable(() -> {
            mAppDatabase.accessoriesDao().insertHue(hueLightbulbWhite);
            return true;
        });
    }
    @Override
    public Observable<Boolean> insertAllNestThermos(final NestThermostat... nestThermostats){
        return Observable.fromCallable(() -> {
            mAppDatabase.accessoriesDao().insertAllNestThermos(nestThermostats);
            return true;
        });
    }
    @Override
    public Observable<Boolean> insertNestThermo(final NestThermostat nestThermostat){
        return Observable.fromCallable(() -> {
            mAppDatabase.accessoriesDao().insertNestThermo(nestThermostat);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertHueBridge(final HueBridge hueBridge){
        return Observable.fromCallable(() -> {
            mAppDatabase.accessoriesDao().insertHueBidge(hueBridge);
            return true;
        });
    }
    @Override
    public Observable<Boolean> insertNestHub(final NestHub nestHub){
        return Observable.fromCallable(() -> {
            mAppDatabase.accessoriesDao().insertNestHub(nestHub);
            return true;
        });
    }

    @Override
    public Observable<List<HueLightbulbWhite>> getHueLightsBySmartDeviceId(final Integer id){
        return  Observable.fromCallable(()-> mAppDatabase.accessoriesDao().getHueLightsBySmartDeviceId(id));
    }
    @Override
    public Observable<List<NestThermostat>> getNestThermoBySmartDeviceId(final Integer id){
        return Observable.fromCallable(()-> mAppDatabase.accessoriesDao().getNestThermoBySmartDeviceId(id));
    }


    @Override
    public Observable<List<HueBridge>> getAllHueBridges(){
        return Observable.fromCallable(()-> mAppDatabase.accessoriesDao().getAllHueBridges());
    }

    @Override
    public Observable<HueBridge> getLastHueBridge() {
        return Observable.fromCallable(() -> mAppDatabase.accessoriesDao().getLastHueBridge());

    }

    @Override
    public Observable<List<NestHub>> getAllNestHubs(){
        return Observable.fromCallable(() -> mAppDatabase.accessoriesDao().getAllNestHubs());
    }



    //</editor-fold>
}
