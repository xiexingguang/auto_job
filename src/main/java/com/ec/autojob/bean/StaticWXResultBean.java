/**
 * Project Name:autoJob
 * File Name:StaticWXResultBean.java
 * Package Name:com.ec.autojob.bean
 * Date:2015年7月28日下午1:56:21
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.bean;

import com.ec.autojob.annotaion.CollectFlag;
import com.ec.autojob.annotaion.FiledName;
import com.ec.autojob.annotaion.Table;
import com.ec.autojob.annotaion.TimeFlag;

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
@Table(name="t_ec_wx_static")
public class StaticWXResultBean {
	
	@FiledName(name="f_total")	
	public double totalMsg;
	@FiledName(name="f_ec_msg")	
	public double ecSendMsg;
	@FiledName(name="f_noec_msg")	
	public double noEcSendMsg;
	@FiledName(name="f_ec_usr")	
	public double ecUse;
	@FiledName(name="f_noec_usr")	
	public double noEcUser;
	@FiledName(name="f_ec_crop")	
	public double ecCrop;
	@FiledName(name="f_noec_crop")	
	public double noEcCrop;
	@FiledName(name="f_ec_single")	
	public double single;
	@FiledName(name="f_ec_group")	
	public double group;
	@FiledName(name="f_statictime")
	@TimeFlag
	public double staticTime;
	@FiledName(name="f_static_col")
	@CollectFlag
	public double staticCol;//统计集合名词
	public double getTotalMsg() {
		return totalMsg;
	}
	public void setTotalMsg(double totalMsg) {
		this.totalMsg = totalMsg;
	}
	public double getEcSendMsg() {
		return ecSendMsg;
	}
	public void setEcSendMsg(double ecSendMsg) {
		this.ecSendMsg = ecSendMsg;
	}
	public double getNoEcSendMsg() {
		return noEcSendMsg;
	}
	public void setNoEcSendMsg(double noEcSendMsg) {
		this.noEcSendMsg = noEcSendMsg;
	}
	public double getEcUse() {
		return ecUse;
	}
	public void setEcUse(double ecUse) {
		this.ecUse = ecUse;
	}
	public double getNoEcUser() {
		return noEcUser;
	}
	public void setNoEcUser(double noEcUser) {
		this.noEcUser = noEcUser;
	}
	public double getEcCrop() {
		return ecCrop;
	}
	public void setEcCrop(double ecCrop) {
		this.ecCrop = ecCrop;
	}
	public double getNoEcCrop() {
		return noEcCrop;
	}
	public void setNoEcCrop(double noEcCrop) {
		this.noEcCrop = noEcCrop;
	}
	public double getSingle() {
		return single;
	}
	public void setSingle(double single) {
		this.single = single;
	}
	public double getGroup() {
		return group;
	}
	public void setGroup(double group) {
		this.group = group;
	}
	public double getStaticTime() {
		return staticTime;
	}
	public void setStaticTime(double staticTime) {
		this.staticTime = staticTime;
	}
	public double getStaticCol() {
		return staticCol;
	}
	public void setStaticCol(double staticCol) {
		this.staticCol = staticCol;
	}
	
	
	
	

}

