package p8project.sw801.data.local.RelationEntity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;

public class SmartDeviceWithData {

    @Embedded
    public SmartDevice smartDevice;

    @Relation(parentColumn = "id", entityColumn = "smartDeviceId", entity = HueBridge.class)
    public List<HueBridge> hueBridgeList;

    @Relation(parentColumn = "id", entityColumn = "smartDeviceId", entity = NestHub.class)
    public List<NestHub> nestHubList;

    @Relation(parentColumn = "id", entityColumn = "smartDeviceId", entity = HueLightbulbWhite.class)
    public List<HueLightbulbWhite> hueLightbulbWhiteList;

    @Relation(parentColumn = "id", entityColumn = "smartDeviceId", entity = NestThermostat.class)
    public List<NestThermostat> nestThermostatList;

}
