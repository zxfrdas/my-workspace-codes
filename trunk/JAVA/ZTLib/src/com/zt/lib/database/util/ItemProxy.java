package com.zt.lib.database.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.database.Cursor;

import com.zt.lib.database.Column;
import com.zt.lib.database.SQLite3DataType;
import com.zt.lib.database.Table;
import com.zt.lib.util.Reflector;

public class ItemProxy<T> {
	
	private static class ColumnItem {
		private int index;
		private String name;
		private SQLite3DataType type;
		private Field field;
	}
	
	private static class RowItem {
		private Map<Integer, ColumnItem> columns = new HashMap<Integer, ItemProxy.ColumnItem>();
	}
	
	private T item;
	private Class<?> clazz;
	private RowItem row;
	
	public ItemProxy(T item) {
		this.item = item;
		this.clazz = item.getClass();
		this.row = new RowItem();
		analyzeItem();
	}
	
	private void analyzeItem() {
		Annotation t = clazz.getAnnotation(Table.class);
		if (null == t) throw new NullPointerException("Must Have Table Name");
		Field[] fields = Reflector.getFields(item);
		for (Field field : fields) {
			Annotation c = field.getAnnotation(Column.class);
			if (null != c) {
				ColumnItem column = new ColumnItem();
				if (0 == ((Column) c).index()) {
					// primary id, ignore
					continue;
				}
				column.index = ((Column) c).index();
				column.name = ((Column) c).name();
				column.type = ((Column) c).type();
				column.field = field;
				row.columns.put(column.index, column);
			}
		}
	}
	
	public T getItemFromDB(Cursor c) throws IllegalAccessException, IllegalArgumentException {
		for (Entry<Integer, ColumnItem> map : row.columns.entrySet()) {
			int index = map.getKey();
			SQLite3DataType sqlite3type = map.getValue().type;
			Field field = map.getValue().field;
			Class<?> fieldType = field.getType();
			switch (sqlite3type)
			{
			case BLOB:
				field.set(item, c.getBlob(index));
				break;
				
			case INTEGER:
				if (boolean.class.equals(fieldType) || Boolean.class.equals(fieldType)) {
					field.set(item, 1 == c.getInt(index) ? true : false);
				} else if (int.class.equals(fieldType) || Integer.class.equals(fieldType)) {
					field.set(item, c.getInt(index));
				} else if (long.class.equals(fieldType) || Long.class.equals(fieldType)) {
					field.set(item, c.getLong(index));
				} else if (short.class.equals(fieldType) || Short.class.equals(fieldType)) {
					field.set(item, c.getShort(index));
				}
				break;
				
			case REAL:
				if (float.class.equals(fieldType) || Float.class.equals(fieldType)) {
					field.set(item, c.getFloat(index));
				} else if (double.class.equals(fieldType) || Double.class.equals(fieldType)) {
					field.set(item, c.getDouble(index));
				}
				break;
				
			case TEXT:
				field.set(item, c.getString(index));
				break;
				
			case NULL:
			default:
				break;
			}
		}
		return item;
	}
	
	public ContentValues getContentValues() throws IllegalAccessException, IllegalArgumentException {
		ContentValues values = new ContentValues();
		for (Entry<Integer, ColumnItem> map : row.columns.entrySet()) {
			String name = map.getValue().name;
			SQLite3DataType sqlite3type = map.getValue().type;
			Field field = map.getValue().field;
			Class<?> fieldType = field.getType();
			switch (sqlite3type)
			{
			case BLOB:
				values.put(name, (byte[])field.get(item));
				break;
				
			case INTEGER:
				if (boolean.class.equals(fieldType) || Boolean.class.equals(fieldType)) {
					values.put(name, field.getBoolean(item) ? 1 : 0);
				} else if (int.class.equals(fieldType) || Integer.class.equals(fieldType)) {
					values.put(name, field.getInt(item));
				} else if (long.class.equals(fieldType) || Long.class.equals(fieldType)) {
					values.put(name, field.getLong(item));
				} else if (short.class.equals(fieldType) || Short.class.equals(fieldType)) {
					values.put(name, field.getShort(item));
				}
				break;
				
			case REAL:
				if (float.class.equals(fieldType) || Float.class.equals(fieldType)) {
					values.put(name, field.getFloat(item));
				} else if (double.class.equals(fieldType) || Double.class.equals(fieldType)) {
					values.put(name, field.getDouble(item));
				}
				break;
				
			case TEXT:
				values.put(name, field.get(item).toString());
				break;
				
			case NULL:
			default:
				break;
			}
		}
		return values;
	}
	
}
