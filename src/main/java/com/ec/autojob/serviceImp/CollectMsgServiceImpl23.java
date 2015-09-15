/**
 * Project Name:autoJob
 * File Name:CollectMsgServiceImpl.java
 * Package Name:com.ec.autojob.serviceImp
 * Date:2015年6月24日下午1:54:47
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.serviceImp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ec.autojob.bean.LogMsgBean;
import com.ec.autojob.collectJob.core.CollectMsgExceptionHandler;
import com.ec.autojob.collectJob.core.PareDataRule;
import com.ec.autojob.common.MsgResultHandle;
import com.ec.autojob.common.exception.HandleCollectDataException;
import com.ec.autojob.common.exception.PareFileException;
import com.ec.autojob.properties.ConfigProperties;
import com.ec.autojob.service.CollectMsgFileLogService;
import com.ec.autojob.util.IoUtil;
import com.ec.autojob.util.StringUtil;
import com.mongodb.MongoSocketReadException;
/**
 * ClassName:CollectMsgServiceImpl <br/>
 * Function:  <br/>
 * Reason:	. <br/>
 * Date:     2015年6月24日 下午1:54:47 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value = "collectMsgServiceImpl23")
public class CollectMsgServiceImpl23  implements CollectMsgFileLogService{
    
	private static final Logger LOG = LogManager.getLogger("collectJobLog");
	private static final Logger LOG_ERROR = LogManager.getLogger("errorLog");
	
	/**
	 * 数据解析规类，将待转换的字符，根据指定解析规则，解析成对应的javabean对象
	 */
	@Autowired
    @Qualifier("pareString2MsgBean3")
	private PareDataRule pdrule;
	
	/**
	 * 消息结果出来策略，针对单条记录处理策略，有可能是入mysql库，可能入mogodb库，等。。。
	 */
	@Autowired
	@Qualifier("mongoMsgResultHandle")
	private MsgResultHandle msgHandle;
	
	@Autowired
	private ConfigProperties configPrpoerties;
	
	/**
	 * 程序将日志  string 转成bean 出现异常
	 */
	@Autowired
	@Qualifier("convertString2beanException")
	private RuntimeException  convertString2BeanException;
	
	
	/**
	 * 对应异常处理类
	 */
	@Autowired
	@Qualifier("convertString2beanExHandler")
	private CollectMsgExceptionHandler convertString2BeanExceptionHandler;
	
	/**
	 * 异常处理map，异常类型和对应的处理器
	 */
	private  HashMap<RuntimeException,CollectMsgExceptionHandler> EXCEPTION_HANDLER = new HashMap<RuntimeException, CollectMsgExceptionHandler>();
	
	
	@PostConstruct
	private void init(){
		EXCEPTION_HANDLER.put(convertString2BeanException, convertString2BeanExceptionHandler);
	}
	
	
	@Override
	public void collectMsgFromSingleFile(String filepath) {
		
			// boolean flag = false;
		     File  file  = new File(filepath);
		     if(!file.isFile()){ 
		    	  throw new IllegalArgumentException("路径为["+filepath+"]不是一个文件");
		     }
		     parseFile(file);
	}
	 
	/**
	 * 
	 * getpDataRule:日志解析规则，子类如果希望可以继承重写. <br/>
	 * @author xxg
	 * @return
	 * @since JDK 1.7
	 */
	  protected  PareDataRule getpDataRule(){
		   return pdrule; 
	  }
	  
	  /**
	   * 
	   * getMsgResultHandle:日志数据结果处理，子类可以重写该方法，默认为
	   * 将数据插入到mongodb中<br/>
	   * @author xxg
	   * @return
	   * @since JDK 1.7
	   */
	  protected  MsgResultHandle getMsgResultHandle(){
		    return msgHandle;
	  }

	@Override
	public void collectMsgFromFile(File file) {
		  
		 if(file == null || !file.exists()){
			 throw new NullPointerException("要解析的目录不存在或者为null");
		 }
	      if(file.isDirectory()){
	    	   File[] files = file.listFiles();
	    	   for(File f : files){
	    		   if(LOG.isDebugEnabled()){
	    			   LOG.debug("单个文件名称为===》"+f.getName() +"<===========单个日志文件路径为====》"+f.getAbsolutePath());
	    		   }
	    		   collectMsgFromSingleFileByFiles(f);   //统计数据，循环调用这样可能导致持续时间很长，这个方法会阻塞？优化？多线程？？先这样，如果有性能问题重写该方法
	    	   }
	      }	else{
	           collectMsgFromSingleFileByFiles(file);
	      }
	}

	
	// 收集单个文件数据
	/**
	 * 
	 * collectMsgFromSingleFileByFiles:(这里用一句话描述这个方法的作用). <br/>
	 * @author xxg
	 * @param file
	 * @since JDK 1.7
	 * @throws PareFileException :当文件解析的时候出现IO异常，这个时候异常处理不了，必须抛出
	 */
	private void collectMsgFromSingleFileByFiles(File file) {
		  
		 if(file == null){
			 throw new NullPointerException("传入文件为null");
		 }
		
		 if(!file.exists()){
			 if(LOG.isDebugEnabled()){
				  LOG.debug("单个文件绝对路径为"+file.getAbsolutePath()+"===名称为"+file.getName());
			 }
			 throw new RuntimeException("文件不存在"+file.getName());
		 }
		 
		 if(file.isDirectory()){
			 throw new RuntimeException("解析失败，该文件为一个目录，目录路径为"+file.getAbsolutePath());
		 }
		
		 parseFile(file);
	}
	
	private void parseFile(File file){
		
		 int mongoExceptionCount = 0 ;                           //在一次解析文件中，mongodb允许出现的错误次数，最开始进入方法时，默认为0次
		 List<String> strsCach = new ArrayList<String>();        //字符串缓冲对象，
		 List<String> dealLeakString = new ArrayList<String>();  // 处理遗漏的字符串
	     FileReader fReader = null;
	     BufferedReader bufferedReader = null;
	     String str = "";
	     String fileName = file.getName(); //文件名称
	     PareDataRule pareRule = null;
	     int cachSize = Integer.parseInt(configPrpoerties.CACHE_CAPICITY);
	    try {
	         fReader = new FileReader(file);
		     bufferedReader = new BufferedReader(fReader);
		     pareRule = getpDataRule();
		     while((str = bufferedReader.readLine()) != null){
		    	 int strCount = strsCach.size();
		    	 if(LOG.isDebugEnabled()){
		    		 LOG.debug("单条消息日志记录为====="+str);
		    	 }
		    	 if(StringUtil.isChineseChar(str)){
		    		 LOG.info("为中文 过滤掉"+str);
		    		 continue;                          // 判断日志是否有中文，如果有则直接下次读取
		    	 }
		    	 if(strCount == cachSize){
		    		 dealLeakString.add(str);          // 补掉遗漏的str
		    	 }
		    	 
		    	 if(strCount < cachSize){                  // 单数量少于10w的时候，就往临时list添加，不需要考虑多线，因为是局部变量
		    	     strsCach.add(str);  
		    	 }else{
		    		 LOG.info("解析日志list前，list大小为===》"+strsCach.size());
		    		 List<LogMsgBean> logbeans = pareRule.getLogMsgBeans(strsCach);
			    	 msgHandle.handleMsgBeans(logbeans, fileName);
			    	 strsCach.clear();
			    	 logbeans.clear();
			    	 LOG.info("解析日志list后，list大小为===》"+strsCach.size());

		    	 }
             }//end while  有可能第一次文档数量没10w条。只有4w条？
		     
		      //处理最后一次的字符串
	    	 LOG.info("最后一次解析list list大小为===》"+strsCach.size());
	    	 List<LogMsgBean> logbeans = pareRule.getLogMsgBeans(strsCach);
	    	 msgHandle.handleMsgBeans(logbeans, fileName); 
	    	 strsCach.clear();
	    	 logbeans.clear();
	    	 LOG.info("最后一次解析list list大小为===》"+strsCach.size());
	    	  //处理遗漏的字符串
	    	 LOG.info("处理遗漏的list，该list 处理前大小为===》"+dealLeakString.size());
	    	 List<LogMsgBean> logbeanss = pareRule.getLogMsgBeans(dealLeakString);
	    	 msgHandle.handleMsgBeans(logbeanss, fileName); 
	    	 dealLeakString.clear();
	    	 LOG.info("处理遗漏的list，该list 处理完以后大小为===》"+dealLeakString.size());
		     
		     
	    }catch(IOException e){
   	     LOG_ERROR.error("异常消息日志记录为["+str+"]异常信息为===》"+e.toString()+"出现异常文件为======》"+file.getAbsolutePath());
   	     throw new PareFileException("解析文件出现IO异常", e);
   	   
       }
	     catch(MongoSocketReadException e){
	  	    LOG_ERROR.error("异常消息日志记录为["+str+"]异常信息为===》"+e.toString()+"出现异常文件为======》"+file.getAbsolutePath());
		    mongoExceptionCount++;
		    if(mongoExceptionCount > 3){
		    	throw e;                   // 在解析同一个文件中，如果超过3次 则认为该不是由网络引起的异常，应带抛出
		    }
		    
	    }catch(HandleCollectDataException e){   //如果出现将收集到的 bean 插入mongodb或者mysql出现异常
		   LOG_ERROR.error("异常消息日志记录为["+str+"]异常信息为===》"+e.toString()+"出现异常文件为======》"+file.getAbsolutePath());
	       //TODD 目前没有将异常的日志写入文件
	    }catch(Exception e){  // 其他异常
	 	    throw e;
		  
		  }finally{
		 	     IoUtil.freeStream(fReader, bufferedReader);
		}//end finally
		
	}
	
}

