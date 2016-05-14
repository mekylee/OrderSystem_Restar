package com.example.ordersystem_rest.utils;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVDeleteOption;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogUtil.log;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.search.AVSearchQuery;
import com.example.ordersytem_rest.entity.Comment;
import com.example.ordersytem_rest.entity.Cusine;
import com.example.ordersytem_rest.entity.Menu;
import com.example.ordersytem_rest.entity.Order;
import com.example.ordersytem_rest.entity.Restaurant;
import com.example.ordersytem_rest.entity.User;

public class AVService {
	public static void AVInit(Context context){
		//注册子类
		 AVObject.registerSubclass(Menu.class);
    	 AVObject.registerSubclass(Cusine.class);
    	 AVObject.registerSubclass(Order.class);
    	 AVObject.registerSubclass(Comment.class);
    	 AVObject.registerSubclass(Restaurant.class);
    	 AVOSCloud.setDebugLogEnabled(true);
    	// 初始化应用 Id 和 应用 Key
     	AVOSCloud.initialize(context,LeanCloudConf.APP_ID, LeanCloudConf.APP_Key);
     	 // 启用崩溃错误报告
        AVAnalytics.enableCrashReport(context, true);
        AVOSCloud.setLastModifyEnabled(true);
	}
     
	/*
	 *  对数据库中的菜单的增删查改
	 */
	
	//通过菜单objectId获取菜单
	public static void fetchMenuById(String objectId,GetCallback<AVObject> getCallback){
		Menu menu=new Menu();
		menu.setObjectId(objectId);
		//通过Fetch获取菜单
		menu.fetchInBackground(getCallback);
	}
	
	//查询菜单列表，返回菜单对象列表
	public static  List<Menu> findMenus(){
		//查询当前Menu列表
		AVQuery<Menu> query=AVQuery.getQuery(Menu.class);
		//按照更新时间降序排序
		query.orderByDescending("updatedAt");
		//最大返回1000条
		query.limit(1000);
		try{
			return query.find();
		}
		catch(AVException exception){
			log.e("tag", "查询菜单失败.",exception);
			return Collections.emptyList();
		}
		
	}
	
	//创建或更新菜单
	public static void createOrUpdateMenu(String objectId,String menuName,SaveCallback saveCallback){
		final Menu menu=new Menu();
		if(!TextUtils.isEmpty(objectId)){
			//如果存在objectId,保存会变成更新操作
			menu.setObjectId(objectId);
		}
		menu.setMenuName(menuName);
		//异步保存
		menu.saveInBackground(saveCallback);
	}
	
	//创建新的菜单
	public static void createMenu(String menuName,SaveCallback saveCallback){
		Menu menu=new Menu();
		menu.setMenuName(menuName);
		//异步保存
		menu.saveInBackground(saveCallback);
		
	}
	//根据objectId删除菜单
	public static void deleteMenuById(String objectId){
		final Menu menu=new Menu();
		if(!TextUtils.isEmpty(objectId)){
			//如果存在objectId,保存会变成更新操作
			menu.setObjectId(objectId);
			menu.deleteInBackground(new DeleteCallback() {
				
				@Override
				public void done(AVException arg0) {
					// TODO Auto-generated method stub
					if(arg0==null){
						Log.d("tag", "成功删除菜单"+menu.getMenuName());
					}else{
						Log.d("tag", "删除菜单失败："+arg0.toString());
					}
				}
			});
		}
	}
	
	/*
	 * 对数据库中用户的增删查改
	 */

	//根据ObjectId来获取用户
	public static void fetchUserById(String objectId,GetCallback<AVObject> getCallback){
		User user=new User();
		user.setObjectId(objectId);
		//通过fetch获取用户的邮箱地址和用户类型
		user.fetchInBackground(getCallback);
	}
	
