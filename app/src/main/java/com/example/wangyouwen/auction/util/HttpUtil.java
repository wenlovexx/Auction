package com.example.wangyouwen.auction.util;

import android.util.Log;

import com.example.wangyouwen.auction.Login;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class HttpUtil
{
	// 创建HttpClient对象

	//public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL =
			"http://192.168.2.107:8080/auction/android/";
	/**
	 *

	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String getRequest(final String urlpath)
			throws Exception
	{
		StringBuffer tempUrl = new StringBuffer(urlpath);
		if(!urlpath.contains("?")) {

			tempUrl.append("?").append("userId=" + Login.getUserId());
			Log.i("Test1", tempUrl.toString());
		}
		final String myUrl = tempUrl.toString();
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>()
				{
					@Override
					public String call() throws Exception
					{
						// 创建HttpGet对象。
						InputStream inputStream = null;
						ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
						HttpURLConnection httpURLConnection =null;
						//httpURLConnection.setRequestProperty("Cookie",SessionId.getSessionId());
						byte[] data = new byte[1024],data1;
						int len = 0;
						try {
							URL url = new URL(myUrl.toString());
							if(url!=null){

								httpURLConnection = (HttpURLConnection)url.openConnection();
								httpURLConnection.setConnectTimeout(3000);
								httpURLConnection.setDoInput(true);
								httpURLConnection.setRequestMethod("GET");
								Log.i("Test1", SessionId.getSessionId());
								httpURLConnection.setRequestProperty("Cookie", SessionId.getSessionId());
								//httpURLConnection.setRequestProperty("Cookie", SessionId.getSessionId(urlpath));
								int code = httpURLConnection.getResponseCode();
								if(code == 200){
									inputStream = httpURLConnection.getInputStream();
									while((len = inputStream.read(data))!=-1){
										outputStream.write(data,0,len);

									}
									data1 = outputStream.toByteArray();
									Log.i("Test",new String(data1,0,data1.length,"gbk"));
									return new String(data1,0,data1.length,"gbk");

								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}



						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}

	public static String postRequest(final String urlpath
			, final Map<String ,String> rawParams)throws Exception
	{


//		StringBuffer params = new StringBuffer(urlpath);
//		params.append("?");
//		// 表单参数与get形式一样
//		for(String key : rawParams.keySet()) {
//			params.append(key).append("=").append(rawParams.get(key)).append("&");
//		}
//		final String temp = params.substring(0, params.length()-1);
//		Log.i("Test",temp);
//		final byte[] bypes = temp.toString().getBytes();


		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>()
				{
					@Override
					public String call() throws Exception
					{

						InputStream inputStream = null;
						ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
						HttpURLConnection httpURLConnection =null;
						byte[] data = new byte[1024],data1;
						int len = 0;
						try {
							URL url = new URL(urlpath);
							if(url!=null){
								httpURLConnection = (HttpURLConnection)url.openConnection();
//								String cookieval = httpURLConnection.getHeaderField("set-cookie");
//								String sessionid;
//								if(cookieval != null) {
//									sessionid = cookieval.substring(0, cookieval.indexOf(";"));
//									httpURLConnection.setRequestProperty("cookie", sessionid);
//								}

								httpURLConnection.setConnectTimeout(3000);
								httpURLConnection.setDoInput(true);
								httpURLConnection.setDoOutput(true);
								httpURLConnection.setRequestMethod("POST");
								httpURLConnection.setRequestProperty("Charset", "GBK");
								Log.i("Test1",SessionId.getSessionId());
								//httpURLConnection.setRequestProperty("Cookie", SessionId.getSessionId(urlpath));
								httpURLConnection.setRequestProperty("Cookie",SessionId.getSessionId());
								httpURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=GBK");
								//get session info
								//String session_value = httpURLConnection.getHeaderField("Set-Cookie");
								//String[] sessionId = session_value.split(";");
								//save session info
								//httpURLConnection.setRequestProperty("Cookie", sessionId[0]);
								try {

									StringBuffer params = new StringBuffer();
									// 表单参数与get形式一样
									for(String key : rawParams.keySet()) {
										params.append(key).append("=").append(rawParams.get(key)).append("&");
									}
									final String temp = params.substring(0, params.length() - 1);
									Log.i("Test",temp);
									final byte[] bypes = temp.toString().getBytes("gbk");
									DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());

									dataOutputStream.write(bypes, 0, bypes.length);
									//httpURLConnection.getOutputStream().write(bypes);// 输入参数
									Log.i("Test", "哎呦，对了...");
								}catch (Exception e){
									Log.i("Test","又错了。。。。");
								}
								int code = httpURLConnection.getResponseCode();
								Log.i("Test", String.valueOf(code));
								if(code == 200){
									//get session info
									//String session_value = httpURLConnection.getHeaderField("Set-Cookie");
									//Log.i("Test", session_value);
									//String[] sessionId = session_value.split(";");
									//save session info
									//Log.i("Test", sessionId[0]);
									//httpURLConnection.setRequestProperty("Cookie", "JSESSIONID=F492AE5F5234424C7B97B5D87D448132");
									Log.i("Test","哦了。。。");
									inputStream = httpURLConnection.getInputStream();
									while((len = inputStream.read(data))!=-1){
										outputStream.write(data,0,len);

									}
									data1 = outputStream.toByteArray();
									//return new String(data1,data1.length,"GBK");
									Log.i("Test",new String(data1,0,data1.length,"gbk"));
									return new String(data1,0,data1.length,"gbk");

								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}



}
