/**
 * Project Name:autoJob
 * File Name:FileReaderTest.java
 * Package Name:com.ec.autojob
 * Date:2015年7月1日下午3:15:58
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.ec.autojob.bean.LogMsgBean;
import com.ec.autojob.collectJob.core.PareDataRule;
import com.ec.autojob.util.StringUtil;

/**
 * ClassName:FileReaderTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月1日 下午3:15:58 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class FileReaderTest {
	
	
	
	public static void main(String[] args) throws IOException {
		  
		  // 读5kw 的时间量 大概33-34分钟左右
		  long beginTime = System.currentTimeMillis();
		  File  file  = new File("E:\\home\\scribe\\log\\primary\\immsg\\immsg-2015-06-30_00000");
		    
		     FileReader fReader = null;
		     BufferedReader bufferedReader = null;
		     String str = "";
		    
		  
			     fReader = new FileReader(file);
			     bufferedReader = new BufferedReader(fReader);
			  //   PareDataRule pdrule = new ; //可扩展自己需要的解析规则
			     while((str = bufferedReader.readLine()) != null){
			    	
			    	 if(StringUtil.isChineseChar(str)){
			    		 continue;         // 判断日志是否有中文，如果有则直接下次读取
			    	 }
			    	System.out.println(str);
			     }//end while
			     fReader.close();
			     bufferedReader.close();
			     
			     System.out.println("cost time is "+(System.currentTimeMillis()-beginTime));
	}

}

