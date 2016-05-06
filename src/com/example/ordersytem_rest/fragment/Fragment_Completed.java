package com.example.ordersytem_rest.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ordersytem_rest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fragment_Completed extends Fragment{
	 private ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	View view =inflater.inflate(R.layout.fragment_order,null);
    	
    	return view;
    }
    
    
    public void initialView(View view){
    	listview=(ListView)view.findViewById(R.id.order_listview);
    	listview.setAdapter(new SimpleAdapter(getActivity(), getData(), R.layout.order_listview_item,
    			new String[]{"order_number","oder_time","operation"},new int[]{R.id.ordernumber_tv,R.id.ordertime_tv,R.id.op_btn} ));
    }
	
	public List<Map<String,Object>> getData(){
		List<Map<String,Object>> list=new ArrayList<>();
		for(int i=0;i<10;i++){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("order_number","22233");
			map.put("order_time", "2016-05-06 11:00:00");
			map.put("operation", "确认");
			list.add(map);
		}
		return list;
		
	}

}
