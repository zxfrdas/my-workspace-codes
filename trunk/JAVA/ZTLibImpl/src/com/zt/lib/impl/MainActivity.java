package com.zt.lib.impl;


import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.zt.lib.config.ConfigManager;
import com.zt.lib.config.EnumConfigType;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity implements Observer {

	TestObject mConfigData = new TestObject();
	ConfigManager mConfigManager = ConfigManager.getInstance(MainActivity.this, mConfigData);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConfigManager.loadFile("testLoadFile", "testLoadFile", EnumConfigType.PROP);
        for (String s : mConfigData.publicStringArray) {
        	System.out.println(s);
        }
        mConfigData.publicStringArray[0] = "asdasd";
        mConfigData.publicStringArray[1] = "我";
        mConfigData.publicStringArray[2] = "啊";
        try {
			mConfigManager.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
        for (String s : mConfigData.publicStringArray) {
        	System.out.println(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void update(Observable observable, Object data)
	{
		mConfigData = (TestObject) mConfigManager.getConfigData();
	}

}
