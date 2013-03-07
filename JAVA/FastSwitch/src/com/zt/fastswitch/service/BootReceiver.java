package com.zt.fastswitch.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())
				|| "com.zt.test".equals(intent.getAction())) {
			Log.d("FastSwitch", "Receive Broadcast = " + intent.getAction());
			Intent i = new Intent();
			i.setClass(context, FastSwitchServer.class);
			context.startService(i);
		}
	}

}
