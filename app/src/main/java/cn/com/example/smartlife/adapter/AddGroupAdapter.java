package cn.com.example.smartlife.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.AddGroup;


public class AddGroupAdapter extends BaseQuickAdapter<AddGroup, BaseViewHolder> {

    public AddGroupAdapter(List<AddGroup> list) {
        super(R.layout.recyclerview_add_group, list);
    }
    //String[] name = new String[]{"客厅","阳台","走廊","主卧","次卧","书房","娱乐厅","卫浴间","办公室","厨房","其他"};
    int[] img = new int[]{R.mipmap.room_1,R.mipmap.room_2,R.mipmap.room_3,R.mipmap.room_4, R.mipmap.scene_img_8,
            R.mipmap.room_5,R.mipmap.room_6,R.mipmap.room_7,R.mipmap.room_8,R.mipmap.movie1};
    @Override
    protected void convert(BaseViewHolder helper, AddGroup item) {
        helper.setText(R.id.name,item.getName());
        if (Integer.parseInt(item.getId_name()) < 11){
            helper.setImageResource(R.id.icon,img[Integer.parseInt(item.getId_name())-1]);
        }
        helper.addOnClickListener(R.id.switchs);
    }
}
