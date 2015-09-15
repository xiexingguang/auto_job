/**
 * Project Name:autoJob
 * File Name:TestServer.java
 * Package Name:com.ec.autojob
 * Date:2015年8月20日上午11:51:02
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob;

import org.junit.Test;

import com.ec.autojob.basedaoTest.BaseDaoTest;
import com.lmax.disruptor.SleepingWaitStrategy;

/**
 * ClassName:TestServer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年8月20日 上午11:51:02 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TestServer extends BaseDaoTest{
	
	@Test
	public void test() throws InterruptedException{
		while(true){
			
			Thread.sleep(100000);
			
		}
	}

}

