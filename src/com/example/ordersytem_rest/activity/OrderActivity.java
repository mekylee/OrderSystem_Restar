package com.example.ordersytem_rest.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.ordersystem_rest.utils.NetworkReceiver;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.FragmentAdapter;
import com.example.ordersytem_rest.fragment.CompletedFragment;
import com.example.ordersytem_rest.fragment.WaitConfirmFragment;
import com.example.ordersytem_rest.fragment.WaitBillFragment;
import com.example.ordersytem_rest.fragment.WaitDishesFragment;

import android.app.Activity;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderActivity extends FragmentActivity implements OnClickListener{
	//private Button back_btn;  //页面顶部的回退按钮、操作按钮
	 private NetworkReceiver networkReceiver;
	private ViewPager viewPager;
	private List<Fragment> fragment_list=new ArrayList<Fragment>();
	private FragmentAdapter fragment_adapter;
	private WaitConfirmFragment confirm_frag;
	private WaitBillFragment bill_frag;
	private WaitDishesFragment dish_frag;
	private CompletedFragment completed_frag;
	private TextView waitfor_confirm_tv, waitfor_dishes_tv,waitfor_bill_tv,complelted_tv;//各个tab的名字
    private int screen_width;  //屏幕的宽度
    private int current_index;// 当前选中的页卡
    private ImageView tab_line;//tab的导航线
    private Button back_btn;  //回退到上一页面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_manage_order);
	   
	    initial();
	    initTabLineWidth();
	    
    }
	
	
	 @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
			networkReceiver =new NetworkReceiver();
			registerReceiver(networkReceiver, filter);
		}
		
	     @Override
	    protected void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	unregisterReceiver(networkReceiver);  
	    }
    public void initial(){
    	back_btn=(Button)findViewById(R.id.order_back_btn);
	    back_btn.setOnClickListener(this);
	    viewPager=(ViewPager)findViewById(R.id.order_pager);
	    waitfor_confirm_tv=(TextView)findViewById(R.id.waitfor_confirm_tv);
	    waitfor_bill_tv=(TextView)findViewById(R.id.waitfor_bill_tv);
	    waitfor_dishes_tv=(TextView)findViewById(R.id.wairfor_dishes_tv);
	    complelted_tv=(TextView)findViewById(R.id.completed_tv);
	    tab_line=(ImageView)findViewById(R.id.tab_line);
	    bill_frag=new WaitBillFragment();
	    confirm_frag=new WaitConfirmFragment();
	    dish_frag=new WaitDishesFragment();
	    completed_frag=new CompletedFragment();
	    fragment_list.add(confirm_frag);
	    fragment_list.add(dish_frag);
	    fragment_list.add(bill_frag);
	    fragment_list.add(completed_frag);
	    fragment_adapter =new FragmentAdapter(this.getSupportFragmentManager(), fragment_list);
	    viewPager.setAdapter(fragment_adapter);
	    viewPager.setCurrentItem(0);
	    viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				resetTextView();
				switch(arg0){
				case 0:
					waitfor_confirm_tv.setTextColor(getResources().getColor(R.color.hulanse));
					break;
				case 1:
					waitfor_dishes_tv.setTextColor(getResources().getColor(R.color.hulanse));
					break;
				case 2:
					waitfor_bill_tv.setTextColor(getResources().getColor(R.color.hulanse));
					break;
				case 3:
					complelted_tv.setTextColor(getResources().getColor(R.color.hulanse));
					break;
				}
				current_index=arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				 LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)tab_line.getLayoutParams();  
	                Log.e("offset:", arg1 + "");  
	                /** 
	                 * 利用current_index(当前所在页面)和position(下一个页面)以及offset来 
	                 * 设置tab_line的左边距 滑动场景： 
	                 * 记4个页面, 
	                 * 从左到右分别为0,1,2,3
	                 * 0->1; 1->2; 2->3;3-2;2->1; 1->0 
	                 */  
	  
	                if (current_index == 0 && arg0 == 0)// 0->1  
	                {  
	                    lp.leftMargin = (int) (arg1 * (screen_width * 1.0 / 4) + current_index * (screen_width / 4));  
	  
	                } else if (current_index == 1 && arg0 == 0) // 1->0  
	                {  
	                    lp.leftMargin = (int) (-(1 - arg1) * (screen_width * 1.0 / 4) + current_index * (screen_width / 4));  
	  
	                } else if (current_index == 1 && arg0 == 1) // 1->2  
	                {  
	                    lp.leftMargin = (int) (arg1 * (screen_width * 1.0 / 4) + current_index   * (screen_width / 4));  
	                } else if (current_index == 2 && arg0 == 1) // 2->1  
	                {  
	                    lp.leftMargin = (int) (-(1 - arg1)  * (screen_width * 1.0 / 4) + current_index  * (screen_width / 4));  
	                } else if(current_index==2 && arg0==2){      //2->3
	                	lp.leftMargin = (int) (arg1 * (screen_width * 1.0 / 4) + current_index   * (screen_width / 4));
	                }else if(current_index==3 && arg0==2){         //3->2
	                	lp.leftMargin = (int) (-(1 - arg1)  * (screen_width * 1.0 / 4) + current_index  * (screen_width / 4)); 
	                }
	                
	                tab_line.setLayoutParams(lp);  
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    }
    /** 
     * 设置滑动条的宽度为屏幕的1/4(根据Tab的个数而定) 
     */  
    private void initTabLineWidth() {  
        DisplayMetrics dpMetrics = new DisplayMetrics();  
        getWindow().getWindowManager().getDefaultDisplay()  
                .getMetrics(dpMetrics);  
        
        screen_width = dpMetrics.widthPixels;  
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_line.getLayoutParams();  
        lp.width = screen_width / 4;  
        tab_line.setLayoutParams(lp);  
    }  
    /** 
     * 重置颜色 
     */  
    private void resetTextView() {  
        complelted_tv.setTextColor(Color.BLACK);  
        waitfor_bill_tv.setTextColor(Color.BLACK);  
        waitfor_confirm_tv.setTextColor(Color.BLACK);  
        waitfor_dishes_tv.setTextColor(Color.BLACK);  
    }  
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		this.finish();
	}
}
