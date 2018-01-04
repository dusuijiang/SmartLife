package cn.com.example.smartlife.message;

public class DataCmd {

    private final MessageData instance;

    public static DataCmd getInstance() {

        return DataCmdHolder.INSTANCE;
    }

    private static class DataCmdHolder {
        public static final DataCmd INSTANCE = new DataCmd();
    }

    private DataCmd(){
        instance = MessageData.getInstance();
    }

    //
    public void getAllDevices(){
        instance.setCmd("02").setData("00").sendMessages();
    }

    //
    public void getAllowConnectGateway(){
        instance.setCmd("03").setData("00").sendMessages();
    }
    //
    public void setDevicesName(String id,String name){
        instance.setCmd("04").setData(name).sendMessages();
    }
    //6.	删除设备  06
    public void deleteDevicesPoint(String sn){
        instance.setCmd("06").setData(sn).sendMessages();
    }

    //7 增加分组  0a
    public void addDevicesGroup(String name){
        instance.setCmd("0A").setData(name).sendMessages();
    }

    public void updataDevicesGroupName(String name){
        instance.setCmd("0B").setData(name).sendMessages();
    }

    public void deleteGroup(String id){
        instance.setCmd("09").setData(id).sendMessages();
    }

    //data:"分组ID+设备总数+设备ID"
    public void addDeviceToGroup(String groupId){
        instance.setCmd("07").setData(groupId).sendMessages();
    }
    //data:"00+设备总数+设备ID"
    public void deleteDevicesForGroup(String groupId){
        instance.setCmd("08").setData(groupId).sendMessages();
    }
    //data:"00+设备总数+设备ID"
    public void deleteDevicesForSceens(String groupId){
        instance.setCmd("4A").setData(groupId).sendMessages();
    }
    public void addScene(String sceneId){
        instance.setCmd("0C").setData(sceneId).sendMessages();
    }

    public void updataScene(String sceneId,String name){
        instance.setCmd("10").setData(sceneId).sendMessages();
    }

    public void deleteScene(String sceneId){
        instance.setCmd("0F").setData(sceneId).sendMessages();
    }

    public void addDevicesToScene(String sceneId){
        instance.setCmd("0E").setData(sceneId).sendMessages();
    }

    public void runScene(String sceneId){
        instance.setCmd("0D").setData(sceneId).sendMessages();
    }

    public void deleteDevicesForScene(String sceneId,int num,String deviceId){
        instance.setCmd("11").setData(sceneId).sendMessages();
    }

    public void getAllGroupInfo(){
        instance.setCmd("12").setData("00").sendMessages();
    }

    public void setSwitchDevices(String sn){
        instance.setCmd("19").setData(sn).sendMessages();
    }

    public void getOneSceenDevices(String sceenId){
        instance.setCmd("46").setData(sceenId).sendMessages();
    }

    public void setOneDevicesTime(String sceenId){
        instance.setCmd("4B").setData(sceenId).sendMessages();
    }

    public void getAllSceen(){
        instance.setCmd("13").setData("00").sendMessages();
    }

    public void setCloseGroups(String groupsId){
        instance.setCmd("52").setData(groupsId).sendMessages();
    }
}
