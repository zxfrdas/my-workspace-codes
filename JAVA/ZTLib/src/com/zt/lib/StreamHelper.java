package com.zt.lib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamHelper {
	
	public static String toString(InputStream stream) throws IOException
	{
		byte[] buffer = new byte[1024];
		int length = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (-1 != (length = stream.read(buffer))) {
			baos.write(buffer, 0, length);
		}
		String str = new String(baos.toByteArray());
		baos.close();
		return str.toString();
	}
	
	public static void output(InputStream is, OutputStream os) throws IOException
	{
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = is.read(buffer)) > 0) {
			os.write(buffer, 0, count);
		}
		os.flush();
		os.close();
		is.close();
	}
	
}
