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
    
   //由于LeanCloud保存的用户密码是隐藏的，不提供获取用户的密码，所以只能设置初始密码，再根据用户邮箱进行重置密码
    
}
