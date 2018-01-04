package cn.com.example.smartlife.im.login;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.plugin.LoginSyncDataStatusObserver;

import cn.com.example.smartlife.im.DemoCache;

/**
 * 注销帮助类
 * Created by huangjun on 2015/10/8.
 */
public class LogoutHelper {
    public static void logout() {
        // 清理缓存&注销监听&清除状态
        NimUIKit.logout();
       // ChatRoomHelper.logout();
        DemoCache.clear();
        LoginSyncDataStatusObserver.getInstance().reset();
        DropManager.getInstance().destroy();
      //  NIMRedPacketClient.clear();
    }
}
