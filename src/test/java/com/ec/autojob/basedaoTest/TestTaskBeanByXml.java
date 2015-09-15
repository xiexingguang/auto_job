/**
 * Project Name:autoJob
 * File Name:TestTaskBeanByXml.java
 * Package Name:com.ec.autojob.basedaoTest
 * Date:2015年6月30日下午5:08:28
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.basedaoTest;

import org.dom4j.Element;
import org.quartz.Job;

import com.ec.autojob.util.PropertiesUtil;
import com.ec.autojob.util.StringUtil;

/**
 * ClassName:TestTaskBeanByXml <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月30日 下午5:08:28 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TestTaskBeanByXml {
	
	 public static void main(String[] args) throws Exception {
		
		 Element e = PropertiesUtil.getTaskElement("staticSumJob");
		 String className =e.attributeValue("class");
		 System.out.println(className);
		 Job job = (Job)Class.forName(className).newInstance();
		 System.out.println(job.getClass());
		/* String ss ="wewewrwr";
	    System.out.println(StringUtil.isChineseChar(ss));
		 */
		 
	}

}

