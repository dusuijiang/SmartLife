package cn.com.example.smartlife.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/6/2
 * Description: Dialog View的处理类
 */
class DialogViewHelper {
    private View mContentView = null;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    /**
     * 通过LayoutId设置布局
     *
     * @param mContext
     * @param mViewLayoutResId
     */
    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
        this();
        this.mContentView = LayoutInflater.from(mContext).inflate(mViewLayoutResId, null);
    }

    /**
     * 通过设置布局
     *
     * @param mView
     */
    public void setContentView(View mView) {
        this.mContentView = mView;
    }

    /**
     * 设置文字
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {

        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    /**
     * 获取View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewReference = mViews.get(viewId);
        View view = null;
        if (viewReference != null) {
            view = viewReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置点击事件监听
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    public View getContentView() {
        return mContentView;
    }
}
