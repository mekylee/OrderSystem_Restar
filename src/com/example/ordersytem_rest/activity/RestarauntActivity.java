package com.example.ordersytem_rest.activity;


import com.example.ordersytem_rest.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RestarauntActivity extends Activity  implements OnClickListener{
	private RelativeLayout name_rl,phone_rl,address_rl;
	private Button name_btn,phone_btn,address_btn,back_btn;
	private TextView name_tv,phone_tv,address_tv;
	private ImageButton image_btn;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_manage_restar);
	initialView();
    }
   
   private void initialView(){
	   name_tv=(TextView)findViewById(R.id.name_tv);
	   phone_tv=(TextView)findViewById(R.id.phone_tv);
	   address_tv=(TextView)findViewById(R.id.address_tv);
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
	   image_btn=(ImageButton)findViewById(R.id.imageButton1);
	   image_btn.setOnClickListener(this);
   }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	    switch(arg0.getId()){
			case R.id.restar_back_btn:
				this.finish();
				break;
			case R.id.resta_name_layout:case R.id.name_btn:
				String name=name_tv.getText().toString();
				new AlertDialog.Builder(this).setTitle("编辑餐厅名称")
				.setView(new EditText(this))
				.setPositiveButton("确定", null)
			    .setNegativeButton("取消", null)
			    .show();
				break;
			case R.id.resta_address_layout:case R.id.address_btn:
				new AlertDialog.Builder(this).setTitle("编辑餐厅地址")
				.setView(new EditText(this))
				.setPositiveButton("确定", null)
			    .setNegativeButton("取消", null)
			    .show();
				break;
				
			case R.id.resta_phone_layout:case R.id.phone_btn:
				new AlertDialog.Builder(this).setTitle("编辑餐厅电话")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(new EditText(this))
				.setPositiveButton("确定", null)
			    .setNegativeButton("取消", null)
			    .show();
				break;
			case R.id.imageButton1:
				break;
			default:
			    break;
		}
	}
}
