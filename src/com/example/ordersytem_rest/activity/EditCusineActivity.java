package com.example.ordersytem_rest.activity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.example.ordersystem_rest.customview.CleanableEditText;
import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.entity.Cusine;
import com.example.ordersytem_rest.entity.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class EditCusineActivity extends Activity  implements OnClickListener,OnItemSelectedListener{
	private CleanableEditText ed_name;
	private CleanableEditText ed_price;
	private ImageButton image_btn;//上传图片的图标
	private ImageView  image;   //上传图片的缩略图
	private Button confirm_btn,add_btn,back_btn;
	private Spinner cusine_type_spinner;
	private String local_path;
	private String image_name;
	private List<String> cusine_type_list;
	private Cusine cusine;  //当前编辑的菜品对象
	private String cusine_type; //所选择的菜品类型
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_cusine);
        inialView();
    }
    
    public void inialView(){
    	ed_name=(CleanableEditText)findViewById(R.id.cuisne_name_ed);
    	ed_price=(CleanableEditText)findViewById(R.id.cuisne_price_ed);
    	image_btn=(ImageButton)findViewById(R.id.uploadCusineImage);
    	image=(ImageView)findViewById(R.id.imageView1);
    	confirm_btn=(Button)findViewById(R.id.button1);
    	add_btn=(Button)findViewById(R.id.addcusine_btn);
    	back_btn=(Button)findViewById(R.id.ed_cusine_back_btn);
    	cusine_type_spinner=(Spinner)findViewById(R.id.spinner1);
    	image_btn.setOnClickListener(this);
    	confirm_btn.setOnClickListener(this);
    	add_btn.setOnClickListener(this);
    	back_btn.setOnClickListener(this);
    	cusine_type_spinner.setOnItemSelectedListener(this);
    	Intent i=getIntent();//获取Intent
    	String cusineString=i.getStringExtra("菜品对象");
    	//将菜品对象反序列化
    	try {
			cusine=(Cusine) Cusine.parseAVObject(cusineString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String price=i.getStringExtra("菜品单价");
    	ed_name.setText(cusine.getCusineName().toString());
    	ed_price.setText(price);
    	Log.i("tag", "点击item传过来的的菜品为："+cusine.getCusineName().toString());
    	Log.i("tag", "点击item传过来的的菜品价格为："+Integer.toString(cusine.getInt("cuisne_price")));
    //	cusine_type_list=getCusineType();
	//	cusine_type_spinner.setAdapter(new ArrayAdapter<String>(EditCusineActivity.this,android.R.layout.simple_spinner_dropdown_item,cusine_type_list));
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.button1:
				//添加成功，跳转到菜单管理的页面，显示出新增的菜单
				//获取编辑框的信息
			    String cusine_name=ed_name.getText().toString();
			    String cusine_price=ed_price.getText().toString();
			    Pattern p = Pattern.compile("[0-9]*");   
			     Matcher m = p.matcher(cusine_price);   
			  
			    if(cusine_name.isEmpty()==true){
			            Toast.makeText(this, "菜品名不能为空", 1000).show();
			    }else if(cusine_price.isEmpty()==true){
			    	  Toast.makeText(this, "菜品价格不能为空", 1000).show();
			     
			    }else if(m.matches()||Integer.getInteger(cusine_price)<0){
			    	Toast.makeText(this, "菜品价格应为正整数",1000).show();
			    }else{
			    	 /* //将编辑后的菜品数据提交到数据库中
			    	 AVService.createOrUpdateCusine(cusine.getObjectId(), cusine_image, cusine_name, cusine_price, new SaveCallback() {
						
						@Override
						public void done(AVException arg0) {
							// TODO Auto-generated method stub
							if(arg0==null){
							  //如果修改成功跳转到菜品管理首页
							  Intent i=new Intent(EditCusineActivity.this,CusienActivity.class);
							  startActivity(i);
						}else{
							Log.i("tag", "修改菜品失败"+arg0.toString());
							Toast.makeText(EditCusineActivity.this, "修改菜品失败",1000).show();
						}
					}
				});*/
			    	 
			    }
			    	
				break;
			case R.id.uploadCusineImage:
				//更改照片
				setImage();
				break;
			case R.id.addcusine_btn:
				Intent intent=new Intent(EditCusineActivity.this,AddCusineActivity.class);
				startActivity(intent);
				break;
			case R.id.ed_cusine_back_btn:
				finish();
				break;
		}
	}
	
	

	
	//实现Spinner下拉列表的选择事件
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//获取选择的spinner的item的内容
		String type=(String) cusine_type_spinner.getItemAtPosition(position);
		Log.i("tag", "所选择的菜单为："+type);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	
	//获取到数据库中的菜单列表
	   public List<String> getCusineType(){
		   List<String> cusine_type_list=new ArrayList<String>();
		   SharedPreferences pre=getSharedPreferences("menu", MODE_WORLD_READABLE);
		   
		  /* List<Menu> menus=AVService.findMenus();//获取到Menu对象列表，从中提取中Menu的名称
		   for(int i=0;i<menus.size();i++){
			   String menu_name=menus.get(i).getMenuName();
			   cusine_type_list.add(menu_name);
		   }*/
		  
		   return cusine_type_list;
	   }
	   
   public Menu getMenu(List<Menu> menus){
		   Menu menu=new Menu();
		   for(int i=0;i<menus.size();i++){
			   menu=menus.get(i);
		   }
		   return menu;
	   }
	
		
	//读取本地图片
	public void setImage(){
	   //使用Intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
	   Intent i=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	   startActivityForResult(i, 1);
	   
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	 super.onActivityResult(requestCode, resultCode, data);
	 if(requestCode==RESULT_OK && resultCode==1 && data!=null){
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

	
	
}
