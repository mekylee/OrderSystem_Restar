package com.example.ordersystem_rest.utils;



import com.example.ordersytem_rest.R;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	       NetworkInfo wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	       NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	       if( ! wifiNetInfo.isConnected() && ! mobNetInfo.isConnected()){
	    	   Log.i("tag", "网络断开，请检查网络连接");
	    	   Toast.makeText(context, "网络断开，请检查网络连接", Toast.LENGTH_SHORT).show();
	           AlertDialog.Builder builder=new AlertDialog.Builder(context);
	           builder.setIcon(R.drawable.wifi);
	           builder.setTitle("网络提示");
	           builder.setMessage("是否设置网络？");
	           builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				             dialog.dismiss();	
				}
			});
	           builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 Intent intent=new Intent("/");
					 ComponentName comp=new ComponentName("com.android.settings","com.android.settings.WirlessSettings");
					 intent.setComponent(comp);
					 intent.setAction("android.intent.action.VIEW");
					 context.startActivity(intent);
				}
			});
	           builder.create().show();
	         
	       }
	       else {
	    	   Log.i("tag", "网络正常"+intent.toString());
	       }
	}

}
