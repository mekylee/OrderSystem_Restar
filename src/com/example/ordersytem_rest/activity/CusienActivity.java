package com.example.ordersytem_rest.activity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.LogUtil.log;
import com.example.ordersystem_rest.utils.AVService;
import com.example.ordersystem_rest.utils.CustomDialog;
import com.example.ordersystem_rest.utils.NetworkReceiver;
import com.example.ordersytem_rest.R;
import com.example.ordersytem_rest.adapter.CusineAdapter;
import com.example.ordersytem_rest.entity.Cusine;
import com.example.ordersytem_rest.entity.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
@AVClassName("Comment")
public class CusienActivity extends Activity implements OnClickListener,OnItemClickListener{
	private Button back_btn,edit_btn;
	private ListView listview;
    private NetworkReceiver networkReceiver;
    private List<Cusine> cusines;
    private CusineAdapter cusineAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage_cusine);
        initialView();
        new RemoteGetCusine().execute();
    }
    
    //异步获取菜品的信息
    private  class RemoteGetCusine extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//查询菜品的数据
			cusines=AVService.findCusines();
			return null;
		}
    	
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
		
			//显示lsitview
			cusineAdapter=new CusineAdapter(CusienActivity.this,cusines);
			listview.setAdapter(cusineAdapter);
			TextView empty=(TextView)findViewById(android.R.id.empty);
			if(cusines!=null&&!cusines.isEmpty()){
				empty.setVisibility(View.INVISIBLE);
			}else{
				empty.setVisibility(View.VISIBLE);
			}
			super.onPostExecute(result);
		}
    }
    
   
    private void initialView(){
    	back_btn=(Button)findViewById(R.id.cusine_back_btn);
    	edit_btn=(Button)findViewById(R.id.addcusine_btn);
    	listview=(ListView)findViewById(R.id.cusine_listview);
    	back_btn.setOnClickListener(this);
    	edit_btn.setOnClickListener(this);
        listview.setOnItemClickListener(this);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		   case R.id.cusine_back_btn:
			   this.finish();  //返回上一个Activity
			   break;
		   case R.id.addcusine_btn:  
			   Intent i=new Intent(CusienActivity.this,AddCusineActivity.class);
			   startActivity(i);
			   break;
			   default:
				   break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		   Intent i=new Intent(CusienActivity.this,EditCusineActivity.class);
		   //获取当前的Cusine对象
		   Cusine cusine=(Cusine) listview.getItemAtPosition(position);
		   String cusineString=cusine.toString();  //将cusine序列化传递给EdtiCusineActivity
		 /*  //获取当前的菜品图片
		   AVFile cusine_image=cusine.getCusineImage();
		   //cusine_image.get
	//	   String image=cusine_image.toString();   //序列化图片对象
		   //获取当前的菜品名称和单价
		   String cusine_name=cusine.getCusineName();
		   int cusine_price=cusine.getPrice();
		   Bundle bundle=new Bundle();
		//   bundle.putSerializable("菜品图片", image);
		   bundle.putString("菜品名称", cusine_name);
		   bundle.putInt("菜品价格", cusine_price);
		   bundle.putString("菜品对象", cusineString);
		   
		 //  bundle.putInt("菜品图片", );
		  * 
*/		    
		   String  cusine_price=Integer.toString(cusine.getInt("cusine_price"));
		   i.putExtra("菜品单价", cusine_price);
		   Log.i("tag", "传递过去菜品价格为："+cusine_price);
		   i.putExtra("菜品对象", cusineString);
           Log.i("tag", "传递菜品对象给EditCusineAciivity");
		   startActivity(i);
		
	}
   
	 @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
			networkReceiver =new NetworkReceiver();
			registerReceiver(networkReceiver, filter);
		}
		
	     @Override
	    protected void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	unregisterReceiver(networkReceiver);  
	    }
     
	     

	
}
