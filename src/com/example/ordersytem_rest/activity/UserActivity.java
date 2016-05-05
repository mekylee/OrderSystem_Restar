package com.example.ordersytem_rest.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ordersytem_rest.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UserActivity extends Activity implements OnClickListener{
	private Button back_btn;
	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_manage_user);
		back_btn=(Button)findViewById(R.id.user_back_btn);
		listview=(ListView)findViewById(R.id.user_listview);
		listview.setAdapter(new SimpleAdapter(this, getData(), R.layout.user_listview_item, new String[]{"user_type","user_name"},new int[]{R.id.user_type_tv,R.id.user_name_tv}));
		back_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.user_back_btn:
			this.finish();
			break;
		case R.id.edituser_btn:
			break;
		}
	
	}
	
	public List<Map<String,Object>> getData(){
		List<Map<String,Object>> list=new ArrayList<>();
		for(int i=0;i<10;i++){
			Map<String,Object> map=new HashMap<>();
			map.put("user_type", "顾客");
			map.put("user_name", "12323@qq.com");
			list.add(map);
			}
		return list;
	}

}
