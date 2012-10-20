package bugreport.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import bugreport.AppManager;
import bugreport.vdisk.IService;
import bugreport.vdisk.VDiskService;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

/**
 * 用于处理未在代码中进行捕获的错误异常
 */
public class CrashHandler implements UncaughtExceptionHandler, IService
{

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = null;
	// 程序的Context对象
	private Context mContext;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	
	private VDiskService mVDiskService = null;

	/**
	 * 获取CrashHandler实例 ,单例模式
	 */
	public static CrashHandler getInstance()
	{
		synchronized (CrashHandler.class)
		{
			if (null == INSTANCE)
			{
				INSTANCE = new CrashHandler();
			}
			return INSTANCE;
		}
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context)
	{
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
		// 初始化微盘
		mVDiskService = VDiskService.getInstance();
		mVDiskService.setContext(mContext);
		mVDiskService.setListener(this);
	}
	
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		if (!handleException(ex) && mDefaultHandler != null)
		{
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex)
	{
		if (ex == null)
		{
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run()
			{
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉，程序出现异常，正在上传错误信息，请稍后", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo();
		// 保存日志文件并上传
		saveCrashInfo2File(ex);
		ex.printStackTrace();
		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo()
	{
		System.out.println("收集设备参数信息");
		try
		{
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null)
			{
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		}
		catch (NameNotFoundException e)
		{
			System.out.println("an error occured when collect package info");
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields)
		{
			try
			{
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
			}
			catch (Exception e)
			{
				System.out.println("an error occured when collect crash info");
			}
		}
		System.out.println("收集完毕");
	}
	
	/**
	 * 网络是否可用 想访问网络状态，首先得添加权限
	 * <p>uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"</p>
	 * @param context
	 * @return
	 */
	private static boolean isNetworkAvailable(Context context)
	{
		System.out.println("判断网络状态");
		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null)
		{
			for (int i = 0; i < info.length; i++)
			{
				if (info[i].getState() == NetworkInfo.State.CONNECTED)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 文件名
	 */
	private String saveCrashInfo2File(Throwable ex)
	{
		System.out.println("保存错误信息到文件");
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null)
		{
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try
		{
			String time = formatter.format(new Date());
			String fileName = "crash-" + mContext.getApplicationInfo().loadLabel(mContext.getPackageManager())
					+ "-" + time  + ".log";
			String path = "/data/data/" + mContext.getPackageName() + "/crash/";
			File dir = new File(path);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path + fileName));
			osw.write(sb.toString());
			osw.flush();
			osw.close();
			//上传
			if (isNetworkAvailable(mContext))
			{
				System.out.println("开始上传");
				mVDiskService.uploadErrLog(new File(path, fileName), mContext.getPackageName());
			}
			else
			{
				AppManager.getInstance().exitApp();
			}
			return fileName;
		}
		catch (Exception e)
		{
			System.out.println("an error occured while writing file...");
		}
		return null;
	}

	@Override
	public void onCompl()
	{
		System.out.println("uploaded");
		// 退出程序
		AppManager.getInstance().exitApp();
	}

}
