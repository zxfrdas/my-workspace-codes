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
	
	public static class TestObject {
		public int publicInt = 0;
		public boolean publicBoolean = true;
		public String publicString = "publicString";
	}
	
}
