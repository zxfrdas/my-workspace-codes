package com.zt.lib.impl.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.suitebuilder.TestSuiteBuilder;

public class AllTests extends TestSuite {
	
	public static final String TAG = "ZTLibTest";
	
	public static Test suite()
	{
		return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
	}
	
}
