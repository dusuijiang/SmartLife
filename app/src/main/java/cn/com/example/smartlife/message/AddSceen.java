package cn.com.example.smartlife.message;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by rd0404 on 2017/11/22.
 */

@Entity
public class AddSceen {
    private long id;
    private String name;

    private String id_name;

    @Generated(hash = 177959511)
    public AddSceen(long id, String name, String id_name) {
        this.id = id;
        this.name = name;
        this.id_name = id_name;
    }

    @Generated(hash = 1602639018)
    public AddSceen() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_name() {
        return this.id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }
}
