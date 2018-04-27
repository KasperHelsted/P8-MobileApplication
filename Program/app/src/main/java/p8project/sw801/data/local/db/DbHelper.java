package p8project.sw801.data.local.db;


import java.util.List;

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
 * Created by Kasper Helsted on 3/26/2018.
 */

public interface DbHelper {
    //<editor-fold desc="Coordinates">
    Observable<List<Coordinate>> getAllCoordinates();

    Observable<Coordinate> getCoordinateById(final Integer id);

    Observable<List<Coordinate>> getCoordinatesById(final Integer[] ids);

    Observable<Coordinate> getLast();

    Observable<List<Coordinate>> getLast(Integer limit);

    Observable<Integer> getCoordinateCount();

    Observable<Boolean> isCoordinateEmpty();

    Observable<Boolean> insertCoordinate(final Coordinate obj);

    Observable<Boolean> insertAllCoordinates(final Coordinate... obj);

    Observable<Boolean> updateCoordinate(final Coordinate obj);

    Observable<Boolean> deleteCoordinate(final Coordinate obj);
    //</editor-fold>

    //<editor-fold desc="Events">
    Observable<List<Event>> getAllEvents();

    Observable<Event> getEventById(final Integer id);

    Observable<List<Event>> getEventsByIds(final Integer[] ids);

    Observable<Integer> getEventCount();

    Observable<Boolean> isEventEmpty();

    Observable<Event> getLastEvent();

    Observable<Boolean> insertEvent(final Event event);

    Observable<Boolean> insertAllEvents(final Event... events);

    Observable<Boolean> updateEvent(final Event event);

    Observable<Boolean> deleteEvent(final Event event);
    //</editor-fold>

    //<editor-fold desc="GlobalMutes">
    Observable<List<GlobalMute>> getAllGlobalMutes();

    Observable<GlobalMute> getGlobalMuteById(final Integer id);

    Observable<List<GlobalMute>> getGlobalMutesByIds(final Integer[] ids);

    Observable<Integer> getGlobalMuteCount();

    Observable<Boolean> isGlobalMuteEmpty();

    Observable<Boolean> insertGlobalMute(final GlobalMute globalMute);

    Observable<Boolean> insertAllGlobalMutes(final GlobalMute... globalMutes);

    Observable<Boolean> updateGlobalMute(final GlobalMute globalMute);

    Observable<Boolean> deleteGlobalMute(final GlobalMute globalMute);
    //</editor-fold>

    //<editor-fold desc="PredefinedLocations">
    Observable<List<PredefinedLocation>> getAllPredefinedLocations();

    Observable<PredefinedLocation> getPredefinedLocationById(final Integer id);

    Observable<List<PredefinedLocation>> getPredefinedLocationsByIds(final Integer[] ids);

    Observable<PredefinedLocation> getLastPredefinedLocation();

    Observable<Integer> getPredefinedLocationCount();

    Observable<Boolean> isPredefinedLocationEmpty();

    Observable<Boolean> insertPredefinedLocation(final PredefinedLocation predefinedLocation);

    Observable<Boolean> insertAllPredefinedLocation(final PredefinedLocation... predefinedLocations);

    Observable<Boolean> updatePredefinedLocation(final PredefinedLocation predefinedLocation);

    Observable<Boolean> deletePredefinedLocation(final PredefinedLocation predefinedLocation);
    //</editor-fold>

    //<editor-fold desc="SmartDevices">
    Observable<List<SmartDevice>> getAllSmartDevices();

    Observable<SmartDevice> getSmartDeviceById(final Integer id);

    Observable<SmartDevice> getLastSmartDevice();

    Observable<List<SmartDevice>> getSmartDevicesByIds(final Integer[] ids);

    Observable<Integer> getSmartDeviceCount();

    Observable<Boolean> isSmartDeviceEmpty();

    Observable<Boolean> insertSmartDevice(final SmartDevice smartDevice);

    Observable<Boolean> insertAllSmartDevices(final SmartDevice... smartDevices);

    Observable<Boolean> updateSmartDevice(final SmartDevice smartDevice);

    Observable<Boolean> deleteSmartDevice(final SmartDevice smartDevice);
    //</editor-fold>

    //<editor-fold desc="Triggers">
    Observable<List<Trigger>> getAllTriggers();

    Observable<Trigger> getTriggerById(final Integer id);

    Observable<List<Trigger>> getTriggersByIds(final Integer[] ids);

    Observable<List<Trigger>> getTriggersByEventId(final Integer id);

    Observable<Integer> getTriggerCount();

    Observable<Boolean> isTriggerEmpty();

    Observable<List<Trigger>> getTriggersBySmartDeviceId(final Integer id);

    Observable<Boolean> deleteTriggerBySmartDeviceId(Integer id);

    Observable<Boolean> deleteTriggerByEventId(Integer id);

    Observable<Boolean> insertTrigger(final Trigger trigger);

    Observable<Boolean> insertAllTriggers(final List<Trigger> triggers);

    Observable<Boolean> updateTrigger(final Trigger trigger);

    Observable<Boolean> deleteTrigger(final Trigger trigger);
    //</editor-fold>

    //<editor-fold desc="When">
    Observable<List<When>> getAllWhens();

