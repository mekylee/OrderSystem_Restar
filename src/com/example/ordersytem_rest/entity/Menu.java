package com.example.ordersytem_rest.entity;

import android.R.menu;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject; 
   /*
    * ��Menu���໯
    */
   @AVClassName(Menu.MENU_CLASS)
	public class Menu extends AVObject {
	static final String MENU_CLASS="Menu";
	//��ȡ�˵����
	public String getMenuName() {
		return getString("menu_name");
	}
    //���ò˵����
	public void setMenuName(String menuName) {
		this.put("menu_name", menuName);
	}
	
	   
}
