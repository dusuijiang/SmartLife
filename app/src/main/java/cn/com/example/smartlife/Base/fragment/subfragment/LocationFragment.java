package cn.com.example.smartlife.Base.fragment.subfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseFragment;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.activity.AddGroupActivity;
import cn.com.example.smartlife.activity.GroupInfoActivity;
import cn.com.example.smartlife.activity.UndefinedGroupActivity;
import cn.com.example.smartlife.adapter.GroupAdapter;
import cn.com.example.smartlife.dialog.BaseDialog;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.DevicesInfo;
import cn.com.example.smartlife.message.DevicesInfoDao;
import cn.com.example.smartlife.message.Groups;
import cn.com.example.smartlife.message.GroupsDao;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.utils.UtilsEncode;

public class LocationFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private GroupAdapter mAdapter;
    private static final String TAG = "LocationFragment";
    private List<Groups> list;
    private BaseDialog mDialog;
    private Groups mGroups;

    public static LocationFragment newInstance(String s) {
        LocationFragment homeFragment = new LocationFragment();
        return homeFragment;
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_location_content;
    }

    @Override
    protected void findViews(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();

    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {
        Log.e(TAG, "loadData: " + messageData.toString() );
        switch (messageData.getCmd()){
            //获取分组
            case "12":
                loadData();
                break;
            //删除分组
            case "09":
                Groups groups = App.getApp().getGroups().queryBuilder().where(GroupsDao.Properties.IdName.eq(messageData.getData())).build().unique();
                if (groups != null){
                    App.getApp().getGroups().deleteByKey(groups.getId());
                    App.getApp().getDevicesInfo().updateInTx();
                }
                List<DevicesInfo> devicesInfoList =  App.getApp().getDevicesInfo().queryBuilder().where(DevicesInfoDao.Properties.Rs1.eq(messageData.getData())).list();
                for (int i = 0; i <devicesInfoList.size() ; i++) {
                    devicesInfoList.get(i).setRs1("00");
                }
                App.getApp().getDevicesInfo().updateInTx(devicesInfoList);
                loadData();
                if (mDialog != null){
                    mDialog.cancel();
                }

                break;
            case "0A":
                loadData();
                break;
            case "52":
                List<DevicesInfo> devicesInfos = App.getApp().getDevicesInfo()
                        .queryBuilder().where(DevicesInfoDao.Properties.Rs1.eq(messageData.getData())).build().list();
                for (int i = 0; i <devicesInfos.size() ; i++) {
                    devicesInfos.get(i).setDevState("00");
                }
                App.getApp().getDevicesInfo().updateInTx(devicesInfos);
                List<DevicesInfo> devicesInfoss = App.getApp().getDevicesInfo()
                        .queryBuilder().where(DevicesInfoDao.Properties.Rs1.eq(messageData.getData())).build().list();
                for (int i = 0; i < devicesInfoss.size(); i++) {
                    Log.e(TAG, "loadData: "   + devicesInfoss.get(i).getDevState());
                }

                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


    private void loadData(){
        list = new ArrayList<>();
        List<Groups> list1 = App.getApp().getGroups().queryBuilder().build().list();
        if (list != null && list1 != null) {
            list.clear();
            list.addAll(list1);
        }
        if (mAdapter != null) {
            Log.e(TAG, "onResume: ");
            mAdapter.getData().clear();
            mAdapter.addData(list);
        }
    }
    private void initAdapter() {
        mAdapter = new GroupAdapter(list);
        View headerView = getHeaderView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UndefinedGroupActivity.class));
            }
        });
        mAdapter.addHeaderView(headerView);

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                List<Groups> groupsList = adapter.getData();
                mGroups = groupsList.get(position);
                comform(mGroups.getIdName(),"删除该分组",true);
                return false;
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<Groups> groupsList = adapter.getData();
                switch (view.getId()){
                    case R.id.switchs:
                        DataCmd.getInstance().setCloseGroups(groupsList.get(position).getIdName());
                        break;
                    case R.id.name:
                        comform(groupsList.get(position).getIdName(),"修改分组名称",false);
                        break;
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<Groups> groupsList = adapter.getData();
                Intent intent = new Intent(getActivity(), GroupInfoActivity.class);
                intent.putExtra("idName",groupsList.get(position).getIdName());
                intent.putExtra("name",groupsList.get(position).getName());
                startActivity(intent);
            }
        });

        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddGroupActivity.class));
            }
        });
        mAdapter.addFooterView(footerView, 0);

        mRecyclerView.setAdapter(mAdapter);
    }

    private View getHeaderView(int type, View.OnClickListener listener) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.recyclerview_header_item, (ViewGroup) mRecyclerView.getParent(), false);

        view.setOnClickListener(listener);
        return view;
    }

    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.recyclerview_footer_item, (ViewGroup) mRecyclerView.getParent(), false);
        view.setOnClickListener(listener);
        return view;
    }

    private void comform(final String position,String titles,final boolean delete) {
        mDialog = new BaseDialog.Builder(getActivity()).setContentView(R.layout.dialog_comform)
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
        title.setText(titles);
        View view = (View) mDialog.findViewById(R.id.view);
        final EditText ets = (EditText) mDialog.findViewById(R.id.et);
        if (delete){
            setMargins(view, 0, 100, 0, 0);
            ets.setVisibility(View.GONE);
        }
        final String s = UtilsEncode.getEncode(ets.getText().toString());
        ets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = UtilsEncode.getEncode(ets.getText().toString());
                if (s.length() > 16){
                 //   ets.setInputType(InputType.TYPE_NULL);//来禁止手机软键盘。
                }
               // ets.setInputType(InputType.TYPE_CLASS_TEXT);//来开启软键盘。
               // ets.setFilters(new InputFilter[]{new InputFilter.LengthFilter(s.length())}); //最大输入长度
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ets.setHint("请输入5个范围内的汉字");
        mDialog.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delete){
                    DataCmd.getInstance().deleteGroup(position);
                }else {
                    if (s.length() > 16){
                        ToastUtils.setBgColor(getResources().getColor(R.color.color_black_000000_88_transparent));
                        ToastUtils.showShort("输入超出限制");
                        return;
                    }
                    String  ss = position+"+"+ s ;
                    DataCmd.getInstance().updataDevicesGroupName(ss);
                }

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

    private  int count(String str){
        int count=0;
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        char c[] = str.toCharArray();
        for(int i=0;i<c.length;i++){
            Matcher matcher = pattern.matcher(String.valueOf(c[i]));
            if(matcher.matches()){
                count++;
            }
        }
        return count;
    }
}
