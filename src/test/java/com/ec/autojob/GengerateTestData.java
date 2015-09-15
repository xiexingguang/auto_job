/**
 * Project Name:formInterface
 * File Name:GengerateTestData.java
 * Package Name:com.ec.form.repository.test
 * Date:2015年6月23日下午7:48:55
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/**
 * ClassName:GengerateTestData <br/>
 * Function: 
 * 生产mongodb测试数据 <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月23日 下午7:48:55 <br/>
 * @author  xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class GengerateTestData {
	
	    public static void main(String[] args) {
	    	
	         List<ServerAddress> lists = new ArrayList<ServerAddress>();
	         ServerAddress seed = new ServerAddress("10.0.100.4", 20000);
	         ServerAddress seed1= new ServerAddress("10.0.100.2", 20000);
	         ServerAddress seed2 = new ServerAddress("10.0.100.5", 20000);
	         lists.add(seed2);
	         lists.add(seed1);
	         lists.add(seed);
	         
	         
	         MongoClient mongoClient = new MongoClient(lists);
	         MongoCollection collection = mongoClient.getDatabase("fristdb").getCollection("cmsg2015072922");
	         collection.deleteMany(Filters.exists("corpid"));
	         System.out.println(mongoClient.getMongoClientOptions());
	         /*
	    	  MongoClient client = new MongoClient("192.168.81.128", 30000);
			  MongoCollection collection = client.getDatabase("fristdb").getCollection("staticmsg");*/
	       /*  for(int i = 0 ; i<50000; i++){
	        	 String jsonString  = generateTestData();
	        	 Document document = Document.parse(jsonString);
	        	 collection.insertOne(document);
	        	
	         }*/
	        
		}
	    
	    
	    private static String  generateTestData(){
	    	 Date randomDate = randomDate("2015-04-01", "2015-06-24");  
	    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    	String  sendtimeString = format.format(randomDate);
	    	String  senderString = generateString6Num();
	    	String  rec = generateString6Num();
	    	String  userTypeString = generateUserType();
	    	String  msgTypeString = mssageTypeorMoudleType();
	    	String  corpIdString = generateString5Num();
	    	String  zdType = generateZdType();
	        String  Module = mssageTypeorMoudleType();
	        
	        //拼接报文
	        StringBuffer sb = new StringBuffer();
	        sb.append("{\"sedtime\":"+"\""+sendtimeString+"\",")
	           .append("\"sender\":"+"\""+senderString+"\",")
	           .append("\"recieve\":"+"\""+rec+"\",")
	           .append("\"msgtype\":"+"\""+msgTypeString+"\",")
	           .append("\"corpid\":"+"\""+corpIdString+"\",")
	           .append("\"zdtype\":"+"\""+zdType+"\",")
	           .append("\"module\":"+"\""+Module+"\"}");
	    	return sb.toString();
	    }
	    
	   
	    //生成6位随机数,发送者id,接收者id
	    private static String generateString6Num(){
	    	return (int)((Math.random()*9+1)*100000)+"";
	    }
	    //生成5位随机数
	    private static String generateString5Num(){
	    	return (int)((Math.random()*9+1)*10000)+"";
	    }
	    
	    //1,2,3,4
	    public static String generateZdType(){
	    	return ((int) (Math.random() * 4) + 1)+"";
	    }
	    //1,2,3,4,5
	    public static String generateUserType(){
	    	return ((int) (Math.random() * 5) + 1)+"";
	    	
	    }
	    
	    //1,2,3
	    public static String mssageTypeorMoudleType(){
	    	return ((int) (Math.random() * 3) + 1)+"";
	    }
	    private static Date randomDate(String beginDate, String endDate) {  
	        try {  
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	            Date start = format.parse(beginDate);// 构造开始日期  
	            Date end = format.parse(endDate);// 构造结束日期  
	            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。  
	            if (start.getTime() >= end.getTime()) {  
	                return null;  
	            }  
	            long date = random(start.getTime(), end.getTime());  
	  
	            return new Date(date);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
	  
	    private static long random(long begin, long end) {  
	        long rtn = begin + (long) (Math.random() * (end - begin));  
	        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值  
	        if (rtn == begin || rtn == end) {  
	            return random(begin, end);  
	        }  
	        return rtn;  
	    }  
	  

}

