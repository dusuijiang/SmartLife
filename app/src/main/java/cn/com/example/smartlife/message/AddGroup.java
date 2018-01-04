package cn.com.example.smartlife.message;

/**
 * Created by rd0404 on 2017/11/16.
 */

import org.litepal.crud.DataSupport;

/**
 * 1客厅
 2	阳台
 3	走廊
 4	主卧
 5	次卧
 6	书房
 7	娱乐厅
 8	卫浴间
 9	办公室
 10	厨房

 *
 *
 */
public class AddGroup extends DataSupport {
    private String name;

    private String id_name;

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddGroup( String id_name,String name) {
        this.name = name;
        this.id_name = id_name;
    }

    @Override
    public String toString() {
        return "AddGroup{" +
                "name='" + name + '\'' +
                ", id_name='" + id_name + '\'' +
                '}';
    }
}
