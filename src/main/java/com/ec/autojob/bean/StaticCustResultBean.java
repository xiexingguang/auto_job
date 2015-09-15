/**
 * Project Name:autoJob
 * File Name:StaticCustResultBean.java
 * Package Name:com.ec.autojob.bean
 * Date:2015年6月25日下午7:14:53
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.bean;
import com.ec.autojob.annotaion.CollectFlag;
import com.ec.autojob.annotaion.FiledName;
import com.ec.autojob.annotaion.Table;
import com.ec.autojob.annotaion.TimeFlag;
/**
 * ClassName:StaticCustResultBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月25日 下午7:14:53 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Table(name="t_ec_msgcusm_static")
public class StaticCustResultBean {
	
	 @FiledName(name="custormusenum")
	 public  double msgKfTotalUser;
	 @FiledName(name="custormcompanynum")
	 public  double msgKfTotalCorp;
	 @FiledName(name="visitnum")
	 public  double msgFk;
	 @FiledName(name="avgcustormnum")
	 public  double avgKfCorp;
	 @FiledName(name="eclitecustormnum")
	 public  double ecliteKfUser;
	 @FiledName(name="eclitecompanynum")
	 public  double ecliteKfcorp;
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
	public double getMsgKfTotalUser() {
		return msgKfTotalUser;
	}
	public void setMsgKfTotalUser(double msgKfTotalUser) {
		this.msgKfTotalUser = msgKfTotalUser;
	}
	public double getMsgKfTotalCorp() {
		return msgKfTotalCorp;
	}
	public void setMsgKfTotalCorp(double msgKfTotalCorp) {
		this.msgKfTotalCorp = msgKfTotalCorp;
	}
	public double getMsgFk() {
		return msgFk;
	}
	public void setMsgFk(double msgFk) {
		this.msgFk = msgFk;
	}
	public double getAvgKfCorp() {
		return avgKfCorp;
	}
	public void setAvgKfCorp(double avgKfCorp) {
		this.avgKfCorp = avgKfCorp;
	}
	public double getEcliteKfUser() {
		return ecliteKfUser;
	}
	public void setEcliteKfUser(double ecliteKfUser) {
		this.ecliteKfUser = ecliteKfUser;
	}
	public double getEcliteKfcorp() {
		return ecliteKfcorp;
	}
	public void setEcliteKfcorp(double ecliteKfcorp) {
		this.ecliteKfcorp = ecliteKfcorp;
	}
	 
	
	
}

