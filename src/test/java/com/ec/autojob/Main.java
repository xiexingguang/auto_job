/**
 * Project Name:autoJob
 * File Name:Main.java
 * Package Name:com.ec.autojob
 * Date:2015年6月30日上午9:54:37
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob;

import java.util.Date;

import com.ec.autojob.bean.StaticCustResultBean;
import com.ec.autojob.staticJob.StaticMsgSumJob;

/**
 * ClassName:Main <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月30日 上午9:54:37 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class Main {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		/*StaticMsgSumJob ss = (StaticMsgSumJob<StaticCustResultBean>)Class.forName("com.ec.autojob.staticJob.StaticMsgSumJob").newInstance();
    	System.out.println(ss.getClass());*/
		
		Date d = new Date();
		
		d.setHours(02);
		d.setMinutes(22);
		System.out.println(d.toLocaleString());
	}

}

