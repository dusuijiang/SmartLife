package cn.com.example.smartlife.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.example.smartlife.message.MessageData;

/**
 * Created by dusj on 2016/6/27.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setViewId(),container,false);

        findViews(view);
        initEvents();
        new ObserverEvent().setMessageEvent(new ObserverEvent.MessageEvent() {
            @Override
            public void Message(MessageData messageData, String[] arr, String data) {
                loadData(messageData,arr,data);
            }
        });
        return view;
    }

    //设置布局文件
    protected abstract  int setViewId();
    //初始化控件
    protected  abstract void findViews(View view);

    //初始化监听事件
    protected   void initEvents(){}
    //加载数据
    protected   abstract void  loadData(MessageData messageData,String[] arr,String data);


}
