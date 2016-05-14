package com.example.ordersystem_rest.utils;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.example.ordersytem_rest.entity.Comment;
import com.example.ordersytem_rest.entity.Cusine;
import com.example.ordersytem_rest.entity.Menu;
import com.example.ordersytem_rest.entity.Order;
import com.example.ordersytem_rest.entity.User;

import android.app.Application;


public class RestApplication extends Application {
    @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
        AVService.AVInit(this);
    
    	
    }
} 
