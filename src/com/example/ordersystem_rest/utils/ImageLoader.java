package com.example.ordersystem_rest.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageLoader {
	private ImageView image_view;
	private String image_url;
   private Handler handler=new Handler(){
	   public void handleMessage(android.os.Message msg) {
		   super.handleMessage(msg);
		   if(image_view.getTag().equals(image_url)){
		   image_view.setImageBitmap((Bitmap) msg.obj);
		   }
	   }
   };
   public void showImageByThread(ImageView imageView,final String url){
	   image_view=imageView;
	   image_url=url;
	   new Thread(){
         @Override
        public void run() {
        	// TODO Auto-generated method stub
        	super.run();
        	//图像加载
        	Bitmap bitmap=getBitmapFromUrl(url);
        	Message message=Message.obtain();
        	message.obj=bitmap;
        	handler.sendMessage(message);
        	
        }
	   }.start();
	   
   }
   
   //从图片的url中获取bitmap
   public Bitmap getBitmapFromUrl(String urlString){
	   Bitmap bitmap=null;
	   InputStream is = null;
		try {
			URL url=new URL(urlString);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		    is=new BufferedInputStream(conn.getInputStream());
		    bitmap=BitmapFactory.decodeStream(is);
		    conn.disconnect();
		  
			return bitmap;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	   return null;
	  
   }
}
