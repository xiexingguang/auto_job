/**
 * Project Name:autoJob
 * File Name:StaticMsgSumServiceImpl.java
 * Package Name:com.ec.autojob.serviceImp
 * Date:2015年6月25日下午3:41:01
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
import com.ec.autojob.daoImpl.StaticMsgDaoImpl;
import com.ec.autojob.db.DBContextHolder;
import com.ec.autojob.service.StaticMsgService;
import com.ec.autojob.util.DButil;
import com.ec.autojob.util.StringUtil;
/**
 * ClassName:StaticMsgSumServiceImpl <br/>
 * Function: 统计消息发送总量，
 * 因为要提供rmi暴露接口，所以一个业务统计
 * 一个service，方便通过api方式手工调用
 *  <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月25日 下午3:41:01 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	  
 */
@Component(value="staticMsgSumServiceImpl2")
public class StaticMsgSumApi implements StaticMsgService {
	
	
	private static final Logger LOG = LogManager.getLogger("apiLog");
	@Autowired
	private StaticMsgDaoImpl staticMsgDao;
	@Autowired
	private  JdbcTemplate jdbcTemplate;
     // 统计每日消息各种逻辑	
	@Override
	public <T> T handlerStaticData(String colletcionName, T t) {
	    LOG.info("=============统计每日消息总量service方法开始========统计集合为====》【"+colletcionName+"】");
		double pcEIM = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_PC, LogConig.MODULE_EIM, colletcionName);
		double pcKF = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_PC, LogConig.MODULE_KF, colletcionName);
		double iosEIM = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_IOS, LogConig.MODULE_EIM, colletcionName);
		double iosKF = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_IOS, LogConig.MODULE_KF, colletcionName);
		double androidEIM = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_ANDORID, LogConig.MODULE_EIM, colletcionName);
		double androidKF = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_ANDORID, LogConig.MODULE_KF, colletcionName);
		double wx = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_WX, LogConig.MODULE_EIM, colletcionName);
		double fk = staticMsgDao.staticEveryMsgFromEC(null, LogConig.MODULE_FK, colletcionName);
		double totalMsg = staticMsgDao.staticEveryMsgFromEC(null, null, colletcionName);// 消息总数
		double staticTime = System.currentTimeMillis();
		double[] results = new double[11];
		results[0] = pcEIM;
		results[1] = pcKF;
		results[2] = iosEIM;
		results[3] = iosKF;
		results[4] = androidEIM;
		results[5] = androidKF;
		results[6] = wx;
		results[7] = fk;
		results[8] = totalMsg;
		results[9] = staticTime;
		String coName = StringUtil.pareColelctionName(colletcionName);
		results[10] = Double.parseDouble(coName); //将集合转成flaot存进
		//将结果转成实体bean
		T bean = null;
	try{	
		bean = StringUtil.convertStringArray2Object(results, t); //如果统计结果出来再转换bean出现异常怎么样处理？？？
		LOG.info("消息总量统计==》"+JSONObject.toJSONString(bean));
	}catch(Exception e){
		LOG.error("消息统计结果为===》"+e);
		//return (T)results;
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

