package cn.com.example.smartlife;


import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.contact.core.query.PinYin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;

import org.litepal.LitePalApplication;

import cn.com.example.smartlife.im.DemoCache;
import cn.com.example.smartlife.im.NimInitManager;
import cn.com.example.smartlife.im.NimSDKOptionConfig;
import cn.com.example.smartlife.im.config.SystemUtil;
import cn.com.example.smartlife.im.config.preference.Preferences;
import cn.com.example.smartlife.im.config.preference.UserPreferences;
import cn.com.example.smartlife.message.AddSceenDao;
import cn.com.example.smartlife.message.DaoMaster;
import cn.com.example.smartlife.message.DaoSession;
import cn.com.example.smartlife.message.DevicesInfoDao;
import cn.com.example.smartlife.message.GroupsDao;
import cn.com.example.smartlife.message.SceenBeanDao;
import cn.com.example.smartlife.message.SceensDevicesDao;
import cn.com.example.smartlife.utils.ExceptionCrashHandler;

/**
 * Created by rd0404 on 2017/11/6.
 */

public class App extends LitePalApplication {

    private static App mApp;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Utils.init(this);
        MultiDex.install(this);
        DemoCache.setContext(this);
        initDb();
        ExceptionCrashHandler.getInstance().init(this);
        // 注册自定义推送消息处理，这个是可选项
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions());
        // crash handler
       // AppCrashHandler.getInstance(this);
        // 以下逻辑只在主进程初始化时执行
        if (inMainProcess()) {
            // 初始化红包模块，在初始化UIKit模块之前执行
            // init pinyin
            PinYin.init(this);
            PinYin.validate();
            // 初始化UIKit模块
            initUIKit();
            // 初始化消息提醒
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
            // 云信sdk相关业务初始化
            NimInitManager.getInstance().init(true);
        }
        //消息提醒
        NIMClient.toggleNotification(false);

    }

    public static App getApp() {
        return mApp;
    }
    private void initDb() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "smartlife.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();

    }

    public DevicesInfoDao getDevicesInfo(){
        DevicesInfoDao devices = mDaoSession.getDevicesInfoDao();
        return devices;
    }
    public GroupsDao getGroups(){
        GroupsDao devices = mDaoSession.getGroupsDao();
        return devices;
    }
    public SceenBeanDao getSceens(){
        SceenBeanDao devices = mDaoSession.getSceenBeanDao();
        return devices;
    }
    public AddSceenDao getAddSceen(){
        AddSceenDao devices = mDaoSession.getAddSceenDao();
        return devices;
    }
    private static final String TAG = "App";
    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        Log.e(TAG, "getLoginInfo: "  + account + "   "+token );
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    private void initUIKit() {
        // 初始化，使用 uikit 默认的用户信息提供者
        NimUIKit.init(this);

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        //NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // IM 会话窗口的定制初始化。
     //   SessionHelper.init();

        // 聊天室聊天窗口的定制初始化。
     //   ChatRoomSessionHelper.init();

        // 通讯录列表定制初始化
    //    ContactHelper.init();

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        //NimUIKit.setCustomPushContentProvider(new DemoPushContentProvider());

      //  NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }



    public SceensDevicesDao getSceensDevicesSn(){
        SceensDevicesDao devices = mDaoSession.getSceensDevicesDao();
        return devices;
    }

}

