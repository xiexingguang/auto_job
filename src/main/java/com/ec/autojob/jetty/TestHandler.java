/**
 * Project Name:autoJob
 * File Name:TestHandler.java
 * Package Name:com.ec.autojob.netty
 * Date:2015年7月29日下午5:34:24
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.jetty;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * ClassName:TestHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月29日 下午5:34:24 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TestHandler extends AbstractHandler{
	 ExecutorService executor = Executors.newFixedThreadPool(5);
	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		   String json = request.getParameter("json");
		  // 初始化线程池
		   if(json.equals("xxg")){
			    executor.execute(new Runnable() {
					
					@Override
					public void run() {
						
					   wasteTime();
					}
				});
		   }
			//System.out.println(command);
		//	response.setContentType("text/html;charset=utf-8");  
	      response.setStatus(HttpServletResponse.SC_OK);  
	      baseRequest.setHandled(true);  
	        
	     /*   try {
			//	Thread.sleep(20000);
			} catch (InterruptedException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}*/
	        response.getWriter().println("<h1>Hello World</h1>");  
		
	}
  
	private void wasteTime(){
		   try {
			   System.out.println("executing wasteTime...");
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		   System.out.println("executing wasteTime...is  end./...");
	}
	
}

