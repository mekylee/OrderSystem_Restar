package com.example.ordersytem_rest.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.ordersystem_rest.customview.CleanableEditText;
import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.entity.Cusine;
import com.example.ordersytem_rest.entity.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCusineActivity extends Activity implements OnClickListener,OnItemSelectedListener{
	private CleanableEditText ed_name;
	private CleanableEditText ed_price;
	private ImageButton image_btn;//上传图片的图标
	private ImageView  image;   //上传图片的缩略图
	private Button confirm_btn,back_btn;
	private Spinner cusine_type_sp;
	private List<String> cusine_type_list;
	private List<Menu> menus=AVService.findMenus();
	private AVFile imageFile;
	private final String IMAGE_TYPE="image/*";
	private static final int IMAGE_CODE=1;
	private String local_path=null;   //要上传照片的本地绝对路径
	private String image_name=null;    //要上传照片的名字
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onCreate(savedInstanceState);
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_add_cusine);
	        inialView();
	    
	    }
	 
	  public void inialView(){
	    	ed_name=(CleanableEditText)findViewById(R.id.cuisne_name_ed);
	    	ed_price=(CleanableEditText)findViewById(R.id.cuisne_price_ed);
	    	image_btn=(ImageButton)findViewById(R.id.uploadCusineImage);
	    	image=(ImageView)findViewById(R.id.imageView1);
	    	confirm_btn=(Button)findViewById(R.id.button1);
	    	back_btn=(Button)findViewById(R.id.add_cusine_back_btn);
	    	cusine_type_sp=(Spinner)findViewById(R.id.cusine_type_spinner);
	    	image_btn.setOnClickListener(this);
	    	confirm_btn.setOnClickListener(this);
	    	back_btn.setOnClickListener(this);
	    	cusine_type_list=getCusineType();
	    	Log.i("tag", "获取到菜单为："+cusine_type_list);
	    	cusine_type_sp.setAdapter(new ArrayAdapter<String>(AddCusineActivity.this,android.R.layout.simple_spinner_dropdown_item, cusine_type_list));
	        cusine_type_sp.setOnItemSelectedListener(this);
	    }

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
				case R.id.button1:  
					//点击确定按钮，添加成功，跳转到菜单管理的页面，显示出新增的菜单
					String cusine_name=ed_name.getText().toString();
					int price=Integer.parseInt(ed_price.getText().toString());
					
					if(price<0) {
						Toast.makeText(this, "价格应为", 1000).show();
						ed_price.setText("");
					}else if(ed_price.getText().toString().isEmpty()==true){
						Toast.makeText(this, "价格不能为空", 1000).show();

					}else if(cusine_name.isEmpty()==true){
						Toast.makeText(this, "菜品不能为空", 1000).show();

					}else if(local_path==null&& image_name==null){
						Toast.makeText(this, "请选择图片！", 1000).show();
					}else{
					AVFile image =AVService.upLoadImage(image_name, local_path);
				   AVService.createCusine(getMenu(menus), image, cusine_name, price, new SaveCallback() {
					
					@Override
					public void done(AVException arg0) {
						// TODO Auto-generated method stub
						if(arg0==null){
							Log.i("tag", "添加菜品成功:");
						}else{
							Log.i("tag","添加菜品失败"+ arg0.toString());
						}
					}
				});
					Intent i=new Intent(AddCusineActivity.this,CusienActivity.class);
					startActivity(i);
					}
					break;
				case R.id.uploadCusineImage:
					//读取本地照片
					setImage();
					break;
				case R.id.add_cusine_back_btn:
					finish();
					break;
			
			}
		}
		
		
  //读取本地图片
   public void setImage(){
	   //使用Intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
	   Intent i=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	   startActivityForResult(i, IMAGE_CODE);
	   
   }
   
   @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	 super.onActivityResult(requestCode, resultCode, data);
     if(requestCode==RESULT_OK && resultCode==IMAGE_CODE && data!=null){
       Uri uri =data.getData();
       String[] filePath={ MediaStore.Images.Media.DATA};
       Cursor cursor=getContentResolver().query(uri, filePath, null, null, null);
       cursor.moveToFirst();
       int columnIndex=cursor.getColumnIndex(filePath[0]);
       String imagePath=cursor.getString(columnIndex);
       cursor.close();
       //判断是什么类型的图片
       if(imagePath.endsWith("jpg")||imagePath.endsWith("png")){
    	   local_path = imagePath;
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
			    image.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }else{
    	   Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
   				.setMessage("您选择的不是有效的图片")
   				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
   					public void onClick(DialogInterface dialog, int which) {
   						local_path = null;
   					}
   				}).create();
   		dialog.show();
       }
     
     
     }
   }
	
   //获取到数据库中的菜单列表
   public List<String> getCusineType(){
	   List<String> cusine_type_list=new ArrayList<String>();
	   List<Menu> menus=AVService.findMenus();//获取到Menu对象列表，从中提取中Menu的名称
	   for(int i=0;i<menus.size();i++){
		   String menu_name=menus.get(i).getMenuName();
		   cusine_type_list.add(menu_name);
	   }
	  
	   return cusine_type_list;
   }
   
   public Menu getMenu(List<Menu> menus){
	   Menu menu=new Menu();
	   for(int i=0;i<menus.size();i++){
		   menu=menus.get(i);
	   }
	   return menu;
   }
   
    //实现Spinner的点击事件
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String type=(String) cusine_type_sp.getSelectedItem();
		Log.i("tag", "选择的菜品类型为："+type);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
