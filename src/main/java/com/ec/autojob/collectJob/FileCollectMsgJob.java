/**
 * Project Name:autoJob
 * File Name:FileCollectMsgJob.java
 * Package Name:com.ec.autojob.collectJob
 * Date:2015年6月24日下午5:20:55
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.collectJob;

import com.ec.autojob.collectJob.core.AbstractCollectMsgJob;
import com.ec.autojob.collectJob.core.CollectMsgInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * ClassName:FileCollectMsgJob <br/>
 * Function: 数据源为日志文件job类 <br/>
   <br/>
 * Date:     2015年6月24日 下午5:20:55 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="fileCollectMsgJob")
public class FileCollectMsgJob extends AbstractCollectMsgJob{


	@Autowired
	@Qualifier("collectMsgFromFileLog2")
	private CollectMsgInterface collectMsg;
	@Override
	protected CollectMsgInterface getCollectMsg() {
		    return collectMsg;
	}

}

