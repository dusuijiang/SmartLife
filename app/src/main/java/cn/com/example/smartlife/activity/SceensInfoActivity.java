package cn.com.example.smartlife.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseActivity;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.adapter.MultipleItemSceensInfoAdapter;
import cn.com.example.smartlife.dialog.BaseDialog;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.DevicesInfo;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.message.MultipleItemDevicesInfo;
import cn.com.example.smartlife.message.SceensDevices;
import cn.com.example.smartlife.message.SceensDevicesDao;
import cn.com.example.smartlife.utils.SPUtils;
import cn.com.example.smartlife.utils.TimeUtils;

public class SceensInfoActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private String mIdName;
    private MultipleItemSceensInfoAdapter mMultipleItemGroupInfoAdapter;
    private static final String TAG = "SceensInfoActivity";
    private List<MultipleItemDevicesInfo> mMultipleItemDevicesInfos;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_sceens_info;
    }


    @Override
    protected void findViews() {
        showBackButton();
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getIntent().getStringExtra("name"));
        mIdName = getIntent().getStringExtra("idName");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SPUtils spUtils = new SPUtils(this,"SceensInfoActivity"+mIdName);
        if (!spUtils.getBoolean("SceensInfoActivity"+mIdName,false)){
            DataCmd.getInstance().getOneSceenDevices(mIdName);
            spUtils.putBoolean("SceensInfoActivity"+mIdName,true);
        }
        initData("findViews");

        mMultipleItemGroupInfoAdapter = new MultipleItemSceensInfoAdapter(mMultipleItemDevicesInfos);
        mRecyclerView.setAdapter(mMultipleItemGroupInfoAdapter);

        //删除场景中的设备
        mMultipleItemGroupInfoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                List<MultipleItemDevicesInfo> list = adapter.getData();
                //mDeleteDevicesInfo = list.get(position).getDevicesInfo();
                SceensDevices sceensDevices = list.get(position).getSceensDevices();
                String deleteDevidesInfo = mIdName +"+"+sceensDevices.getMSn_0()+sceensDevices.getMSn_1()+sceensDevices.getMSn()
                        +sceensDevices.getType_0()+sceensDevices.getType_1()+sceensDevices.getType_2()+sceensDevices.getType_3()+ TimeUtils.getTimeHx(sceensDevices.getRs2()) ;
                comform(deleteDevidesInfo);
                return false;
            }
        });
        mMultipleItemGroupInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView time = (TextView) view.findViewById(R.id.time);
                switch (view.getId()){
                    case R.id.time:
                        initLunarPicker(time,position);
                        pvCustomLunar.show();
                        break;
                }
            }
        });
        mMultipleItemGroupInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                switch (adapter.getItemViewType(position)) {
                    case MultipleItemDevicesInfo.STA_0:
                        List<MultipleItemDevicesInfo> list = adapter.getData();
                        DevicesInfo devicesInfo = list.get(position).getDevicesInfo();
                        String state = devicesInfo.getDevState();
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
                Intent intent = new Intent(SceensInfoActivity.this, AddDevicesToSceensActivity.class);
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
        initData("onResume ");
    }

    private void initData(String  sss) {
        List<SceensDevices> sceensDevices = App.getApp().getSceensDevicesSn().queryBuilder().where(SceensDevicesDao.Properties.SceensId.eq(mIdName)).build().list();

        for (int i = 0; i < sceensDevices.size(); i++) {
            SceensDevices d = sceensDevices.get(i);
            Log.e(TAG, "initData: " + d.getMSn() + "   "+d.getRs2() );
        }
        List<DevicesInfo> list = App.getApp().getDevicesInfo().queryBuilder().build().list();
        mMultipleItemDevicesInfos = new ArrayList<>();
        mMultipleItemDevicesInfos.clear();
        for (int i = 0; i < sceensDevices.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (sceensDevices.get(i).getMSn().equals(list.get(j).getSn())){
                    Log.e(TAG, "initData: " + list.get(j).getDeviceID() );
                    switch (list.get(j).getDeviceID()){
                        case "01":
                            mMultipleItemDevicesInfos.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_0,sceensDevices.get(i),list.get(j)));
                            break;
                        //窗帘
                        case "03":
                            mMultipleItemDevicesInfos.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_2,sceensDevices.get(i),list.get(j)));
                            break;
                        //调色灯
                        case "05":
                            mMultipleItemDevicesInfos.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_3,sceensDevices.get(i),list.get(j)));
                            break;
                        default:
                            mMultipleItemDevicesInfos.add(new MultipleItemDevicesInfo(MultipleItemDevicesInfo.STA_0,sceensDevices.get(i),list.get(j)));
                    }
                }
            }
        }
        if (mMultipleItemGroupInfoAdapter != null) {
            mMultipleItemGroupInfoAdapter.getData().clear();
            mMultipleItemGroupInfoAdapter.addData(mMultipleItemDevicesInfos);
            mMultipleItemGroupInfoAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {
        switch (messageData.getCmd()){
            //删除分组中的设备
            case "4A":
                String s = messageData.getData();
                String ss = s.substring(7,11);
                if (mMultipleItemDevicesInfos != null){
                    for (int i = mMultipleItemDevicesInfos.size()-1; i >=0 ; i--) {
                        if (mMultipleItemDevicesInfos.get(i).getSceensDevices().getMSn().contains(ss)){
                            mMultipleItemDevicesInfos.remove(i);
                        }
                    }
                }
                 SceensDevices sceendevices = App.getApp().getSceensDevicesSn().queryBuilder().where(SceensDevicesDao.Properties.MSn.eq(ss),SceensDevicesDao.Properties.SceensId.eq(mIdName)).build().unique();
                Log.e(TAG, "loadData:  " + sceendevices.getMSn()   + "   " +sceendevices.getSceensId() );
                if (sceendevices != null)
                    App.getApp().getSceensDevicesSn().delete(sceendevices);
                if (mDialog != null){
                    mDialog.cancel();
                }
                break;
            case "46":
                break;
        }
        initData("loadData");
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
                DataCmd.getInstance().deleteDevicesForSceens(position);
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

    private TimePickerView  pvCustomLunar;
    private void initLunarPicker(final TextView time,final int position) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
             //   Toast.makeText(SceensInfoActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                time.setText(getTime(date));

                SceensDevices sceensDevices = App.getApp().getSceensDevicesSn().queryBuilder().where(SceensDevicesDao.Properties.MSn.eq(mMultipleItemDevicesInfos.get(position).getSceensDevices().getMSn())
                        ,SceensDevicesDao.Properties.SceensId.eq(mIdName)).unique();
                sceensDevices.setRs2(getTime(date));
                App.getApp().getSceensDevicesSn().update(sceensDevices);

                SceensDevices sceensDevicess = mMultipleItemDevicesInfos.get(position).getSceensDevices();
                DevicesInfo devices = mMultipleItemDevicesInfos.get(position).getDevicesInfo();

                String msg =sceensDevicess.getSceensId()+"+"+sceensDevicess.getMSn_0()+sceensDevicess.getMSn_1()+sceensDevicess.getMSn()+sceensDevicess.getType()+TimeUtils.getTimeHx(sceensDevicess.getRs2())+
                        "00"+"+"+sceensDevicess.getMSn_0()+sceensDevicess.getMSn_1()+sceensDevicess.getMSn()+sceensDevicess.getType()+TimeUtils.getTimeHx(getTime(date))+"00";
                        DataCmd.getInstance().setOneDevicesTime(msg);

                initData("");

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, 0.8f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timepicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timepicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timepicker.getChildCount(); i++) {
                            View childAt = timepicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{false, false, false, false, false, true})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("ss");
        return format.format(date);
    }


}
