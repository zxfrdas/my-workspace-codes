package com.zt.lib.database.util;

import java.util.List;

import android.content.ContentValues;

import com.zt.lib.database.SQLite3DataType;

public class ItemProxy<T> {
	
	private static class ColumnItem implements Comparable<ColumnItem> {
		private int index;
		private String name;
		private SQLite3DataType type;
		@Override
		public int compareTo(ColumnItem another) {
			if (this.index < another.index) {
				return -1;
			} else if (this.index > another.index) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	private static class RowItem {
		private List<ColumnItem> columns;
	}
	
	private T item;
	private Class<?> clazz;
	private RowItem row;
	
	public ItemProxy(T item) {
		this.item = item;
		this.clazz = item.getClass();
		this.row = new RowItem();
	}
	
	private void analyzeItem() {
		
	}
	
	public <V> void setValue(int index, V value) {
		
	}
	
	public ContentValues getValue(int index) {
		return null;
	}
	
	public ContentValues getValues() {
		return null;
	}

	public T getItem() {
		return this.item;
	}
	
}
