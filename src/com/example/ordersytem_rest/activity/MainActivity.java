package com.example.ordersytem_rest.activity;

import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.R.id;
import com.example.ordersytem_rest.R.layout;
import com.example.ordersytem_rest.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener{
    //private RelativeLayout menu_layout,cusine_layout,order_layout,restar_layout,statstic_layout,user_layout;
    private Button menu_btn,go_menu_btn;
    private Button cusine_btn,go_cusine_btn;
    private Button order_btn,go_order_btn;
    private Button resta_btn,go_resta_btn;
    private Button statistic_btn,go_statistic_btn;
    private Button user_btn,go_user_btn;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initialView();
		
	}
	private void initialView(){
	/*	menu_layout=(RelativeLayout)findViewById(R.id.menu_layout);
		cusine_layout=(RelativeLayout)findViewById(R.id.cusine_layout);
		order_layout=(RelativeLayout)findViewById(R.id.order_layout);
		restar_layout=(RelativeLayout)findViewById(R.id.resta_layout);
		statstic_layout=(RelativeLayout)findViewById(R.id.resta_layout);
		user_layout=(RelativeLayout)findViewById(R.id.user_layout);*/
		menu_btn=(Button)findViewById(R.id.menu_btn);
		order_btn=(Button)findViewById(R.id.order_btn);
		cusine_btn=(Button)findViewById(R.id.cusine_btn);
		resta_btn=(Button)findViewById(R.id.restaraunt_btn);
		statistic_btn=(Button)findViewById(R.id.statistic_btn);
		user_btn=(Button)findViewById(R.id.user_btn);
		go_menu_btn=(Button)findViewById(R.id.go_menu_btn);
		go_cusine_btn=(Button)findViewById(R.id.go_cusine_btn);
		go_order_btn=(Button)findViewById(R.id.go_order_btn);
		go_resta_btn=(Button)findViewById(R.id.go_restaraunt_btn);
		go_statistic_btn=(Button)findViewById(R.id.go_statistic_btn);
		go_user_btn=(Button)findViewById(R.id.go_user_btn);
	/*	menu_layout.setOnClickListener(this);
		cusine_layout.setOnClickListener(this);
		order_layout.setOnClickListener(this);
		restar_layout.setOnClickListener(this);
		statstic_layout.setOnClickListener(this);
		user_layout.setOnClickListener(this);*/
		menu_btn.setOnClickListener(this);
		cusine_btn.setOnClickListener(this);
		order_btn.setOnClickListener(this);
        resta_btn.setOnClickListener(this);
        statistic_btn.setOnClickListener(this);
        user_btn.setOnClickListener(this);
        go_cusine_btn.setOnClickListener(this);
        go_menu_btn.setOnClickListener(this);
        go_order_btn.setOnClickListener(this);
        go_resta_btn.setOnClickListener(this);
        go_statistic_btn.setOnClickListener(this);
        go_user_btn.setOnClickListener(this);
        
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	   switch(arg0.getId()){
	     case R.id.menu_btn:case R.id.go_menu_btn:
	    	  Intent intent=new Intent(MainActivity.this,MenuActivity.class);
	    	  startActivity(intent);
	    	  Log.i("tag", "跳转到菜单管理页面");
	    	  break;
	      case R.id.cusine_btn:case R.id.go_cusine_btn:
	    	  Intent intent1=new Intent(MainActivity.this,CusienActivity.class);
	    	  startActivity(intent1);
	    	  Log.i("tag", "跳转到菜品管理页面");
	    	  break;
	     case R.id.restaraunt_btn:case R.id.go_restaraunt_btn:
	    	  Intent intent2=new Intent();
	    	  intent2.setClass(getApplicationContext(), RestarauntActivity.class);
	    	  startActivity(intent2);
	    	  Log.i("tag", "跳转到餐厅管理页面");
	    	  break;
	     case R.id.order_btn:case R.id.go_order_btn:
	    	  Intent intent3=new Intent();
	    	  intent3.setClass(getApplicationContext(), OrderActivity.class);
	    	  startActivity(intent3);
	    	  Log.i("tag", "跳转到订单管理页面");
	    	  break;
	     case R.id.statistic_btn:case R.id.go_statistic_btn:
	    	  Intent intent4=new Intent();
	    	  intent4.setClass(getApplicationContext(), StatisticActivity.class);
	    	  startActivity(intent4);
	    	  Log.i("tag", "跳转到销售统计页面");
	    	  break;
	     case R.id.user_btn:case R.id.go_user_btn:
	    	  Intent intent5=new Intent();
	    	  intent5.setClass(getApplicationContext(), UserActivity.class);
	    	  startActivity(intent5);
	    	  Log.i("tag", "跳转到用户管理页面");
	    	  break;
	    	  default:
	    		  break;
	   }
	}
}
