package cn.com.example.smartlife.message;

/**
 * Created by rd0404 on 2017/10/30.
 */

import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.text.SimpleDateFormat;

import cn.com.example.smartlife.im.config.preference.Preferences;

/**
 * News news = new News();
 * news.setTitle("这是一条新闻标题");
 * news.setContent("这是一条新闻内容");
 * news.setPublishDate(new Date());
 * news.save();
 */

public class MessageData {

    /**
     * gatewayid : 305419896
     * fromaccount : admin
     * cmd : 02
     * time : 202361113233720
     * rand : 000009E7
     * md5 : 7A783C375A14AFC601E494EDE2DA974B
     * data : 0500000412002AB50100000001000000000000000000000000000000000000000000000000000413002BB50100000001000000000000000000000000000000000000000000000000000415002DB50100000001000000000000000000000000000000000000000000000000000416002EB50100000001000000000000000000000000000000000000000000000000000417002FB501000000010000000000000000000000000000000000000000000000
     */

    // AC + 版本 + 长度  + json  + BC
    private int gatewayid;
    private String fromaccount;
    private String cmd;
    private String time;
    private String rand;
    private String md5;
    private String data;
    public static String CONEST_KEY = "59200623";

    public static MessageData getInstance() {

        return MessageDataHolder.INSTANCE;
    }

    private static class MessageDataHolder {
        public static final MessageData INSTANCE = new MessageData();
    }

    private MessageData() {

    }

    public MessageData(int gatewayid, String fromaccount, String cmd, String time, String rand, String data) {
        this.gatewayid = gatewayid;
        this.fromaccount = fromaccount;
        this.cmd = cmd;
        this.time = time;
        this.rand = rand;
        this.data = data;
        this.md5 = fromaccount + cmd + time + rand + CONEST_KEY;
    }

    public MessageData(String cmd, String time, String data) {
        this.cmd = cmd;
        this.time = time;
        this.data = data;
        this.md5 = fromaccount + cmd + time + rand + CONEST_KEY;
    }

    public int getGatewayid() {
        return gatewayid;
    }

    public MessageData setGatewayid(int gatewayid) {
        this.gatewayid = gatewayid;
        return this;
    }

    public String getFromaccount() {
        return fromaccount;
    }

    public MessageData setFromaccount(String fromaccount) {
        this.fromaccount = fromaccount;
        return this;
    }

    public String getCmd() {
        return cmd;
    }

    public MessageData setCmd(String cmd) {
        this.gatewayid = 12345678;
        fromaccount = Preferences.getUserAccount();
        time = TimeUtils.getNowString(new SimpleDateFormat("yyyyMMddHHmmss"));
        this.cmd = cmd;
        this.rand = "12345678";
        return this;
    }

    public String getTime() {
        return time;
    }

    public MessageData setTime(String time) {
        this.time = time;
        return this;
    }

    public String getRand() {
        return rand;
    }

    public MessageData setRand(String rand) {
        this.rand = rand;
        return this;
    }

    public String getMd5() {
        return md5;
    }

    public MessageData setMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    public String getData() {
        return data;
    }

    public MessageData setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        md5 = EncryptUtils.encryptMD5ToString(fromaccount + cmd + time + rand + "59200623");
        return "AC" + "11234" + "{\"" + "gatewayid" + "\":" + gatewayid + ",\"" + "fromaccount" + "\":" + "\"" + fromaccount + "\"" + ",\"" + "cmd" + "\":"  +  "\"" +cmd + "\"" + ",\"" + "time" + "\":" + "\"" + time + "\"" + ",\"" + "rand" + "\":" + "\"" + rand + "\"" + ",\"" + "md5" + "\":" + "\"" + md5 + "\"" + ",\"" + "data" + "\":" + "\"" + data + "\"\r\n"  + "}BC";
    }

    public void sendMessages(String account, String json) {
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, json);
        NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
    }

    private static final String TAG = "MessageData";
    public void sendMessages() {
        Log.e(TAG, "sendMessages: " +toString() );
        String account = "62F8B7413844993D";
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, toString());
        NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
    }
}
