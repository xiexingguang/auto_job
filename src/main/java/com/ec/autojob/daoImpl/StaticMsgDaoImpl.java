/**
 * Project Name:autoJob
 * File Name:StaticMsgSumDaoImpl.java
 * Package Name:com.ec.autojob.daoImpl
 * Date:2015年6月26日下午6:00:23
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.daoImpl;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Component;

import com.ec.autojob.common.LogMsgMongoMap;
import com.ec.autojob.util.LogUtil;
import com.ec.autojob.util.StringUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
/**
 * ClassName:StaticMsgSumDaoImpl <br/>
 * Function: 统计各种业务dao层。由上层service
 * 依赖调用 <br/>
 * Date:     2015年6月26日 下午6:00:23 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component
public class StaticMsgDaoImpl extends BaseRepository{
    
	private static final Logger LOG = LogManager.getLogger("staticEveryJobLog");
	private static final Logger LOG_ERROR = LogManager.getLogger("errorLog");
	
	/**
	 * 这个集合名称是自动生成的。
	 */
	@Override
	public  MongoCollection<Document> getCollection() {
	   return getMongoDataBase().getCollection(LogUtil.generatColectionNameByDay());
		
	}
	
	
	/**
	 * 
	 * staticChatTypeFromMsgType:(这里用一句话描述这个方法的作用). <br/>
	 * @author xxg
	 * @param msgType
	 * @return
	 * @since JDK 1.7
	 */
	public float staticChatTypeFromMsgType(String msgType,String collectionName){
		try{	
			String cName = "";
		    if(collectionName == null || collectionName == ""){  // 集合名词扩展部分，如果调用不传集合名称则默认生成的集合名称为当前时间前一天比如今天是20150628 则生成为cmsg20150627
		          cName = LogUtil.generatColectionNameByDay();	
		    }else{
		    	  cName = collectionName;
		    }	
			MongoCollection<Document> collection = getMongoDataBase().getCollection(cName);
			
			if(StringUtil.isNullString(msgType)){
				return collection.count(); //如果消息字段为null 就统计总数
			}
		
			return collection
				   .count((Filters.eq(LogMsgMongoMap.MSG_TYPY, msgType)));
		}catch(Exception e){
			 LOG_ERROR.error("统计每日消息发生异常，统计集合名称为==>"+collectionName +"异常信息为==="+e.toString());
			 return -1;
		}
	}
	
	/**
	 * 统计每日消息业务逻辑,这些逻辑涵盖了每日消息统计前面需求
	 * @param zdtype
	 * @param module
	 * @return
	 */
	public float staticEveryMsgFromEC(String zdtype,String module,String collectionName){
		
	try{	
		String cName = "";
	    if(collectionName == null || collectionName == ""){  // 集合名词扩展部分，如果调用不传集合名称则默认生成的集合名称为当前时间前一天比如今天是20150628 则生成为cmsg20150627
	          cName = LogUtil.generatColectionNameByDay();	
	    }else{
	    	  cName = collectionName;
	    }	
		MongoCollection<Document> collection = getMongoDataBase().getCollection(cName);
		
		if(StringUtil.isNullString(module) && StringUtil.isNullString(zdtype)){
			return collection.count();
		}
		if(StringUtil.isNullString(zdtype)){
			return collection.count(Filters.and(Filters.eq(LogMsgMongoMap.MODULE, module)));
		}
		if(StringUtil.isNullString(module)){
			return collection.count(Filters.and(Filters.eq(LogMsgMongoMap.ZD_TYPE, zdtype)));
		}
		
		return collection
			   .count(Filters.and
					  (Filters.eq(LogMsgMongoMap.ZD_TYPE, zdtype),
				      Filters.eq(LogMsgMongoMap.MODULE, module)));
	}catch(Exception e){
		 LOG_ERROR.error("统计每日消息发生异常，统计集合名称为==>"+collectionName +"异常信息为==="+e.toString());
		 return -1;
	}
  }
	
	
	
	//c.distinct("sender", String.class).filter(Filters.eq("module", "1")).into(new ArrayList()).size());

	/**
	 * 
	 * @param fitterFileName
	 * @param module
	 * @param zdtype
	 * @return -1 ,表示统计发生异常
	 */
		
	public float staticUserORcustormFromEC(String fitterFileName,String module,String zdtype,String collectionName ){
		if(fitterFileName == null || fitterFileName == ""){
			throw new NullPointerException("过滤名称不能为null");
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug("dao层 要操作的集合名称为====》"+collectionName);
		}
		
	 try{
		String cName = "";
	    if(collectionName == null || collectionName == ""){
	          cName = LogUtil.generatColectionNameByDay();	
	    }else{
	    	  cName = collectionName;
	    }	 
		MongoCollection<Document> collection = getMongoDataBase().getCollection(cName);
		
		// 如果 module == null 且zdtpye==null
		if(StringUtil.isNullString(module) && StringUtil.isNullString(zdtype)){
		  return collection.distinct(fitterFileName, String.class).into(new ArrayList<Object>()).size();
		}
		// 如果moudle == null
		if(StringUtil.isNullString(module)){//为null
		  return collection.distinct(fitterFileName, String.class)
					.filter(
							Filters.eq(LogMsgMongoMap.ZD_TYPE, zdtype)).into(new ArrayList<Object>()).size();
		}
		// 如果 zdtype == null
		if(StringUtil.isNullString(zdtype)){//为null
		  return collection.distinct(fitterFileName, String.class)
					.filter(
							Filters.eq(LogMsgMongoMap.MODULE, module)).into(new ArrayList<Object>()).size(); 	
		}
		
		return collection.distinct(fitterFileName, String.class)
				.filter(Filters.and
					(Filters.eq(LogMsgMongoMap.MODULE, module),
					Filters.eq(LogMsgMongoMap.ZD_TYPE, zdtype))).into(new ArrayList<Object>()).size();
	 }catch(Exception e){
		 LOG_ERROR.error("统计统计集合名称为==>"+collectionName +"异常信息为==="+e.toString());
		 return -1;
	 }
		
	}

}

