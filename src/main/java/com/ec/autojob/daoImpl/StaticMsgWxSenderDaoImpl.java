/**
 * Project Name:autoJob
 * File Name:StaticMsgWxSenderServiceImpl.java
 * Package Name:com.ec.autojob.serviceImp
 * Date:2015年8月20日下午12:40:12
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.daoImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Component;
import com.ec.autojob.common.LogMsgMongoMap;
import com.ec.autojob.util.LogUtil;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/**
 * ClassName:StaticMsgWxSenderServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年8月20日 下午12:40:12 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component
public class StaticMsgWxSenderDaoImpl extends BaseRepository{

	private static final Logger LOG = LogManager.getLogger("staticWxJobLog");
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
	 * findWXSender:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	      得到不重复的企业id,这个企业id会排查c++那边过来的
	 * @author xxg
	 * @param collectionName
	 * @return
	 * @since JDK 1.7
	 */
	public List<String> findWXSender(String collectionName){
		try{	
			String cName = "";
		    if(collectionName == null || collectionName == ""){  // 集合名词扩展部分，如果调用不传集合名称则默认生成的集合名称为当前时间前一天比如今天是20150628 则生成为cmsg20150627
		          cName = LogUtil.generatColectionNameByDay();	
		    }else{
		    	  cName = collectionName;
		    }	
		MongoCollection<Document> collection = getMongoDataBase().getCollection(cName);
		DistinctIterable<String> s = collection.distinct(LogMsgMongoMap.CORP_ID, String.class)
				.filter(Filters.in(LogMsgMongoMap.MODULE, "1100","1200"));
		List<String>  corpids = new ArrayList<String>();
		Iterator<String> logs = s.iterator();
		while(logs.hasNext()){
			String corp_id = logs.next();
			if(!corp_id.contains("wx")) corpids.add(corp_id); //过滤掉企业id为wxid情况
		}
		LOG.debug("消息统计企业数量:"+corpids.size());
		return corpids;
		}catch(Exception e){
			 LOG_ERROR.error("微信统计发送dao层==>"+collectionName +"异常信息为==="+e.toString());
			 return null;
		}
	}
	
	
	//根据企业id，发送类型，统计该企业id 从ec发送到微信的量。1表示从ec发到微信，2
	public long getNumCorpIdSender(String corpId,String sendType,String collectionName){
		try{	
			String cName = "";
		    if(collectionName == null || collectionName == ""){  // 集合名词扩展部分，如果调用不传集合名称则默认生成的集合名称为当前时间前一天比如今天是20150628 则生成为cmsg20150627
		          cName = LogUtil.generatColectionNameByDay();	
		    }else{
		    	  cName = collectionName;
		    }	
	    MongoCollection<Document> collection = getMongoDataBase().getCollection(cName);
	    return collection.count(Filters.and
						  (Filters.eq(LogMsgMongoMap.CORP_ID, corpId),
					      Filters.eq(LogMsgMongoMap.SENDTYPE, sendType)));
		}catch(Exception e){
			 LOG_ERROR.error("微信统计发送==>"+collectionName +"异常信息为==="+e.toString());
			 return -1;
		}
		
		
	}
     
}

