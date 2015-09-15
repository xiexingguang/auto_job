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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ec.autojob.bean.LogMsgBean;
import com.ec.autojob.collectJob.core.CollectMsgExceptionHandler;
import com.ec.autojob.collectJob.core.PareDataRule;
import com.ec.autojob.common.exception.ConvertString2beanException;
import com.ec.autojob.db.DBContextHolder;
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
@Component(value = "pareString2MsgBean3")
public  class PareString2MsgBean3  implements PareDataRule{
    
	private  String  default_fixString = "\\|\\|";
//	private static  final Logger   LOG = LogManager.getLogger("collectJobLog");
	
	private static  final Logger   LOG_ERROR = LogManager.getLogger("errorLog");
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	@Autowired
	@Qualifier("convertString2beanExHandler")
	private CollectMsgExceptionHandler exceptionHandler;
	
	/**
	 * 根据用户id查询企业id缓存
	 */
	private static Map<String, String> cropIdCache = new HashMap<String, String>();
	
	
	
	
	@Override
	public <T> T pareString2Object(String pareDate,Class<T> t) {
		
	   try{	 
			 String regex = getSeparator();
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
		
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_2); // 设置数据源
		//LOG.info("基础库的数据源：JdbcTemplate为====》"+JdbcTemplate);
		// 首先判断缓存里是否有
		if(cropIdCache.containsKey(id)){
			return cropIdCache.get(id);
		}
		int f_id = Integer.parseInt(id);
		int group_id = -1;
			try{
		 	group_id = JdbcTemplate.queryForInt("SELECT f_group_id FROM d_ec_user.t_user_group WHERE f_id = "+f_id+" and f_type=2");
		 	cropIdCache.put(id, group_id+""); // 如果查询到新的，则入缓存
			}catch(Exception e){
			// LOG_ERROR.error(e);
			 group_id = -1;
		}
		return group_id+"";
	}
	
	
	 //分隔符，子类可以继承该类，设定自己的日志分隔方式，目前默认为|| 方式分隔
	 public  String  getSeparator(){
		 return default_fixString;
	 }

	@Override
	public <T> List<T> pareStrings2Objects(List<String> pareDates, Class<T>  t) {
	    List<T> lists = new ArrayList<T>();
		for(int i = 0 ; i < pareDates.size() ; i++){
			String pareDate = pareDates.get(i);
		  try{	
			T  tt =  pareString2Object(pareDate, t);
			lists.add(tt);
		  }catch(Exception e){
			LOG_ERROR.error("收集日志数据将字符转成bean出现异常，解析 该条信息出现异常【"+pareDate+"】异常为"+e.toString());
		  }
		}
		return lists;
	}
	
	
	@Override
	public LogMsgBean getLogMsgBean(String string) {
		
		 try{ 
			  
		     LogMsgBean bean = new LogMsgBean(); 
			  //因为c++和wx传来的日志文件不一样，所以要分别对待
			 
			  String tmp = string ;
			  if(string.indexOf("||") == 20){  //如果是c++过来的日志
			  tmp = string.substring(0, 19) +string.substring(20);
			  }
			  String[] arrays =tmp.trim().split(default_fixString);
			  
			  // 为适用微信需求，因为在c++端，传过来的为f_id，要通过用户id去查询企业id在插入报文，在微信中，它直接传来的就为企业id，不需要替换
			  
			  //1.首先判断是微信统计还是消息统计，通过判断报文最后的字段值取判断，因为我们的微信统计业务规则为最后为是否ec企业，值只能为1100或者1200
			  
			  boolean isWXStatic = arrays[6].equals("1100") || arrays[6].equals("1200");
			  if(!isWXStatic){
			   arrays[4] = replaceCorpId(arrays[1]); //如果不是微信过来的统计需求，则将-- 替换查询出来的企业id
            } else if (arrays.length > 7) {
			      bean.setSendType( arrays[7]);
			  }
			  
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

	@Override
	public List<LogMsgBean> getLogMsgBeans(List<String> strings) {
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
			      exceptionHandler.handlerException(sss); // write the errordata to file
				 }
			  }
		  return beans;
	}
	

	public static void main(String[] args) {
		Map<String, String> cropIdCache = new HashMap<String, String>();
		cropIdCache.put("1", "xxg");
		System.out.println(cropIdCache.containsKey("1"));
		
		String yyyy = "2015-07-28 15:27:04 ||857713||857759||1||--||3||1";
		
		                                       String ssss = "2015-07-28 20:30:16||970696||938773||3||21299||1300||1100";
		//String tmp = yyyy.substring(0, 19) +yyyy.substring(20);
	//	System.out.println(ssss.trim());
	//	System.out.println(yyyy.trim());
		String  default_fixString = "\\|\\|";
		System.out.println(yyyy.indexOf(default_fixString));
		System.out.println(ssss.indexOf("||"));
	//	System.out.println(yyyy.length());
	}

}

