/**
 * Project Name:autoJob
 * File Name:StaticUserResultBean.java
 * Package Name:com.ec.autojob.bean
 * Date:2015年6月25日下午7:14:34
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.bean;

import com.ec.autojob.annotaion.CollectFlag;
import com.ec.autojob.annotaion.FiledName;
import com.ec.autojob.annotaion.Table;
import com.ec.autojob.annotaion.TimeFlag;

/**
 * ClassName:StaticUserResultBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月25日 下午7:14:34 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Table(name="t_ec_msgusr_static")
public class StaticUserResultBean {
	
	@FiledName(name="usemsg")	
	public double msgTotalUser;
	@FiledName(name="companymsg")	
	public double msgTotalCorp;
	@FiledName(name="avgusemsg")	
	public double avgUser;
	@FiledName(name="avgcompanymsg")	
	public double avgCorp;
	@FiledName(name="ecliteusemsg")	
	public double ecliteUser;
	@FiledName(name="eclitecompany")	
	public double ecliteCorp;
	@FiledName(name="wxuse")	
	public double wxUser;
	@FiledName(name="wxcompany")	
	public double wxCorp;
	@FiledName(name="statictime")
	@TimeFlag
	public double staticTime;
	@FiledName(name="staticCol")
	@CollectFlag
	public double staticCol;//统计集合名词
	 
	 
	 
	 
	public double getStaticCol() {
		return staticCol;
	}
	public void setStaticCol(double staticCol) {
		this.staticCol = staticCol;
	}
	
	
	
	public double getStaticTime() {
		return staticTime;
	}
	public void setStaticTime(double staticTime) {
		this.staticTime = staticTime;
	}
	public double getMsgTotalUser() {
		return msgTotalUser;
	}
	public void setMsgTotalUser(double msgTotalUser) {
		this.msgTotalUser = msgTotalUser;
	}
	public double getMsgTotalCorp() {
		return msgTotalCorp;
	}
	public void setMsgTotalCorp(double msgTotalCorp) {
		this.msgTotalCorp = msgTotalCorp;
	}
	public double getAvgUser() {
		return avgUser;
	}
	public void setAvgUser(double avgUser) {
		this.avgUser = avgUser;
	}
	public double getAvgCorp() {
		return avgCorp;
	}
	public void setAvgCorp(double avgCorp) {
		this.avgCorp = avgCorp;
	}
	public double getEcliteUser() {
		return ecliteUser;
	}
	public void setEcliteUser(double ecliteUser) {
		this.ecliteUser = ecliteUser;
	}
	public double getEcliteCorp() {
		return ecliteCorp;
	}
	public void setEcliteCorp(double ecliteCorp) {
		this.ecliteCorp = ecliteCorp;
	}
	public double getWxUser() {
		return wxUser;
	}
	public void setWxUser(double wxUser) {
		this.wxUser = wxUser;
	}
	public double getWxCorp() {
		return wxCorp;
	}
	public void setWxCorp(double wxCorp) {
		this.wxCorp = wxCorp;
	}

	
	
}

