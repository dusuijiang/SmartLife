package cn.com.example.smartlife.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.DevicesInfo;


public class AddDevicesToGroupAdapter extends BaseQuickAdapter<DevicesInfo, BaseViewHolder> {

    public AddDevicesToGroupAdapter(List<DevicesInfo> list) {
        super(R.layout.reacycerview_item_adddevicestogroup, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DevicesInfo item) {
        helper.setText(R.id.name,item.getName());
        helper.setText(R.id.id_name,"Sn : "+item.getSn());
    }
}
