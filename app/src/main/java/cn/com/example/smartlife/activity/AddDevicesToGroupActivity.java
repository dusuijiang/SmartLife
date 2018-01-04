package cn.com.example.smartlife.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseActivity;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.adapter.AddDevicesToGroupAdapter;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.DevicesInfo;
import cn.com.example.smartlife.message.DevicesInfoDao;
import cn.com.example.smartlife.message.MessageData;

public class AddDevicesToGroupActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String mIdName;
    private DevicesInfo mDevicesInfo;
    private List<DevicesInfo> mDevicesInfoList;
    private AddDevicesToGroupAdapter mAddDevicesToGroupAdapter;
    private static final String TAG = "AddDevicesToGroupActivi";
    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_devices_to_group;
    }

    @Override
    protected void findViews() {
        showBackButton();
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("未定义分组");
        mIdName = getIntent().getStringExtra("mIdName");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.e(TAG, "findViews: " +mIdName );
        initData();

        mAddDevicesToGroupAdapter = new AddDevicesToGroupAdapter(mDevicesInfoList);
        mRecyclerView.setAdapter(mAddDevicesToGroupAdapter);
        mAddDevicesToGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               List<DevicesInfo> list = adapter.getData();
               mDevicesInfo = list.get(position);
               if (mDevicesInfo != null){
                   String id = mIdName + "+01+" + mDevicesInfo.getData_0()+ mDevicesInfo.getData_1()+ mDevicesInfo.getSn();
                   Log.e(TAG, "onItemClick: " + id );
                   DataCmd.getInstance().addDeviceToGroup(id);
               }


           }
       });
    }

    private void initData() {
        mDevicesInfoList = App.getApp().getDevicesInfo()
                .queryBuilder().where(DevicesInfoDao.Properties.Rs1.eq("00")).build().list();
        if (mAddDevicesToGroupAdapter != null){
            mAddDevicesToGroupAdapter.getData().clear();
            mAddDevicesToGroupAdapter.addData(mDevicesInfoList);
        }

    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {
            switch (messageData.getCmd()){
                case "07":
                    //返回成功的数据后 修改设备的组ID，
                    if (mDevicesInfo != null){
                        mDevicesInfo.setRs1(mIdName);
                        App.getApp().getDevicesInfo().update(mDevicesInfo);
                        initData();
                    }

                    break;
            }
    }
}
