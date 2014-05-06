package com.zt.lib.database;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 本工程所有数据库操作类均需继承此类。
 * <p>提供读/写锁，数据库{@code SQLiteDatabase}实例
 * @param <T> 数据库对应JAVA层数据类
 * @param <K> 操作依据
 * @see SQLiteDatabase
 */
public abstract class AbsDAO<T, K> extends SQLiteOpenHelper
		implements IDAO<T, K> {
	
	private final ReentrantReadWriteLock mLock;
	protected final ReadLock mReadLock;
	protected final WriteLock mWriteLock;
	protected SQLiteDatabase mDatabase;
	
	public AbsDAO(Context context, String name, int version)
	{
		super(context, name, null, version);
		mLock = new ReentrantReadWriteLock();
		mReadLock = mLock.readLock();
		mWriteLock = mLock.writeLock();
		mDatabase = getWritableDatabase();
	}

	@Override
	public boolean insert(String table, T item)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String table, K key)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(String table, T item)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T query(String table, K key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertList(String table, List<T> items)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteList(String table, List<T> items)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(String table)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateList(String table, List<T> items)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<T> queryAll(String table, String orderBy)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount(String table)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub
		
	}

}
