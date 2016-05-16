package com.example.ordersytem_rest.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVDeleteOption;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRole;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersystem_rest.utils.CustomDialog;
import com.example.ordersystem_rest.utils.NetworkReceiver;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.MenuAdapter;
import com.example.ordersytem_rest.adapter.UserAdapter;
import com.example.ordersytem_rest.entity.Menu;
import com.example.ordersytem_rest.entity.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends Activity implements OnClickListener,OnItemClickListener{
	private NetworkReceiver networkReceiver;
	private Button back_btn;
	private ListView listview;
	private volatile List<User> users;
	private List<String> usertypeList;
	private UserAdapter userAdapter;
	private Button add_btn;
	private Spinner sp_usertype;
	private String user_type;
    private  class RemoeDataTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//查询用户数据
			users=AVService.findUsers();
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//展示ListView
			userAdapter=new UserAdapter(UserActivity.this,users);
			listview.setAdapter(userAdapter);
	
			TextView empty=(TextView)findViewById(android.R.id.empty);
			if(users!=null&&!users.isEmpty()){
				empty.setVisibility(View.INVISIBLE);
			}else{
				empty.setVisibility(View.VISIBLE);
			}
			super.onPostExecute(result);
		}
    	
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_manage_user);
		back_btn=(Button)findViewById(R.id.user_back_btn);
		listview=(ListView)findViewById(R.id.user_listview);
		new RemoeDataTask().execute();
		back_btn.setOnClickListener(this);
		listview.setOnItemClickListener(this);
		add_btn=(Button)findViewById(R.id.adduser_btn);
		add_btn.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.user_back_btn:
			this.finish();
			break;
		case R.id.adduser_btn:
			  View dialogView=LayoutInflater.from(UserActivity.this).inflate(R.layout.dialog, null);
			  final EditText ed_username=(EditText) dialogView.findViewById(R.id.ed_username);
			  ed_username.setHint("请输入邮箱地址");
			 
			  sp_usertype=(Spinner)dialogView.findViewById(R.id.spinner_usertype);
			  usertypeList=getUserType();   //获取用户类型列表
			   sp_usertype.setAdapter(new ArrayAdapter<String>(UserActivity.this,android.R.layout.simple_spinner_dropdown_item, usertypeList));
			   String type=(String) sp_usertype.getSelectedItem();
			   Log.d("tag", "获取到的用户类型为:"+type);
			   //获取下拉列表的数据
			   sp_usertype.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					user_type=(String) sp_usertype.getSelectedItem();
					Log.d("tag", "新增的用户类型为："+user_type);
					//如果选择的是顾客，为顾客分配顾客角色；如果选择的是经理，为经理分配角色；如果选择的是服务员，为经理分配经理角色
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			   new AlertDialog.Builder(this)
			   .setTitle("新增用户")
			   .setIcon(R.drawable.profile_img)
			   .setView(dialogView)
			   .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					AVService.createUser(ed_username.getText().toString(),user_type,new SaveCallback() {
						
						@Override
						public void done(AVException arg0) {
							// TODO Auto-generated method stub
							if(arg0==null){
								Log.d("tag", "创建成功---新的用户为："+ed_username.getText().toString());
								//更新ui
								//MenuAdapter adapter=(MenuAdapter) list_view.getAdapter();
								User user =new User();
								user.setUsername(ed_username.getText().toString());
								user.setUserType(user_type);
								users.add(user);
								userAdapter.notifyDataSetChanged();
							}else{
								Log.d("修改失败：", arg0.toString());
							}
						}
					});
				}
			})
			   .setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			   .show();
			break;
		}
	
	}
	
	//为用户类型下拉列表添加数据
	public List<String> getUserType(){
	      List<String> usertype_list=new ArrayList<String>();
	      usertype_list.add("顾客");
	      usertype_list.add("经理");
	      usertype_list.add("服务员");
	      usertype_list.add("管理员");
	      return usertype_list;
	}
	
	//点击listview的item触发对话框进行编辑用户操作
	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {
		//获取当前点击的Item的内容
	
	    final User user=(User)listview.getItemAtPosition(position);
	  //Log.d("tag",listview.getItemAtPosition(position).toString());
		String username = user.getString("email");
	    final String usertype=user.getString("usertype");
		//Toast.makeText(UserActivity.this, "获取到的用户名为："+username+" "+"类型为："+usertype,Toast.LENGTH_SHORT).show();
		Log.d("tag","获取到的用户名为"+username+""+"类型为："+usertype );
		   //通过LayoutInflater来加载一个xml布局作为一个View对象
		   View dialogView=LayoutInflater.from(UserActivity.this).inflate(R.layout.dialog, null);
		   final EditText ed_username=(EditText) dialogView.findViewById(R.id.ed_username);
		   final Spinner sp_usertype=(Spinner)dialogView.findViewById(R.id.spinner_usertype);
		   ed_username.setText(username);
		   usertypeList=getUserType();
		   sp_usertype.setAdapter(new ArrayAdapter<String>(UserActivity.this,android.R.layout.simple_spinner_dropdown_item, usertypeList));
		   sp_usertype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//获取到所选择的内容
				user_type=(String) sp_usertype.getItemAtPosition(position);
				Toast.makeText(UserActivity.this,"选择后的用户类型为："+user_type,Toast.LENGTH_SHORT);
				Log.d("tag", "选择后的用户类型为："+usertype);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		   
		   new AlertDialog.Builder(this)
		   .setTitle("编辑用户")
		   .setIcon(R.drawable.profile_img)
		   .setView(dialogView)
		   .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
					AVService.createOrUpdateUser(user.getObjectId(), ed_username.getText().toString(), usertype, new SaveCallback() {
						
						@Override
						public void done(AVException arg0) {
							// TODO Auto-generated method stub
							if(arg0==null){
								Log.d("tag", "修改用户成功");
							//	Toast.makeText(UserActivity.this, "修改成功,修改后的用户名为："+user.getEmail(), Toast.LENGTH_SHORT).show();
							   //更新ui
								users.get(position).setEmail(ed_username.getText().toString());
								Log.d("tag", "修改后的用户名为："+users.get(position).getEmail());
							
						       // users.get(position).setUsername(ed_username.getText().toString());
						    	//Log.d("tag", "修改后的邮箱地址为："+users.get(position).getUsername());
								userAdapter.notifyDataSetChanged();
							}else{
								Log.d("tag", "修改失败"+arg0.toString());
							}
						}
					});
				}
				
			}
		)
		   .setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		   .show();
		
	}
	
	/**
	 * 新增新用户
	 */
	public AVUser addUser(String email,String password){
		 AVUser user=new AVUser();
	     user.setUsername(email);
	     user.setEmail(email);
	     user.setPassword(password);
	     user.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				if(arg0==null){
					//更新ListView
				}else{
					Log.e("error","添加失败:"+arg0.toString());
				}
			}
		});
	     return user;
	}
	/*
	 * 为用户分配角色
	 */
	public void authority(String  type,final AVUser user){
		switch(type){
		    case  "admin":
				AVQuery<AVRole> query=new AVQuery<AVRole>();
                query.getInBackground("57307d3bf38c840067ce031f", new GetCallback<AVRole>() {
					
					@Override
					public void done(AVRole arg0, AVException arg1) {
						// TODO Auto-generated method stub
						arg0.getUsers().add(user);//赋予用户admin的角色
					}
				});              
		    	break;
		    case "waiter":
		    	AVQuery<AVRole> query1=new AVQuery<AVRole>();
                query1.getInBackground("57307e9ff38c840067ce1581", new GetCallback<AVRole>() {
					
					@Override
					public void done(AVRole arg0, AVException arg1) {
						// TODO Auto-generated method stub
						arg0.getUsers().add(user);//赋予用户waiter的角色
					}
				});     
		    	break;
		    case "manager":
		    	break;
		    case "customer":
		    	break;
		    	
		 }
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
}  
