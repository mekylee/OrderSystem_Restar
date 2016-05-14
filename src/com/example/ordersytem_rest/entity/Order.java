package com.example.ordersytem_rest.entity;

import java.util.Date;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;

@AVClassName(Order.ORDER_CLASS)
public class Order extends AVObject {
	static final String ORDER_CLASS="Order";
	//获取订单号
	public Number getoId() {
		return getNumber("order_number"); 
	}
	//设置订单号
	public void setoId(String order_number) {
		this.put("order_number", order_number);
	}
	
	
	//获取就餐人数
	public Integer getNumberOfPerson() {
		return getInt("number_of_person");
	}
	//设置就餐人数
	public void setNumberOfPerson(Integer number_of_person) {
		this.put("number_of_person",number_of_person);
	}

	
	//获取下单时间
	public Date getOrderCreatedDate(){
		return getDate(CREATED_AT);
	}
	
	//获取订单最后更新的时间
	public Date getOrderUpdatedDate(){
		return getDate(UPDATED_AT);
	}
	
	 //设置下单人
	public void setOrderUser(User user){
		put("order_owner",user);
	}
	//获取下单人
	public User getOrderUser(){
		
		return getAVObject("order_owner");
	}
	
	//设置订单状态
	public void setOrderType(int type){
		switch(type){
		 case  0:
			 put("order_type",0);  //0-->待确认
			 break;
		 case 1:
			 put("order_type",1);  //1-->待上菜
			 break;
		 case 2:
			 put("order_type",2); //2-->待付款
			 break;
		 case 3:
			 put("order_type",3);//3-->待完成
			 break;
		 case 4:
			 put("order_type",4);//4-->已完成
		 case -1:
			 put("order_type",-1);//-1-->取消
			 break;
		}
	}
	
	//获取订单状态
	public int getOrderType(){
		return getInt("order_type");
	}

	
   
   
   
}
