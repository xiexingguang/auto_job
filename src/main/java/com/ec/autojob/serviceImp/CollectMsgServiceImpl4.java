/**
 * Project Name:autoJob
 * File Name:CollectMsgServiceImpl.java
 * Package Name:com.ec.autojob.serviceImp
 * Date:2015年6月24日下午1:54:47
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.serviceImp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.ec.autojob.bean.LogMsgBean;
import com.ec.autojob.collectJob.core.CollectMsgExceptionHandler;
import com.ec.autojob.collectJob.core.PareDataRule;
import com.ec.autojob.common.MsgResultHandle;
import com.ec.autojob.common.exception.ConvertString2beanException;
import com.ec.autojob.common.exception.HandleCollectDataException;
import com.ec.autojob.common.exception.PareFileException;
import com.ec.autojob.service.CollectMsgFileLogService;
import com.ec.autojob.util.StringUtil;
import com.mongodb.MongoSocketReadException;
/**
 * ClassName:CollectMsgServiceImpl <br/>
 * Function:  <br/>
 * Reason:	. <br/>
 * Date:     2015年6月24日 下午1:54:47 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="collectMsgServiceImpl4")
public class CollectMsgServiceImpl4  implements CollectMsgFileLogService{
    
	private static final Logger LOG = LogManager.getLogger("collectJobLog");
	private static final Logger LOG_ERROR = LogManager.getLogger("errorLog");
	private static final Logger aLOG = LogManager.getLogger("appLog");
	
	/**
	 * 数据解析规类，将待转换的字符，根据指定解析规则，解析成对应的javabean对象
	 */
	@Autowired
	@Qualifier("pareString2MsgBean2")
	private PareDataRule pdrule;
	
	/**
	 * 消息结果出来策略，针对单条记录处理策略，有可能是入mysql库，可能入mogodb库，等。。。
	 */
	@Autowired
	@Qualifier("mongoMsgResultHandle")
	private MsgResultHandle msgHandle;
	
	
	/**
	 * 程序将日志  string 转成bean 出现异常
	 */
	@Autowired
	@Qualifier("convertString2beanException")
	private RuntimeException  convertString2BeanException;
	
	
	/**
	 * 对应异常处理类
	 */
	@Autowired
	@Qualifier("convertString2beanExHandler")
	private CollectMsgExceptionHandler convertString2BeanExceptionHandler;
	
	/**
	 * 异常处理map，异常类型和对应的处理器
	 */
	private  HashMap<RuntimeException,CollectMsgExceptionHandler> EXCEPTION_HANDLER = new HashMap<RuntimeException, CollectMsgExceptionHandler>();
	
	
	@PostConstruct
	private void init(){
		EXCEPTION_HANDLER.put(convertString2BeanException, convertString2BeanExceptionHandler);
	}
	
	
	@Override
	public void collectMsgFromSingleFile(String filepath) {
	/*	 File file = new File(filepath);
		 BlockingQueue<List<String>> sharedQueue = new LinkedBlockingQueue<List<String>>(); //共享的阻塞队列
		 aLOG.debug("sharedQueue===size"+sharedQueue.size());
		 Thread prodThread = new Thread(new Producer(sharedQueue,file));
		 Thread consThread = new Thread(new Consumer(sharedQueue,file));
		 prodThread.start();
	     consThread.start();*/
	}
	 
	/**
	 * 
	 * getpDataRule:日志解析规则，子类如果希望可以继承重写. <br/>
	 * @author xxg
	 * @return
	 * @since JDK 1.7
	 */
	  protected  PareDataRule getpDataRule(){
		   return pdrule; 
	  }
	  
	  /**
	   * 
	   * getMsgResultHandle:日志数据结果处理，子类可以重写该方法，默认为
	   * 将数据插入到mongodb中<br/>
	   * @author xxg
	   * @return
	   * @since JDK 1.7
	   */
	  protected  MsgResultHandle getMsgResultHandle(){
		    return msgHandle;
	  }

	@Override
	public void collectMsgFromFile(File file) {
		  
		 if(file == null || !file.exists()){
			 throw new NullPointerException("要解析的目录不存在或者为null");
		 }
	      if(file.isDirectory()){
	    	   File[] files = file.listFiles();
	    	   for(File f : files){
	    		   if(LOG.isDebugEnabled()){
	    			   LOG.debug("单个文件名称为===》"+f.getName() +"<===========单个日志文件路径为====》"+f.getAbsolutePath());
	    		   }
	    		   collectMsgFromSingleFileByFiles(f);   //统计数据，循环调用这样可能导致持续时间很长，这个方法会阻塞？优化？多线程？？先这样，如果有性能问题重写该方法
	    	   }
	      }	else{
	           collectMsgFromSingleFileByFiles(file);
	      }
	}

	
	// 收集单个文件数据
	/**
	 * 
	 * collectMsgFromSingleFileByFiles:(这里用一句话描述这个方法的作用). <br/>
	 * @author xxg
	 * @param file
	 * @since JDK 1.7
	 * @throws PareFileException :当文件解析的时候出现IO异常，这个时候异常处理不了，必须抛出
	 */
	private  void collectMsgFromSingleFileByFiles(File file) {
        BlockingQueue<List<String>> sharedQueue = new LinkedBlockingQueue<List<String>>(); //共享的阻塞队列
		// ConcurrentLinkedQueue<List<String>> sharedQueue = new ConcurrentLinkedQueue<List<String>>();
       //  aLOG.debug("sharedQueue===size"+sharedQueue.size());
	//	 Thread prodThread = new Thread(new Producer(sharedQueue,file));
		// Thread consThread = new Thread(new Consumer(sharedQueue,file));
		 
		 ExecutorService service = Executors.newFixedThreadPool(4);
			for(int i = 0 ;i<5;i++){
		     	service.execute(new Consumer(sharedQueue,file));
			}
			
		    
			service.execute(new Producer(sharedQueue, file));
	//	 prodThread.setName("生产者");
		// consThread.setName("消费者");
	//	 prodThread.start();
	   //  consThread.start();
	}
	
	
	
	class Producer implements Runnable {
		
	  //  private final ConcurrentLinkedQueue<List<String>> sharedQueue;
	    private final  BlockingQueue<List<String>> sharedQueue;
	    private final File file;
	 
	    public Producer(BlockingQueue<List<String>> sharedQueue,File file) {
	        this.sharedQueue = sharedQueue;
	        this.file = file;
	    }
	 
		@Override
	    public void run() {
	    	 int mongoExceptionCount = 0 ; //在一次解析文件中，mongodb允许出现的错误次数，最开始进入方法时，默认为0次
			 int strCount = 0;
			 List<String> strsCach = new ArrayList<String>();
			 if(file == null){
				 throw new NullPointerException("传入文件为null");
			 }
			 if(!file.exists()){
				 if(LOG.isDebugEnabled()){
					  LOG.debug("单个文件绝对路径为"+file.getAbsolutePath()+"===名称为"+file.getName());
				 }
				 throw new RuntimeException("文件不存在"+file.getName());
			 }
			 if(file.isDirectory()){
				 throw new RuntimeException("解析失败，该文件为一个目录，目录路径为"+file.getAbsolutePath());
			 }
			
			     FileReader fReader = null;
			     BufferedReader bufferedReader = null;
			     String str = "";
			    try {
			         fReader = new FileReader(file);
				     bufferedReader = new BufferedReader(fReader);
				  //   prule = getpDataRule(); //可扩展自己需要的解析规则
				     while((str = bufferedReader.readLine()) != null){
				    	 
				    	// flag = true;//　标记变量是判断，是否为第一次进来
				    	 if(LOG.isDebugEnabled()){
				    		 LOG.debug("单条消息日志记录为====="+str);
				    	 }
				    	 
				    	 try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								
								// TODO Auto-generated catch block
								e.printStackTrace();
								
							}
				    	 if(StringUtil.isChineseChar(str)){
				    		 continue;         // 判断日志是否有中文，如果有则直接下次读取
				    	 }
				    	 
				    	 if(strCount < 100000){ // 单数量少于10w的时候，就往临时list添加，不需要考虑多线，因为是局部变量
				    	     strsCach.add(str);  
					    	 strCount++;
				    	 }else{
				    		 sharedQueue.add(strsCach); //把数据放入队列里面
				    		 strCount = 0;
					    	 strsCach.clear();
				    		 
				    	 }
		              }//end while
				              
				            // sharedQueue.
				              sharedQueue.add(strsCach);
				              aLOG.debug("producer>>>>>===size"+sharedQueue.size());
				     
			    }catch(IOException e){
			    	 
		    	     LOG_ERROR.error("异常消息日志记录为["+str+"]异常信息为===》"+e.toString()+"出现异常文件为======》"+file.getAbsolutePath());
		    	     throw new PareFileException("解析文件出现IO异常", e);
		    	   
		        }catch (ConvertString2beanException e) { //出现在解析日志消息出现异常时
		    	 
		    	    LOG_ERROR.error("异常消息日志记录为["+str+"]异常信息为===》"+e.toString()+"出现异常文件为======》"+file.getAbsolutePath());
		    	    CollectMsgExceptionHandler  hander = EXCEPTION_HANDLER.get(e);
		    	    hander.handlerException(str);
		     
			    }catch(MongoSocketReadException e){
			  	    LOG_ERROR.error("异常消息日志记录为["+str+"]异常信息为===》"+e.toString()+"出现异常文件为======》"+file.getAbsolutePath());
				    mongoExceptionCount++;
				    if(mongoExceptionCount > 3){
				    	throw e;                   // 在解析同一个文件中，如果超过3次 则认为该不是由网络引起的异常，应带抛出
				    }
				    
			    }catch(HandleCollectDataException e){   //如果出现将收集到的 bean 插入mongodb或者mysql出现异常
				   LOG_ERROR.error("异常消息日志记录为["+str+"]异常信息为===》"+e.toString()+"出现异常文件为======》"+file.getAbsolutePath());
				   
			       //TODD 目前没有将异常的日志写入文件
			    }catch(Exception e){  // 其他异常
			 	    throw e;
				  
				  }finally{
					  freeStream(fReader,bufferedReader);
				}//end finally
	    	
	    	
	    }//end run
	       
	}//end class
	
             
	
	     /**
	      * 
	      * ClassName: Consumer <br/>
	      * Function: 消费者<br/>
	      * Reason:  <br/>
	      * date: 2015年7月3日 上午8:43:08 <br/>
	      *
	      * @author xxg
	      * @version CollectMsgServiceImpl3
	      * @since JDK 1.7
	      */
		class Consumer implements Runnable{
		// private final  ConcurrentLinkedQueue<List<String>> sharedQueue;
		   private final BlockingQueue<List<String>> sharedQueue;
		    private final File file;
		 
		    public Consumer (BlockingQueue<List<String>> sharedQueue,File file) {
		        this.sharedQueue = sharedQueue;
		        this.file = file;
		    }
		    @Override
		    public void run() {
		    	String fileName = file.getName();
		        //业务逻辑
		    	while(true){
		    		aLOG.debug("消费者消费数据");
		    		 aLOG.debug("popSharedQueue===size"+sharedQueue.size());
		    		List<String> strs = (List<String>)sharedQueue.poll();
					 aLOG.debug("popSharedQueue===size"+sharedQueue.size());
					List<LogMsgBean> logbeans = pdrule.pareStrings2Objects(strs, LogMsgBean.class); //当数据超过10w批量处理
					msgHandle.handleMsgBeans(logbeans,fileName);
		    		
		    	}
		    }
		 
		}
		
		/**
		 * 
		 * freeStream:关闭流方法 <br/>
		 * TODO(这里描述这个方法适用条件 – 可选).<br/>
		 * @author xxg
		 * @param fReader
		 * @param bufferedReader
		 * @since JDK 1.7
		 */
		
		private void freeStream(FileReader fReader, BufferedReader bufferedReader){
			
			 if(fReader  != null){
				  try {
					fReader.close();
				} catch (IOException e) {
					fReader = null;
					
				}
	         }
	        if(bufferedReader != null){
				  try {
					bufferedReader.close();
				} catch (IOException e) {
				    bufferedReader = null;
				}
	      }
		}
	
}

