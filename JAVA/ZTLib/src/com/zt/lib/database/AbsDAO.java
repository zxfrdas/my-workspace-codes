package com.zt.lib.database;

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
	
	public static final String TAG = AbsDAO.class.getSimpleName();
	private final ReentrantReadWriteLock mLock;
	protected final ReadLock mReadLock;
	protected final WriteLock mWriteLock;
	protected SQLiteDatabase mDatabase;
	
	public AbsDAO(Context context, String name, int version)
	{
		super(context, name, null, version);
		mDatabase = getWritableDatabase();
		mLock = new ReentrantReadWriteLock();
		mReadLock = mLock.readLock();
		mWriteLock = mLock.writeLock();
	}

}
