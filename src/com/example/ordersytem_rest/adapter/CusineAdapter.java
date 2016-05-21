package com.example.ordersytem_rest.adapter;

import java.util.List;




import com.avos.avoscloud.AVFile;
import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersystem_rest.utils.ImageLoader;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.UserAdapter.ViewHolder;
import com.example.ordersytem_rest.entity.Cusine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CusineAdapter extends BaseAdapter {
   private List<Cusine> data;
   private Context context;
   private LayoutInflater layoutInflater;
   private float downX,upX;//点下时获取的X坐标、手指离开时的X坐标
   private Button delet_btn;
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
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewholder=null;
		if(arg1==null){
		    viewholder=new ViewHolder();
			arg1=layoutInflater.inflate(R.layout.cusine_listview_item, null);
		    viewholder.cusine_img=(ImageView)arg1.findViewById(R.id.cusine_img);
		    viewholder.price_tv=(TextView)arg1.findViewById(R.id.price_tv);
		    viewholder.cusine_name_tv=(TextView)arg1.findViewById(R.id.cusine_name_tv);
		    viewholder.delete_btn=(Button)arg1.findViewById(R.id.cusine_delete_btn);
		    arg1.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)arg1.getTag();
		}
		Cusine cusine=data.get(arg0);
		//绑定数据
		viewholder.cusine_img.setImageResource(R.drawable.app_icon);
		viewholder.cusine_img.setTag(cusine.getAVFile("cusine_image").getUrl());
		new ImageLoader().showImageByThread(viewholder.cusine_img, cusine.getAVFile("cusine_image").getUrl());
		viewholder.price_tv.setText(Integer.toString(cusine.getInt("cusine_price")));
		Log.i("tag", "获取到的价格为："+Integer.toString(cusine.getInt("cusine_price")));
		viewholder.cusine_name_tv.setText((String)cusine.get("cusine_name"));
		
		//为每个item设置触控监控
	    arg1.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final ViewHolder holder=(ViewHolder) v.getTag();
				switch(event.getAction()){
				   case MotionEvent.ACTION_DOWN:   //手指按下
					   downX=event.getX();//获取手指X坐标
					   if(delet_btn!=null){
						   delet_btn.setVisibility(View.GONE);
					   }
					   break;
				   case MotionEvent.ACTION_UP: //手指离开
					   upX=event.getX();
					   break;
				}
				if(holder.delete_btn!=null){
					if(Math.abs(downX)-upX>35){
						holder.delete_btn.setVisibility(View.VISIBLE);
						delet_btn=holder.delete_btn;

						return true;
					}
					return false;//释放事件，使得onitemClick可以执行
				}
				return false;
			}
	    });   
	    //为删除按钮添加监听事件
	    viewholder.delete_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    new AlertDialog.Builder(context).setTitle("确认删除").setMessage("是否删除该 菜单？")
			    .setNegativeButton("取消", null)
			    .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						AVService.deleteCusineById(data.get(arg0).getObjectId());
						
							if(delet_btn!=null){
								delet_btn.setVisibility(View.GONE);
								data.remove(arg0);
								notifyDataSetChanged();
							}
					}
			    })
			    .create().show();
				
			}
		});
	    
		
		return arg1;
	}
	
	class ViewHolder{
		 private ImageView cusine_img;
		 private TextView cusine_name_tv,price_tv;
		 private Button delete_btn;
		 
	}
	
	}


