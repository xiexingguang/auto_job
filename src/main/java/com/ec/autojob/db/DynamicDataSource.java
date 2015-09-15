/**
 * Project Name:autoJob
 * File Name:DynamicDataSource.java
 * Package Name:com.ec.autojob.common
 * Date:2015年7月6日下午5:26:32
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

/**
 * ClassName:DynamicDataSource <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月6日 下午5:26:32 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="dynamicDataSource")
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		
		 return DBContextHolder.getDBType();  
	}

}

