package com.example.ordersytem_rest.entity;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

@AVClassName(Cusine.CUSINE_CLASS)
public class Cusine extends AVObject {
	static final String CUSINE_CLASS="Cusine";
	 public static final Creator CREATOR = AVObjectCreator.instance;
	 
	 public Cusine(){
		 super();
	 }
	 
	 public Cusine(Parcel in){
		 super(in);
	 }
   //获取菜品编号
	public int getCusinNumber() {
		return getInt("cusine_number");
	}
	//设置菜品编号
	public void setCusineNumber(int  cusine_order) {
		put("cusine_number",cusine_order);
	}
	//获取菜品图片
	public AVFile getCusineImage(){
		return getAVFile("cusine_image");
	}
	//设置菜品图片
	public void setCusineImage(AVFile image){
		put("cusine_image",image);
	}
	//获取菜品名称
	public String getCusineName() {
		return getString("cusine_name");
	}
	//设置菜品名称
	public void setCusineName(String cusine_name) {
		put("cusine_name",cusine_name);
	}
	//获取菜品单价
	public int getPrice() {
		return getInt("price");
	}
	//设置菜品单价
	public void setPrice(int price) {
	     put("price",price)	;
	}
	
	//获取菜品类型
	public Menu getCusineType(){
		
		return getAVObject("cusine_type");
	}
	
	//设置菜品类型
	public void setCusineType(Menu menu){
		put("cusine_type",menu);
	}
	

   
}
