/**
 * Project Name:autoJob
 * File Name:FileReaderTest3.java
 * Package Name:com.ec.autojob
 * Date:2015年7月1日下午3:40:58
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * ClassName:FileReaderTest3 <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月1日 下午3:40:58 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class FileReaderTest3 {
	
	 public static void main(String[] args) throws IOException {
		  Runtime rt = Runtime.getRuntime();
		  System.out.println("totlaMemory ====>"+rt.totalMemory()/(1024*1024)+"M");
		  long beginTime = System.currentTimeMillis();
		 File  file  = new File("E:\\home\\scribe\\log\\primary\\immsg\\immsg-2015-06-30_00000");
		 LineIterator it = FileUtils.lineIterator(file, "UTF-8");
		 try {
		     while (it.hasNext()) {
		         String line = it.nextLine();
		         System.out.println(line);
		     }
		 } finally {
		     LineIterator.closeQuietly(it);
		 }
		 
		 System.out.println("cost time is "+(System.currentTimeMillis()-beginTime));
	}
	
	

}

