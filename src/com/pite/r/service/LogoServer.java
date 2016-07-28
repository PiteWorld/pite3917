package com.pite.r.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import com.pite.r.LoginActivity;
import com.pite.r.R;
import com.pite.r.constant.Constant;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class LogoServer extends IntentService{
	private int filesize;   //文件大小
	private int downsize;  //当前现在进度
	private Notification notif;
	private NotificationManager manager;
	public LogoServer() {
		super(LogoServer.class.getName());
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			//更新进度
				case 0:
					int j =(int)(downsize*100/filesize);
					notif.contentView.setTextViewText(R.id.pro_tv, j+"%");
					notif.contentView.setProgressBar(R.id.pro_progressBar,100,j,false);
					manager.notify(0, notif);
					break;
				//下载完成
				case 1:
					Toast.makeText(getApplication(), "下载完成", Toast.LENGTH_SHORT).show();
					break;
				//下载失败	
				case 3:
					break;
			}
		};
	};
	@Override
	protected void onHandleIntent(Intent intent) {
		DownLogo(intent);
		DownApk(intent);
	}
	/***
	 * 更新apk
	 */
	private void DownApk(Intent intent){
		notificationInit();
		Log.e("TAG", intent.getStringExtra("flage")+" APK");
		if(intent!=null&&intent.getStringExtra("flage").equals("1")){
			BufferedInputStream in=null;
			FileOutputStream out=null;
			String fileurl = intent.getStringExtra("apkurl");
			Log.e("tag", "apk更新的地址为："+fileurl);
			File file = new File(Constant.LOGOFILE+"/"+"apk");
			Log.e("tag", Constant.LOGOFILE+"/"+"apk"+ "    下载apk存储地址");
			if(!file.exists())
				file.mkdirs();
			try {
				out = new FileOutputStream(file+"/"+fileurl.substring(fileurl.lastIndexOf("/")+1));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			 try {
				URL url = new URL(fileurl);
				URLConnection con = url.openConnection(); 
				filesize = con.getContentLength();
				Log.e("tag", "apk name"+con.getURL().getFile().substring(con.getURL().getFile().lastIndexOf("/")+1));
				in = new BufferedInputStream(con.getInputStream());
					byte[] buffer = new byte[1024]; 
					int len =0;
					int num = 0;
					while ((len = in.read(buffer))!=-1) {
						downsize+=len;
						out.write(buffer,0,len);
						if(downsize*100/filesize==num){
						handler.sendEmptyMessage(0);
						num= num+5;
						}
					}
					handler.sendEmptyMessage(2);
					Log.e("tag", "apk下载完成");
					//判断文件是否是以apk为后缀的安装包，代码：
					File apk = new File(file+"/"+fileurl.substring(fileurl.lastIndexOf("/")+1));
					if(apk.getName().endsWith(".apk")){
					
					//创建Intent打开apk进行安装，代码：
					Intent install = new Intent();
					install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					install.setAction(android.content.Intent.ACTION_VIEW);
					install.setDataAndType(Uri.fromFile(apk),"application/vnd.android.package-archive");
					startActivity(install);
					}
			} catch (Exception e) {
				e.printStackTrace();
				handler.sendEmptyMessage(3);
			}finally {
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					
			}
			 
		}
	}
	 private void notificationInit(){
		  //通知栏内显示下载进度条
		  Intent intent=new Intent(this,LoginActivity.class);//点击进度条，进入程序
		  PendingIntent pIntent=PendingIntent.getActivity(this, 0, intent, 0);
		  manager=(NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
		  notif=new Notification();
		  notif.icon=R.drawable.ic_launcher;
		  notif.contentView=new RemoteViews(getPackageName(),R.layout.progressbar_content);//通知栏中进度布局
		  notif.contentIntent=pIntent;
		 }
	/**
	 * 下载企业图片
	 */
	 String str=null;
	private void DownLogo(Intent intent) {
		if(intent!=null&&intent.getStringExtra("flage").equals("2")){
			 str = intent.getStringExtra("image");
			if(new File(Constant.LOGOIMAGE+"/"+str.substring(str.lastIndexOf("/")+1)).exists())
				return;
			BufferedInputStream in=null;
			FileOutputStream out=null;
			Log.e("tag", str+"下载logo的地址");
			File file = new File(Constant.LOGOFILE+"/"+"loge");
			if(!file.exists()){
				file.mkdirs();
			}
			//下载图片
			 try {
				URL url = new URL(str);
				URLConnection con = url.openConnection(); 
				if(con.getContentLength()<1){
					Log.e("tag", "图片长度为0");
					return;
				}
				out = new FileOutputStream(file+"/"+str.substring(str.lastIndexOf("/")+1));
				in = new BufferedInputStream(con.getInputStream());
				//拿到服务器的响应吗
					byte[] buffer = new byte[64]; 
					int len =0;
					while ((len = in.read(buffer))!=-1) {
						out.write(buffer,0,len);
					}
					Log.e("tag", "logo图片下载完成");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					
			}
			 
		}
	}
}
