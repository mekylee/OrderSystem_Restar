package com.example.ordersytem_rest.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogUtil.log;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.example.ordersystem_rest.customview.CleanableEditText;
import com.example.ordersytem_rest.R;
public class ResetPassActivity extends Activity implements OnClickListener{
	private Button reset_btn,back_btn;
	private CleanableEditText email;
//	private NetworkReceiver networkReceiver;
//	util util=new util();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_resetpass);
		email=(CleanableEditText)findViewById(R.id.reset_txt);
		reset_btn=(Button)findViewById(R.id.reset_btn);
		back_btn=(Button)findViewById(R.id.back_btn);
		back_btn.setOnClickListener(this);
		reset_btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		   case R.id.back_btn:
			   this.finish();
			   break;
		   case R.id.reset_btn:
				//�������������ʼ���������
				sendResetEmail(email.getText().toString());
				break;
				default:
					break;
		}
	}
	private void sendResetEmail(String email){
		//���Email�ĸ�ʽ�Ƿ���ȷ
		if(isEmail(email)==false||email.isEmpty()==true){
			Toast.makeText(ResetPassActivity.this, "�����ַ����ȷ", 1000).show();
		}else{
			AVUser.requestPasswordResetInBackground(email.trim(), new RequestPasswordResetCallback() {

				@Override
				public void done(AVException arg0) {
					// TODO Auto-generated method stub
					if(arg0==null){
						Toast.makeText(ResetPassActivity.this, "�뵽������������", 1000).show();
						Log.i("tag","�������������ʼ��ɹ�");
						
					}
					else{
						Toast.makeText(ResetPassActivity.this, "�����ʼ�ʧ�ܣ���������", 1000).show();
						Log.i("tag", arg0.toString());
					}
				}
			});
		}
	}
	 public boolean isEmail(String email){
	   	 String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	   	 Pattern p = Pattern.compile(str);
	   	 Matcher m = p.matcher(email);
	   	 return m.matches();
	    }
/*	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		networkReceiver =new NetworkReceiver();
		registerReceiver(networkReceiver, filter);
	}
	
     @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	unregisterReceiver(networkReceiver);  
    }
	*/

}
