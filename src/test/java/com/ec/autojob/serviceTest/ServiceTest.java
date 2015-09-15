package com.ec.autojob.serviceTest;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ec.autojob.basedaoTest.BaseDaoTest;
import com.ec.autojob.bean.StaticSumResultBean;
import com.ec.autojob.bean.StaticUserResultBean;
import com.ec.autojob.bean.StaticWXBean;
import com.ec.autojob.daoImpl.StaticMsgWxSenderDaoImpl;
import com.ec.autojob.jetty.ServerJetty;
import com.ec.autojob.service.StaticMsgService;


public class ServiceTest extends BaseDaoTest {
	
	@Autowired
	@Qualifier("staticMsgSumServiceImpl")
	private StaticMsgService sum;
	@Autowired
	@Qualifier("staticMsgUserServiceImpl")
	private StaticMsgService usr;
	@Autowired
	@Qualifier("staticMsgConsumerServiceImp")
	private StaticMsgService con ;
   
	@Autowired
	@Qualifier("serverJetty")
	private ServerJetty server;
	@Autowired
	private StaticMsgWxSenderDaoImpl dao;
	
	@Test
    public void test() throws InterruptedException{
		Thread.sleep(200000);
		System.out.println(Thread.currentThread().getName());
    	System.out.println(sum+";"+usr+":"+con);
    }
	@Test
	public void test1(){
		System.out.println(Thread.currentThread().getName());
	}
	
	 
	@Test
	public void testSum(){
		StaticSumResultBean d = sum.handlerStaticData("staticmsg", new StaticSumResultBean());
		System.out.println(d.getTotalMsg());
	}
	
	@Test
	public void testUsr(){
		StaticUserResultBean sb = usr.handlerStaticData("staticmsg", new StaticUserResultBean());
	} 
	
	@Test
	public void testCon(){
		
		while(true){
			
			try {
				server.startJetty();
				Thread.sleep(2000000);
			} catch (InterruptedException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
		
		}
		
	
	  }
	  @Test
	  public void testStaticWXDaoImpl(){
		  List<String> staticBeans = dao.findWXSender( "cmsg20150819");
		  System.out.println(staticBeans.size());
		//  System.out.println(dao.getNumCorpIdSender("879091", "1", null));
				  
		  
	 // StaticCustResultBean co = con.handlerStaticData("staticmsg", new StaticCustResultBean());
	/*	
		File f = new File("E:\\home\\log\\manager_error");
		System.out.println(f.isFile());
		System.out.println(f.getName());*/
		
	}
	
}
