/**
 * Project Name:autoJob
 * File Name:StaticMsgUserServiceImpl.java
 * Package Name:com.ec.autojob.serviceImp
 * Date:2015年6月25日下午7:09:11
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ec.autojob.common.LogConig;
import com.ec.autojob.common.LogMsgMongoMap;
import com.ec.autojob.daoImpl.StaticMsgDaoImpl;
import com.ec.autojob.db.DBContextHolder;
import com.ec.autojob.service.StaticMsgService;
import com.ec.autojob.util.DButil;
import com.ec.autojob.util.StringUtil;

/**
 * ClassName:StaticMsgUserServiceImpl <br/>
 * Function: TODO 统计EIM用户数<br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月25日 下午7:09:11 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="staticMsgUserServiceImpl2")
public class StaticMsgUserApi implements StaticMsgService {
    
	
	private static final Logger LOG = LogManager.getLogger("apiLog");
	@Autowired
	private StaticMsgDaoImpl staticMsgDao;
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	
	
	
	
	
	@Override
	public <T> T handlerStaticData(String colletcionName, T t) {
	    
		LOG.info("==============执行统计每日EIM活跃用户service开始执行================");
		//有通过eim发送消息的用户总数
		double msgTotalUser = staticMsgDao.
				staticUserORcustormFromEC(LogMsgMongoMap.SENDER,LogConig.MODULE_EIM, null, colletcionName);
		//有通过eim发送的企业总数
		double msgTotalCorp = staticMsgDao.
				staticUserORcustormFromEC(LogMsgMongoMap.CORP_ID,LogConig.MODULE_EIM, null, colletcionName);
		
		//EIM总消息数
		double tempSumMsg = staticMsgDao.staticEveryMsgFromEC(null, LogConig.MODULE_EIM, colletcionName);
		
	    
		//平均每个用户发的消息量
		double avgUser = 0;
		if(msgTotalUser != 0.0){
			avgUser = tempSumMsg / msgTotalUser;
		}
		//平均每个企业法的消息量
		double avgCorp = 0;
		if(msgTotalCorp != 0.0){
			avgCorp = tempSumMsg / msgTotalCorp;
		}
			
		
		double iosUser = staticMsgDao.
				staticUserORcustormFromEC
				(LogMsgMongoMap.SENDER, LogConig.MODULE_EIM, LogConig.ZD_TYPE_IOS, colletcionName);
		
		double andoridUser = staticMsgDao.
				staticUserORcustormFromEC
				(LogMsgMongoMap.SENDER, LogConig.MODULE_EIM, LogConig.ZD_TYPE_ANDORID, colletcionName);
		
		double ecliteUser = iosUser + andoridUser;
		
		double iosCorp = staticMsgDao.
				staticUserORcustormFromEC
				(LogMsgMongoMap.CORP_ID, LogConig.MODULE_EIM, LogConig.ZD_TYPE_IOS, colletcionName);
		
		double andoridCopr = staticMsgDao.
				staticUserORcustormFromEC
				(LogMsgMongoMap.CORP_ID, LogConig.MODULE_EIM, LogConig.ZD_TYPE_ANDORID, colletcionName);
		
		double ecliteCorp = iosCorp + andoridCopr;
		
		double wxUser = staticMsgDao.
				staticUserORcustormFromEC
				(LogMsgMongoMap.SENDER, LogConig.MODULE_EIM, LogConig.ZD_TYPE_WX, colletcionName);
		
		
		double wxCorp = staticMsgDao.
				staticUserORcustormFromEC
				(LogMsgMongoMap.CORP_ID, LogConig.MODULE_EIM, LogConig.ZD_TYPE_WX, colletcionName);
		 
		double staticTime = System.currentTimeMillis();
		double[] results = new double[10];
		results[0] = msgTotalUser;
		results[1] = msgTotalCorp;
		results[2] = avgUser;
		results[3] = avgCorp;
		results[4] = ecliteUser;
		results[5] = ecliteCorp;
		results[6] = wxUser;
		results[7] = wxCorp;
		results[8] = staticTime;
		
		//cmsg20150524 将日志名称解析成20150524格式的
		String coName = StringUtil.pareColelctionName(colletcionName);
		results[9] = Double.parseDouble(coName); //将集合转成flaot存进
		
		T bean = null;
		try{
		  bean = StringUtil.convertStringArray2Object(results, t);
		  LOG.info("每日EIM消息统计==》"+JSONObject.toJSONString(bean));
		}catch(Exception e){
		 LOG.error("转换统计结果bean出现异常，异常信息为=="+e);
		}
		
		//插入db操作
				try{
					DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE);
				    LOG.info("将统计结果插入db库中==============》");
				    String sql =null;
				    sql = DButil.generatorInsertSqlFromBean(bean,"d_ec_new_statistics");
				    LOG.info("插入sql语句为======》"+sql);
				    jdbcTemplate.update(sql);
				}catch(Exception e){
					 throw e;
				}
		return bean;
	}

}

