/**
 * Project Name:autoJob
 * File Name:AutoJobHandler.java
 * Package Name:com.ec.autojob.netty
 * Date:2015年7月29日下午3:40:14
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.jetty;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.ec.autojob.api.CollectMsgApi;
import com.ec.autojob.bean.StaticCustResultBean;
import com.ec.autojob.bean.StaticSumResultBean;
import com.ec.autojob.bean.StaticUserResultBean;
import com.ec.autojob.bean.StaticWXResultBean;
import com.ec.autojob.service.StaticMsgService;
import com.ec.autojob.util.StringUtil;
/**
 * ClassName:AutoJobHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月29日 下午3:40:14 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="autoJobHandler")
public class AutoJobHandler extends AbstractHandler {
	
	
	private static final Logger LOG = LogManager.getLogger("apiLog");
	private static final Logger LOG_ERROR = LogManager.getLogger("errorLog");
	private static final int THREAD_SIZE = 5;
  
	@Autowired
	@Qualifier("collectMsgApi")
	private CollectMsgApi collectLogService; //收集日志job
	
	@Autowired
	@Qualifier("staticMsgSumServiceImpl2")
	private StaticMsgService staticSumservice; //统计消息总量
	
	@Autowired
	@Qualifier("staticMsgConsumerServiceImp2")
	private StaticMsgService staticKFservice;//统计网站客服
	
	@Autowired
	@Qualifier("staticMsgUserServiceImpl2")
	private StaticMsgService staticEimservice;//统计EIM活跃用户
	
	@Autowired
	@Qualifier("staticMsgWxServiceImpl2")
	private StaticMsgService staticWXservice; //统计微信
	
	
	private List<String> methods = new ArrayList<String>();
	// 根据不同的method做业务操作，异步方式
	private ExecutorService executor = Executors.newFixedThreadPool(THREAD_SIZE);// 初始化线程池
	@PostConstruct
	public void init(){
		 methods.add("collectLog");
		 methods.add("staticSum");
		 methods.add("staticEIM");
		 methods.add("staticWX");
		 methods.add("staticCustom");
	}
	
	
	@Override
	public void handle(String target, Request baseRequest,HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
        LOG.info("handle the request is begin, request uri is ===>"+request.getRequestURI());
        response.setContentType("text/html;charset=utf-8");  
        //1.请求报文校验过程，校验通过才起线程池做业务操作
        response.setStatus(HttpServletResponse.SC_OK);  
	    baseRequest.setHandled(true); 
	    
		//String requestBody = request.getParameter("json"); //请求参数
	
	    
		String requestBody = streamtransform(request.getInputStream());
	 	StringBuffer responseBody = new StringBuffer();
	 	LOG.info("请求参数为======================》"+requestBody);
		if(StringUtil.isNullString(requestBody)){
			responseBody.append("{\"code\":\"501\",\"msg\":\"the input param is null,please check\"}");
			response.getWriter().write(responseBody.toString());
			return;
		}
		JSONObject requestJson =null; 
		
  try{	
	      requestJson = JSONObject.parseObject(requestBody);
	  }catch(Exception e){
		  LOG_ERROR.error("转换json对象出现异常："+e);
		  responseBody.append("{\"code\":\"500\",\"msg\":\"json格式有错，json为"+requestJson+"\"}");
		  response.getWriter().write(responseBody.toString());
		  return;
	  }
	  
	  
	    final String method = requestJson.getString("method");
		final String pathValue = requestJson.getString("path");
		final String collection = requestJson.getString("collection");
		final String append = requestJson.getString("append");
		
		if(StringUtil.isNullString(method)|| StringUtil.isNullString(pathValue)){
			responseBody.append("{\"code\":\"500\",\"msg\":\"the body of method or path  is null,please check\"}");
			response.getWriter().write(responseBody.toString());
			return;
		}
		
		if(!method.contains(method)){
			responseBody.append("{\"code\":\"500\",\"msg\":\"the method is out of our sevice ,please check\"}");
			response.getWriter().write(responseBody.toString());
			return;
		}
		
		
		
		if(method.equals("collectLog")){
			LOG.info("收集日志开始执行");
			executor.execute(new Runnable() {
				@Override
				public void run() {
				   collectLogService.collectMsgFromSingleFile(pathValue, append, collection);
				   LOG.info("收集日志  执行结束");
				}
			});
			
			responseBody.append("{\"code\":\"200\",\"msg\":\"collectJob　is executing..please wait...\"}");
			response.getWriter().write(responseBody.toString());
			return;
		}
		if(method.equals("staticSum")){
			LOG.info("消息总数统计");
			executor.execute(new Runnable() {
				@Override
				public void run() {
					staticSumservice.handlerStaticData(pathValue,new StaticSumResultBean());
					LOG.info("统计总条数 执行结束");
				}
			});
			
			responseBody.append("{\"code\":\"200\",\"msg\":\"staticSum　is executing..please wait...\"}");
			response.getWriter().write(responseBody.toString());
			return;
		}
		
		if(method.equals("staticEIM")){
			LOG.info("每日EIM is begin excute");
			executor.execute(new Runnable() {
				@Override
				public void run() {
					staticEimservice.handlerStaticData(pathValue, new StaticUserResultBean());
					LOG.info("统计每日EIM  执行结束");
				}
			});
			
			responseBody.append("{\"code\":\"200\",\"msg\":\"staticEIM　is executing..please wait...\"}");
			response.getWriter().write(responseBody.toString());
			return;
		}
		
		
		if(method.equals("staticWX")){
			LOG.info("微信统计 is begin excute");
			executor.execute(new Runnable() {
				@Override
				public void run() {
					staticWXservice.handlerStaticData(pathValue, new StaticWXResultBean());
					LOG.info("微信统计 执行结束");
				}
			});
			
			responseBody.append("{\"code\":\"200\",\"msg\":\"staticWX　is executing..please wait...\"}");
			response.getWriter().write(responseBody.toString());
			return;
		 }
		
		
		  if(method.equals("staticCustom")){
			LOG.info("网站客服统计 is begin excute");
			executor.execute(new Runnable() {
				@Override
				public void run() {
					staticKFservice.handlerStaticData(pathValue, new StaticCustResultBean());
				}
			});
			
			responseBody.append("{\"code\":\"200\",\"msg\":\"staticCustom　is executing..please wait...\"}");
			response.getWriter().write(responseBody.toString());
			return;
		}
		

	}
	
	
	private String streamtransform(InputStream input) {
        StringBuilder sb = new StringBuilder();
        Reader reader = null;
        try {
            // 一次读一个字符
            reader = new InputStreamReader(input);
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    sb.append((char) tempchar);
                }
            }
        } catch (Exception e) {
            Logger errorLog = LogManager.getLogger("errorLog");
            errorLog.error(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Logger errorLog = LogManager.getLogger("errorLog");
                errorLog.error(e);
            }
        }
        return sb.toString();
    }

	
	
	
	
          public static void main(String[] args){
        	  /* String ss = "{\"code\":\"200\",\"msg\":\"staticWX　is executing..please wait...\"}";
        	   
        	   
        	   String kk = "{\"method\":\"collectLog\",\"path\":\"/home/scribe/log/primary/wx101/immsg-2015-07-29-1.log\",\"collection\":\"wxsg20150729\"}";
        	   JSONObject requestJson = JSONObject.parseObject(kk);
        	   System.out.println(requestJson.getString("path"));*/
          }
	           
	  
}

