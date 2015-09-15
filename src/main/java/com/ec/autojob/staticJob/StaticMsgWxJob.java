/**
 * Project Name:autoJob
 * File Name:StaticMsgByDayJob.java
 * Package Name:com.ec.autojob.staticJob
 * Date:2015年6月25日下午3:10:07
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.staticJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.ec.autojob.bean.StaticWXResultBean;
import com.ec.autojob.service.StaticMsgService;
import com.ec.autojob.staticJob.core.AbstractStaticMsgJob;


/**
 * ClassName:StaticMsgByDayJob <br/>
 * Function: 业务要求，按天统计日志信息 <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月25日 下午3:10:07 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="staticMsgWxJob")
public class StaticMsgWxJob<T> extends AbstractStaticMsgJob<T> {
	
	
	@Autowired(required=true)
	@Qualifier("staticMsgWxServiceImpl")
	public StaticMsgService staticService;
	
	private static final Logger LOG = LogManager.getLogger("staticWxJobLog");
	/**
	 * 
	 * getStaticMsgService: 目前该service 是通过mongodb 统计数据结果
	 * 可以重写改用其他方式，比如用mysql 数据库 统计. <br/>
	 * @author xxg
	 * @return
	 * @since JDK 1.7
	 */
	protected  StaticMsgService getStaticMsgService(){
		  return staticService;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected T handleStaticTask(String collectionName) {
	
		LOG.info("统计微信消息总数job执行中，统计的集合为===》"+collectionName);
		StaticWXResultBean t =  getStaticMsgService().handlerStaticData(collectionName, new StaticWXResultBean());
		return (T)t;
	}
                      
}
