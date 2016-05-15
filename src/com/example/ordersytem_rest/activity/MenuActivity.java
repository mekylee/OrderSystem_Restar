package com.example.ordersytem_rest.activity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.LogUtil.log;
import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersystem_rest.utils.CustomDialog;
import com.example.ordersystem_rest.utils.NetworkReceiver;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.MenuAdapter;
import com.example.ordersytem_rest.entity.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
@AVClassName("Menu")
public class MenuActivity extends Activity implements OnClickListener,OnItemClickListener{
    private NetworkReceiver networkReceiver;
	private Button back_btn,edit_btn;
	private ListView list_view;
	private static final String TAG=MenuActivity.class.getName();
    private volatile List<Menu> menus;
    private MenuAdapter menuAdapter;
	private class  RemoteDataTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			menus=AVService.findMenus();
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
			//展现ListView
		   menuAdapter=new MenuAdapter(MenuActivity.this, menus);
			list_view.setAdapter(menuAdapter);
			TextView empty=(TextView)findViewById(android.R.id.empty);
			if(menus!=null&&!menus.isEmpty()){
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
        setContentView(R.layout.activity_manage_menu);
        initialView();
        new RemoteDataTask().execute();
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
    private void initialView(){
    	back_btn=(Button)findViewById(R.id.menu_back_btn);
    	edit_btn=(Button)findViewById(R.id.editmenu_btn);	
    	list_view=(ListView)findViewById(R.id.menu_listview);
    	//list_view.setAdapter(new ArrayAdapter<>(this,  android.R.layout.simple_list_item_1, getData()));
    	back_btn.setOnClickListener(this);
    	edit_btn.setOnClickListener(this);
    	list_view.setOnItemClickListener(this);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		   case R.id.menu_back_btn:
			   this.finish();  //返回上一个Activity
			   break;
		   case R.id.editmenu_btn:
			   final EditText edtext=new EditText(this);
			   edtext.setHint("请输入菜单名");
			   new AlertDialog.Builder(this)
			   .setTitle("新增菜单")
			   .setIcon(R.drawable.app_icon)
			   .setView(edtext)
			   .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					AVService.createMenu(edtext.getText().toString(), new SaveCallback() {
						
						@Override
						public void done(AVException arg0) {
							// TODO Auto-generated method stub
							if(arg0==null){
								Log.d("tag", "创建成功---新的菜单名为："+edtext.getText().toString());
								//更新ui
								//MenuAdapter adapter=(MenuAdapter) list_view.getAdapter();
								Menu menu =new Menu();
								menu.setMenuName(edtext.getText().toString());
							    menus.add(menu);
								menuAdapter.notifyDataSetChanged();
							}else{
								Log.d("erro", arg0.toString());
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
			   default:
				   break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {
		//获取当前点击的Item的内容
		final Menu menu=(Menu) list_view.getItemAtPosition(position);
	    final String menuName=menu.getString("menu_name");
		 Log.d("tag","获取到的菜单名为"+menuName );
		// Toast.makeText(MenuActivity.this, "获取到的菜单名为："+menuName,Toast.LENGTH_SHORT).show();;
		   final EditText edtext=new EditText(this);
		   edtext.setText(menuName);
		  
		   new AlertDialog.Builder(this)
		   .setTitle("编辑菜单")
		   .setIcon(R.drawable.app_icon)
		   .setView(edtext)
		   .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
		
			   
			   
			   
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				AVService.createOrUpdateMenu(menu.getObjectId(), edtext.getText().toString(), new SaveCallback() {
					
					@Override
					public void done(AVException arg0) {
						// TODO Auto-generated method stub
						if(arg0==null){
							Log.d("tag", "修改菜单成功");
						   //更新ui
							menus.get(position).setMenuName(edtext.getText().toString());
							menuAdapter.notifyDataSetChanged();
						}
						else{
							Log.e("tag", arg0.toString());
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
		 
		 
		 
	
	}
	

	
	
	
	
	
	
}
