package com.example.ordersytem_rest.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.CusineAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CusienActivity extends Activity implements OnClickListener{
	private Button back_btn,edit_btn;
	private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage_cusine);
        initialView();
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
   
	/*
	 * 获取数据源
	 */
	public List<Map<String,Object>>  getData(){
		List<Map<String,Object>>  list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<10;i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("image", R.drawable.app_icon);
			map.put("price", "￥90");
			map.put("cusine_name", "红烧鱼");
			list.add(map);
		}
		return list;
		
	}
	
}
