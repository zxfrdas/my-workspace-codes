package com.zt.lib.database;

import java.util.List;

/**
 * 数据库操作接口
 * @author zhaotong
 * @param <T> 数据库对应JAVA层数据类
 * @param <K> 操作依据
 */
public interface IDAO<T, K> {

	/**
	 * 数据库单个插入操作。
	 * @param table 进行插入操作的表名
	 * @param item 试图插入的数据类
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean insert(String table, T item);
	
	/**
	 * 数据库单个删除操作。
	 * @param table 进行删除操作的表名
	 * @param key 删除依据
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean delete(String table, K key);
	
	/**
	 * 数据库单个更新操作
	 * @param table 进行更新操作的表名
	 * @param item 试图更新的数据类
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean update(String table, T item);
	
	/**
	 * 数据库单个查询操作
	 * @param table 进行查询操作的表名
	 * @param key 查询依据
	 * @return 查询所得数据类
	 */
	public T query(String table, K key);
	
	/**
	 * 数据库多条插入操作。
	 * @param table 进行插入操作的表名
	 * @param items 试图插入的数据类集合
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean insertAll(String table, List<T> items);

	/**
	 * 数据库多条删除操作
	 * @param table 进行删除操作的表名
	 * @param items 试图删除的数据类集合
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean deleteAll(String table, List<T> items);
	
	/**
	 * 数据库多条更新操作
	 * @param table 进行更新操作的表名
	 * @param items 试图更新的数据类集合
	 * @return 成功返回{@code true}，反之{@code false}
	 */
	public boolean updateAll(String table, List<T> items);
	
	/**
	 * 数据库查询表中所有数据
	 * @param table 试图进行查询操作的表名
	 * @param orderBy 结果排序依据
	 * @return 查询所得数据类集合
	 */
	public List<T> queryAll(String table, String orderBy);
	
	/**
	 * 获取指定表中共有多少行数据
	 * @param table 试图进行查询的表名
	 * @return 表的行数
	 */
	public int getCount(String table);
	
}
