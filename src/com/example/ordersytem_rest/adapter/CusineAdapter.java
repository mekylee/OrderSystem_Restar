package com.example.ordersytem_rest.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.entity.Cusine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CusineAdapter extends BaseAdapter {
   private List<Cusine> data;
   private Context context;
   private LayoutInflater layoutInflater;
  
   public CusineAdapter(Context context,List<Cusine> data){  
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
		ViewHolder viewholder=null;
		if(arg1==null){
		    viewholder=new ViewHolder();
			arg1=layoutInflater.inflate(R.layout.listview_item, null);
		    viewholder.cusine_img=(ImageView)arg1.findViewById(R.id.cusine_img);
		    viewholder.price_tv=(TextView)arg1.findViewById(R.id.price_tv);
		    viewholder.cusine_name_tv=(TextView)arg1.findViewById(R.id.cusine_name_tv);
		    arg1.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)arg1.getTag();
		}
		Cusine cusine=data.get(arg0);
		//绑定数据
		viewholder.cusine_img.setBackgroundResource((Integer)data.get(arg0).get("cusine_image"));
		viewholder.price_tv.setText(cusine.getPrice().toString());
		viewholder.cusine_name_tv.setText((String)data.get(arg0).get("cusine_name"));
		return arg1;
	}
	
	class ViewHolder{
		 private ImageView cusine_img;
		 private TextView cusine_name_tv,price_tv;
		 
	}
	
	}