    Observable<When> getWhenById(final Integer id);

    Observable<List<When>> getWhensByIds(final Integer[] ids);

    Observable<Integer> getWhenCount();

    Observable<Boolean> isWhenEmpty();

    Observable<Boolean> deleteWhenByEventId(Integer id);

    Observable<Boolean> insertWhen(final When when);

    Observable<Boolean> insertAllWhen(final When... whens);

    Observable<Boolean> updateWhen(final When when);

    Observable<Boolean> deleteWhen(final When when);
    //</editor-fold>

    //<editor-fold desc="Chain">
    Observable<List<Chain>> getAllChains();

    Observable<Integer> getChainCount();

    Observable<List<Chain>> getActiveChains();

    Observable<List<Chain>> getChainsByIds(final Integer[] ids);

    Observable<Chain> getChainById(final Integer id);

    Observable<Boolean> insertAllChains(final List<Chain> chains);

    Observable<Boolean> insertChain(final Chain chain);

    Observable<Boolean> updateChain(final Chain chain);

    Observable<Boolean> deleteChain(final Chain chain);

    //</editor-fold>

    //<editor-fold desc="Store">
    Observable<List<Store>> getAllStores();

    Observable<Integer> getStoreCount();

    Observable<List<Store>> getStoresByIds(final Integer[] ids);

    Observable<List<Store>> getFavoriteStores();

    Observable<Store> getStoreByName(final String storeName);

    Observable<Store> getStoreById(final Integer id);

    Observable<Boolean> insertAllStores(final Store... stores);

    Observable<Boolean> insertStore(final Store store);

    Observable<Boolean> updateStore(final Store store);

    Observable<Boolean> deleteStore(final Store store);
    //</editor-fold>

    //<editor-fold desc="EventWithData">
    Observable<EventWithData> getEventWithData(final Integer id);
    //</editor-fold>

    //<editor-fold desc="Hue Bridge">
    Observable<List<HueBridge>> getAllHueBridges();

    Observable<HueBridge> getLastInsertedHueBridge();

    Observable<List<HueBridge>> getAllHueBridgesBySmartDeviceId(Integer id);

    Observable<Boolean> deleteHueBridgeBySmartDeviceId(Integer id);

    Observable<Boolean> insertAllHueBridges(HueBridge... hueBridges);

    Observable<Boolean> insertHueBridge(HueBridge hueBridge);

    Observable<Boolean> updateHueBridge(HueBridge hueBridge);

    Observable<Boolean> deleteHueBridge(HueBridge hueBridge);
    //</editor-fold>

    //<editor-fold desc="Hue Light Bulb RGB">
    Observable<List<HueLightbulbRGB>> getRGBLightsByBridgeId(Integer id);

    Observable<List<HueLightbulbRGB>> getRGBHueLightsBySmartDeviceId(Integer id);

    Observable<Boolean> deleteRGBHueLightsBySmartDeviceId(Integer id);

    Observable<Boolean> insertAllRGBHueLightbulbs(HueLightbulbRGB... hueLightbulbRGBS);

    Observable<Boolean> insertRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB);

    Observable<Boolean> updateRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB);

    Observable<Boolean> deleteRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB);
    //</editor-fold>

    //<editor-fold desc="Hue Light Bulb White">
    Observable<List<HueLightbulbWhite>> getWhiteLightsByBridgeId(Integer id);

    Observable<List<HueLightbulbWhite>> getWhiteHueLightsBySmartDeviceId(Integer id);

    Observable<Boolean> deleteWhiteHueLightsBySmartDeviceId(Integer id);

    Observable<Boolean> insertAllWhiteHueLightbulbs(HueLightbulbWhite... hueLightbulbWhites);

    Observable<Boolean> insertWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite);

    Observable<Boolean> updateWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite);

    Observable<Boolean> deleteWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite);
    //</editor-fold>

    //<editor-fold desc="Nest Hub">
    Observable<List<NestHub>> getAllNestHubs();

    Observable<NestHub> getLastInsertedNestHub();

    Observable<List<NestHub>> getAllNestHubsBySmartDeviceId(Integer id);

    Observable<Boolean> deleteNestHubBySmartDeviceId(Integer id);

    Observable<Boolean> insertAllNestHubs(NestHub... nestHubs);

    Observable<Boolean> insertNestHub(NestHub nestHub);

    Observable<Boolean> updateNestHub(NestHub nestHub);

    Observable<Boolean> deleteNestHub(NestHub nestHub);
    //</editor-fold>

    //<editor-fold desc="Nest Thermostat">
    Observable<List<NestThermostat>> getThermostatByHubId(Integer id);

    Observable<List<NestThermostat>> getNestThermostatBySmartDeviceId(Integer id);

    Observable<Boolean> deleteNestThermostatBySmartDeviceId(Integer id);

    Observable<Boolean> insertAllNestThermostats(NestThermostat... nestThermostats);

    Observable<Boolean> insertNestThermostat(NestThermostat nestThermostat);

    Observable<Boolean> updateNestThermostat(NestThermostat nestThermostat);

    Observable<Boolean> deleteNestThermostat(NestThermostat nestThermostat);
    //</editor-fold>
}
