package cn.com.example.smartlife.message;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by rd0404 on 2017/11/29.
 */
//场景中的设备
@Entity
public class SceensDevices {
    @Id
    private Long id;
    private String mSn_0="00";
    private String mSn_1="00";
    private String mSn_2="00";
    private String mSn_3="00";
    private String sceensId="00";
    private String type_0="00";
    private String type_1="00";
    private String type_2="00";
    private String type_3="00";
    private String rs1="00";
    private String rs2="00";
    private String data0="00";
    private String data1="00";
    private String mSn="00";
    @Generated(hash = 666715869)
    public SceensDevices(Long id, String mSn_0, String mSn_1, String mSn_2,
            String mSn_3, String sceensId, String type_0, String type_1,
            String type_2, String type_3, String rs1, String rs2, String data0,
            String data1, String mSn) {
        this.id = id;
        this.mSn_0 = mSn_0;
        this.mSn_1 = mSn_1;
        this.mSn_2 = mSn_2;
        this.mSn_3 = mSn_3;
        this.sceensId = sceensId;
        this.type_0 = type_0;
        this.type_1 = type_1;
        this.type_2 = type_2;
        this.type_3 = type_3;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.data0 = data0;
        this.data1 = data1;
        this.mSn = mSn;
    }
    @Generated(hash = 209190844)
    public SceensDevices() {
    }
    public String getMSn_0() {
        return this.mSn_0;
    }
    public void setMSn_0(String mSn_0) {
        this.mSn_0 = mSn_0;
    }
    public String getMSn_1() {
        return this.mSn_1;
    }
    public void setMSn_1(String mSn_1) {
        this.mSn_1 = mSn_1;
    }
    public String getMSn_2() {
        return this.mSn_2;
    }
    public void setMSn_2(String mSn_2) {
        this.mSn_2 = mSn_2;
    }
    public String getMSn_3() {
        return this.mSn_3;
    }
    public void setMSn_3(String mSn_3) {
        this.mSn_3 = mSn_3;
    }
    public String getSceensId() {
        return this.sceensId;
    }
    public void setSceensId(String sceensId) {
        this.sceensId = sceensId;
    }
    public String getType_0() {
        return this.type_0;
    }
    public void setType_0(String type_0) {
        this.type_0 = type_0;
    }
    public String getType_1() {
        return this.type_1;
    }
    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }
    public String getType_2() {
        return this.type_2;
    }
    public void setType_2(String type_2) {
        this.type_2 = type_2;
    }
    public String getType_3() {
        return this.type_3;
    }
    public void setType_3(String type_3) {
        this.type_3 = type_3;
    }
    public String getRs1() {
        return this.rs1;
    }
    public void setRs1(String rs1) {
        this.rs1 = rs1;
    }
    public String getRs2() {
        return this.rs2;
    }
    public void setRs2(String rs2) {
        this.rs2 = rs2;
    }
    public String getData0() {
        return this.data0;
    }
    public void setData0(String data0) {
        this.data0 = data0;
    }
    public String getData1() {
        return this.data1;
    }
    public void setData1(String data1) {
        this.data1 = data1;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }
    public String getMSn() {
        return getMSn_2()+getMSn_3();
    }
    public void setMSn(String mSn) {
        this.mSn = mSn;
    }

    public String getType(){
        return getType_0()+getType_1()+getType_2()+getType_3();
    }
    @Override
    public String toString() {
        return "SceensDevices{" +
                "id=" + id +
                ", mSn_0='" + mSn_0 + '\'' +
                ", mSn_1='" + mSn_1 + '\'' +
                ", mSn_2='" + mSn_2 + '\'' +
                ", mSn_3='" + mSn_3 + '\'' +
                ", sceensId='" + sceensId + '\'' +
                ", type_0='" + type_0 + '\'' +
                ", type_1='" + type_1 + '\'' +
                ", type_2='" + type_2 + '\'' +
                ", type_3='" + type_3 + '\'' +
                ", rs1='" + rs1 + '\'' +
                ", rs2='" + rs2 + '\'' +
                ", data0='" + data0 + '\'' +
                ", data1='" + data1 + '\'' +
                ", mSn='" + mSn + '\'' +
                '}';
    }
}
