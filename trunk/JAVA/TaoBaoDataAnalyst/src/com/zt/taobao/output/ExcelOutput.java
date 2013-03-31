package com.zt.taobao.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zt.lib.Reflector;
import com.zt.lib.collect.SingletonValueMap;
import com.zt.taobao.Item;

public class ExcelOutput implements IOutput {

	private String mPath;
	private Workbook mWorkbook;
	private SingletonValueMap<String, String> mNameAnnotion;
	private boolean bIsFirstCreat;
	
	public ExcelOutput(String filePath) {
		mPath = filePath;
		mNameAnnotion = new SingletonValueMap<String, String>();
	}
	
	@Override
	public boolean output(List<Item> items)
	{
		FileOutputStream outputStream = null;
		File mExcelFile = new File(mPath);
		bIsFirstCreat = false;
		if (!mExcelFile.exists()) {
			bIsFirstCreat = true;
		}
		try {
			mExcelFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bIsFirstCreat) {
			mWorkbook = new XSSFWorkbook();
			mWorkbook.createSheet();
		} else {
			try {
				mWorkbook = new XSSFWorkbook(new FileInputStream(mExcelFile));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Sheet sheet = mWorkbook.getSheetAt(mWorkbook.getActiveSheetIndex());
		for (Item item : items) {
			addRow(item, sheet);
		}
		try {
			outputStream = new FileOutputStream(mExcelFile, false);
			mWorkbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private void updateMap(Item item)
	{
		mNameAnnotion.clear();
		Field[] fields = Reflector.getFields(item);
		for (Field field : fields) {
			mNameAnnotion.put(field.getName(), Reflector.getFieldTargetNameValue(field));
		}
	}
	
	private void addRow(Item item, Sheet sheet)
	{
		if (0 == sheet.getLastRowNum()) {
			updateMap(item);
			createFirstRow(item, sheet);
		}
		Row row = sheet.createRow(sheet.getLastRowNum() + 1);
		for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i ++) {
			row.createCell(i);
		}
		String[] columnNames = Reflector.getFieldTargetNameValues(item);
		for (String columnName : columnNames) {
			try {
				String columnValue = (String) Reflector.getFieldValue(item,
						mNameAnnotion.getKeyByValue(columnName));
				row.getCell(getColumnIndex(sheet, columnName)).setCellValue(columnValue);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createFirstRow(Item item, Sheet sheet)
	{
		String[] columnNames = Reflector.getFieldTargetNameValues(item);
		Row firstRow = sheet.createRow(0);
		for (int index = 0; index < columnNames.length; index ++) {
			firstRow.createCell(index).setCellValue(columnNames[index]);
		}
	}
	
	private short getColumnIndex(Sheet sheet, String name)
	{
		Iterator<Cell> iterator = sheet.getRow(0).cellIterator();
		short index = 0;
		while (iterator.hasNext()) {
			Cell cell = iterator.next();
			if (null != cell) {
				if (cell.getStringCellValue().equals(name)) {
					break;
				}
				index ++;
			}
		}
		return index;
	}

}
