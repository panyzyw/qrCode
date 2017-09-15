package com.yongyida.robot.qrcode;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import com.yongyida.robot.qrcode.R;

public class DeleteUserDialog extends DialogFragment implements OnClickListener {

	private Button mLeft_btn;
	private Button mRight_btn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.delete_binding_user_dilog, null);
		mLeft_btn = (Button) view.findViewById(R.id.left);
		mRight_btn = (Button) view.findViewById(R.id.right);
		mLeft_btn.setOnClickListener(this);
		mRight_btn.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			
			break;
		case R.id.right:
			dismiss();
			break;
		default:
			break;
		}
	}

}
