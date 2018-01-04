package cn.com.example.smartlife.message;

/**
 * Created by rd0404 on 2017/10/30.
 */

/**
 * Byte[1]= data[0] = dev_info->Sn>>24; //设备唯一索引（地址和设备类型的合成）
 data[1] = dev_info->Sn>>16;
 data[2] = dev_info->Sn>>8;
 data[3] = dev_info->Sn;
 data[4] = dev_info->ShortAddr>>8; //设备地址
 data[5] = dev_info->ShortAddr;
 data[6] = dev_info->Endpoint;	//设备短地址，预留
 data[7] = dev_info->DeviceID;	//设备类型
 data[8] = dev_info->DevState;	//设备状态，开关等设备的控制，基本上遵循，00关，01开，02停；
 data[9] = dev_info->OnlineState;	//在线状态，0不在，1在线。
 data[10] = dev_info->Rs1;			//设备所在的区域分组ID。对应 区域分组。
 data[11] = dev_info->Rs3;			//设备是否布防，1布防，0撤防
 data[12] = dev_info->ProfileID>>8;		//设备信号强度
 data[13] = dev_info->ProfileID;  		//预留
 data[14] = ZoneType;				//预留
 data[15] = 0;	//预留
 data[16] = 0;	//预留
 data[17] = 0;	//预留
 data[18] = 0;	//预留

 data[19-35]  	//设备名字；(因为是循环体，这里是固定长度)

 *
 *
 *
 *     News news = new News();
 news.setTitle("这是一条新闻标题");
 news.setContent("这是一条新闻内容");
 news.setPublishDate(new Date());
 news.saveThrows();
 *
 */

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * 00008C2A
 002A
 002300010B0100B50000000000E58EA8E688BFE7839FE99BBE00000000
 */
//设备详情
@Entity
public class DevicesInfo{
    @Id
    private Long id;
    String data_0="00";
    String data_1="00";
    String data_2="00";  //
    String data_3="00";  //

    String shortAddr_0="00";
    String shortAddr_1="00";

    String endpoint="00";
    String deviceID="00"; //
    String devState="00";   ///
    String onlineState="00";///

    String rs1="00";////
    String rs3="00";////

    String profileID_0="00";
    String profileID_1="00";
    String zoneType ="00";
    String data_14="00";
    String data_15="00";
    String data_16="00";
    String data_17="00";

    public String getData_0() {
        return data_0;
    }

    public void setData_0(String data_0) {
        this.data_0 = data_0;
    }

    public String getData_1() {
        return data_1;
    }

    public void setData_1(String data_1) {
        this.data_1 = data_1;
    }

    public String getData_2() {
        return data_2;
    }

    public void setData_2(String data_2) {
        this.data_2 = data_2;
    }

    public String getData_3() {
        return data_3;
    }

    public void setData_3(String data_3) {
        this.data_3 = data_3;
    }

    public String getShortAddr_0() {
        return shortAddr_0;
    }

    public void setShortAddr_0(String shortAddr_0) {
        this.shortAddr_0 = shortAddr_0;
    }

    public String getShortAddr_1() {
        return shortAddr_1;
    }

    public void setShortAddr_1(String shortAddr_1) {
        this.shortAddr_1 = shortAddr_1;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevState() {
        return devState;
    }

    public void setDevState(String devState) {
        this.devState = devState;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public String getRs1() {
        return rs1;
    }

    public void setRs1(String rs1) {
        this.rs1 = rs1;
    }

    public String getRs3() {
        return rs3;
    }

    public void setRs3(String rs3) {
        this.rs3 = rs3;
    }

    public String getProfileID_0() {
        return profileID_0;
    }

    public void setProfileID_0(String profileID_0) {
        this.profileID_0 = profileID_0;
    }

    public String getProfileID_1() {
        return profileID_1;
    }

    public void setProfileID_1(String profileID_1) {
        this.profileID_1 = profileID_1;
    }

    public String getZoneType() {
        return zoneType;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
    }

    public String getData_14() {
        return data_14;
    }

    public void setData_14(String data_14) {
        this.data_14 = data_14;
    }

    public String getData_15() {
        return data_15;
    }

    public void setData_15(String data_15) {
        this.data_15 = data_15;
    }

    public String getData_16() {
        return data_16;
    }

    public void setData_16(String data_16) {
        this.data_16 = data_16;
    }

    public String getData_17() {
        return data_17;
    }

    public void setData_17(String data_17) {
        this.data_17 = data_17;
    }

    private String name; // 19-35;

    public String getName(){
        return name;
    }


    public String getSn(){
        return getData_2()+getData_3();
    }

    private static final String TAG = "DevicesInfo";

    @Generated(hash = 204923554)
    public DevicesInfo(Long id, String data_0, String data_1, String data_2,
            String data_3, String shortAddr_0, String shortAddr_1, String endpoint,
            String deviceID, String devState, String onlineState, String rs1,
            String rs3, String profileID_0, String profileID_1, String zoneType,
            String data_14, String data_15, String data_16, String data_17,
            String name) {
        this.id = id;
        this.data_0 = data_0;
        this.data_1 = data_1;
        this.data_2 = data_2;
        this.data_3 = data_3;
        this.shortAddr_0 = shortAddr_0;
        this.shortAddr_1 = shortAddr_1;
        this.endpoint = endpoint;
        this.deviceID = deviceID;
        this.devState = devState;
        this.onlineState = onlineState;
        this.rs1 = rs1;
        this.rs3 = rs3;
        this.profileID_0 = profileID_0;
        this.profileID_1 = profileID_1;
        this.zoneType = zoneType;
        this.data_14 = data_14;
        this.data_15 = data_15;
        this.data_16 = data_16;
        this.data_17 = data_17;
        this.name = name;
    }

    @Generated(hash = 368249028)
    public DevicesInfo() {
    }
    public void setName(String names){
        StringBuilder ss = new StringBuilder(names);
        for(int i =ss.length()-2;i>=0;i=i-2){
            ss.insert(i, "%");
        }
        try {
            this.name = URLDecoder.decode(ss+"", "UTF-8");
            Log.e(TAG, "setName: " +name );
        } catch (UnsupportedEncodingException e) {
           // e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "DevicesInfo{" +
                "data_0='" + data_0 + '\'' +
                ", data_1='" + data_1 + '\'' +
                ", data_2='" + data_2 + '\'' +
                ", data_3='" + data_3 + '\'' +
                ", shortAddr_0='" + shortAddr_0 + '\'' +
                ", shortAddr_1='" + shortAddr_1 + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", devState='" + devState + '\'' +
                ", onlineState='" + onlineState + '\'' +
                ", rs1='" + rs1 + '\'' +
                ", rs3='" + rs3 + '\'' +
                ", profileID_0='" + profileID_0 + '\'' +
                ", profileID_1='" + profileID_1 + '\'' +
                ", zoneType='" + zoneType + '\'' +
                ", data_14='" + data_14 + '\'' +
                ", data_15='" + data_15 + '\'' +
                ", data_16='" + data_16 + '\'' +
                ", data_17='" + data_17 + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
