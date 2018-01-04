package cn.com.example.smartlife.Base;

import android.util.Log;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.message.DevicesInfo;
import cn.com.example.smartlife.message.Groups;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.message.SceenBean;
import cn.com.example.smartlife.message.SceensDevices;
import cn.com.example.smartlife.utils.TimeUtils;

import static java.lang.Integer.parseInt;

/**
 * Created by rd0404 on 2017/11/21.
 */

public class ObserverEvent {
    public ObserverEvent() {
        registerObservers(true);
    }

    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(incomingMessageObserver, register);
    }

    /**
     * 消息接收观察者
     */
    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            if (messages == null || messages.isEmpty()) {
                return;
            }
            onEventData(messages);
        }
    };

    private MessageEvent mMessageEvent;

    public void setMessageEvent(MessageEvent messageEvent) {
        this.mMessageEvent = messageEvent;
    }

    public interface MessageEvent {
        void Message(MessageData messageData, String[] arr, String data);
    }

    private static final String TAG = "ObserverEvent";

    private synchronized void onEventData(List<IMMessage> message) {
        String s = message.get(0).getContent();
        s = s.substring(s.indexOf("{"), s.indexOf("}") + 1);
        Gson gson = new Gson();
        MessageData messageData = gson.fromJson(s, MessageData.class);
        String data = messageData.getData();
        int len = parseInt(data.charAt(0) + "" + data.charAt(1), 16);
        data = data.substring(2);
        String[] ss = new String[len];
        mMessageEvent.Message(messageData, ss, data);
        switch (messageData.getCmd()) {
            //设备详情
            case "02":
                LinkedHashMap<String, DevicesInfo> devicesInfoLinkedHashMap = new LinkedHashMap<>();
                List<DevicesInfo> list = App.getApp().getDevicesInfo().queryBuilder().build().list();
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        devicesInfoLinkedHashMap.put(list.get(i).getSn(), list.get(i));
                    }
                }
                for (int i = 0; i < len; i++) {
                    ss[i] = data.substring(i * 70, (i + 1) * 70);
                }
                for (int i = 0; i < ss.length; i++) {
                    DevicesInfo devicesInfo = new DevicesInfo();
                    devicesInfo.setData_0(ss[i].substring(0, 2));
                    devicesInfo.setData_1(ss[i].substring(2, 4));
                    devicesInfo.setData_2(ss[i].substring(4, 6));
                    devicesInfo.setData_3(ss[i].substring(6, 8));

                    devicesInfo.setShortAddr_0(ss[i].substring(8, 10));
                    devicesInfo.setShortAddr_1(ss[i].substring(10, 12));

                    devicesInfo.setEndpoint(ss[i].substring(12, 14));
                    devicesInfo.setDeviceID(ss[i].substring(14, 16));
                    devicesInfo.setDevState(ss[i].substring(16, 18));
                    devicesInfo.setOnlineState(ss[i].substring(18, 20));

                    devicesInfo.setRs1(ss[i].substring(20, 22));
                    devicesInfo.setRs3(ss[i].substring(22, 24));
                    devicesInfo.setProfileID_0(ss[i].substring(24, 26));
                    devicesInfo.setProfileID_1(ss[i].substring(26, 28));
                    devicesInfo.setZoneType(ss[i].substring(28, 30));
                    devicesInfo.setData_14(ss[i].substring(30, 32));
                    devicesInfo.setData_15(ss[i].substring(32, 34));
                    devicesInfo.setData_16(ss[i].substring(34, 36));
                    devicesInfo.setData_17(ss[i].substring(36, 38));
                    devicesInfo.setName(ss[i].substring(38, 70));
                    devicesInfoLinkedHashMap.put(devicesInfo.getSn(), devicesInfo);
                }
                App.getApp().getDevicesInfo().deleteAll();
                for (Map.Entry<String, DevicesInfo> entry : devicesInfoLinkedHashMap.entrySet()) {
                    App.getApp().getDevicesInfo().insert(entry.getValue());
                }
                break;
            //获取分组
            case "12":
                LinkedHashMap<String, Groups> groupsInfoLinkedHashMap = new LinkedHashMap<>();
                List<Groups> groupList = App.getApp().getGroups().queryBuilder().build().list();
                if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        groupsInfoLinkedHashMap.put(groupList.get(i).getIdName(), groupList.get(i));
                    }
                }
                for (int i = 0; i < ss.length; i++) {
                    ss[i] = data.substring(i * 44, (i + 1) * 44);
                }
                for (int i = 0; i < ss.length; i++) {
                    Groups groups = new Groups();
                    groups.setNature(ss[i].substring(0, 2));
                    groups.setIdName(ss[i].substring(2, 4));
                    groups.setuId(ss[i].substring(4, 6));
                    groups.setRs1(ss[i].substring(6, 8));
                    groups.setRs2(ss[i].substring(8, 10));
                    groups.setNameLen(ss[i].substring(10, 12));
                    groups.setName(ss[i].substring(12, 44));
                    groupsInfoLinkedHashMap.put(groups.getIdName(), groups);
                }
                App.getApp().getGroups().deleteAll();
                for (Map.Entry<String, Groups> entry : groupsInfoLinkedHashMap.entrySet()) {
                    App.getApp().getGroups().insert(entry.getValue());
                }
                break;
            //获取场景
            case "13":
                LinkedHashMap<String, SceenBean> sceenBeanLinkedHashMap = new LinkedHashMap<>();
                List<SceenBean> sceenBeanList = App.getApp().getSceens().queryBuilder().build().list();
                if (sceenBeanList.size() > 0) {
                    for (int i = 0; i < sceenBeanList.size(); i++) {
                        sceenBeanLinkedHashMap.put(sceenBeanList.get(i).getIdName(), sceenBeanList.get(i));
                    }
                }
                for (int i = 0; i < ss.length; i++) {
                    ss[i] = data.substring(i * 44, (i + 1) * 44);
                }
                for (int i = 0; i < ss.length; i++) {
                    SceenBean sceenBean = new SceenBean();
                    sceenBean.setNature(ss[i].substring(0, 2));
                    sceenBean.setIdName(ss[i].substring(2, 4));
                    sceenBean.setUId(ss[i].substring(4, 6));
                    sceenBean.setRs1(ss[i].substring(6, 8));
                    sceenBean.setRs2(ss[i].substring(8, 10));
                    sceenBean.setNameLen(ss[i].substring(10, 12));
                    sceenBean.setName(ss[i].substring(12, 44));
                    sceenBeanLinkedHashMap.put(sceenBean.getIdName(), sceenBean);
                }
                App.getApp().getSceens().deleteAll();
                for (Map.Entry<String, SceenBean> entry : sceenBeanLinkedHashMap.entrySet()) {
                    Log.e(TAG, "onEventData: " + entry.getKey() );
                    App.getApp().getSceens().insert(entry.getValue());
                }
                break;
            //场景中的设备详情
            case "46":
                if (messageData.getData().length() < 3){
                    return;
                }
                LinkedHashMap<String, SceensDevices> sceendevices = new LinkedHashMap<>();
                List<SceensDevices> sceensList = App.getApp().getSceensDevicesSn().queryBuilder().list();
                if (sceensList.size() > 0) {
                    for (int i = 0; i < sceensList.size(); i++) {
                        sceendevices.put(sceensList.get(i).getMSn(), sceensList.get(i));
                    }
                }
                for (int i = 0; i < ss.length; i++) {
                    ss[i] = data.substring(i * 26, (i + 1) * 26);
                }
                for (int i = 0; i < ss.length; i++) {
                    SceensDevices sceensDevices = new SceensDevices();
                    sceensDevices.setMSn_0(ss[i].substring(0, 2));
                    sceensDevices.setMSn_1(ss[i].substring(2, 4));
                    sceensDevices.setMSn_2(ss[i].substring(4, 6));
                    sceensDevices.setMSn_3(ss[i].substring(6, 8));

                    sceensDevices.setSceensId(ss[i].substring(8, 10));

                    sceensDevices.setType_0(ss[i].substring(10, 12));
                    sceensDevices.setType_1(ss[i].substring(12, 14));
                    sceensDevices.setType_2(ss[i].substring(14, 16));
                    sceensDevices.setType_3(ss[i].substring(16, 18));

                    sceensDevices.setRs1(ss[i].substring(18, 20));
                    int time = Integer.parseInt(ss[i].substring(20, 22),16);
                    sceensDevices.setRs2(TimeUtils.setDate(time));
                   // sceensDevices.setData0(ss[i].substring(22, 24));
                    //sceensDevices.setData1(ss[i].substring(24, 26));
                    sceendevices.put(sceensDevices.getMSn(), sceensDevices);
                }
                App.getApp().getSceensDevicesSn().deleteAll();
                for (Map.Entry<String, SceensDevices> entry : sceendevices.entrySet()) {
                    App.getApp().getSceensDevicesSn().insert(entry.getValue());
                }
                break;
        }
    }
}
