package cn.com.example.smartlife.Base.fragment.subfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseFragment;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.activity.AddSceenActivity;
import cn.com.example.smartlife.activity.SceensInfoActivity;
import cn.com.example.smartlife.activity.UndefinedGroupActivity;
import cn.com.example.smartlife.adapter.SceensAdapter;
import cn.com.example.smartlife.dialog.BaseDialog;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.message.SceenBean;
import cn.com.example.smartlife.message.SceenBeanDao;


/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

public class LikeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SceensAdapter mAdapter;
    private List<SceenBean> list;
    private BaseDialog mDialog;
    private static final String TAG = "LikeFragment";
    public static LikeFragment newInstance(String s) {
        LikeFragment homeFragment = new LikeFragment();
        return homeFragment;
    }


    @Override
    protected int setViewId() {
        return R.layout.fragment_like_content;
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
            case "13":
                loadData();
                break;
            //删除场景
            case "0F":
                SceenBean groups = App.getApp().getSceens().queryBuilder().where(SceenBeanDao.Properties.IdName.eq(messageData.getData())).build().unique();
                if (groups != null){
                    App.getApp().getSceens().deleteByKey(groups.getId());
                }
                loadData();
                if (mDialog != null){
                    mDialog.cancel();
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
        List<SceenBean> list1 = App.getApp().getSceens().queryBuilder().build().list();
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
        mAdapter = new SceensAdapter(list);
        View headerView = getHeaderView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UndefinedGroupActivity.class));
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                List<SceenBean> groupsList = adapter.getData();
                comform(groupsList.get(position).getIdName());
                return false;
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<SceenBean> groupsList = adapter.getData();
                //DataCmd.getInstance().getOneSceenDevices(groupsList.get(position).getIdName());
                Intent intent = new Intent(getActivity(), SceensInfoActivity.class);
                intent.putExtra("idName",groupsList.get(position).getIdName());
                intent.putExtra("name",groupsList.get(position).getName());
                startActivity(intent);
            }
        });
        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddSceenActivity.class));
            }
        });
        mAdapter.addFooterView(footerView, 0);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DataCmd.getInstance().runScene(list.get(position).getIdName());
            }
        });
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
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(R.string.add_sceen);
        return view;
    }

    private void comform(final String position) {
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
        title.setText("确定删除该场景吗？");
        View view = (View) mDialog.findViewById(R.id.view);
        setMargins(view, 0, 100, 0, 0);
        final EditText et = (EditText) mDialog.findViewById(R.id.et);
        et.setVisibility(View.GONE);
        mDialog.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCmd.getInstance().deleteScene(position);
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

