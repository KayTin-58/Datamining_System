package com.zhang;


import com.zhang.dao.PersonDao;
import com.zhang.entity.Person;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataminingSystemApplicationTests {
   private static Logger logger=Logger.getLogger(DataminingSystemApplicationTests.class);

	@Autowired
	PersonDao personDao;


	@Test
	public void contextLoads() {
		personDao.save(new Person("1","张波",23));
	}


	@Test
	public void get() {
		Person p=personDao.getPerson();
		System.out.println(p.toString());
	}

}
