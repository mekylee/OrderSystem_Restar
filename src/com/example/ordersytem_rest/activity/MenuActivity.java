package com.example.ordersytem_rest.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.ordersytem_rest.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.ordersytem_rest.activity.CustomDialog;
public class MenuActivity extends Activity implements OnClickListener{
	private Button back_btn,edit_btn;
	private ListView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage_menu);
        initialView();
    }
    private void initialView(){
    	back_btn=(Button)findViewById(R.id.menu_back_btn);
    	edit_btn=(Button)findViewById(R.id.editmenu_btn);
    	list_view=(ListView)findViewById(R.id.menu_listview);
    	list_view.setAdapter(new ArrayAdapter<>(this,  android.R.layout.simple_list_item_1, getData()));
    	back_btn.setOnClickListener(this);
    	edit_btn.setOnClickListener(this);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		   case R.id.menu_back_btn:
			   this.finish();  //返回上一个Activity
			   break;
		   case R.id.editmenu_btn:
			   CustomDialog.Builder builder = new CustomDialog.Builder(this);  
		        builder.setMessage("新增菜单");  
		        builder.setTitle("新增菜单");  
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
			   
			 /*  new AlertDialog.Builder(this)
			   .setTitle("请输入")
			   .setIcon(android.R.drawable.ic_dialog_info)
			   .setView(new EditText(this))
			   .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			})
			   .setNegativeButton("取消", null)
			   .show();*/
			   break;
			   default:
				   break;
		}
	}
	
	/*
	 * 获取数据源
	 */
	public List<String> getData(){
	    List<String> data = new ArrayList<>();
		data.add("扒类");
		data.add("粤菜");
		data.add("湘菜");
        data.add("汉堡");
		return data;
	}
	
}
