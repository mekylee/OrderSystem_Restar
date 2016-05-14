package com.example.ordersytem_rest.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

@AVClassName(Cusine.CUSINE_CLASS)
public class Cusine extends AVObject {
	static final String CUSINE_CLASS="Cusine";
  
   //获取菜品编号
	public Number getCusinNumber() {
		return getNumber("cusine_number");
	}
	//设置菜品编号
	public void setCusineNumber(String cusine_order) {
		put("cusine_number",cusine_order);
	}
	//获取菜品图片
	public AVFile getCusineImage(){
		return getAVFile("cusine_imge");
	}
	//设置菜品图片
	public void setCusineImage(AVFile image){
		put("cusine_imge",image);
	}
	//获取菜品名称
	public String getCusineName() {
		return getString("cusine_name");
	}
	//设置菜品名称
	public void setcName(String cusine_name) {
		put("cusine_name",cusine_name);
	}
	//获取菜品单价
	public Integer getPrice() {
		return getInt("price");
	}
	//设置菜品单价
	public void setPrice(Integer price) {
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
