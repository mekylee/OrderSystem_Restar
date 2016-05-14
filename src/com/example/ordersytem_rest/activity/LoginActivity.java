package com.example.ordersytem_rest.activity;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.ordersystem_rest.customview.CleanableEditText;
import com.example.ordersytem_rest.R;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LoginActivity extends Activity implements OnClickListener{
    private Button login_btn,resetpassword_btn;
    private CleanableEditText account_editext, password_editext;
 //   private  NetworkReceiver networkReceiver; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initialView();
	
	  
	}
	
	/*@Override
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
	*/
	
	
	private void initialView(){
		login_btn=(Button)findViewById(R.id.login_btn);

		resetpassword_btn=(Button)findViewById(R.id.resetpassword_btn);
		account_editext=(CleanableEditText)findViewById(R.id.edit_account);
		password_editext=(CleanableEditText)findViewById(R.id.edit_password);
	    login_btn.setOnClickListener(this);
	    resetpassword_btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String account=account_editext.getText().toString();
		String password=password_editext.getText().toString();
		
		switch(v.getId()){
		case R.id.login_btn:
			loginByEmail(account.trim(),password.trim()); 
			break;
		case R.id.resetpassword_btn: 
			Intent intent=new Intent(LoginActivity.this,ResetPassActivity.class);
			startActivity(intent);
			Log.i("tag", "跳转到重置密码页面");
			break;
			default:
				break;
				
			
		
		
		}
	}
	
	/*
	 * 
	 */
	public boolean isUsernameOrEmail(String text){
		if(text.contains("@")){
			return false;
		}else{
		return true;
		}
	}
	/**
	 * 登录
	 */
	private void loginByEmail(String email,String password){
		
		if(email.isEmpty()==true||isEmail(email)==false){
			Log.i("tag", "邮箱地址不正确");
			Toast.makeText(LoginActivity.this, "邮箱地址不正确", 2000).show();
		}else if(password.length()<6||password.length()>16){
			Toast.makeText(LoginActivity.this, "密码长度应为6-16位", 2000).show();
			Log.i("tag", "密码长度应为6-16位");
		}
		else {
		AVUser.logInInBackground(email.trim(), password.trim(), new LogInCallback<AVUser>() {
				@Override
				public void done(AVUser arg0, AVException arg1) {
					// TODO Auto-generated method stub
					if(arg1==null){
						Log.i("tag", "登录成功");
						Intent intent=new Intent(LoginActivity.this,MainActivity.class);
						startActivity(intent);
					}else{
						Toast.makeText(LoginActivity.this, "邮箱地址不存在",Toast.LENGTH_SHORT).show();
						Log.e("tag", arg1.toString());
					}
				}
			});
		}
	}

	/*
     * 判断电子邮箱地址是否正确
     */
    public boolean isEmail(String email){
   	 String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|"
   	 		+ "(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
   	 Pattern p = Pattern.compile(str);
   	 Matcher m = p.matcher(email);
   	 return m.matches();
    }
    
    /*
     * 
     */
	

	/*
	 * �����û����¼
	 
	private void loginByUsername(String account,String password){
		
		if(account.length()<1||account.length()>20){
			Toast.makeText(LoginActivity.this, "�˺ų���ӦΪ1-20λ", Toast.LENGTH_SHORT).show();
		    Log.i("tag", "�˺ų���ӦΪ1-20λ");
		}
		else if(password.length()<6||password.length()>16){
			Toast.makeText(LoginActivity.this, "���볤��ӦΪ6-16λ", Toast.LENGTH_SHORT).show();

		}
		else {
			AVUser user=new AVUser();
		   user.logInInBackground(account.trim(), password.trim(), new LogInCallback<AVUser>() {
			
			@Override
			public void done(AVUser arg0, AVException arg1) {
				// TODO Auto-generated method stub
				if(arg1==null){
					Log.i("tag", "��¼�ɹ�");
					Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(LoginActivity.this, "���û�δע��", Toast.LENGTH_SHORT);
					//Log.e("tag", "��¼ʧ�ܣ��������������");
				}
			}
		});
		}
		
	}
	
	*/
	
	
	
	
	
	/**
	/**
	 * �ֻ��һ���¼
	
	private void phoneNumberLogin(){
		//1.���÷�����֤��Ľӿ�
		AVOSCloud.requestSMSCodeInBackground("13826473629", new RequestMobileCodeCallback() {
			
			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				//����ʧ�ܿ��Բ鿴arg0�ṩ����Ϣ
				Log.e("tag", arg0.toString());
			}
		});
		//2.��UI�ϸ����û�������֤���������û������¼��ʱ��������½ӿ�:
		AVUser.signUpOrLoginByMobilePhoneInBackground("13826473629", "123456",new LogInCallback<AVUser>() {

			@Override
			public void done(AVUser arg0, AVException arg1) {
				// TODO Auto-generated method stub
				//���arg1Ϊ�վͱ�ʾ��¼�ɹ��ˣ�����User�Ǹ�ȫ�µ��û�
			}
		});
	}
	
	private void createObject(){
		//��������
		final AVObject userFolder=new AVObject("UserFolder");
		
		AVObject user1=new AVObject("User");
		user1.put("uid","001��");
		user1.put("account","hellworld");
		user1.put("password","12342");
		
		AVObject user2=new AVObject("User");
		user2.put("uid","002");
		user2.put("account","sfdf");
		user2.put("password","fd223");
		
		AVObject user3=new AVObject("User");
		user3.put("uid","003");
		user3.put("account","13232dd");
		user3.put("password","dji33");
		
		//��������
		AVRelation<AVObject> relation =userFolder.getRelation("��USer");
		relation.add(user1);
		relation.add(user2);
		relation.add(user3);
		//���浽�����
		userFolder.saveInBackground(new SaveCallback() {   
			
			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				if(arg0==null){
					//�洢�ɹ�,objectId���Զ��ӷ���˼��ص�����
					Log.e("tag", "�½���user������Ϊ"+userFolder
							
							
							.getObjectId());
				}else{
					//ʧ�ܵĻ����������绷���Լ�sdk�����Ƿ���ȷ
					log.e("createObject","�洢ʧ�ܣ��������绷���Լ�sdk�Ƿ�������ȷ");
				}
			}
		});
		
		
		
	}
	
	private void insertObject(){
		//ִ��CQL���ʵ������һ��User����
		AVQuery.doCloudQueryInBackground("insert into User(uid,account,password)values('0002',13826473629,12345)",
				new CloudQueryCallback<AVCloudQueryResult>() {
					
					@Override
					public void done(AVCloudQueryResult arg0, AVException arg1) {
						// TODO Auto-generated method stub
						if(arg1==null){
							//�洢�ɹ�
							
						}else{
							//ʧ�ܵĻ����������绷���Լ�sdk�����Ƿ���ȷ
							log.e("createObject","�洢ʧ�ܣ��������绷���Լ�sdk�Ƿ�������ȷ");
						}	
					}
				});
	}
	
	private void modifyObject(){
		AVObject avobject=new AVObject();
		//��ȡ�û����˺�
		String account = avobject.getString("account");
		AVSaveOption avSaveOption=new AVSaveOption();
		AVQuery<AVObject> query=new AVQuery<AVObject>("uname");
		query.whereEqualTo("account", account);
		
		avSaveOption.query(query);
		avobject.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				if(arg0.getCode()==305){
					Log.d("tag", "�޷������޸ģ��˻������ѱ������˸ı�");
					
				}
			}
		});
	}
	
	//��ѯ����
	private void queryObject(){
		AVQuery<AVObject> avquery =new AVQuery<AVObject>("User");
		avquery.getInBackground("sdsdsds", new GetCallback<AVObject>() {
			
			@Override
			public void done(AVObject arg0, AVException arg1) {
				// TODO Auto-generated method stub
				int uid=arg0.getInt("uid");
				String account=arg0.getString("account");
				String password =arg0.getString("password");
				String objectId=arg0.getObjectId();
				Date updateAt=arg0.getUpdatedAt();
				Date createAt=arg0.getCreatedAt();
			}
		});
	}
	
	//ͬ������
	private void synchronizationObject(){
		//������֪��ObjectID���������µķ�ʽ����һ��AVObject
		AVObject anotherUser=AVObject.createWithoutData("User", "dsdssddssd");
		//Ȼ�����ˢ�µķ���������ݴӷ������������
		anotherUser.fetchIfNeededInBackground(new GetCallback<AVObject>() {
			
			@Override
			public void done(AVObject arg0, AVException arg1) {
				// TODO Auto-generated method stub
				//����fetchIfNeededInBackground��refreshInBackgroundЧ����һ���
			}
		});
		
		//�����Ҫ�ڱ�������֮���ñ�������Զ����ƶ˱���һ�£�����ʹ�ñ���ѡ��fetchWhenSave
		anotherUser.setFetchWhenSave(true);
		anotherUser.saveInBackground();
		
		//ͬ��ָ������
		final AVObject avObject=new AVObject();
		AVObject theUser=AVObject.createWithoutData("User", "11wew");
		String keys="account,password";//�ƶ�ˢ�µ�key�ַ�
		theUser.fetchInBackground(keys, new GetCallback<AVObject>() {
			
			@Override
			public void done(AVObject arg0, AVException arg1) {
				// TODO Auto-generated method stub
				//theUser��account��password���Ե�ֵ����������һ�µ�
				String account=avObject.getString("account");
				String password=avObject.getString("password");
			}
		});
	}
	
	//���¶���.LeanStorage�ĸ��¶�������Ե���������ȷ�������ObjectId��Ϊ��ʱ�ſ��Ը��¶����ƶ˸����ûObjectId������һ������ʱ�������Ǹ���
	private void updateObject(){
		final AVObject user=new AVObject("User");
		user.put("uid","�û����");
		user.put("account","�û��˺�");
		user.put("password","�û�����");
		//���浽�����
		user.saveInBackground(new SaveCallback() {   
			
			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				if(arg0==null){
					//�洢�ɹ�,objectId���Զ��ӷ���˼��ص�����
					Log.e("tag", "�½���user������Ϊ"+user.getObjectId());
				}else{
					//ʧ�ܵĻ����������绷���Լ�sdk�����Ƿ���ȷ
					log.e("createObject","�洢ʧ�ܣ��������绷���Լ�sdk�Ƿ�������ȷ");
				}
			}
		});
		
		//ʹ��CQL�﷨�����¶���
		AVQuery.doCloudQueryInBackground("update User set uid=10002 where obejctId='s13826473629'", new CloudQueryCallback<AVCloudQueryResult>() {
			
			@Override
			public void done(AVCloudQueryResult arg0, AVException arg1) {
				// TODO Auto-generated method stub
				if(arg1==null){
					//�洢�ɹ�
					
				}
			}
		});
	}
	/**
	 * ԭ�Ӳ��������¼���������������
	
	//���¼�����
	private void updateCounter(){
		final AVObject user=AVObject.createWithoutData("User","sdsddsds");
		user.put("uname", "hello world");
		user.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				//ԭ�����Ӳ鿴����
				user.increment("uname");
				user.setFetchWhenSave(true);
				user.saveInBackground();
				//Ҳ����ʹ��increamentKey:byAmount������Number�����ֶ��ۼ�һ���ض���ֵ
				user.increment("uname",5);
				user.saveInBackground();
				//��saveInBackground����֮�����ɹ��Ļ�������ļ������ֶ��ǵ�ǰϵͳ����ֵ
			}
		});
		
		
	}
	/��������
	private void updateArray(){
		//��������
				Date getDateWithDateString(String dateString){
					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date=dateFormat.parse(dateString);
					return date;
				}
				void addReminders(){
					Date reminder1=getDateWithDateString("2015-11-11 07:10:00");
					Date reminder2=getDateWithDateString("2015-11-11 07:10:00");
					Date reminder3=getDateWithDateString("2015-11-11 07:10:00");
					
					AVObject todo=new AVObject("todo");
					todo.addUnique("reminders", Arrays.asList(reminder1,reminder2,reminder3));
					todo.saveInBackground();
				}													
	}
    
	ɾ�����
	private void deleteObject(){
		AVObject user=AVObject.createWithoutData("User","sdsddsds");
	    user.deleteInBackground();
	    //ʹ��CQL�﷨ɾ�����
	    AVQuery.doCloudQueryInBackground("delete fron User where objectId='2ewew'", new CloudQueryCallback<AVCloudQueryResult>() {

			@Override
			public void done(AVCloudQueryResult arg0, AVException arg1) {
				// TODO Auto-generated method stub
				if(arg1==null){
					//����ɹ�
				}
			}
		});
	   
	}
	
	/*
	 * ��������
	
	//��������������
	saveAll();
	saveAllInBackground()
	//����ɾ��
	deleteAll();
	deleteAllBackground();
	//������ȡ
	fetchAll();
	fetchAllInBackground();
	 
	private void setObject(){
		AVQuery<AVObject> query=new AVQuery<AVObject>("User");
		query.findInBackground(new FindCallback<AVObject>() {
			
			@Override
			public void done(List<AVObject> arg0, AVException arg1) {
				// TODO Auto-generated method stub
				ArrayList<AVObject> users=new ArrayList<AVObject>();
				for(AVObject user:users){
					user.put("sex","female");
				}
				AVObject.saveAllInBackground(users, new SaveCallback() {
					
					@Override
					public void done(AVException arg0) {
						// TODO Auto-generated method stub
						if(arg0==null){
							//���ִ���
						}else{
							//����ɹ�
						}
					}
				});
				
			}
		});
	}
	
	
	
	
	
	
	**/
	
	
	
	
	
	
	
	
	
	
	
	

	
}
