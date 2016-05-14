package com.example.ordersytem_rest.entity;

import com.avos.avoscloud.AVUser;

public class User extends AVUser {
	//获取用户名
    public String getUsername(){
    	return getString("username");
    }
    
    //获取用户邮箱地址
    public void setEmail(String email){
    	this.put("email", email);
    }
     
    //设置用户类型
    public void setUserType(String usertype){
    	this.put("usertype", usertype);
    }
    
    //获取用户类型
    public String getUserType(){
    	return getString("usertype");
    }
    
    
    
}
