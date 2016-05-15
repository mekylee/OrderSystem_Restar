package com.example.ordersystem_rest.utils;
import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.LogUtil.log;
import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.entity.Cusine;
import com.example.ordersytem_rest.entity.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class test extends Activity{
	private TextView tv;
	private  List<Cusine> cusine=new ArrayList<>();
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		tv=(TextView)findViewById(R.id.text);
		cusine=AVService.findCusines();
		for(int i=0;i<cusine.size();i++){
			String cusine_name=cusine.get(i).getCusineName();
			Menu cusine_type=cusine.get(i).getCusineType();
			String type=cusine_type.getMenuName();
			Toast.makeText(this, "菜品类型为："+type+" "+"菜品名为："+cusine_name, Toast.LENGTH_SHORT).show();
		    log.d("菜品类型为："+type+" "+"菜品名为："+cusine_name);
		}
		
	}
	
	    	
   
}
