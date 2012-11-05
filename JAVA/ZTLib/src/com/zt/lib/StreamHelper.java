package com.zt.lib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


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
	
}
