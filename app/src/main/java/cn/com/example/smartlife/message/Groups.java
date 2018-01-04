package cn.com.example.smartlife.message;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by rd0404 on 2017/11/20.
 */

/**
 * data[1] = group_info->Nature;		//区域分组类别
 data[2] = group_info->id; 		//组ID
 data[3] = group_info->Uid ; 		//预留
 data[4] = group_info->Rs1 ; 		//预留
 data[5] = group_info->Rs2 ; 		//预留
 data[6] = group_info->Namelen;		//预留
 data[7-23]				//区域分组名称

 *
 */
//分组
@Entity
public class Groups{
    @Id
    private Long id;
    private String nature ="00";
    @Unique
    private String idName ="00";
    private String uId ="00";
    private String rs1 ="00";
    private String rs2 ="00";
    private String nameLen ="00";
    private String name = "00";

    @Generated(hash = 1815291690)
    public Groups(Long id, String nature, String idName, String uId, String rs1,
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

    @Generated(hash = 893039872)
    public Groups() {
    }

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

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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
    public void setName(String name) {
        StringBuilder ss = new StringBuilder(name);
        for(int i =ss.length()-2;i>=0;i=i-2){
            ss.insert(i, "%");
        }
        try {
            this.name = URLDecoder.decode(ss+"", "UTF-8");
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
