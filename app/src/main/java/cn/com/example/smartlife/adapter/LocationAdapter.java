package cn.com.example.smartlife.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.DevicesInfo;


public class LocationAdapter extends BaseQuickAdapter<DevicesInfo, BaseViewHolder> {

    public LocationAdapter(List<DevicesInfo> list) {
        super(R.layout.recyclerview_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DevicesInfo item) {
        helper.setText(R.id.name,item.getName());
        helper.setText(R.id.id_name,"Sn : "+item.getSn()  + "   deviceId : " +item.getDeviceID());
    }
}
