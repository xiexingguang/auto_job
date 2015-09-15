/**
 * Project Name:autoJob
 * File Name:TimeFlag.java
 * Package Name:com.ec.autojob.annotaion
 * Date:2015年6月29日下午3:11:17
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:TimeFlag <br/>
 * Function: 标记注解，标记该字段为一个时间类型字段 <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月29日 下午3:11:17 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeFlag {
     
}
