package com.example.ordersytem_rest.adapter;

import java.util.List;

import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.MenuAdapter.ViewHolder;
import com.example.ordersytem_rest.entity.Menu;
import com.example.ordersytem_rest.entity.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {
    private List<User> users;
    private Context context;
    private float downX,upX;//点下时获取的X坐标、手指离开时的X坐标
    private Button delet_btn;
	public UserAdapter(Context context,List<User> users) {
		super();
		this.users = users;
		this.context = context;
	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users != null ? users.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(users!=null){
		return users.get(position);
		}
		else{
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
		ViewHolder viewholder=null;
		if(convertView==null){
			viewholder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.user_listview_item, null);
			viewholder.username=(TextView)convertView.findViewById(R.id.user_name_tv);
			viewholder.usertype=(TextView)convertView.findViewById(R.id.user_type_tv);
			viewholder.delete=(Button)convertView.findViewById(R.id.user_delete_btn);
			convertView.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)convertView.getTag();
		}
		User user=users.get(position);
		if(user!=null){
			viewholder.username.setText(user.getUsername());
			viewholder.usertype.setText(user.getUserType());
		}
		
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
				if(holder.delete!=null){
					if(Math.abs(downX)-upX>35){
						holder.delete.setVisibility(View.VISIBLE);
						delet_btn=holder.delete;

						return true;
					}
					return false;//释放事件，使得onitemClick可以执行
				}
				return false;
			}
	    });
			

	   viewholder.username.setText(users.get(position).getUsername());
	   viewholder.usertype.setText(users.get(position).getUserType());
	   
	    //为删除按钮添加监听事件
	    viewholder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    new AlertDialog.Builder(context).setTitle("确认删除").setMessage("是否删除该 菜单？")
			    .setNegativeButton("取消", null)
			    .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						AVService.deleteUserById(users.get(position).getObjectId());
						
							if(delet_btn!=null){
								delet_btn.setVisibility(View.GONE);
								users.remove(position);
								notifyDataSetChanged();
							}
					}
			    })
			    .create().show();
				
			}
		});
	    
	   return convertView;
	}
	
	class ViewHolder{
		private TextView username;
		private TextView usertype;
		private Button  delete;
	}

}
