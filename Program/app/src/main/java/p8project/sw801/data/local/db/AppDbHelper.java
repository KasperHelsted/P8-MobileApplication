package p8project.sw801.data.local.db;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.Chain;
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
import p8project.sw801.data.model.db.Store;
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
    public Observable<Event> getLastEvent() {
        return Observable.fromCallable(() -> mAppDatabase.eventDao().getLastEvent());
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
    public Observable<Boolean> insertGlobalMute(GlobalMute globalMute) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().insert(globalMute);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllGlobalMutes(GlobalMute... globalMutes) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().insertAll(globalMutes);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateGlobalMute(GlobalMute globalMute) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().update(globalMute);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteGlobalMute(GlobalMute globalMute) {
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
    public Observable<PredefinedLocation> getPredefinedLocationByCoordinateId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.predefinedLocationDao().loadByCoordinateId(id));
    }

    @Override
    public Observable<PredefinedLocation> getLastPredefinedLocation() {
        return Observable.fromCallable(() -> mAppDatabase.predefinedLocationDao().getLast());
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
    public Observable<List<Trigger>> getTriggersBySmartDeviceId(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().loadAllBySmartDeviceId(id));
    }

    @Override
    public Observable<Boolean> deleteTriggerBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().deleteTriggerBySmartDeviceId(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteTriggerByEventId(Integer id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().deleteTriggerByEventId(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertTrigger(final Trigger trigger) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().insert(trigger);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllTriggers(final List<Trigger> triggers) {
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
    public Observable<Boolean> deleteWhenByEventId(Integer id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().deleteWhenByEventId(id);
            return true;
        });
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

    //<editor-fold desc="Start Chain helper">
    @Override
    public Observable<List<Chain>> getAllChains() {
        return Observable.fromCallable(() -> mAppDatabase.chainDao().getAll());
    }

    @Override
    public Observable<Integer> getChainCount() {
        return Observable.fromCallable(() -> mAppDatabase.chainDao().count());
    }

    @Override
    public Observable<Chain> getChainbyName(String brandName) {
        return Observable.fromCallable(() -> mAppDatabase.chainDao().byChainName(brandName));
    }

    @Override
    public Observable<List<Chain>> getActiveChains() {
        return Observable.fromCallable(() -> mAppDatabase.chainDao().getActiveChains());
    }

    @Override
    public Observable<List<Chain>> getChainsByIds(Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.chainDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Chain> getChainById(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.chainDao().loadById(id));
    }

    @Override
    public Observable<Boolean> insertAllChains(List<Chain> chains) {
        return Observable.fromCallable(() -> {
            mAppDatabase.chainDao().insertAll(chains);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertChain(Chain chain) {
        return Observable.fromCallable(() -> {
            mAppDatabase.chainDao().insert(chain);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateChain(Chain chain) {
        return Observable.fromCallable(() -> {
            mAppDatabase.chainDao().update(chain);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteChain(Chain chain) {
        return Observable.fromCallable(() -> {
            mAppDatabase.chainDao().delete(chain);
            return true;
        });
    }

    //</editor-fold>

    //<editor-fold desc="Start Store helper">
    @Override
    public Observable<List<Store>> getAllStores() {
        return Observable.fromCallable(() -> mAppDatabase.storeDao().getAll());
    }

    @Override
    public Observable<Integer> getStoreCount() {
        return Observable.fromCallable(() -> mAppDatabase.storeDao().count());
    }

    @Override
    public Observable<List<Store>> getStoresByIds(Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.storeDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Store> getStoreByName(String storeName) {
        return Observable.fromCallable(() -> mAppDatabase.storeDao().getStoreByName(storeName));
    }

    @Override
    public Observable<Store> getStoreById(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.storeDao().loadById(id));
    }

    @Override
    public Observable<Boolean> insertAllStores(Store... stores) {
        return Observable.fromCallable(() -> {
            mAppDatabase.storeDao().insertAll(stores);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertStore(Store store) {
        return Observable.fromCallable(() -> {
            mAppDatabase.storeDao().insert(store);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateStore(Store store) {
        return Observable.fromCallable(() -> {
            mAppDatabase.storeDao().update(store);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteStore(Store store) {
        return Observable.fromCallable(() -> {
            mAppDatabase.storeDao().delete(store);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="EventWithData helper">
    @Override
    public Observable<EventWithData> getEventWithData(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.eventWithDataDao().getEventWithData(id));
    }
    //</editor-fold>

    //<editor-fold desc="Hue Bridge helper">
    @Override
    public Observable<List<HueBridge>> getAllHueBridges() {
        return Observable.fromCallable(() -> mAppDatabase.hueBridgeDao().getAllHueBridges());
    }

    @Override
    public Observable<HueBridge> getLastInsertedHueBridge() {
        return Observable.fromCallable(() -> mAppDatabase.hueBridgeDao().getLastInsertedHueBridge());
    }

    @Override
    public Observable<List<HueBridge>> getAllHueBridgesBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.hueBridgeDao().getAllHueBridgesBySmartDeviceId(id));
    }

    @Override
    public Observable<Boolean> deleteHueBridgeBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueBridgeDao().deleteHueBridgeBySmartDeviceId(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllHueBridges(HueBridge... hueBridges) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueBridgeDao().insertAllHueBridges(hueBridges);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertHueBridge(HueBridge hueBridge) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueBridgeDao().insertHueBridge(hueBridge);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateHueBridge(HueBridge hueBridge) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueBridgeDao().updateHueBridge(hueBridge);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteHueBridge(HueBridge hueBridge) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueBridgeDao().deleteHueBridge(hueBridge);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Hue Light Bulb RGB helper">
    @Override
    public Observable<List<HueLightbulbRGB>> getRGBLightsByBridgeId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.hueLightbulbRGBDao().getRGBLightsByBridgeId(id));
    }

    @Override
    public Observable<List<HueLightbulbRGB>> getRGBHueLightsBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.hueLightbulbRGBDao().getRGBHueLightsBySmartDeviceId(id));
    }

    @Override
    public Observable<Boolean> deleteRGBHueLightsBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbRGBDao().deleteRGBHueLightsBySmartDeviceId(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllRGBHueLightbulbs(HueLightbulbRGB... hueLightbulbRGBS) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbRGBDao().insertAllRGBHueLightbulbs(hueLightbulbRGBS);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbRGBDao().insertRGBHueLightbulb(hueLightbulbRGB);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbRGBDao().updateRGBHueLightbulb(hueLightbulbRGB);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbRGBDao().deleteRGBHueLightbulb(hueLightbulbRGB);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Hue Light Bulb White helper">
    @Override
    public Observable<List<HueLightbulbWhite>> getWhiteLightsByBridgeId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.hueLightbulbWhiteDao().getWhiteLightsByBridgeId(id));
    }

    @Override
    public Observable<List<HueLightbulbWhite>> getWhiteHueLightsBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.hueLightbulbWhiteDao().getWhiteHueLightsBySmartDeviceId(id));
    }

    @Override
    public Observable<Boolean> deleteWhiteHueLightsBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbWhiteDao().deleteWhiteHueLightsBySmartDeviceId(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllWhiteHueLightbulbs(HueLightbulbWhite... hueLightbulbWhites) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbWhiteDao().insertAllWhiteHueLightbulbs(hueLightbulbWhites);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbWhiteDao().insertWhiteHueLightbulb(hueLightbulbWhite);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbWhiteDao().updateWhiteHueLightbulb(hueLightbulbWhite);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite) {
        return Observable.fromCallable(() -> {
            mAppDatabase.hueLightbulbWhiteDao().deleteWhiteHueLightbulb(hueLightbulbWhite);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Nest Hub helper">
    @Override
    public Observable<List<NestHub>> getAllNestHubs() {
        return Observable.fromCallable(() -> mAppDatabase.nestHubDao().getAllNestHubs());
    }

    @Override
    public Observable<NestHub> getLastInsertedNestHub() {
        return Observable.fromCallable(() -> mAppDatabase.nestHubDao().getLastInsertedNestHub());
    }

    @Override
    public Observable<List<NestHub>> getAllNestHubsBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.nestHubDao().getAllNestHubsBySmartDeviceId(id));
    }

    @Override
    public Observable<Boolean> deleteNestHubBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestHubDao().deleteNestHubBySmartDeviceId(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllNestHubs(NestHub... nestHubs) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestHubDao().insertAllNestHubs(nestHubs);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertNestHub(NestHub nestHub) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestHubDao().insertNestHub(nestHub);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateNestHub(NestHub nestHub) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestHubDao().updateNestHub(nestHub);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteNestHub(NestHub nestHub) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestHubDao().deleteNestHub(nestHub);
            return true;
        });
    }
    //</editor-fold>

    //<editor-fold desc="Nest Thermostat helper">
    @Override
    public Observable<List<NestThermostat>> getThermostatByHubId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.nestThermostatDao().getThermostatByHubId(id));
    }

    @Override
    public Observable<List<NestThermostat>> getNestThermostatBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.nestThermostatDao().getNestThermostatBySmartDeviceId(id));
    }

    @Override
    public Observable<Boolean> deleteNestThermostatBySmartDeviceId(Integer id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestThermostatDao().deleteNestThermostatBySmartDeviceId(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAllNestThermostats(NestThermostat... nestThermostats) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestThermostatDao().insertAllNestThermostats(nestThermostats);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertNestThermostat(NestThermostat nestThermostat) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestThermostatDao().insertNestThermostat(nestThermostat);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateNestThermostat(NestThermostat nestThermostat) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestThermostatDao().updateNestThermostat(nestThermostat);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteNestThermostat(NestThermostat nestThermostat) {
        return Observable.fromCallable(() -> {
            mAppDatabase.nestThermostatDao().deleteNestThermostat(nestThermostat);
            return true;
        });
    }
    //</editor-fold>
}