	//查询用户，返回用户对象列表
		public static List<User> findUsers(){
			//查询当前Menu列表
			AVQuery<User> query=AVQuery.getQuery(User.class);
			//按照更新时间降序排序
			query.orderByDescending("updatedAt");
			//最大返回1000条
			query.limit(1000);
			try{
				return query.find();
					}
			catch(AVException exception){
				log.e("tag", "查询用户失败.",exception);
				return Collections.emptyList();
					}
			
		}
		
	
	//更新用户名或用户类型
	public static void createOrUpdateUser(String objectId,String email,String usertype,SaveCallback saveCallback){
		final User user=new User();
		if(!TextUtils.isEmpty(objectId)){
			//如果存在objectId,保存会变成更新操作
			user.setObjectId(objectId);
		}
		user.setEmail(email);
		user.setUserType(usertype);
		//异步保存
		user.saveInBackground(saveCallback);
	}
	
	//创建新的用户
	public static void createUser(String username ,String usertype,SaveCallback saveCallback){
		User user=new User();
		user.setUsername(username);
		user.setUserType(usertype);
		//异步保存
		user.saveInBackground(saveCallback);
			
		}
	
	//根据objectId删除用户
		public static void deleteUserById(String objectId){
			final User user=new User();
			if(!TextUtils.isEmpty(objectId)){
				//如果存在objectId,保存会变成更新操作
				user.setObjectId(objectId);
				user.deleteInBackground(new DeleteCallback() {
					
					@Override
					public void done(AVException arg0) {
						// TODO Auto-generated method stub
						if(arg0==null){
							Log.d("tag", "成功删除用户"+user.getUsername());
						}else{
							Log.d("tag", "删除用户失败："+arg0.toString());
						}
					}
				});
			}
		}
	

	
	/*
	 * 对数据库中的餐厅进行增删查改
	 */
	
	//查询餐厅信息，返回餐厅对象列表
	public static List<Restaurant> findRestaurants(){
		//查询当前Restaurant列表
				AVQuery<Restaurant> query=AVQuery.getQuery(Restaurant.class);
				//按照更新时间降序排序
				query.orderByDescending("updatedAt");
				//最大返回1000条
				query.limit(1000);
				try{
					return query.find();
						}
				catch(AVException exception){
					log.e("tag", "查询餐厅失败.",exception);
					return Collections.emptyList();
						}
				
	}
	
	//更新餐厅信息
		public static void createOrUpdateRestInfo(String objectId,String restName,String restAddress,String restPhone,SaveCallback saveCallback){
			final Restaurant rest=new Restaurant();
			if(!TextUtils.isEmpty(objectId)){
				//如果存在objectId,保存会变成更新操作
				rest.setObjectId(objectId);
			}
			rest.setRestName(restName);
			rest.setRestAddress(restAddress);
			rest.setRestPhone(restPhone);
			//异步保存
			rest.saveInBackground(saveCallback);
		}
		
		//创建新的餐厅
		public static void createRestInfo(String restName,String restAddress,String restPhone,SaveCallback saveCallback){
			Restaurant rest=new Restaurant();
			rest.setRestName(restName);
			rest.setRestAddress(restAddress);
			rest.setRestPhone(restPhone);
			//异步保存
			rest.saveInBackground(saveCallback);
				
			}
	
	/*
	 * 对数据中的订单进行增删查改
	 */
	//查询订单，返回订单列表
	public static  List<Order> findOrders(){
		//查询当前的订单列表
		AVQuery<Order> query=AVQuery.getQuery(Order.class);
		//按照更新时间降序排序
		query.orderByDescending("updatedAt");
		//最大返回1000条
		query.limit(1000);
		try{
			return query.find();
				}
		catch(AVException exception){
			log.e("tag", "查询订单失败.",exception);
			return Collections.emptyList();
				}
	}
	
	//创建或更新订单
	public static void createOrder(User user, Integer number_of_person,
	   SaveCallback saveCallback) {
		Order order=new Order();
		order.setNumberOfPerson(number_of_person);
		order.setOrderUser(user);
		// 异步保存
		order.saveInBackground(saveCallback);

	}

