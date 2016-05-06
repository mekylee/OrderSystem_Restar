package com.example.ordersytem_rest.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentlist=new ArrayList<Fragment>();
	public FragmentAdapter(FragmentManager fm,List<Fragment> fragmentlist) {
		super(fm);
		this.fragmentlist=fragmentlist;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentlist.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentlist.size();
	}
	
	

}
