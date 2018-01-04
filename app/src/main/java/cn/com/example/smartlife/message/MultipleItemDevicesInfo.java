package cn.com.example.smartlife.message;


import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by rd0404 on 2017/11/21.
 */

public class MultipleItemDevicesInfo implements MultiItemEntity {

    private int itemType;
    public static final  int STA_0 = 0;
    public static final  int STA_1 = 1;
    public static final  int STA_2 = 2;
    public static final  int STA_3 = 3;
    public static final  int STA_4 = 4;
    public static final  int STA_5 = 5;
    public static final  int STA_6 = 6;
    public static final  int STA_7 = 7;
    public static final  int STA_8 = 8;
    public static final  int STA_9 = 9;
    @Override
    public int getItemType() {
        return itemType;
    }

    private SceensDevices mSceensDevices;
    private DevicesInfo devicesInfo;
    public MultipleItemDevicesInfo(int itemType, DevicesInfo devicesInfo){
        this.itemType = itemType;
        this.devicesInfo = devicesInfo;
    }

    public MultipleItemDevicesInfo(int itemType, SceensDevices sceensDevices, DevicesInfo devicesInfo) {
        this.itemType = itemType;
        mSceensDevices = sceensDevices;
        this.devicesInfo = devicesInfo;
    }

    public SceensDevices getSceensDevices() {
        return mSceensDevices;
    }

    public void setSceensDevices(SceensDevices sceensDevices) {
        mSceensDevices = sceensDevices;
    }

    public DevicesInfo getDevicesInfo() {
        return devicesInfo;
    }

    public void setDevicesInfo(DevicesInfo devicesInfo) {
        this.devicesInfo = devicesInfo;
    }
}
