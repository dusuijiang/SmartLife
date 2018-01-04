package cn.com.example.smartlife.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseActivity;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.adapter.AddDevicesToGroupAdapter;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.DevicesInfo;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.message.SceensDevices;
import cn.com.example.smartlife.message.SceensDevicesDao;

public class AddDevicesToSceensActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String mIdName;
    private DevicesInfo mDevicesInfo;
    private List<DevicesInfo> mDevicesInfoList;
    private AddDevicesToGroupAdapter mAddDevicesToGroupAdapter;
    private static final String TAG = "AddDevicesToSceensActiv";
    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_devices_to_sceens;
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
        initData("findViews");

        mAddDevicesToGroupAdapter = new AddDevicesToGroupAdapter(mDevicesInfoList);
        mRecyclerView.setAdapter(mAddDevicesToGroupAdapter);
        mAddDevicesToGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<DevicesInfo> list = adapter.getData();
                mDevicesInfo = list.get(position);
                //设备ID 四个字节，设备状态4个字节，接下俩1字节，代表状态保持时间(S),预留1字节；每个设备总共十字节；
                if (mDevicesInfo != null){
                    String id = mIdName + "+01+" + mDevicesInfo.getData_0()+ mDevicesInfo.getData_1()+ mDevicesInfo.getSn()+"000000"+mDevicesInfo.getDevState() ;
                    Log.e(TAG, "onItemClick: " + id );
                    DataCmd.getInstance().addDevicesToScene(id);
                }


            }
        });
    }

    private void initData(String  ss ) {
        List<SceensDevices> sceensDevices = App.getApp().getSceensDevicesSn().queryBuilder().where(SceensDevicesDao.Properties.SceensId.eq(mIdName)).build().list();
        mDevicesInfoList = App.getApp().getDevicesInfo()
                .queryBuilder().build().list();

        for (int j= 0; j < sceensDevices.size(); j++){
            for (int i = mDevicesInfoList.size()-1; i  >= 0 ; i--) {
                if (mDevicesInfoList.get(i).getSn().equals(sceensDevices.get(j).getMSn())){
                    mDevicesInfoList.remove(i);
                }
            }
        }


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
                    initData("07");
                }
                break;
            //添加设备到场景
            case "0E":
                if (messageData.getData().length() < 3){
                    return;
                }

                String datas = messageData.getData();
                Log.e(TAG, "loadData: " + datas );
                SceensDevices sceens = new SceensDevices();
                sceens.setSceensId(datas.substring(0,2));
                sceens.setMSn_0(datas.substring(6,8));
                sceens.setMSn_1(datas.substring(8,10));
                sceens.setMSn_2(datas.substring(10,12));
                sceens.setMSn_3(datas.substring(12,14));
                SceensDevices sceensList = App.getApp().getSceensDevicesSn().queryBuilder().where(SceensDevicesDao.Properties.SceensId.eq(mIdName),SceensDevicesDao.Properties.MSn.eq(sceens.getMSn())).build().unique();

                if (sceensList == null){
                    Log.e(TAG, "loadData:  insert " );
                    App.getApp().getSceensDevicesSn().insert(sceens);
                }else {

                    LogUtils.file(sceensList.getId());
                    sceens.setId(sceensList.getId());
                    App.getApp().getSceensDevicesSn().update(sceens);
                }
                initData("");
                break;
        }
    }
}