package cn.com.example.smartlife.Base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.MessageData;

/**
 * Created by dusj on 2016/6/27.
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 在使用自定义toolbar时候的根布局 =toolBarView+childView
     */
    private View baseView;
    public Toolbar mToolbar;
    public TextView tv_title;
    public TextView tv_rightTitle;
    public ImageView iv_rightTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNightModeHelper();
        setContentViews(getContentViewResId());

    }


    private void setContentViews( int layoutResID) {
        setContentView(R.layout.ac_base);
        FrameLayout fl_container = (FrameLayout)findViewById(R.id.fl_container);//子布局容器
        LayoutInflater _inflater = getLayoutInflater();
        View _view = _inflater.inflate(layoutResID, null);
        fl_container.addView(_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            initTitle();
        }
        findViews();
        new ObserverEvent().setMessageEvent(new ObserverEvent.MessageEvent() {
            @Override
            public void Message(MessageData messageData, String[] arr, String data) {
                loadData(messageData,arr,data);
            }
        });
    }

    public void setNightModeHelper(){}
    /**
     * 获取contentView 资源id
     */
    public abstract int getContentViewResId();

    /**
     * 初始化toolbar
     */
    protected void initTitle() {
        if (mToolbar == null) {
            return;
        }
        tv_title = (TextView) findViewById(R.id.toolbar_title);
        tv_rightTitle = (TextView) findViewById(R.id.tv_toolbar_right);
        iv_rightTitle = (ImageView) findViewById(R.id.iv_toolbar_right);
    }

    /**
     * 显示返回按钮
     */
    public void showBackButton() {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.base_toolbar_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    //显示返回按钮
    public void showBackButton(@DrawableRes int res) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(res);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    //显示返回按钮
    public void showBackButton(@DrawableRes int res, @Nullable View.OnClickListener listener) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(res);
            if (listener != null) {
                mToolbar.setNavigationOnClickListener(listener);
            } else {
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();

                    }
                });
            }
        }
    }

    //显示返回按钮
    public void showBackButton(Drawable drawable, @Nullable View.OnClickListener listener) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(drawable);
            if (listener != null) {
                mToolbar.setNavigationOnClickListener(listener);
            } else {
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                });
            }

        }
    }

    public void setRightTitle(String string, View.OnClickListener listener) {
        if (null != tv_rightTitle) {
            tv_rightTitle.setText(string);
            tv_rightTitle.setVisibility(View.VISIBLE);
            tv_rightTitle.setOnClickListener(listener);
        }
    }

    public void setRightImage(@DrawableRes int res, View.OnClickListener listener) {
        if (null != iv_rightTitle) {
            iv_rightTitle.setImageResource(res);
            iv_rightTitle.setVisibility(View.VISIBLE);
            iv_rightTitle.setOnClickListener(listener);
        }
    }

    public void setRightImage(Drawable drawable, View.OnClickListener listener) {
        if (null != iv_rightTitle) {
            iv_rightTitle.setImageDrawable(drawable);
            iv_rightTitle.setVisibility(View.VISIBLE);
            iv_rightTitle.setOnClickListener(listener);
        }
    }


    public void setTitle(String string) {
        if (null != tv_title)
            tv_title.setText(string);
    }

    public void setTitle(int id) {
        if (null != tv_title)
            tv_title.setText(id);
    }

    //初始化控件
    protected abstract void findViews();

    //加载数据
    protected  abstract void  loadData(MessageData messageData, String[] arr, String data);

    protected void start(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}
