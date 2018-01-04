package cn.com.example.smartlife.message;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by rd0404 on 2017/11/22.
 */

//场景
@Entity
public class SceenBean {

    @Id
    private Long id;
    private String nature = "00";
    private String idName = "00";
    private String uId = "00";
    private String rs1 = "00";
    private String rs2 = "00"; //保留的时间
    private String nameLen = "00";
    private String name = "00";

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getRs1() {
        return rs1;
    }

    public void setRs1(String rs1) {
        this.rs1 = rs1;
    }

    public String getRs2() {
        return rs2;
    }

    public void setRs2(String rs2) {
        this.rs2 = rs2;
    }

    public String getNameLen() {
        return nameLen;
    }

    public void setNameLen(String nameLen) {
        this.nameLen = nameLen;
    }

    public String getName() {
        return name;
    }

    private static final String TAG = "Groups";

    @Generated(hash = 1515974824)
    public SceenBean(Long id, String nature, String idName, String uId, String rs1,
            String rs2, String nameLen, String name) {
        this.id = id;
        this.nature = nature;
        this.idName = idName;
        this.uId = uId;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.nameLen = nameLen;
        this.name = name;
    }

    public SceenBean(String nature, String idName, String uId, String rs1,
                     String rs2, String nameLen, String name) {
        this.nature = nature;
        this.idName = idName;
        this.uId = uId;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.nameLen = nameLen;
        this.name = name;
    }
    @Generated(hash = 214245371)
    public SceenBean() {
    }

    public void setName(String name) {
        StringBuilder ss = new StringBuilder(name);
        for (int i = ss.length() - 2; i >= 0; i = i - 2) {
            ss.insert(i, "%");
        }
        Log.e(TAG, "setName: " + ss );
        try {
            this.name = URLDecoder.decode(ss + "", "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    @Override
    public String toString() {
        return "Groups{" +
                "nature='" + nature + '\'' +
                ", idName='" + idName + '\'' +
                ", uId='" + uId + '\'' +
                ", rs1='" + rs1 + '\'' +
                ", rs2='" + rs2 + '\'' +
                ", nameLen='" + nameLen + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getUId() {
        return this.uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

}

