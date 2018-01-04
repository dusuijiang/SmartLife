package cn.com.example.smartlife.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.Groups;


public class GroupAdapter extends BaseQuickAdapter<Groups, BaseViewHolder> {

    public GroupAdapter(List<Groups> list) {
        super(R.layout.recyclerview_group_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Groups item) {
        helper.setText(R.id.name,item.getName());
        helper.setText(R.id.num_devices,"设备数 : " );
        helper.addOnClickListener(R.id.switchs);
        helper.addOnClickListener(R.id.name);
    }
}
