/**
 * Project Name:autoJob
 * File Name:HandleCollectDataException.java
 * Package Name:com.ec.autojob.common.exception
 * Date:2015年7月1日上午9:38:41
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.common.exception;

import org.springframework.stereotype.Component;

/**
 * ClassName:HandleCollectDataException <br/>
 * Function: 数据收集结果处理异常<br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月1日 上午9:38:41 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="handleCollectDataException")
public class HandleCollectDataException extends RuntimeException {
	
	
	public HandleCollectDataException(){
		 super();
	}

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	
	public HandleCollectDataException(String msg , Throwable t){
		 super(msg, t);
	}

}

