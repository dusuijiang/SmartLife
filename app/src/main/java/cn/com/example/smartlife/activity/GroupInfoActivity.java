package cn.com.example.smartlife.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseActivity;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.adapter.MultipleItemGroupInfoAdapter;
import cn.com.example.smartlife.dialog.BaseDialog;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.DevicesInfo;
import cn.com.example.smartlife.message.DevicesInfoDao;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.message.MultipleItemDevicesInfo;

public class GroupInfoActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String mIdName;
    private List<MultipleItemDevicesInfo> mDevicesInfoList;
    private MultipleItemGroupInfoAdapter mMultipleItemGroupInfoAdapter;
    private DevicesInfo mDeleteDevicesInfo;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_group_info;
    }

    private static final String TAG = "GroupInfoActivity";

    @Override
    protected void findViews() {
        showBackButton();
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getIntent().getStringExtra("name"));
        mIdName = getIntent().getStringExtra("idName");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        initData();

        mMultipleItemGroupInfoAdapter = new MultipleItemGroupInfoAdapter(mDevicesInfoList);
        mRecyclerView.setAdapter(mMultipleItemGroupInfoAdapter);

        //删除分组中的设备
        mMultipleItemGroupInfoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                List<MultipleItemDevicesInfo> list = adapter.getData();
                mDeleteDevicesInfo = list.get(position).getDevicesInfo();
                String deleteDevidesInfo = mIdName +"+01+"+mDeleteDevicesInfo.getData_0() + mDeleteDevicesInfo.getData_1() + mDeleteDevicesInfo.getSn();
                comform(deleteDevidesInfo);
                return false;
            }
        });
        mMultipleItemGroupInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (adapter.getItemViewType(position)) {
                    case MultipleItemDevicesInfo.STA_0:
                        List<MultipleItemDevicesInfo> list = adapter.getData();
                        DevicesInfo devicesInfo = list.get(position).getDevicesInfo();
                        String state = devicesInfo.getDevState();
                       // DevicesInfo devices = App.getApp().getDevicesInfo().queryBuilder()
                       //         .where(DevicesInfoDao.Properties.Data_2.eq(devicesInfo.getData_2()), DevicesInfoDao.Properties.Data_3.eq(devicesInfo.getData_3())).build().uniqueOrThrow();
                        if (state.equals("00")) {
                            devicesInfo.setDevState("01");
                        } else {
                            devicesInfo.setDevState("00");
                        }
                        if (devicesInfo != null) {
                            App.getApp().getDevicesInfo().update(devicesInfo);
                        }
                        DataCmd.getInstance().setSwitchDevices(devicesInfo.getData_0() + devicesInfo.getData_1() + devicesInfo.getSn() + "+" + devicesInfo.getDevState());
                        break;
                    //跳转
                    case MultipleItemDevicesInfo.STA_1:
                        start(UndefinedGroupActivity.class);
                        break;
                }

            }
        });

        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupInfoActivity.this, AddDevicesToGroupActivity.class);
                intent.putExtra("mIdName",mIdName);
                startActivity(intent);
            }
        });
        mMultipleItemGroupInfoAdapter.addFooterView(footerView, 0);
        mRecyclerView.setAdapter(mMultipleItemGroupInfoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        List<DevicesInfo> list = App.getApp().getDevicesInfo().queryBuilder().where(DevicesInfoDao.Properties.Rs1.eq(mIdName)).build().list();
        mDevicesInfoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDeviceID().equals("01")) {
                mDevicesInfoList.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_1, list.get(i))); //1 跳转
            } else {
                mDevicesInfoList.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_0, list.get(i)));
            }
        }

        if (mMultipleItemGroupInfoAdapter != null) {
            mMultipleItemGroupInfoAdapter.getData().clear();
            mMultipleItemGroupInfoAdapter.addData(mDevicesInfoList);
        }

    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {
        switch (messageData.getCmd()){
            //删除分组中的设备
            case "08":

                if (mDeleteDevicesInfo != null){
                    mDeleteDevicesInfo.setRs1("00");
                    App.getApp().getDevicesInfo().update(mDeleteDevicesInfo);
                }
                if (mDialog != null){
                    mDialog.cancel();
                }
                break;
        }
        initData();
    }

    private View getFooterView(int type, View.OnClickListener listener) {
        View view = this.getLayoutInflater().inflate(R.layout.recyclerview_footer_item, (ViewGroup) mRecyclerView.getParent(), false);
        view.setOnClickListener(listener);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(R.string.add_devices_to_group);
        return view;
    }

    private BaseDialog mDialog;
    private void comform(final String position) {
        mDialog = new BaseDialog.Builder(this).setContentView(R.layout.dialog_comform)
                .setCanceledOnTouchOutside(false)
                .setAnimation(R.style.DialogFromBottomAnim)
                .show();
        mDialog.setOnClickListener(R.id.cancal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.cancel();
                }
            }
        });
        TextView title = (TextView) mDialog.findViewById(R.id.title);
        title.setText("确定删除该设备吗？");
        View view = (View) mDialog.findViewById(R.id.view);
        setMargins(view, 0, 100, 0, 0);
        final EditText et = (EditText) mDialog.findViewById(R.id.et);
        et.setVisibility(View.GONE);
        mDialog.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCmd.getInstance().deleteDevicesForGroup(position);
            }
        });

    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
