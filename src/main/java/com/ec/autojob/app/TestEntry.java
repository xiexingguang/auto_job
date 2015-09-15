/**
 * Project Name:autoJob
 * File Name:TestEntry.java
 * Package Name:com.ec.autojob.app
 * Date:2015年6月30日下午7:20:55
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
 *

*//*




package com.ec.autojob.app;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ec.autojob.collectJob.FileCollectMsgJob;
import com.ec.autojob.staticJob.StaticMsgCosumerJob;
import com.ec.autojob.staticJob.StaticMsgSumJob;
import com.ec.autojob.staticJob.StaticMsgUserJob;
import com.ec.autojob.staticJob.StaticMsgWxJob;

*//**
 * ClassName:TestEntry <br/>
 * Function: TODO ADD FUNCTION. <br/>
<<<<<<< .mine
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月30日 下午7:20:55 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 *//*

@Component
public class TestEntry implements Job{

	@Autowired
	@Qualifier("fileCollectMsgJob")
	private FileCollectMsgJob job;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private StaticMsgCosumerJob staticCusJob;
	
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private StaticMsgSumJob staticSumJob;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private StaticMsgWxJob  wxJob;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private StaticMsgUserJob userJob;
	private boolean flag = false;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		// TODO Auto-generated method stub
		
	}
	
	@Scheduled(cron = "0/5 * * * * *") 
	public void process() throws JobExecutionException{
		System.out.println("begin test....");
	    if(!flag){
	    	//staticSumJob.execute(null);
	    	//staticCusJob.execute(null);
	    	//userJob.execute(null);

	     wxJob.execute(null);
	    	// job.execute(null);

            wxJob.execute(null);
            // job.execute(null);

	    	flag = true;
	    }	
		
	}
	
}

*/