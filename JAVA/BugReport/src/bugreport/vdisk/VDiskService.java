package bugreport.vdisk;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * 后台线程执行微盘（VDisk）方法
 * 
 * @author zhaotong
 */
public class VDiskService
{
	private static VDiskService instance = null;
	private ExecutorService mThread = null;
	private VDisk mVDisk = null;
	private IService mListener = null;
	private Context mContext = null;

	public static VDiskService getInstance()
	{
		if (null == instance)
		{
			instance = new VDiskService();
		}
		return instance;
	}

	public void setListener(IService listener)
	{
		mListener = listener;
	}

	public void setContext(Context context)
	{
		mContext = context;
	}

	private VDiskService()
	{
		mThread = Executors.newCachedThreadPool();
		mVDisk = VDisk.getInstance();
		keepTokenEvery10Min();
	}

	/**
	 * 获取Token，并且每隔十分钟保持一次Token
	 */
	private void keepTokenEvery10Min()
	{
		mThread.execute(new Runnable() {

			@Override
			public void run()
			{
				final boolean bIsGot = mVDisk.getToken();
				while (bIsGot)
				{
					mVDisk.keepToken();
					try
					{
						TimeUnit.MILLISECONDS.sleep(10 * 60 * 1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * 上传错误日志
	 * 
	 * @param errLog
	 *            File
	 * @param dir
	 *            目录
	 */
	public void uploadErrLog(final File errLog, final String dir)
	{
		mThread.execute(new Runnable() {

			@Override
			public void run()
			{
				Looper.prepare();
				Handler handler = new Handler();
				String dirId = mVDisk.getDirId(dir);
				if (null == dirId)
				{
					dirId = mVDisk.createDir(dir);
				}
				final boolean bIsUploaded = mVDisk.uploadErrLog(errLog, dirId);
				if (bIsUploaded)
				{
					Toast.makeText(mContext, "上传成功", 1500).show();
				}
				else
				{
					Toast.makeText(mContext, "上传失败", 1500).show();
				}
				handler.postDelayed(new Runnable() {

					@Override
					public void run()
					{
						if (null != mListener)
						{
							mListener.onCompl();
						}
					}
				}, 1500);
				Looper.loop();
			}
		});
	}

}
