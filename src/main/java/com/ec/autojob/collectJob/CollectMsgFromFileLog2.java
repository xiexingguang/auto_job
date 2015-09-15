/**
 * Project Name:autoJob
 * File Name:CollectMsgFromFile.java
 * Package Name:com.ec.autojob.collectJob
 * Date:2015年6月24日上午11:54:10
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.collectJob;
import java.io.File;
import java.io.FilenameFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ec.autojob.collectJob.core.CollectMsgInterface;
import com.ec.autojob.common.MsgConstants;
import com.ec.autojob.properties.ConfigProperties;
import com.ec.autojob.service.CollectMsgFileLogService;
import com.ec.autojob.util.LogUtil;

/**
 * ClassName:CollectMsgFromFile2 <br/>
 * Function: 从文件中解析日志. 
 * 数据来源从文件中获得，
 * CollectMsgFromFileLog 的增强版，
 * 增强了 对多个多级目录的遍历，这样设计，防止版本不稳定可以马上
 * 替换之前的fileLog
 * <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月24日 上午11:54:10 <br/>
 * @author  xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="collectMsgFromFileLog2")
public  class  CollectMsgFromFileLog2  implements  CollectMsgInterface {
	
	
	private static final Logger  LOG = LogManager.getLogger("collectJobLog");
   // private static final String  LOG_FILE_DIR= ConfigProperties.MSG_LOG_DIR; //消息目录
	private static final Logger  ERROR_LOG = LogManager.getLogger("errorLog");
    @Autowired
    private ConfigProperties configProperties;
    @Autowired(required=true)
    @Qualifier("collectMsgServiceImpl23")
    public  CollectMsgFileLogService  msgService;
    
    /**
     * 
     * TODO  收集日志业务逻辑处理类
     * （可选）.
     * @see com.ec.autojob.collectJob.core.CollectMsgInterface#collectMsg()
     */
	@Override
	public void collectMsg() {
		    // 生成搜索文件名称,模糊搜索匹配昨天的所有日志
		     final  String dir = configProperties.MSG_LOG_DIR +File.separator ; //搜索日志消息所在目录
		     final  String pattern = MsgConstants.LOG_NAME_PROFIX+LogUtil.generatePareLogFileDir(); //匹配条件
		     File f = new File(dir);
		     
		     if(!f.exists()){
		    	 throw new NullPointerException(dir+"目录不存在...");
		     }
		      
		     File[] fs = f.listFiles(); //遍历f下的二级目录
				for(File ff:fs){
					if(!ff.getName().equals("imerrormsg") && ff.isDirectory()){
						 LOG.info("搜索的目录为====>>>>"+ff.getName());
						 File[] files = ff.listFiles(new FilenameFilter() {
								@Override
								public boolean accept(File dir, String name) {
								    if(name.startsWith(pattern))
									return true;
								    else  return false;
								} 
							});
						 
						  //  如果目录下没有有文件
						  if(files.length == 0){
						 	  ERROR_LOG.error("目录为"+ff.getName()+"下的没有当天的日志文件，请检查相应目录文件");
						  }
						  
						  for(File fff  : files){
						      LOG.info("当天要收集的日志路径为=======》"+fff.getAbsolutePath());
						      getMsgService().collectMsgFromFile(fff); //不处理file为null异常，其他异常处理掉了
						  }
						  
					 }//end if
					
				}//end for
	}
	
	/**
	 * 
	 * getMsgService:这个service 是专门解析数据源为file的，
	 * 
	 *  如果要实现不同逻辑可以覆盖这个方法，实现自己的
	 *  处理规则。
	 *  <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author xxg
	 * @return
	 * @since JDK 1.7
	 */
	protected  CollectMsgFileLogService getMsgService(){
		return msgService;
	}
		  
	public static void main(String[] args) {
		File f = new File("E:\\home\\scribe\\log\\primary");
		File[] fs = f.listFiles(); //每个file是个目录
		for(File ff:fs){
			if(!ff.getName().equals("immerrormsg") && ff.isDirectory()){
				LOG.info("搜索的目录为====>>>>"+ff.getName());
				 File[] files = ff.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
						    if(name.startsWith("immsg-2015-07-05"))
							return true;
						    else  return false;
						} 
					});
				 System.out.println(files.length);
				 for(File fff  : files){
				      LOG.info("当天要收集的日志路径为=======》"+fff.getAbsolutePath());
				     // getMsgService().collectMsgFromFile(fff); //不处理file为null异常，其他异常处理掉了
				  }
			}
		}
	}
	
}

