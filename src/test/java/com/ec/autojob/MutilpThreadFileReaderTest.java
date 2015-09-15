/**
 * Project Name:autoJob
 * File Name:MutilpThreadFileReaderTest.java
 * Package Name:com.ec.autojob
 * Date:2015年7月2日上午8:41:51
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName:MutilpThreadFileReaderTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年7月2日 上午8:41:51 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class MutilpThreadFileReaderTest {
	
	
	 public static void main(String args[]) throws Exception{ 
		 
		    //String infile = "D:\\workspace\\test\\usagetracking.log"; 
		    //FileInputStream fin= new FileInputStream(infile); 
		    //FileChannel fcin = fin.getChannel(); 
		 
		    int bufSize = 100; 
		    File fin = new File("E:\\home\\scribe\\log\\primary\\immsg\\immsg-2015-06-31_00000"); 
		    File fout = new File("D:\\xxg\\immsg-2015-05-03_00000"); 
		 
		    @SuppressWarnings("resource")
			FileChannel fcin = new RandomAccessFile(fin, "r").getChannel(); 
		    ByteBuffer rBuffer = ByteBuffer.allocate(bufSize); 
		 
		    @SuppressWarnings("resource")
			FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel(); 
		    ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize); 
		 
		    long beginTime = System.currentTimeMillis();
		    readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer); 
		    System.out.println("cost time is ==="+(System.currentTimeMillis()-beginTime)/60000+"分钟");
		 
		    System.out.print("OK!!!"); 
		    } 
		 
		    public static void readFileByLine(int bufSize, FileChannel fcin, ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer){ 
		        String enterStr = "\n"; 
		        try{ 
		        byte[] bs = new byte[bufSize]; 
		 
		        int size = 0; 
		        StringBuffer strBuf = new StringBuffer(""); 
		        //while((size = fcin.read(buffer)) != -1){ 
		        while(fcin.read(rBuffer) != -1){ 
		              int rSize = rBuffer.position(); 
		              rBuffer.rewind(); 
		              rBuffer.get(bs); 
		              rBuffer.clear(); 
		              String tempString = new String(bs, 0, rSize); 
		              //System.out.print(tempString); 
		              //System.out.print("<200>"); 
		 
		              int fromIndex = 0; 
		              int endIndex = 0; 
		              while((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1){ 
		               String line = tempString.substring(fromIndex, endIndex); 
		               line = new String(strBuf.toString() + line); 
		               System.out.print(line); 
		               //System.out.print("</over/>"); 
		               //write to anthone file 
		             //  writeFileByLine(fcout, wBuffer, line); 
		 
		                
		               strBuf.delete(0, strBuf.length()); 
		               fromIndex = endIndex + 1; 
		              } 
		              if(rSize > tempString.length()){ 
		              strBuf.append(tempString.substring(fromIndex, tempString.length())); 
		              }else{ 
		              strBuf.append(tempString.substring(fromIndex, rSize)); 
		              } 
		        } 
		        } catch (IOException e) { 
		        // TODO Auto-generated catch block 
		        e.printStackTrace(); 
		        } 
		    } 
		 
		    public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line){ 
		        try { 
		            //write on file head 
		            //fcout.write(wBuffer.wrap(line.getBytes())); 
		            //wirte append file on foot 
		            fcout.write(wBuffer.wrap(line.getBytes()), fcout.size()); 
		 
		        } catch (IOException e) { 
		            // TODO Auto-generated catch block 
		            e.printStackTrace(); 
		        } 
		    } 
		 
		}


