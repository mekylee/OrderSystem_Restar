package com.example.ordersytem_rest.adapter;

import java.util.List;

import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.entity.Menu;
import com.example.ordersytem_rest.entity.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {
    private List<User> users;
    private Context context;
    
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewholder=null;
		if(convertView==null){
			viewholder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.user_listview_item, null);
			viewholder.username=(TextView)convertView.findViewById(R.id.user_name_tv);
			viewholder.usertype=(TextView)convertView.findViewById(R.id.user_type_tv);
			convertView.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)convertView.getTag();
		}
		User user=users.get(position);
		if(user!=null){
			viewholder.username.setText(user.getEmail());
			viewholder.usertype.setText(user.getUserType());
		}
	   return convertView;
	}
	
	class ViewHolder{
		private TextView username;
		private TextView usertype;
	}

}
