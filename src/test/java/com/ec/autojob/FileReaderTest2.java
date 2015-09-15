/**
 * Project Name:autoJob
 * File Name:FileReaderTest2.java
 * Package Name:com.ec.autojob
 * Date:2015年7月1日下午3:32:03
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * ClassName:FileReaderTest2 <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月1日 下午3:32:03 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class FileReaderTest2 {
	  
	
	  // 5kw 的数据要半个小时,比bufferreder快2分钟左右
	  public static void main(String[] args) throws IOException {
		  Runtime rt = Runtime.getRuntime();
		  System.out.println("totlaMemory"+rt.totalMemory()+"字节");
		  long beginTime = System.currentTimeMillis();
		  FileInputStream inputStream = null;
		  Scanner sc = null;
		  try {
		      inputStream = new FileInputStream("E:\\home\\scribe\\log\\primary\\immsg\\immsg-2015-06-30_00000");
		      sc = new Scanner(inputStream, "UTF-8");
		      while (sc.hasNextLine()) {
		          String line = sc.nextLine();
		          System.out.println(line);
		      }
		      // note that Scanner suppresses exceptions
		      if (sc.ioException() != null) {
		          throw sc.ioException();
		      }
		  } finally {
		      if (inputStream != null) {
		          inputStream.close();
		      }
		      if (sc != null) {
		          sc.close();
		      }
		  }
		 
		  long freeMemory = rt.freeMemory();
		  System.out.println("当前 Java 虚拟机中的空闲内存量：" + freeMemory + " 字节");
		  System.out.println("costTime===>"+(System.currentTimeMillis()-beginTime));
	}

}

