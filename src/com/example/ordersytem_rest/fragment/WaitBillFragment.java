package com.example.ordersytem_rest.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ordersytem_rest.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WaitBillFragment extends Fragment{
	private ListView order_listview;
//	private GetData util=new GetData();
	private List<Map<String,Object>> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreateView(inflater, container, savedInstanceState);
    	View view=inflater.inflate(R.layout.fragment_order,container,false);
    	init(view);
    	return view;
    }
    
     public void init(View view){
    	 order_listview=(ListView)view.findViewById(R.id.order_listview);
  //  	 data=util.getData("0001", "2013-03-12 11:00:11", "结账");
    	 order_listview.setAdapter(new SimpleAdapter(getContext(), data, R.layout.order_listview_item,
    			 new String[]{"number_of_order","time","confirm_btn"}, new int[]{R.id.ordernumber_tv,R.id.ordertime_tv,R.id.op_btn}));
    	 
     }
     
   
     
    
   
}
