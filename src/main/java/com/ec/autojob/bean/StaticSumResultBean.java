/**
 * Project Name:autoJob
 * File Name:StaticSumResultBean.java
 * Package Name:com.ec.autojob.bean
 * Date:2015年6月25日下午5:01:44
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.bean;

import com.ec.autojob.annotaion.CollectFlag;
import com.ec.autojob.annotaion.FiledName;
import com.ec.autojob.annotaion.Table;
import com.ec.autojob.annotaion.TimeFlag;

/**
 * ClassName:StaticSumResultBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月25日 下午5:01:44 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Table(name="t_ec_msgnum_static")
public class StaticSumResultBean {
  @FiledName(name="pceim")	
  public double  pcEIM;
  @FiledName(name="pccustomer")	
  public double  pcKF       ;
  @FiledName(name="ioseim")	
  public double  iosEIM     ;
  @FiledName(name="ioscustomer")	
  public double  iosKF      ;
  @FiledName(name="andorideim")	
  public double  androidEIM ;
  @FiledName(name="andoridcustomer")	
  public double  androidKF  ;
  @FiledName(name="wx")	
  public double  wx         ;
  @FiledName(name="visitor")	
  public double  fk         ;
  @FiledName(name="total")	
  public double totalMsg    ;
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
	public double getTotalMsg() {
	return totalMsg;
}
public void setTotalMsg(double totalMsg) {
	this.totalMsg = totalMsg;
}
	public double getPcEIM() {
		return pcEIM;
	}
	public void setPcEIM(double pcEIM) {
		this.pcEIM = pcEIM;
	}
	public double getPcKF() {
		return pcKF;
	}
	public void setPcKF(double pcKF) {
		this.pcKF = pcKF;
	}
	public double getIosEIM() {
		return iosEIM;
	}
	public void setIosEIM(double iosEIM) {
		this.iosEIM = iosEIM;
	}
	public double getIosKF() {
		return iosKF;
	}
	public void setIosKF(double iosKF) {
		this.iosKF = iosKF;
	}
	public double getAndroidEIM() {
		return androidEIM;
	}
	public void setAndroidEIM(double androidEIM) {
		this.androidEIM = androidEIM;
	}
	public double getAndroidKF() {
		return androidKF;
	}
	public void setAndroidKF(double androidKF) {
		this.androidKF = androidKF;
	}
	public double getWx() {
		return wx;
	}
	public void setWx(double wx) {
		this.wx = wx;
	}
	public double getFk() {
		return fk;
	}
	public void setFk(double fk) {
		this.fk = fk;
	}
	   
	public String toString(){
		return  pcEIM+"";
		
	}
	   
 

}