	//更新订单
	public static void createOrUpdateOrderInfo(String objectId,User user, Integer number_of_person,int order_type,SaveCallback saveCallback){
		final Order order=new Order();
		if(!TextUtils.isEmpty(objectId)){
			//如果存在objectId,保存会变成更新操作
			order.setObjectId(objectId);
		}
		order.setOrderUser(user);
		order.setNumberOfPerson(number_of_person);
		order.setOrderType(order_type);
		//异步保存
		order.saveInBackground(saveCallback);
	}
	//根据objectId删除订单
	public static void deleteOrderById(String objectId) {
		final Order order = new Order();
		if (!TextUtils.isEmpty(objectId)) {
			// 如果存在objectId,保存会变成更新操作
			order.setObjectId(objectId);
			order.deleteInBackground(new DeleteCallback() {

				@Override
				public void done(AVException arg0) {
					// TODO Auto-generated method stub
					if (arg0 == null) {
						Log.d("tag", "成功删除订单" + order.getOrderUser().toJSONObject());
					} else {
						Log.d("tag", "删除订单失败：" + arg0.toString());
					}
				}
			});
		}
	}
	
	
	/*
	 * 对数据库中的菜品进行增删查改
	 */
	
	//查询菜品，返回菜品对象列表
	public static List<Cusine> findCusines(){
		AVQuery<Cusine> query=AVQuery.getQuery(Cusine.class);
		//按照更新时间降序排序
		query.orderByDescending("updatedAt");
		//最大返回1000条
		query.limit(1000);
		try{
			return query.find();
				}
		catch(AVException exception){
			log.e("tag", "查询菜品失败.",exception);
			return Collections.emptyList();
				}
	}
	//更新菜品
		public static void createOrUpdateCusine(String objectId,Menu menu, AVFile cusine_image,String cusine_name,Integer price,SaveCallback saveCallback){
			final Cusine cusine=new Cusine();
			if(!TextUtils.isEmpty(objectId)){
				//如果存在objectId,保存会变成更新操作
				cusine.setObjectId(objectId);
			}
			cusine.setCusineType(menu);
			cusine.setCusineNumber(cusine_name);
			cusine.setCusineImage(cusine_image);
			cusine.setPrice(price);
			//异步保存
			cusine.saveInBackground(saveCallback);
		}
	
	//根据objectId删除菜品
		public static void deleteCusineById(String objectId) {
			final Cusine cusine = new Cusine();
			if (!TextUtils.isEmpty(objectId)) {
				// 如果存在objectId,保存会变成更新操作
				cusine.setObjectId(objectId);
				cusine.deleteInBackground(new DeleteCallback() {

					@Override
					public void done(AVException arg0) {
						// TODO Auto-generated method stub
						if (arg0 == null) {
							Log.d("tag", "成功删除菜品" + cusine.getCusineName());
						} else {
							Log.d("tag", "删除菜品失败：" + arg0.toString());
						}
					}
				});
			}
		}
	
	/*
	 * 对数据库洪的评论进行增删查改
	 */
		//查询评论，返回评论对象列表
		public static List<Comment> findComments(){
			AVQuery<Comment> query=AVQuery.getQuery(Comment.class);
			//按照更新时间降序排序
			query.orderByDescending("updatedAt");
			//最大返回1000条
			query.limit(1000);
			try{
				return query.find();
					}
			catch(AVException exception){
				log.e("tag", "查询评论失败.",exception);
				return Collections.emptyList();
					}
		}
		//创建或更新评论
			public static void createOrUpdateComment(String objectId,User user,String content,SaveCallback saveCallback){
				final Comment comment=new Comment();
				if(!TextUtils.isEmpty(objectId)){
					//如果存在objectId,保存会变成更新操作
					comment.setObjectId(objectId);
				}
				comment.setAuthor(user);
				comment.setContent(content);
				//异步保存
				comment.saveInBackground(saveCallback);
			}
		
		//根据objectId删除评论
			public static void deleteCommentById(String objectId) {
				final Comment comment = new Comment();
				if (!TextUtils.isEmpty(objectId)) {
					// 如果存在objectId,保存会变成更新操作
					comment.setObjectId(objectId);
					comment.deleteInBackground(new DeleteCallback() {

						@Override
						public void done(AVException arg0) {
							// TODO Auto-generated method stub
							if (arg0 == null) {
								Log.d("tag", "成功删除评论" + comment.getContent());
							} else {
								Log.d("tag", "删除评论失败：" + arg0.toString());
							}
						}
					});
				}
			}	
	
	//在整个应用搜索
	public static void searchQuery(String inputSearch){
		AVSearchQuery searchQuery = new AVSearchQuery(inputSearch);
	    searchQuery.search();
	}
	

	
	
}
