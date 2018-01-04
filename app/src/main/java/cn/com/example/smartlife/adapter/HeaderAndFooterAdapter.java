package cn.com.example.smartlife.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.com.example.smartlife.R;
import cn.com.example.smartlife.Status;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HeaderAndFooterAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {

    public HeaderAndFooterAdapter() {
        super(R.layout.item_header_and_footer, getSampleData());
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tv,item.getText());
        helper.setImageResource(R.id.iv,item.getIcon());
    }

    static int[] name = new int[]{R.string.menu_0,R.string.menu_1,R.string.menu_2,R.string.menu_3,
    R.string.menu_4,R.string.menu_5,R.string.menu_6,R.string.menu_7,R.string.menu_8,R.string.menu_9,R.string.menu_10};
    static int [] icon = new int[]{R.drawable.ic_qiehuan,R.drawable.ic_music,R.drawable.ic_shiyongshuoming, R.drawable.ic_home,
            R.drawable.ic_strt,R.drawable.ic_lock,R.drawable.ic_apfactory,R.drawable.ic_restart,R.drawable.ic_version,R.drawable.ic_version_up,R.drawable.ic_unlogin};
    public static List<Status> getSampleData() {
        List<Status> list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            Status status = new Status();
            status.setText(name[i]);
            status.setIcon(icon[i]);
            list.add(status);
        }
        return list;
    }
}
