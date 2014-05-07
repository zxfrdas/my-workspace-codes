package com.zt.lib.database.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.zt.lib.database.ExecCondition;
import com.zt.lib.database.IDAO;
import com.zt.lib.database.util.ItemProxy;

/**
 * 本工程所有数据库操作类均需继承此类。
 * <p>提供读/写锁，数据库{@code SQLiteDatabase}实例
 * @param <T> 数据库对应JAVA层数据类
 * @param <K> 操作依据
 * @see SQLiteDatabase
 */
public abstract class SQLite3DAO<T> extends SQLiteOpenHelper implements IDAO<T> {
	
	private final ReentrantReadWriteLock mLock;
	protected final ReadLock mReadLock;
	protected final WriteLock mWriteLock;
	protected SQLiteDatabase mDatabase;
	protected ItemProxy<T> mItemProxy;
	
	public SQLite3DAO(Context context, String name, int version, Class<?> item)
	{
		super(context, name, null, version);
		mItemProxy = new ItemProxy<T>(item);
		mLock = new ReentrantReadWriteLock();
		mReadLock = mLock.readLock();
		mWriteLock = mLock.writeLock();
		mDatabase = getWritableDatabase();
	}

	@Override
	public boolean insert(T item)
	{
		long ret = -1;
		ContentValues values = null;
		try {
			values = mItemProxy.getContentValues(item);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
		mWriteLock.lock();
		try {
			ret = mDatabase.insert(mItemProxy.getTableName(), null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
		} finally {
			mWriteLock.unlock();
		}
		if (-1 != ret) {
			return true;
		}
		return false;
	}

	@Override
	public boolean insert(List<T> items)
	{
		long ret = -1;
		ArrayList<ContentValues> values = new ArrayList<ContentValues>();
		for (T item : items) {
			ContentValues value = null;
			try {
				value = mItemProxy.getContentValues(item);
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			}
			values.add(value);
		}
		mWriteLock.lock();
		mDatabase.beginTransaction();
		try {
			for (ContentValues v : values) {
				ret = mDatabase.insert(mItemProxy.getTableName(), null, v);
			}
			mDatabase.setTransactionSuccessful();
		} catch (SQLiteException e) {
			e.printStackTrace();
			ret = -1;
		} finally {
			mDatabase.endTransaction();
			mWriteLock.unlock();
		}
		if (-1 != ret) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(ExecCondition condition)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(List<T> items, ExecCondition condition)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(T item, ExecCondition condition)
	{
		long ret = -1;
		ContentValues values = null;
		try {
			values = mItemProxy.getContentValues(item);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
		mWriteLock.lock();
		try {
			condition.setWhereColumns(mItemProxy.getColumnName(condition.getWhereFields()));
			ret = mDatabase.update(mItemProxy.getTableName(), values, condition.getWhereClause(),
					condition.getWhereArgs());
		} catch (SQLiteException e) {
			e.printStackTrace();
		} finally {
			mWriteLock.unlock();
		}
		if (-1 != ret) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateList(List<T> items, ExecCondition condition)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAll(T item)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T query(ExecCondition condition)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> queryAll(ExecCondition condition)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount()
	{
		mReadLock.lock();
		int count = mDatabase.query(mItemProxy.getTableName(), null, null, null, null, null, null).getCount();
		mReadLock.unlock();
		return count;
	}

}
