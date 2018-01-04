package cn.com.example.smartlife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import cn.com.example.smartlife.Base.NightModeHelper;
import cn.com.example.smartlife.Base.fragment.NavigationFragment;
import cn.com.example.smartlife.adapter.HeaderAndFooterAdapter;
import cn.com.example.smartlife.im.DemoCache;
import cn.com.example.smartlife.im.config.preference.Preferences;
import cn.com.example.smartlife.im.login.LoginActivity;
import cn.com.example.smartlife.message.DataCmd;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationFragment mNavigationFragment;
    private NightModeHelper mNightModeHelper;
    private RecyclerView mRecyclerView;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNightModeHelper = new NightModeHelper(this, R.style.BaseTheme);
        setContentView(R.layout.activity_main);
        findViews();
        //CacheUtils.getInstance().put();
        if ( CacheUtils.getInstance().getString("getAllDevices","0").equals("0")){
            DataCmd.getInstance().getAllDevices();
            CacheUtils.getInstance().put("getAllDevices","1");
        }
        LogUtils.getConfig().toString();
    }

    protected void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_settings_black_36dp);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();

        mTitle = (TextView) findViewById(R.id.title);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                    return;
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setCurrentFragment();
    }
    private void setCurrentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mNavigationFragment = NavigationFragment.newInstance(getString(R.string.menu_0),mTitle);
        transaction.replace(R.id.frame_content, mNavigationFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String TAG = "MainActivity";
    private HeaderAndFooterAdapter headerAndFooterAdapter;
    private void initAdapter() {
        headerAndFooterAdapter = new HeaderAndFooterAdapter();
        mRecyclerView.setAdapter(headerAndFooterAdapter);

        headerAndFooterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e(TAG, "onItemClick: " + position );
                switch (position){
                    case 10:
                        removeLoginState();
                        NIMClient.getService(AuthService.class).logout();
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                }
            }
        });
    }
    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    private void removeLoginState() {
        Preferences.saveUserToken("");
        DemoCache.setAccount("");
    }
}
