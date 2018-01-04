package cn.com.example.smartlife.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseActivity;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.adapter.AddGroupAdapter;
import cn.com.example.smartlife.dialog.BaseDialog;
import cn.com.example.smartlife.message.AddGroup;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.Groups;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.utils.UtilsEncode;

public class AddGroupActivity extends BaseActivity {


    private RecyclerView mRecyclerView;

    String[] name = new String[]{"客厅", "阳台", "走廊", "主卧", "次卧", "书房", "娱乐厅", "卫浴间", "办公室", "厨房", "其他"};

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_group;
    }

    @Override
    protected void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        showBackButton();
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.add_groups);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {
        Log.e(TAG, "loadData: " + messageData.toString());
        switch (messageData.getCmd()) {
            case "0A":
                if (messageData.getData().length() < 3){
                    return;
                }
                LinkedHashMap<String, Groups> groupsInfoLinkedHashMap = new LinkedHashMap<>();
                List<Groups> groupList = App.getApp().getGroups().queryBuilder().build().list();
                if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        groupsInfoLinkedHashMap.put(groupList.get(i).getIdName(), groupList.get(i));
                    }
                }
                Log.e(TAG, "loadData: " +messageData.getData() );
                Groups groups = new Groups();
                groups.setNature(messageData.getData().substring(0, 2));
                groups.setIdName(messageData.getData().substring(3, 5));
                groups.setName(messageData.getData().substring(6, messageData.getData().length()));
                groupsInfoLinkedHashMap.put(groups.getIdName(), groups);
                App.getApp().getGroups().deleteAll();
                Log.e(TAG, "loadData 000000000: " + groups.toString() );
                //插入数据
                for (Map.Entry<String, Groups> entry : groupsInfoLinkedHashMap.entrySet()) {
                    App.getApp().getGroups().insert(entry.getValue());
                }
                break;
        }
    }

    protected void loadData() {
        List<AddGroup> list = new ArrayList<AddGroup>();

        for (int i = 0; i < name.length; i++) {
            list.add(new AddGroup(i < 9 ? "0" + (i + 1) + "" : (i + 1) + "", name[i]));
        }
        if (DataSupport.findAll(AddGroup.class).size() <= 0) {
            DataSupport.saveAll(list);
        }
        AddGroupAdapter adapter = new AddGroupAdapter(DataSupport.findAll(AddGroup.class));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<AddGroup> list = adapter.getData();
                if (position < list.size() - 1) {
                    DataCmd.getInstance().addDevicesGroup((position <= 8 ? "0" + (position + 1) : (position + 1)) + "+" + UtilsEncode.getEncode(name[position]));
                } else {
                    comform();
                }

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private void comform() {
        final BaseDialog dialog = new BaseDialog.Builder(AddGroupActivity.this).setContentView(R.layout.dialog_comform)
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

    private static final String TAG = "AddGroupActivity";
}
