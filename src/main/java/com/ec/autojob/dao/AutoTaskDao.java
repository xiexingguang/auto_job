/**
 * Project Name:autoJob
 * File Name:AutoTaskDao.java
 * Package Name:com.ec.autojob.dao
 * Date:2015年6月29日下午9:07:40
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.dao;
import java.util.List;
import org.springframework.stereotype.Component;
import com.ec.autojob.bean.AutoTaskBean;
import com.ec.autojob.bean.StaticWXBean;

/**
 * ClassName:AutoTaskDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月29日 下午9:07:40 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component
public interface AutoTaskDao {
     
	
	 /**
	  * 
	  * getAutoTasks:(这里用一句话描述这个方法的作用). <br/>
	  * TODO(这里描述这个方法适用条件 – 可选).<br/>
	  * TODO(这里描述这个方法的执行流程 – 可选).<br/
	  * @author ecuser
	  * @return
	  * @since JDK 1.7
	  */
	 public List<AutoTaskBean> getAutoTasks();
	 
	 public  void update(AutoTaskBean task);
	 
	 
	 public void insert(StaticWXBean bean2);
	
}

