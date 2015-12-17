/**
 * Project Name:autoJob
 * File Name:StaticMsgConsumerSeviceImpl.java
 * Package Name:com.ec.autojob.serviceImp
 * Date:2015年6月25日下午7:09:54
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.serviceImp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ec.autojob.common.LogConig;
import com.ec.autojob.common.LogMsgMongoMap;
import com.ec.autojob.daoImpl.StaticMsgDaoImpl;
import com.ec.autojob.service.StaticMsgService;
import com.ec.autojob.util.StringUtil;

/**
 * ClassName:StaticMsgConsumerSeviceImpl <br/>
 * Function: TODO 统计网站客服消息量<br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月25日 下午7:09:54 <br/>
 * @author  xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="staticMsgConsumerServiceImp")
public class StaticMsgConsumerSeviceImpl  implements  StaticMsgService{
	
	private static final Logger LOG = LogManager.getLogger("staticCustomerJobLog");
	@Autowired
	private StaticMsgDaoImpl staticMsgDao;
	






	@Override
	public <T> T handlerStaticData(String colletcionName, T t) {
		
		LOG.info("==============执行统计客服消息service开始执行================");
		
		//发送客服消息的用户
	    double msgKfTotalUser = staticMsgDao.
	    		staticUserORcustormFromEC(LogMsgMongoMap.SENDER, LogConig.MODULE_KF, null, colletcionName);
	    
	    //发送客服消息的企业数
	    double msgKfTotalCorp = staticMsgDao.
	    		staticUserORcustormFromEC(LogMsgMongoMap.CORP_ID, LogConig.MODULE_KF, null, colletcionName);
	    
	    double msgFk = staticMsgDao.
	    		staticUserORcustormFromEC(LogMsgMongoMap.SENDER, LogConig.MODULE_FK, null, colletcionName);
	    
	    //客服消息总数
	    double kfMsgTotal = staticMsgDao.staticEveryMsgFromEC(null, LogConig.MODULE_KF, colletcionName);
	    
	  //平均每个企业发送的客服消息数
	    double avgKfCorp = 0;
	    if(msgKfTotalCorp != 0.0){
	    	avgKfCorp = kfMsgTotal / msgKfTotalCorp;
	    }
	    
	    double iosKfUser = staticMsgDao.
	    		staticUserORcustormFromEC(LogMsgMongoMap.SENDER, LogConig.MODULE_KF, LogConig.ZD_TYPE_IOS, colletcionName);
	    
	    double androidUser = staticMsgDao.
	    		staticUserORcustormFromEC(LogMsgMongoMap.SENDER, LogConig.MODULE_KF, LogConig.ZD_TYPE_ANDORID, colletcionName);
	    
	    
	    
	    double ecliteKfUser = iosKfUser + androidUser;
	    		
	    double iosKfCorp = staticMsgDao.
	    		staticUserORcustormFromEC(LogMsgMongoMap.CORP_ID, LogConig.MODULE_KF, LogConig.ZD_TYPE_IOS, colletcionName);
	    
	    double andoridKfCorp = staticMsgDao.
	    		staticUserORcustormFromEC(LogMsgMongoMap.CORP_ID, LogConig.MODULE_KF, LogConig.ZD_TYPE_ANDORID, colletcionName);
	    
	    double ecliteKfcorp = iosKfCorp + andoridKfCorp;
	    
	    double staticTime = System.currentTimeMillis();
	    
	    double[] results = new double[8];
	    
	    results[0] = msgKfTotalUser;
	    LOG.info("msgKfTotalUser"+msgKfTotalUser);
		results[1] = msgKfTotalCorp;
		LOG.info("msgKfTotalCorp"+msgKfTotalCorp);
		results[2] = msgFk;
		LOG.info("msgFk"+msgFk);
		results[3] = avgKfCorp;
		LOG.info("avgKfCorp"+avgKfCorp);
		results[4] = ecliteKfUser;
		LOG.info("ecliteKfUser"+ecliteKfUser);
		results[5] = ecliteKfcorp;
		LOG.info("ecliteKfcorp"+ecliteKfcorp);
		results[6] = staticTime;
		LOG.info("staticTime"+staticTime);
		String coName = StringUtil.pareColelctionName(colletcionName);
		results[7] = Double.parseDouble(coName); //将集合转成flaot存进
		LOG.info("coName"+results[7]);
		
		T bean = null;
		
		try{
		 bean = StringUtil.convertStringArray2Object(results, t);
		}catch(Exception e){
		 LOG.error("转换统计网站客服结果信息bean出现异常，异常信息为==》"+e.toString()); //如果出现异常可以将jons格式传回
		}
		return bean;
	}
 
	
	 public static void main(String[] args) {
		 double ss  = 100;
		 double xx =  3;
		  
		 
		 double hhh = 10;
		 double mmmm = 3;
		 System.out.println(hhh/mmmm);
		 double ssss= ss/xx;
		System.out.println(ss/xx);
		System.out.println(ssss);
	}
}

