package com.example.ordersytem_rest.activity;

import com.example.ordersytem_rest.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener{
	private Button back_btn,edit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage_menu);
        initialView();
    }
    private void initialView(){
    	back_btn=(Button)findViewById(R.id.menu_back_btn);
    	edit_btn=(Button)findViewById(R.id.editmenu_btn);
    	back_btn.setOnClickListener(this);
    	edit_btn.setOnClickListener(this);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		   case R.id.menu_back_btn:
			   this.finish();  //返回上一个Activity
			   break;
		   case R.id.editmenu_btn:
			   EditMenuFragment edmenuFragment=new EditMenuFragment();
			   FragmentManager fgm=getFragmentManager();
			   FragmentTransaction begintransaction=fgm.beginTransaction();
			   begintransaction.add(R.id.menu_fragment, edmenuFragment);
			   break;
			   default:
				   break;
		}
	}
}
