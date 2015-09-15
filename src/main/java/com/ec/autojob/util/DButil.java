/**
 * Project Name:autoJob
 * File Name:DButil.java
 * Package Name:com.ec.autojob.util
 * Date:2015年6月29日下午3:33:01
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.util;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.ec.autojob.annotaion.CollectFlag;
import com.ec.autojob.annotaion.FiledName;
import com.ec.autojob.annotaion.Table;
import com.ec.autojob.annotaion.TimeFlag;
import com.ec.autojob.bean.StaticCustResultBean;
import com.ec.autojob.common.exception.GenerateInsertSqlException;

/**
 * ClassName:DButil <br/>
 * Function:  <br/>
 * Reason:	 <br/>
 * Date:     2015年6月29日 下午3:33:01 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class DButil {
	  
	  
	
	  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  /**
	   * 
	   * generatorSqlFromBean:(这里用一句话描述这个方法的作用). <br/>
	   *   <br/>
	   * 目前为适应项目需求，根据t的信息生成数据插入sql语句，目前仅
	   * 支持简单的插入，主键为自增。
	   * 目前仅支持 bean对象的域为float 类型，其他类型以后扩展
	   * @author xxg
	   * @param t  待插入的对象，该对象上需要自定义注解标记
	   * @databaseName 数据库名称
	   * @return
	   * @throws GenerateInsertSqlException 将反射各种异常包装后抛出
	   * @since JDK 1.7
	   */
	
	//jdbcTemplate.update("insert into user (name,age) "+"values (' " + name + " ',  " +age +")");
	   public  static <T> String generatorInsertSqlFromBean(T t,String databaseName){
		     StringBuffer sb = new StringBuffer("INSERT INTO  ");
			   try  {
			     @SuppressWarnings("rawtypes")
				 Class  tclass = t.getClass();
			     @SuppressWarnings("unchecked")
				 Table table = (Table)tclass.getAnnotation(Table.class);
			     String tableName = table.name(); // 获取表名
			     sb.append(databaseName+"."+tableName);
			     Field[] fileds = tclass.getFields();
			     String  rightSql = generatValuesProfix(fileds, t);
			     sb.append(rightSql);
			     return sb.toString();
	   }catch(Exception e){
		         throw new  GenerateInsertSqlException("自动生成sql出现异常,该类为===》"+t.getClass().getName(), e);
	   }
}
	   
	   // 拼接tablejdbcTemplate.update("insert into user (name,age) "+"values (' " + name + " ',  " +age +")"); ,拼接形如  (name,age) "+"values (' " + name + " ',  " +age +")") 格式字符
	   private static <T> String  generatValuesProfix(Field[] fileds,T  t) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		   StringBuffer beforeValues = new StringBuffer(); 
		   beforeValues.append("(");
		   StringBuffer afterValues = new StringBuffer();
		   afterValues.append("(");
		   int filedsLength = fileds.length;
		   
		   // 拼接字符串
		   for(int i = 0 ; i<filedsLength ; i++){
			      String filedName = fileds[i].getName(); //bean的域名称
				  FiledName filedNameAnn = fileds[i].getAnnotation(FiledName.class);  //获取bean域的注解
				  TimeFlag    timeFlag = fileds[i].getAnnotation(TimeFlag.class);     //这是个标记接口，标识字段类型是否为时间类型
				  CollectFlag  collflag = fileds[i].getAnnotation(CollectFlag.class); //标记集合名称，根据集合名词得到时间
				  // 拼接beforeValues
				  String tableFiledName = filedNameAnn.name(); //获取实际字段对应的表的字段名称
				  if(i == filedsLength - 1){
				   beforeValues.append(tableFiledName+")");
				  }else{
				   beforeValues.append(tableFiledName+",");
				  }
				  
				  //拼接afterValues
				  String methodName = "get"+StringUtil.toUpperFildeName(filedName);
				  Method m =   t.getClass().getDeclaredMethod(methodName);
				  double o =(double)  m.invoke(t); //因为事先已经知道了为o类型
				  if(timeFlag != null){ //如果不是time类型则直接拼接 解决
					  Calendar c = Calendar.getInstance();  // 将long类型转换成时间类型
					  c.setTimeInMillis((long)o);
					  Date d = c.getTime();
					  String time = sdf.format(d);  //将long类型转成string类型
						  if(i == filedsLength -1){                  // 判断是否为最后一个
							  afterValues.append("'"+time + "')");
						  }else{
							   afterValues.append("'"+time +" ',");
						  }
				  }else if(collflag != null){
					   String collectName = StringUtil.convertFolat2String(o);
					   if(i == filedsLength -1){                  // 判断是否为最后一个
							  afterValues.append("'"+collectName + "')");
						  }else{
							   afterValues.append("'"+collectName +" ',");
						  }
				  }else {  // 如果每个字段不是时间类型，则直接拼接
					     if(i == filedsLength - 1){
					    	  afterValues.append(o + " )");
					     }else{
					    	  afterValues.append(o + ",");
					     }
					  
				  }//end timeFlag
				 
				}//end for
				
			return beforeValues.toString()  + "values" + afterValues.toString();
	   }
		   

	    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	    	StaticCustResultBean  b = new StaticCustResultBean();
	    	b.setAvgKfCorp(134l);
	    	b.setEcliteKfcorp(22l);
	    	b.setEcliteKfUser(41513l);
	    	b.setMsgFk(12314l);
	    	b.setMsgKfTotalCorp(123123l);
	    	b.setMsgKfTotalUser(1313l);
	    	b.setStaticTime(System.currentTimeMillis());
	    	b.setStaticCol(20150514);
	    	System.out.println(DButil.generatorInsertSqlFromBean(b,"xxg"));
	    	
	    }
	   
}

