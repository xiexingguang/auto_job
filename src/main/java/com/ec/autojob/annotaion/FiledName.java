/**
 * Project Name:autoJob
 * File Name:FiledName.java
 * Package Name:com.ec.autojob.annotaion
 * Date:2015年6月29日下午2:44:03
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:FiledName <br/>
 * Function: 字段名映射对应数据库中真实的
 * 字段名称 <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月29日 下午2:44:03 <br/>
 * @author  xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FiledName {
	public abstract String name();
}

