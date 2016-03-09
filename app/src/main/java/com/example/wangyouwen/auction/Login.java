package com.example.wangyouwen.auction;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.example.wangyouwen.auction.util.DialogUtil;
import com.example.wangyouwen.auction.util.HttpUtil;
import com.example.wangyouwen.auction.util.SessionId;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity
{
	// 定义界面中两个文本框
	EditText etName, etPass;
	// 定义界面中两个按钮
	Button bnLogin, bnCancel;
	static Integer userId = 0;
	public static Integer getUserId(){

		return userId;
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// 获取界面中两个编辑框
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		// 获取界面中的两个按钮
		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		// 为bnCancal按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new HomeListener(this));
		bnLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 执行输入校验
				if (validate())  // ①
				{
					// 如果登录成功
					if (loginPro())  // ②
					{
						// 启动Main Activity
						Intent intent = new Intent(Login.this
								, AuctionClientActivity.class);
						startActivity(intent);
						// 结束该Activity
						finish();
					}
					else
					{
						DialogUtil.showDialog(Login.this
								, "用户名称或者密码错误，请重新输入！", false);
					}
				}
			}
		});
	}

	private boolean loginPro()
	{
		// 获取用户输入的用户名、密码
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		JSONObject jsonObj;
		try
		{
			jsonObj = query(username, pwd);
			Log.i("userid",String.valueOf(jsonObj.getInt("userId")));
			// 如果userId 大于0
			if (jsonObj.getInt("userId") > 0)

			{
				userId = jsonObj.getInt("userId");
				return true;
			}
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this
					, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}

		return false;
	}

	// 对用户输入的用户名、密码进行校验
	private boolean validate()
	{
		String username = etName.getText().toString().trim();
		if (username.equals(""))
		{
			DialogUtil.showDialog(this, "用户账户是必填项！", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		if (pwd.equals(""))
		{
			DialogUtil.showDialog(this, "用户口令是必填项！", false);
			return false;
		}
		return true;
	}

	// 定义发送请求的方法
	private JSONObject query(String username, String password)
			throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<>();
		map.put("user", username);
		map.put("pass", password);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "login.jsp";
		Log.i("url",url);
		Log.i("map",map.toString());
		// 发送请求
		return new JSONObject(postRequest(url, map));
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
								SessionId session = new SessionId();
								session.setSessionId(urlpath);


								httpURLConnection.setRequestProperty("Cookie",session.getSessionId());
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
									Log.i("Test","id"+new String(data1,0,data1.length,"gbk"));
									String tempId =new String(data1,0,data1.length,"gbk");
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