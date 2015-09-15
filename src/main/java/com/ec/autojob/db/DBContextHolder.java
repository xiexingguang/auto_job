/**
 * Project Name:autoJob
 * File Name:DBContextHolder.java
 * Package Name:com.ec.autojob.db
 * Date:2015年7月6日下午5:27:38
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.db;
/**
 * ClassName:DBContextHolder <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月6日 下午5:27:38 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class DBContextHolder {
	
	    public static final String DATA_SOURCE = "dataSource";  
	    public static final String DATA_SOURCE_2 = "dataSource2";  
	      
	    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
	      
	    public static void setDBType(String dbType) {  
	        contextHolder.set(dbType);  
	    }  
	      
	    public static String getDBType() {  
	        return contextHolder.get();  
	    }  
	      
	    public static void clearDBType() {  
	        contextHolder.remove();  
	    }  
}

