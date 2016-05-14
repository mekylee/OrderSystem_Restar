package com.example.ordersytem_rest.fragment;

import com.example.ordersytem_rest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentStatisticsMonth extends Fragment {
	
   @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		   View view =inflater.inflate(R.layout.fragment_statistic_month, container,false);
		
		   return view;
	}
   
   
   public void init( View view){
	   
   }
}
