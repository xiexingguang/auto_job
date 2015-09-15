package com.ec.autojob.basedaoTest;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ec.autojob.bean.AutoTaskBean;
import com.ec.autojob.bean.StaticWXBean;
import com.ec.autojob.dao.AutoTaskDao;
import com.ec.autojob.db.DBContextHolder;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext.xml"})
public class BaseDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private JdbcTemplate jdbpcTemplate;
	@Autowired
	@Qualifier("autoTaskDaoImpl")
	private AutoTaskDao autoDao;
	@Test
	public void testInsert(){
		//org.springframework.jdbc.core.JdbcTemplate@427f722
	//DBContextHolder.setDBType("dataSource2");
	System.out.println(jdbpcTemplate.queryForInt("select count(*) from acl_user;"));

	//int sss =    jdbpcTemplate.queryForInt("SELECT f_group_id FROM d_ec_user.t_user_group WHERE f_id = 937745 and f_type=2");
 //   System.out.println(sss);
	}
	
	@Test
	public void testQuery(){
	  List<AutoTaskBean> autos = autoDao.getAutoTasks();
	  for(AutoTaskBean a : autos){
		  System.out.println(a.getTaskName());
		  Date d = a.getLastStartDate();   
		  Date dd = a.getLastEndDate();
	  }
	  System.out.println(autoDao);
	}
	 
	@Test
	public  void update(){
	
	   AutoTaskBean a = new AutoTaskBean();
	   a.setId(1);
	   a.setStarting("3");
	   a.setLastEndDate(new Date());
	   a.setLastStartDate(new Date());
	   autoDao.update(a);
	}
	
	@Test
	public void testInsertWX(){
		StaticWXBean wx = new StaticWXBean();
		wx.setCorpId(1314);
		/*wx.setMsgCount(1314);
		wx.setSendType(4444);*/
		wx.setUsedTime("2015-09-14");
		autoDao.insert(wx);
		
	}

}
