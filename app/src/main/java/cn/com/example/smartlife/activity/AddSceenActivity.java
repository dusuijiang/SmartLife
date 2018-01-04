package cn.com.example.smartlife.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseActivity;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.adapter.AddSceensAdapter;
import cn.com.example.smartlife.dialog.BaseDialog;
import cn.com.example.smartlife.message.AddSceen;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.message.SceenBean;
import cn.com.example.smartlife.utils.UtilsEncode;

public class AddSceenActivity extends BaseActivity {

    private static final String TAG = "AddSceenActivity";
    private RecyclerView mRecyclerView;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_group;
    }

    @Override
    protected void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        showBackButton();
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.add_sceen);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();

    }

    private void initData() {
        App.getApp().getAddSceen().insert(new AddSceen(1,"睡眠模式","01"));
        App.getApp().getAddSceen().insert(new AddSceen(2,"回家模式","02"));
        App.getApp().getAddSceen().insert(new AddSceen(3,"离家模式","03"));
        App.getApp().getAddSceen().insert(new AddSceen(4,"起床模式","04"));

        LinkedHashMap<String,AddSceen> map = new LinkedHashMap<>();

        List<AddSceen> list = App.getApp().getAddSceen().queryBuilder().build().list();
        if (list.size()> 0){
            for (int i = 0; i < list.size(); i++) {
                map.put(list.get(i).getId_name(),list.get(i));
            }
        }
        App.getApp().getAddSceen().deleteAll();
        for (Map.Entry<String, AddSceen> entry : map.entrySet()) {
            App.getApp().getAddSceen().insert(entry.getValue());
        }
        List<AddSceen> lists = App.getApp().getAddSceen().queryBuilder().build().list();
        AddSceensAdapter adapter = new AddSceensAdapter(lists);

        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<AddSceen>  addSceenList = adapter.getData();
                AddSceen addSceen = addSceenList.get(position);
                //data:"场景类别+分组名称"
                DataCmd.getInstance().addScene(addSceen.getId_name() +"+"+ UtilsEncode.getEncode(addSceen.getName()));
            }
        });
    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {
        Log.e(TAG, "loadData: " + messageData.toString());
        switch (messageData.getCmd()) {
            case "0C":
                if (messageData.getData().length() < 3){
                    return;
                }
                LinkedHashMap<String, SceenBean> groupsInfoLinkedHashMap = new LinkedHashMap<>();
                List<SceenBean> groupList = App.getApp().getSceens().queryBuilder().build().list();
                if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        groupsInfoLinkedHashMap.put(groupList.get(i).getIdName(), groupList.get(i));
                    }
                }
                //02+02+E59B9EE5AEB6E6A8A1E5BC8F
                Log.e(TAG, "loadData: " +messageData.getData() );
                SceenBean groups = new SceenBean("00","00","00","00","00","00","00");
                groups.setIdName(messageData.getData().substring(0, 2));
                groups.setNature(messageData.getData().substring(0, 2));
                groups.setName(messageData.getData().substring(6, messageData.getData().length()));
                groupsInfoLinkedHashMap.put(groups.getIdName(), groups);
                App.getApp().getSceens().deleteAll();
                //插入数据
                for (Map.Entry<String, SceenBean> entry : groupsInfoLinkedHashMap.entrySet()) {
                    App.getApp().getSceens().insert(entry.getValue());
                }
                break;

        }
    }

    private void comform() {
        final BaseDialog dialog = new BaseDialog.Builder(AddSceenActivity.this).setContentView(R.layout.dialog_comform)
                .setFullWidth()
                .setCanceledOnTouchOutside(false)
                .setAnimation(R.style.DialogFromBottomAnim)
                .show();
        dialog.setOnClickListener(R.id.cancal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.cancel();
                }
            }
        });
        final EditText et = (EditText) dialog.findViewById(R.id.et);
        dialog.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
