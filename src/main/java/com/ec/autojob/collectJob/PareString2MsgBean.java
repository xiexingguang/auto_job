/**
 * Project Name:autoJob
 * File Name:PareString2StirngArray.java
 * Package Name:com.ec.autojob.collectJob
 * Date:2015年6月24日上午10:42:48
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.collectJob;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ec.autojob.bean.LogMsgBean;
import com.ec.autojob.collectJob.core.PareDataRule;
import com.ec.autojob.common.exception.ConvertString2beanException;
import com.ec.autojob.util.StringUtil;

/**
 * ClassName:PareString2StirngArray <br/>
 * Function:将单条消息记录转换成javabean对象。  <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月24日 上午10:42:48 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="pareString2MsgBean")
public  class PareString2MsgBean  implements PareDataRule{
    
	private  String  default_fixString = "\\|\\|";
	//private static  final Logger   LOG = LogManager.getLogger("collectJobLog");
	
	private static  final Logger   LOG_ERROR = LogManager.getLogger("errorLog");
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	@Override
	public <T> T pareString2Object(String pareDate,Class<T> t) {
		
	try{	 
		     
			 String regex = getSeparator();
			 //2014-05-15 14:24:31||2||764536||2||13412345432||13098765432||1||2
			 String[] arraysStrings = pareDate.split(regex); 
			 arraysStrings[4] = replaceCorpId(arraysStrings[1]); // 通过用户id获取企业id，如果其中出现异常则默认企业id为-1 ,
			 T  msgBean = StringUtil.convertStringArray2Object(arraysStrings,t); 
			 return msgBean;
		}catch(Exception e){
			 LOG_ERROR.error("错误的日志信息为===》"+ pareDate);
			 throw new ConvertString2beanException("转换bean出现异常",e);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private  String replaceCorpId(String id){
		int f_id = Integer.parseInt(id);
		int group_id = -1;
			try{
		 	group_id = JdbcTemplate.queryForInt("SELECT f_group_id FROM d_ec_user.t_user_group WHERE f_id = "+f_id+" and f_type=2");
			}catch(Exception e){
			// LOG_ERROR.error("根据用户id查询企业id出现异常，异常信息为:"+e.toString());
			 group_id = -1;
		}
		return group_id+"";
	}
	
	
	 //分隔符，子类可以继承该类，设定自己的日志分隔方式，目前默认为|| 方式分隔
	 public  String  getSeparator(){
		 return default_fixString;
	 }

	@Override
	public <T> List<T> pareStrings2Objects(List<String> pareDates, Class<T> t) {
	    List<T> lists = new ArrayList<T>();
		for(int i = 0 ; i < pareDates.size() ; i++){
			String pareDate = pareDates.get(i);
		  try{	
			T  tt =  pareString2Object(pareDate, t);
			lists.add(tt);
		  }catch(Exception e){
		//	LOG_ERROR.error("收集日志数据将字符转成bean出现异常，解析 该条信息出现异常【"+pareDate+"】异常为"+e.toString());
		  }
		}
		return lists;
	}
	
	
	
	  public  LogMsgBean getLogMsgBean(String string){
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
		  
		  
		  public  List<LogMsgBean> getLogMsgBeans(List<String> strings){
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
				  strings.clear();
			  return beans;
		  }
		  
}

