/**
 * Project Name:autoJob
 * File Name:AbstractStaticMsgJob.java
 * Package Name:com.ec.autojob.staticJob.core
 * Date:2015年6月25日下午3:58:06
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.staticJob.core;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.ec.autojob.common.MsgConstants;
import com.ec.autojob.service.StaticMsgService;
import com.ec.autojob.staticJob.listener.StaticMsgListener;
import com.ec.autojob.util.LogUtil;

/**
 * ClassName:AbstractStaticMsgJob <br/>
 * Function: 
 * T 为统计后的数据bean实体
 * 统计抽象job类，该抽象类维护了监听器列表，监听器负责
 * 在统计数据时之前之后添加一些业务处理，面向切面思想编程<br/>
 * Date:     2015年6月25日 下午3:58:06 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component
public abstract class AbstractStaticMsgJob<T> implements Job{
	
	/**
	 * 统计job监听器，内部维护一个列表，设计为静态的，所有统计收集业务共享该监听器
	 * 列表，目前只有一个默认的监听器，3个统计在执行做相同的业务操作
	 */
	protected static List<StaticMsgListener> listeners = new ArrayList<StaticMsgListener>();
	
	private static final Logger LOG = LogManager.getLogger("appLog");
	
	@Autowired
	@Qualifier("defautStaticMsgListener")
	private StaticMsgListener staticListener;  // 单例
	
	@PostConstruct
	private  void init(){
	 if(!listeners.contains(staticListener))	
		  listeners.add(staticListener);  // 如果不加 有3个子类，则会往这个listener添加3次，因为子类实例化的时候会先实例化父类
		
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		      
		      long beginTime = System.currentTimeMillis();
			  for(StaticMsgListener listener : listeners){
				     listener.before();                  //执行数据统计操作 做一些业务处理
			  }
		      String collectionName = MsgConstants.LOG_COLLECTION_NAME_PRFIX + LogUtil.generateCollectionPattern();
			  T t = (T)handleStaticTask(collectionName); //子类实现
			   LOG.info("统计结束执行,执行消耗时间为["+(System.currentTimeMillis()-beginTime)/1000+"]秒");
			
		      for(StaticMsgListener listener : listeners){
			        listener.after(t);                   //执行数据计算结果后对
		      }
	}
	/** 
	 * 
	 * getStaticMsgService:(统计job需要的service). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author xxg
	 * @return
	 * @since JDK 1.7
	 */
	protected abstract StaticMsgService getStaticMsgService(); //辅助service，要调用mongdb或者mysql进行后期数据处理
	
	/**
	 * 
	 * handleStaticTask://处理统计结果关键方法，所有业务统计逻辑在这个方法里执行. <br/>
	 * @author xxg
	 * @return
	 * @since JDK 1.7
	 */
	protected abstract T handleStaticTask(String  collectonName); 
}

