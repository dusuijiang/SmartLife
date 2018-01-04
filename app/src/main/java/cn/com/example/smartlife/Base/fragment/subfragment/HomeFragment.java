package cn.com.example.smartlife.Base.fragment.subfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.com.example.smartlife.App;
import cn.com.example.smartlife.Base.BaseFragment;
import cn.com.example.smartlife.Base.Constants;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.DataCmd;
import cn.com.example.smartlife.message.DevicesInfo;
import cn.com.example.smartlife.message.MessageData;
import cn.com.example.smartlife.message.SceensDevices;


/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description: HomeFragment
 */

public class HomeFragment extends BaseFragment{
    public static HomeFragment newInstance(String s){
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS,s);
        homeFragment.setArguments(bundle);
        return homeFragment;
}
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_home_content;
    }

    @Override
    protected void findViews(View view) {
        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " );
                DataCmd.getInstance().getAllGroupInfo();
            }
        });
        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " );
                DataCmd.getInstance().getAllDevices();
            }
        });
        final TextView tv = (TextView) view.findViewById(R.id.tv);
        view.findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCmd.getInstance().getAllSceen();
            }
        });
        view.findViewById(R.id.bt4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCmd.getInstance().getOneSceenDevices("03");
            }
        });
        view.findViewById(R.id.bt5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SceensDevices> sceensDevices = App.getApp().getSceensDevicesSn().queryBuilder().build().list();

                for (int i = 0; i <sceensDevices.size() ; i++) {
                    SceensDevices de = sceensDevices.get(i);
                    Log.e(TAG, "onClick: "  +  " Id "+de.getId()  +"  Sn : "+ de.getMSn()  + " scId : "+de.getSceensId());
                }
            }
        });
        view.findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DevicesInfo> list = App.getApp().getDevicesInfo().queryBuilder().build().list();
                String  str="";
                for (int i = 0; i <list.size() ; i++) {
                    DevicesInfo devices = list.get(i);
                    Log.e(TAG, "onClick: " + devices );
                    str =str+   i +"  "+ devices.getName()  + "  "+devices.getDeviceID() + "  " + devices.getRs1()   +"  " +devices.getSn()+"\n";
                }
                tv.setText(str);
               // Log.e(TAG, "onClick: " + str );
            }
        });
    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {
        Log.e(TAG, "loadData:         0000 " + messageData.toString() );
    }

    private static final String TAG = "HomeFragment";


}
