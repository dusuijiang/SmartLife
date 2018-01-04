package cn.com.example.smartlife.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.MultipleItemDevicesInfo;

/**
 * Created by rd0404 on 2017/11/21.
 */

public class MultipleItemGroupInfoAdapter extends BaseMultiItemQuickAdapter<MultipleItemDevicesInfo, BaseViewHolder> {

    public MultipleItemGroupInfoAdapter(List data) {
        super(data);
        addItemType(MultipleItemDevicesInfo.STA_0, R.layout.recyclerview_item);  //开关
        addItemType(MultipleItemDevicesInfo.STA_1, R.layout.recyclerview_item);  //跳转
        addItemType(MultipleItemDevicesInfo.STA_2, R.layout.recyclerview_item);  //跳转
        addItemType(MultipleItemDevicesInfo.STA_3, R.layout.recyclerview_item);  //跳转
        addItemType(MultipleItemDevicesInfo.STA_4, R.layout.recyclerview_item);  //跳转

    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItemDevicesInfo item) {
        ImageView switchs = helper.getView(R.id.switchs);
        ImageView skip = helper.getView(R.id.skip);
        RadioGroup radioGroup = helper.getView(R.id.radio_group);
        SeekBar seekBar = helper.getView(R.id.seekbar);
        TextView time = helper.getView(R.id.time);
        switch (helper.getItemViewType()) {
            case MultipleItemDevicesInfo.STA_0:
                switchs.setVisibility(View.VISIBLE);
                skip.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);
                seekBar.setVisibility(View.INVISIBLE);
                helper.addOnClickListener(R.id.switchs);
                if (item.getDevicesInfo().getDevState().equals("00")) {
                    switchs.setImageResource(R.drawable.ic_switch_off);
                } else {
                    switchs.setImageResource(R.drawable.ic_switch_on);
                }
                break;
            case MultipleItemDevicesInfo.STA_1:
                skip.setVisibility(View.VISIBLE);
                switchs.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);
                seekBar.setVisibility(View.INVISIBLE);
                helper.addOnClickListener(R.id.skip);
                break;
            case MultipleItemDevicesInfo.STA_2:
                switchs.setVisibility(View.INVISIBLE);
                skip.setVisibility(View.INVISIBLE);
                seekBar.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                break;
            case MultipleItemDevicesInfo.STA_3:
                switchs.setVisibility(View.INVISIBLE);
                skip.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                break;
            case MultipleItemDevicesInfo.STA_4:
                time.setVisibility(View.VISIBLE);
                time.setText("定时");
                helper.addOnClickListener(R.id.time);
                break;
        }
        helper.setText(R.id.name, item.getDevicesInfo().getName().toString());
        helper.setText(R.id.id_name, item.getDevicesInfo().getSn().toString());
    }

}

