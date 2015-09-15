/**
 * Project Name:SocketIO
 * File Name:WriteTestData.java
 * Package Name:com.xxg.nio
 * Date:2015年6月18日下午7:05:25
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * ClassName:WriteTestData <br/>
 * Function: 
 * 准备测试数据
 * 
 * <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月18日 下午7:05:25 <br/>
 * @author   ecuser
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class WriteTestData {
	
	
	
	
	    public static void main(String[] args) {
	    	
	    	
	    /*	String path = "e:/home/scribe/log/primary/immsg";
	    	
	    	File f = new File(path);
	    	System.out.println(f.mkdirs());*/
	    	  System.out.println("测试数据开始插入。。。。。。。。。。。");
		    	  long beginTime = System.currentTimeMillis();
		    	  FileOutputStream fis = null;
		    	    BufferedWriter bw = null;
		    	    String str="\r\n";
		    	    try {
		    	     
		    	     fis = new FileOutputStream("e:/home/scribe/log/primary/immsg/immsg-2015-06-31_00000");
		    	     bw = new BufferedWriter(new OutputStreamWriter(fis));
		    	     for(int i=0;i<1000000;i++)
		    	     {
		    	      bw.write("2014-05-15 23:41:32||131414||313134||1||2||3||"+i+str);
		    	     }
		    	     bw.flush();
		    	     System.out.println("写入文件完成!");
		    	    } catch (FileNotFoundException e) {
		    	     e.printStackTrace();
		    	    } catch (IOException e) {
		    	     e.printStackTrace();
		    	    } finally{
		    	     try {
		    	      if(fis!=null)fis.close();     
		    	      if(bw!=null) bw.close();
		    	     } catch (IOException e) {
		    	      e.printStackTrace();
		    	     }
		    	    }
	    	    System.out.println("写入测试数据一共耗时为："+(System.currentTimeMillis()-beginTime));
	    	
		}
	
	
	
	
	
	

}

