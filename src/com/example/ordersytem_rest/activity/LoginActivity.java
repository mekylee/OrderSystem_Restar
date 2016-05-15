package com.example.ordersytem_rest.activity;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.ordersystem_rest.customview.CleanableEditText;
import com.example.ordersystem_rest.utils.NetworkReceiver;
import com.example.ordersytem_rest.R;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
    private Button login_btn,resetpassword_btn;
    private CleanableEditText account_editext, password_editext;
    private  NetworkReceiver networkReceiver; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initialView();
		/*SharedPreferences pre=getSharedPreferences("userinfo",MODE_WORLD_READABLE);
		String username=pre.getString("username", "");
		String password=pre.getString("password", "");
		if(username!=null && password !=null){
			
		}*/
	  
	}
	
	
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
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences.Editor editor=getSharedPreferences("userinfo", MODE_WORLD_WRITEABLE).edit();
		//将用户名和密码添加到编辑器中
		editor.putString("username", account_editext.getText().toString());
		Log.d("username", "用户名为："+account_editext.getText().toString());
		editor.putString("password", password_editext.getText().toString());
		Log.d("username", "密码为："+password_editext.getText().toString());
		//提交编辑器内容
		editor.commit();
		
	}
	
	private void initialView(){
		login_btn=(Button)findViewById(R.id.login_btn);

		resetpassword_btn=(Button)findViewById(R.id.resetpassword_btn);
		account_editext=(CleanableEditText)findViewById(R.id.edit_account);
		password_editext=(CleanableEditText)findViewById(R.id.edit_password);
	    login_btn.setOnClickListener(this);
	    resetpassword_btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String account=account_editext.getText().toString();
		String password=password_editext.getText().toString();
		
		switch(v.getId()){
		case R.id.login_btn:
			loginByEmail(account.trim(),password.trim());
			break;
		case R.id.resetpassword_btn: 
			Intent intent=new Intent(LoginActivity.this,ResetPassActivity.class);
			startActivity(intent);
			Log.i("tag", "跳转到重置密码页面");
			break;
			default:
				break;
				
			
		
		
		}
	}
	
	/*
	 * 
	 */
	public boolean isUsernameOrEmail(String text){
		if(text.contains("@")){
			return false;
		}else{
		return true;
		}
	}
	/**
	 * 登录
	 */
	private void loginByEmail(String email,String password){
		
		if(email.isEmpty()==true||isEmail(email)==false){
			Log.i("tag", "邮箱地址不正确");
			Toast.makeText(LoginActivity.this, "邮箱地址不正确", 2000).show();
		}else if(password.length()<6||password.length()>16){
			Toast.makeText(LoginActivity.this, "密码长度应为6-16位", 2000).show();
			Log.i("tag", "密码长度应为6-16位");
		}
		else {
		AVUser.logInInBackground(email.trim(), password.trim(), new LogInCallback<AVUser>() {
				@Override
				public void done(AVUser arg0, AVException arg1) {
					// TODO Auto-generated method stub
					if(arg1==null){
						Log.i("tag", "登录成功");
						Intent intent=new Intent(LoginActivity.this,MainActivity.class);
						startActivity(intent);
					}else{
						
						Toast.makeText(LoginActivity.this, "邮箱地址不存在",Toast.LENGTH_SHORT).show();
						Log.e("tag", arg1.toString());
					}
				}
			});
		}
	}

	/*
     * 判断电子邮箱地址是否正确
     */
    public boolean isEmail(String email){
   	 String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|"
   	 		+ "(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
   	 Pattern p = Pattern.compile(str);
   	 Matcher m = p.matcher(email);
   	 return m.matches();
    }
    
    /*
     * 
     */
	

	/*
	 * �����û����¼
	 
	private void loginByUsername(String account,String password){
		
		if(account.length()<1||account.length()>20){
			Toast.makeText(LoginActivity.this, "�˺ų���ӦΪ1-20λ", Toast.LENGTH_SHORT).show();
		    Log.i("tag", "�˺ų���ӦΪ1-20λ");
		}
		else if(password.length()<6||password.length()>16){
			Toast.makeText(LoginActivity.this, "���볤��ӦΪ6-16λ", Toast.LENGTH_SHORT).show();

		}
		else {
			AVUser user=new AVUser();
		   user.logInInBackground(account.trim(), password.trim(), new LogInCallback<AVUser>() {
			
			@Override
			public void done(AVUser arg0, AVException arg1) {
				// TODO Auto-generated method stub
				if(arg1==null){
					Log.i("tag", "��¼�ɹ�");
					Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(LoginActivity.this, "���û�δע��", Toast.LENGTH_SHORT);
					//Log.e("tag", "��¼ʧ�ܣ��������������");
				}
			}
		});
		}
		
	}
	
	*/
	
	
	
	
	
	
	
	
	
	
	

	
}
