package p8project.sw801.data;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.db.DbHelper;
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

public class AppDataManager implements DataManager {
    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mGson = gson;
    }

    //<editor-fold desc="Coordinate data manager">
    @Override
    public Observable<List<Coordinate>> getAllCoordinates() {
        return mDbHelper.getAllCoordinates();
    }

    @Override
    public Observable<Coordinate> getCoordinateById(Integer id) {
        return mDbHelper.getCoordinateById(id);
    }

    @Override
    public Observable<List<Coordinate>> getCoordinatesById(Integer[] ids) {
        return mDbHelper.getCoordinatesById(ids);
    }

    @Override
    public Observable<Coordinate> getLast() {
        return mDbHelper.getLast();
    }

    @Override
    public Observable<List<Coordinate>> getLast(Integer limit) {
        return mDbHelper.getLast(limit);
    }

    @Override
    public Observable<Integer> getCoordinateCount() {
        return mDbHelper.getCoordinateCount();
    }

    @Override
    public Observable<Boolean> isCoordinateEmpty() {
        return mDbHelper.isCoordinateEmpty();
    }

    @Override
    public Observable<Boolean> insertCoordinate(Coordinate coordinate) {
        return mDbHelper.insertCoordinate(coordinate);
    }

    @Override
    public Observable<Boolean> insertAllCoordinates(Coordinate... coordinates) {
        return mDbHelper.insertAllCoordinates(coordinates);
    }

    @Override
    public Observable<Boolean> updateCoordinate(Coordinate coordinate) {
        return mDbHelper.updateCoordinate(coordinate);
    }

    @Override
    public Observable<Boolean> deleteCoordinate(Coordinate coordinate) {
        return mDbHelper.deleteCoordinate(coordinate);
    }
    //</editor-fold>

    //<editor-fold desc="Event data manager">
    @Override
    public Observable<List<Event>> getAllEvents() {
        return mDbHelper.getAllEvents();
    }


    @Override
    public Observable<Event> getEventById(Integer id) {
        return mDbHelper.getEventById(id);
    }

    @Override
    public Observable<List<Event>> getEventsByIds(Integer[] ids) {
        return mDbHelper.getEventsByIds(ids);
    }

    @Override
    public Observable<Integer> getEventCount() {
        return mDbHelper.getEventCount();
    }

    @Override
    public Observable<Boolean> isEventEmpty() {
        return mDbHelper.isEventEmpty();
    }

    @Override
    public Observable<Boolean> insertEvent(Event event) {
        return mDbHelper.insertEvent(event);
    }

    @Override
    public Observable<Boolean> insertAllEvents(Event... events) {
        return mDbHelper.insertAllEvents(events);
    }

    @Override
    public Observable<Event> getLastEvent() {
        return mDbHelper.getLastEvent();
    }

    @Override
    public Observable<Boolean> updateEvent(Event event) {
        return mDbHelper.updateEvent(event);
    }

    @Override
    public Observable<Boolean> deleteEvent(Event event) {
        return mDbHelper.deleteEvent(event);
    }
    //</editor-fold>

    //<editor-fold desc="Global Mute data manager">
    @Override
    public Observable<List<GlobalMute>> getAllGlobalMutes() {
        return mDbHelper.getAllGlobalMutes();
    }

    @Override
    public Observable<GlobalMute> getGlobalMuteById(Integer id) {
        return mDbHelper.getGlobalMuteById(id);
    }

    @Override
    public Observable<List<GlobalMute>> getGlobalMutesByIds(Integer[] ids) {
        return mDbHelper.getGlobalMutesByIds(ids);
    }

    @Override
    public Observable<Integer> getGlobalMuteCount() {
        return mDbHelper.getGlobalMuteCount();
    }

    @Override
    public Observable<Boolean> isGlobalMuteEmpty() {
        return mDbHelper.isGlobalMuteEmpty();
    }

    @Override
    public Observable<Boolean> insertGlobalMute(GlobalMute globalMute) {
        return mDbHelper.insertGlobalMute(globalMute);
    }

    @Override
    public Observable<Boolean> insertAllGlobalMutes(GlobalMute... globalMutes) {
        return mDbHelper.insertAllGlobalMutes(globalMutes);
    }

    @Override
    public Observable<Boolean> updateGlobalMute(GlobalMute globalMute) {
        return mDbHelper.updateGlobalMute(globalMute);
    }

    @Override
    public Observable<Boolean> deleteGlobalMute(GlobalMute globalMute) {
        return mDbHelper.deleteGlobalMute(globalMute);
    }
    //</editor-fold>

    //<editor-fold desc="Predefined Location data manager">
    @Override
    public Observable<List<PredefinedLocation>> getAllPredefinedLocations() {
        return mDbHelper.getAllPredefinedLocations();
    }

    @Override
    public Observable<PredefinedLocation> getPredefinedLocationById(Integer id) {
        return mDbHelper.getPredefinedLocationById(id);
    }

    @Override
    public Observable<List<PredefinedLocation>> getPredefinedLocationsByIds(Integer[] ids) {
        return mDbHelper.getPredefinedLocationsByIds(ids);
    }

    @Override
    public Observable<Integer> getPredefinedLocationCount() {
        return mDbHelper.getPredefinedLocationCount();
    }

    @Override
    public Observable<Boolean> isPredefinedLocationEmpty() {
        return mDbHelper.isPredefinedLocationEmpty();
    }

    @Override
    public Observable<Boolean> insertPredefinedLocation(PredefinedLocation predefinedLocation) {
        return mDbHelper.insertPredefinedLocation(predefinedLocation);
    }

    @Override
    public Observable<Boolean> insertAllPredefinedLocation(PredefinedLocation... predefinedLocations) {
        return mDbHelper.insertAllPredefinedLocation(predefinedLocations);
    }

    @Override
    public Observable<Boolean> updatePredefinedLocation(PredefinedLocation predefinedLocation) {
        return mDbHelper.updatePredefinedLocation(predefinedLocation);
    }

    @Override
    public Observable<Boolean> deletePredefinedLocation(PredefinedLocation predefinedLocation) {
        return mDbHelper.deletePredefinedLocation(predefinedLocation);
    }
    //</editor-fold>

    //<editor-fold desc="Smart Device data manager">
    @Override
    public Observable<List<SmartDevice>> getAllSmartDevices() {
        return mDbHelper.getAllSmartDevices();
    }

    @Override
    public Observable<SmartDevice> getSmartDeviceById(Integer id) {
        return mDbHelper.getSmartDeviceById(id);
    }

    @Override
    public Observable<SmartDevice> getLastSmartDevice() {
        return mDbHelper.getLastSmartDevice();
    }

    @Override
    public Observable<List<SmartDevice>> getSmartDevicesByIds(Integer[] ids) {
        return mDbHelper.getSmartDevicesByIds(ids);
    }

    @Override
    public Observable<Integer> getSmartDeviceCount() {
        return mDbHelper.getSmartDeviceCount();
    }

    @Override
    public Observable<Boolean> isSmartDeviceEmpty() {
        return mDbHelper.isSmartDeviceEmpty();
    }

    @Override
    public Observable<Boolean> insertSmartDevice(SmartDevice smartDevice) {
        return mDbHelper.insertSmartDevice(smartDevice);
    }

    @Override
    public Observable<Boolean> insertAllSmartDevices(SmartDevice... smartDevices) {
        return mDbHelper.insertAllSmartDevices(smartDevices);
    }

    @Override
    public Observable<Boolean> updateSmartDevice(SmartDevice smartDevice) {
        return mDbHelper.updateSmartDevice(smartDevice);
    }

    @Override
    public Observable<Boolean> deleteSmartDevice(SmartDevice smartDevice) {
        return mDbHelper.deleteSmartDevice(smartDevice);
    }
    //</editor-fold>

    //<editor-fold desc="Trigger data manager">
    @Override
    public Observable<List<Trigger>> getAllTriggers() {
        return mDbHelper.getAllTriggers();
    }

    @Override
    public Observable<List<Trigger>> getTriggersByEventId(Integer id) {
        return mDbHelper.getTriggersByEventId(id);
    }

    @Override
    public Observable<Trigger> getTriggerById(Integer id) {
        return mDbHelper.getTriggerById(id);
    }

    @Override
    public Observable<List<Trigger>> getTriggersByIds(Integer[] ids) {
        return mDbHelper.getTriggersByIds(ids);
    }

    @Override
    public Observable<Integer> getTriggerCount() {
        return mDbHelper.getTriggerCount();
    }

    @Override
    public Observable<Boolean> isTriggerEmpty() {
        return mDbHelper.isTriggerEmpty();
    }

    @Override
    public Observable<List<Trigger>> getTriggersBySmartDeviceId(Integer id) {
        return mDbHelper.getTriggersBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> deleteTriggerBySmartDeviceId(Integer id) {
        return mDbHelper.deleteTriggerBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> deleteTriggerByEventId(Integer id) {
        return mDbHelper.deleteTriggerByEventId(id);
    }

    @Override
    public Observable<Boolean> insertTrigger(Trigger trigger) {
        return mDbHelper.insertTrigger(trigger);
    }

    @Override
    public Observable<Boolean> insertAllTriggers(List<Trigger> triggers) {
        return mDbHelper.insertAllTriggers(triggers);
    }

    @Override
    public Observable<Boolean> updateTrigger(Trigger trigger) {
        return mDbHelper.updateTrigger(trigger);
    }

    @Override
    public Observable<Boolean> deleteTrigger(Trigger trigger) {
        return mDbHelper.deleteTrigger(trigger);
    }
    //</editor-fold>

    //<editor-fold desc="When data manager">
    @Override
    public Observable<List<When>> getAllWhens() {
        return mDbHelper.getAllWhens();
    }

    @Override
    public Observable<When> getWhenById(Integer id) {
        return mDbHelper.getWhenById(id);
    }

    @Override
    public Observable<List<When>> getWhensByIds(Integer[] ids) {
        return mDbHelper.getWhensByIds(ids);
    }

    @Override
    public Observable<Integer> getWhenCount() {
        return mDbHelper.getWhenCount();
    }

    @Override
    public Observable<Boolean> isWhenEmpty() {
        return mDbHelper.isWhenEmpty();
    }

    @Override
    public Observable<Boolean> deleteWhenByEventId(Integer id) {
        return mDbHelper.deleteWhenByEventId(id);
    }

    @Override
    public Observable<Boolean> insertWhen(When when) {
        return mDbHelper.insertWhen(when);
    }

    @Override
    public Observable<Boolean> insertAllWhen(When... whens) {
        return mDbHelper.insertAllWhen(whens);
    }

    @Override
    public Observable<Boolean> updateWhen(When when) {
        return mDbHelper.updateWhen(when);
    }

    @Override
    public Observable<Boolean> deleteWhen(When when) {
        return mDbHelper.deleteWhen(when);
    }
    //</editor-fold>

    //<editor-fold desc="Start Chain data manager">
    @Override
    public Observable<List<Chain>> getAllChains() {
        return mDbHelper.getAllChains();
    }

    @Override
    public Observable<Integer> getChainCount() {
        return mDbHelper.getChainCount();
    }

    @Override
    public Observable<List<Chain>> getActiveChains() {
        return mDbHelper.getActiveChains();
    }

    @Override
    public Observable<List<Chain>> getChainsByIds(Integer[] ids) {
        return mDbHelper.getChainsByIds(ids);
    }

    @Override
    public Observable<Chain> getChainById(Integer id) {
        return mDbHelper.getChainById(id);
    }

    @Override
    public Observable<Boolean> insertAllChains(List<Chain> chains) {
        return mDbHelper.insertAllChains(chains);
    }

    @Override
    public Observable<Boolean> insertChain(Chain chain) {
        return mDbHelper.insertChain(chain);
    }

    @Override
    public Observable<Boolean> updateChain(Chain chain) {
        return mDbHelper.updateChain(chain);
    }

    @Override
    public Observable<Boolean> deleteChain(Chain chain) {
        return mDbHelper.deleteChain(chain);
    }

    //</editor-fold>

    //<editor-fold desc="Start Store data manager">
    @Override
    public Observable<List<Store>> getAllStores() {
        return mDbHelper.getAllStores();
    }

    @Override
    public Observable<Integer> getStoreCount() {
        return mDbHelper.getStoreCount();
    }

    @Override
    public Observable<List<Store>> getStoresByIds(Integer[] ids) {
        return mDbHelper.getStoresByIds(ids);
    }

    @Override
    public Observable<Store> getStoreByName(String storeName) {
        return mDbHelper.getStoreByName(storeName);
    }

    @Override
    public Observable<Store> getStoreById(Integer id) {
        return mDbHelper.getStoreById(id);
    }

    @Override
    public Observable<Boolean> insertAllStores(Store... stores) {
        return mDbHelper.insertAllStores(stores);
    }

    @Override
    public Observable<Boolean> insertStore(Store store) {
        return mDbHelper.insertStore(store);
    }

    @Override
    public Observable<Boolean> updateStore(Store store) {
        return mDbHelper.updateStore(store);
    }

    @Override
    public Observable<Boolean> deleteStore(Store store) {
        return mDbHelper.deleteStore(store);
    }
    //</editor-fold>

    //<editor-fold desc="EventWithData data manager">
    @Override
    public Observable<EventWithData> getEventWithData(final Integer id) {
        return mDbHelper.getEventWithData(id);
    }
    //</editor-fold>

    //<editor-fold desc="Hue Bridge data manager">
    @Override
    public Observable<List<HueBridge>> getAllHueBridges() {
        return mDbHelper.getAllHueBridges();
    }

    @Override
    public Observable<HueBridge> getLastInsertedHueBridge() {
        return mDbHelper.getLastInsertedHueBridge();
    }

    @Override
    public Observable<List<HueBridge>> getAllHueBridgesBySmartDeviceId(Integer id) {
        return mDbHelper.getAllHueBridgesBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> deleteHueBridgeBySmartDeviceId(Integer id) {
        return mDbHelper.deleteHueBridgeBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> insertAllHueBridges(HueBridge... hueBridges) {
        return mDbHelper.insertAllHueBridges(hueBridges);
    }

    @Override
    public Observable<Boolean> insertHueBridge(HueBridge hueBridge) {
        return mDbHelper.insertHueBridge(hueBridge);
    }

    @Override
    public Observable<Boolean> updateHueBridge(HueBridge hueBridge) {
        return mDbHelper.updateHueBridge(hueBridge);
    }

    @Override
    public Observable<Boolean> deleteHueBridge(HueBridge hueBridge) {
        return mDbHelper.deleteHueBridge(hueBridge);
    }
    //</editor-fold>

    //<editor-fold desc="Hue Light Bulb RGB data manager">
    @Override
    public Observable<List<HueLightbulbRGB>> getRGBLightsByBridgeId(Integer id) {
        return mDbHelper.getRGBLightsByBridgeId(id);
    }

    @Override
    public Observable<List<HueLightbulbRGB>> getRGBHueLightsBySmartDeviceId(Integer id) {
        return mDbHelper.getRGBHueLightsBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> deleteRGBHueLightsBySmartDeviceId(Integer id) {
        return mDbHelper.deleteRGBHueLightsBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> insertAllRGBHueLightbulbs(HueLightbulbRGB... hueLightbulbRGBS) {
        return mDbHelper.insertAllRGBHueLightbulbs(hueLightbulbRGBS);
    }

    @Override
    public Observable<Boolean> insertRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB) {
        return mDbHelper.insertRGBHueLightbulb(hueLightbulbRGB);
    }

    @Override
    public Observable<Boolean> updateRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB) {
        return mDbHelper.updateRGBHueLightbulb(hueLightbulbRGB);
    }

    @Override
    public Observable<Boolean> deleteRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB) {
        return mDbHelper.deleteRGBHueLightbulb(hueLightbulbRGB);
    }
    //</editor-fold>

    //<editor-fold desc="Hue Light Bulb White data manager">
    @Override
    public Observable<List<HueLightbulbWhite>> getWhiteLightsByBridgeId(Integer id) {
        return mDbHelper.getWhiteLightsByBridgeId(id);
    }

    @Override
    public Observable<List<HueLightbulbWhite>> getWhiteHueLightsBySmartDeviceId(Integer id) {
        return mDbHelper.getWhiteHueLightsBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> deleteWhiteHueLightsBySmartDeviceId(Integer id) {
        return mDbHelper.deleteWhiteHueLightsBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> insertAllWhiteHueLightbulbs(HueLightbulbWhite... hueLightbulbWhites) {
        return mDbHelper.insertAllWhiteHueLightbulbs(hueLightbulbWhites);
    }

    @Override
    public Observable<Boolean> insertWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite) {
        return mDbHelper.insertWhiteHueLightbulb(hueLightbulbWhite);
    }

    @Override
    public Observable<Boolean> updateWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite) {
        return mDbHelper.updateWhiteHueLightbulb(hueLightbulbWhite);
    }

    @Override
    public Observable<Boolean> deleteWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite) {
        return mDbHelper.deleteWhiteHueLightbulb(hueLightbulbWhite);
    }
    //</editor-fold>

    //<editor-fold desc="Nest Hub data manager">
    @Override
    public Observable<List<NestHub>> getAllNestHubs() {
        return mDbHelper.getAllNestHubs();
    }

    @Override
    public Observable<NestHub> getLastInsertedNestHub() {
        return mDbHelper.getLastInsertedNestHub();
    }

    @Override
    public Observable<List<NestHub>> getAllNestHubsBySmartDeviceId(Integer id) {
        return mDbHelper.getAllNestHubsBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> deleteNestHubBySmartDeviceId(Integer id) {
        return mDbHelper.deleteNestHubBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> insertAllNestHubs(NestHub... nestHubs) {
        return mDbHelper.insertAllNestHubs(nestHubs);
    }

    @Override
    public Observable<Boolean> insertNestHub(NestHub nestHub) {
        return mDbHelper.insertNestHub(nestHub);
    }

    @Override
    public Observable<Boolean> updateNestHub(NestHub nestHub) {
        return mDbHelper.updateNestHub(nestHub);
    }

    @Override
    public Observable<Boolean> deleteNestHub(NestHub nestHub) {
        return mDbHelper.deleteNestHub(nestHub);
    }
    //</editor-fold>

    //<editor-fold desc="Nest Thermostat data manager">
    @Override
    public Observable<List<NestThermostat>> getThermostatByHubId(Integer id) {
        return mDbHelper.getThermostatByHubId(id);
    }

    @Override
    public Observable<List<NestThermostat>> getNestThermostatBySmartDeviceId(Integer id) {
        return mDbHelper.getNestThermostatBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> deleteNestThermostatBySmartDeviceId(Integer id) {
        return mDbHelper.deleteNestThermostatBySmartDeviceId(id);
    }

    @Override
    public Observable<Boolean> insertAllNestThermostats(NestThermostat... nestThermostats) {
        return mDbHelper.insertAllNestThermostats(nestThermostats);
    }

    @Override
    public Observable<Boolean> insertNestThermostat(NestThermostat nestThermostat) {
        return mDbHelper.insertNestThermostat(nestThermostat);
    }

    @Override
    public Observable<Boolean> updateNestThermostat(NestThermostat nestThermostat) {
        return mDbHelper.updateNestThermostat(nestThermostat);
    }

    @Override
    public Observable<Boolean> deleteNestThermostat(NestThermostat nestThermostat) {
        return mDbHelper.deleteNestThermostat(nestThermostat);
    }
    //</editor-fold>
}
