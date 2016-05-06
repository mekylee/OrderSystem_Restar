package com.example.ordersytem_rest.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.FragmentAdapter;
import com.example.ordersytem_rest.fragment.Fragment_Bill;
import com.example.ordersytem_rest.fragment.Fragment_Completed;

import android.app.Activity;
import android.graphics.Color;
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
	private Button back_btn;  //页面顶部的回退按钮、操作按钮
	private ViewPager viewPager;
	private List<Fragment> fragment_list=new ArrayList<>();
	private FragmentAdapter fragment_adapter;
	private Fragment_Completed bill_frag,confirm_frag,dish_confrag,completed_frag;
	private TextView waitfor_confirm_tv, waitfor_dishes_tv,waitfor_bill_tv,complelted_tv;//各个tab的名字
    private int screen_width;  //屏幕的宽度
    private int current_index;// 当前选中的页卡
    private ImageView tab_line;//tab的导航线
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_manage_order);
	    initial();
	    initTabLineWidth();
	    
    }
    public void initial(){
    	back_btn=(Button)findViewById(R.id.order_back_btn);
	    back_btn.setOnClickListener(this);
	    viewPager=(ViewPager)findViewById(R.id.order_pager);
	    waitfor_confirm_tv=(TextView)findViewById(R.id.wait_for_confirmed);
	    waitfor_bill_tv=(TextView)findViewById(R.id.wait_for_bill);
	    waitfor_dishes_tv=(TextView)findViewById(R.id.completed_tv);
	    tab_line=(ImageView)findViewById(R.id.tab_line);
	    completed_frag=new Fragment_Completed();
	    fragment_list.add(completed_frag);
	    fragment_adapter =new FragmentAdapter(this.getSupportFragmentManager(), fragment_list);
	    viewPager.setAdapter(fragment_adapter);
	    viewPager.setCurrentItem(0);
	    viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch(arg0){
				case 0:
					waitfor_confirm_tv.setTextColor(Color.BLUE);
					break;
				case 1:
					waitfor_dishes_tv.setTextColor(Color.BLUE);
					break;
				case 2:
					waitfor_bill_tv.setTextColor(Color.BLUE);
					break;
				case 3:
					complelted_tv.setTextColor(Color.BLUE);
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
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_line  .getLayoutParams();  
        lp.width = screen_width / 4;  
        tab_line.setLayoutParams(lp);  
    }  
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		this.finish();
	}
}
