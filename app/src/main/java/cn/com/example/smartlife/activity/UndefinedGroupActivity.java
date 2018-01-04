package cn.com.example.smartlife.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseActivity;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.adapter.MultipleItemGroupInfoAdapter;
import cn.com.example.smartlife.message.DevicesInfo;
import cn.com.example.smartlife.message.DevicesInfoDao;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.message.MultipleItemDevicesInfo;

public class UndefinedGroupActivity extends BaseActivity {


    private static final String TAG = "UndefinedGroupActivity";
    private RecyclerView mRecycler_view;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_undefined_group;
    }



    @Override
    protected void findViews() {
        showBackButton();
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.undefined_group);
        mRecycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycler_view.setLayoutManager(new LinearLayoutManager(this));
      //  LocationAdapter locationAdapter = new LocationAdapter(App.getApp().getDevicesInfo()
     //           .queryBuilder()/*.where(DevicesInfoDao.Properties.Rs1.eq("00"))*/.build().list());
     //   mRecycler_view.setAdapter(locationAdapter);
        List<DevicesInfo> list = App.getApp().getDevicesInfo().queryBuilder().where(DevicesInfoDao.Properties.Rs1.eq("00")).build().list();
        List<MultipleItemDevicesInfo> devicesInfoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG, "findViews: "  +list.get(i).getDeviceID() );
            switch (list.get(i).getDeviceID()){
                case "01":
                    devicesInfoList.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_0,list.get(i)));
                    break;
                //窗帘
                case "03":
                    devicesInfoList.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_2,list.get(i)));
                    break;
                //调色灯
                case "05":
                    devicesInfoList.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_3,list.get(i)));
                    break;
                default:
                    devicesInfoList.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_1,list.get(i)));
            }

        }
        MultipleItemGroupInfoAdapter multipleItemGroupInfoAdapter = new MultipleItemGroupInfoAdapter(devicesInfoList);
        mRecycler_view.setAdapter(multipleItemGroupInfoAdapter);
    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {

    }

}
