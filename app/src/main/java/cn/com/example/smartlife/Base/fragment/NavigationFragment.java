package cn.com.example.smartlife.Base.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import cn.com.example.smartlife.Base.BaseFragment;
import cn.com.example.smartlife.Base.fragment.subfragment.HomeFragment;
import cn.com.example.smartlife.Base.fragment.subfragment.LikeFragment;
import cn.com.example.smartlife.Base.fragment.subfragment.LocationFragment;
import cn.com.example.smartlife.Base.fragment.subfragment.PersonFragment;
import cn.com.example.smartlife.R;
import cn.com.example.smartlife.message.MessageData;

/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description: Bottom Navigation Bar by BottomNavigationBar
 */

public class NavigationFragment extends BaseFragment implements BottomNavigationBar.OnTabSelectedListener {


    private BottomNavigationBar mBottomNavigationBar;
    private HomeFragment mHomeFragment;
    private LocationFragment mLocationFragment;
    private LikeFragment mLikeFragment;
    private PersonFragment mPersonFragment;

    static TextView mTitle;
    private FragmentTransaction mTransaction;

    public static NavigationFragment newInstance(String s, TextView view) {
        NavigationFragment navigationFragment = new NavigationFragment();
        mTitle = view;
        return navigationFragment;
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_bottom_navigation_bar;
    }

    @Override
    protected void findViews(View view) {

        mBottomNavigationBar = (BottomNavigationBar) view.findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home_fill, getString(R.string.item_home)).setInactiveIconResource(R.drawable.home).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.location_fill, getString(R.string.item_location)).setInactiveIconResource(R.drawable.location).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.like_fill, getString(R.string.item_like)).setInactiveIconResource(R.drawable.like).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
              //  .addItem(new BottomNavigationItem(R.drawable.person_fill, getString(R.string.item_person)).setInactiveIconResource(R.drawable.person).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    @Override
    protected void loadData(MessageData messageData, String[] arr, String data) {
        Log.e(TAG, "loadData: " + messageData.toString() );
    }

    private static final String TAG = "NavigationFragment";

    private void setDefaultFragment() {
        mTransaction = getFragmentManager().beginTransaction();
        mHomeFragment = HomeFragment.newInstance(getString(R.string.item_home));
        mTransaction.add(R.id.sub_content, mHomeFragment);
        mLastSelFragment = mHomeFragment;
        mLocationFragment = LocationFragment.newInstance(getString(R.string.item_home));
        mTransaction.add(R.id.sub_content, mLocationFragment);

        mTransaction.hide(mLocationFragment);
        mLikeFragment = LikeFragment.newInstance(getString(R.string.item_home));
        mTransaction.add(R.id.sub_content, mLikeFragment);
        mTransaction.hide(mLikeFragment);
        mPersonFragment = PersonFragment.newInstance(getString(R.string.item_home));
        mTransaction.add(R.id.sub_content, mPersonFragment);
        mTransaction.hide(mPersonFragment);
        mTransaction.commit();
    }

    Fragment mLastSelFragment;

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                mTitle.setText(R.string.item_home);
                if (!(mLastSelFragment instanceof HomeFragment)) {
                    transaction.hide(mLastSelFragment);
                }
                transaction.show(mHomeFragment);
                mLastSelFragment = mHomeFragment;

                break;
            case 1:
                mTitle.setText(R.string.item_location);
                if (!(mLastSelFragment instanceof LocationFragment)) {
                    transaction.hide(mLastSelFragment);
                }
                transaction.show(mLocationFragment);
                mLastSelFragment = mLocationFragment;

                break;
            case 2:
                mTitle.setText(R.string.item_like);
                if (!(mLastSelFragment instanceof LikeFragment)) {
                    transaction.hide(mLastSelFragment);
                }
                transaction.show(mLikeFragment);
                mLastSelFragment = mLikeFragment;

                break;
            case 3:
                mTitle.setText(R.string.item_person);
                if (!(mLastSelFragment instanceof PersonFragment)) {
                    transaction.hide(mLastSelFragment);
                }
                transaction.show(mPersonFragment);
                mLastSelFragment = mPersonFragment;
                break;
        }
        transaction.commit();

    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
    }


}
