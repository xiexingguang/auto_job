/**
 * Project Name:autoJob
 * File Name:IoUtil.java
 * Package Name:com.ec.autojob.util
 * Date:2015年7月5日下午12:46:55
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * ClassName:IoUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月5日 下午12:46:55 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class IoUtil {
	
	public static void  freeStream(FileReader fReader,BufferedReader bufferedReader){
		
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

