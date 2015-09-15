/**
 * Project Name:autoJob
 * File Name:DealLogResultHandle.java
 * Package Name:com.ec.autojob.api
 * Date:2015年7月30日下午3:38:32
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ec.autojob.common.exception.HandleCollectDataException;
import com.ec.autojob.daoImpl.BaseRepository;
import com.ec.autojob.properties.MongoProperties;
import com.ec.autojob.util.StringUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoSocketReadException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * ClassName:DealLogResultHandle <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月30日 下午3:38:32 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="dealLogResultHandle")
public class DealLogResultHandle {
	private static final Logger LOG = LogManager.getLogger("apiLog");
	private static final Logger ERROR_LOG = LogManager.getLogger("errorLog");
	
	@Autowired
	private MongoProperties mongoProperties;
	@Autowired
	@Qualifier("collectionResultDaoImpl")
	private  BaseRepository  mongoc;
	
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	

	/**
	 * 因为业务需要根据集合不同名称拿不同了连接，目前针对mongobd客户端api还不清楚
	 * 到底mongo.getclient. 就连接了 还是要等到 client.getdatabase才去连接
	 * 或者是拿到数据库了，先是拿到相同集合的连接就放cach里面，如果有相同集合统计
	 * 只拿一次
	 */
	private HashMap<String,MongoCollection<Document>> collectionCach = new HashMap<String, MongoCollection<Document>>();

   
	/**
	 * 
	 * handleMsgBeans:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author xxg
	 * @param lists 待插入的集合
	 * @param fileName  默认的日志文件名称，  
	 * @param fileNewName 用户自定义的文件名
	 * @since JDK 1.7
	 */
	public <T> void handleMsgBeans(List<T> lists, String fileName,String fileNewName) {
		
		 String json  = null;
		 
			try{	 
				 LOG.info("mongdb 收集日志处理begin。。。。。，处理的集合名为====》"+fileName); 
				 if(mongoClient == null){
			     	 mongoClient = mongoc.getMongoClient();
				 }
				 if(database == null){
					 database = mongoClient.getDatabase(mongoProperties.MONGO_DATABASE);
				 }
				 // 先去cache 里面拿，如果拿不到在获取
				 String collectionName = "";
				 if(StringUtil.isNullString(fileNewName)){
					 collectionName = StringUtil.pareFileName(fileName);// 根据日志名称生成数据库集合名
				 }else{
					 collectionName = fileNewName;
				 }
				 MongoCollection<Document> collection = collectionCach.get(collectionName);
				 if(collection == null){
					 collection = database.getCollection(collectionName);
					 collectionCach.put(collectionName, collection);
				 }
				 
				 // 循环变量进行封装数据批量成插入
				 if(lists == null || lists.size() == 0){
					   return;
				 }
				 
				 List<Document> documents = new ArrayList<Document>();
				 for(int i=0; i<lists.size() ; i++){
					 T t = lists.get(i);
					 json = JSONObject.toJSONString(t);
					 if(LOG.isDebugEnabled()){
					      LOG.debug("收集日志信息数据，要插入mongodb的格式为===》"+json);
					 }
					 Document o = Document.parse(json);
					 documents.add(o);
				 }//end for
			     collection.insertMany(documents); //批量插入
			     
			}catch(MongoSocketReadException e){ // 如果捕获mongod socket读的异常，如果
				 throw e; //直接抛出
			}catch(Exception e){
				ERROR_LOG.error("将收集数据插入mongodb出现异常，异常信息为==={"+e.toString()+"}出现异常的消息日志数据为===》"+json);
				throw new HandleCollectDataException("将日志信息数据插入mongodb出现异常", e);
			}
	}
} 

