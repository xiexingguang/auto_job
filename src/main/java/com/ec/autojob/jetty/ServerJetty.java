/**
 * Project Name:autoJob
 * File Name:ServerJetty.java
 * Package Name:com.ec.autojob.netty
 * Date:2015年7月29日下午3:36:47
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.jetty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * ClassName:ServerJetty <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月29日 下午3:36:47 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="serverJetty")
public class ServerJetty {
	 
	private static final Logger LOG = LogManager.getLogger("appLog");
	private static final Logger LOG_ERROR = LogManager.getLogger("errorLog");
	@Autowired
	@Qualifier("autoJobHandler")
	private AbstractHandler hander;
	/*  public static void main(String[] args) throws Exception {
		  Server sr = new Server(8080);
		//  sr.setHandler(new TestHandler());
		  
		  sr.start();
		
	  }
	  */
	  public void startJetty(){
		  LOG.info("the server of jetty is starting.....");
		  Server sr = new Server(9999);
		  sr.setHandler(hander);
		  try {
		   sr.start();
		 } catch (Exception e) {
			 LOG_ERROR.error(e);
			
		}
	  }

}

