/**
 * Project Name:autoJob
 * File Name:StaticMongoTest.java
 * Package Name:com.ec.autojob.basedaoTest
 * Date:2015年6月26日下午9:05:15
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.basedaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ec.autojob.daoImpl.StaticMsgDaoImpl;
/**
 * ClassName:StaticMongoTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月26日 下午9:05:15 <br/>
 * @author  xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component
public class StaticMongoDaoTest extends BaseDaoTest{
	
	@Autowired
	private StaticMsgDaoImpl dao;
	
	
	@Test
	public void test(){
          	System.out.println(dao);
	}
    
	//测试每日统计业务数据是否准确，和在mongodb js终端下运行结果是否一致
	@Test
	public void testEveryPCEIM(){
		/*System.out.println(dao.staticEveryMsgFromEC("3","1","staticmsg"));
		System.out.println(dao.staticEveryMsgFromEC("3","2","staticmsg"));
		System.out.println(dao.staticEveryMsgFromEC("1","1","staticmsg"));
		System.out.println(dao.staticEveryMsgFromEC("1","2","staticmsg"));
		System.out.println(dao.staticEveryMsgFromEC("2","1","staticmsg"));*/
		System.out.println(dao.staticEveryMsgFromEC(null,"3","staticmsg"));
		
	}
	
	// 测试eim 活跃用户量
	@Test
	public void testUserKF(){
		float eimTotla = dao.staticUserORcustormFromEC("sender", "1", null, "staticmsg");
		System.out.println(eimTotla);
		float eimtotalCorp = dao.staticUserORcustormFromEC("corpid", "1", null, "staticmsg");
		System.out.println(eimtotalCorp);
		
		float totalmsg = dao.staticEveryMsgFromEC(null, "1", "staticmsg");
		System.out.println(totalmsg);
		System.out.println(totalmsg/eimTotla);
		
		
		float ios = dao.staticUserORcustormFromEC("sender", "1", "2", "staticmsg");
		System.out.println(ios);
		
	}
	
	
	// 测试
	@Test
	public void testCustomer(){
		
	}
	
}

