package com.yongyida.robot.qrcode;

import java.net.Socket;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.yongyida.robot.qrcode.adapter.AppAdapter;
import com.yongyida.robot.qrcode.bean.User;
import com.yongyida.robot.qrcode.constant.IntentConstant;
import com.yongyida.robot.qrcode.R;
import com.yongyida.robot.swipemenulistview.SwipeMenu;
import com.yongyida.robot.swipemenulistview.SwipeMenuCreator;
import com.yongyida.robot.swipemenulistview.SwipeMenuItem;
import com.yongyida.robot.swipemenulistview.SwipeMenuListView;
import com.yongyida.robot.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.yongyida.robot.version.VersionControl;

public class UserActivity extends Activity{

	private static final String TAG = "UserActivityTAG";
    private SwipeMenuListView mListView;
    //private List<ApplicationInfo> mAppList;
    private AppAdapter mAdapter;
    private Button mBack_btn;
    public static SwipeRefreshLayout mRefreshLayout;
    private DeleteUserDialog deleteUserDialog;
    Socket socket = null;
    private UserReceiver mReceiver;
    private User mUser;
    private List<User> mList;

    Intent mIntent;

    private class UserReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(IntentConstant.RESLUT)) {

//                Log.d(TAG, "适配器数据更新完成");

                String json = intent.getExtras().getString("result");
                mList = JsonUtils.getUser(json);
                if (mList == null || mList.size() == 0) {
                    refreshing();
                }
                Log.i("User", "User:" + mList.toString());
                mAdapter.getDataList().clear();
                mAdapter.getDataList().addAll(mList);
                mAdapter.notifyDataSetChanged();

                refreshing();
                //Toast.makeText(getApplicationContext(), action.toString(), Toast.LENGTH_SHORT).show();
            } else if (action.equals(IntentConstant.DRESLUT)) {

            }
        }
    }

    private void refreshing() {

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binding_user_list);
        
        //
        TextView tvBindUserList = (TextView) findViewById(R.id.binding_user_list_tv);
        tvBindUserList.setText(getString(VersionControl.mBindUserListTV));
        
        mIntent = new Intent(IntentConstant.QUERY);
        sendBroadcast(mIntent);
        Log.i("User", mIntent.getAction().toString());
        mReceiver = new UserReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentConstant.RESLUT);
        intentFilter.addAction(IntentConstant.DRESLUT);
        this.registerReceiver(mReceiver, intentFilter);
        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        mBack_btn = (Button) findViewById(R.id.binding_user_list_back_btn);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.view_swipe_refresh);
        //mAppList = getPackageManager().getInstalledApplications(0);
        mAdapter = new AppAdapter(UserActivity.this);
        mListView.setAdapter(mAdapter);


        mRefreshLayout.setColorSchemeColors(Color.BLUE);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendBroadcast(mIntent);
            }
        });

        mBack_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                /**
                 // create "open" item
                 SwipeMenuItem openItem = new SwipeMenuItem(
                 getApplicationContext());
                 // set item background
                 openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                 0xCE)));
                 // set item width
                 openItem.setWidth(dp2px(90));
                 // set item title
                 openItem.setTitle("Open");
                 // set item title fontsize
                 openItem.setTitleSize(18);
                 // set item title font color
                 openItem.setTitleColor(Color.WHITE);
                 // add to menu
                 menu.addMenuItem(openItem);
                 */
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator, mRefreshLayout);
        mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //ApplicationInfo item = mAppList.get(position);
                User user = mList.get(position);
                switch (index) {
                    case 0:
                        // delete
//					delete(item);
                        Intent intent = new Intent(IntentConstant.DELETE);
                        intent.putExtra("id", user.getId().toString());
                        sendBroadcast(intent);
                        mList.remove(position);
                        Log.i("user", mList.size() + "listsize");
                        mAdapter.getDataList().remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());
        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                //TextView tv_name = (TextView) parent.getChildAt(position).findViewById(R.id.user_name_tv);
                //tv_name.setTextColor(getResources().getColor(R.color.binding_user_list_selected_text_blue));
                //Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                //deleteUserDialog = new DeleteUserDialog();
                //deleteUserDialog.show(getFragmentManager(), "DeleteUserDialog");
                //deleteUserDialog.setCancelable(false);
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /**
         if (id == R.id.action_left) {
         mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
         return true;
         }
         */
        if (id == R.id.action_right) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mReceiver);
    }

    public Adapter getAdapter() {
        return mAdapter;
    }
}
