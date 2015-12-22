package com.ec.autojob.app;
import com.ec.autojob.bean.AutoTaskBean;
import com.ec.autojob.common.TaskConstants;
import com.ec.autojob.dao.AutoTaskDao;
import com.ec.autojob.jetty.ServerJetty;
import com.ec.autojob.util.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Element;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class AutoJobEntry implements Job{
	
	private static final Logger LOG = LogManager.getLogger("appLog");
    private static final Map<String, AutoTaskBean> executingTask = new HashMap<>();


    @Autowired
	private AutoTaskDao autoTaskDao;
	
	@Autowired
	@Qualifier("staticMsgCosumerJob")
	private Job staticCusJob;
	
	
	@Autowired
	@Qualifier("staicMsgSumJob")
	private Job staticSumJob;
	
	
	@Autowired
	@Qualifier("staticMsgUsrJob")
	private Job userJob;
	
	@Autowired
	@Qualifier("fileCollectMsgJob")
	private Job collectJob;

	
	@Autowired
	@Qualifier("staticMsgWxJob")
	private Job staticWXJob;

    @Autowired
    @Qualifier("packageDataJob")
    private Job apiPackageJob;

	private  static Map<String,Job> JOB_MAP = new HashMap<String, Job>();
	
	@Autowired
	@Qualifier("serverJetty")
	private ServerJetty server;
	
	@PostConstruct
	private  void init(){
		
		 if(LOG.isDebugEnabled()){
			 LOG.debug("job初始化。。。");
		 }
		 
		 //初始化jetty
		 LOG.info("Jetty is listen on 9999");
		 server.startJetty();
		 
		JOB_MAP.put("collectJob", collectJob);
		JOB_MAP.put("staticSumJob",staticSumJob);
		JOB_MAP.put("staticUserJob", userJob);
		JOB_MAP.put("staticCustomJob", staticCusJob);
		JOB_MAP.put("staticMsgWxJob", staticWXJob);
        JOB_MAP.put("staticMsgWxJob", staticWXJob);
        JOB_MAP.put("apiPackageJob", apiPackageJob);
	}                    
	
	//private 
	
/*
*
	 * 
	 * process:自动任务job 入口<br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * @author xxg
	 * @throws Exception 
	 * @since JDK 1.7
*/



	@SuppressWarnings("deprecation")
	@Scheduled(cron = "0/5 * * * * *") 
	public void process() throws Exception{
		LOG.info("===================扫描自动任务开始========该任务执行时间为========="+ new Date().toLocaleString());
		List<AutoTaskBean> tasks = autoTaskDao.getAutoTasks();
		if(tasks == null){
			LOG.debug("当前没有可执行任务...");
			return;
		}
	    if(LOG.isDebugEnabled()){
	    	LOG.debug("当前的待执行的任务数为====》"+tasks.size());
	    }
	    // 调整当前时间
	    Calendar c = Calendar.getInstance(); 
	    Date nowDate = new Date();// 当前时间
		nowDate.setSeconds(0);//清空秒针
	    
		//遍历可执行的任务列表
		for(AutoTaskBean task : tasks){
		    LOG.debug("遍历的任务名称为====》"+task.getTaskName());
		    
		  try{	
			   Date   lastStart = task.getLastStartDate();
			   String frequence = task.getFrequence()+"";
			   String isExecuting = task.getStarting(); //是否正在执行，0 表示没有执行，1，表示执行成功，2，表示执行失败，3表示正在执行
			   String exectime = task.getExectime();    //格式时分2243：表示22点43分
			   if(exectime == null || exectime.length() != 4){ //如果格式不正确或者为空
				    exectime ="0000";
			   }
			   if(lastStart != null){  // 上次执行时间不为空表示已经执行过了
				   lastStart.setHours(Integer.parseInt(exectime.substring(0, 2)));   //设置时
				   lastStart.setMinutes(Integer.parseInt(exectime.substring(2, 4)));//设置分
				   lastStart.setSeconds(0);
				   c.setTime(lastStart);
					if ("1".equals(frequence)) { // 天 执行频率
						c.add(Calendar.DATE, 1);
					}else{
						c.add(Calendar.MONTH, 1);//月
					}
					Date ecTime = c.getTime();
					LOG.debug("当前时间为==》"+nowDate.toLocaleString() +"执行时间==》"+ecTime.toLocaleString());
					//如果当前时间小于上次开始执行日期+执行频率
					if (nowDate.getTime() < ecTime.getTime()) {
						continue;
					}
			   }else{ // 第一次执行
				    String hours = nowDate.getHours()<10 ? "0"+nowDate.getHours():""+nowDate.getHours();
					String minutes = nowDate.getMinutes()<10 ? "0"+nowDate.getMinutes():""+nowDate.getMinutes();
					//如果小于执行时间，则返回
					if(Integer.parseInt(hours+minutes)<Integer.parseInt(exectime)){
						continue;
					}
			   }//end if lastStart
			   if(TaskConstants.TASK_EXE_ING.equals(isExecuting)){     // 如果当前正在执行，则执行下条 
				   continue;                                           // 如果不是没有开始执行，则进行下次执行。 执行失败的 ，成功的，执行中的。不在执行
			   }
				String tasktype = task.getTaskType();  //  任务执行类型，是存储过程还是任务代码
				String taskpath = task.getTaskPath();  //  任务执行路径
				task.setStarting(TaskConstants.TASK_EXE_ING); // 设置正在执行
				task.setLastStartDate(new Date());
		 		autoTaskDao.update(task);          // 更新数据库任务状态
				if(TaskConstants.TASK_TYPE_CODE.equals(tasktype)){           // 如果是任务代码
					Element taskE = PropertiesUtil.getTaskElement(taskpath);  
					if(taskE == null){             // 如果没有任务
						task.setStarting(TaskConstants.TASK_EXE_FAIL);   // 设置执行失败
						task.setLastEndDate(new Date());
						autoTaskDao.update(task); // 然后更新数据库
						continue;
					}
					//String className = taskE.attributeValue("class");   //类名称
					//Job job =(Job)Class.forName(className).newInstance();
					Job job = JOB_MAP.get(taskpath);
					job.execute(null);  //执行job
				}else{
					
					 //TODO 目前还没扩展存储过程，或者其他，待以后扩展实现
				}
				task.setLastEndDate(new Date());
				task.setStarting(TaskConstants.TASK_EXE_SUCCESS);  //任务执行完成 ，设置执行成功标记
				autoTaskDao.update(task);
				 
			  }catch(Exception e){
				  LOG.error("执行 "+task.getTaskName() +" Job 失败 =====异常信息为==》"+e.toString());
				  task.setLastEndDate(new Date());
 				  task.setStarting(TaskConstants.TASK_EXE_FAIL);
				  autoTaskDao.update(task);
			  }
				
		   }//end for 在for循环中抓异常。。不够优雅，，看性能情况以后优化
		    
		}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	}
}
