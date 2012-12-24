package com.konka.kkimplements.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import android.util.Log;

public class IniEditor
{
	HashMap<String, String> map = new HashMap<String, String>();
	
	public boolean loadFile(String fileName)
	{
		File file = new File(fileName);
		BufferedReader reader = null;		
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempString = null;
	        String strKey = null;
	        while ((tempString = reader.readLine()) != null)
	        {
	        	String str = tempString.trim();
	        	if(str.isEmpty())
	        	{
	        		continue;
	        	}
	        	if(str.charAt(0) == '#' || str.charAt(0) == ';')
	        	{
	        		continue;
	        	}
	        	if(str.charAt(0) == '[')
		        {
	        		int indexEnd = str.indexOf(']');
	        		String strKeyTmp = str.substring(1, indexEnd);
	        		strKey = strKeyTmp.trim();
	        	}
	        	else if(str.contains("="))
	        	{
	        		int indexNameEnd = str.indexOf('=');
	        		Log.d("DEBUG", "Str key : " + indexNameEnd);
	        		String strNameTmp = str.substring(0, indexNameEnd);
	        		String strName = strNameTmp.trim();
	        		String strValueTmp = null;
	        		int indexValueStart = indexNameEnd + 1;
	        		int indexValueEnd = 0;
	        		int indexValueEnd1 = str.indexOf(';');
	        		int indexValueEnd2 = str.indexOf('#');
	        		if(indexValueEnd1 == -1 || indexValueEnd2 == -1)
	        		{
	        			indexValueEnd = Math.max(indexValueEnd1, indexValueEnd2);
	        		}
	        		else
	        		{
	        			indexValueEnd = Math.min(indexValueEnd1, indexValueEnd2);
	        		}
	        		if(indexValueEnd == -1)
	        		{
	        			strValueTmp = str.substring(indexValueStart);
	        		}
	        		else
	        		{
	        			strValueTmp = str.substring(indexValueStart, indexValueEnd);
	        		}
	        		String strValue = strValueTmp.trim();
	        		if(strKey != null)
	        		{
	        			String strKeyTmp = strKey;
	        			strKeyTmp += ":";
	        			strKeyTmp += strName;
		        		map.put(strKeyTmp, strValue);
		        		System.out.printf("%s = %s \n", strKeyTmp, strValue);
		        	}
	        		else
	        		{
	        			return false;
	        		}
	        	}
	        }
	        reader.close();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        if (reader != null)
	        {
	            try
	            {
	                reader.close();
	            }
	            catch (IOException e1)
	            {
	            }
	        }
	    }
	    
	    return true;
	}
	
	public void unloadFile()
	{
		map.clear();
	}
	
	public String getValue(String key, String def)
	{
		String strValue = map.get(key);
		if(strValue == null)
		{
			strValue = def;
		}
		
		return strValue;
	}
}
