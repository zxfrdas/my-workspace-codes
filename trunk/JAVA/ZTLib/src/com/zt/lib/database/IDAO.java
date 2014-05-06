package com.zt.lib.database;

import java.util.List;

public interface IDAO<T> {

	/**
	 * 数据库单个插入操作。
	 * @param item 试图插入的数据类
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean insert(T item);
	
	/**
	 * 数据库多条插入操作。
	 * @param items 试图插入的数据类集合
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean insert(List<T> items);
	
	/**
	 * 数据库单个删除操作。
	 * @param condition 操作的条件
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean delete(ExecCondition condition);
	
	/**
	 * 数据库多条删除操作
	 * @param items 试图删除的数据类集合
	 * @param condition 操作的条件
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean delete(List<T> items, ExecCondition condition);
	
	/**
	 * 数据库表删除所有条目操作
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean deleteAll();
	
	/**
	 * 数据库单个更新操作
	 * @param item 试图更新的数据类
	 * @param condition 操作的条件
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean update(T item, ExecCondition condition);
	
	/**
	 * 数据库多条更新操作
	 * @param items 试图更新的数据类集合
	 * @param condition 操作的条件
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean updateList(List<T> items, ExecCondition condition);
	
	/**
	 * 数据库全部更新操作
	 * @param items 试图更新的数据
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean updateAll(T item);
	
	/**
	 * 数据库单个查询操作
	 * @param condition 操作的条件
	 * @return 查询所得数据类
	 */
	public T query(ExecCondition condition);
	
	/**
	 * 数据库查询表中所有数据
	 * @param condition 操作的条件
	 * @return 查询所得数据类集合
	 */
	public List<T> queryAll(ExecCondition condition);
	
	/**
	 * 获取共有多少行数据
	 * @return 表的行数
	 */
	public int getCount();
	
	/**
	 * 关闭数据库
	 */
	public void close();
}
