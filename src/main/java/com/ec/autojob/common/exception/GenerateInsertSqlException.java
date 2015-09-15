/**
 * Project Name:autoJob
 * File Name:GenerateInsertSqlException.java
 * Package Name:com.ec.autojob.common.exception
 * Date:2015年6月29日下午5:58:11
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.common.exception;
/**
 * ClassName:GenerateInsertSqlException <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月29日 下午5:58:11 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class GenerateInsertSqlException  extends RuntimeException{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
   
	public GenerateInsertSqlException(String msg,Throwable t){
		super(msg, t);
	}
	
}

