package com.atguigu.gmall.list;

import io.searchbox.client.JestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallListServiceApplicationTests {

	@Autowired
	/**
	 * 用于操作 elasticsearch 的类
	 */
	private JestClient jestClient;

	@Test
	public void contextLoads() {
	}

}
