package com.yongyida.robot.qrcode.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yongyida.robot.qrcode.R;
import com.yongyida.robot.qrcode.bean.User;
import com.yongyida.robot.swipemenulistview.BaseSwipListAdapter;

public class AppAdapter extends BaseSwipListAdapter {
 
    protected Context mContext;  
    protected List<User> mDatas = new ArrayList<User>(); 
	
	public AppAdapter(Context context) {
		this.mContext = context;
	}
	
	public List<User> getDataList() {
        return mDatas;
    }
	
	@Override
	public int getCount() {
		Log.i("AppAdapter", mDatas.size() + "");
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);  
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.binding_user_list_item, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        //ApplicationInfo item = (ApplicationInfo) getItem(position);
        //holder.tv_phone.setText(item.loadLabel(mContext.getPackageManager()));
        //holder.tv_name.setText(item.loadLabel(mContext.getPackageManager()));
        User user = (User) getItem(position);
        if(!TextUtils.isEmpty(user.getNickName())){
        	holder.tv_phone.setText(user.getNickName());
        }
        /**
        if(user.getId() != null){
        holder.tv_phone.setText(user.getId().toString());
        }
        
        if(!TextUtils.isEmpty(user.getName())){
        	holder.tv_name.setText(user.getName());
        }
        if(!TextUtils.isEmpty(user.getNickName())){
        	holder.tv_nick.setText(user.getNickName());
        }
        */
        if(user.getController() == 0){
            //holder.tv_online.setText(mContext.getString(R.string.off_oline));
        	holder.tv_online.setText("");
            }else{
            	//holder.tv_online.setText(mContext.getString(R.string.oline));
            	holder.tv_online.setText("");
            }
		return convertView;
	}


	class ViewHolder {
        TextView tv_name;
        TextView tv_phone;
        TextView tv_nick;
        TextView tv_online;
        
        public ViewHolder(View view) {
            tv_phone = (TextView) view.findViewById(R.id.user_phone_tv);
            tv_name = (TextView) view.findViewById(R.id.user_name_tv);
            tv_nick = (TextView) view.findViewById(R.id.user_nick_tv);
            tv_online = (TextView) view.findViewById(R.id.user_online_tv);
            view.setTag(this);
        }
    }
	
}
