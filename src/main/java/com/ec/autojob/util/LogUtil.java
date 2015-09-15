/**
 * Project Name:autoJob
 * File Name:LogUtil.java
 * Package Name:com.ec.autojob.util
 * Date:2015年6月24日下午8:15:37
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ec.autojob.bean.LogMsgBean;
import com.ec.autojob.common.MsgConstants;
import com.ec.autojob.common.exception.ConvertString2beanException;


/**
 * ClassName:LogUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月24日 下午8:15:37 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class LogUtil {
	
	
	 private static final Logger LOG_ERROR = LogManager.getLogger("errorLog");
      
	 /**
	  * 
	  * //根据当天的日期算出前一天的日期，因为我们自动任务跑的都是跑前一天的文件，
	 // 前一天的消息日志我们放在20150629 <br/>
       
	  * @author xxg 
	  * @return  如果当前日期是20150630，则返回20150629 
	  * @since JDK 1.7
	  */
	  public static String  generatePareLogFileDir(){
		  Date date = new Date();
		  Date  dBefore = new Date();
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_MONTH, -1);
		  dBefore = calendar.getTime();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		  String defaultStartDate = sdf.format(dBefore);    //格式化前一天
          return defaultStartDate;
	  }
	  
	  /**
	   * 
	   * @return 返回的当前时间的前一天世界返回格式为20150626
	   */
	  
	  public static String  generateCollectionPattern(){
		  Date date = new Date();
		  Date  dBefore = new Date();
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_MONTH, -1);
		  dBefore = calendar.getTime();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		  String defaultStartDate = sdf.format(dBefore);    //格式化前一天
          return defaultStartDate;
	  }
	  
	  /**
	   * 
	   * @return 返回mongodb中集合的命名 cmsg20150626
	   */
	  public static String generatColectionNameByDay(){
		  return MsgConstants.LOG_COLLECTION_NAME_PRFIX + generateCollectionPattern();
	  }
	  
	   
	  /**
	   * 
	   * getLogMsgBean:(这里用一句话描述这个方法的作用). <br/>
	   * TODO(这里描述这个方法适用条件 – 可选).<br/>
	   * @author xxg
	   * @param string
	   * @return
	   * @since JDK 1.7
	   */
	  public static LogMsgBean getLogMsgBean(String string){
		 try{ 
			
		  String tmp = "";
		  tmp = string.substring(0, 19) +string.substring(20);
		  String[] arrays =tmp.trim().split("\\|\\|");
		  LogMsgBean bean = new LogMsgBean();
		  bean.setSedtime(arrays[0]);
		  bean.setSender(arrays[1]);
		  bean.setRecieve(arrays[2]);
		  bean.setMsgtype(arrays[3]);
		  bean.setCorpid(arrays[4]);
		  bean.setZdtype(arrays[5]);
		  bean.setModule(arrays[6]);
		  return bean;
		 }catch(Exception e){
		   throw new ConvertString2beanException("转换bean出现异常，异常为==》"+e.toString());
		 }
	  }
	  
	  
	  public static List<LogMsgBean> getLogMsgBeans(List<String> strings){
		  if(strings == null){
			  throw new NullPointerException("the insert stirngs is null");
		  }
		  List<LogMsgBean> beans = new ArrayList<LogMsgBean>();
			  for(int i = 0 ; i < strings.size() ; i++){
				 String sss = strings.get(i);
				 try{
				  LogMsgBean bean = getLogMsgBean(sss);
				  beans.add(bean);
				 }catch(Exception e){
			      LOG_ERROR.error(e);
				  StringUtil.writeString2File("d:\\xxg\\erro.txt", sss);
				 }
			  }
		  return beans;
	  }
	  
	  public static void main(String[] args) {
		//System.out.println(generatePareLogFileDir());
		  String ss = "2015-07-06 00:09:46 ||962092233||944431||1||--||3||3";
		 System.out.println(ss.substring(0, 19));
		 System.out.println(ss.substring(20));
		System.out.println(generatColectionNameByDay());
	}
	
	
	  
}

