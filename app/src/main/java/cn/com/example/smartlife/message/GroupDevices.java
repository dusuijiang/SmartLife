package cn.com.example.smartlife.message;

import org.litepal.crud.DataSupport;

/**
 * Created by rd0404 on 2017/11/16.
 */

//区域分组情况
public class GroupDevices extends DataSupport {

    private String name;
    private int num_devices;
    private boolean on;

    public GroupDevices(String name, int num_devices, boolean on) {
        this.name = name;
        this.num_devices = num_devices;
        this.on = on;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_devices() {
        return num_devices;
    }

    public void setNum_devices(int num_devices) {
        this.num_devices = num_devices;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    @Override
    public String toString() {
        return "GroupDevices{" +
                "name='" + name + '\'' +
                ", num_devices=" + num_devices +
                ", on=" + on +
                '}';
    }
}
