package com.ec.autojob.collectJob.core;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
/**
 * ClassName:CollectMsgJob <br/>
 * Function: 收集消息job类
 * 抽象job类，该execute是一个模板方法
 * 子类继承它，实现自己的消息收集，
 * 目前项目中我们使用的收集数据源为文件 
 *
 * <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月24日 上午11:32:27 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component
public abstract class AbstractCollectMsgJob implements Job {
	
	private static final Logger  LOG = LogManager.getLogger("appLog");
	//private static final Logger  LOG_ERROR = LogManager.getLogger("errorLog");
  
	protected abstract CollectMsgInterface  getCollectMsg();
    
    
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void execute(JobExecutionContext arg0){
		   long  beginTime = System.currentTimeMillis();
	       LOG.info(">>>>>>>>>>>>>>>收集消息日志数据job开始执行>>>>>执行时间为>>>>>"+sdf.format(new Date()));
		   getCollectMsg().collectMsg(); //收集数据
	       LOG.info(">>>>>>>>>>>>>>>收集消息日志数据job结束话费时间为===》"+(System.currentTimeMillis()-beginTime));
		  
	}
   
}

