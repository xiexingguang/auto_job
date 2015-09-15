package com.ec.autojob.basedaoTest;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;

import com.ec.autojob.bean.LogMsgBean;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertManyOptions;

/**
 * 本机测试mongodb
 * @author jasshine_xxg
 *
 */
public class MongoTest  {
	
	 /**
	  * {
  "_id" : ObjectId("558e542065a5af20d8e5b4eb"),
  "sedtime" : "2015-06-18 21:28:00",
  "sender" : "588854",
  "recieve" : "743855",
  "msgtype" : "2",
  "corpid" : "18791",
  "zdtype" : "2",
  "module" : "1"
}
	  * @param args
	  */
	  public static void main(String[] args) {
		  MongoClient client = new MongoClient("192.168.81.128", 30000);
		  MongoCollection c = client.getDatabase("fristdb").getCollection("staticmsg");
		  Document d = new Document();
		 // System.out.println(c.getCodecRegistry());
		 
		  //统计db.msg.find({"zdtype":"3","module":"1"}).count();
		 //Document o =  (Document) c.find(Filters.eq("recieve", "743855")).first();
		  
		 //System.out.println(o.toJson());
		//  c.find().
		  
		//1.统计  c.count(Filters.and(Filters.eq("zdtype", "3"),Filters.eq("module", "1")))
		  
		  System.out.println(c.count(Filters.and(Filters.eq("zdtype", "3"),Filters.eq("module", "1"))));
		  
		 // c.distinct(fieldName, resultClass)
		System.out.println(c.distinct("sender", String.class).filter(Filters.eq("module", "1")).into(new ArrayList()).size());
	//	System.out.println();
		//c.distinct("sender", null)
		//c.distinct("", String.class).into(new ArrayList()).size();
	
		//c.distinct("sender", String.class).filter(filter)
		
		//InsertManyOptions
	}
	  
	  

}
