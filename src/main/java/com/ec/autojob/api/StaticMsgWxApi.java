/**
 * Project Name:autoJob
 * File Name:StaticMsgUserServiceImpl.java
 * Package Name:com.ec.autojob.serviceImp
 * Date:2015年6月25日下午7:09:11
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ec.autojob.bean.StaticWXBean;
import com.ec.autojob.common.LogConig;
import com.ec.autojob.common.LogMsgMongoMap;
import com.ec.autojob.dao.AutoTaskDao;
import com.ec.autojob.daoImpl.StaticMsgDaoImpl;
import com.ec.autojob.daoImpl.StaticMsgWxSenderDaoImpl;
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
@Component(value="staticMsgWxServiceImpl2")
public class StaticMsgWxApi implements StaticMsgService {
    
	
	private static final Logger LOG = LogManager.getLogger("apiLog");
	@Autowired
	private StaticMsgDaoImpl staticMsgDao;
	private static final Logger LOG_ERROR = LogManager.getLogger("errorLog");
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaticMsgWxSenderDaoImpl staticWxSenderDao;
	@Autowired
	private AutoTaskDao autoTaskDao;
	public static final int EC2WX = 1;
	public static final int WX2EC = 2;
	
	
	
	@Override
	public <T> T handlerStaticData(String colletcionName, T t) {
	    
		LOG.info("==============执行统计微信消息发送量service开始执行================");
		//消息总量
		double msgTotal = 0.0;
		//ec用户消息发送量
		double ecMsg = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_WX_EC, null, colletcionName);
		//非ec用户消息发送量
		double noEcMsg = staticMsgDao.staticEveryMsgFromEC(LogConig.ZD_TYPE_WX_NO_EC, null, colletcionName);
		
		msgTotal = noEcMsg + ecMsg;
		
		
		
		//ec用户数
		double ecUser = staticMsgDao.
				staticUserORcustormFromEC(LogMsgMongoMap.SENDER,null, LogConig.ZD_TYPE_WX_EC, colletcionName);
		
		//非ec用户数
		double noEcUser = staticMsgDao.
				staticUserORcustormFromEC(LogMsgMongoMap.SENDER,null, LogConig.ZD_TYPE_WX_NO_EC, colletcionName);
		
	    
		
		//EC企业数
		double ecCrop = staticMsgDao.
				staticUserORcustormFromEC(LogMsgMongoMap.CORP_ID,LogConig.MODULE_WX_CROP, null, colletcionName);
			
		//非ec企业数
		double noEcCrop = staticMsgDao.
				staticUserORcustormFromEC(LogMsgMongoMap.CORP_ID,LogConig.MODULE_WX_NO_CROP, null, colletcionName);
		//单聊消息数
		double singleMsg = staticMsgDao.staticChatTypeFromMsgType(LogConig.WX_MSG_TYPE_SINGLE, colletcionName);
		
		
		//群聊消息数
		double groupMsg_1 = staticMsgDao.staticChatTypeFromMsgType(LogConig.WX_MSG_TYPE_GROUP_1, colletcionName);
		double groupMsg_2 = staticMsgDao.staticChatTypeFromMsgType(LogConig.WX_MSG_TYPE_GROUP_2, colletcionName);
		double groupMsg = groupMsg_1 + groupMsg_2;
		
		
	//8/20号添加 统计新增的需求，比如统计企业id，从ec发送到微信的用户
		
		LOG.debug("统计企业id从ec发送到微信的业务逻辑开始执行=====>");
	
	try{
		// 封装统计结果
		List<String> allcorpIds = staticWxSenderDao.findWXSender(null); //所有的企业的id,nul表示使用默认生产的日期
	//	List<StaticWXBean> wxBeans = new ArrayList<StaticWXBean>();
		LOG.info("企业总数为"+allcorpIds.size());
		String time = StringUtil.pareColelctionName(colletcionName);
		if(allcorpIds!=null){
			for(int i = 0;i<allcorpIds.size();i++){
				String corpid = allcorpIds.get(i);
				// 企业id 的消息发送数量
				long ec2wxNums = staticWxSenderDao.getNumCorpIdSender(corpid,LogConig.WX_MSG_EC_TO_WX,null);
				long wx2ecNums = staticWxSenderDao.getNumCorpIdSender(corpid,LogConig.WX_MSG_WX_TO_EC,null);
				long totalMsg = ec2wxNums + wx2ecNums;
				
				StaticWXBean b = new StaticWXBean();
				b.setCorpId(Integer.parseInt(corpid));
				b.setEc2wxmsgCount((int)ec2wxNums);
				b.setWx2ecmsgCount((int)wx2ecNums);
				b.setUsedTime(time);     // 日志最后使用时间
				b.setTotalMsg(totalMsg);
				autoTaskDao.insert(b);  // 先单条插入 ，走离线统计 先简单起见不批量插
			}
		}
	}catch(Exception e){
		LOG_ERROR.error("微信统计消息从ec到wx或者是从微信到ec的消息数出现异常=="+e);
	}
		
		double staticTime = System.currentTimeMillis();
		double[] results = new double[11];
		results[0] = msgTotal;
		results[1] = ecMsg;
		results[2] = noEcMsg;
		results[3] = ecUser;
		results[4] = noEcUser;
		results[5] = ecCrop;
		results[6] = noEcCrop;
		results[7] = singleMsg;
		results[8] = groupMsg;
		results[9] = staticTime;
		String coName = StringUtil.pareColelctionName(colletcionName);
		results[10] = Double.parseDouble(coName); //将集合转成flaot存进
		
		T bean = null;
		try{
		  bean = StringUtil.convertStringArray2Object(results, t);
		  LOG.info("微信消息统计信息为==》"+JSONObject.toJSONString(bean));
		}catch(Exception e){
		  LOG_ERROR.error("转换统计结果bean出现异常，异常信息为=="+e);
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

