/**
 * Project Name:autoJob
 * File Name:StaticWXResultBean.java
 * Package Name:com.ec.autojob.bean
 * Date:2015年7月28日下午1:56:21
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.bean;

/**
 * ClassName:StaticWXResultBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月28日 下午1:56:21 <br/>
 * @author   xxg
 * @version  1.1
 * @since    JDK 1.7
 * @see 	 
 */

public class StaticWXBean {
   
    public int id;
    
    public int corpId;
 
    public int ec2wxmsgCount;

    public int wx2ecmsgCount;
   
	public long totalMsg;
    
    public String usedTime;

	public long getTotalMsg() {
		return totalMsg;
	}


	public void setTotalMsg(long totalMsg) {
		this.totalMsg = totalMsg;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCorpId() {
		return corpId;
	}


	public void setCorpId(int corpId) {
		this.corpId = corpId;
	}





	public int getEc2wxmsgCount() {
		return ec2wxmsgCount;
	}


	public void setEc2wxmsgCount(int ec2wxmsgCount) {
		this.ec2wxmsgCount = ec2wxmsgCount;
	}


	public int getWx2ecmsgCount() {
		return wx2ecmsgCount;
	}


	public void setWx2ecmsgCount(int wx2ecmsgCount) {
		this.wx2ecmsgCount = wx2ecmsgCount;
	}


	public String getUsedTime() {
		return usedTime;
	}


	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}
 
    

 
}

