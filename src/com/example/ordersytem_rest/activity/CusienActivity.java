package com.example.ordersytem_rest.activity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.LogUtil.log;
import com.example.ordersystem_rest.utils.CustomDialog;
import com.example.ordersystem_rest.utils.NetworkReceiver;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.CusineAdapter;
import com.example.ordersytem_rest.entity.Cusine;
import com.example.ordersytem_rest.entity.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
@AVClassName("Comment")
public class CusienActivity extends Activity implements OnClickListener,OnItemClickListener{
	private Button back_btn,edit_btn;
	private ListView listview;
    private NetworkReceiver networkReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage_cusine);
        initialView();
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
    	back_btn=(Button)findViewById(R.id.cusine_back_btn);
    	edit_btn=(Button)findViewById(R.id.editcusine_btn);
    	listview=(ListView)findViewById(R.id.cusine_listview);
    	List<Map<String,Object>> list=getData();
    	back_btn.setOnClickListener(this);
    	edit_btn.setOnClickListener(this);
    	listview.setAdapter(new SimpleAdapter(this, getData(),R.layout.listview_item,
    			new String[]{"image","price","cusine_name"}, new int[]{R.id.cusine_img,R.id.price_tv,R.id.cusine_name_tv}));
        listview.setOnItemClickListener(this);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		   case R.id.cusine_back_btn:
			   this.finish();  //返回上一个Activity
			   break;
		   case R.id.editcusine_btn:
			   new AlertDialog.Builder(this).setTitle("请输入")
			   .setIcon(android.R.drawable.ic_dialog_info)
			   .setView(new EditText(this))
			   .setPositiveButton("确定", null)
			   .setNegativeButton("取消", null)
			   .show();
			   break;
			   default:
				   break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		 CustomDialog.Builder builder = new CustomDialog.Builder(this);  
	        builder.setMessage("编辑菜品");  
	        builder.setTitle("编辑菜品");  
	        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();  
	                //设置你的操作事项  
	            }  
	        });  
	  
	        builder.setNegativeButton("取消",  
	                new android.content.DialogInterface.OnClickListener() {  
	                    public void onClick(DialogInterface dialog, int which) {  
	                        dialog.dismiss();  
	                    }  
	                });  
	  
	        builder.create().show();  
	}
   
	/*
	 * 获取菜品名、菜品类型、菜品单价
	 */
	public List<Map<String,Object>>  getData(){
		final List<Map<String,Object>>  data=new ArrayList<Map<String,Object>>();
		AVQuery<Cusine> query=new AVQuery<Cusine>();
		query.findInBackground(new FindCallback<Cusine>() {
			
			@Override
			public void done(List<Cusine> arg0, AVException arg1) {
				// TODO Auto-generated method stub
				if(arg1==null){
					for(int i=0;i<arg0.size();i++){
						Map<String,Object> map=new HashMap<String, Object>();
						//map.put("image", R.);
					}
				}else{
					log.e("error", arg1.toString());
				}
			}
		});
		for(int i=0;i<10;i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("image", R.drawable.app_icon);
			map.put("price", "￥90");
			map.put("cusine_name", "红烧鱼");
			data.add(map);
		}
		return data;
		
	}
	/**
	 * 根据菜品来查找菜品
	 */
	public Cusine findCusineByName(String cusine_name ){
		Cusine cusine=new Cusine();
		AVQuery<Cusine> query=new AVQuery<Cusine>();  
		List<Cusine> cusine_list=new ArrayList<Cusine>();
		try {
			cusine_list = query.find();//返回的是Cusine对象List列表
			for(int i=0;i<cusine_list.size();i++){
				if(cusine_list.get(i).getCusineName()==cusine_name){
					cusine=cusine_list.get(i);
				}
			}
		} catch (AVException e) {
			// TODO Auto-generated catch block
			Log.i("error",e.toString());
			
		}
		
		return cusine;
		
	}
	/**
	 * 新增菜品
	 * 上传图片、菜品名称、选择菜品类型、菜品单价
	 */
	 public void addCusine(){
	 	 //上传图片
		 try {
			AVFile image=AVFile.withAbsoluteLocalPath("food1.jpg", Environment.getExternalStorageDirectory()+"/food1.jpg");
		    image.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(AVException arg0) {
					// TODO Auto-generated method stub
					if(arg0==null){
						//上传成功，提示
						Toast.makeText(CusienActivity.this, "照片上传成功", Toast.LENGTH_SHORT).show();
						Log.i("tag","菜品照片上传成功");
					}else{
						Log.e("error",arg0.toString());
					}
				}
			},new ProgressCallback() {    //进度
				
				@Override
				public void done(Integer arg0) {
					// TODO Auto-generated method stub
                     Log.i("tag","图片上传进度为:"+arg0);
				}
			});
		 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * 删除指定菜品
	  */
	 public void deleteCusine(final String cusine_name){
		 AVQuery<Cusine> query=new AVQuery<Cusine>();
			query.findInBackground(new FindCallback<Cusine>() {
				
				@Override
				public void done(List<Cusine> arg0, AVException arg1) {
					// TODO Auto-generated method stub
					 for(int i=0;i<arg0.size();i++){
						 if(arg0.get(i).getCusineName()==cusine_name){
							 arg0.get(i).deleteInBackground();
						 }
					 }
				}
			});
	 }
	 
	
	
}
