package com.zt.lib.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamHelper {
	
	/**
	 * 将InputStream转换为字符串
	 * @param stream
	 * @return 转换后的字符串
	 * @throws IOException
	 */
	public static String toString(InputStream stream)
	{
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[1024];
		int length = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			while (-1 != (length = stream.read(buffer))) {
				baos.write(buffer, 0, length);
			}
			sb.append(baos.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将输入流写入输出流中
	 * @param is 输入流
	 * @param os 输出流
	 * @throws IOException
	 */
	public static void output(InputStream is, OutputStream os)
	{
		byte[] buffer = new byte[1024];
		int count = 0;
		try {
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
