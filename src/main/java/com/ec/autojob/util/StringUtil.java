/**
 * Project Name:autoJob
 * File Name:StringUtil.java
 * Package Name:com.ec.autojob.util
 * Date:2015年6月24日上午11:22:23
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.util;

import com.ec.autojob.common.MsgConstants;
import com.ec.autojob.common.exception.AayLengthNotEqObFileNumsException;
import com.ec.autojob.common.exception.CallReflectSetException;
import com.ec.autojob.common.exception.WriteStringToLogException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:StringUtil <br/>
 * Function: 
 * 字符串工具类
 *  
 *  <br/>
 * Date:     2015年6月24日 上午11:22:23 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class StringUtil {
	
	
	/**
	 * 
	 * convertStringArray2Object:(将字符数组里面的值映射成javabean对象).
	 * 
	 *  <br/>
	 * @param arrays
	 * @param 
	 * @return
	 * @throws AayLengthNotEqObFileNumsException 数组长度跟bean数量不一致出现异常
	 * @throws CallReflectSetException   反射调用set** 方法出现异常。
	 * @throws 其他异常
	 * @since JDK 1.7
	 */
	public  static <T>  T  convertStringArray2Object(String[] arrays,Class<T> tt){
		
			  //  Class  tclass = t.getClass();
			   Field[] fields = tt.getFields(); //得到该类的所有域
			   T o = null;
			   if(fields.length != arrays.length){
				   throw new AayLengthNotEqObFileNumsException("数组长跟根实体bean长度不一致，转换失败");//自定义异常
			   }
			   try{
				   o = tt.newInstance();
				   for(int i = 0 ; i < arrays.length ; i++){
						 String fieldName = fields[i].getName();
						 String methodName = "set"+toUpperFildeName(fieldName);
						 Method m = tt.getMethod(methodName, fields[i].getType());
						 m.invoke(o, arrays[i]);
				   }
			   }catch(Exception e){
				       throw new CallReflectSetException("call set 方法出现异常", e);
			   }
		   return o;
	}
	
	public  static String toUpperFildeName(String fileName){
		 String fristChar = fileName.substring(0, 1).toUpperCase();
		 String newS = fristChar + fileName.substring(1, fileName.length());
		 return newS;
	}
	
	
	/**
	 * 
	 * convertStringArray2Object:(将字符数组里面的值映射成javabean对象).
	 * 
	 *  <br/>
	 * @param arrays
	 * @param 
	 * @return
	 * @throws AayLengthNotEqObFileNumsException 数组长度跟bean数量不一致出现异常
	 * @throws CallReflectSetException   反射调用set** 方法出现异常。
	 * @throws 其他异常
	 * @since JDK 1.7
	 */
	public  static <T>  T  convertStringArray2Object(double[] arrays,T t){
		
			  @SuppressWarnings("rawtypes")
			   Class  tclass = t.getClass();
			   Field[] fields = tclass.getFields(); //得到该类的所有域
			   
			   if(fields.length != arrays.length){
				   throw new AayLengthNotEqObFileNumsException("数组长跟根实体bean长度不一致，转换失败");//自定义异常
			   }
			   try{
				   for(int i = 0 ; i < arrays.length ; i++){
						 String fieldName = fields[i].getName();
						 String methodName = "set"+toUpperFildeName(fieldName);
						 @SuppressWarnings("unchecked")
						 Method m = tclass.getMethod(methodName, fields[i].getType());
						 m.invoke(t, arrays[i]);
				   }
			   }catch(Exception e){
				       throw new CallReflectSetException("call set 方法出现异常", e);
			   }
		   return t;
	}
	
	
	/**
	 * 
	 * convertStringArray2Object:(将字符数组里面的值映射成javabean对象).
	 * 
	 *  <br/>
	 * @param arrays
	 * @param 
	 * @return
	 * @throws AayLengthNotEqObFileNumsException 数组长度跟bean数量不一致出现异常
	 * @throws CallReflectSetException   反射调用set** 方法出现异常。
	 * @throws 其他异常
	 * @since JDK 1.7
	 */
	public  static <T>  T  convertStringArray2Object(float[] arrays,T t){
		
			  @SuppressWarnings("rawtypes")
			   Class  tclass = t.getClass();
			   Field[] fields = tclass.getFields(); //得到该类的所有域
			   
			   if(fields.length != arrays.length){
				   throw new AayLengthNotEqObFileNumsException("数组长跟根实体bean长度不一致，转换失败");//自定义异常
			   }
			   try{
				   for(int i = 0 ; i < arrays.length ; i++){
						 String fieldName = fields[i].getName();
						 String methodName = "set"+toUpperFildeName(fieldName);
						 @SuppressWarnings("unchecked")
						 Method m = tclass.getMethod(methodName, fields[i].getType());
						 m.invoke(t, arrays[i]);
				   }
			   }catch(Exception e){
				       throw new CallReflectSetException("call set 方法出现异常", e);
			   }
		   return t;
	}
	

	
	/**
	 * 
	 * writeString2File:将记录一行行写入到文件中去. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author xxg
	 * @param filepath，文件完整路径，包括文件名 d://xxg/yyy/1.txt
	 * @throws WriteStringToLogException 
	 * @since JDK 1.7
	 */
	public  static void writeString2File(String filepath,String msg){
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			  File  file = new File(filepath);
			  if(!file.exists()){
				  String parentFliePath = file.getParent();
				  File  parenFile = new File(parentFliePath);
				  if(parenFile.mkdirs()){
					  file.createNewFile();
				  }//end if
			  }//end if
			  fw = new FileWriter(file,true);
		  	  bw = new BufferedWriter(fw);
		  	  bw.write(msg+"\n\r");
		}catch(Exception e){
			  throw new WriteStringToLogException("写入数据到文件异常", e);
		}finally{
				 if(bw!=null){
					   try {
							bw.close();
						} catch (IOException e) {
						    bw = null;
						}
				 }
				 if(fw!=null){
					   try {
						fw.close();
					} catch (IOException e) {
					    fw = null;
					}
				 }
		}
	}
	
	/**
	 * 
	 * isNullString:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author xxg
	 * @param input
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean isNullString(String input){
		return (input==null||input=="");
	}
	
	
	/**
	 * 
	 * isChineseChar:(这里用一句话描述这个方法的作用). <br/>
	 * TODO 判断是否为中文.<br/>
	 * @author xxg
	 * @param str
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean isChineseChar(String str){
	       boolean temp = false;
	       Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
	       Matcher m=p.matcher(str); 
	       if(m.find()){ 
	           temp =  true;
	       }
	       return temp;
	   }
	
	
	/**
	 * 
	 * pareFileName:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 类似解析入场：immsg-2015-05-14_00000 j
	 *   要得到如下cmsg20150514格式
	 * @author xxg
	 * @param str
	 * @return
	 * @since JDK 1.7
	 */
	public static String pareFileName(String str){
		//TODO 可设计抛异常
		return  MsgConstants.LOG_COLLECTION_NAME_PRFIX+str.substring(6,16).replace("-", ""); 
		
	}
	
	/**
	 * 
	 * pareColelctionName:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 将cmsg20150524
	 * @author xxg
	 * @param str
	 * @return
	 * @since JDK 1.7
	 */
	public static String pareColelctionName(String str){
	     return str.substring(4);
	}


    public static List<Long> convertStringList2LongList(List<String> strs) {
        List<Long> longs = new ArrayList<>();
        for (String s : strs) {
            Long l = Long.parseLong(s);
            longs.add(l);
        }
        return longs;
    }

    /**
	 * 
	 * convertFolat2String:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 将float类型该类型为20150614格式的
	 * 转换成时间格式2015-06-14
	 * @author xxg
	 * @return
	 * @since JDK 1.7
	 */
	public static String convertFolat2String(double str){
		String tmp = (int)str+""; //先转成string类型
		return tmp.substring(0,4)+"-"+tmp.substring(4,6)+"-"+tmp.substring(6,8);
		
	}
	
      public static void main(String[] args) throws IOException {
		/*   File file = new File("d:/wewe/xxg/ee.txt");
		 //  System.out.println(file.isDirectory());
		   String s = file.getParent();
		   System.out.println(s);
		   
		   File parentFI = new File(s);
		   System.out.println(parentFI.mkdirs());
		   System.out.println(file.exists()); //如果文件不存在，则创建新文件
		   file.createNewFile();*/
    	  
    	  //writeString2File("d:/xxg/2.txt","i love you");
    	/*  File f = new File("d://xxg");
    	  
    	  File[]  fs = f.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
                if(name.startsWith("immsg-2015-05-03"))
				return true;
                else return false;
			}
		});
    	  System.out.println(f.isFile());
    	  System.out.println(f.exists());
         for(File ff:fs){
        	  System.out.println(ff.getName());
         }*/
    	  
    	  
    	/*  String[] araay = {"1","2","3","4","5","321","123123","123"};
    	  Long[] lon ={1l, 2l,3l,4l,5l,65l,52l,123l};
    	  List<Long> resultLists = new ArrayList<Long>();
    	  resultLists.add(1l);
    	  resultLists.add(1l);
    	  resultLists.add(1l);
    	  resultLists.add(1l);
    	  resultLists.add(1l);
    	  resultLists.add(1l);
    	  resultLists.add(1l);
    	  resultLists.add(1l);*/
    	  
    	 // Long[] ss =(Long[])resultLists.toArray();
    	  
    //  StaticSumResultBean SUM = convertStringArray2Object(lon,new StaticSumResultBean());
    	//  System.out.println(SUM.getAndroidEIM());immsg-2015-05-14_00000 j
    	/*  
    	  String msg = "immsg-2015-05-14_00000 ";
    	  String ssss = "20150514";
    	  float bbb = Float.parseFloat(ssss);
    	 // System.out.println((int)bbb+"".);
    	  float ss = 20150514;
    	  System.out.println("cmsg"+msg.substring(6,16 ).replace("-", ""));
    	  System.out.println("20150514".substring(0, 4)+"-"+"20150514".substring(4,6)+"-"+"20150514".substring(6, 8));
    	  
    	 
    	  System.out.println(pareColelctionName("cmsg2015014"));
    	  float sssss = 20150514;
    	  System.out.println(convertFolat2String(sssss));*/
    	  
    	
    	  double  kkk = Double.parseDouble("20150705");
    	  System.out.println((int)kkk);
	} 
      
      
      
      
}

