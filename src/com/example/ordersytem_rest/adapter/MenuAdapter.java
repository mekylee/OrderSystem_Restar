package com.example.ordersytem_rest.adapter;

import java.util.List;

import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.entity.Menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<Menu> menus;
    private float downX,upX;//点下时获取的X坐标、手指离开时的X坐标
    private Button delet_btn;
    public MenuAdapter(Context context,List<Menu> menus) {
		// TODO Auto-generated constructor stub
    	this.context=context;
    	this.menus=menus;

    	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menus != null ? menus.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(menus !=null){
		return menus.get(position);
		}
		else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if(convertView == null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.menu_listview_item, null);
			viewHolder.menuName=(TextView)convertView.findViewById(R.id.menu_name_tv);
			viewHolder.deleteBtn=(Button)convertView.findViewById(R.id.menu_delete_btn);
		    convertView.setTag(viewHolder);
		}else {
			viewHolder =(ViewHolder)convertView.getTag();
		}
//		Menu menu=menus.get(position);
//		if(menu!=null){
//			viewHolder.menuName.setText(menu.getMenuName());
//		}
		
		//为每个item设置触控监控
	    convertView.setOnTouchListener(new OnTouchListener() {
			
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
				if(holder.deleteBtn!=null){
					if(Math.abs(downX)-upX>35){
						holder.deleteBtn.setVisibility(View.VISIBLE);
						delet_btn=holder.deleteBtn;

						return true;
					}
					return false;//释放事件，使得onitemClick可以执行
				}
				return false;
			}
	    });
			

	   viewHolder.menuName.setText(menus.get(position).getMenuName());
	   
	    //为删除按钮添加监听事件
	    viewHolder.deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    new AlertDialog.Builder(context).setTitle("确认删除").setMessage("是否删除该 菜单？")
			    .setNegativeButton("取消", null)
			    .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						AVService.deleteMenuById(menus.get(position).getObjectId());
						
							if(delet_btn!=null){
								delet_btn.setVisibility(View.GONE);
								menus.remove(position);
								notifyDataSetChanged();
							}
					}
			    })
			    .create().show();
				
			}
		});
	    
	    
	    return convertView;
	
	}
    static class ViewHolder{
    	private TextView menuName;
    	private Button deleteBtn;
    }
}
