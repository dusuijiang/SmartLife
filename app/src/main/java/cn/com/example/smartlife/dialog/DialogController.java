package cn.com.example.smartlife.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/6/2
 * Description:
 */
class DialogController {
    private BaseDialog mBaseDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public DialogController(BaseDialog baseDialog, Window window) {
        this.mBaseDialog = baseDialog;
        this.mWindow = window;
    }

    /**
     * 获取dialog
     *
     * @return
     */
    public BaseDialog getDialog() {
        return mBaseDialog;
    }

    /**
     * 获取dialog的window
     *
     * @return
     */
    public Window getWindow() {
        return mWindow;
    }

    /**
     * 设置ViewHelper
     *
     * @param viewHelper
     */
    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    /**
     * 设置文字
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    /**
     * 获取View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     * 设置点击事件监听
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        mViewHelper.setOnClickListener(viewId, onClickListener);
    }

    public static class DialogParams {
        public Context mContext;
        public int mThemeResId;
        //点击空白是否能够取消
        public boolean mCancelable = true;
        // dialog Cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        // dialog消失监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        // dialog按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        // dialog布局View
        public View mView;
        // dialog布局Layout Id
        public int mViewLayoutResId;
        // 存放文字的修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        // 存放点击事件的监听
        public SparseArray<View.OnClickListener> mOnClickListenerArray = new SparseArray<>();
        //宽度  默认自适应
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //高度  默认自适应
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //弹出方向
        public int mGravity = Gravity.CENTER;
        //动画
        public int mAnimations = 0;

        //x柱偏移
        public int mXoffset = 0;

        //y柱偏移
        public int mYoffset = 0;

        public DialogParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         *
         * @param mController
         */
        public void apply(DialogController mController) {
            DialogViewHelper viewHelper = null;

            //设置dialog布局
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                Toast.makeText(mContext, "布局为空", Toast.LENGTH_LONG).show();
                return;
            }

            mController.getDialog().setContentView(viewHelper.getContentView());

            //设置Controller辅助类
            mController.setViewHelper(viewHelper);


            //设置文字
            for (int i = 0; i < mTextArray.size(); i++) {
                mController.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            //设置点击事件
            for (int i = 0; i < mOnClickListenerArray.size(); i++) {
                mController.setOnClickListener(mOnClickListenerArray.keyAt(i), mOnClickListenerArray.valueAt(i));
            }
            //自定义效果  全屏  弹出方式  默认动画
            Window window = mController.getWindow();

            //设置弹出方向
            window.setGravity(mGravity);

            //设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }

            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            params.x = mXoffset;
            params.y = mYoffset;
            window.setAttributes(params);
        }
    }


}
