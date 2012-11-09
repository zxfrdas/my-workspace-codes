package com.zt.lib.impl.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.zt.lib.StreamHelper;

import android.content.Context;
import android.test.AndroidTestCase;

public class StreamHelperTest extends AndroidTestCase {
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		FileOutputStream fos = getContext().openFileOutput("StreamHelper", Context.MODE_PRIVATE);
		fos.write(new StringBuilder("StreamHelper").toString().getBytes());
		fos.flush();
		fos.close();
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testToString()
	{
		try {
			assertEquals("StreamHelper", StreamHelper.toString(getContext().openFileInput("StreamHelper")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
