package com.example.ordersytem_rest.activity;


import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.MenuAdapter;
import com.example.ordersytem_rest.entity.Restaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantActivity extends Activity  implements OnClickListener{
	private RelativeLayout name_rl,phone_rl,address_rl;
	private Button name_btn,phone_btn,address_btn,back_btn;
	private TextView name_tv,phone_tv,address_tv;
	private List<Restaurant> restaraunts;
	private  class RemoteDataTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			getRestInfo();
			return null;
		}
		
	}
   @Override
   protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_manage_restar);
	initialView();
	new RemoteDataTask().execute();
    }
   
   private void initialView(){
	   name_tv=(TextView)findViewById(R.id.rest_name);
	   phone_tv=(TextView)findViewById(R.id.phone);
	   address_tv=(TextView)findViewById(R.id.address);
	   name_rl=(RelativeLayout)findViewById(R.id.resta_name_layout);
	   phone_rl=(RelativeLayout)findViewById(R.id.resta_phone_layout);
	   address_rl=(RelativeLayout)findViewById(R.id.resta_address_layout);
	   name_rl.setOnClickListener(this);
	   phone_rl.setOnClickListener(this);
	   address_rl.setOnClickListener(this);
	   name_btn=(Button)findViewById(R.id.name_btn);
	   phone_btn=(Button)findViewById(R.id.phone_btn);
	   address_btn=(Button)findViewById(R.id.address_btn);
	   name_btn.setOnClickListener(this);
	   phone_btn.setOnClickListener(this);
	   address_btn.setOnClickListener(this);
	   back_btn=(Button)findViewById(R.id.restar_back_btn);
	   back_btn.setOnClickListener(this);
	
   }
   
   //解析LeanCloud返回的餐厅列表获取餐厅的数据
   public void getRestInfo(){
	   restaraunts=AVService.findRestaurants();
	   for(int i=0;i<restaraunts.size();i++){
		   name_tv.setText(restaraunts.get(i).getString("restName"));
		   address_tv.setText(restaraunts.get(i).getString("restAddress"));
		   phone_tv.setText(restaraunts.get(i).getString("restPhone"));
	   }
   }
   
   
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		final EditText edtx=new EditText(this);
		
	    switch(arg0.getId()){
			case R.id.restar_back_btn:
				this.finish();
				break;
			case R.id.resta_name_layout:case R.id.name_btn:
				String restName=name_tv.getText().toString();
				final String restAddress=address_tv.getText().toString();
				final String restPhone=phone_tv.getText().toString();
				edtx.setText(restName);
				new AlertDialog.Builder(this).setTitle("编辑餐厅名称")
				.setView(edtx)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						AVService.createOrUpdateRestInfo("5732ac0f49830c0061c8ff88", edtx.getText().toString(), restAddress, restPhone, new SaveCallback() {
							
							@Override
							public void done(AVException arg0) {
								// TODO Auto-generated method stub
								if(arg0==null){
									Log.d("tag", "成功，修改后的餐厅名为："+edtx.getText().toString());
									Toast.makeText(RestaurantActivity.this,  "成功，修改后的餐厅名为："+edtx.getText().toString(), Toast.LENGTH_SHORT).show();
									//更新ui
									name_tv.setText(edtx.getText());
								}
								else{
									Log.e("tag", "修改失败："+arg0.toString());
								}
							}
						});
					}
				})
			    .setNegativeButton("取消", null)
			    .show();
				break;
			case R.id.resta_address_layout:case R.id.address_btn:
				final String restName1=name_tv.getText().toString();
				final String restAddress1=address_tv.getText().toString();
				final String restPhone1=phone_tv.getText().toString();
				edtx.setText(restAddress1);
				new AlertDialog.Builder(this).setTitle("编辑餐厅地址")
				.setView(edtx)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						AVService.createOrUpdateRestInfo("5732ac0f49830c0061c8ff88",restName1,  edtx.getText().toString(), restPhone1, new SaveCallback() {
							
							@Override
							public void done(AVException arg0) {
								// TODO Auto-generated method stub
								if(arg0==null){
									Log.d("tag", "成功，修改后的餐厅地址为："+edtx.getText().toString());
									Toast.makeText(RestaurantActivity.this,  "成功，修改后的餐厅地址为："+edtx.getText().toString(), Toast.LENGTH_SHORT).show();
									//更新ui
									address_tv.setText(edtx.getText());
								}
								else{
									Log.e("tag", "修改失败："+arg0.toString());
								}
							}
						});
					}
				})
			    .setNegativeButton("取消", null)
			    .show();
				break;
				
			case R.id.resta_phone_layout:case R.id.phone_btn:
				final String restName2=name_tv.getText().toString();
				final String restAddress2=address_tv.getText().toString();
				final String restPhone2=phone_tv.getText().toString();
				edtx.setText(restPhone2);
				new AlertDialog.Builder(this).setTitle("编辑餐厅电话")
				.setView(edtx)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						AVService.createOrUpdateRestInfo("5732ac0f49830c0061c8ff88",restName2, restAddress2,  edtx.getText().toString(), new SaveCallback() {
							
							@Override
							public void done(AVException arg0) {
								// TODO Auto-generated method stub
								if(arg0==null){
									Log.d("tag", "成功，修改后的餐厅电话为："+edtx.getText().toString());
									Toast.makeText(RestaurantActivity.this,  "成功，修改后的餐厅电话为："+edtx.getText().toString(), Toast.LENGTH_SHORT).show();
									//更新ui
									phone_tv.setText(edtx.getText());
								}
								else{
									Log.e("tag", "修改失败："+arg0.toString());
								}
							}
						});
					}
				})
			    .setNegativeButton("取消", null)
			    .show();
				break;
			
			default:
			    break;
		}
	}
}
