package bugreport;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class AppManager
{
	private static List<Activity> activities = null;
	private static AppManager instance = null;
	private AppManager()
	{
		activities = new ArrayList<Activity>();
	}
	
	public static AppManager getInstance()
	{
		synchronized (AppManager.class)
		{
			if (null == instance)
			{
				instance = new AppManager();
			}
			return instance;
		}
	}
	
	public void insert(Activity activity)
	{
		activities.add(activity);
	}
	
	public void remove(Activity activity)
	{
		activities.remove(activity);
	}
	
	public void exitApp()
	{
		for (Activity activity : activities)
		{
			activity.finish();
		}
		activities = null;
		instance = null;
		System.exit(0);
	}
	
}
