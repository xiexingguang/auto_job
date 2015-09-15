/**
 * Project Name:autoJob
 * File Name:ConvertString2beanExceptionHandler.java
 * Package Name:com.ec.autojob.collectJob
 * Date:2015年6月24日下午7:23:54
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.collectJob;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ec.autojob.collectJob.core.CollectMsgExceptionHandler;
import com.ec.autojob.common.MsgConstants;
import com.ec.autojob.properties.ConfigProperties;
import com.ec.autojob.util.LogUtil;
import com.ec.autojob.util.StringUtil;

/**
 * ClassName:ConvertString2beanExceptionHandler <br/>
 * Function: 
 * 某一条消息解析出现异常，处理类。
 * . <br/>
 * Date:     2015年6月24日 下午7:23:54 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="convertString2beanExHandler")
public class ConvertString2beanExceptionHandler implements CollectMsgExceptionHandler {
	
	private static final Logger LOG = LogManager.getLogger("collectJobLog");
	private static final Logger LOG_ERROR = LogManager.getLogger("errorLog");
	
	@Autowired
	private ConfigProperties configProperties;
    /**
     * 
     * 当解析日志文件，将日志文件消息记录转成bean出现异常时，异常处理策略
     * 将异常消息记录写入文件
     * @see com.ec.autojob.collectJob.core.CollectMsgExceptionHandler#handlerException()
     */
	@Override
	public void handlerException(Object o) {
	     	String str = "";
		
	try{	
			if(o instanceof String){
				str = (String)o;
			}
		    String  errorDir = configProperties.ERROR_MSG_LOG_DIR + File.separator + MsgConstants.ERROR_LOG_NAME_PROFIX+LogUtil.generatePareLogFileDir();
		    String  fileName =MsgConstants.ERROR_LOG_NAME_PROFIX+LogUtil.generatePareLogFileDir();
		    String  filePath = errorDir + File.separator+fileName;
		    LOG.info("错误文件目录为===》"+errorDir +"=======>完整文件路径为===》"+filePath);
			StringUtil.writeString2File(filePath, str);   //将错误写入日志文件
	}catch(Exception e){
		   LOG_ERROR.error(e);  //如果写错误日志异常,直接记错误日志。不管。对业务影响是很大
	}
	}
}
