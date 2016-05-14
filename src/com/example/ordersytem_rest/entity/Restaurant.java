package com.example.ordersytem_rest.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
@AVClassName(Restaurant.RESTAURANT_CLASS)
public class Restaurant extends AVObject {
    static final String RESTAURANT_CLASS="Restaurant";
    
    //设置餐厅的名称
    public void setRestName(String restName){
    	put("restName", restName);
    }
    //获取餐厅的名称
    public String getRestName(){
    	return getString("restName");
    }
    //设置餐厅的电话
    public void setRestPhone(String restPhone){
    	put("restPhone",restPhone);
    }
  
    //获取餐厅的电话
    public String getRestPhone(){
    	return getString("restPhone");
    }
    
    //获取餐厅的地址
    public String  getRestAddress(){
    	 return getString("restAddress");
    }
    //设置餐厅的地址
    public void setRestAddress(String restAddress){
    	put("restAddress",restAddress);
    }
}
