package com.zt.lib.database.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import android.test.mock.MockContext;

import com.zt.lib.database.DAOFactory;
import com.zt.lib.database.IDAO;
import com.zt.lib.database.impl.TestItemDAO;

public class SQLite3DAOTest {

	@Before
	public void setUp() throws Exception
	{
		IDAO<TestItem> test = DAOFactory.getInstance(TestItemDAO.class,
				new MockContext(), TestItem.class);
		IDAO<TestItem> test2 = new TestItemDAO(new MockContext(), TestItem.class);
	}

	@Test
	public void testInsertT()
	{
		fail("尚未实现");
	}

	@Test
	public void testInsertListOfT()
	{
		fail("尚未实现");
	}

	@Test
	public void testDeleteExecCondition()
	{
		fail("尚未实现");
	}

	@Test
	public void testDeleteListOfTExecCondition()
	{
		fail("尚未实现");
	}

	@Test
	public void testDeleteAll()
	{
		fail("尚未实现");
	}

	@Test
	public void testUpdate()
	{
		fail("尚未实现");
	}

	@Test
	public void testUpdateList()
	{
		fail("尚未实现");
	}

	@Test
	public void testUpdateAll()
	{
		fail("尚未实现");
	}

	@Test
	public void testQuery()
	{
		fail("尚未实现");
	}

	@Test
	public void testQueryAll()
	{
		fail("尚未实现");
	}

	@Test
	public void testGetCount()
	{
		fail("尚未实现");
	}

}
