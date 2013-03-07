package com.zt.fastswitch.service;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.zt.fastswitch.R;

public class FastSwitchServer extends Service {
	private static final String TAG = "FastSwitch";
	private WindowManager mWManager;
	private LayoutParams mParams;
	private View mClickView;
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(TAG, "Service Start");
		initView();
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void initView()
	{
		if (null == mWManager) {
			mWManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		}
		if (null == mParams) {
			mParams = new LayoutParams();
			mParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
			mParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
			mParams.width = getResources().getDimensionPixelSize(R.dimen.view_width);
			mParams.height = getResources().getDimensionPixelSize(R.dimen.view_height);
			mParams.gravity = Gravity.TOP | Gravity.LEFT;
			mParams.alpha = 0.4f;
		}
		if (null == mClickView) {
			mClickView = new View(this);
			mClickView.setBackgroundResource(R.drawable.ic_launcher);
			mClickView.setAlpha(0.4f);
			mClickView.setOnTouchListener(new OnTouchListener() {
				float posX = 0f;
				float posY = 0f;
				float touchX = 0f;
				float touchY = 0f;
				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					posX = event.getRawX();
					posY = event.getRawY() - 25;
					if (MotionEvent.ACTION_DOWN == event.getAction()) {
						touchX = event.getX();
						touchY = event.getY();
					} else if (MotionEvent.ACTION_MOVE == event.getAction()) {
						Log.d(TAG, "posX = " + posX + " posY = " + posY);
						Log.d(TAG, "touchX = " + touchX + " touchY = " + touchY);
						mParams.x = (int) (posX - touchX);
						mParams.y = (int) (posY - touchY);
						mWManager.updateViewLayout(mClickView, mParams);
					} else if (MotionEvent.ACTION_UP == event.getAction()) {
						checkRunningTasks();
					}
					return false;
				}
			});
		}
		mWManager.addView(mClickView, mParams);
	}
	
	private void checkRunningTasks()
	{
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskInfos = activityManager.getRunningTasks(10);
		for (RunningTaskInfo taskInfo : taskInfos) {
			Log.d(TAG, "Task = " + taskInfo.id + " TopActivity = " + taskInfo.topActivity);
		}
	}

}
