package com.zt.fastswitch.config;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zt.fastswitch.R;

public class ConfigActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		Log.d("FastSwitch", "onResume!");
		sendBroadcast(new Intent("com.zt.test"));
	}



	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

}
