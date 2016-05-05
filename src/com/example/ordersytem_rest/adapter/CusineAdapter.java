package com.example.ordersytem_rest.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ordersytem_rest.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CusineAdapter extends BaseAdapter {
   private List<Map<String,Object>> data;
   private Context context;
   private LayoutInflater layoutInflater;
   private ImageView cusine_img;
   private TextView cusine_name_tv,price_tv;
   public CusineAdapter(Context context,List<Map<String, Object>> data){  
       this.context=context;  
       this.data=data;  
       this.layoutInflater=LayoutInflater.from(context);  
   }  
   
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//初始化控件 
			arg1=layoutInflater.inflate(R.layout.listview_item, null);
		    cusine_img=(ImageView)arg1.findViewById(R.id.cusine_img);
		    price_tv=(TextView)arg1.findViewById(R.id.price_tv);
		    cusine_name_tv=(TextView)arg1.findViewById(R.id.cusine_name_tv);
		//绑定数据
		cusine_img.setBackgroundResource((Integer)data.get(arg0).get("cusine_image"));
	    price_tv.setText((String)data.get(arg0).get("price"));
	    cusine_name_tv.setText((String)data.get(arg0).get("cusine_name"));
		return arg1;
	}
	
	
	
	}

