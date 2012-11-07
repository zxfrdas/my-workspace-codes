package com.zt.lib;

import android.database.Cursor;
import android.util.Log;

public class Print {

	private static boolean printable = true;
	private static String TAG = "";

	public static void setTAG(String tag)
	{
		TAG = tag;
	}
	
	public static void enable()
	{
		printable = true;
	}
	
	public static void disable()
	{
		printable = false;
	}

	public static void d(String msg)
	{
		if (printable) {
			StackTraceElement line = new Throwable().getStackTrace()[1];

			if (line != null) {
				Log.d(TAG, "File[" + line.getFileName() + "]Line[" + line.getLineNumber()
						+ "]Msg[" + msg + "]");
			}
		}
	}

	public static void w(String msg)
	{
		if (printable) {
			StackTraceElement line = new Throwable().getStackTrace()[1];
			if (line != null) {
				Log.w(TAG, "File[" + line.getFileName() + "]Line[" + line.getLineNumber()
						+ "]Msg[" + msg + "]");
			}
		}
	}
	
	public static void i(String msg)
	{
		if (printable) {
			StackTraceElement line = new Throwable().getStackTrace()[1];
			if (line != null) {
				Log.i(TAG, "File[" + line.getFileName() + "]Line[" + line.getLineNumber()
						+ "]Msg[" + msg + "]");
			}
		}
	}
	
	public static void cursor(Cursor c)
	{
		if (printable) {
			String names[] = c.getColumnNames();
			for (String name : names) {
				d(name + " = " + c.getString(c.getColumnIndex(name)));
			}
		}
	}
}
