package com.zt.taobao.output;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zt.taobao.Item;

public class ExcelOutput implements IOutput {

	private String mPath;
	private Workbook mWorkbook;
	
	public ExcelOutput(String filePath) {
		mPath = filePath;
		mWorkbook = new XSSFWorkbook();
	}
	
	@Override
	public boolean output(List<Item> items)
	{
		return false;
	}

}
