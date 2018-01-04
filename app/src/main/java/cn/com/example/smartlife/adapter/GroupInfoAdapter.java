package cn.com.example.smartlife.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.DevicesInfo;


public class GroupInfoAdapter extends BaseQuickAdapter<DevicesInfo, BaseViewHolder> {

    public GroupInfoAdapter(List<DevicesInfo> list) {
        super(R.layout.recyclerview_group_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DevicesInfo item) {
        helper.setText(R.id.name,item.getName());
        helper.setText(R.id.num_devices,"设备数 : " );
        helper.addOnClickListener(R.id.switchs);
    }
}
