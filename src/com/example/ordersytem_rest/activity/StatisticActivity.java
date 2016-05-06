package com.example.ordersytem_rest.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.FragmentAdapter;
import com.example.ordersytem_rest.fragment.FragmentStatistics;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class StatisticActivity extends FragmentActivity implements OnClickListener{
	private Button back_btn;
	private List<Fragment> fragment_list=new ArrayList<Fragment>();
	private FragmentAdapter fragmentAdapter;
	private ViewPager viewPager;
	private FragmentStatistics statistic_by_day_fragment;
	private FragmentStatistics statistic_by_month_fragment;
	/*
	 * Tab显示的内容
	 */
	private TextView statistic_by_day_tv,statistic_by_month_tv;
	/*
	 * tab下面的引导线
	 */
	private ImageView tab_line;
	/*
	 * 屏幕的宽度
	 */
	private  int screen_width;
	/*
	 * 当前tab停靠的位置
	 */
	private int current_index;
     @Override
    protected void onCreate(Bundle arg0) {
    	// TODO Auto-generated method stub
    	super.onCreate(arg0);
    	setContentView(R.layout.activity_statics);
    	findById();
    	init();
    	initTabLineWidth();
    }
  
     
     public void findById(){
    	 back_btn=(Button)findViewById(R.id.statistic_back_btn);
    	 viewPager=(ViewPager)this.findViewById(R.id.statistic_pager);
    	 statistic_by_day_tv=(TextView)this.findViewById(R.id.statistic_by_day_tv);
    	 statistic_by_month_tv=(TextView)findViewById(R.id.statistic_by_month_tv);
    	 tab_line=(ImageView)this.findViewById(R.id.tab_line);
     }
     
     public void init(){
    	 back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    	 statistic_by_day_fragment =new FragmentStatistics();
    	 statistic_by_month_fragment=new FragmentStatistics();
    	 fragment_list.add(statistic_by_day_fragment);
    	 fragment_list.add(statistic_by_month_fragment);
    	
    	 fragmentAdapter =new FragmentAdapter(this.getSupportFragmentManager(), fragment_list);
    	 viewPager.setAdapter(fragmentAdapter);
    	 viewPager.setCurrentItem(0);
    	 viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
			   resetTextView();
			   switch(arg0){
			     case 0:
			    	 statistic_by_day_tv.setTextColor(getResources().getColor(R.color.hulanse));
			    	 break;
			     case 1:
			    	 statistic_by_month_tv.setTextColor(getResources().getColor(R.color.hulanse));
			    	 break;
			   }
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				 LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)tab_line.getLayoutParams();  
	                Log.e("offset:", arg1 + "");  
	                /** 
	                 * 利用current_index(当前所在页面)和position(下一个页面)以及offset来 
	                 * 设置tab_line的左边距 滑动场景： 
	                 * 记2个页面, 
	                 * 从左到右分别为0,1
	                 * 0->1; 1->2
	                 */  
	  
	                if (current_index == 0 && arg0 == 0)// 0->1  
	                {  
	                    lp.leftMargin = (int) (arg1 * (screen_width * 1.0 / 2) + current_index * (screen_width / 2));  
	  
	                } else if (current_index == 1 && arg0 == 0) // 1->0  
	                {  
	                    lp.leftMargin = (int) (-(1 - arg1) * (screen_width * 1.0 / 2) + current_index * (screen_width / 2));  
	  
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
      * 设置滑动条的宽度为屏幕的1/2(根据Tab的个数而定) 
      */  
     private void initTabLineWidth() {  
         DisplayMetrics dpMetrics = new DisplayMetrics();  
         getWindow().getWindowManager().getDefaultDisplay()  
                 .getMetrics(dpMetrics);  
         screen_width = dpMetrics.widthPixels;  
         LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_line  .getLayoutParams();  
         lp.width = screen_width /2;  
         tab_line.setLayoutParams(lp);  
     } 
     /** 
      * 重置颜色 
      */  
     private void resetTextView() {  
         statistic_by_day_tv.setTextColor(Color.BLACK);  
         statistic_by_month_tv.setTextColor(Color.BLACK);  
        
     }  
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		this.finish();
	}
}
