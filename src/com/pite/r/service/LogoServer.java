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
	private int filesize;   //�ļ���С
	private int downsize;  //��ǰ���ڽ���
	private Notification notif;
	private NotificationManager manager;
	public LogoServer() {
		super(LogoServer.class.getName());
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			//���½���
				case 0:
					int j =(int)(downsize*100/filesize);
					notif.contentView.setTextViewText(R.id.pro_tv, j+"%");
					notif.contentView.setProgressBar(R.id.pro_progressBar,100,j,false);
					manager.notify(0, notif);
					break;
				//�������
				case 1:
					Toast.makeText(getApplication(), "�������", Toast.LENGTH_SHORT).show();
					break;
				//����ʧ��	
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
	 * ����apk
	 */
	private void DownApk(Intent intent){
		notificationInit();
		Log.e("TAG", intent.getStringExtra("flage")+" APK");
		if(intent!=null&&intent.getStringExtra("flage").equals("1")){
			BufferedInputStream in=null;
			FileOutputStream out=null;
			String fileurl = intent.getStringExtra("apkurl");
			Log.e("tag", "apk���µĵ�ַΪ��"+fileurl);
			File file = new File(Constant.LOGOFILE+"/"+"apk");
			Log.e("tag", Constant.LOGOFILE+"/"+"apk"+ "    ����apk�洢��ַ");
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
					Log.e("tag", "apk�������");
					//�ж��ļ��Ƿ�����apkΪ��׺�İ�װ�������룺
					File apk = new File(file+"/"+fileurl.substring(fileurl.lastIndexOf("/")+1));
					if(apk.getName().endsWith(".apk")){
					
					//����Intent��apk���а�װ�����룺
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
		  //֪ͨ������ʾ���ؽ�����
		  Intent intent=new Intent(this,LoginActivity.class);//������������������
		  PendingIntent pIntent=PendingIntent.getActivity(this, 0, intent, 0);
		  manager=(NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
		  notif=new Notification();
		  notif.icon=R.drawable.ic_launcher;
		  notif.contentView=new RemoteViews(getPackageName(),R.layout.progressbar_content);//֪ͨ���н��Ȳ���
		  notif.contentIntent=pIntent;
		 }
	/**
	 * ������ҵͼƬ
	 */
	 String str=null;
	private void DownLogo(Intent intent) {
		if(intent!=null&&intent.getStringExtra("flage").equals("2")){
			 str = intent.getStringExtra("image");
			if(new File(Constant.LOGOIMAGE+"/"+str.substring(str.lastIndexOf("/")+1)).exists())
				return;
			BufferedInputStream in=null;
			FileOutputStream out=null;
			Log.e("tag", str+"����logo�ĵ�ַ");
			File file = new File(Constant.LOGOFILE+"/"+"loge");
			if(!file.exists()){
				file.mkdirs();
			}
			//����ͼƬ
			 try {
				URL url = new URL(str);
				URLConnection con = url.openConnection(); 
				if(con.getContentLength()<1){
					Log.e("tag", "ͼƬ����Ϊ0");
					return;
				}
				out = new FileOutputStream(file+"/"+str.substring(str.lastIndexOf("/")+1));
				in = new BufferedInputStream(con.getInputStream());
				//�õ�����������Ӧ��
					byte[] buffer = new byte[64]; 
					int len =0;
					while ((len = in.read(buffer))!=-1) {
						out.write(buffer,0,len);
					}
					Log.e("tag", "logoͼƬ�������");
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
