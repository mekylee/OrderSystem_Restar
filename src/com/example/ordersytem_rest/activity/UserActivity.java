package com.example.ordersytem_rest.activity;

import com.example.ordersytem_rest.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserActivity extends Activity implements OnClickListener{
	private Button back_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_manage_user);
		back_btn=(Button)findViewById(R.id.user_back_btn);
		back_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		this.finish();
	}

}
